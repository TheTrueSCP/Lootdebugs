package net.the_goldbeards.lootdebugs.Block.Beers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.Block.BasicRotatableBlock;

public class DarkMorkiteBlock extends BasicRotatableBlock {

    public DarkMorkiteBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE_N = Shapes.or(
            Block.box(7.5, 5, 11, 10, 6, 12),
            Block.box(6, 5, 11, 8.5, 6, 12),
            Block.box(7, 2, 10.5, 8, 6, 11.5),
            Block.box(7.5, 5, 4, 10, 6, 5),
            Block.box(6, 5, 4, 8.5, 6, 5),
            Block.box(8, 2, 4.5, 9, 6, 5.5),
            Block.box(3, 1, 7, 4, 6, 9),
            Block.box(2.9000000000000004, 0.9, 6.9, 5.9, 2, 9.1),
            Block.box(3, 5, 6.9, 6, 6, 9.1),
            Block.box(1, 5, 6.9, 4, 6, 9.1),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(9.5, 1.5, 5.5, 10.5, 4.5, 6.5),
            Block.box(10.5, 1.5, 7, 11.5, 4.5, 8),
            Block.box(11.5, 1.5, 8, 12.5, 4.5, 9),
            Block.box(9.5, 1.5, 9.5, 10.5, 4.5, 10.5),
            Block.box(5.5, 1.5, 9.5, 6.5, 4.5, 10.5),
            Block.box(5.5, 1.5, 5.5, 6.5, 4.5, 6.5),
            Block.box(6.9, 5.2, 4.699999999999999, 9.1, 7.2, 6.199999999999999),
            Block.box(6.9, 5.2, 4.699999999999999, 9.1, 7.2, 6.199999999999999),
            Block.box(6.9, 5.2, 9.8, 9.1, 7.2, 11.3),
            Block.box(6.9, 5.2, 9.8, 9.1, 7.2, 11.3),
            Block.box(5, 7, 6, 9, 9, 9),
            Block.box(6, 5, 5, 9, 8, 11),
            Block.box(6, 1, 6, 10, 8, 10)
    );

    private static final VoxelShape SHAPE_E = Shapes.or(
            Block.box(4, 5, 7.5, 5, 6, 10),
            Block.box(4, 5, 6, 5, 6, 8.5),
            Block.box(4.5, 2, 7, 5.5, 6, 8),
            Block.box(11, 5, 7.5, 12, 6, 10),
            Block.box(11, 5, 6, 12, 6, 8.5),
            Block.box(10.5, 2, 8, 11.5, 6, 9),
            Block.box(7, 1, 3, 9, 6, 4),
            Block.box(6.9, 0.9, 2.9000000000000004, 9.1, 2, 5.9),
            Block.box(6.9, 5, 3, 9.1, 6, 6),
            Block.box(6.9, 5, 1, 9.1, 6, 4),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(9.5, 1.5, 9.5, 10.5, 4.5, 10.5),
            Block.box(8, 1.5, 10.5, 9, 4.5, 11.5),
            Block.box(7, 1.5, 11.5, 8, 4.5, 12.5),
            Block.box(5.5, 1.5, 9.5, 6.5, 4.5, 10.5),
            Block.box(5.5, 1.5, 5.5, 6.5, 4.5, 6.5),
            Block.box(9.5, 1.5, 5.5, 10.5, 4.5, 6.5),
            Block.box(9.8, 5.2, 6.9, 11.3, 7.2, 9.1),
            Block.box(9.8, 5.2, 6.9, 11.3, 7.2, 9.1),
            Block.box(4.7, 5.2, 6.9, 6.199999999999999, 7.2, 9.1),
            Block.box(4.7, 5.2, 6.9, 6.199999999999999, 7.2, 9.1),
            Block.box(7, 7, 5, 10, 9, 9),
            Block.box(5, 5, 6, 11, 8, 9),
            Block.box(6, 1, 6, 10, 8, 10)
    );

    private static final VoxelShape SHAPE_S = Shapes.or(
            Block.box(6, 5, 4, 8.5, 6, 5),
            Block.box(7.5, 5, 4, 10, 6, 5),
            Block.box(8, 2, 4.5, 9, 6, 5.5),
            Block.box(6, 5, 11, 8.5, 6, 12),
            Block.box(7.5, 5, 11, 10, 6, 12),
            Block.box(7, 2, 10.5, 8, 6, 11.5),
            Block.box(12, 1, 7, 13, 6, 9),
            Block.box(10.1, 0.9, 6.9, 13.1, 2, 9.1),
            Block.box(10, 5, 6.9, 13, 6, 9.1),
            Block.box(12, 5, 6.9, 15, 6, 9.1),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(5.5, 1.5, 9.5, 6.5, 4.5, 10.5),
            Block.box(4.5, 1.5, 8, 5.5, 4.5, 9),
            Block.box(3.5, 1.5, 7, 4.5, 4.5, 8),
            Block.box(5.5, 1.5, 5.5, 6.5, 4.5, 6.5),
            Block.box(9.5, 1.5, 5.5, 10.5, 4.5, 6.5),
            Block.box(9.5, 1.5, 9.5, 10.5, 4.5, 10.5),
            Block.box(6.9, 5.2, 9.8, 9.1, 7.2, 11.3),
            Block.box(6.9, 5.2, 9.8, 9.1, 7.2, 11.3),
            Block.box(6.9, 5.2, 4.7, 9.1, 7.2, 6.2),
            Block.box(6.9, 5.2, 4.7, 9.1, 7.2, 6.2),
            Block.box(7, 7, 7, 11, 9, 10),
            Block.box(7, 5, 5, 10, 8, 11),
            Block.box(6, 1, 6, 10, 8, 10)
    );

    private static final VoxelShape SHAPE_W = Shapes.or(
            Block.box(11, 5, 6, 12, 6, 8.5),
            Block.box(11, 5, 7.5, 12, 6, 10),
            Block.box(10.5, 2, 8, 11.5, 6, 9),
            Block.box(4, 5, 6, 5, 6, 8.5),
            Block.box(4, 5, 7.5, 5, 6, 10),
            Block.box(4.5, 2, 7, 5.5, 6, 8),
            Block.box(7, 1, 12, 9, 6, 13),
            Block.box(6.899999999999999, 0.9, 10.1, 9.1, 2, 13.100000000000001),
            Block.box(6.899999999999999, 5, 10, 9.1, 6, 13),
            Block.box(6.899999999999999, 5, 12, 9.1, 6, 15),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(5.5, 1.5, 5.5, 6.5, 4.5, 6.5),
            Block.box(7, 1.5, 4.5, 8, 4.5, 5.5),
            Block.box(8, 1.5, 3.5, 9, 4.5, 4.5),
            Block.box(9.5, 1.5, 5.5, 10.5, 4.5, 6.5),
            Block.box(9.5, 1.5, 9.5, 10.5, 4.5, 10.5),
            Block.box(5.5, 1.5, 9.5, 6.5, 4.5, 10.5),
            Block.box(4.699999999999999, 5.2, 6.9, 6.199999999999999, 7.2, 9.1),
            Block.box(4.699999999999999, 5.2, 6.9, 6.199999999999999, 7.2, 9.1),
            Block.box(9.8, 5.2, 6.9, 11.3, 7.2, 9.1),
            Block.box(9.8, 5.2, 6.9, 11.3, 7.2, 9.1),
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

}
