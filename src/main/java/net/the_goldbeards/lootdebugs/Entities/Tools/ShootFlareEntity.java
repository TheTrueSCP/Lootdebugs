package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;

import javax.annotation.Nullable;

public class ShootFlareEntity extends AbstractShootablePhysicsArrowLikeEntity {

    private BlockPos hitPos;

    public ShootFlareEntity(EntityType<? extends ShootFlareEntity> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public ShootFlareEntity( LivingEntity pShooter, Level pLevel) {
        super(ModEntities.SHOOT_FLARE.get(), pShooter, pLevel);
    }



    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {




        if(pResult.getEntity() != null)
        {
            if (pResult.getEntity() != this.getOwner()) {
                pResult.getEntity().hurt(DamageSource.GENERIC, 2);
                Vec3 pPos = this.position();
                ItemEntity SatchelCharge = new ItemEntity(level,pPos.x(),pPos.y(),pPos.z(),new ItemStack(ModItems.SHOOT_FLARE.get(),1));
                level.addFreshEntity(SatchelCharge);
                this.kill();
            }
        }


        super.onHitEntity(pResult);




    }


    @Override
    public void tick() {


        if(this.getOwner() != null)
        {
            if(this.getOwner().position().distanceTo(this.position()) >= 40)
            {
                this.kill();
            }
        }

        if(hitPos != null && !this.inGround)
        {
           this.level.setBlock(hitPos, Blocks.AIR.defaultBlockState(),2);
        }


        super.tick();
    }

    @Override
    public void kill() {

        if(hitPos != null)
        {
            this.level.setBlock(hitPos, Blocks.AIR.defaultBlockState(), 2);
        }

        super.kill();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult)
    {
        super.onHitBlock(hitResult);
        Vec3 vec3 = hitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);

        this.inGround = true;
        this.position();

        Level level = this.getLevel();
        BlockPos pos = hitResult.getBlockPos();
       this.hitPos = tryToSetFlareAroundBlock(level, pos);
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



}
