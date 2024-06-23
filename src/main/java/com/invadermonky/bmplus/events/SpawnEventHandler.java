/*
    Code adapted from WayOfTime BloodMagic source. All code belongs to original owner.
    Source code found here:
    https://github.com/WayofTime/BloodMagic/tree/1.12

    Blood Magic 1.12 is Licensed under Creative Commons Attribution 4.0 International Public License
    https://github.com/WayofTime/BloodMagic/blob/1.12/LICENSE
    https://creativecommons.org/licenses/by/4.0/
*/

package com.invadermonky.bmplus.events;

import WayofTime.bloodmagic.api.ritual.AreaDescriptor;
import WayofTime.bloodmagic.api.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.tile.TileMasterRitualStone;
import com.invadermonky.bmplus.ritual.RitualVeilOfEvil;
import com.invadermonky.bmplus.ritual.RitualWardOfSacrosanctity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class SpawnEventHandler {
    public static Map<World, Map<IMasterRitualStone, AreaDescriptor>> preventSpawnMap = new HashMap<>();
    public static Map<World, Map<IMasterRitualStone, AreaDescriptor>> forceSpawnMap = new HashMap<>();

    @SubscribeEvent
    public static void onLivingSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        World world = event.getWorld();

        if (!(event.getEntityLiving() instanceof EntityMob)) {
            return;
        }

        /* WardOfSacrosanctity */

        if (preventSpawnMap.containsKey(world)) {
            Map<IMasterRitualStone, AreaDescriptor> pMap = preventSpawnMap.get(world);

            if (pMap != null) {
                for (Map.Entry<IMasterRitualStone, AreaDescriptor> entry : pMap.entrySet()) {
                    IMasterRitualStone masterRitualStone = entry.getKey();
                    AreaDescriptor blockRange = entry.getValue();

                    if (masterRitualStone instanceof TileMasterRitualStone) {
                        TileMasterRitualStone tile = (TileMasterRitualStone) masterRitualStone;
                        if (tile.isActive() && tile.getCurrentRitual() instanceof RitualWardOfSacrosanctity) {
                            if (blockRange.offset(masterRitualStone.getBlockPos()).isWithinArea(new BlockPos(event.getX(), event.getY(), event.getZ()))) {
                                switch (event.getResult()) {
                                    case ALLOW:
                                        event.setResult(Result.DEFAULT);
                                        break;
                                    case DEFAULT:
                                        event.setResult(Result.DENY);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            }
                        } else {
                            pMap.remove(masterRitualStone);
                        }
                    }
                }
            }
        }

        /* VeilOfEvil */

        if (forceSpawnMap.containsKey(world)) {
            Map<IMasterRitualStone, AreaDescriptor> fMap = forceSpawnMap.get(world);

            if (fMap != null) {
                for (Map.Entry<IMasterRitualStone, AreaDescriptor> entry : fMap.entrySet()) {
                    IMasterRitualStone masterRitualStone = entry.getKey();
                    AreaDescriptor blockRange = entry.getValue();

                    if (masterRitualStone instanceof TileMasterRitualStone) {
                        TileMasterRitualStone tile = (TileMasterRitualStone) masterRitualStone;
                        if (tile.isActive() && tile.getCurrentRitual() instanceof RitualVeilOfEvil) {
                            if (blockRange.offset(masterRitualStone.getBlockPos()).isWithinArea(new BlockPos(event.getX(), event.getY(), event.getZ()))) {
                                switch (event.getResult()) {
                                    case DEFAULT:
                                        event.setResult(Result.ALLOW);
                                        break;
                                    case DENY:
                                        event.setResult(Result.DEFAULT);
                                    default:
                                        break;
                                }
                                break;
                            }
                        } else {
                            fMap.remove(masterRitualStone);
                        }
                    }
                }
            }
        }
    }
}
