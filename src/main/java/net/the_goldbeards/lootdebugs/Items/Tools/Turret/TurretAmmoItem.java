package net.the_goldbeards.lootdebugs.Items.Tools.Turret;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.util.ModUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TurretAmmoItem extends Item
{
    public static final float initialFuel = 50;

    public TurretAmmoItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {


        if(!ModUtils.ItemNBTHelper.hasKey(pStack, "ammoAmount"))
        {
            ModUtils.ItemNBTHelper.putFloat(pStack, "ammoAmount", initialFuel);
        }

        if(ModUtils.ItemNBTHelper.getFloat(pStack, "ammoAmount") <= 0)
        {
            pStack.shrink(pStack.getCount() + 1);
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public int getBarWidth(ItemStack pStack)
    {
        return Math.round(13 / initialFuel * ModUtils.ItemNBTHelper.getFloat(pStack, "ammoAmount"));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(ModUtils.ItemNBTHelper.hasKey(pStack, "ammoAmount"))
        {
            pTooltipComponents.add(new TextComponent(new TranslatableComponent("tooltip.lootdebugs.turret_ammo.amount").getString() + ModUtils.ItemNBTHelper.getFloat(pStack, "ammoAmount") + "/" + (int) TurretAmmoItem.initialFuel));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    public static void ShrinkAmmo(ItemStack pStack, float shrinkAmount)
    {
        float ammoAmount = ModUtils.ItemNBTHelper.getFloat(pStack, "ammoAmount");

        float newAmount = ammoAmount - shrinkAmount;

        if(newAmount < 0)
        {
            ModUtils.ItemNBTHelper.putFloat(pStack, "ammoAmount", 0);
        }
        else
        {
            ModUtils.ItemNBTHelper.putFloat(pStack, "ammoAmount", newAmount);
        }
    }
}
