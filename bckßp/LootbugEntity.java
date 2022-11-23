package net.the_goldbeards.lootdebugs.Entities;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModSounds;
import net.the_goldbeards.lootdebugs.util.ModTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.example.client.renderer.item.JackInTheBoxRenderer;
import software.bernie.example.entity.GeoExampleEntity;
import software.bernie.example.item.JackInTheBoxItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class LootbugEntity extends Animal implements ItemSteerable, IAnimatable, ISyncable
{
    private Vec3 pos;
    public LootbugEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.pos = this.position();
    }

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(
            Items.REDSTONE,
            ModItems.OMM0RAN_HEARTHSTONE.get(),
            ModItems.NITRA.get(),
            ModBlocks.NITRA_BLOCK.get().asItem(),
            ModItems.MORKITE.get(),
            ModItems.MORKITE_BLOCK.get());

    private boolean isPetting = false;
    private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.INT);
    private final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);
    private static final int ANIM_OPEN = 0;
    private AnimationFactory factory = new AnimationFactory(this);


    //Which Child should be breeded
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob parent) {
        return ModEntities.LOOTBUG.get().create(world);
    }


    //General Configs
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
      // this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, TEMPTATION_ITEMS, false));
     //   this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.00f)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.JUMP_STRENGTH, 1.15);
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


    @javax.annotation.Nullable
    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }


    //Datasync

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.steering.addAdditionalSaveData(tag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SADDLE_ID, false);
        this.entityData.define(DATA_BOOST_TIME, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.steering.readAdditionalSaveData(tag);
    }


    //Interaction
    @Override
    public InteractionResult mobInteract(Player playerIn, InteractionHand hand) {

       /* if(playerIn.getItemInHand(hand).is(ModTags.Items.LOOTBUG_RIDING_ITEMS) && !playerIn.isSecondaryUseActive())
        {
            if (!this.level.isClientSide) {
                playerIn.startRiding(this);
            }
            playerIn.getItemInHand(hand).shrink(1);
        }
      if(playerIn.getItemInHand(hand).isEmpty())
        {
            playSound(getReactionSound(),12,1);
            //Start Animation
            return InteractionResult.PASS;
        }*/
        isPetting = true;



        return InteractionResult.sidedSuccess(this.level.isClientSide);

    }




    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getDismountLocationForPassenger(entity);
        } else {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(Pose pose : entity.getDismountPoses()) {
                AABB aabb = entity.getLocalBoundsForPose(pose);

                for(int[] aint1 : aint) {
                    blockpos$mutableblockpos.set(blockpos.getX() + aint1[0], blockpos.getY() + 3.5, blockpos.getZ() + aint1[1]);
                    double d0 = this.level.getBlockFloorHeight(blockpos$mutableblockpos);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutableblockpos, d0);
                        if (DismountHelper.canDismountTo(this.level, entity, aabb.move(vec3))) {
                            entity.setPose(pose);
                            return vec3;
                        }
                    }
                }
            }

            return super.getDismountLocationForPassenger(entity);
        }
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
    public boolean boost() {
            return this.steering.boost(this.getRandom());
    }

    @Override
    public void travelWithInput(Vec3 vec3) {
        super.travel(vec3);
    }

    @Override
    public float getSteeringSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
      return stack.is(ModTags.Items.LOOTBUG_BREEDING_ITEMS);
    }


    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving())
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }
        else {
            return PlayState.STOP;
        }
    }

    private <E extends IAnimatable> PlayState petting(AnimationEvent<E> event) {
        System.out.println(this.isPetting);
        if (this.isPetting)
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("petting", true));
            this.isPetting = false;
            return PlayState.CONTINUE;

        }
        else {
            this.isPetting = false;
            return PlayState.STOP;
        }
    }
    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController(this, "controller1", 0, this::petting));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, "controller");

    }
}
