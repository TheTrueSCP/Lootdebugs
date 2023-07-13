package net.the_goldbeards.lootdebugs.Items.Tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public abstract class BasicToolItem extends Item
{
    public BasicToolItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(pEntity instanceof Player player && pIsSelected)
        {
            if(!ModUtils.DwarfClasses.canPlayerUseItem(pStack, player, getDwarfClassToUse()))
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("message.lootdebugs.tool.wrong_class_0").getString() + " " + ModUtils.DwarfClasses.getClassTranslate(getDwarfClassToUse()).getString() + " " + new TranslatableComponent("message.lootdebugs.tool.wrong_class_1").getString()), true);
            }
        }


        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public abstract IClassData.Classes getDwarfClassToUse();


    public boolean canUseItem(Player player)
    {
        return ModUtils.DwarfClasses.getPlayerClass(player) == getDwarfClassToUse();
    }

    public boolean canUseItem(IClassData.Classes dwarfClass)
    {
        return dwarfClass == getDwarfClassToUse();
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
