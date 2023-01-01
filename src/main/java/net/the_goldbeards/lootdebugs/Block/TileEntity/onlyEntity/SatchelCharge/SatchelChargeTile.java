package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import org.jetbrains.annotations.Nullable;

import static net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge.SatchelChargeBlock.ACTIVATED;

public class SatchelChargeTile extends BlockEntity {

    private int count = 0;

    public SatchelChargeTile( BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.SATCHEL_CHARGE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick()
    {
        count++;

        if(count >= 5)
        {
            switchActivated(this.getBlockState());
            count = 0;
        }
    }

    private void switchActivated(BlockState pState)
    {
        boolean currentState = pState.getValue(ACTIVATED);
        level.setBlock(this.getBlockPos(), pState.setValue(ACTIVATED, !currentState),3);
    }

    public void detonate(@Nullable LivingEntity entity)
    {
        SatchelChargeBlock.explode(level, this.getBlockPos(), entity);
    }


}
