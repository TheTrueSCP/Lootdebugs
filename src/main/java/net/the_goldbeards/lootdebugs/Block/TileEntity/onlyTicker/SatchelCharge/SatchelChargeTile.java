package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.SatchelCharge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.Block.Weapons.SatchelCharge.SatchelChargeBlock;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;

public class SatchelChargeTile extends BlockEntity {

    private int count = 0;
    public static int frequence;
    public SatchelChargeTile( BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.SATCHEL_CHARGE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick()
    {
/*
        Level level = this.getLevel();
        BlockPos blockPos = this.getBlockPos();

        if(level.getBlockState(blockPos).is(ModBlocks.SATCHEL_CHARGE.get()))
        {
            if(frequence == level.getBlockState(blockPos).getValue(SatchelChargeBlock.FREQUENCE))
            {
                SatchelChargeBlock.explode(this.worldPosition,this.level);
            }

        }
*/


        if(level.getBlockState(this.worldPosition).getValue(SatchelChargeBlock.ACTIVATED))
        {
            count++;
            if(count >= 100)
            {
                SatchelChargeBlock.explode(this.worldPosition,this.level);
            }
        }
        else
        {
            count = 0;
        }
    }


}
