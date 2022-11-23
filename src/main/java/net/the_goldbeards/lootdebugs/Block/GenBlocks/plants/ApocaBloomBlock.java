package net.the_goldbeards.lootdebugs.Block.GenBlocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class ApocaBloomBlock extends BushBlock {
    public ApocaBloomBlock(Properties properties) {
        super(properties);
    }
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(-3, 18, 4, 8, 18, 12),
            Block.box(8, 18, 4, 19, 18, 12),
            Block.box(4, 18, 8, 12, 18, 19),
            Block.box(4, 18, -3, 12, 18, 8),
            Block.box(4, 22.207748825332708, -11.146218157458815, 12, 22.207748825332708, -2.146218157458815),
            Block.box(-11.163, 22.207748825332708, 4, -2.163000000000003, 22.207748825332708, 12),
            Block.box(18.157, 22.209, 4, 27.156999999999996, 22.209, 12),
            Block.box(4, 22.207748825332708, 18.146218157458815, 12, 22.207748825332708, 27.146218157458815),
            Block.box(6, 0, 4, 10, 10, 12),
            Block.box(6, 10, 6, 10, 18, 10),
            Block.box(4, 0, 6, 6, 10, 10),
            Block.box(10, 0, 6, 12, 10, 10),
            Block.box(10, 18, 2, 10, 30, 14),
            Block.box(6, 18, 2, 6, 30, 14),
            Block.box(2, 18, 6, 14, 30, 6),
            Block.box(2, 18, 10, 14, 30, 10),
            Block.box(2, 0, 8, 14, 18, 8),
            Block.box(8, 0, 2, 8, 18, 14),
            Block.box(8, 0, 2, 8, 18, 14),
            Block.box(2, 0, 8, 14, 18, 8)
            );

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
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
                state.is(Blocks.GRAVEL) ||
                state.is(Blocks.ANDESITE) ||
                state.is(Blocks.GRANITE);
    }



}


