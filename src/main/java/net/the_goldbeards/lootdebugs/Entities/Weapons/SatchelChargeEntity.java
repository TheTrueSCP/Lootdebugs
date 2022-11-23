package net.the_goldbeards.lootdebugs.Entities.Weapons;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Block.Weapons.SatchelCharge.SatchelChargeBlock;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class SatchelChargeEntity extends ThrowableProjectile
{
    public SatchelChargeEntity(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    protected SatchelChargeEntity(EntityType<? extends ThrowableProjectile> p_37462_, LivingEntity p_37463_, Level p_37464_) {
        super(p_37462_, p_37463_, p_37464_);
    }

    public SatchelChargeEntity(LivingEntity pShooter, Level p_37464_) {
        super(ModEntities.SATCHEL_CHARGE.get(), pShooter, p_37464_);
    }


    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        Vec3 vec3 = hitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        Level level = this.getLevel();

        BlockPos pos = hitResult.getBlockPos();

        tryToSetSatchelChargeAroundBlock(level, hitResult.getBlockPos());



        if(level.getBlockState(hitResult.getBlockPos()).canSurvive(level, pos) && level.getBlockState(hitResult.getBlockPos()).isAir() && level.getBlockState(hitResult.getBlockPos()).isCollisionShapeFullBlock(level, pos))
        {
            level.setBlock(hitResult.getBlockPos(), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true),2);
        }
        else if(level.getBlockState(hitResult.getBlockPos().above(1)).canSurvive(level, pos) && level.getBlockState(hitResult.getBlockPos().above(1)).isAir()&& level.getBlockState(hitResult.getBlockPos()).isCollisionShapeFullBlock(level, pos))
        {
            level.setBlock(hitResult.getBlockPos().above(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true),2);
        }
        else if(level.getBlockState(hitResult.getBlockPos().below(1)).canSurvive(level, pos) && level.getBlockState(hitResult.getBlockPos().below(1)).isAir()&& level.getBlockState(hitResult.getBlockPos()).isCollisionShapeFullBlock(level, pos))
        {
            level.setBlock(hitResult.getBlockPos().below(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true),2);
        }
        else
        {
            Vec3 pPos = this.position();
            ItemEntity SatchelCharge = new ItemEntity(level,pPos.x(),pPos.y(),pPos.z(),new ItemStack(ModItems.SATCHEL_CHARGE.get(),1));
            level.addFreshEntity(SatchelCharge);
            this.kill();
        }

        this.kill();



    }

    public static void tryToSetSatchelChargeAroundBlock(Level pLevel, BlockPos pPos)
    {



/*v
        if(pLevel.isEmptyBlock(pPos) && !pLevel.isEmptyBlock(pPos))
        {
            pLevel.setBlock(pPos, ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }
        else if(pLevel.isEmptyBlock(pPos.below(1)) && !pLevel.isEmptyBlock(pPos.below(1)))
        {
            pLevel.setBlock(pPos.below(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }

        else if(pLevel.isEmptyBlock(pPos.below(2)) && !pLevel.isEmptyBlock(pPos.below(2))
        {
            pLevel.setBlock(pPos.below(2), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }
        else if(pLevel.isEmptyBlock(pPos.above(1)) && !pLevel.isEmptyBlock(pPos.above(1)))
        {
            pLevel.setBlock(pPos.above(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }
        else if (pLevel.isEmptyBlock(pPos.north(1)) && !pLevel.isEmptyBlock(pPos.north(1)))
        {
            pLevel.setBlock(pPos.north(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }
        else if (pLevel.isEmptyBlock(pPos.east(1)) && !pLevel.isEmptyBlock(pPos.e))
        {
            pLevel.setBlock(pPos.east(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }
        else if (pLevel.isEmptyBlock(pPos.south(1)) && !pLevel.isEmptyBlock(pPos.below()))
        {
            pLevel.setBlock(pPos.south(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }
        else if (pLevel.isEmptyBlock(pPos.west(1)) && !pLevel.isEmptyBlock(pPos.below()))
        {
            pLevel.setBlock(pPos.west(1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.ACTIVATED, true), 2);
        }
        else
        {
            ItemEntity DROP = new ItemEntity(pLevel,pPos.getX(),pPos.getY(),pPos.getZ(),new ItemStack(ModItems.SATCHEL_CHARGE.get(),1));
            pLevel.addFreshEntity(DROP);
        }*/

    }

    @Override
    protected void defineSynchedData() {

    }
}
