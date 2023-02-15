package net.the_goldbeards.lootdebugs.Items.Tools.SatchelCharge;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge.SatchelChargeTile;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicAllClassItem;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

public class SatchelChargeDetonatorItem extends BasicAllClassItem
{
    public SatchelChargeDetonatorItem(Properties pProperties) {
        super(pProperties);
    }

    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Driller;

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        BlockPos satchelPos = NbtUtils.readBlockPos(UsefullStuff.ItemNBTHelper.getTagCompound(pStack, "satchel_charge"));


        if (pEntity instanceof Player player)
        {
            if (!(pLevel.getBlockEntity(satchelPos) instanceof SatchelChargeTile))
            {
                player.getInventory().removeItem(pStack);
            }

            if(pIsSelected)
            {
                if (canUseItem(pStack))
                {
                    if (pLevel.getBlockEntity(satchelPos) instanceof SatchelChargeTile)
                    {
                        player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + new TranslatableComponent("message.lootdebugs.tool.satchel_charge.ready").getString()), true);
                    }
                }
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);

        if (UsefullStuff.DwarfClasses.canPlayerUseItem(pStack, pPlayer, dwarfClassToUse)) {

            BlockPos satchelPos = NbtUtils.readBlockPos(UsefullStuff.ItemNBTHelper.getTagCompound(pStack, "satchel_charge"));

            if (pLevel.getBlockEntity(satchelPos) instanceof SatchelChargeTile satchelChargeTile) {
                satchelChargeTile.detonate(pPlayer);
                pPlayer.displayClientMessage(new TextComponent(ChatFormatting.DARK_RED + new TranslatableComponent("message.lootdebugs.tool.satchel_charge.detonated").getString()), true);

            }

            pPlayer.getInventory().removeItem(pStack);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}
