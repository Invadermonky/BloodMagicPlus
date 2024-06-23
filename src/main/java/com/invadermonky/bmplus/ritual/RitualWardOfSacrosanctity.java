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
import WayofTime.bloodmagic.api.soul.DemonWillHolder;
import WayofTime.bloodmagic.api.soul.EnumDemonWillType;
import WayofTime.bloodmagic.demonAura.WorldDemonWillHandler;
import com.invadermonky.bmplus.events.SpawnEventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RitualWardOfSacrosanctity extends Ritual {
    public static final String name = "wardOfSacrosanctityRitual";
    public static final int level = 0;
    public static final int cost = 40000;
    public static final String unlocName = "ritual." + Constants.Mod.MODID.toLowerCase() + "." + name;

    public static final String SPAWN_WARD = "spawnward";

    public RitualWardOfSacrosanctity() {
        super(name, level, cost, unlocName);
        addBlockRange(SPAWN_WARD, new AreaDescriptor.Rectangle(new BlockPos(-16, -10, -16), 33));
        setMaximumVolumeAndDistanceOfRange(SPAWN_WARD, 0, 256, 256);
    }


    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
        /* Default Ritual Stuff */
        World world = masterRitualStone.getWorldObj();
        int currentEssence = masterRitualStone.getOwnerNetwork().getCurrentEssence();
        BlockPos pos = masterRitualStone.getBlockPos();

        if (currentEssence < getRefreshCost()) {
            masterRitualStone.getOwnerNetwork().causeNausea();
            return;
        }

        // int maxEffects = currentEssence / getRefreshCost();
        // int totalEffects = 0;

        /* Default will augment stuff */
        List<EnumDemonWillType> willConfig = masterRitualStone.getActiveWillConfig();
        DemonWillHolder holder = WorldDemonWillHandler.getWillHolder(world, pos);

        double rawWill = this.getWillRespectingConfig(world, pos, EnumDemonWillType.DEFAULT, willConfig);
        double corrosiveWill = this.getWillRespectingConfig(world, pos, EnumDemonWillType.CORROSIVE, willConfig);
        double destructiveWill = this.getWillRespectingConfig(world, pos, EnumDemonWillType.DESTRUCTIVE, willConfig);
        double steadfastWill = this.getWillRespectingConfig(world, pos, EnumDemonWillType.STEADFAST, willConfig);
        double vengefulWill = this.getWillRespectingConfig(world, pos, EnumDemonWillType.VENGEFUL, willConfig);

        double rawDrained = 0;
        double corrosiveDrained = 0;
        double destructiveDrained = 0;
        double steadfastDrained = 0;
        double vengefulDrained = 0;

        /* Actual ritual stuff begins here */

                if (SpawnEventHandler.preventSpawnMap.containsKey(world)) {
                        Map<IMasterRitualStone, AreaDescriptor> preventSpawnMap = SpawnEventHandler.preventSpawnMap.get(world);
            if (preventSpawnMap != null) {
                                preventSpawnMap.put(masterRitualStone, this.modableRangeMap.get(SPAWN_WARD));
            } else {
                preventSpawnMap = new HashMap<>();
                                preventSpawnMap.put(masterRitualStone, this.modableRangeMap.get(SPAWN_WARD));
                                SpawnEventHandler.preventSpawnMap.put(world, preventSpawnMap);
            }
        } else {
            HashMap<IMasterRitualStone, AreaDescriptor> preventSpawnMap = new HashMap<>();
                        preventSpawnMap.put(masterRitualStone, this.modableRangeMap.get(SPAWN_WARD));
                        SpawnEventHandler.preventSpawnMap.put(world, preventSpawnMap);
        }

                masterRitualStone.getOwnerNetwork().syphon(getRefreshCost());
    }

    @Override
    public int getRefreshTime() {
        return 25;
    }

    @Override
    public int getRefreshCost() {
        return 2;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<>();

        for (int i = 2; i < 5; i++) {
            if (i < 4) {
                addParallelRunes(components, 1, 0, EnumRuneType.AIR);
            }
            addCornerRunes(components, i, 0, EnumRuneType.FIRE);
        }
        addParallelRunes(components, 5, 0, EnumRuneType.DUSK);
        addOffsetRunes(components, 5, 6, 0, EnumRuneType.WATER);

        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualWardOfSacrosanctity();
    }
}
