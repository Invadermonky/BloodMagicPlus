/*
    Code adapted from WayOfTime BloodMagic source. All code belongs to original owner.
    Source code found here:
    https://github.com/WayofTime/BloodMagic/tree/1.12

    Blood Magic 1.12 is Licensed under Creative Commons Attribution 4.0 International Public License
    https://github.com/WayofTime/BloodMagic/blob/1.12/LICENSE
    https://creativecommons.org/licenses/by/4.0/
*/

package com.invadermonky.bmplus.events;

import WayofTime.bloodmagic.registry.ModPotions;
import com.google.common.collect.Lists;
import com.invadermonky.bmplus.potions.RegistrarPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class PotionEventHandler {
    public static Map<World, List<EntityPlayer>> flightListMap = new HashMap<>();
    public static Map<World, List<EntityLivingBase>> noGravityListMap = new HashMap<>();

    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase eventEntityLiving = event.getEntityLiving();

        if (eventEntityLiving.isPotionActive(ModPotions.boost)) {
            int i = eventEntityLiving.getActivePotionEffect(ModPotions.boost).getAmplifier();
            eventEntityLiving.motionY += (0.1f) * (2 + i);
        }

        if (eventEntityLiving.isPotionActive(RegistrarPotions.GROUNDED))
            eventEntityLiving.motionY = 0;
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        EntityLivingBase eventEntityLiving = event.getEntityLiving();

        if(event.getEntityLiving().isPotionActive(RegistrarPotions.FEATHERED))
            event.setDamageMultiplier(0);

        if (eventEntityLiving.isPotionActive(RegistrarPotions.HEAVY_HEART)) {
            int i = eventEntityLiving.getActivePotionEffect(RegistrarPotions.HEAVY_HEART).getAmplifier() + 1;
            event.setDamageMultiplier(event.getDamageMultiplier() + i);
            event.setDistance(event.getDistance() + i);
        }
    }

    @SubscribeEvent
    public static void onEntityHurtEvent(LivingHurtEvent event) {
        if (event.getSource() == DamageSource.fall)
            if (event.getEntityLiving().isPotionActive(RegistrarPotions.FEATHERED))
                event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase eventEntityLiving = event.getEntityLiving();
        World world = eventEntityLiving.world;

        if(world.isRemote)
            return;

        List<EntityPlayer> flightList = flightListMap.getOrDefault(world, Lists.newArrayList());
        if (eventEntityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) eventEntityLiving;
            if (!player.world.isRemote) {
                if (player.isPotionActive(RegistrarPotions.FLIGHT)) {
                    if (!player.isSpectator() && !player.capabilities.allowFlying) {
                        player.capabilities.allowFlying = true;
                        player.sendPlayerAbilities();
                        flightList.add(player);
                        if(!flightListMap.containsKey(world)) {
                            flightListMap.put(world, flightList);
                        }
                    }
                } else {
                    if (flightList.contains(player)) {
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        player.sendPlayerAbilities();
                        flightList.remove(player);
                    }
                }
            }
        }

        if (event.getEntityLiving().isPotionActive(ModPotions.boost)) {
            int amplifier = event.getEntityLiving().getActivePotionEffect(ModPotions.boost).getAmplifier();
            float percentIncrease = (amplifier + 1) * 0.5F;

            boolean isPlayerAndFlying = eventEntityLiving instanceof EntityPlayer && ((EntityPlayer) eventEntityLiving).capabilities.isFlying;
            if (percentIncrease != 0 && (eventEntityLiving.onGround || isPlayerAndFlying) &&
                    (eventEntityLiving.moveForward != 0 || eventEntityLiving.moveStrafing != 0 || eventEntityLiving.motionY != 0)) {


                eventEntityLiving.move(eventEntityLiving.moveStrafing * percentIncrease,
                        isPlayerAndFlying ? eventEntityLiving.motionY * percentIncrease : 0,
                        eventEntityLiving.moveForward * percentIncrease);

                if (isPlayerAndFlying && eventEntityLiving.motionY != 0)
                    eventEntityLiving.motionY *= (1 + Math.min(percentIncrease, 0.75F)); // if this goes too high, it escalates
            }
        }

        List<EntityLivingBase> noGravityList = noGravityListMap.getOrDefault(world, Lists.newArrayList());
        if (eventEntityLiving.isPotionActive(RegistrarPotions.SUSPENDED)) {
            if(!eventEntityLiving.hasNoGravity()) {
                eventEntityLiving.setNoGravity(true);
                noGravityList.add(eventEntityLiving);
                if (!noGravityListMap.containsKey(world)) {
                    noGravityListMap.put(world, noGravityList);
                }
            }
        } else {
            if (noGravityList.contains(eventEntityLiving)) {
                eventEntityLiving.setNoGravity(false);
                noGravityList.remove(eventEntityLiving);
            }
        }

        if (eventEntityLiving.isPotionActive(RegistrarPotions.GROUNDED)) {
            PotionEffect activeEffect = eventEntityLiving.getActivePotionEffect(RegistrarPotions.GROUNDED);
            if (activeEffect != null) {
                if (eventEntityLiving instanceof EntityPlayer && ((EntityPlayer) eventEntityLiving).capabilities.isFlying)
                    eventEntityLiving.motionY -= (0.05D * (double) activeEffect.getAmplifier() + 1 - eventEntityLiving.motionY) * 0.2D;
                else
                    eventEntityLiving.motionY -= (0.1D * (double) activeEffect.getAmplifier() + 1 - eventEntityLiving.motionY) * 0.2D;
            }
        }
    }
}
