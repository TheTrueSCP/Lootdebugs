package net.the_goldbeards.lootdebugs.Entities.Mob;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Entities.Mob.Goals.GoToWantedItemGoal;
import net.the_goldbeards.lootdebugs.Entities.Mob.Inventory.Inventory;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LootbugEntity extends Animal implements ItemSteerable, PlayerRideable
{



    private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(LootbugEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(LootbugEntity.class, EntityDataSerializers.INT);

    private boolean isPetting = false;
    private float shakeAnim;
    private float shakeAnimO;
    private final Inventory inventory = new Inventory(30);
    private final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(LootbugEntity.class, EntityDataSerializers.BYTE);
    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.REDSTONE, ModItems.OMM0RAN_HEARTHSTONE.get(), ModItems.NITRA.get(), ModBlocks.NITRA_BLOCK.get().asItem(), ModItems.MORKITE.get(), ModItems.MORKITE_BLOCK.get());


    public LootbugEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 5;
    }


    //General Configs
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new GoToWantedItemGoal(this, ModTags.Items.LOOTBUG_BREEDING_ITEMS, 40));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, TEMPTATION_ITEMS, false));
        //   this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));


    }


    //Atributes
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.00f)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.JUMP_STRENGTH, 1.15);
    }

    //Datasync
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
        this.entityData.define(DATA_SADDLE_ID, false);
        this.entityData.define(DATA_BOOST_TIME, 0);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

    }

    @Override
    public InteractionResult mobInteract(Player playerIn, InteractionHand hand)
    {
        if(playerIn.getItemInHand(hand).is(ModItems.RED_SUGAR.get()))
        {

            if(!this.isPetting && this.shakeAnim == 0 && this.shakeAnimO == 0) {
                this.heal(3);
                playerIn.getItemInHand(hand).shrink(1);

                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);

                petting();
            }

            return InteractionResult.SUCCESS;
        }
        else if (!playerIn.getItemInHand(hand).isEmpty() && !playerIn.getItemInHand(hand).is(ModTags.Items.LOOTBUG_BREEDING_ITEMS) && !this.isVehicle() && !playerIn.isSecondaryUseActive())
        {//if you press Shift and have nothing in your hand, you'll start riding the lootbug

            if(!this.level.isClientSide())
            {
                playerIn.startRiding(this);

            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        else
        {
            InteractionResult interactionresult = super.mobInteract(playerIn, hand);//Feeding

            if (!interactionresult.consumesAction()) {
                ItemStack itemstack = playerIn.getItemInHand(hand);

               petting();

                return InteractionResult.SUCCESS;
            }
            else
            {
                return interactionresult;
            }
        }

    }

    public void petting()
    {
        if(!this.isPetting && this.shakeAnim == 0 && this.shakeAnimO == 0)
        {
            this.isPetting = true;
            level.playLocalSound(this.getX(),this.getY(),this.getZ(),getReactionSound(), SoundSource.NEUTRAL,1,1, true);//petting
            int rand = this.level.getRandom().nextInt(60,80);

            if(rand == 69)
            {
                BlockPos DropPos = new BlockPos(this.getX() + 2,this.getY()+ 3,this.getZ() + 2);
                ItemEntity Loot = new ItemEntity(level,DropPos.getX(),DropPos.getY(),DropPos.getZ(), getPettingDrop());

                level.addFreshEntity(Loot);
                //AdLoot


            }

        }
    }
// Wall Climbing

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new WallClimberNavigation(this,pLevel);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean pClimbing) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (pClimbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }


    //Riding

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    public boolean canBeControlledByRider() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof Player)) {
            return false;
        } else {

            return ((Player) entity).getMainHandItem().is(ModItems.NITRA_ON_A_STICK.get()) || ((Player) entity).getOffhandItem().is(ModItems.NITRA_ON_A_STICK.get());

        }
    }

    public void travel(Vec3 pTravelVector) {

        if(!this.verticalCollision)
        {
            this.setClimbing(this.horizontalCollision);
        }
        else
        {
            this.setClimbing(false);
        }
        this.travel(this, this.steering, pTravelVector);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);

        for(ItemStack itemStack : this.inventory.getAll())
        {
            ItemEntity entity = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), itemStack);
            this.level.addFreshEntity(entity);
        }
        this.inventory.deleteAll();
    }

    @Override
    protected void pickUpItem(ItemEntity pItemEntity) {
        super.pickUpItem(pItemEntity);
        if (pItemEntity.getItem().is(ModTags.Items.LOOTBUG_BREEDING_ITEMS))
        {
            if(this.inventory.add(pItemEntity.getItem()))
            {
                pItemEntity.discard();
            }
        }
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }



    @Override
    public boolean wantsToPickUp(ItemStack pStack)
    {
return true;
    }
    // Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    //use this to react to sunlight and start to burn.

    //Ticks
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide  && !this.isPetting  && this.onGround) {
            this.shakeAnim = 0.0F;
            this.shakeAnimO = 0.0F;

        }
    }

    //Called to update the entity's position/logic.
    @Override
    public void tick() {
        super.tick();
        if(!this.level.isClientSide())
        {
            this.setClimbing(this.horizontalCollision);
        }
        if (this.isAlive()) {

            if (this.isPetting) {
                if (this.shakeAnim == 0.0F) {
                    this.playSound(ModSounds.LOOTBUG_REACTION.get(), this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    this.gameEvent(GameEvent.WOLF_SHAKING);
                }

                this.shakeAnimO = this.shakeAnim;
                this.shakeAnim += 0.05F;
                if (this.shakeAnimO >= 2.0F) {
                    this.isPetting = false;
                    this.shakeAnimO = 0.0F;
                    this.shakeAnim = 0.0F;
                }

                if (this.shakeAnim > 0.4F) {
                    float f = (float) this.getY();
                    int i = (int) (Mth.sin((this.shakeAnim - 0.4F) * (float) Math.PI) * 7.0F);
                    Vec3 vec3 = this.getDeltaMovement();

                    for (int j = 0; j < i; ++j) {
                        float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                        float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                    }
                }
            }
        }
    }


    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_BOOST_TIME.equals(pKey) && this.level.isClientSide) {
            this.steering.onSynced();
        }

        super.onSyncedDataUpdated(pKey);
    }

//Pet Animation
    /**
     * Handler for {World#setEntityState}
     */
    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 8) {
            this.isPetting = true;
            this.shakeAnim = 0.0F;
            this.shakeAnimO = 0.0F;
        } else if (pId == 56) {
            this.cancelShake();
        } else {
            super.handleEntityEvent(pId);
        }
    }

    private void cancelShake() {
        this.isPetting = false;
        this.shakeAnim = 0.0F;
        this.shakeAnimO = 0.0F;
    }

    public float getTailRotation(float pPartialTicks, float pOffset)
    {
        float f = (Mth.lerp(pPartialTicks, this.shakeAnimO, this.shakeAnim) + pOffset) / 2.0F;
        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }


        return Mth.sin(f * (float)Math.PI) * Mth.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    //Sounds
    @javax.annotation.Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return ModSounds.LOOTBUG_IDLE.get();
    }

    @javax.annotation.Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.LOOTBUG_DEATH.get();
    }

    @javax.annotation.Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.LOOTBUG_HURT.get();
    }

    protected SoundEvent getReactionSound() {
        return ModSounds.LOOTBUG_REACTION.get();
    }

    //Interaction

    public ItemStack getPettingDrop()
    {
       int rand = level.random.nextInt(0, 100);

       if(rand <= 5)
       {
           return new ItemStack(Items.FLINT, 1);
       }
       else if(rand <= 10)
       {
           return new ItemStack(ModItems.RAW_BISMOR.get(), 1);
       }
       else if(rand <= 15)
       {
           return new ItemStack(ModItems.RAW_CROPPER.get(), 1);
       }
       else if(rand <= 20)
       {
           return new ItemStack(ModItems.RAW_DYSTRUM.get(), 1);
       }
       else if(rand <= 40)
       {
           return new ItemStack(Items.RAW_GOLD, 1);
       }
       else if(rand <= 100)
       {
           return new ItemStack(ModItems.NITRA.get(), 1);
       }
        return new ItemStack(ModItems.NITRA.get(), 1);
    }

    //What Happend, when the Lootbug is stroke by an Lightning Bolt?
    @Override
    public void thunderHit(ServerLevel worldIn, LightningBolt bolt) {
        if (worldIn.getDifficulty() != Difficulty.PEACEFUL && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, ModEntities.LOOTBUG_GOLDEN.get(), (timer) -> {})) {
            LootbugEntity lootbug = ModEntities.LOOTBUG_GOLDEN.get().create(worldIn);
            lootbug.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            lootbug.setNoAi(this.isNoAi());
            lootbug.setBaby(this.isBaby());
            if (this.hasCustomName()) {
                lootbug.setCustomName(this.getCustomName());
                lootbug.setCustomNameVisible(this.isCustomNameVisible());
            }

            lootbug.setPersistenceRequired();
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, lootbug);
            worldIn.addFreshEntity(lootbug);
            this.discard();
        } else {
            super.thunderHit(worldIn, bolt);
        }

    }

    //Steering
    @Override
    public boolean canBreed() {
        return true;
    }

    @Override
    public void travelWithInput(Vec3 pTravelVec) {
        super.travel(pTravelVec);
    }

    @Override
    public boolean boost() {
    return false;
    }

    @Override
    public float getSteeringSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.Items.LOOTBUG_BREEDING_ITEMS);
    }

    //Which Child should be breeded
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob parent) {
        return ModEntities.LOOTBUG.get().create(world);
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }


//spawning
    public static boolean checkLootbugSpawnRules(EntityType<LootbugEntity> lootbugEntityEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos pos, Random random)
    {
        return checkMobSpawnRules(serverLevelAccessor, mobSpawnType, pos, random);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor pLevel, BlockPos pPos, Random pRandom) {
        if (pLevel.getBrightness(LightLayer.BLOCK, pPos) <= 7)
        {
            return true;
        }
        return false;
    }

    public static boolean checkMobSpawnRules(ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        BlockPos blockpos = pPos.below();
        System.out.println("" + (blockpos.getY() <= 20) + isDarkEnoughToSpawn(pLevel, pPos, pRandom));
        return pReason == MobSpawnType.SPAWNER || (blockpos.getY() <= 20) && isDarkEnoughToSpawn(pLevel, pPos, pRandom);
    }
}
