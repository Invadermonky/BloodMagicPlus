package com.invadermonky.bmplus.integration.mods;

import WayofTime.bloodmagic.api.registry.HarvestRegistry;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.invadermonky.bmplus.integration.BMCropsHarvestHandler;

public class HarvestHandlerUniqueCrops extends BMCropsHarvestHandler {
    public HarvestHandlerUniqueCrops() {
        HarvestRegistry.registerStandardCrop(UCBlocks.cropNormal, UCBlocks.cropNormal.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropPrecision, UCBlocks.cropPrecision.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropKnowledge, UCBlocks.cropKnowledge.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropDirigible, UCBlocks.cropDirigible.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropMillennium, UCBlocks.cropMillennium.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropEnderlily, UCBlocks.cropEnderlily.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropCollis, UCBlocks.cropCollis.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropWeepingbells, UCBlocks.cropWeepingbells.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropInvisibilia, UCBlocks.cropInvisibilia.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropMaryjane, UCBlocks.cropMaryjane.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropMusica, UCBlocks.cropMusica.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropCinderbella, UCBlocks.cropCinderbella.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropMerlinia, UCBlocks.cropMerlinia.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropFeroxia, UCBlocks.cropFeroxia.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropEula, UCBlocks.cropEula.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropCobblonia, UCBlocks.cropCobblonia.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropDyeius, UCBlocks.cropDyeius.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropAbstract, UCBlocks.cropAbstract.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropWafflonia, UCBlocks.cropWafflonia.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropDevilsnare, UCBlocks.cropDevilsnare.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropPixelsius, UCBlocks.cropPixelsius.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropArtisia, UCBlocks.cropArtisia.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropPetramia, UCBlocks.cropPetramia.getMaxAge());
        HarvestRegistry.registerStandardCrop(UCBlocks.cropMalleatoris, UCBlocks.cropMalleatoris.getMaxAge());
    }
}
