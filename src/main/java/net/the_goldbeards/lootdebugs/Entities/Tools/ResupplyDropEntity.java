package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;

public class ResupplyDropEntity extends AbstractShootablePhysicsArrowLikeEntity
{
    private final int targetHeight;
    boolean inGround;

    private Player caller;

    public ResupplyDropEntity(EntityType<? extends AbstractShootablePhysicsArrowLikeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        inGround = true;
        targetHeight = -50;
    }

    public ResupplyDropEntity(Level pLevel, int targetHeight, Player caller, BlockPos dropPos) {
        super(ModEntities.RESUPPLY_DROP_ENTITY.get(), pLevel);
        inGround = false;
        this.targetHeight = targetHeight;
        this.caller = caller;
        this.setPos(dropPos.getX(), dropPos.getY(), dropPos.getZ());
    }

    @Override
    public void tick() {
        if(this.blockPosition().getY() <= targetHeight)
        {
            if (!level.getBlockState(this.blockPosition()).isAir()) {
                level.setBlock(this.blockPosition(), Blocks.AIR.defaultBlockState(), 2);
            }
            if (!level.getBlockState(this.blockPosition().below()).isAir()) {
                level.setBlock(this.blockPosition(), Blocks.AIR.defaultBlockState(), 2);
            }

            if (!level.getBlockState(this.blockPosition().above()).isAir()) {
                level.setBlock(this.blockPosition(), Blocks.AIR.defaultBlockState(), 2);
            }
        }

        super.tick();
    }

    @Override
    public void onDespawn()
    {
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {

        if(p_36755_.getBlockPos().getY() <= targetHeight)
        {
            super.onHitBlock(p_36755_);
        }
        else
        {
            level.destroyBlock(p_36755_.getBlockPos(), false);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        pResult.getEntity().kill();

        super.onHitEntity(pResult);
    }

    @Override
    public boolean mayInteract(Level pLevel, BlockPos pPos) {
        return true;
    }
}
