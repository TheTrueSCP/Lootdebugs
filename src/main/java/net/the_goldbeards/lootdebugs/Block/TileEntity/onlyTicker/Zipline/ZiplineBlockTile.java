package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Block.Tools.Zipline.ZiplineBlock;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;

public class ZiplineBlockTile extends BlockEntity {

    public ZiplineBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.ZIPLINE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick()
    {
        if(this.level.getBlockState(getBlockPos()).getBlock() instanceof ZiplineBlock ZB) 
        {
            if(ZB.moving && ZB.player != null)
            {
                Vec3 vec3 = new Vec3((ZB.connectedBlockX - this.getBlockPos().getX()) * -1, (ZB.connectedBlockY - this.getBlockPos().getY()) * -1, (ZB.connectedBlockZ - this.getBlockPos().getZ()) * -1);//calculate Movement from Entity to hook
                ZB.player.setDeltaMovement(ZB.player.getDeltaMovement().add(vec3).normalize());//Normalize Vec cause the vec would be to fast

                if(vec3.distanceTo(new Vec3(ZB.connectedBlockX, ZB.connectedBlockY, ZB.connectedBlockZ)) < 2)
                {
                    ZB.moving = false;
                    ZB.player = null;
                }

            }

        }

        
    }
}
