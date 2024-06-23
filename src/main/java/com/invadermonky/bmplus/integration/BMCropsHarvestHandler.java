package com.invadermonky.bmplus.integration;

import WayofTime.bloodmagic.api.BlockStack;
import WayofTime.bloodmagic.api.iface.IHarvestHandler;
import WayofTime.bloodmagic.api.registry.HarvestRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class BMCropsHarvestHandler implements IHarvestHandler {
    @Override
    public boolean harvestAndPlant(World world, BlockPos blockPos, BlockStack blockStack) {
        if(HarvestRegistry.getStandardCrops().containsKey(blockStack.getBlock())) {
            if (blockStack.getMeta() >= HarvestRegistry.getStandardCrops().get(blockStack.getBlock())) {
                List<ItemStack> drops = blockStack.getBlock().getDrops(world, blockPos, blockStack.getState(), 0);
                boolean foundSeed = false;

                for (ItemStack drop : drops) {
                    if(drop != null && drop.getItem() instanceof IPlantable) {
                        if(drop.stackSize > 1) {
                            --drop.stackSize;
                        } else {
                            drops.remove(drop);
                        }
                        foundSeed = true;
                        break;
                    }
                }

                if(foundSeed) {
                    world.setBlockState(blockPos, blockStack.getBlock().getDefaultState());
                    world.playEvent(2001, blockPos, Block.getStateId(blockStack.getState()));

                    for (ItemStack drop : drops) {
                        if(!world.isRemote) {
                            EntityItem toDrop = new EntityItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), drop);
                            world.spawnEntity(toDrop);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
