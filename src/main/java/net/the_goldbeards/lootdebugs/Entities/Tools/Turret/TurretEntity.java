package net.the_goldbeards.lootdebugs.Entities.Tools.Turret;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class TurretEntity extends Entity 
{

    private int ammoAmount = 0;
    private int shootDelay = 0;

    private static int searchRadiusInBlocks = 5;
    private static final Predicate<LivingEntity> ENEMY_PREDECATE = (p_30636_) -> {
        return p_30636_ instanceof Monster;
    };
    private static final TargetingConditions ENEMY_TARGETING = TargetingConditions.forCombat().range(searchRadiusInBlocks).ignoreLineOfSight().selector(ENEMY_PREDECATE);

    public TurretEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    protected void defineSynchedData()
    {

    }

    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        double d3 = vec3.horizontalDistance();

        this.setXRot(lerpRotation(this.xRotO, (float) (Mth.atan2(vec3.y, d3) * (double) (180F / (float) Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI))));

        if (!this.level.isClientSide)
        {
            if(/*targetPos != null*/true)
            {
                Monster nearestEnemy = level.getNearestEntity(level.getEntitiesOfClass(Monster.class, getSearchBound(this.blockPosition())), ENEMY_TARGETING, null, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());
                if(nearestEnemy != null)
                {

                    shootDelay++;
                    if(shootDelay >= 5)
                    {
                        BulletEntity bulletEntity = new BulletEntity(level, nearestEnemy.blockPosition());
                        //bulletEntity.shootFromRotation(this, this.r);
                        level.addFreshEntity(bulletEntity);
                        level.playSound(null, this.blockPosition(), SoundEvents.CROSSBOW_SHOOT, SoundSource.BLOCKS, 5, 1);
                        shootDelay = 0;
                    }
                }

            }
        }
        else
        {
            this.setPosRaw(d0, d1, d2);
        }

    }
    
    private static AABB getSearchBound(BlockPos pos)
    {
        return new AABB(pos.getX() - searchRadiusInBlocks, pos.getY() - searchRadiusInBlocks, pos.getZ() - searchRadiusInBlocks, pos.getX() + searchRadiusInBlocks, pos.getY() + searchRadiusInBlocks, pos.getZ() + searchRadiusInBlocks);
    }
    
    //Entity Stuff

    
    public void lerpMotion(double pX, double pY, double pZ) {
        this.setDeltaMovement(pX, pY, pZ);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = Math.sqrt(pX * pX + pZ * pZ);
            this.setYRot((float) (Mth.atan2(pX, pZ) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(pY, d0) * (double) (180F / (float) Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    public float getBrightness() {
        return 1.0F;
    }

    public boolean isAttackable() {
        return false;
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while(p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while(p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.2F, p_37274_, p_37275_);
    }
}
