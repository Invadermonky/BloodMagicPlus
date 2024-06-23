package com.invadermonky.bmplus.potions;

import WayofTime.bloodmagic.potion.PotionBloodMagic;
import com.invadermonky.bmplus.BMPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

public class PotionBloodMagicExtra extends PotionBloodMagic {
    public static ResourceLocation texture = new ResourceLocation(BMPlus.MOD_ID, "textures/misc/potions.png");

    public PotionBloodMagicExtra(String name, boolean badEffect, int potionColor, int iconIndexX, int iconIndexY) {
        super(name, texture, badEffect, potionColor, iconIndexX, iconIndexY);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        try {
            Field fieldIconIndex = Potion.class.getDeclaredField("statusIconIndex");
            fieldIconIndex.setAccessible(true);
            return (int) fieldIconIndex.get(this);
        } catch (Exception ignored) {
            return super.getStatusIconIndex();
        }
    }
}
