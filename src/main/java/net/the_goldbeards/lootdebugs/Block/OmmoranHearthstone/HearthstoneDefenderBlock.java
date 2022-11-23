package net.the_goldbeards.lootdebugs.Block.OmmoranHearthstone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.HearthstoneDefender.HearhstoneDefenderTile;
import org.jetbrains.annotations.Nullable;

public class HearthstoneDefenderBlock extends BaseEntityBlock
{
    public HearthstoneDefenderBlock(Properties p_49795_)
    {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HearhstoneDefenderTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof HearhstoneDefenderTile BE) {
                    BE.tick(lvl, pos, blockState, BE);
                }
            };
        }
    }
}
