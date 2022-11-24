package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Block.Tools.Zipline.ZiplineBlock;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import org.lwjgl.system.CallbackI;

public class ZiplineBlockTile extends BlockEntity {

    public ZiplineBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.ZIPLINE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }
    public <T extends BlockEntity> void tick(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {

        if(pState.getBlock() instanceof ZiplineBlock ZB)
        {
            if(ZB.moving && ZB.lastPlayer != null && pLevel.getBlockState(ZB.linkedBlockPos).getBlock() instanceof ZiplineBlock)
            {
                if(level.getPlayerByUUID(ZB.lastPlayer) != null)
                {
                    Vec3 vec3 = new Vec3((ZB.linkedBlockPos.getX() - this.getBlockPos().getX()), (ZB.linkedBlockPos.getY() - this.getBlockPos().getY()), (ZB.linkedBlockPos.getZ() - this.getBlockPos().getZ()));//calculate Movement from Entity to hook
                    System.out.println(vec3);
                    level.getPlayerByUUID(ZB.lastPlayer).setDeltaMovement(vec3.normalize());//Normalize Vec cause the vec would be to fast

                    if(vec3.distanceTo(Vec3.atCenterOf(ZB.linkedBlockPos)) <= 2)
                    {
                        ZB.moving = false;
                        ZB.lastPlayer = null;
                    }
                }
            }

        }
    }
}
