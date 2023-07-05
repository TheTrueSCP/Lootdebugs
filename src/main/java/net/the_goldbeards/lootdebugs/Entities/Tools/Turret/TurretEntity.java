package net.the_goldbeards.lootdebugs.Entities.Tools.Turret;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
import net.minecraft.world.entity.Mob;
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

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

import static net.the_goldbeards.lootdebugs.util.ModUtils.BlockHelpers.isBlockBetween;

public class TurretEntity extends Entity
{
    public static int defaultAmmo = 200;

    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Engineer;

    private static final EntityDataAccessor<Integer> AMMO = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.INT);

    @Nullable
    private int targetID;

    private static final EntityDataAccessor<Optional<BlockPos>> TARGET_POS = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Boolean> ATTACK_NEUTRAL = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.BOOLEAN);

    private static int searchRadiusInBlocks = 20;
    private static float shootCooldownTicks = 5;

    private Player owner;
    private float shootCooldown;
    private static final Predicate<LivingEntity> ENEMY_PREDECATE = (p_30636_) -> {
        return p_30636_ instanceof Monster;
    };

    private static final Predicate<LivingEntity> ENEMY_AND_NEUTRAL_PREDECATE = (p_30636_) -> {
        return p_30636_ instanceof Mob;
    };

    private static final TargetingConditions ENEMY_TARGETING = TargetingConditions.forCombat().range(searchRadiusInBlocks).selector(ENEMY_PREDECATE);

    private static final TargetingConditions ENEMY_AND_NEUTRAL_TARGETING = TargetingConditions.forCombat().range(searchRadiusInBlocks).selector(ENEMY_AND_NEUTRAL_PREDECATE);

    public TurretEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public TurretEntity(Level pLevel, Player owner)
    {
        super(ModEntities.TURRET_ENTITY.get(), pLevel);
        setOwner(owner);
    }

    protected void defineSynchedData()
    {
        this.entityData.define(AMMO, 0);
        this.entityData.define(TARGET_POS, Optional.empty());
        this.entityData.define(ATTACK_NEUTRAL, true);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        if(pPlayer.getItemInHand(pHand).is(ModItems.TURRET_AMMO.get())) {

            float ammoItemAmount = ModUtils.ItemNBTHelper.getFloat(pPlayer.getItemInHand(pHand), "ammoAmount");

            if(ammoItemAmount >= 10 || pPlayer.isCreative())
            {
                addAmmo(10);

                if(!pPlayer.isCreative())
                {
                    TurretAmmoItem.ShrinkAmmo(pPlayer.getItemInHand(pHand), 10);
                }
                return InteractionResult.SUCCESS;
            }
            else if (pPlayer.getItemInHand(pHand).is(ModItems.TURRET_AMMO.get()))
            {
                addAmmo(1);

                if(!pPlayer.isCreative())
                {
                    TurretAmmoItem.ShrinkAmmo(pPlayer.getItemInHand(pHand), 1);
                }
                return InteractionResult.SUCCESS;
            }
        }
            boolean oldAttackNeutral = getAttackNeutral();
            setAttackNeutral(!oldAttackNeutral);

            System.out.println(getAttackNeutral());

            if (getAttackNeutral())
            {
                pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.turret.attack.neutral"), true);
            } else {
                pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.turret.attack.enemy"), true);
            }


        return InteractionResult.SUCCESS;
    }


    public void tick() {
        super.tick();
        if (!this.level.isClientSide)
        {
            if(getOwner() != null)
            {
                if(!ModUtils.DwarfClasses.isPlayerClass(getOwner(), dwarfClassToUse))
                {
                    this.dropTurret();
                }
            }

            LivingEntity target = level.getNearestEntity(level.getEntitiesOfClass(LivingEntity.class, getSearchBound(this.blockPosition())), getTargeting(), null, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());

            this.setCustomName(new TextComponent("" + getAmmo()));
            this.setCustomNameVisible(true);

            if (shootCooldown <= 0 && target != null)
            {
                this.entityData.set(TARGET_POS, Optional.of(target.blockPosition()));
                shootCooldown = shootCooldownTicks;

                if(canShoot(level, this))
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

    private void dropTurret()
    {
        ItemStack turretItemStack = new ItemStack(ModItems.TURRET.get(), 1);

        ModUtils.ItemNBTHelper.putInt(turretItemStack, "ammo", getAmmo());

        ItemEntity turretItemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), turretItemStack);

        this.level.addFreshEntity(turretItemEntity);

        this.discard();
    }


    public float getTurretYRot()
    {
        Optional<BlockPos> targetBlockPosOptional = this.entityData.get(TARGET_POS);

        if(targetBlockPosOptional.isPresent() && canShoot(level, this))
        {
            BlockPos targetBlockPos = targetBlockPosOptional.get();
                double radian = Math.toRadians(calculateYAngleFromBlockPos(this.blockPosition().south(), this.blockPosition(), targetBlockPos));

                return (float) radian;
        }
        return 0f;
    }

    public float getTurretXRot()
    {
        Optional<BlockPos> targetBlockPosOptional = this.entityData.get(TARGET_POS);

        if(targetBlockPosOptional.isPresent() && canShoot(level, this))
        {
            BlockPos targetBlockPos = targetBlockPosOptional.get();

            if(!isBlockBetween(level, this.eyeBlockPosition(), targetBlockPos.above()))
            {

                double radian = Math.toRadians(calculateXAngleFromBlockPos(this.blockPosition(), targetBlockPos));

                return (float) radian;

            }
        }
        return 0f;
    }

     /**
     * @param angelOrientation the null anchor, where the angle should come from(null-line)     Example:            nullLine:    |
     *
     * @param mainBlockPos where the angle come from (turret)                                                       mainPos:     x       targetPos: *
     *
     * @param targetPos where the angle should relate to     (north)                                   Output: 90°
     * @return
     */
    public static double calculateYAngleFromBlockPos(BlockPos angelOrientation, BlockPos mainBlockPos, BlockPos targetPos) {

        if(mainBlockPos.getX() == targetPos.getX() && mainBlockPos.getZ() == targetPos.getZ())
        {
            return 0.0f;
        }

        // Calculate the vectors between the points
    double[] vector1 = { angelOrientation.getX() - mainBlockPos.getX(), angelOrientation.getZ() - mainBlockPos.getZ() };
    double[] vector2 = { targetPos.getX() - mainBlockPos.getX(), targetPos.getZ() - mainBlockPos.getZ() };

    // Calculate the dot product
    double dotProduct = vector1[0] * vector2[0] + vector1[1] * vector2[1];

    // Calculate the magnitudes of the vectors
    double magnitude1 = Math.sqrt(vector1[0] * vector1[0] + vector1[1] * vector1[1]);
    double magnitude2 = Math.sqrt(vector2[0] * vector2[0] + vector2[1] * vector2[1]);

    // Calculate the cosine of the angle using the dot product and magnitudes
    double cosTheta = dotProduct / (magnitude1 * magnitude2);

    // Calculate the angle in radians using the arccosine function
    double angleRad = Math.acos(cosTheta);

    // Convert the angle from radians to degrees
    double angleDeg = Math.toDegrees(angleRad);

    if(((mainBlockPos.getX()) - (targetPos.getX())) < 0)
    {
        angleDeg = 360 - angleDeg;
    }

    return angleDeg;
}

    /**
     * @param mainBlockPos where the angle come from (turret)
     *
     * @param targetPos where the angle should relate to (height)
     * @return
     */
    public static double calculateXAngleFromBlockPos(BlockPos mainBlockPos, BlockPos targetPos) {

        if(mainBlockPos.getX() == targetPos.getX() && mainBlockPos.getZ() == targetPos.getZ() && targetPos.getY() < mainBlockPos.getY())
        {
            return 0.0f;
        }

        double d0 = targetPos.getX() - mainBlockPos.getX();
        double d1 = targetPos.getY() - mainBlockPos.getY();
        double d2 = targetPos.getZ() - mainBlockPos.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return !(Math.abs(d1) > (double)1.0E-5F) && !(Math.abs(d3) > (double)1.0E-5F) ? 0.0f : (float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
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

    private static AABB getSearchBound(BlockPos pos)
    {
        return new AABB(pos.getX() - searchRadiusInBlocks, pos.getY() - searchRadiusInBlocks, pos.getZ() - searchRadiusInBlocks, pos.getX() + searchRadiusInBlocks, pos.getY() + searchRadiusInBlocks, pos.getZ() + searchRadiusInBlocks);
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

            if(ModUtils.DwarfClasses.canPlayerDo(ModUtils.DwarfClasses.getPlayerClass(player), IClassData.Classes.Engineer) && itemInHand.is(ModItems.WRENCH.get()))
            {
                this.dropTurret();
            }
        }

        return false;
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

    public static boolean canShoot(Level level, TurretEntity turret)
    {
        Optional<BlockPos> optionalTargetPos = turret.entityData.get(TARGET_POS);
        if (optionalTargetPos.isPresent())
        {
            return turret.getAmmo() >= 1 && !isBlockBetween(level, optionalTargetPos.get(), turret.eyeBlockPosition());
        }

        return false;
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

    public TargetingConditions getTargeting()
    {
        return getAttackNeutral() ? ENEMY_AND_NEUTRAL_TARGETING : ENEMY_TARGETING;
    }

    public void setAttackNeutral(boolean setAttackNeutral)

    {this.entityData.set(ATTACK_NEUTRAL, setAttackNeutral);}

    public boolean getAttackNeutral()
    {
        return this.entityData.get(ATTACK_NEUTRAL);
    }

    public void setAmmo(int amount)
    {
        this.entityData.set(AMMO, amount);
    }

    public int getAmmo()
    {
        return this.entityData.get(AMMO);
    }

    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }
}
