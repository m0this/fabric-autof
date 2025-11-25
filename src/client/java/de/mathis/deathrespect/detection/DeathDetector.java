package de.mathis.deathrespect.detection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class DeathDetector {

    private static final Map<UUID, Float> lastHealthMap = new HashMap<>();

    public interface DeathListener {
        void onPlayerDeath(Player player);
    }

    private static DeathListener listener;

    public static void setDeathListener(DeathListener l) {
        listener = l;
    }

    public static void tick(Minecraft client) {
        if (client.level == null || client.player == null) return;

        List<AbstractClientPlayer>  currentPlayers = client.level.players();
        UUID localPlayerId = client.player.getUUID();

        // Remove left players from state maps
        lastHealthMap.keySet().removeIf(uuid ->
                currentPlayers.stream().noneMatch(p -> p.getUUID().equals(uuid))
        );

        for (AbstractClientPlayer p : currentPlayers) {

            // Do not track the local player
            if (p.getUUID().equals(localPlayerId)) continue;

            float currentHealth = p.getHealth();
            float lastHealth = lastHealthMap.getOrDefault(p.getUUID(), currentHealth);

            boolean isDead = currentHealth <= 0f;
            boolean wasAlive = lastHealth > 0f;

            // Detect death: previous tick alive, now dead
            if (wasAlive && isDead) {
                if (listener != null) {
                    listener.onPlayerDeath(p);
                }
            }

            lastHealthMap.put(p.getUUID(), currentHealth);
        }
    }
}
