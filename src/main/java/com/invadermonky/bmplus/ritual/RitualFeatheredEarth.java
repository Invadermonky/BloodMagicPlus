/*
    Code adapted from WayOfTime BloodMagic source. All code belongs to original owner.
    Source code found here:
    https://github.com/WayofTime/BloodMagic/tree/1.12

    Blood Magic 1.12 is Licensed under Creative Commons Attribution 4.0 International Public License
    https://github.com/WayofTime/BloodMagic/blob/1.12/LICENSE
    https://creativecommons.org/licenses/by/4.0/
*/

package com.invadermonky.bmplus.ritual;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.ritual.*;
import com.invadermonky.bmplus.potions.RegistrarPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualFeatheredEarth extends Ritual {
    public static final String name = "featheredEarthRitual";
    public static final int level = 0;
    public static final int cost = 5000;
    public static final String unlocName = "ritual." + Constants.Mod.MODID.toLowerCase() + "." + name;

    public static final String FALL_PROTECTION_RANGE = "fallProtRange";

    public RitualFeatheredEarth() {
        super(name, level, cost, unlocName);
        addBlockRange(FALL_PROTECTION_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-25, 0, -25), new BlockPos(25, 30, 25)));
        setMaximumVolumeAndDistanceOfRange(FALL_PROTECTION_RANGE, 0, 200, 200);
    }


    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
        World world = masterRitualStone.getWorldObj();

        int currentEssence = masterRitualStone.getOwnerNetwork().getCurrentEssence();
        BlockPos pos = masterRitualStone.getBlockPos();
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        if (currentEssence < getRefreshCost()) {
            masterRitualStone.getOwnerNetwork().causeNausea();
            return;
        }

        int maxEffects = currentEssence / getRefreshCost();
        int totalEffects = 0;

        if (masterRitualStone.getCooldown() > 0) {
            world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y + 5, z + 4, false));
            world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y + 5, z - 4, false));
            world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y + 5, z - 4, false));
            world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y + 5, z + 4, false));
            masterRitualStone.setCooldown(0);
        }

                AreaDescriptor fallProtRange = this.modableRangeMap.get(FALL_PROTECTION_RANGE);
        AxisAlignedBB fallProtBB = fallProtRange.getAABB(masterRitualStone.getBlockPos());
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, fallProtBB);

        for (EntityLivingBase entity : entities) {
            if (totalEffects >= maxEffects) {
                break;
            }
                        entity.addPotionEffect(new PotionEffect(RegistrarPotions.FEATHERED, 20, 0));
            totalEffects++;
        }

                masterRitualStone.getOwnerNetwork().syphon(getRefreshCost() * totalEffects);
    }

    @Override
    public int getRefreshCost() {
        return 5;
    }

    @Override
    public int getRefreshTime() {
        return 10;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<>();

        addParallelRunes(components, 1, 0, EnumRuneType.DUSK);
        addCornerRunes(components, 2, 0, EnumRuneType.AIR);
        addOffsetRunes(components, 1, 3, 0, EnumRuneType.EARTH);
        addParallelRunes(components, 3, 0, EnumRuneType.EARTH);
        addCornerRunes(components, 4, 4, EnumRuneType.FIRE);
        addOffsetRunes(components, 4, 5, 5, EnumRuneType.AIR);
        addOffsetRunes(components, 3, 4, 5, EnumRuneType.AIR);

        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualFeatheredEarth();
    }
}
