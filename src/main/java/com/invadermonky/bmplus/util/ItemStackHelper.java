package com.invadermonky.bmplus.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemStackHelper {
    public static final ItemStack EMPTY = null;

    @SuppressWarnings("all")
    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.getItem() == null || Block.getBlockFromItem(stack.getItem()) == Blocks.AIR;
    }
}
