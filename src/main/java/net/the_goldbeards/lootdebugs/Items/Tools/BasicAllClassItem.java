package net.the_goldbeards.lootdebugs.Items.Tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicToolItem;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

public abstract class BasicAllClassItem extends Item
{
    public BasicAllClassItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(pEntity instanceof Player player && pIsSelected)
        {
            if(UsefullStuff.DwarfClasses.canPlayerUseItem(pStack, player, IClassData.Classes.LeafLover))
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("message.lootdebugs.tool.no_dwarf").getString()), true);
                UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.tool.usable", false);
            }
            else
            {
                UsefullStuff.ItemNBTHelper.putBoolean(pStack, "lootdebugs.tool.usable", true);
            }
        }


        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public static boolean canUseItem(ItemStack pStack)
    {
        if(UsefullStuff.ItemNBTHelper.hasKey(pStack, "lootdebugs.tool.usable"))
        {
            return UsefullStuff.ItemNBTHelper.getBoolean(pStack, "lootdebugs.tool.usable");
        }
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }



    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

}
