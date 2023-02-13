package net.the_goldbeards.lootdebugs.Items.Tools;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.util.function.Predicate;

public abstract class FuelConsumeItem extends BasicToolItem
{

    public FuelConsumeItem(Properties pProperties)
    {
        super(pProperties);
    }


    //Call this if you want that you item should consum fuel
    protected void consumeFuel(Player player, Predicate<ItemStack> FuelType, float fuelAmount)
    {
        if(fuelAmount < 0)
        {
            throw new IllegalArgumentException("FuelConsumeItem/consumeFuel have got an negative fuelAmount");
        }

        ItemStack fuel = getUsableInventoryFuel(player, FuelType);

        if(!fuel.isEmpty())
        {
            float newFuel = UsefullStuff.ItemNBTHelper.getFloat(fuel, "fuelAmount") - fuelAmount;

            if (newFuel < 0) {
                newFuel = 0;
            }
            UsefullStuff.ItemNBTHelper.putFloat(fuel, "fuelAmount", newFuel);
        }
    }


    public static ItemStack getUsableInventoryFuel(Player player, Predicate<ItemStack> FUEL)
    {
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i)
        {
        ItemStack itemstack1 = player.getInventory().getItem(i);

        if (FUEL.test(itemstack1))
        {
            if(UsefullStuff.ItemNBTHelper.getFloat(itemstack1, "fuelAmount") > 0)
            {
                return itemstack1;
            }
        }
    }

        return ItemStack.EMPTY;

    }
}
