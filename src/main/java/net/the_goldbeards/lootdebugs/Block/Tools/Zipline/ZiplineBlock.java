package net.the_goldbeards.lootdebugs.Block.Tools.Zipline;

import net.minecraft.advancements.critereon.StartRidingTrigger;
import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.Zipline.ZiplineBlockTile;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ZiplineBlock extends BaseEntityBlock
{
    public BlockPos linkedBlockPos = new BlockPos(10, 10, 10);

    public boolean moving = false;
    public UUID lastPlayer;

    public ZiplineBlock(Properties p_49224_)
    {
        super(p_49224_);
    }

    public void setConnectedBlocks(BlockPos linkedBlockPos)
    {
        this.linkedBlockPos = linkedBlockPos;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        moving = true;
        this.lastPlayer = pPlayer.getUUID();
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ZiplineBlockTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof ZiplineBlockTile BE) {
                    BE.tick(pLevel, pState, pBlockEntityType);
                }
            };
        }
    }
}
