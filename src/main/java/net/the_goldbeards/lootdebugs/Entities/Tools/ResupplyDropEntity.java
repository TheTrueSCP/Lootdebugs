package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.Items.Fuel.FuelCanisterItem;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import javax.annotation.Nullable;
import java.util.List;

public class ResupplyDropEntity extends Projectile {

    private static final int initialSupplyCount = 4;

    @Nullable
    BlockState lastState;
    public boolean inGround;

    private static final EntityDataAccessor<Integer> TARGET_HEIGHT = SynchedEntityData.defineId(ResupplyDropEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> SUPPLY_COUNT = SynchedEntityData.defineId(ResupplyDropEntity.class, EntityDataSerializers.INT);


    public ResupplyDropEntity(EntityType<? extends ResupplyDropEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        inGround = false;
        setTargetHeight(-40);
    }

    public ResupplyDropEntity(Level pLevel, int targetHeight, Player caller, BlockPos dropPos) {
        super(ModEntities.RESUPPLY_DROP_ENTITY.get(), pLevel);
        inGround = false;
        setTargetHeight(targetHeight);
        setOwner(caller);
        this.setPos(dropPos.getX(), dropPos.getY(), dropPos.getZ());
    }

    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.setPos(pX, pY, pZ);
        this.setRot(pYaw, pPitch);
    }

    @Override
    public void tick() {
        super.tick();

        boolean flag = this.isNoPhysics();
        Vec3 vec3 = this.getDeltaMovement();

        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir() && !flag && blockpos.getY() <= getTargetHeight()) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();

                for (AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.inGround && !flag) {
            if (this.lastState != blockstate && this.shouldFall())
            {
                this.startFalling();
            }
        } else {

            vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();
            if (flag) {
                this.setYRot((float) (Mth.atan2(-d5, -d1) * (double) (180F / (float) Math.PI)));
            } else {
                this.setYRot((float) (Mth.atan2(d5, d1) * (double) (180F / (float) Math.PI)));
            }

            this.setXRot((float) (Mth.atan2(d6, d4) * (double) (180F / (float) Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));


            if (!this.isNoGravity() && !flag) {
                Vec3 vec34 = this.getDeltaMovement();
                this.setDeltaMovement(vec34.x, -0.6f, vec34.z);//set the movement in air
            }

            this.setPos(d7, d2, d3);
        }

        this.checkInsideBlocks();
    }


    @Override
    protected void onInsideBlock(BlockState pState) {

        if (!level.isClientSide()) {
            if (!destinationHeight()) {
                destroyBlocks(level, blockPosition());
            }
        }

        super.onInsideBlock(pState);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TARGET_HEIGHT, 0);
        this.entityData.define(SUPPLY_COUNT, initialSupplyCount);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        if (destinationHeight()) {
            this.lastState = this.level.getBlockState(pResult.getBlockPos());
            super.onHitBlock(pResult);
            this.setDeltaMovement(Vec3.ZERO);
            this.setPos(pResult.getBlockPos().getX(), pResult.getBlockPos().above().getY(), pResult.getBlockPos().getZ());
            this.inGround = true;
            level.playSound(null, pResult.getBlockPos(), ModSounds.SUPPLY_POD_IMPACT.get(), SoundSource.BLOCKS, 0.5f, 1);
        } else {
            destroyBlocks(level, pResult.getBlockPos());
        }
        super.onHitBlock(pResult);
    }


    public boolean hurt(DamageSource pSource, float pAmount) {

        if(pSource.getEntity() instanceof Player player)
        {
            ItemStack itemInHand = player.getMainHandItem();

            if(ModUtils.DwarfClasses.isPlayerDwarf(player) && itemInHand.is(ModItems.WRENCH.get()))
            {
                this.dropResupply();
            }
        }

        return false;
    }

    private void dropResupply()
    {
        this.discard();
        ItemEntity itemEntity = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.IRON_NUGGET, random.nextInt(0, 10)));
        level.addFreshEntity(itemEntity);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {

        pResult.getEntity().hurt(ModDamageSources.RESUPPLY_POD, Float.MAX_VALUE);

        super.onHitEntity(pResult);
    }

    private void destroyBlocks(Level pLevel, BlockPos pos) {
        if (!pLevel.isClientSide())
        {
            if(!pLevel.getBlockState(pos).is(ModTags.Blocks.NOT_DESTROYABLE_BLOCKS))
            {
                pLevel.destroyBlock(pos, true);
            }
            else
            {
                this.setTargetHeight(pos.getY());
            }
        }
    }

    protected boolean destinationHeight() {
        return this.blockPosition().getY() <= getTargetHeight();
    }

    protected boolean shouldFall() {
        return this.inGround && this.level.noCollision((new AABB(this.position(), this.position())).inflate(0.06D));
    }

    protected void startFalling() {
        this.inGround = false;
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, 0.50f, vec3.z);
    }

    public boolean isNoPhysics() {
        if (!this.level.isClientSide) {
            return this.noPhysics;
        }
        return false;
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        if(ModUtils.DwarfClasses.isPlayerDwarf(pPlayer))
        {
            if (shrinkSupplyCount(1)) {
                for (ItemStack pStack : getRefillItems(ModUtils.DwarfClasses.getPlayerClass(pPlayer)))
                {
                    pPlayer.heal(10);
                    pPlayer.addItem(pStack);
                }
                return InteractionResult.SUCCESS;
            }
        }
        else
        {
            pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.resupply_pod.no_dwarf"), true);
        }


        return super.interact(pPlayer, pHand);
    }

    private List<ItemStack> getRefillItems(IClassData.Classes dwarfClass)
    {
        List<ItemStack> items = NonNullList.create();

        switch (dwarfClass)
        {
            case Scout, MonsieurHannes ->
            {
                items.add(new ItemStack(ModItems.FLARE_GUN_AMMO.get(), 64));
            }
            case Driller, TheTrueSCP ->
            {
                for(int i = 0; i < 2; i++)
                {
                    items.add(new ItemStack(ModItems.SATCHEL_CHARGE.get(), 1));
                }

                items.add(FuelCanisterItem.getFullFuel());
            }
            case Engineer ->
            {
                for(int i = 0; i < 4; i++)
                {
                    items.add(new ItemStack(ModItems.TURRET_AMMO.get(), 1));
                }

                items.add(new ItemStack(ModItems.PLATFORM_GUN_AMMO.get(), 32));
            }
            case Gunner ->
            {
                items.add(new ItemStack(ModItems.SHIELD.get(), 1));
                items.add(new ItemStack(ModItems.ZIPLINE_AMMO.get(), 32));
            }
        }
        return items;
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.lastState != null) {
            pCompound.put("inBlockState", NbtUtils.writeBlockState(this.lastState));
        }

        pCompound.putBoolean("inGround", this.inGround);

        pCompound.putInt("supplyCount", getSupplyCount());

        pCompound.putInt("targetHeight", getTargetHeight());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("inBlockState", 10)) {
            this.lastState = NbtUtils.readBlockState(pCompound.getCompound("inBlockState"));
        }

        this.inGround = pCompound.getBoolean("inGround");

        setSupplyCount(pCompound.getInt("supplyCount"));

        setTargetHeight(pCompound.getInt("targetHeight"));
    }

    public boolean isAttackable() {
        return true;
    }

    @Override
    public void lerpMotion(double pX, double pY, double pZ) {
        setPos(pX, pY, pZ);
    }

    protected float getEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.13F;
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 *= 64.0D * getViewScale();
        return pDistance < d0 * d0;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean mayInteract(Level pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    private void setTargetHeight(int targetHeight) {
        this.entityData.set(TARGET_HEIGHT, targetHeight);
    }

    private int getTargetHeight() {
        return this.entityData.get(TARGET_HEIGHT);
    }

    private boolean shrinkSupplyCount(int amount)
    {
        if(getSupplyCount() - amount >= 0)
        {
            setSupplyCount(getSupplyCount() - amount);
            return true;
        }
        return false;
    }

    private void setSupplyCount(int supplyCount)
    {
        this.entityData.set(SUPPLY_COUNT, supplyCount);
    }

    private int getSupplyCount()
    {
        return this.entityData.get(SUPPLY_COUNT);
    }

    public boolean arm0Visible()
    {
        return getSupplyCount() >= 1;
    }

    public boolean arm1Visible()
    {
        return getSupplyCount() >= 2;
    }

    public boolean arm2Visible()
    {
        return getSupplyCount() >= 3;
    }

    public boolean arm3Visible()
    {
        return getSupplyCount() >= 4;
    }

    @Override
    public boolean updateFluidHeightAndDoFluidPushing(TagKey<Fluid> pFluidTag, double pMotionScale) {
       return false;
    }
}
