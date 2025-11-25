package de.mathis.deathrespect.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class DeathRespectConfig extends MidnightConfig {

    // ============================================================
    // CATEGORIES
    // ============================================================

    public static final String GENERAL = "general";
    public static final String DELAY   = "delay";


    // ============================================================
    // GENERAL CATEGORY
    // ============================================================

    @Entry(category = GENERAL, name = "deathrespect.midnightconfig.enabled")
    public static boolean enabled = true;

    @Comment(category = GENERAL)
    public static Comment spacer1;

    @Comment(category = GENERAL, name = "deathrespect.midnightconfig.messageComment")
    public static Comment messageComment;
    @Entry(category = GENERAL, name = "deathrespect.midnightconfig.messageTemplate")
    public static String messageTemplate = "F";

    @Comment(category = GENERAL, name = "deathrespect.midnightconfig.messageSuggestions")
    public static Comment messageSuggestions;


    // ============================================================
    // DELAY CATEGORY
    // ============================================================

    @Comment(category = DELAY, name = "deathrespect.midnightconfig.randomDelayComment")
    public static Comment randomDelayComment;

    @Entry(category = DELAY, name = "deathrespect.midnightconfig.useRandomDelay")
    public static boolean useRandomDelay = true;


    @Comment(category = DELAY, name = "deathrespect.midnightconfig.delayMinComment")
    @Entry(category = DELAY, min = 0f, max = 10f, precision = 10, isSlider = true,
            name = "deathrespect.midnightconfig.delayMinSec")
    public static float delayMinSec = 1.0f;


    @Comment(category = DELAY, name = "deathrespect.midnightconfig.delayMaxComment")
    @Entry(category = DELAY, min = 0f, max = 10f, precision = 10, isSlider = true,
            name = "deathrespect.midnightconfig.delayMaxSec")
    @Condition(requiredOption = "deathrespect:useRandomDelay", requiredValue = "true")
    public static float delayMaxSec = 3.0f;
}
