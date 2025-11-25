package de.mathis.deathrespect;

import de.mathis.deathrespect.config.DeathRespectConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathRespect implements ModInitializer {
	public static final String MOD_ID = "AutoF";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        MidnightConfig.init("deathrespect", DeathRespectConfig.class);
		LOGGER.info("DeathRespect initialized.");
	}
}