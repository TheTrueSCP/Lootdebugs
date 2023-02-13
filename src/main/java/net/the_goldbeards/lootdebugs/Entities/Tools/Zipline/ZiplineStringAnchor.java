package net.the_goldbeards.lootdebugs.Entities.Tools.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.init.ModEntities;

public class ZiplineStringAnchor extends Entity
{
    private static final EntityDataAccessor<BlockPos> LINKED_POS = SynchedEntityData.defineId(ZiplineStringAnchor.class, EntityDataSerializers.BLOCK_POS);

    public ZiplineStringAnchor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ZiplineStringAnchor(Level pLevel, BlockPos pos, Entity linkedEntity) {
        super(ModEntities.STRING_ANCHOR_ENTITY.get(), pLevel);
        this.setLinkedPos(linkedEntity.blockPosition());
        this.setPos(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
    }


    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(LINKED_POS, BlockPos.ZERO);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.put("linkPos", NbtUtils.writeBlockPos(getLinkedUpperPos()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        setLinkedPos(NbtUtils.readBlockPos(pCompound.getCompound("linkPos")));
    }

    public void setLinkedPos(BlockPos linkedPos)
    {
        this.entityData.set(LINKED_POS, linkedPos);
    }

    public BlockPos getLinkedUpperPos()
    {
        return this.entityData.get(LINKED_POS);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
