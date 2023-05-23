package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ZiplinePoleTile extends BlockEntity
{
    private UUID linkedEntityUUID;
    private UUID thisAnchorUUID;

    public void setThisAnchor(UUID thisAnchorUUID) {
        this.thisAnchorUUID = thisAnchorUUID;
    }

    public ZiplinePoleTile(BlockPos pWorldPosition, BlockState pBlockState)
    {
        super(ModTileEntities.ZIPLINE_POLE_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void setLinkedEntityUUID(UUID linkedEntityUUID)
    {
        this.linkedEntityUUID = linkedEntityUUID;
    }

    public void setThisAnchorUUID(UUID thisAnchorUUID)
    {
        this.thisAnchorUUID = thisAnchorUUID;
    }


    public ZiplineStringAnchor getThisAnchor(ServerLevel serverLevel)
    {
        if(serverLevel.getEntity(thisAnchorUUID) instanceof ZiplineStringAnchor ziplineStringAnchor)
        {
            return ziplineStringAnchor;
        }
        return null;
    }

    @Nullable
    public ZiplineEntity getLinkedEntity(ServerLevel serverLevel)
    {
            if(serverLevel.getEntity(linkedEntityUUID) instanceof ZiplineEntity ziplineEntity)
            {
                return ziplineEntity;
            }

        return null;
    }

    @Nullable
    public UUID getLinkedEntityUUID()
    {
        return linkedEntityUUID;
    }

    @Nullable
    public UUID getThisAnchorUUID()
    {
        return thisAnchorUUID;
    }




    public static void tick(Level level, BlockPos pos, BlockState blockState, ZiplinePoleTile e)
    {
        if(!blockState.canSurvive(level, pos))
        {
            ZiplinePoleBlock.removeZipline(level, pos);
        }
    }



    @Override
    public void load(CompoundTag pTag)
    {
        if(pTag != null)
        {
            this.setThisAnchorUUID(pTag.getUUID("stringHook"));
            this.setLinkedEntityUUID(pTag.getUUID("linkedEntity"));
        }
        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        if(getLinkedEntityUUID() != null)
        {
            pTag.putUUID("linkedEntity", getLinkedEntityUUID());
        }

        if(getThisAnchorUUID() != null)
        {
            pTag.putUUID("stringHook", getThisAnchorUUID());
        }
        super.saveAdditional(pTag);
    }
}
