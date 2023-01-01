package net.the_goldbeards.lootdebugs.Block.GenBlocks.Ores;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class OreBlocks extends Block
{

    public OreBlocks(Properties p_49795_) {
        super(p_49795_);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return pState.getFluidState().isEmpty();
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isNeighborBlock(pPos, pLevel);
    }

    public boolean isNeighborBlock(BlockPos pos, LevelReader level)
    {
        if (level.getBlockState(pos.above(1)).isCollisionShapeFullBlock(level, pos.above(1)))
        {
            return true;
        }

        else if (level.getBlockState(pos.below(1)).isCollisionShapeFullBlock(level, pos.below(1)))
        {
            return true;
        }

        else if (level.getBlockState(pos.north(1)).isCollisionShapeFullBlock(level, pos.north(1)))
        {
            return true;
        }
        else if (level.getBlockState(pos.east(1)).isCollisionShapeFullBlock(level, pos.east(1)))
        {
            return true;
        }
        else if (level.getBlockState(pos.south(1)).isCollisionShapeFullBlock(level, pos.south(1)))
        {
            return true;
        }
        else if(level.getBlockState(pos.west(1)).isCollisionShapeFullBlock(level, pos.west(1)))
        {
            return true;
        }
        return false;
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return pType == PathComputationType.AIR && !this.hasCollision ? true : super.isPathfindable(pState, pLevel, pPos, pType);
    }




}
