package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.Block.Tools.Zipline.ZiplineBlock;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;

import javax.annotation.Nullable;

public class ZiplineEntity extends AbstractShootablePhysicsArrowLikeEntity
{
    BlockPos otherBlockPos;

    public ZiplineEntity(LivingEntity shooter, Level level, @Nullable BlockPos otherBlockPos)
    {
        super(ModEntities.ZIPLINE_ENTITY.get(), shooter, level);
        this.otherBlockPos = otherBlockPos;
    }

    public ZiplineEntity(EntityType<ZiplineEntity> ziplineEntityEntityType, Level level)
    {
        super(ziplineEntityEntityType, level);
    }

    public void setPos(BlockPos pos)
    {
        this.otherBlockPos = pos;
    }

    @Override
    protected void defineSynchedData()
    {

    }

    @Override
    protected void onHitBlock(BlockHitResult result)
    {
        BlockPos thisPos = result.getBlockPos();
        this.level.setBlock(thisPos, ModBlocks.ZIPLINE_BLOCK.get().defaultBlockState(), 3);

        if(this.level.getBlockState(thisPos).getBlock() instanceof ZiplineBlock ziplineBlock)
        {
            ziplineBlock.setConnectedBlocks(otherBlockPos);//this block
        }

        if(this.level.getBlockState(otherBlockPos).getBlock() instanceof ZiplineBlock ziplineBlock)
        {
            ziplineBlock.setConnectedBlocks(thisPos);//set others block link blockpos

        }
        super.onHitBlock(result);
    }
}
