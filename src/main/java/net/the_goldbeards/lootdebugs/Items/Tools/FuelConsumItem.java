package net.the_goldbeards.lootdebugs.Items.Tools;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public abstract class FuelConsumItem extends Item
{

    public FuelConsumItem(Properties pProperties)
    {
        super(pProperties);
    }


    //Call this if you want that you item should consum fuel
    protected void consumeFuel(Player player, Predicate<ItemStack> FuelType, float fuelAmount)
    {
        ItemStack fuel = getInventoryFuel(player, FuelType);

        fuel.hurtAndBreak((int) fuelAmount, player, (p_41300_) -> { //Calculate the fuel consum to range and max damage
            p_41300_.broadcastBreakEvent(fuel.getEquipmentSlot());

        });
    }


    public static ItemStack getInventoryFuel(Player player, Predicate<ItemStack> FUEL)
    {
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i)
        {
        ItemStack itemstack1 = player.getInventory().getItem(i);
        if (FUEL.test(itemstack1))
        {
            return itemstack1;
        }
    }

        return ItemStack.EMPTY;

    }


}
