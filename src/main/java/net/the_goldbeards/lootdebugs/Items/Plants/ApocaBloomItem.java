package net.the_goldbeards.lootdebugs.Items.Plants;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;


public class ApocaBloomItem extends BlockItem
{

    public ApocaBloomItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }


    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 64;
    }


}
