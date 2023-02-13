package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;

import javax.swing.text.html.parser.Entity;

public class ZiplineTile extends BlockEntity
{
    private ZiplineEntity linkedEntity;

    private ZiplineStringAnchor thisAnchor;

    public ZiplineStringAnchor getThisAnchor() {
        return thisAnchor;
    }

    public void setThisAnchor(ZiplineStringAnchor thisAnchor) {
        this.thisAnchor = thisAnchor;
    }

    public ZiplineTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.ZIPLINE_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void setLinkedEntity(ZiplineEntity entity)
    {
        this.linkedEntity = entity;
    }

    public ZiplineEntity getLinkedEntity()
    {
        return linkedEntity;
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, ZiplineTile e)
    {
        if(!blockState.canSurvive(level, pos))
        {
            ZiplineBlock.removeZipline(level, pos, blockState);
        }
    }
}
