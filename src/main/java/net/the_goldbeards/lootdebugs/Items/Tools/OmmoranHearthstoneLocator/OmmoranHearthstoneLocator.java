package net.the_goldbeards.lootdebugs.Items.Tools.OmmoranHearthstoneLocator;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.util.ModConfiguredStructureTags;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

public class OmmoranHearthstoneLocator extends Item
{

    public OmmoranHearthstoneLocator(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected)
    {
        if (pLevel instanceof ServerLevel SL)
        {
            if(!UsefullStuff.ItemNBTHelper.getBoolean(pStack, "lootdebugs.isLocatorLocked"))
            {
                BlockPos findBlockPos = SL.findNearestMapFeature(ModConfiguredStructureTags.OMMORAN_HEARTHSTONE, pEntity.blockPosition(), 100, false);
                if (findBlockPos != null) {

                    if (!pLevel.getBlockState(findBlockPos).is(ModBlocks.OMMORAN_HEARTHSTONE.get()))
                    {
                        for (int x = -30; x < 30; x++) {
                            for (int y = -30; y < 30; y++) {
                                for (int z = -30; z < 30; z++) {
                                    BlockPos locatePos = new BlockPos(findBlockPos.getX() + (x), findBlockPos.getY() + (y), findBlockPos.getZ() + (z));
                                    if (pLevel.getBlockState(locatePos).is(ModBlocks.OMMORAN_HEARTHSTONE.get()))
                                    {
                                        UsefullStuff.ItemNBTHelper.put(pStack, "OmmoranPos", NbtUtils.writeBlockPos(locatePos));
                                        UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.isLocatorLocked", true);
                                        break;

                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        UsefullStuff.ItemNBTHelper.put(pStack, "OmmoranPos", NbtUtils.writeBlockPos(findBlockPos));
                        UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.isLocatorLocked", true);
                    }
                }
            }



            super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
        }
    }
}

