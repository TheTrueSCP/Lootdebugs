package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;

public class ZiplineTile extends BlockEntity
{
    private BlockPos linkedPos;

    public BlockPos getLinkedPos() {
        return linkedPos;
    }

    public void setLinkedPos(BlockPos linkedPos) {
        this.linkedPos = linkedPos;
    }

    public ZiplineTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.ZIPLINE_ENTITY.get(), pWorldPosition, pBlockState);
    }
}
