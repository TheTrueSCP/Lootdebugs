package net.the_goldbeards.lootdebugs.Block.GenBlocks.Ores;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CropperBlock extends OreBlocks
{

    public CropperBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isAnNeighborBlock(pPos, pLevel, BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    }

    public boolean isAnNeighborBlock(BlockPos pos, LevelReader level, TagKey block)
    {
        if (!level.getBlockState(pos.above(1)).is(block))
        {
            return true;
        }

        else if (!level.getBlockState(pos.below(1)).is(block))
        {
            return true;
        }

        else if (!level.getBlockState(pos.north(1)).is(block))
        {
            return true;
        }
        else if (!level.getBlockState(pos.east(1)).is(block))
        {
            return true;
        }
        else if (!level.getBlockState(pos.south(1)).is(block))
        {
            return true;
        }
        else if(!level.getBlockState(pos.west(1)).is(block))
        {
            return true;
        }
        return false;
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
                state.is(Blocks.DEEPSLATE);
    }

}
