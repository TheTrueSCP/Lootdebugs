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
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplineTile;
import net.the_goldbeards.lootdebugs.init.ModEntities;

public class ZiplineStringAnchor extends Entity
{
    private static final EntityDataAccessor<BlockPos> LINKED_POS = SynchedEntityData.defineId(ZiplineStringAnchor.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> STRING_RENDER = SynchedEntityData.defineId(ZiplineStringAnchor.class, EntityDataSerializers.BOOLEAN);

    public ZiplineStringAnchor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ZiplineStringAnchor(Level pLevel, BlockPos pos) {
        super(ModEntities.STRING_ANCHOR_ENTITY.get(), pLevel);
        this.setPos(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
    }

    @Override
    public void tick()
    {
        super.tick();

          
        if(getLinkedPos() != null)
        {
            if(!(this.level.getBlockEntity(this.blockPosition()) instanceof ZiplineTile))
            {
                this.discard();
            }
        }
    }


    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(LINKED_POS, BlockPos.ZERO);
        this.entityData.define(STRING_RENDER, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.put("linkPos", NbtUtils.writeBlockPos(getLinkedPos()));
        pCompound.putBoolean("string_render", getShouldStringRender());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        setStringRender(pCompound.getBoolean("string_render"));
        setLinkedPos(NbtUtils.readBlockPos(pCompound.getCompound("linkPos")));
    }

    public void setLinkedPos(BlockPos linkedPos)
    {
        this.entityData.set(LINKED_POS, linkedPos);
    }

    public BlockPos getLinkedPos()
    {
        return this.entityData.get(LINKED_POS);
    }

    public void setStringRender(Boolean shouldRender)
    {
        this.entityData.set(STRING_RENDER, shouldRender);
    }

    public Boolean getShouldStringRender()
    {
        return this.entityData.get(STRING_RENDER);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
