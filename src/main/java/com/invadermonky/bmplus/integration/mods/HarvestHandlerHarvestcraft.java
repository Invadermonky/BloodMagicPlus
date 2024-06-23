package com.invadermonky.bmplus.integration.mods;

import WayofTime.bloodmagic.api.BlockStack;
import WayofTime.bloodmagic.api.registry.HarvestRegistry;
import com.invadermonky.bmplus.integration.BMCropsHarvestHandler;
import com.pam.harvestcraft.Reference;
import com.pam.harvestcraft.blocks.CropRegistry;
import com.pam.harvestcraft.blocks.FruitRegistry;
import com.pam.harvestcraft.blocks.growables.BlockPamFruit;
import com.pam.harvestcraft.blocks.growables.BlockPamFruitLog;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class HarvestHandlerHarvestcraft extends BMCropsHarvestHandler {
    public HarvestHandlerHarvestcraft() {
        CropRegistry.getCrops().forEach((name, crop) -> HarvestRegistry.registerStandardCrop(crop, crop.getMatureAge()));
        FruitRegistry.fruits.forEach(fruit -> HarvestRegistry.registerStandardCrop(fruit, fruit.getMatureAge()));
        FruitRegistry.logs.forEach((name, log) -> HarvestRegistry.registerStandardCrop(log, log.getMatureAge()));
        //Apples are special children for some reason.
        Block appleBlock = Block.REGISTRY.getObject(new ResourceLocation(Reference.MODID, "pamapple"));
        if(appleBlock instanceof BlockPamFruit) {
            HarvestRegistry.registerStandardCrop(appleBlock, ((BlockPamFruit)appleBlock).getMatureAge());
        }
    }

    @Override
    public boolean harvestAndPlant(World world, BlockPos blockPos, BlockStack blockStack) {
        if(blockStack.getBlock() instanceof BlockPamFruit || blockStack.getBlock() instanceof BlockPamFruitLog) {
            if (HarvestRegistry.getStandardCrops().containsKey(blockStack.getBlock())) {
                int matureMeta = HarvestRegistry.getStandardCrops().get(blockStack.getBlock());
                if (blockStack.getMeta() >= matureMeta) {
                    List<ItemStack> drops = blockStack.getBlock().getDrops(world, blockPos, blockStack.getState(), 0);
                    world.setBlockState(blockPos, blockStack.getBlock().getDefaultState());
                    world.playEvent(2001, blockPos, Block.getStateId(blockStack.getState()));
                    for (ItemStack drop : drops) {
                        if (!world.isRemote) {
                            EntityItem toDrop = new EntityItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), drop);
                            world.spawnEntity(toDrop);
                        }
                    }
                    return true;
                }
            }
            return false;
        } else {
            return super.harvestAndPlant(world, blockPos, blockStack);
        }
    }
}
