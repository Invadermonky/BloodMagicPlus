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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualCondor extends Ritual {
    public static final String name = "condorRitual";
    public static final int level = 0;
    public static final int cost = 1000000;
    public static final String unlocName = "ritual." + Constants.Mod.MODID.toLowerCase() + "." + name;

    public static final String FLIGHT_RANGE = "flightRange";

    public RitualCondor() {
        super(name, level, cost, unlocName);
        addBlockRange(FLIGHT_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, 0, -10), new BlockPos(10, 30, 10)));
        setMaximumVolumeAndDistanceOfRange(FLIGHT_RANGE, 0, 100, 200);
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
                AxisAlignedBB aabb = this.modableRangeMap.get(FLIGHT_RANGE).getAABB(masterRitualStone.getBlockPos());
        World world = masterRitualStone.getWorldObj();

        int currentEssence = masterRitualStone.getOwnerNetwork().getCurrentEssence();

        List<EntityPlayer> entityPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
        int entityCount = entityPlayers.size();

        if (currentEssence < getRefreshCost() * entityCount) {
            masterRitualStone.getOwnerNetwork().causeNausea();
            return;
        } else {
            entityCount = 0;
            for (EntityPlayer player : entityPlayers) {
                                player.addPotionEffect(new PotionEffect(RegistrarPotions.FLIGHT, 20, 0));
            }
        }

                masterRitualStone.getOwnerNetwork().syphon(getRefreshCost() * entityCount);
    }

    @Override
    public int getRefreshTime() {
        return 10;
    }

    @Override
    public int getRefreshCost() {
        return 5;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<>();

        addParallelRunes(components, 1, 0, EnumRuneType.DUSK);
        addCornerRunes(components, 2, 0, EnumRuneType.AIR);
        addOffsetRunes(components, 1, 3, 0, EnumRuneType.EARTH);
        addParallelRunes(components, 3, 0, EnumRuneType.EARTH);
        addOffsetRunes(components, 3, 4, 0, EnumRuneType.WATER);
        addParallelRunes(components, 1, 1, EnumRuneType.FIRE);
        addParallelRunes(components, 2, 1, EnumRuneType.BLANK);
        addParallelRunes(components, 4, 1, EnumRuneType.BLANK);
        addParallelRunes(components, 5, 1, EnumRuneType.AIR);
        addParallelRunes(components, 5, 0, EnumRuneType.DUSK);

        for (int i = 2; i <= 4; i++) {
            addParallelRunes(components, i, 2, EnumRuneType.EARTH);
        }

        addOffsetRunes(components, 2, 1, 4, EnumRuneType.FIRE);
        addCornerRunes(components, 2, 4, EnumRuneType.AIR);
        addCornerRunes(components, 4, 2, EnumRuneType.FIRE);

        for (int i = -1; i <= 1; i++) {
            addOffsetRunes(components, 3, i, 4, EnumRuneType.EARTH);
        }
        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualCondor();
    }
}
