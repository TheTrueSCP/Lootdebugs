package net.the_goldbeards.lootdebugs.Items.Fuel;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;



public class MorkiteItem extends Item {

    public static int burnTime = 600;

    public MorkiteItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @org.jetbrains.annotations.Nullable RecipeType<?> recipeType) {
        return burnTime;
    }
}
