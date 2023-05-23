package net.the_goldbeards.lootdebugs.Entities.Tools.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplinePoleBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplinePoleTile;
import net.the_goldbeards.lootdebugs.Entities.Tools.AbstractShootablePhysicsArrowLikeEntity;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import org.jetbrains.annotations.NotNull;

public class ZiplineEntity extends AbstractShootablePhysicsArrowLikeEntity
{
    private static final EntityDataAccessor<BlockPos> LINKED_BASE_POS = SynchedEntityData.defineId(ZiplineEntity.class, EntityDataSerializers.BLOCK_POS);
    private boolean lock = false;
    private Direction rotPlaceDirection;

    private float killCooldown;

    public ZiplineEntity(EntityType<? extends AbstractShootablePhysicsArrowLikeEntity> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
        killCooldown = 0;
    }

    public ZiplineEntity(LivingEntity pShooter, Level pLevel, @NotNull BlockPos ziplineMountBase, Direction playerLookDirection) {
        super(ModEntities.ZIPLINE_ENTITY.get(), pShooter, pLevel);
        this.setZiplineMountBase(ziplineMountBase);
        this.setRotPlaceDirection(playerLookDirection);
    }

    @Override
    protected void defineSynchedData()
    {

        this.entityData.define(LINKED_BASE_POS, BlockPos.ZERO);

        super.defineSynchedData();
    }


    @Override
    public void tick()
    {
        super.tick();

        if(killCooldown > 0)
        {
            killCooldown--;
        }

        if(killCooldown < 0)
        {
            killCooldown = 0;
        }

        if(this.inGround && !lock)
        {
            this.lock = true;
        }

        if(this.isInWater() || this.isInLava())
        {
            this.kill();

            if(this.getZiplineMountBase() != null)
            {
                ZiplinePoleBlock.removeBlock(level, getZiplineMountBase());
            }

        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount)
    {

        if(pSource.getEntity() instanceof Player player)
        {
            if(killCooldown <= 0)
            {
                killCooldown = 60;

                player.displayClientMessage(new TranslatableComponent("message.lootdebugs.zipline_entity.hit_again_discard"), true);

            }
            else
            {
                if (getZiplineMountBase() != null) {
                    ZiplinePoleBlock.removeBlock(level, getZiplineMountBase());
                }
                this.discard();
            }
        }

        return super.hurt(pSource, pAmount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {

        pCompound.put("link_base_pos", NbtUtils.writeBlockPos(getZiplineMountBase()));
        pCompound.putBoolean("lock", this.lock);
        pCompound.putInt("rot_place_direction", getRotPlaceDirection().get2DDataValue());

        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {

        if(pCompound.contains("link_base_pos"))
        {
            setZiplineMountBase(NbtUtils.readBlockPos(pCompound.getCompound("link_base_pos")));
        }

        if(pCompound.contains("rot_place_direction"))
        {
            this.setRotPlaceDirection(Direction.from2DDataValue(pCompound.getInt("rot_place_direction")));
        }

        if(pCompound.contains("lock"))
        {
            this.lock = pCompound.getBoolean("lock");
        }

        super.readAdditionalSaveData(pCompound);
    }

    @Override
    protected void tickDespawn()
    {
        this.life = 0;
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand)
    {
        Level pLevel = pPlayer.getLevel();
        BlockPos pPos = this.blockPosition();

        if(!pLevel.isClientSide()) {
            if (getZiplineMountBase() != null)
            {
                if (pLevel.getBlockEntity(getZiplineMountBase().above(1)) instanceof ZiplinePoleTile && this.lock)
                {
                    //Move player
                    ZiplineMoveEntity ziplineMoveEntity = new ZiplineMoveEntity(pLevel, pPos, getZiplineMountBase().above(1));
                    pLevel.addFreshEntity(ziplineMoveEntity);
                    pPlayer.startRiding(ziplineMoveEntity);
                } else
                {
                    pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.zipline_block.link_invalid"), true);
                }
            }
        }

        return super.interact(pPlayer, pHand);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult)
    {

    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult)
    {
        super.onHitBlock(blockHitResult);

        //When hit, lock pos and build block at origin
        if(!this.level.isClientSide && getZiplineMountBase() != null && this.getRotPlaceDirection() != null)
        {
            ZiplinePoleBlock.placeBlock(this.level, getZiplineMountBase(), this, getRotPlaceDirection());
            this.lock = true;
        }
    }

    @Override
    protected boolean shouldFall()
    {

        return !lock && super.shouldFall();
    }

    public BlockPos getZiplineMountBase()
    {
        return this.entityData.get(LINKED_BASE_POS);
    }

    public void setZiplineMountBase(BlockPos pos)
    {
        this.entityData.set(LINKED_BASE_POS, pos);
    }

    public boolean getIsLocked()
    {
        return lock;
    }

    public Direction getRotPlaceDirection()
    {
        return rotPlaceDirection;
    }

    public void setRotPlaceDirection(Direction rotPlaceDirection)
    {
        this.rotPlaceDirection = rotPlaceDirection;
    }
}
