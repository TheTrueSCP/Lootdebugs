package net.the_goldbeards.lootdebugs.Entities.Tools.Turret;


import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Items.Tools.Turret.TurretAmmoItem;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.client.Screens.TurretTargetingScreen;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static net.the_goldbeards.lootdebugs.util.ModUtils.BlockHelpers.isBlockBetween;

public class TurretEntity extends Entity
{
    public static int defaultAmmo = 200;

    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Engineer;

    private static final EntityDataAccessor<Integer> AMMO = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> TARGETING_MODE = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> ATTACK_NEUTRAL = SynchedEntityData.defineId(TurretEntity.class, EntityDataSerializers.BOOLEAN);

    private static int searchRadiusInBlocks = 20;
    private static float shootCooldownTicks = 5;

    private Player owner;
    private float shootCooldown;
    private static final Predicate<LivingEntity> ENEMY_PREDECATE = (p_30636_) -> {
        return p_30636_ instanceof Enemy;
    };

    private static final Predicate<LivingEntity> ENEMY_AND_NEUTRAL_PREDECATE = (p_30636_) -> {
        return p_30636_ instanceof Mob;
    };

    private final Predicate<LivingEntity> CAN_REACH_PREDECATE = (livingEntity) -> {
        return !isBlockBetween(livingEntity.level, livingEntity.blockPosition(), this.blockPosition());
    };

    private final Predicate<Entity> CAN_REACH_ENTITY_PREDECATE = (entity) -> {
        return !isBlockBetween(entity.level, entity.blockPosition(), this.blockPosition());
    };



    private final TargetingConditions ENEMY_TARGETING = TargetingConditions.forCombat().range(searchRadiusInBlocks).selector(ENEMY_PREDECATE).selector(CAN_REACH_PREDECATE);

    private final TargetingConditions ENEMY_AND_NEUTRAL_TARGETING = TargetingConditions.forCombat().range(searchRadiusInBlocks).selector(ENEMY_AND_NEUTRAL_PREDECATE).selector(CAN_REACH_PREDECATE);

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
        this.entityData.define(ATTACK_NEUTRAL, true);
        this.entityData.define(TARGET, 0);
        this.entityData.define(TARGETING_MODE, 0);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {



        if(ModUtils.DwarfClasses.isPlayerClass(pPlayer, IClassData.Classes.Engineer))
        {
            if (pPlayer.getItemInHand(pHand).is(ModItems.TURRET_AMMO.get()))
            {

                float ammoItemAmount = ModUtils.ItemNBTHelper.getFloat(pPlayer.getItemInHand(pHand), "ammoAmount");

                if (ammoItemAmount >= 10 || pPlayer.isCreative()) {
                    addAmmo(10);

                    if (!pPlayer.isCreative()) {
                        TurretAmmoItem.ShrinkAmmo(pPlayer.getItemInHand(pHand), 10);
                    }
                    return InteractionResult.SUCCESS;
                } else if (pPlayer.getItemInHand(pHand).is(ModItems.TURRET_AMMO.get())) {
                    addAmmo(1);

                    if (!pPlayer.isCreative()) {
                        TurretAmmoItem.ShrinkAmmo(pPlayer.getItemInHand(pHand), 1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }

            else if(pPlayer.getLevel() instanceof ClientLevel)
            {
                Minecraft.getInstance().setScreen(new TurretTargetingScreen(this, getTargetingMode()));
            }
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
                    this.setTargetingMode(TargetingModes.PASSIVE);
                }
            }

            if(getTargetingConditions() != null)
            {
                LivingEntity nearestTarget = level.getNearestEntity(level.getEntitiesOfClass(LivingEntity.class, getSearchBound(this.blockPosition())), getTargetingConditions(), null, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());

                List<Entity> nearEntities = level.getEntities((Entity) null, getSearchBound(this.blockPosition()));

                List<LivingEntity> nearPingedLivingEntities = NonNullList.create();

                for(Entity entity : nearEntities)//filter for pinged entities and convert to living entity
                {
                    if(entity instanceof LivingEntity livingEntity)
                    {
                        if(livingEntity.hasGlowingTag())
                        {
                            nearPingedLivingEntities.add(livingEntity);
                        }
                    }
                }

                LivingEntity nearestPingedMob = level.getNearestEntity(nearPingedLivingEntities, getTargetingConditions(), null, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());



                this.setCustomName(new TextComponent("" + getAmmo()));
                this.setCustomNameVisible(true);

                if(shootCooldown <= 0)
                {
                    //get target, if pinged mob is present, use this instead
                    LivingEntity target = null;

                    if(nearestPingedMob != null)
                    {
                        target = nearestPingedMob;
                    }

                    if(nearestTarget != null && target == null)
                    {
                        target = nearestTarget;
                    }

                    //if some target present
                    if(target != null)
                    {
                        setTargetID(target.getId());
                        shootCooldown = shootCooldownTicks;

                        if (canShoot(level, this)) {
                            BulletEntity bulletEntity = new BulletEntity(level);
                            bulletEntity.setPos(this.position().x, this.position().y + 1f, this.position().z);

                            Vec3 shootVec = new Vec3((bulletEntity.getX() - target.getX()) * -1, (bulletEntity.getY() - target.blockPosition().getY()) * -1, (bulletEntity.getZ() - target.getZ()) * -1);//calculate Movement from Entity to hook

                            level.addFreshEntity(bulletEntity);

                            level.playSound(null, this.blockPosition(), SoundEvents.CROSSBOW_SHOOT, SoundSource.NEUTRAL, 1, 1);

                            bulletEntity.setDeltaMovement(bulletEntity.getDeltaMovement().add(shootVec));//Normalize Vec cause the vec would be to fast

                            shrinkAmmo(1);
                        }
                    }
                    else
                    {
                        setTargetID(0);
                    }
                }
            }
            else//passive
            {
                this.setCustomNameVisible(false);
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


    public void dropTurret()
    {
        ItemStack turretItemStack = new ItemStack(ModItems.TURRET.get(), 1);

        ModUtils.ItemNBTHelper.putInt(turretItemStack, "ammo", getAmmo());
        ModUtils.ItemNBTHelper.putInt(turretItemStack, "targetingMode", getTargetingMode().getId());

        ItemEntity turretItemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), turretItemStack);

        this.level.addFreshEntity(turretItemEntity);

        this.discard();
    }


    public float getTurretYRot()
    {
        Entity target = level.getEntity(getTargetID());

        if(target != null && canShoot(level, this))
        {
                double radian = Math.toRadians(calculateYAngleFromBlockPos(this.position().add(new Vec3(0, 0, 1)), this.position(), target.position().add(new Vec3(0, 1, 0))));

                return (float) radian;
        }
        return 0f;
    }

    public float getTurretXRot()
    {
        Entity target = level.getEntity(getTargetID());

        if(getTargetingConditions() == null)
        {
            return (float) Math.toRadians(35);
        }

        if(target != null && canShoot(level, this))
        {
            if(!isBlockBetween(level, this.eyeBlockPosition(), target.blockPosition()))
            {

                double radian = Math.toRadians(calculateXAngleFromBlockPos(this.position(), target.position()));

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
    public static double calculateYAngleFromBlockPos(Vec3 angelOrientation, Vec3 mainBlockPos, Vec3 targetPos) {

        if(mainBlockPos.x == targetPos.x && mainBlockPos.x == targetPos.x)
        {
            return 0.0f;
        }

        // Calculate the vectors between the points
    double[] vector1 = { angelOrientation.x - mainBlockPos.x, angelOrientation.z - mainBlockPos.z };
    double[] vector2 = { targetPos.x - mainBlockPos.x, targetPos.z - mainBlockPos.z };

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

    if(((mainBlockPos.x) - (targetPos.x)) < 0)
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
    public static double calculateXAngleFromBlockPos(Vec3 mainBlockPos, Vec3 targetPos) {

        if(mainBlockPos.x == targetPos.x && mainBlockPos.z == targetPos.z && targetPos.y < mainBlockPos.y)
        {
            return 0.0f;
        }

        double d0 = targetPos.x - mainBlockPos.x;
        double d1 = targetPos.y - mainBlockPos.y;
        double d2 = targetPos.z - mainBlockPos.z;
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
        if(pCompound.contains("ammo"))
        {
            setAmmo(pCompound.getInt("ammo"));
        }

        if(pCompound.contains("targetingMode"))
        {
            setTargetingMode(TargetingModes.byId(pCompound.getInt("targetingMode")));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.putInt("ammo", getAmmo());
        pCompound.putInt("targetingMode", getTargetingMode().getId());
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
        Entity target = level.getEntity(turret.getTargetID());
        if (target != null)
        {
            return turret.getAmmo() >= 1 && !isBlockBetween(level, target.blockPosition(), turret.eyeBlockPosition());
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

    public TargetingConditions getTargetingConditions()
    {
        switch (getTargetingMode())
        {
            case NEUTRAL_AND_ENEMYS -> {return ENEMY_AND_NEUTRAL_TARGETING;}
            case PASSIVE -> {return null;}
            case ENEMYS -> {return ENEMY_TARGETING;}
            default -> {return ENEMY_TARGETING;}
        }
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

    private void setTargetID(int id)
    {
        this.entityData.set(TARGET, id);
    }

    private int getTargetID()
    {
        return this.entityData.get(TARGET);
    }

    public void setTargetingMode(TargetingModes targetingMode)
    {
        this.entityData.set(TARGETING_MODE, targetingMode.getId());
    }

    public TargetingModes getTargetingMode()
    {
        return TargetingModes.byId(this.entityData.get(TARGETING_MODE));
    }
    public enum TargetingModes
    {
        NEUTRAL_AND_ENEMYS(0, "neutral_and_enemy"),

        ENEMYS(1, "enemys"),
        PASSIVE(2, "passive");



        final int id;
        private final String name;

        private TargetingModes(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private static final TargetingModes[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(TargetingModes::getId)).toArray((p_41067_) -> {
            return new TurretEntity.TargetingModes[p_41067_];
        });


        public int getId() {
            return this.id;
        }

        public static TargetingModes byId(int classId) {
            if (classId < 0 || classId >= BY_ID.length) {
                classId = 0;
            }

            return BY_ID[classId];
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
