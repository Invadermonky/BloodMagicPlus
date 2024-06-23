/*
    Code adapted from WayOfTime BloodMagic source. All code belongs to original owner.
    Source code found here:
    https://github.com/WayofTime/BloodMagic/tree/1.12

    Blood Magic 1.12 is Licensed under Creative Commons Attribution 4.0 International Public License
    https://github.com/WayofTime/BloodMagic/blob/1.12/LICENSE
    https://creativecommons.org/licenses/by/4.0/
*/

package com.invadermonky.bmplus.events;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber
public class WorldEventHandler {
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        World world = event.getWorld();
        SpawnEventHandler.forceSpawnMap.computeIfAbsent(world, k -> new HashMap<>());
        SpawnEventHandler.preventSpawnMap.computeIfAbsent(world, k -> new HashMap<>());
        PotionEventHandler.flightListMap.computeIfAbsent(world, k -> new ArrayList<>());
        PotionEventHandler.noGravityListMap.computeIfAbsent(world, k -> new ArrayList<>());
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        World world = event.getWorld();
        SpawnEventHandler.forceSpawnMap.remove(world);
        SpawnEventHandler.preventSpawnMap.remove(world);
        PotionEventHandler.flightListMap.remove(world);
        PotionEventHandler.noGravityListMap.remove(world);
    }
}
