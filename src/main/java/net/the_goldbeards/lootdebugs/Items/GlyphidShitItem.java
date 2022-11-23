package net.the_goldbeards.lootdebugs.Items;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;



public class GlyphidShitItem extends BlockItem {


    public GlyphidShitItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}

