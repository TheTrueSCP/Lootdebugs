package net.the_goldbeards.lootdebugs.Entities.Tools.Turret;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Entities.Tools.PingEntity;
import net.the_goldbeards.lootdebugs.init.ModItems;

import java.util.function.Predicate;

import java.util.function.Predicate;

public class TurretEntity extends Entity
{

    private static final EntityDataAccessor<Integer> AMMO = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.INT);
    private static int searchRadiusInBlocks = 10;
    private static float shootCooldownTicks = 10;

    private float shootCooldown;
    private static final Predicate<LivingEntity> ENEMY_PREDECATE = (p_30636_) -> {
        return p_30636_ instanceof Monster;
    };
    private static final TargetingConditions ENEMY_TARGETING = TargetingConditions.forCombat().range(searchRadiusInBlocks).ignoreLineOfSight().selector(ENEMY_PREDECATE);

    public TurretEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData()
    {
        this.entityData.define(AMMO, 200);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        if(pPlayer.getItemInHand(pHand).is(ModItems.TURRET_AMMO.get()))
        {
            addAmmo(1);
            pPlayer.getItemInHand(pHand).shrink(1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void tick() {
        super.tick();
        if (!this.level.isClientSide)
        {

            LivingEntity target = level.getNearestEntity(level.getEntitiesOfClass(Monster.class, getSearchBound(this.blockPosition())), ENEMY_TARGETING, null, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());

            this.setCustomName(new TextComponent("" + getAmmo()));
            this.setCustomNameVisible(true);

            if (shootCooldown <= 0 && target != null && canShoot())
            {
                shootCooldown = shootCooldownTicks;

                BulletEntity bulletEntity = new BulletEntity(level);
                bulletEntity.setPos(this.position().x, this.position().y + 1f, this.position().z);

                Vec3 shootVec = new Vec3((bulletEntity.getX() - target.getX()) * -1, (bulletEntity.getY() - target.getY()) * -1, (bulletEntity.getZ() - target.getZ()) * -1);//calculate Movement from Entity to hook

                level.addFreshEntity(bulletEntity);

                bulletEntity.setDeltaMovement(bulletEntity.getDeltaMovement().add(shootVec));//Normalize Vec cause the vec would be to fast

                shrinkAmmo(1);
            }

            if(shootCooldown > 0)
            {
                shootCooldown--;
            }

            if(shootCooldown < 0)
            {
                shootCooldown = 0;
            }
            
        }

    }

    private static AABB getSearchBound(BlockPos pos)
    {
        return new AABB(pos.getX() - searchRadiusInBlocks, pos.getY() - searchRadiusInBlocks, pos.getZ() - searchRadiusInBlocks, pos.getX() + searchRadiusInBlocks, pos.getY() + searchRadiusInBlocks, pos.getZ() + searchRadiusInBlocks);
    }

    //Entity Stuff

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.putInt("ammo", getAmmo());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound)
    {
        setAmmo(pCompound.getInt("ammo"));
    }

    public float getTurretRotation(float pPartialTicks, float pOffset)
    {

        return 0;
    }

    public float getBrightness() {
        return 1.0F;
    }

    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean mayInteract(Level pLevel, BlockPos pPos) {
        return true;
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

    public boolean canShoot()
    {
        return getAmmo() >= 1;
    }

    public void addAmmo(int amount)
    {
        this.entityData.set(AMMO, getAmmo() + amount);
    }

    public void shrinkAmmo(int amount)
    {
        if(getAmmo() - amount < 0)
        {
            this.entityData.set(AMMO, 0);
            return;
        }
        this.entityData.set(AMMO, getAmmo() - amount);
    }

    public void setAmmo(int amount)
    {
        this.entityData.set(AMMO, amount);
    }

    public int getAmmo()
    {
        return this.entityData.get(AMMO);
    }
}
