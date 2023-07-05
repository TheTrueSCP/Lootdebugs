package net.the_goldbeards.lootdebugs.Block.OmmoranHeartstone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.HeartstoneDefender.HeartstoneDefenderTile;
import org.jetbrains.annotations.Nullable;

public class HeartstoneDefenderBlock extends BaseEntityBlock
{


    public HeartstoneDefenderBlock(Properties p_49795_)
    {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HeartstoneDefenderTile(pPos, pState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof HeartstoneDefenderTile BE) {
                    BE.tick(lvl, pos, blockState, BE);
                }
            };
        }
    }
}
