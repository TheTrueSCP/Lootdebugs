package net.the_goldbeards.lootdebugs.Entities.Tools.Turret;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;

public class BulletEntity extends Projectile
{
    private BlockPos targetPos;


    public BulletEntity(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    public BulletEntity(Level pLevel, BlockPos targetPos) {
        super(ModEntities.BULLET_ENTITY.get(), pLevel);
        this.targetPos = targetPos;
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
            if(targetPos != null)
            {
                vec3 = new Vec3((this.blockPosition().getX() - targetPos.getX()) * -1, (this.blockPosition().getY() - targetPos.getY()) * -1, (this.blockPosition().getZ() - targetPos.getZ()) * -1);
                this.setDeltaMovement(vec3.normalize().multiply(3, 3, 3));

                this.setPos(d0, d1, d2);
                if (!this.level.isClientSide && (this.blockPosition().closerThan(targetPos, 1D))) {
                    this.discard();
                }
            }
        }
        else
        {
            this.setPosRaw(d0, d1, d2);
        }

    }


    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        pResult.getEntity().hurt(ModDamageSources.BULLET, 5);
        super.onHitEntity(pResult);
    }

    //NBT
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.put("TargetPos", NbtUtils.writeBlockPos(this.targetPos));
    }

    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        if (pCompound.contains("TargetPos"))
        {
            this.targetPos = NbtUtils.readBlockPos(pCompound.getCompound("TargetPos"));
        }
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
