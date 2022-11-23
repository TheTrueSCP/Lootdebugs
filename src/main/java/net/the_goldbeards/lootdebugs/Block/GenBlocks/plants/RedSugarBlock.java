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
import net.the_goldbeards.lootdebugs.init.ModBlocks;

public class RedSugarBlock extends BushBlock {

    public RedSugarBlock(Properties properties) {
        super(properties);
    }

    public static final VoxelShape SHAPE = Shapes.or(
            Block.box(1.5, 0, 1.25, 14.5, 16, 14.75),
            Block.box(-1, 2, 10, 2, 4, 12),
            Block.box(6, 1, 14.25, 8, 3, 17.25),
            Block.box(5, 11, 14.25, 7, 13, 17.25),
            Block.box(3, 6, 14.25, 5, 8, 17.25),
            Block.box(7, 7, 14.25, 9, 9, 17.25),
            Block.box(10, 12, 14.25, 12, 14, 17.75),
            Block.box(10, 3, 14.25, 12, 5, 17.25),
            Block.box(9, 11, -1.25, 11, 13, 1.75),
            Block.box(4, 12, -1.75, 6, 14, 1.75),
            Block.box(7, 7, -1.25, 9, 9, 1.75),
            Block.box(11, 6, -1.25, 13, 8, 1.75),
            Block.box(8, 1, -1.25, 10, 3, 1.75),
            Block.box(4, 3, -1.25, 6, 5, 1.75),
            Block.box(-1, 13, 11, 2, 15, 13),
            Block.box(-1.25, 11, 4, 2, 13, 6),
            Block.box(-1.25, 3, 3, 2, 5, 5),
            Block.box(-1.5, 8, 9, 2, 10, 11),
            Block.box(14, 13, 3, 17, 15, 5),
            Block.box(14, 11, 10, 17.25, 13, 12),
            Block.box(14, 8, 5, 17.5, 10, 7),
            Block.box(14, 3, 11, 17.25, 5, 13),
            Block.box(14, 2, 4, 17, 4, 6)
    );

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
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
                state.is(ModBlocks.RED_SUGAR.get());
    }
}
