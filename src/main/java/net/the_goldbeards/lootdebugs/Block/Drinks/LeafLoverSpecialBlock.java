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
public class LeafLoverSpecialBlock extends BushBlock {

    public LeafLoverSpecialBlock(Properties properties) {
        super(properties);
    }
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_N =Shapes.or(
            Block.box(2, 4.9, 7.5, 4, 5.932, 8.5),
            Block.box(2.25, 1.6799999999999997, 7.5, 3.25, 5.58, 8.5),
            Block.box(3.405899999999999, 5.1, 7.5, 5, 6.1, 8.5),
            Block.box(2.3360000000000003, 1.0999999999999996, 7.5, 5.5, 2.0999999999999996, 8.5),
            Block.box(5, 1, 7, 6, 6, 9),
            Block.box(10, 1, 7, 11, 6, 9),
            Block.box(7, 1, 10, 9, 6, 11),
            Block.box(7, 1, 5, 9, 6, 6),
            Block.box(7, 1, 10, 9, 6, 11),
            Block.box(7, 1, 5, 9, 6, 6),
            Block.box(10, 1, 7, 11, 6, 9),
            Block.box(9.75, 1, 7.25, 11.25, 7.25, 8.75),
            Block.box(4.75, 1, 7.25, 6.25, 7.25, 8.75),
            Block.box(5, 1, 7, 6, 6, 9),
            Block.box(10, 6, 6.9, 11.100000000000001, 7, 9.1),
            Block.box(10, 6, 6.9, 11.100000000000001, 7, 9.1),
            Block.box(6.899999999999999, 6, 10, 9.100000000000001, 7, 11.100000000000001),
            Block.box(6.899999999999999, 6, 10, 9.100000000000001, 7, 11.100000000000001),
            Block.box(6.899999999999999, 6, 4.899999999999999, 9.100000000000001, 7, 6),
            Block.box(6.899999999999999, 6, 4.899999999999999, 9.100000000000001, 7, 6),
            Block.box(4.9, 6, 6.9, 6, 7, 9.1),
            Block.box(4.9, 6, 6.9, 6, 7, 9.1),
            Block.box(7, 0, 9.75, 9, 1, 10.85),
            Block.box(7, 0, 9.75, 9, 1, 10.85),
            Block.box(5.15, 0, 7, 6.25, 1, 9),
            Block.box(5.15, 0, 7, 6.25, 1, 9),
            Block.box(9.75, 0, 7, 10.850000000000001, 1, 9),
            Block.box(9.75, 0, 7, 10.850000000000001, 1, 9),
            Block.box(7, 0, 5.15, 9, 1, 6.25),
            Block.box(7, 0, 5.15, 9, 1, 6.25),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(7, 1, 10.5, 9, 2, 11.5),
            Block.box(9.100000000000001, 3.75, 10.5, 10.299999999999997, 5, 11.5),
            Block.box(8.950000000000003, 2.5, 10.5, 9.95, 3.75, 11.5),
            Block.box(8.700000000000003, 1.5, 10.5, 9.7, 2.5, 11.5),
            Block.box(6.300000000000001, 1.5, 10.5, 7.299999999999997, 2.5, 11.5),
            Block.box(6.050000000000001, 2.5, 10.5, 7.049999999999997, 3.75, 11.5),
            Block.box(5.699999999999999, 3.75, 10.5, 6.899999999999999, 5, 11.5),
            Block.box(6.399999999999999, 4.5, 10.5, 9.600000000000001, 5.5, 11.5),
            Block.box(6.399999999999999, 4.5, 4.5, 9.600000000000001, 5.5, 5.5),
            Block.box(5.699999999999999, 3.75, 4.5, 6.899999999999999, 5, 5.5),
            Block.box(6.050000000000001, 2.5, 4.5, 7.049999999999997, 3.75, 5.5),
            Block.box(7, 1, 4.5, 9, 2, 5.5),
            Block.box(9.100000000000001, 3.75, 4.5, 10.299999999999997, 5, 5.5),
            Block.box(8.950000000000003, 2.5, 4.5, 9.95, 3.75, 5.5),
            Block.box(8.700000000000003, 1.5, 4.5, 9.7, 2.5, 5.5),
            Block.box(6.300000000000001, 1.5, 4.5, 7.299999999999997, 2.5, 5.5),
            Block.box(5, 7, 6, 9, 9, 9),
            Block.box(6, 1, 6, 10, 8, 10),
            Block.box(6, 5.1, 5, 9, 8, 11)
    );

    private static final VoxelShape SHAPE_E =Shapes.or(
            Block.box(7.5, 4.9, 2, 8.5, 5.932, 4),
            Block.box(7.5, 1.6799999999999997, 2.25, 8.5, 5.58, 3.25),
            Block.box(7.5, 5.1, 3.405899999999999, 8.5, 6.1, 5),
            Block.box(7.5, 1.0999999999999996, 2.3360000000000003, 8.5, 2.0999999999999996, 5.5),
            Block.box(7, 1, 5, 9, 6, 6),
            Block.box(7, 1, 10, 9, 6, 11),
            Block.box(5, 1, 7, 6, 6, 9),
            Block.box(10, 1, 7, 11, 6, 9),
            Block.box(5, 1, 7, 6, 6, 9),
            Block.box(10, 1, 7, 11, 6, 9),
            Block.box(7, 1, 10, 9, 6, 11),
            Block.box(7.25, 1, 9.75, 8.75, 7.25, 11.25),
            Block.box(7.25, 1, 4.75, 8.75, 7.25, 6.25),
            Block.box(7, 1, 5, 9, 6, 6),
            Block.box(6.9, 6, 10, 9.1, 7, 11.100000000000001),
            Block.box(6.9, 6, 10, 9.1, 7, 11.100000000000001),
            Block.box(4.899999999999999, 6, 6.899999999999999, 6, 7, 9.100000000000001),
            Block.box(4.899999999999999, 6, 6.899999999999999, 6, 7, 9.100000000000001),
            Block.box(10, 6, 6.899999999999999, 11.100000000000001, 7, 9.100000000000001),
            Block.box(10, 6, 6.899999999999999, 11.100000000000001, 7, 9.100000000000001),
            Block.box(6.9, 6, 4.9, 9.1, 7, 6),
            Block.box(6.9, 6, 4.9, 9.1, 7, 6),
            Block.box(5.15, 0, 7, 6.25, 1, 9),
            Block.box(5.15, 0, 7, 6.25, 1, 9),
            Block.box(7, 0, 5.15, 9, 1, 6.25),
            Block.box(7, 0, 5.15, 9, 1, 6.25),
            Block.box(7, 0, 9.75, 9, 1, 10.850000000000001),
            Block.box(7, 0, 9.75, 9, 1, 10.850000000000001),
            Block.box(9.75, 0, 7, 10.85, 1, 9),
            Block.box(9.75, 0, 7, 10.85, 1, 9),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(4.5, 1, 7, 5.5, 2, 9),
            Block.box(4.5, 3.75, 9.100000000000001, 5.5, 5, 10.299999999999997),
            Block.box(4.5, 2.5, 8.950000000000003, 5.5, 3.75, 9.95),
            Block.box(4.5, 1.5, 8.700000000000003, 5.5, 2.5, 9.7),
            Block.box(4.5, 1.5, 6.300000000000001, 5.5, 2.5, 7.299999999999997),
            Block.box(4.5, 2.5, 6.050000000000001, 5.5, 3.75, 7.049999999999997),
            Block.box(4.5, 3.75, 5.699999999999999, 5.5, 5, 6.899999999999999),
            Block.box(4.5, 4.5, 6.399999999999999, 5.5, 5.5, 9.600000000000001),
            Block.box(10.5, 4.5, 6.399999999999999, 11.5, 5.5, 9.600000000000001),
            Block.box(10.5, 3.75, 5.699999999999999, 11.5, 5, 6.899999999999999),
            Block.box(10.5, 2.5, 6.050000000000001, 11.5, 3.75, 7.049999999999997),
            Block.box(10.5, 1, 7, 11.5, 2, 9),
            Block.box(10.5, 3.75, 9.100000000000001, 11.5, 5, 10.299999999999997),
            Block.box(10.5, 2.5, 8.950000000000003, 11.5, 3.75, 9.95),
            Block.box(10.5, 1.5, 8.700000000000003, 11.5, 2.5, 9.7),
            Block.box(10.5, 1.5, 6.300000000000001, 11.5, 2.5, 7.299999999999997),
            Block.box(7, 7, 5, 10, 9, 9),
            Block.box(6, 1, 6, 10, 8, 10),
            Block.box(5, 5.1, 6, 11, 8, 9)
    );

    private static final VoxelShape SHAPE_S =Shapes.or(
            Block.box(12, 4.9, 7.5, 14, 5.932, 8.5),
            Block.box(12.75, 1.6799999999999997, 7.5, 13.75, 5.58, 8.5),
            Block.box(11, 5.1, 7.5, 12.594100000000001, 6.1, 8.5),
            Block.box(10.5, 1.0999999999999996, 7.5, 13.664, 2.0999999999999996, 8.5),
            Block.box(10, 1, 7, 11, 6, 9),
            Block.box(5, 1, 7, 6, 6, 9),
            Block.box(7, 1, 5, 9, 6, 6),
            Block.box(7, 1, 10, 9, 6, 11),
            Block.box(7, 1, 5, 9, 6, 6),
            Block.box(7, 1, 10, 9, 6, 11),
            Block.box(5, 1, 7, 6, 6, 9),
            Block.box(4.75, 1, 7.25, 6.25, 7.25, 8.75),
            Block.box(9.75, 1, 7.25, 11.25, 7.25, 8.75),
            Block.box(10, 1, 7, 11, 6, 9),
            Block.box(4.9, 6, 6.9, 6, 7, 9.1),
            Block.box(4.9, 6, 6.9, 6, 7, 9.1),
            Block.box(6.9, 6, 4.9, 9.1, 7, 6),
            Block.box(6.9, 6, 4.9, 9.1, 7, 6),
            Block.box(6.9, 6, 10, 9.1, 7, 11.1),
            Block.box(6.9, 6, 10, 9.1, 7, 11.1),
            Block.box(10, 6, 6.9, 11.1, 7, 9.1),
            Block.box(10, 6, 6.9, 11.1, 7, 9.1),
            Block.box(7, 0, 5.15, 9, 1, 6.25),
            Block.box(7, 0, 5.15, 9, 1, 6.25),
            Block.box(9.75, 0, 7, 10.85, 1, 9),
            Block.box(9.75, 0, 7, 10.85, 1, 9),
            Block.box(5.15, 0, 7, 6.25, 1, 9),
            Block.box(5.15, 0, 7, 6.25, 1, 9),
            Block.box(7, 0, 9.75, 9, 1, 10.85),
            Block.box(7, 0, 9.75, 9, 1, 10.85),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(7.000000000000002, 1, 4.5, 9, 2, 5.5),
            Block.box(5.700000000000001, 3.75, 4.5, 6.899999999999999, 5, 5.5),
            Block.box(6.050000000000001, 2.5, 4.5, 7.049999999999999, 3.75, 5.5),
            Block.box(6.300000000000001, 1.5, 4.5, 7.299999999999999, 2.5, 5.5),
            Block.box(8.700000000000001, 1.5, 4.5, 9.7, 2.5, 5.5),
            Block.box(8.950000000000001, 2.5, 4.5, 9.95, 3.75, 5.5),
            Block.box(9.100000000000001, 3.75, 4.5, 10.3, 5, 5.5),
            Block.box(6.4, 4.5, 4.5, 9.6, 5.5, 5.5),
            Block.box(6.4, 4.5, 10.5, 9.6, 5.5, 11.5),
            Block.box(9.100000000000001, 3.75, 10.5, 10.3, 5, 11.5),
            Block.box(8.950000000000001, 2.5, 10.5, 9.95, 3.75, 11.5),
            Block.box(7.000000000000002, 1, 10.5, 9, 2, 11.5),
            Block.box(5.700000000000001, 3.75, 10.5, 6.899999999999999, 5, 11.5),
            Block.box(6.050000000000001, 2.5, 10.5, 7.049999999999999, 3.75, 11.5),
            Block.box(6.300000000000001, 1.5, 10.5, 7.299999999999999, 2.5, 11.5),
            Block.box(8.700000000000001, 1.5, 10.5, 9.7, 2.5, 11.5),
            Block.box(7, 7, 7, 11, 9, 10),
            Block.box(6, 1, 6, 10, 8, 10),
            Block.box(7, 5.1, 5, 10, 8, 11)
    );

    private static final VoxelShape SHAPE_W =
            Shapes.or(
                    Block.box(7.5, 4.9, 12, 8.5, 5.932, 14),
                    Block.box(7.5, 1.6799999999999997, 12.75, 8.5, 5.58, 13.75),
                    Block.box(7.5, 5.1, 11, 8.5, 6.1, 12.594100000000001),
                    Block.box(7.5, 1.0999999999999996, 10.5, 8.5, 2.0999999999999996, 13.664),
                    Block.box(7, 1, 10, 9, 6, 11),
                    Block.box(7, 1, 5, 9, 6, 6),
                    Block.box(10, 1, 7, 11, 6, 9),
                    Block.box(5, 1, 7, 6, 6, 9),
                    Block.box(10, 1, 7, 11, 6, 9),
                    Block.box(5, 1, 7, 6, 6, 9),
                    Block.box(7, 1, 5, 9, 6, 6),
                    Block.box(7.25, 1, 4.75, 8.75, 7.25, 6.25),
                    Block.box(7.25, 1, 9.75, 8.75, 7.25, 11.25),
                    Block.box(7, 1, 10, 9, 6, 11),
                    Block.box(6.9, 6, 4.899999999999999, 9.1, 7, 6),
                    Block.box(6.9, 6, 4.899999999999999, 9.1, 7, 6),
                    Block.box(10, 6, 6.899999999999999, 11.100000000000001, 7, 9.100000000000001),
                    Block.box(10, 6, 6.899999999999999, 11.100000000000001, 7, 9.100000000000001),
                    Block.box(4.899999999999999, 6, 6.899999999999999, 6, 7, 9.100000000000001),
                    Block.box(4.899999999999999, 6, 6.899999999999999, 6, 7, 9.100000000000001),
                    Block.box(6.9, 6, 10, 9.1, 7, 11.1),
                    Block.box(6.9, 6, 10, 9.1, 7, 11.1),
                    Block.box(9.75, 0, 7, 10.85, 1, 9),
                    Block.box(9.75, 0, 7, 10.85, 1, 9),
                    Block.box(7, 0, 9.75, 9, 1, 10.85),
                    Block.box(7, 0, 9.75, 9, 1, 10.85),
                    Block.box(7, 0, 5.149999999999999, 9, 1, 6.25),
                    Block.box(7, 0, 5.149999999999999, 9, 1, 6.25),
                    Block.box(5.15, 0, 7, 6.25, 1, 9),
                    Block.box(5.15, 0, 7, 6.25, 1, 9),
                    Block.box(6, 0.1, 6, 10, 1.1, 10),
                    Block.box(10.5, 1, 7, 11.5, 2, 9),
                    Block.box(10.5, 3.75, 5.700000000000003, 11.5, 5, 6.899999999999999),
                    Block.box(10.5, 2.5, 6.050000000000001, 11.5, 3.75, 7.049999999999997),
                    Block.box(10.5, 1.5, 6.300000000000001, 11.5, 2.5, 7.299999999999997),
                    Block.box(10.5, 1.5, 8.700000000000003, 11.5, 2.5, 9.7),
                    Block.box(10.5, 2.5, 8.950000000000003, 11.5, 3.75, 9.95),
                    Block.box(10.5, 3.75, 9.100000000000001, 11.5, 5, 10.3),
                    Block.box(10.5, 4.5, 6.399999999999999, 11.5, 5.5, 9.600000000000001),
                    Block.box(4.5, 4.5, 6.399999999999999, 5.5, 5.5, 9.600000000000001),
                    Block.box(4.5, 3.75, 9.100000000000001, 5.5, 5, 10.3),
                    Block.box(4.5, 2.5, 8.950000000000003, 5.5, 3.75, 9.95),
                    Block.box(4.5, 1, 7, 5.5, 2, 9),
                    Block.box(4.5, 3.75, 5.700000000000003, 5.5, 5, 6.899999999999999),
                    Block.box(4.5, 2.5, 6.050000000000001, 5.5, 3.75, 7.049999999999997),
                    Block.box(4.5, 1.5, 6.300000000000001, 5.5, 2.5, 7.299999999999997),
                    Block.box(4.5, 1.5, 8.700000000000003, 5.5, 2.5, 9.7),
                    Block.box(6, 7, 7, 9, 9, 11),
                    Block.box(6, 1, 6, 10, 8, 10),
                    Block.box(5, 5.1, 7, 11, 8, 10)
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
