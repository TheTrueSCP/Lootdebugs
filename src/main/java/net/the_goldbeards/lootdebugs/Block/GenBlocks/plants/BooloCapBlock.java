package net.the_goldbeards.lootdebugs.Block.GenBlocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.init.ModBlocks;

public class BooloCapBlock extends BushBlock {

    public BooloCapBlock(Properties properties) {
        super(properties);
    }
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(7, 0, 7, 9, 19, 9),
            Block.box(7, 19, 7, 9, 20, 9),
            Block.box(7, 17, 6, 9, 19, 7),
            Block.box(6, 13, 5, 10, 17, 6),
            Block.box(6, 12, 4, 10, 13, 5),
            Block.box(6, 12, 11, 10, 13, 12),
            Block.box(4, 12, 6, 5, 13, 10),
            Block.box(11, 12, 6, 12, 13, 10),
            Block.box(12, 11, 6, 12, 12, 10),
            Block.box(4, 11, 6, 4, 12, 10),
            Block.box(6, 11, 12, 10, 12, 12),
            Block.box(6, 11, 4, 10, 12, 4),
            Block.box(6, 13, 10, 10, 17, 11),
            Block.box(5, 13, 6, 6, 17, 10),
            Block.box(10, 13, 6, 11, 17, 10),
            Block.box(9, 17, 6, 10, 18, 7),
            Block.box(9, 17, 9, 10, 18, 10),
            Block.box(10, 12, 10, 11, 13, 12),
            Block.box(11, 12, 10, 12, 13, 11),
            Block.box(5, 12, 10, 6, 13, 12),
            Block.box(4, 12, 10, 5, 13, 11),
            Block.box(5, 12, 4, 6, 13, 6),
            Block.box(4, 12, 5, 5, 13, 6),
            Block.box(10, 12, 4, 11, 13, 6),
            Block.box(11, 12, 5, 12, 13, 6),
            Block.box(10, 13, 5, 11, 14, 6),
            Block.box(5, 13, 5, 6, 14, 6),
            Block.box(5, 13, 10, 6, 14, 11),
            Block.box(10, 13, 10, 11, 14, 11),
            Block.box(6, 17, 9, 7, 18, 10),
            Block.box(6, 17, 6, 7, 18, 7),
            Block.box(7, 17, 9, 9, 19, 10),
            Block.box(9, 17, 7, 10, 19, 9),
            Block.box(6, 17, 7, 7, 19, 9),
            Block.box(10.9, 11, 9.5, 10.9, 12, 12.5),
            Block.box(5.1, 11, 9.5, 5.1, 12, 12.5),
            Block.box(5.1, 11, 3.5, 5.1, 12, 6.5),
            Block.box(10.9, 11, 3.5, 10.9, 12, 6.5)
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
                state.is(ModBlocks.BOOLO_CAP.get()) ||
                state.is(Blocks.GRANITE);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {

        if(pLevel.getBlockState(pPos.below()).is(ModBlocks.BOOLO_CAP.get()))
        {
            return true;
        }

        return super.canSurvive(pState, pLevel, pPos);
    }
}
