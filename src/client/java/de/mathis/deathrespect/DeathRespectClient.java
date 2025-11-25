package de.mathis.deathrespect;

import de.mathis.deathrespect.config.DeathRespectConfig;
import de.mathis.deathrespect.detection.DeathDetector;
import de.mathis.deathrespect.scheduler.MessageScheduler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class DeathRespectClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Register callback that fires when another player's death is detected
        DeathDetector.setDeathListener(player -> {
            if (DeathRespectConfig.enabled) {
                MessageScheduler.scheduleMessage(player);
            }
        });

        // Main client tick loop
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            // If the mod is disabled, do nothing
            if (!DeathRespectConfig.enabled) return;

            // Update death detection logic
            DeathDetector.tick(client);

            // Update scheduled messages (check if it's time to send message)
            MessageScheduler.tick(client);
        });

        DeathRespect.LOGGER.info("Client initialized.");
    }
}
