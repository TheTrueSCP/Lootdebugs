package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModTags;

public class ShieldEntity extends ThrowableProjectile
{
    public ShieldEntity(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public ShieldEntity( LivingEntity p_37463_, Level p_37464_) {
        super(ModEntities.SHIELD_ENTITY.get(), p_37463_, p_37464_);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isInLava())
        {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        Vec3 vec3 = hitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        Level level = this.getLevel();

        if(level.getBlockState(hitResult.getBlockPos()).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
        {
            level.setBlock(hitResult.getBlockPos(), ModBlocks.SHIELD_EMITTER_BLOCK.get().defaultBlockState(),2);
        }
        else if(level.getBlockState(hitResult.getBlockPos().above(1)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
        {
            level.setBlock(hitResult.getBlockPos().above(1), ModBlocks.SHIELD_EMITTER_BLOCK.get().defaultBlockState(),2);
        }
        else if(level.getBlockState(hitResult.getBlockPos().below(1)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
        {
            level.setBlock(hitResult.getBlockPos().below(1), ModBlocks.SHIELD_EMITTER_BLOCK.get().defaultBlockState(),2);
        }
        else
        {
            Vec3 pPos = this.position();
            ItemEntity Shield = new ItemEntity(level, pPos.x(),pPos.y(),pPos.z(),new ItemStack(ModItems.SHIELD.get(),1));
            level.addFreshEntity(Shield);
        }

        this.kill();


    }
    
    @Override
    protected void defineSynchedData() {
        
    }
}
