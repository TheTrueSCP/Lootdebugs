package net.the_goldbeards.lootdebugs.Entities.Tools.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplineBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplineTile;
import net.the_goldbeards.lootdebugs.Entities.Tools.AbstractShootablePhysicsArrowLikeEntity;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ZiplineEntity extends AbstractShootablePhysicsArrowLikeEntity
{
    private static final EntityDataAccessor<BlockPos> LINKED_BASE_POS = SynchedEntityData.defineId(ZiplineEntity.class, EntityDataSerializers.BLOCK_POS);
    private boolean lock = false;

    public ZiplineEntity(EntityType<? extends AbstractShootablePhysicsArrowLikeEntity> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public ZiplineEntity(LivingEntity pShooter, Level pLevel, @NotNull BlockPos ziplineMountBase) {
        super(ModEntities.ZIPLINE_ENTITY.get(), pShooter, pLevel);
        this.setZiplineMountBase(ziplineMountBase);
    }

    @Override
    protected void defineSynchedData() {

        this.entityData.define(LINKED_BASE_POS, BlockPos.ZERO);

        super.defineSynchedData();
    }

    @Override
    public void tick()
    {
        super.tick();
        if(this.inGround && !lock)
        {
            this.lock = true;
        }

        if(this.isInWater())
        {
            this.kill();
        }
    }

    @Override
    protected void tickDespawn()
    {

    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand)
    {
        Level pLevel = pPlayer.getLevel();
        BlockPos pPos = this.blockPosition();

        if(!pLevel.isClientSide()) {
            if (getZiplineMountBase() != null)
            {
                if (pLevel.getBlockEntity(getZiplineMountBase().above(1)) instanceof ZiplineTile && this.lock)
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
    protected void onHitBlock(BlockHitResult blockHitResult)
    {
        super.onHitBlock(blockHitResult);

        //When hit, lock pos and build block at origin
        if(!this.level.isClientSide)
        {
            ZiplineBlock.placeBlock(this.level, getZiplineMountBase(), this);
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
}
