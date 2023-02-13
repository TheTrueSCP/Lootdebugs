package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Shield;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;

public class ShieldBlockTile extends BlockEntity {

    private int time = 0;
    public ShieldBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.SHIELD_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick()
    {
        BlockPos pPos = this.getBlockPos();
        Level pLevel = this.getLevel();

        time++;

        if(time >= 180)
        {

            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(),3);
        }

    }
}
