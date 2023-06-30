package net.the_goldbeards.lootdebugs.Entities.Tools.Turret;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Entities.Tools.AbstractShootablePhysicsArrowLikeEntity;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;

public class BulletEntity extends AbstractShootablePhysicsArrowLikeEntity
{


    public BulletEntity(EntityType<? extends AbstractShootablePhysicsArrowLikeEntity> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    public void onDespawn() {

    }

    public BulletEntity(Level pLevel) {
        super(ModEntities.BULLET_ENTITY.get(), pLevel);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {

        if(pResult.getEntity() instanceof TurretEntity)
        {
            this.discard();
            return;
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_)
    {
        this.discard();
    }
}
