package net.the_goldbeards.lootdebugs.Entities.Tools.Flare;

import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.Entities.Tools.AbstractShootablePhysicsArrowLikeEntity;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.util.ModTags;

import javax.annotation.Nullable;

public class FlareEntity extends AbstractShootablePhysicsArrowLikeEntity {

    private static final EntityDataAccessor<BlockPos> LIGHT_POS = SynchedEntityData.defineId(FlareEntity.class, EntityDataSerializers.BLOCK_POS);

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
    public void tick() {
        super.tick();

        if(this.isInLava())
        {
            removeLight();
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DWARF_CLASS, IClassData.Classes.LeafLover.name());
        this.entityData.define(LIGHT_POS, BlockPos.ZERO);
    }

    public IClassData.Classes getDwarfClass()
    {
        return IClassData.Classes.valueOf(this.entityData.get(DWARF_CLASS));
    }

    public void setDwarfClass(IClassData.Classes dwarfClass) {
        this.entityData.set(DWARF_CLASS, dwarfClass.name());
    }

    public void setLightPos(BlockPos pos)
    {
        this.entityData.set(LIGHT_POS, pos);
    }

    public BlockPos getLightPos()
    {
        return this.entityData.get(LIGHT_POS);
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
        if (canPlaceFlare(level, pPos))  {
            pLevel.setBlock(pPos, Blocks.LIGHT.defaultBlockState(), 2);
            return pPos;
        }

        else if (canPlaceFlare(level, pPos.below(1)))  {
            pLevel.setBlock(pPos.below(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.below(1);
        }

        else if (canPlaceFlare(level, pPos.above(1)))
        {
            pLevel.setBlock(pPos.above(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.above(1);
        }

        else if (canPlaceFlare(level, pPos.north(1)))
        {
            pLevel.setBlock(pPos.north(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.north(1);
        }

        else if (canPlaceFlare(level, pPos.east(1)))
        {
            pLevel.setBlock(pPos.east(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.east(1);
        }

        else if (canPlaceFlare(level, pPos.south(1)))
        {
            pLevel.setBlock(pPos.south(1), Blocks.LIGHT.defaultBlockState(), 2);
            return pPos.south(1);
        }

        else if (canPlaceFlare(level, pPos.west(1)))
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

    private static boolean canPlaceFlare(Level pLevel, BlockPos pPos)
    {
        return pLevel.isEmptyBlock(pPos) || pLevel.getBlockState(pPos).is(ModTags.Blocks.REPLACEABLE_BLOCKS);
    }
    
    @Override
    protected void onHitBlock(BlockHitResult hitResult)
    {


removeLight();

        setLightPos(tryToSetFlareAroundBlock(level, hitResult.getBlockPos()));
        super.onHitBlock(hitResult);
    }

    public void removeLight()
    {
        if(getLightPos() != null)
        {
            if(level.getBlockState(getLightPos()).is(Blocks.LIGHT))
            {
                level.setBlock(getLightPos(), Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public void onDespawn() {
       removeLight();


    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 1800) {
            this.onDespawn();
            this.discard();
        }
    }

    @Override
    public void kill() {
      removeLight();
        super.kill();
    }
}





