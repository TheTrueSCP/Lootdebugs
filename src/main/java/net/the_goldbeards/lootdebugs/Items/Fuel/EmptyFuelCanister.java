package net.the_goldbeards.lootdebugs.Items.Fuel;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EmptyFuelCanister extends Item
{
    public EmptyFuelCanister(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}
