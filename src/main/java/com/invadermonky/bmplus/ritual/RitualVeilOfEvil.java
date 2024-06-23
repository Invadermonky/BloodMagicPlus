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

public class RitualVeilOfEvil extends Ritual {
    public static final String name = "veilOfEvilRitual";
    public static final int level = 0;
    public static final int cost = 40000;
    public static final String unlocName = "ritual." + Constants.Mod.MODID.toLowerCase() + "." + name;

    public static final String VEIL_RANGE = "veilRange";

    public RitualVeilOfEvil() {
        super(name, level, cost, unlocName);
        addBlockRange(VEIL_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-16, 0, -16), 33));
        setMaximumVolumeAndDistanceOfRange(VEIL_RANGE, 0, 256, 256);
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

        if (SpawnEventHandler.forceSpawnMap.containsKey(world)) {
            Map<IMasterRitualStone, AreaDescriptor> forceSpawnMap = SpawnEventHandler.forceSpawnMap.get(world);
            if (forceSpawnMap != null) {
                                forceSpawnMap.put(masterRitualStone, this.modableRangeMap.get(VEIL_RANGE));
            } else {
                forceSpawnMap = new HashMap<>();
                                forceSpawnMap.put(masterRitualStone, this.modableRangeMap.get(VEIL_RANGE));
                                SpawnEventHandler.forceSpawnMap.put(world, forceSpawnMap);
            }
        } else {
            HashMap<IMasterRitualStone, AreaDescriptor> forceSpawnMap = new HashMap<>();
                        forceSpawnMap.put(masterRitualStone, this.modableRangeMap.get(VEIL_RANGE));
                        SpawnEventHandler.forceSpawnMap.put(world, forceSpawnMap);
        }

                masterRitualStone.getOwnerNetwork().syphon(getRefreshCost());
    }

    @Override
    public int getRefreshCost() {
        return 0;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<>();

        addOffsetRunes(components, 1, 0, 2, EnumRuneType.DUSK);
        addCornerRunes(components, 3, 0, EnumRuneType.FIRE);

        for (int i = 0; i <= 1; i++) {
            addParallelRunes(components, (4 + i), i, EnumRuneType.DUSK);
            addOffsetRunes(components, (4 + i), i, -1, EnumRuneType.BLANK);
            addOffsetRunes(components, 4, 5, i, EnumRuneType.EARTH);
        }

        addCornerRunes(components, 5, 1, EnumRuneType.BLANK);

        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualVeilOfEvil();
    }
}
