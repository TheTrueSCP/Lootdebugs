package net.the_goldbeards.lootdebugs.Items.Tools.ResupplyCaller;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Entities.Tools.ResupplyDropEntity;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicAllClassItem;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.util.function.Predicate;

public class ResupplyCallerItem extends BasicAllClassItem
{
    private static int resupplyDropHeight = 320;
    private static int resupplyCallAmount = 64;

    public static final Predicate<ItemStack> NITRA = (p_43017_) -> p_43017_.is(ModItems.NITRA.get());

    public ResupplyCallerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack pUsedStack = pPlayer.getItemInHand(pUsedHand);

        if(canCallResupply(pPlayer, pUsedStack, NITRA) || pPlayer.isCreative())
        {
            if(!pPlayer.isCreative())
            {
                removeNitra(pPlayer, NITRA);
            }


            BlockPos playerPos = pPlayer.blockPosition();
            ResupplyDropEntity resupplyDropEntity = new ResupplyDropEntity(pLevel, playerPos.getY(), pPlayer, new BlockPos(playerPos.getX() + 0.5f, resupplyDropHeight, playerPos.getZ() + 0.5f));
            pLevel.addFreshEntity(resupplyDropEntity);

            return InteractionResultHolder.success(pUsedStack);
        }

        else
        {
            pPlayer.displayClientMessage(new TextComponent(new TranslatableComponent("message.lootdebugs.tool.resupply_caller.not_enough_nitra.01").getString() + " " + resupplyCallAmount +  " " + new TranslatableComponent("message.lootdebugs.tool.resupply_caller.not_enough_nitra.02").getString()), true);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    private void removeNitra(Player player, Predicate<ItemStack> NITRA)
    {
        int shrinkAmount = resupplyCallAmount;

        for(int i = 0; i < player.getInventory().getContainerSize(); ++i)
        {
            ItemStack itemstack1 = player.getInventory().getItem(i);

            if (NITRA.test(itemstack1))
            {
                if(itemstack1.getCount() >= resupplyCallAmount)
                {
                    itemstack1.shrink(resupplyCallAmount);
                    return;
                }
                else
                {
                    int maxItemStackShrinkAmount = itemstack1.getCount();

                    if(shrinkAmount < maxItemStackShrinkAmount)
                    {
                        itemstack1.shrink(shrinkAmount);

                        shrinkAmount = 0;
                    }
                    else
                    {

                        itemstack1.shrink(maxItemStackShrinkAmount);

                        shrinkAmount -= maxItemStackShrinkAmount;
                    }
                }

                if(shrinkAmount <= 0)
                {
                    return;
                }
            }
        }
    }

    private static boolean canCallResupply(Player player, ItemStack pStack, Predicate<ItemStack> NITRA)
    {
        return canUseItem(pStack) && haveEnoughNitra(player, NITRA);
    }

    public static boolean haveEnoughNitra(Player player, Predicate<ItemStack> NITRA)
    {
        int invAmount = 0;

        for(int i = 0; i < player.getInventory().getContainerSize(); ++i)
        {
            ItemStack itemstack1 = player.getInventory().getItem(i);

            if (NITRA.test(itemstack1))
            {
                if(itemstack1.getCount() >= resupplyCallAmount)
                {
                    return true;
                }
                else
                {
                    invAmount+= itemstack1.getCount();
                }

                if(invAmount >= resupplyCallAmount)
                {
                    return true;
                }
            }
        }

        return false;

    }
}
