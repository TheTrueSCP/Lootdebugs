package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Items.Tools.PingTool.PingItem;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;

public class PingEntity extends AbstractShootablePhysicsArrowLikeEntity {

    private static final EntityDataAccessor<String> DWARF_CLASS = SynchedEntityData.defineId(PingEntity.class, EntityDataSerializers.STRING);

    private boolean lock = false;
    private ItemStack pingItem;

    public PingEntity(EntityType<? extends PingEntity> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }


    public PingEntity( LivingEntity pShooter, Level p_36719_, ItemStack pingItem) {
        super(ModEntities.PING_ENTITY.get(), pShooter, p_36719_);
        this.pingItem = pingItem;

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

        if(this.getOwner() == null)
        {
            this.kill();
        }
        else if(!this.getOwner().isAlive())
        {
            this.kill();
        }

        if(this.inGround && !lock)
        {
            this.lock = true;
        }

        super.tick();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DWARF_CLASS, IClassData.Classes.LeafLover.name());
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

    public IClassData.Classes getDwarfClass()
    {
        return IClassData.Classes.valueOf(this.entityData.get(DWARF_CLASS));
    }

    public void setDwarfClass(IClassData.Classes dwarfClass) {
        this.entityData.set(DWARF_CLASS, dwarfClass.name());
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {

        if(this.getOwner() != null)
        {
            if (!pResult.getEntity().is(this.getOwner())) {
                if (pResult.getEntity() instanceof LivingEntity LE) {
                    LE.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 10));

                    this.getOwner().playSound(this.entityPingSound(LE), 1, 1);

                    if (pingItem != null) {
                        if (pingItem.getItem() instanceof PingItem) {

                            CompoundTag tag = new CompoundTag();
                            tag.putFloat("lootdebugs.pingitem.pingentity.id", pResult.getEntity().getId());
                            pingItem.setTag(tag);
                        }


                    }
                }

            }
        }

        this.kill();

        super.onHitEntity(pResult);

    }

    private SoundEvent entityPingSound(LivingEntity le)
    {
        if(le.getType() == ModEntities.LOOTBUG.get())
        {
            if(LootdebugsConfig.LOOTBUG_PACIFIST_MODE.get())
            {
                return ModSounds.PING_LOOTBUG_PACIFIST.get();
            }
            else
            {
                return ModSounds.PING_LOOTBUG_NORMAL.get();
            }
        }
        if(le.getType() == ModEntities.LOOTBUG.get())
        {
            if(LootdebugsConfig.LOOTBUG_PACIFIST_MODE.get())
            {
                return ModSounds.PING_LOOTBUG_PACIFIST.get();
            }
            else
            {
                return ModSounds.PING_LOOTBUG_NORMAL.get();
            }
        }
        else
        {
            return ModSounds.PING_MARK.get();
        }
    }



    private SoundEvent playSpecificBlockHitSound(Block block)
    {

      if(block == ModBlocks.MORKITE_BLOCK.get() || block == ModBlocks.MORKITE_ORE.get() || block == ModBlocks.DEEPSLATE_MORKITE_ORE.get())
      {
          return ModSounds.PING_MORKITE.get();
      }
      else if(block == Blocks.DIRT || block == Blocks.DIRT_PATH)
      {
          return ModSounds.PING_DIRT.get();
      }
      else if(block == ModBlocks.DYSTRUM_BLOCK.get() || block == ModBlocks.DYSTRUM_ORE.get())
      {
          return ModSounds.PING_DYSTRUM.get();
      }
      else if(block == ModBlocks.RED_SUGAR.get())
      {
          return ModSounds.PING_RED_SUGAR.get();
      }
      else if( block == Blocks.GOLD_ORE || block == Blocks.NETHER_GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE || block == Blocks.RAW_GOLD_BLOCK)
      {
          return ModSounds.PING_GOLD.get();
      }
      else if( block == Blocks.GOLD_BLOCK)
      {
          return ModSounds.PING_GOLD_WERE_RICH.get();
      }
      else if(block == ModBlocks.NITRA_ORE.get() || block == ModBlocks.NITRA_BLOCK.get())
      {
          return ModSounds.PING_NITRA.get();
      }
      else if(block == Blocks.MUSHROOM_STEM || block == Blocks.BROWN_MUSHROOM_BLOCK || block == Blocks.BROWN_MUSHROOM || block == Blocks.RED_MUSHROOM_BLOCK || block == Blocks.RED_MUSHROOM || block == Blocks.POTTED_BROWN_MUSHROOM ||block == Blocks.POTTED_RED_MUSHROOM)
      {
          return ModSounds.PING_MUSHROOM.get();
      }
      else if(block == Blocks.GRAVEL)
      {
          return ModSounds.PING_WORTHLESS.get();
      }
      else
      {
          return ModSounds.PING_SETMARK.get();
      }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.setGlowingTag(true);
        this.lastState = this.level.getBlockState(blockHitResult.getBlockPos());
        super.onHitBlock(blockHitResult);
        Vec3 vec3 = blockHitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        if(this.getOwner() != null)
        {
            this.getOwner().playSound(playSpecificBlockHitSound(this.level.getBlockState(blockHitResult.getBlockPos()).getBlock()), 1.0F, 1);
        }
        this.inGround = true;
    }

    @Override
    protected boolean shouldFall() {

        return !lock && super.shouldFall();
    }
}
