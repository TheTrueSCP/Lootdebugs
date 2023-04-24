package net.the_goldbeards.lootdebugs.Items.Tools.OmmoranHearthstoneLocator;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.internal.TextComponentMessageFormatHandler;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicAllClassItem;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.util.ModConfiguredStructureTags;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;
import org.jetbrains.annotations.Nullable;

public class OmmoranHearthstoneLocator extends BasicAllClassItem
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
                BlockPos locatePos = searchOmmoran(SL, pStack, pEntity.blockPosition());

                if (locatePos != null)
                {
                    UsefullStuff.ItemNBTHelper.put(pStack, "OmmoranPos", NbtUtils.writeBlockPos(locatePos));
                }

                UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.isLocatorLocked", true);
            }
            else
            {
                if(NbtUtils.readBlockPos(UsefullStuff.ItemNBTHelper.getTagCompound(pStack, "OmmoranPos")) == null)
                {
                    if(pEntity instanceof Player player)
                    {
                        player.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.locator.not_found"), true);
                    }
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);

        if(NbtUtils.readBlockPos(UsefullStuff.ItemNBTHelper.getTagCompound(pStack, "OmmoranPos")) == null)
        {
            UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.isLocatorLocked", false);
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Nullable
    private BlockPos searchOmmoran(ServerLevel serverLevel, ItemStack pStack, BlockPos searchPos) {
        BlockPos findBlockPos = serverLevel.findNearestMapFeature(ModConfiguredStructureTags.OMMORAN_HEARTHSTONE, searchPos, 100, false);


        if (findBlockPos != null)
        {

            if (!serverLevel.getBlockState(findBlockPos).is(ModBlocks.OMMORAN_HEARTHSTONE.get()))
            {
                for (int x = -50; x < 50; x++)
                {
                    for (int y = -50; y < 50; y++)
                    {
                        for (int z = -50; z < 50; z++)
                        {
                            BlockPos locatePos = new BlockPos(findBlockPos.getX() + (x), findBlockPos.getY() + (y), findBlockPos.getZ() + (z));

                            if (serverLevel.getBlockState(locatePos).getBlock() == ModBlocks.OMMORAN_HEARTHSTONE.get()) {
                                return locatePos;
                            }
                        }
                    }
                }
                return findBlockPos;
            }
            else//if the searchpos is already the ommoran
            {
                return findBlockPos;
            }
        }

        return null;
    }
}

