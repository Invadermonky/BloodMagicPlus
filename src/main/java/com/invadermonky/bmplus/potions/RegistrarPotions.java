package com.invadermonky.bmplus.potions;

import com.invadermonky.bmplus.BMPlus;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistrarPotions {
    public static Potion FLIGHT = MobEffects.HASTE;
    public static Potion GROUNDED = MobEffects.HASTE;
    public static Potion HEAVY_HEART = MobEffects.HASTE;
    public static Potion SUSPENDED = MobEffects.HASTE;
    public static Potion FEATHERED = MobEffects.HASTE;

    public static void registerPotions() {
        FLIGHT = registerPotion("Flight", new ResourceLocation(BMPlus.MOD_ID, "flight"), false,  0x000000, 4, 0);
        GROUNDED = registerPotion("Grounded", new ResourceLocation(BMPlus.MOD_ID, "grounded"), true,  0x000000, 1, 0);
        HEAVY_HEART = registerPotion("Heavy Heart", new ResourceLocation(BMPlus.MOD_ID, "heavy_heart"), true,  0x000000, 1, 0);
        SUSPENDED = registerPotion("Suspended", new ResourceLocation(BMPlus.MOD_ID, "suspended"), false,  0x000000, 1, 0);
        FEATHERED = registerPotion("Feathered", new ResourceLocation(BMPlus.MOD_ID, "feathered"), false,  0x000000, 0, 0);
    }

    protected static Potion registerPotion(String name, ResourceLocation loc, boolean badEffect, int colour, int texX, int texY) {
        Potion potion = new PotionBloodMagicExtra(name, badEffect, colour, texX, texY);
        GameRegistry.register(potion.setRegistryName(loc.getResourcePath()));
        return potion;
    }
}
