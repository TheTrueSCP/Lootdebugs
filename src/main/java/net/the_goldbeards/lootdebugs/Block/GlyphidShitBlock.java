package net.the_goldbeards.lootdebugs.Block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlyphidShitBlock extends Block {

    public GlyphidShitBlock(Properties properties) {
        super(properties);
    }

    public static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 5, 16),
            Block.box(6, 6, 6, 10, 10, 10),
            Block.box(3, 4, 3, 13, 8, 13),
            Block.box(7, 8, 8, 16, 17, 8)
    );

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }




}
