package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModEntities;

import javax.annotation.Nullable;

public class FlareEntity extends AbstractShootablePhysicsArrowLikeEntity {

    private static final EntityDataAccessor<String> DWARF_CLASS = SynchedEntityData.defineId(FlareEntity.class, EntityDataSerializers.STRING);

    public FlareEntity(EntityType<? extends FlareEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FlareEntity(LivingEntity pShooter, Level world)
    {
        super(ModEntities.FLARE.get(),pShooter,world);

        if(pShooter instanceof Player player)
        {
            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                setDwarfClass(classCap.getDwarfClass());

            });
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DWARF_CLASS, IClassData.Classes.LeafLover.name());
    }

    public IClassData.Classes getDwarfClass()
    {
        return IClassData.Classes.valueOf(this.entityData.get(DWARF_CLASS));
    }

    public void setDwarfClass(IClassData.Classes dwarfClass) {
        this.entityData.set(DWARF_CLASS, dwarfClass.name());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.putString("dwarfClass", getDwarfClass().name());

        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        if (pCompound.contains("dwarfClass", 8))
        {
            this.setDwarfClass(IClassData.Classes.valueOf(pCompound.getString("dwarfClass")));
        }
        super.readAdditionalSaveData(pCompound);
    }

    @Nullable
    private BlockPos tryToSetFlareAroundBlock(Level pLevel, BlockPos pPos)
    {
        if (pLevel.isEmptyBlock(pPos))  {
            pLevel.setBlock(pPos, Blocks.LIGHT.defaultBlockState(), 2);
            return pPos;
        }

        else if (pLevel.isEmptyBlock(pPos.below(1)))  {
            pLevel.setBlock(pPos.below(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.below(1);
        }

        else if (pLevel.isEmptyBlock(pPos.above(1)))
        {
            pLevel.setBlock(pPos.above(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.above(1);
        }

        else if (pLevel.isEmptyBlock(pPos.north(1)))
        {
            pLevel.setBlock(pPos.north(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.north(1);
        }

        else if (pLevel.isEmptyBlock(pPos.east(1)))
        {
            pLevel.setBlock(pPos.east(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.east(1);
        }

        else if (pLevel.isEmptyBlock(pPos.south(1)))
        {
            pLevel.setBlock(pPos.south(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.south(1);
        }

        else if (pLevel.isEmptyBlock(pPos.west(1)))
        {
            pLevel.setBlock(pPos.west(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.west(1);
        }

        else
        {
            this.kill();
            return null;
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult)
    {
        super.onHitBlock(hitResult);
        tryToSetFlareAroundBlock(level, hitResult.getBlockPos());

    }
}





