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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Items.Tools.Turret.TurretAmmoItem;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.util.Optional;
import java.util.function.Predicate;

public class TurretEntity extends Entity
{
    public static int defaultAmmo = 200;

    private static final EntityDataAccessor<Integer> AMMO = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Optional<BlockPos>> TARGET_POS = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

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

    public TurretEntity( Level pLevel) {
        super(ModEntities.TURRET_ENTITY.get(), pLevel);
    }

    protected void defineSynchedData()
    {
        this.entityData.define(AMMO, 0);
        this.entityData.define(TARGET_POS, Optional.empty());
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        if(pPlayer.getItemInHand(pHand).is(ModItems.TURRET_AMMO.get())) {

            float ammoItemAmount = ModUtils.ItemNBTHelper.getFloat(pPlayer.getItemInHand(pHand), "ammoAmount");

            if(ammoItemAmount >= 10 || pPlayer.isCreative())
            {
                addAmmo(10);

                if(!pPlayer.isCrouching())
                {
                    TurretAmmoItem.ShrinkAmmo(pPlayer.getItemInHand(pHand), 10);
                }
                return InteractionResult.SUCCESS;
            }
            else if (pPlayer.getItemInHand(pHand).is(ModItems.TURRET_AMMO.get()))
            {
                addAmmo(1);

                if(!pPlayer.isCrouching())
                {
                    TurretAmmoItem.ShrinkAmmo(pPlayer.getItemInHand(pHand), 1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }


    public void tick() {
        super.tick();
        if (!this.level.isClientSide)
        {

            LivingEntity target = level.getNearestEntity(level.getEntitiesOfClass(LivingEntity.class, getSearchBound(this.blockPosition())), ENEMY_TARGETING, null, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());

            this.setCustomName(new TextComponent("" + getAmmo()));
            this.setCustomNameVisible(true);

            if (shootCooldown <= 0 && target != null)
            {
                this.entityData.set(TARGET_POS, Optional.of(target.blockPosition()));
                shootCooldown = shootCooldownTicks;

                if(canShoot())
                {
                    BulletEntity bulletEntity = new BulletEntity(level);
                    bulletEntity.setPos(this.position().x, this.position().y + 1f, this.position().z);

                    Vec3 shootVec = new Vec3((bulletEntity.getX() - target.getX()) * -1, (bulletEntity.getY() - target.getY()) * -1, (bulletEntity.getZ() - target.getZ()) * -1);//calculate Movement from Entity to hook

                    level.addFreshEntity(bulletEntity);

                    level.playSound(null, this.blockPosition(), SoundEvents.CROSSBOW_SHOOT, SoundSource.NEUTRAL, 1, 1);

                    bulletEntity.setDeltaMovement(bulletEntity.getDeltaMovement().add(shootVec));//Normalize Vec cause the vec would be to fast

                    shrinkAmmo(1);
                }
            }

            if(target == null)
            {
                this.entityData.set(TARGET_POS, Optional.empty());
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
        setAmmo(pCompound.getInt("ammo"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.putInt("ammo", getAmmo());
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if(pSource.getEntity() instanceof Player player)
        {
            ItemStack itemInHand = player.getMainHandItem();

            if(ModUtils.DwarfClasses.canPlayerDo(ModUtils.DwarfClasses.getPlayerClass(player), IClassData.Classes.Engineer) && itemInHand.is(ModItems.TURRET_WRENCH.get()))
            {
                ItemStack turretItemStack = new ItemStack(ModItems.TURRET.get(), 1);

                ModUtils.ItemNBTHelper.putInt(turretItemStack, "ammo", getAmmo());

                ItemEntity turretItemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), turretItemStack);

                this.level.addFreshEntity(turretItemEntity);

                this.discard();
            }
        }

        return false;
    }

    public float getTurretXRot()
    {
        Optional<BlockPos> targetBlockPosOptional = this.entityData.get(TARGET_POS);

        if(targetBlockPosOptional.isPresent())
        {
            BlockPos targetBlockPos = targetBlockPosOptional.get();

            Optional<Float> xRotOptional = getXRotFromPos(targetBlockPos);
            if(xRotOptional.isPresent())
            {
                System.out.println(6.30f/360 * xRotOptional.get());
                return 6.30f/360 * xRotOptional.get();
            }
        }
        return 0;
    }

    public float getTurretYRot()
    {
        Optional<BlockPos> targetBlockPosOptional = this.entityData.get(TARGET_POS);

        if(targetBlockPosOptional.isPresent())
        {
            BlockPos targetBlockPos = targetBlockPosOptional.get();


            System.out.println(getYRotFromPosWithRotation(targetBlockPos));
            // return 6.30f/360 * getYRotFromPosWithRotation(targetBlockPos);
            return 0;
        }
        return 4f;
    }


    public Optional<Float> getXRotFromPos(BlockPos lookPos) {
        double d0 = lookPos.getX() - this.getX();
        double d1 = lookPos.getY() - this.getY();
        double d2 = lookPos.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return !(Math.abs(d1) > (double)1.0E-5F) && !(Math.abs(d3) > (double)1.0E-5F) ? Optional.empty() : Optional.of((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI))));
    }

    public Optional<Float> getYRotFromPos(BlockPos lookPos)
    {
        double d0 = lookPos.getX() - this.getX();
        double d1 = lookPos.getZ() - this.getZ();
        return !(Math.abs(d1) > (double)1.0E-5F) && !(Math.abs(d0) > (double)1.0E-5F) ? Optional.empty() : Optional.of((float)(-Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) + 90f);
    }

    public float getYRotFromPosWithRotation(BlockPos lookPos)
    {
        float turretRotPositive = Math.abs(this.getXRot());
        Optional<Float> optionalYRotFromPos = getYRotFromPos(lookPos);

        if(optionalYRotFromPos.isPresent())
        {
            System.out.println("turretRotpos" + turretRotPositive);
            float yRotFromPos = optionalYRotFromPos.get();
            if(yRotFromPos < 0)
            {
                return yRotFromPos + (turretRotPositive * -1);
            }
            else if(yRotFromPos > 0)
            {
                return yRotFromPos + turretRotPositive;
            }
        }
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
