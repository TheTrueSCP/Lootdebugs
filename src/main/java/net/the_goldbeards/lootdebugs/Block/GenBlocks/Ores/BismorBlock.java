package net.the_goldbeards.lootdebugs.Block.GenBlocks.Ores;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.init.ModBlocks;

public class BismorBlock extends OreBlocks
{

    public BismorBlock(Properties properties) {
        super(properties);
    }




    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.DIRT) ||
                state.is(Blocks.COARSE_DIRT) ||
                state.is(Blocks.PODZOL) ||
                state.is(Blocks.STONE) ||
                state.is(Blocks.CLAY) ||
                state.is(Blocks.MOSS_BLOCK) ||
                state.is(Blocks.COBBLED_DEEPSLATE) ||
                state.is(Blocks.COBBLESTONE) ||
                state.is(Blocks.DEEPSLATE) ||
                state.is(ModBlocks.RED_SUGAR.get());
    }


}
