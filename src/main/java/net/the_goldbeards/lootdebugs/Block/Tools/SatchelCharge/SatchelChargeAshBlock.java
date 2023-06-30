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
    public static final EnumProperty<SatchelChargeAshSide> NORTH_CONNECTION = EnumProperty.create("north", SatchelChargeAshSide.class);
    public static final EnumProperty<SatchelChargeAshSide> EAST_CONNECTION = EnumProperty.create("east", SatchelChargeAshSide.class);
    public static final EnumProperty<SatchelChargeAshSide> SOUTH_CONNECTION = EnumProperty.create("south", SatchelChargeAshSide.class);
    public static final EnumProperty<SatchelChargeAshSide> WEST_CONNECTION = EnumProperty.create("west", SatchelChargeAshSide.class);

    public SatchelChargeAshBlock(Properties pProperties) {
        super(pProperties);
        this.defaultBlockState().setValue(NORTH_CONNECTION, SatchelChargeAshSide.NONE)
                .setValue(EAST_CONNECTION, SatchelChargeAshSide.NONE)
                .setValue(SOUTH_CONNECTION, SatchelChargeAshSide.NONE)
                .setValue(WEST_CONNECTION, SatchelChargeAshSide.NONE);
    }

    private static final VoxelShape SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 2, 16));

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
       return true;
    }

    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
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

    public List<BlockPos> getAshNeighbors(Level level, BlockPos pos)
    {
        List<BlockPos> ashNeighbors = NonNullList.create();

        if(level.getBlockState(pos.north(1)).is(ModBlocks.SATCHEL_CHARGE_ASH.get()))
        {
            ashNeighbors.add(pos.north());
        }

        if(level.getBlockState(pos.east(1)).is(ModBlocks.SATCHEL_CHARGE_ASH.get()))
        {
            ashNeighbors.add(pos.east(1));
        }

        if(level.getBlockState(pos.south(1)).is(ModBlocks.SATCHEL_CHARGE_ASH.get()))
        {
            ashNeighbors.add(pos.south(1));
        }

        if(level.getBlockState(pos.west(1)).is(ModBlocks.SATCHEL_CHARGE_ASH.get()))
        {
            ashNeighbors.add(pos.west(1));
        }
        return ashNeighbors;
    }

    private void setBlockConnections(Level pLevel, BlockPos pPos)
    {
        List<BlockPos> ashNeighbors = getAshNeighbors(pLevel, pPos.above());

        pLevel.setBlock(pPos, ModBlocks.SATCHEL_CHARGE_ASH.get().defaultBlockState(), 3);

        for(BlockPos ashNeighbor : ashNeighbors)
        {
            setConnectionFromPosition(pLevel, pPos, ashNeighbor);
        }
    }

    /**
     *
     * @param pLevel Level
     * @param ashDown this ash block
     * @param ashNeighbor the ash neighbor one block in one direction(north, east, south, west) AND one above
     */
    private void setConnectionFromPosition(Level pLevel, BlockPos ashDown, BlockPos ashNeighbor)
    {
        float xDiff = Math.abs(ashDown.getX()) - Math.abs(ashNeighbor.getX());
        float zDiff = Math.abs(ashDown.getZ()) - Math.abs(ashNeighbor.getZ());


        if(xDiff > 0)
        {
            pLevel.setBlock(ashDown, pLevel.getBlockState(ashDown).setValue(EAST_CONNECTION, SatchelChargeAshSide.UP), 3);
        }
        else if(xDiff < 0)
        {
            pLevel.setBlock(ashDown, pLevel.getBlockState(ashDown).setValue(WEST_CONNECTION, SatchelChargeAshSide.UP), 3);
        }

        if(zDiff > 0)
        {
            pLevel.setBlock(ashDown, pLevel.getBlockState(ashDown).setValue(NORTH_CONNECTION, SatchelChargeAshSide.UP), 3);
        }
        else if(zDiff < 0)
        {
            pLevel.setBlock(ashDown, pLevel.getBlockState(ashDown).setValue(SOUTH_CONNECTION, SatchelChargeAshSide.UP), 3);
        }
    }


    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
       return true;
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        switch(pRotation) {
            case CLOCKWISE_180:
                return pState.setValue(NORTH_CONNECTION, pState.getValue(SOUTH_CONNECTION)).setValue(EAST_CONNECTION, pState.getValue(WEST_CONNECTION)).setValue(SOUTH_CONNECTION, pState.getValue(NORTH_CONNECTION)).setValue(WEST_CONNECTION, pState.getValue(EAST_CONNECTION));
            case COUNTERCLOCKWISE_90:
                return pState.setValue(NORTH_CONNECTION, pState.getValue(EAST_CONNECTION)).setValue(EAST_CONNECTION, pState.getValue(SOUTH_CONNECTION)).setValue(SOUTH_CONNECTION, pState.getValue(WEST_CONNECTION)).setValue(WEST_CONNECTION, pState.getValue(NORTH_CONNECTION));
            case CLOCKWISE_90:
                return pState.setValue(NORTH_CONNECTION, pState.getValue(WEST_CONNECTION)).setValue(EAST_CONNECTION, pState.getValue(NORTH_CONNECTION)).setValue(SOUTH_CONNECTION, pState.getValue(EAST_CONNECTION)).setValue(WEST_CONNECTION, pState.getValue(SOUTH_CONNECTION));
            default:
                return pState;
        }
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        switch(pMirror) {
            case LEFT_RIGHT:
                return pState.setValue(NORTH_CONNECTION, pState.getValue(SOUTH_CONNECTION)).setValue(SOUTH_CONNECTION, pState.getValue(NORTH_CONNECTION));
            case FRONT_BACK:
                return pState.setValue(EAST_CONNECTION, pState.getValue(WEST_CONNECTION)).setValue(WEST_CONNECTION, pState.getValue(EAST_CONNECTION));
            default:
                return super.mirror(pState, pMirror);
        }
    }


    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH_CONNECTION, EAST_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION);
    }


}
