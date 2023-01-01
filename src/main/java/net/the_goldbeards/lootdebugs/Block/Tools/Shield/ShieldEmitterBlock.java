package net.the_goldbeards.lootdebugs.Block.Tools.Shield;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Shield.ShieldEmitterBlockTile;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class ShieldEmitterBlock extends BaseEntityBlock {


    public static final VoxelShape SHAPE = Shapes.or(
            Block.box(4, 2.75, 4, 12, 3.75, 12),
            Block.box(3.5, 2.25, 3.5, 12.5, 3.25, 12.5),
            Block.box(3.5, 0.5, 3.5, 12.5, 1.5, 12.5),
            Block.box(4, 0, 4, 12, 1, 12),
            Block.box(3.75, 1.25, 3.75, 12.25, 2.25, 12.25),
            Block.box(7, -0.25, 7, 9, 4, 9),
            Block.box(6, -0.5, 6, 9, 4.25, 7),
            Block.box(7, -0.5, 9, 10, 4.25, 10),
            Block.box(9, -0.5, 6, 10, 4.25, 9),
            Block.box(6, -0.5, 7, 7, 4.25, 10),
            Block.box(7.25, 1.15, 3, 8.75, 2.6, 4),
            Block.box(3, 1.1500000000000004, 7.25, 4, 2.5999999999999996, 8.75),
            Block.box(7.25, 1.1500000000000004, 12, 8.75, 2.5999999999999996, 13),
            Block.box(12, 1.15, 7.25, 13, 2.6, 8.75),
            Block.box(10.12157287525381, -0.25, 10.078427124746192, 11.12157287525381, 4, 11.078427124746192),
            Block.box(4.92157287525381, -0.25, 10.078427124746192, 5.92157287525381, 4, 11.078427124746192),
            Block.box(4.92157287525381, -0.25, 4.921572875253808, 5.92157287525381, 4, 5.921572875253808),
            Block.box(10.12157287525381, -0.25, 4.921572875253808, 11.12157287525381, 4, 5.921572875253808),
            Block.box(10.75, -0.10000000000000009, 7.35, 12.75, 3.85, 8.65),
            Block.box(3.25, -0.10000000000000009, 7.35, 5.25, 3.85, 8.65),
            Block.box(7.35, -0.10000000000000009, 10.75, 8.65, 3.85, 12.75),
            Block.box(7.35, -0.10000000000000009, 3.25, 8.65, 3.85, 5.25)
            );


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public ShieldEmitterBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {


        for (int x = -4; x < 4; x++) {
            for (int y = -4; y < 4; y++) {
                for (int z = -4; z < 4; z++) {
                    BlockPos testpos = new BlockPos(pPos.getX() + (x),pPos.getY() + (y), pPos.getZ() + (z));
                    if (pLevel.getBlockState(testpos).is(ModBlocks.SHIELD_BLOCK.get())) {
                       pLevel.setBlock(testpos, Blocks.AIR.defaultBlockState(), 3);

                    }
                }
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ShieldEmitterBlockTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof ShieldEmitterBlockTile BE) {
                    BE.tick();
                }
            };
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {

        return pLevel.getBlockState(pPos.below()).isCollisionShapeFullBlock(pLevel, pPos.below());
    }
}

