package net.the_goldbeards.lootdebugs.Items.Fuel;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FuelCanisterItem extends Item
{
    public static final float maxFuel = 1000;

    public FuelCanisterItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ModItems.FUEL_CANISTER.get(), 1);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(!ModUtils.ItemNBTHelper.hasKey(pStack, "fuelAmount"))
        {
            ModUtils.ItemNBTHelper.putFloat(pStack, "fuelAmount", 0);
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public int getBarWidth(ItemStack pStack)
    {
        return Math.round(13 / maxFuel * ModUtils.ItemNBTHelper.getFloat(pStack, "fuelAmount"));
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
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return (int) (ModUtils.ItemNBTHelper.getFloat(itemStack, "fuelAmount") * 100);
    }



    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(ModUtils.ItemNBTHelper.hasKey(pStack, "fuelAmount"))
        {
        pTooltipComponents.add(new TextComponent(new TranslatableComponent("tooltip.lootdebugs.fuel_canister.amount").getString() + ModUtils.ItemNBTHelper.getFloat(pStack, "fuelAmount") + "/" + (int) FuelCanisterItem.maxFuel));
        }

        if(Screen.hasShiftDown())
        {
            pTooltipComponents.add(new TranslatableComponent("tooltip.lootdebugs.fuel_canister.refuel"));
        }
        else
        {
            pTooltipComponents.add(new TranslatableComponent("tooltip.lootdebugs.more_information"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    public static ItemStack getFullFuel()
    {
        ItemStack fuel = new ItemStack(ModItems.FUEL_CANISTER.get(), 1);

        ModUtils.ItemNBTHelper.putFloat(fuel, "fuelAmount", maxFuel);

        return fuel;
    }
}
