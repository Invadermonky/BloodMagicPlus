package com.invadermonky.bmplus;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.registry.RitualRegistry;
import com.invadermonky.bmplus.potions.RegistrarPotions;
import com.invadermonky.bmplus.proxy.CommonProxy;
import com.invadermonky.bmplus.ritual.*;
import com.invadermonky.bmplus.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = BMPlus.MOD_ID,
        name = BMPlus.MOD_NAME,
        version = BMPlus.MOD_VERSION,
        acceptedMinecraftVersions = BMPlus.MC_VERSION,
        dependencies = BMPlus.DEPENDENCIES
)
public class BMPlus {
    public static final String MOD_ID = "bmplus";
    public static final String MOD_NAME = "Blood Magic+";
    public static final String MOD_VERSION = "1.10.2-1.0.0";
    public static final String MC_VERSION = "[1.10.2]";
    public static final String DEPENDENCIES =
            "required-after:" + Constants.Mod.MODID;

    public static final String ProxyClientClass = "com.invadermonky."+ MOD_ID + ".proxy.ClientProxy";
    public static final String ProxyServerClass = "com.invadermonky."+ MOD_ID + ".proxy.CommonProxy";

    @Mod.Instance(MOD_ID)
    public static BMPlus instance;

    @SidedProxy(modId = MOD_ID, clientSide = ProxyClientClass, serverSide = ProxyServerClass)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting " + MOD_NAME);
        ConfigHandlerRituals.injectConfigs();
        RegistrarPotions.registerPotions();
        proxy.preInit(event);
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        if(ConfigHandlerRituals.ritualCondor) RitualRegistry.registerRitual(new RitualCondor());
        if(ConfigHandlerRituals.ritualCrystalSplit) RitualRegistry.registerRitual(new RitualCrystalSplit());
        if(ConfigHandlerRituals.ritualEllipsoid) RitualRegistry.registerRitual(new RitualEllipsoid());
        if(ConfigHandlerRituals.ritualEternalSoul) RitualRegistry.registerRitual(new RitualEternalSoul());
        if(ConfigHandlerRituals.ritualFeatheredEarth) RitualRegistry.registerRitual(new RitualFeatheredEarth());
        if(ConfigHandlerRituals.ritualGrounding) RitualRegistry.registerRitual(new RitualGrounding());
        if(ConfigHandlerRituals.ritualVeilOfEvil) RitualRegistry.registerRitual(new RitualVeilOfEvil());
        if(ConfigHandlerRituals.ritualWardOfSacrosanctity) RitualRegistry.registerRitual(new RitualWardOfSacrosanctity());

        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        GuideApiRitualEntries.addEntries();
        LogHelper.debug("Finished postInit phase.");
    }
}
