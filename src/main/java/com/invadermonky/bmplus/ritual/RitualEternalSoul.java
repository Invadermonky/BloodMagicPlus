/*
    Code adapted from WayOfTime BloodMagic source. All code belongs to original owner.
    Source code found here:
    https://github.com/WayofTime/BloodMagic/tree/1.12

    Blood Magic 1.12 is Licensed under Creative Commons Attribution 4.0 International Public License
    https://github.com/WayofTime/BloodMagic/blob/1.12/LICENSE
    https://creativecommons.org/licenses/by/4.0/
*/

package com.invadermonky.bmplus.ritual;

import WayofTime.bloodmagic.altar.BloodAltar;
import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.ritual.*;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.tile.TileAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RitualEternalSoul extends Ritual {
    public static final String name = "eternalSoulRitual";
    public static final int level = 2;
    public static final int cost = 2000000;
    public static final String unlocName = "ritual." + Constants.Mod.MODID.toLowerCase() + "." + name;

    public static final String ALTAR_RANGE = "altar";

    private BlockPos altarOffsetPos = new BlockPos(0, 0, 0);

    public RitualEternalSoul() {
        super(name, level, cost, unlocName);
        addBlockRange(ALTAR_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-5, -10, -5), 11, 21, 11));

        setMaximumVolumeAndDistanceOfRange(ALTAR_RANGE, 0, 10, 15);
    }


    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
                UUID owner = UUID.fromString(masterRitualStone.getOwner());
        int currentEssence = NetworkHelper.getSoulNetwork(owner).getCurrentEssence();
        World world = masterRitualStone.getWorldObj();
        BlockPos pos = masterRitualStone.getBlockPos();
        BlockPos altarPos = pos.add(altarOffsetPos);

        TileEntity tile = world.getTileEntity(altarPos);
                AreaDescriptor altarRange = this.modableRangeMap.get(ALTAR_RANGE);

        if (!altarRange.isWithinArea(altarOffsetPos) || !(tile instanceof TileAltar)) {
            for (BlockPos newPos : altarRange.getContainedPositions(pos)) {
                TileEntity nextTile = world.getTileEntity(newPos);
                if (nextTile instanceof TileAltar) {
                    tile = nextTile;
                    altarOffsetPos = newPos.subtract(pos);

                    altarRange.resetCache();
                    break;
                }
            }
        }

        if (!(tile instanceof TileAltar)) {
            return;
        }

        BloodAltar altar = (BloodAltar) tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

        int horizontalRange = 15;
        int verticalRange = 20;

        List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class,
                new AxisAlignedBB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f,
                        pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f)
                        .expand(horizontalRange, verticalRange, horizontalRange).expand(0, -verticalRange, 0));

        EntityPlayer entityOwner = PlayerHelper.getPlayerFromUUID(owner);

        int fillAmount = Math.min(currentEssence / 2, altar.fill(new FluidStack(BlockLifeEssence.getLifeEssence(), 10000), false));

        altar.fill(new FluidStack(BlockLifeEssence.getLifeEssence(), fillAmount), true);

        if (entityOwner != null && list.contains(entityOwner) && entityOwner.getHealth() > 2.0f && fillAmount != 0)
            entityOwner.setHealth(2.0f);

        masterRitualStone.getOwnerNetwork().syphon(fillAmount * 2);
    }

    @Override
    public int getRefreshCost() {
        return 0;
    }

    @Override
    public int getRefreshTime() {
        return 1;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<>();

        addCornerRunes(components, 1, 0, EnumRuneType.FIRE);

        for (int i = 0; i < 4; i++) {
            addCornerRunes(components, 2, i, EnumRuneType.AIR);
        }

        addCornerRunes(components, 4, 1, EnumRuneType.EARTH);

        addOffsetRunes(components, 3, 4, 1, EnumRuneType.EARTH);


        for (int i = 0; i < 2; i++) {
            addCornerRunes(components, 4, i + 2, EnumRuneType.WATER);
        }

        addCornerRunes(components, 4, 4, EnumRuneType.DUSK);

        addOffsetRunes(components, 6, 5, 0, EnumRuneType.FIRE);


        for (int i = 0; i < 2; i++) {
            addCornerRunes(components, 6, i, EnumRuneType.FIRE);
        }

        for (int i = 0; i < 3; i++) {
            addCornerRunes(components, 6, i + 2, EnumRuneType.BLANK);
        }

        addCornerRunes(components, 6, 5, EnumRuneType.DUSK);

        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualEternalSoul();
    }
}
