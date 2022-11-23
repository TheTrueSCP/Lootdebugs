package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.LightBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;

public class LightBlockTile extends BlockEntity {

    private int time = 0;
    public LightBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.LIGHT_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick()
    {
        time++;

        if(time ==180)
        {
            BlockPos pos = this.getBlockPos();
            Level level = this.getLevel();

            level.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
        }



    }
}
