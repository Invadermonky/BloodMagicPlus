package com.invadermonky.bmplus.integration.mods;

import WayofTime.bloodmagic.api.registry.HarvestRegistry;
import com.blakebr0.mysticalagriculture.blocks.ModBlocks;
import com.blakebr0.mysticalagriculture.lib.CropType;
import com.invadermonky.bmplus.integration.BMCropsHarvestHandler;

public class HarvestHandlerMysticalAgriculture extends BMCropsHarvestHandler {
    public HarvestHandlerMysticalAgriculture() {

        HarvestRegistry.registerStandardCrop(ModBlocks.blockTier1InferiumCrop, ModBlocks.blockTier1InferiumCrop.getMaxAge());
        HarvestRegistry.registerStandardCrop(ModBlocks.blockTier2InferiumCrop, ModBlocks.blockTier2InferiumCrop.getMaxAge());
        HarvestRegistry.registerStandardCrop(ModBlocks.blockTier3InferiumCrop, ModBlocks.blockTier3InferiumCrop.getMaxAge());
        HarvestRegistry.registerStandardCrop(ModBlocks.blockTier4InferiumCrop, ModBlocks.blockTier4InferiumCrop.getMaxAge());
        HarvestRegistry.registerStandardCrop(ModBlocks.blockTier5InferiumCrop, ModBlocks.blockTier5InferiumCrop.getMaxAge());

        for(CropType.Type crop : CropType.Type.values()) {
            if(crop.isEnabled()) {
                HarvestRegistry.registerStandardCrop(crop.getPlant(), crop.getPlant().getMaxAge());
            }
        }
    }
}
