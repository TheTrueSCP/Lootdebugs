package net.the_goldbeards.lootdebugs.Block.Tools.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.Zipline.ZiplineBlockTile;
import org.jetbrains.annotations.Nullable;

public class ZiplineBlock extends BaseEntityBlock
{
    public int connectedBlockX = 10;
    public int connectedBlockY = 10;
    public int connectedBlockZ = 10;


    public boolean moving = false;
    public Player player;

    public ZiplineBlock(Properties p_49224_)
    {
        super(p_49224_);
    }

    public void setConnectedBlocks(int x, int y, int z) {
        this.connectedBlockX = x;
        this.connectedBlockY = y;
        this.connectedBlockZ = z;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        moving = true;
        this.player = pPlayer;




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
                    BE.tick();
                }
            };
        }
    }
}
