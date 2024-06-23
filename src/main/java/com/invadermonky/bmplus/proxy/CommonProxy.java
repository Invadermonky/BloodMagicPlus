package com.invadermonky.bmplus.proxy;

import com.invadermonky.bmplus.integration.BMCropsHarvestHandler;
import com.invadermonky.bmplus.integration.mods.HarvestHandlerHarvestcraft;
import com.invadermonky.bmplus.integration.mods.HarvestHandlerMysticalAgriculture;
import com.invadermonky.bmplus.integration.mods.HarvestHandlerUniqueCrops;
import com.invadermonky.bmplus.util.LogHelper;
import com.invadermonky.bmplus.util.ModIds;
import gnu.trove.map.hash.THashMap;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        THashMap<ModIds, Class<? extends BMCropsHarvestHandler>> harvestIntegrations = new THashMap<>(ModIds.values().length);

        harvestIntegrations.put(ModIds.harvestcraft, HarvestHandlerHarvestcraft.class);
        harvestIntegrations.put(ModIds.mystical_agriculture, HarvestHandlerMysticalAgriculture.class);
        harvestIntegrations.put(ModIds.unique_crops, HarvestHandlerUniqueCrops.class);

        harvestIntegrations.forEach((mod, clazz) -> {
            try {
                if(mod.isLoaded) {
                    clazz.newInstance();
                }
            } catch (Exception e) {
                LogHelper.error("Failed to load " + mod.modId);
                e.printStackTrace();
            }
        });
    }

    public void postInit(FMLPostInitializationEvent event) {}
}
