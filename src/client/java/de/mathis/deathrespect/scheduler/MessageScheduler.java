package de.mathis.deathrespect.scheduler;

import de.mathis.deathrespect.config.DeathRespectConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class MessageScheduler {

    private record ScheduledMessage(long executeAt, String message) {}

    private static final Queue<ScheduledMessage> queue = new ArrayDeque<>();

    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    public static void scheduleMessage(Player player) {
        float min = DeathRespectConfig.delayMinSec;
        float max = DeathRespectConfig.delayMaxSec;

        if (max < min) {
            float temp = min;
            min = max;
            max = temp;
        }

        float delaySec = DeathRespectConfig.useRandomDelay
                ? RNG.nextFloat(min, max)
                : min;

        long delayMs = (long) (delaySec * 1000f);
        long target = System.currentTimeMillis() + delayMs;

        String playerName = player.getName().getString();
        String formatted = DeathRespectConfig.messageTemplate.replace("%player%", playerName);

        queue.add(new ScheduledMessage(target, formatted));
    }

    public static void tick(Minecraft client) {
        if (queue.isEmpty()) return;
        if (client.player == null) return;

        long now = System.currentTimeMillis();

        // Process queue in correct order
        while (!queue.isEmpty() && queue.peek().executeAt <= now) {
            ScheduledMessage msg = queue.poll();
            sendChat(client, msg.message());
        }
    }

    private static void sendChat(Minecraft client, String text) {
        if (client.player != null) {
            client.player.connection.sendChat(text);
        }
    }
}
