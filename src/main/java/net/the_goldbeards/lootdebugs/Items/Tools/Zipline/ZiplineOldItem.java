package net.the_goldbeards.lootdebugs.Items.Tools.Zipline;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

@Deprecated(forRemoval = true, since = "2.1")
public class ZiplineOldItem extends Item
{
    public ZiplineOldItem(Properties pProperties)
    {
        super( pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(!UsefullStuff.ItemNBTHelper.hasKey(pStack, "lootdebugs.ziplineitem.setsecond"))
        {
            UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.ziplineitem.setsecond", false);
        }

        if(pEntity.isShiftKeyDown())
        {
            UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.ziplineitem.setsecond", false);
        }

        //ONLY for displaying linking/too far messages on screen
        if(UsefullStuff.ItemNBTHelper.getBoolean(pStack, "lootdebugs.ziplineitem.setsecond"))
        {
            if(pEntity instanceof Player player &&
                    UsefullStuff.ItemNBTHelper.hasKey(pStack, "lootdebugs.ziplineitem.linkpos.x") &&
                    UsefullStuff.ItemNBTHelper.hasKey(pStack, "lootdebugs.ziplineitem.linkpos.y") &&
                    UsefullStuff.ItemNBTHelper.hasKey(pStack, "lootdebugs.ziplineitem.linkpos.z"))
            {
                String pos =
                            UsefullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.ziplineitem.linkpos.x") + " "
                        +  UsefullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.ziplineitem.linkpos.y") + " "
                        + UsefullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.ziplineitem.linkpos.z");

                BlockPos linkedBlockPos = new BlockPos(UsefullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.ziplineitem.linkpos.x"), UsefullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.ziplineitem.linkpos.y"), UsefullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.ziplineitem.linkpos.z"));//create blockpos


                if(player.blockPosition().closerThan(linkedBlockPos, 31D))
                {
                    player.displayClientMessage(new TextComponent(new TranslatableComponent("tool.zipline.linking").getString() + " " + pos), true);
                }
                else
                {
                    player.displayClientMessage(new TextComponent(ChatFormatting.RED  + "" + linkedBlockPos.getX() + " " + linkedBlockPos.getY() + " " + linkedBlockPos.getZ() + " " + new TranslatableComponent("tool.zipline.too_far").getString()), true);
                }
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack ziplineItemStack = pPlayer.getItemInHand(pUsedHand);

        if(!UsefullStuff.ItemNBTHelper.hasKey(ziplineItemStack, "lootdebugs.ziplineitem.setsecond"))
        {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

        }

        if(!UsefullStuff.ItemNBTHelper.getBoolean(ziplineItemStack, "lootdebugs.ziplineitem.setsecond"))
        {
          //  ZiplineEntity ziplineEntityFirst = new ZiplineEntity(pPlayer, pLevel, null, false, ziplineItemStack);
          //  ziplineEntityFirst.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 3.0F, 0.0F);
         //   pLevel.addFreshEntity(ziplineEntityFirst);
            UsefullStuff.ItemNBTHelper.putBoolean(ziplineItemStack, "lootdebugs.ziplineitem.setsecond", true);
        }
        else
        {
            if(UsefullStuff.ItemNBTHelper.hasKey(ziplineItemStack, "lootdebugs.ziplineitem.linkpos.x") &&
                    UsefullStuff.ItemNBTHelper.hasKey(ziplineItemStack, "lootdebugs.ziplineitem.linkpos.y") &&//If blockpos exists
                    UsefullStuff.ItemNBTHelper.hasKey(ziplineItemStack, "lootdebugs.ziplineitem.linkpos.z"))
            {
                BlockPos linkedBlockPos = new BlockPos(UsefullStuff.ItemNBTHelper.getFloat(ziplineItemStack, "lootdebugs.ziplineitem.linkpos.x"), UsefullStuff.ItemNBTHelper.getFloat(ziplineItemStack, "lootdebugs.ziplineitem.linkpos.y"), UsefullStuff.ItemNBTHelper.getFloat(ziplineItemStack, "lootdebugs.ziplineitem.linkpos.z"));//create blockpos

                if(pPlayer.blockPosition().closerThan(linkedBlockPos, 31D)) {
                //    ZiplineEntity ziplineEntitySecond = new ZiplineEntity(pPlayer, pLevel, linkedBlockPos, true, ziplineItemStack);//enable linking with secondPlace and give linking coords.
                //    ziplineEntitySecond.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 3.0F, 0.0F);
                //    pLevel.addFreshEntity(ziplineEntitySecond);
                    UsefullStuff.ItemNBTHelper.putBoolean(ziplineItemStack, "lootdebugs.ziplineitem.setsecond", false);
                }
            }
        }




        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
