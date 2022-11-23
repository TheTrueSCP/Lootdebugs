package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.init.ModEntities;

import javax.annotation.Nullable;

public class FlareEntity extends AbstractShootablePhysicsArrowLikeEntity {


    public FlareEntity(EntityType<? extends FlareEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FlareEntity(LivingEntity pShooter, Level world) {
        super(ModEntities.FLARE.get(),pShooter,world);
    }

    @Nullable
    private BlockPos tryToSetFlareAroundBlock(Level pLevel, BlockPos pPos)
    {



        if (pLevel.isEmptyBlock(pPos))  {
            pLevel.setBlock(pPos, Blocks.LIGHT.defaultBlockState(), 2);
            return pPos;
        }

        else if (pLevel.isEmptyBlock(pPos.below(1)))  {
            pLevel.setBlock(pPos.below(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.below(1);
        }

        else if (pLevel.isEmptyBlock(pPos.above(1)))
        {
            pLevel.setBlock(pPos.above(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.above(1);
        }

        else if (pLevel.isEmptyBlock(pPos.north(1)))
        {
            pLevel.setBlock(pPos.north(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.north(1);
        }

        else if (pLevel.isEmptyBlock(pPos.east(1)))
        {
            pLevel.setBlock(pPos.east(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.east(1);
        }

        else if (pLevel.isEmptyBlock(pPos.south(1)))
        {
            pLevel.setBlock(pPos.south(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.south(1);
        }

        else if (pLevel.isEmptyBlock(pPos.west(1)))
        {
            pLevel.setBlock(pPos.west(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.west(1);
        }

        else
        {
            this.kill();
            return null;
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult)
    {
        super.onHitBlock(hitResult);
        tryToSetFlareAroundBlock(level, hitResult.getBlockPos());

    }
    @Override
    public boolean isOnGround() {
        return super.isOnGround();
    }
}





