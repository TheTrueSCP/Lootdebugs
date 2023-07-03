package net.the_goldbeards.lootdebugs.Block.Tools.SatchelCharge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge.SatchelChargeAshSide;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SatchelChargeAshBlock extends Block
{

    public SatchelChargeAshBlock(Properties pProperties) {
        super(pProperties);
    }



    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());

        return !pLevel.getBlockState(pPos.below()).isAir();

    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            if (!pLevel.isClientSide) {
                for(Direction direction : Direction.values()) {
                    pLevel.updateNeighborsAt(pPos.relative(direction), this);
                }
            }
        }
    }



    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

}
