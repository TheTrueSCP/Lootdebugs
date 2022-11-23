package net.the_goldbeards.lootdebugs.Block.Drinks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class BlackoutStoutBlock extends BushBlock {

    public BlackoutStoutBlock(Properties properties) {
        super(properties);
    }


    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_N =Shapes.or(
            Block.box(10.5, 0, 6.75, 11.75, 7.3, 9.25),
            Block.box(0, 0.75, 7, 1, 6.25, 9),
            Block.box(4, 0.75, 7, 5, 6.25, 9),
            Block.box(3, 3, 7, 4, 4, 9),
            Block.box(3, 5, 7, 4, 6, 9),
            Block.box(2, 6, 7, 3, 7, 9),
            Block.box(2, 4, 7, 3, 5, 9),
            Block.box(2, 2, 7, 3, 3, 9),
            Block.box(3, 1, 7, 4, 2, 9),
            Block.box(2, 0, 7, 3, 1, 9),
            Block.box(1, 1, 7, 2, 2, 9),
            Block.box(1, 5, 7, 2, 6, 9),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(7.5, 3, 4, 8.5, 4, 5.25),
            Block.box(7.5, 3, 10.75, 8.5, 4, 12),
            Block.box(7.5, 3, 4, 8.5, 4, 5.25),
            Block.box(7.5, 3, 10.75, 8.5, 4, 12),
            Block.box(7.5, 4.75, 10.75, 8.5, 5.75, 12),
            Block.box(7.5, 4.75, 4, 8.5, 5.75, 5.25),
            Block.box(7.5, 4.75, 4, 8.5, 5.75, 5.25),
            Block.box(7.5, 4.75, 10.75, 8.5, 5.75, 12),
            Block.box(7.5, 1.25, 10.75, 8.5, 2.25, 12),
            Block.box(7.5, 1.25, 4, 8.5, 2.25, 5.25),
            Block.box(7.5, 1.25, 4, 8.5, 2.25, 5.25),
            Block.box(7.5, 1.25, 10.75, 8.5, 2.25, 12),
            Block.box(11.25, 4, 7.5, 12.25, 5, 8.5),
            Block.box(11.25, 6, 7.5, 12.25, 7, 8.5),
            Block.box(11.25, 2.25, 7.5, 12.25, 3.25, 8.5),
            Block.box(11.25, 0.25, 7.5, 12.25, 1.25, 8.5),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(6.75, 5.75, 4.65, 9.25, 6.75, 5.75),
            Block.box(6.75, 5.75, 4.65, 9.25, 6.75, 5.75),
            Block.box(10.25, 5.75, 6.75, 11.35, 6.75, 9.25),
            Block.box(6.75, 5.75, 10.25, 9.25, 6.75, 11.35),
            Block.box(6.75, 5.75, 10.25, 9.25, 6.75, 11.35),
            Block.box(4.65, 5.75, 6.75, 5.75, 6.75, 9.25),
            Block.box(4.65, 5.75, 6.75, 5.75, 6.75, 9.25),
            Block.box(6.5, 2, 4.5, 9.5, 5, 5.5),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(6.5, 2, 10.5, 9.5, 5, 11.5),
            Block.box(4.75, 1, 6.75, 5.75, 6, 9.25),
            Block.box(4.5, 2, 6.5, 5.5, 5, 9.5),
            Block.box(10.25, 1, 6.625, 11.25, 6, 9.375),
            Block.box(10.5, 2, 6.375, 11.5, 5, 9.625),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(6.5, 2, 4.5, 9.5, 5, 5.5),
            Block.box(6.5, 2, 10.5, 9.5, 5, 11.5),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(4.5, 2, 6.5, 5.5, 5, 9.5),
            Block.box(4.75, 1, 6.75, 5.75, 6, 9.25),
            Block.box(10.5, 2, 6.5, 11.5, 5, 9.5),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(5, 7, 6, 9, 9, 9),
            Block.box(6, 5, 5, 9, 8, 11),
            Block.box(6, 1, 6, 10, 8, 10)
    );

    private static final VoxelShape SHAPE_E = Shapes.or(
            Block.box(6.75, 0, 10.5, 9.25, 7.3, 11.75),
            Block.box(7, 0.75, 0, 9, 6.25, 1),
            Block.box(7, 0.75, 4, 9, 6.25, 5),
            Block.box(7, 3, 3, 9, 4, 4),
            Block.box(7, 5, 3, 9, 6, 4),
            Block.box(7, 6, 2, 9, 7, 3),
            Block.box(7, 4, 2, 9, 5, 3),
            Block.box(7, 2, 2, 9, 3, 3),
            Block.box(7, 1, 3, 9, 2, 4),
            Block.box(7, 0, 2, 9, 1, 3),
            Block.box(7, 1, 1, 9, 2, 2),
            Block.box(7, 5, 1, 9, 6, 2),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(10.75, 3, 7.5, 12, 4, 8.5),
            Block.box(4, 3, 7.5, 5.25, 4, 8.5),
            Block.box(10.75, 3, 7.5, 12, 4, 8.5),
            Block.box(4, 3, 7.5, 5.25, 4, 8.5),
            Block.box(4, 4.75, 7.5, 5.25, 5.75, 8.5),
            Block.box(10.75, 4.75, 7.5, 12, 5.75, 8.5),
            Block.box(10.75, 4.75, 7.5, 12, 5.75, 8.5),
            Block.box(4, 4.75, 7.5, 5.25, 5.75, 8.5),
            Block.box(4, 1.25, 7.5, 5.25, 2.25, 8.5),
            Block.box(10.75, 1.25, 7.5, 12, 2.25, 8.5),
            Block.box(10.75, 1.25, 7.5, 12, 2.25, 8.5),
            Block.box(4, 1.25, 7.5, 5.25, 2.25, 8.5),
            Block.box(7.5, 4, 11.25, 8.5, 5, 12.25),
            Block.box(7.5, 6, 11.25, 8.5, 7, 12.25),
            Block.box(7.5, 2.25, 11.25, 8.5, 3.25, 12.25),
            Block.box(7.5, 0.25, 11.25, 8.5, 1.25, 12.25),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(10.25, 5.75, 6.75, 11.35, 6.75, 9.25),
            Block.box(10.25, 5.75, 6.75, 11.35, 6.75, 9.25),
            Block.box(6.75, 5.75, 10.25, 9.25, 6.75, 11.35),
            Block.box(4.65, 5.75, 6.75, 5.75, 6.75, 9.25),
            Block.box(4.65, 5.75, 6.75, 5.75, 6.75, 9.25),
            Block.box(6.75, 5.75, 4.65, 9.25, 6.75, 5.75),
            Block.box(6.75, 5.75, 4.65, 9.25, 6.75, 5.75),
            Block.box(10.5, 2, 6.5, 11.5, 5, 9.5),
            Block.box(4.75, 1, 6.75, 5.75, 6, 9.25),
            Block.box(4.5, 2, 6.5, 5.5, 5, 9.5),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(6.5, 2, 4.5, 9.5, 5, 5.5),
            Block.box(6.625, 1, 10.25, 9.375, 6, 11.25),
            Block.box(6.375, 2, 10.5, 9.625, 5, 11.5),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(10.5, 2, 6.5, 11.5, 5, 9.5),
            Block.box(4.5, 2, 6.5, 5.5, 5, 9.5),
            Block.box(4.75, 1, 6.75, 5.75, 6, 9.25),
            Block.box(6.5, 2, 4.5, 9.5, 5, 5.5),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(6.5, 2, 10.5, 9.5, 5, 11.5),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(7, 7, 5, 10, 9, 9),
            Block.box(5, 5, 6, 11, 8, 9),
            Block.box(6, 1, 6, 10, 8, 10)
    );

    private static final VoxelShape SHAPE_S = Shapes.or(
            Block.box(4.25, 0, 6.75, 5.5, 7.3, 9.25),
            Block.box(15, 0.75, 7, 16, 6.25, 9),
            Block.box(11, 0.75, 7, 12, 6.25, 9),
            Block.box(12, 3, 7, 13, 4, 9),
            Block.box(12, 5, 7, 13, 6, 9),
            Block.box(13, 6, 7, 14, 7, 9),
            Block.box(13, 4, 7, 14, 5, 9),
            Block.box(13, 2, 7, 14, 3, 9),
            Block.box(12, 1, 7, 13, 2, 9),
            Block.box(13, 0, 7, 14, 1, 9),
            Block.box(14, 1, 7, 15, 2, 9),
            Block.box(14, 5, 7, 15, 6, 9),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(7.5, 3, 10.75, 8.5, 4, 12),
            Block.box(7.5, 3, 4, 8.5, 4, 5.25),
            Block.box(7.5, 3, 10.75, 8.5, 4, 12),
            Block.box(7.5, 3, 4, 8.5, 4, 5.25),
            Block.box(7.5, 4.75, 4, 8.5, 5.75, 5.25),
            Block.box(7.5, 4.75, 10.75, 8.5, 5.75, 12),
            Block.box(7.5, 4.75, 10.75, 8.5, 5.75, 12),
            Block.box(7.5, 4.75, 4, 8.5, 5.75, 5.25),
            Block.box(7.5, 1.25, 4, 8.5, 2.25, 5.25),
            Block.box(7.5, 1.25, 10.75, 8.5, 2.25, 12),
            Block.box(7.5, 1.25, 10.75, 8.5, 2.25, 12),
            Block.box(7.5, 1.25, 4, 8.5, 2.25, 5.25),
            Block.box(3.75, 4, 7.5, 4.75, 5, 8.5),
            Block.box(3.75, 6, 7.5, 4.75, 7, 8.5),
            Block.box(3.75, 2.25, 7.5, 4.75, 3.25, 8.5),
            Block.box(3.75, 0.25, 7.5, 4.75, 1.25, 8.5),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(6.75, 5.75, 10.25, 9.25, 6.75, 11.35),
            Block.box(6.75, 5.75, 10.25, 9.25, 6.75, 11.35),
            Block.box(4.65, 5.75, 6.75, 5.75, 6.75, 9.25),
            Block.box(6.75, 5.75, 4.65, 9.25, 6.75, 5.75),
            Block.box(6.75, 5.75, 4.65, 9.25, 6.75, 5.75),
            Block.box(10.25, 5.75, 6.75, 11.35, 6.75, 9.25),
            Block.box(10.25, 5.75, 6.75, 11.35, 6.75, 9.25),
            Block.box(6.5, 2, 10.5, 9.5, 5, 11.5),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(6.5, 2, 4.5, 9.5, 5, 5.5),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(10.5, 2, 6.5, 11.5, 5, 9.5),
            Block.box(4.75, 1, 6.625, 5.75, 6, 9.375),
            Block.box(4.5, 2, 6.375, 5.5, 5, 9.625),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(6.5, 2, 10.5, 9.5, 5, 11.5),
            Block.box(6.5, 2, 4.5, 9.5, 5, 5.5),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(10.5, 2, 6.5, 11.5, 5, 9.5),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(4.5, 2, 6.5, 5.5, 5, 9.5),
            Block.box(4.75, 1, 6.75, 5.75, 6, 9.25),
            Block.box(7, 7, 7, 11, 9, 10),
            Block.box(7, 5, 5, 10, 8, 11),
            Block.box(6, 1, 6, 10, 8, 10)
    );

    private static final VoxelShape SHAPE_W = Shapes.or(
            Block.box(6.75, 0, 4.25, 9.25, 7.3, 5.5),
            Block.box(7, 0.75, 15, 9, 6.25, 16),
            Block.box(7, 0.75, 11, 9, 6.25, 12),
            Block.box(7, 3, 12, 9, 4, 13),
            Block.box(7, 5, 12, 9, 6, 13),
            Block.box(7, 6, 13, 9, 7, 14),
            Block.box(7, 4, 13, 9, 5, 14),
            Block.box(7, 2, 13, 9, 3, 14),
            Block.box(7, 1, 12, 9, 2, 13),
            Block.box(7, 0, 13, 9, 1, 14),
            Block.box(7, 1, 14, 9, 2, 15),
            Block.box(7, 5, 14, 9, 6, 15),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(10, 0, 7, 11, 7.25, 9),
            Block.box(5, 0, 7, 6, 7.25, 9),
            Block.box(7, 0, 5, 9, 7.25, 6),
            Block.box(7, 0, 10, 9, 7.25, 11),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(4, 3, 7.5, 5.25, 4, 8.5),
            Block.box(10.75, 3, 7.5, 12, 4, 8.5),
            Block.box(4, 3, 7.5, 5.25, 4, 8.5),
            Block.box(10.75, 3, 7.5, 12, 4, 8.5),
            Block.box(10.75, 4.75, 7.5, 12, 5.75, 8.5),
            Block.box(4, 4.75, 7.5, 5.25, 5.75, 8.5),
            Block.box(4, 4.75, 7.5, 5.25, 5.75, 8.5),
            Block.box(10.75, 4.75, 7.5, 12, 5.75, 8.5),
            Block.box(10.75, 1.25, 7.5, 12, 2.25, 8.5),
            Block.box(4, 1.25, 7.5, 5.25, 2.25, 8.5),
            Block.box(4, 1.25, 7.5, 5.25, 2.25, 8.5),
            Block.box(10.75, 1.25, 7.5, 12, 2.25, 8.5),
            Block.box(7.5, 4, 3.75, 8.5, 5, 4.75),
            Block.box(7.5, 6, 3.75, 8.5, 7, 4.75),
            Block.box(7.5, 2.25, 3.75, 8.5, 3.25, 4.75),
            Block.box(7.5, 0.25, 3.75, 8.5, 1.25, 4.75),
            Block.box(4.75, 1, 6.75, 5.75, 6, 9.25),
            Block.box(4.65, 5.75, 6.75, 5.75, 6.75, 9.25),
            Block.box(4.65, 5.75, 6.75, 5.75, 6.75, 9.25),
            Block.box(6.75, 5.75, 4.65, 9.25, 6.75, 5.75),
            Block.box(10.25, 5.75, 6.75, 11.35, 6.75, 9.25),
            Block.box(10.25, 5.75, 6.75, 11.35, 6.75, 9.25),
            Block.box(6.75, 5.75, 10.25, 9.25, 6.75, 11.35),
            Block.box(6.75, 5.75, 10.25, 9.25, 6.75, 11.35),
            Block.box(4.5, 2, 6.5, 5.5, 5, 9.5),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(10.5, 2, 6.5, 11.5, 5, 9.5),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(6.5, 2, 10.5, 9.5, 5, 11.5),
            Block.box(6.625, 1, 4.75, 9.375, 6, 5.75),
            Block.box(6.375, 2, 4.5, 9.625, 5, 5.5),
            Block.box(4.75, 1, 6.75, 5.75, 6, 9.25),
            Block.box(4.5, 2, 6.5, 5.5, 5, 9.5),
            Block.box(10.5, 2, 6.5, 11.5, 5, 9.5),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(6.5, 2, 10.5, 9.5, 5, 11.5),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(6.5, 2, 4.5, 9.5, 5, 5.5),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(6, 7, 7, 9, 9, 11),
            Block.box(5, 5, 7, 11, 8, 10),
            Block.box(6, 1, 6, 10, 8, 10)
    );

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        switch (state.getValue(FACING))
        {
            case NORTH:
                return SHAPE_N;

            case EAST:
                return SHAPE_E;

            case WEST:
                return SHAPE_W;

            case SOUTH:
                return SHAPE_S;

            default:
                return SHAPE_N;
        }
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return !pState.is(ModTags.Blocks.BEER_CAN_PLACE_ON);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


}
