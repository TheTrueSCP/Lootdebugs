package net.the_goldbeards.lootdebugs.Items.Fuel;



import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class MorkiteBlockItem extends BlockItem {


    public MorkiteBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return MorkiteItem.burnTime * 9;
    }
}
