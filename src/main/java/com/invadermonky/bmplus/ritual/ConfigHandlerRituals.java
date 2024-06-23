package com.invadermonky.bmplus.ritual;

import WayofTime.bloodmagic.ConfigHandler;

public class ConfigHandlerRituals {
    public static boolean ritualCondor;
    public static boolean ritualCrystalSplit;
    public static boolean ritualEllipsoid;
    public static boolean ritualEternalSoul;
    public static boolean ritualFeatheredEarth;
    public static boolean ritualGrounding;
    public static boolean ritualVeilOfEvil;
    public static boolean ritualWardOfSacrosanctity;
    
    public static void injectConfigs() {
        ritualCondor = ConfigHandler.getConfig().get("Rituals", "ritualCrystalSplit", true).getBoolean();
		ritualCrystalSplit = ConfigHandler.getConfig().get("Rituals", "ritualCrystalSplit", true).getBoolean();
		ritualEllipsoid = ConfigHandler.getConfig().get("Rituals", "ritualEllipsoid", true).getBoolean();
		ritualEternalSoul = ConfigHandler.getConfig().get("Rituals", "ritualEternalSoul", true).getBoolean();
		ritualFeatheredEarth = ConfigHandler.getConfig().get("Rituals", "ritualFeatheredEarth", true).getBoolean();
		ritualGrounding = ConfigHandler.getConfig().get("Rituals", "ritualGrounding", true).getBoolean();
		ritualVeilOfEvil = ConfigHandler.getConfig().get("Rituals", "ritualVeilOfEvil", true).getBoolean();
		ritualWardOfSacrosanctity = ConfigHandler.getConfig().get("Rituals", "ritualWardOfSacrosanctity", true).getBoolean();
    }
}
