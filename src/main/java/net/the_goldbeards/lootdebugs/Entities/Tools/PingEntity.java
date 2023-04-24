package net.the_goldbeards.lootdebugs.Entities.Tools;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.the_goldbeards.lootdebugs.Items.Tools.PingTool.PingItem;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.util.Config.LootdebugsServerConfig;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.util.Map;

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
    public boolean fireImmune() {
        return true;
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
        if (pCompound.contains("dwarfClass"))
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
    protected void onHitEntity(EntityHitResult pResult)
    {

        if(!level.isClientSide())
        {
            if (this.getOwner() != null) {
                if (!pResult.getEntity().is(this.getOwner()))
                {
                    if (pResult.getEntity() instanceof LivingEntity LE)
                    {
                        LE.setGlowingTag(true);

                        level.playSound(null,  pResult.getEntity().blockPosition(), ModSounds.MARK_SOUND.get(), SoundSource.PLAYERS, 1,1);
                        level.playSound(null, this.getOwner().blockPosition(),entityPingSound(LE), SoundSource.PLAYERS, 1, UsefullStuff.DwarfClasses.getSalutePitch(getDwarfClass()));

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
        }

        this.kill();

    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult)
    {
        this.setGlowingTag(true);
        this.lastState = this.level.getBlockState(blockHitResult.getBlockPos());
        super.onHitBlock(blockHitResult);
        if(!level.isClientSide())
        {
            if (this.getOwner() != null) {
                SoundEvent pingSound = getBlockPing().get(this.level.getBlockState(blockHitResult.getBlockPos()).getBlock());

                if (pingSound == null)
                {
                    pingSound = ModSounds.PING_SETMARK.get();
                }

                this.level.playSound(null,getOwner().blockPosition(), pingSound, SoundSource.PLAYERS, 1.0F, UsefullStuff.DwarfClasses.getSalutePitch(getDwarfClass()));
                this.level.playSound(null,  blockHitResult.getBlockPos(), ModSounds.MARK_SOUND.get(), SoundSource.PLAYERS, 1,1);
            }
        }
        this.inGround = true;
    }

    public static Map<Block, SoundEvent> getBlockPing()
    {
        Map<Block, SoundEvent> map = Maps.newLinkedHashMap();

        //Dirt
        map.put(Blocks.DIRT, ModSounds.PING_DIRT.get());
        map.put(Blocks.GRASS_BLOCK, ModSounds.PING_DIRT.get());
        map.put(Blocks.DIRT_PATH, ModSounds.PING_DIRT.get());

        //Iron
        map.put(Blocks.IRON_ORE, ModSounds.PING_IRON.get());
        map.put(Blocks.DEEPSLATE_IRON_ORE, ModSounds.PING_IRON.get());
        map.put(Blocks.RAW_IRON_BLOCK, ModSounds.PING_IRON.get());
        map.put(Blocks.IRON_BLOCK, ModSounds.PING_IRON.get());

        //Nitra
        map.put(ModBlocks.NITRA_ORE.get(), ModSounds.PING_NITRA.get());
        map.put(ModBlocks.DEEPSLATE_NITRA_ORE.get(), ModSounds.PING_NITRA.get());
        map.put(ModBlocks.NITRA_BLOCK.get(), ModSounds.PING_NITRA.get());

        //Gold
        map.put(Blocks.GOLD_ORE, ModSounds.PING_GOLD.get());
        map.put(Blocks.DEEPSLATE_GOLD_ORE, ModSounds.PING_GOLD.get());
        map.put(Blocks.GOLD_BLOCK, ModSounds.PING_GOLD_WERE_RICH.get());
        map.put(Blocks.RAW_GOLD_BLOCK, ModSounds.PING_GOLD_WERE_RICH.get());

        //Morkite
        map.put(ModBlocks.MORKITE_ORE.get(), ModSounds.PING_MORKITE.get());
        map.put(ModBlocks.MORKITE_BLOCK.get(), ModSounds.PING_MORKITE.get());
        map.put(ModBlocks.DEEPSLATE_MORKITE_ORE.get(), ModSounds.PING_MORKITE.get());
        map.put(ModBlocks.MORKITE_ORE.get(), ModSounds.PING_MORKITE.get());
        map.put(ModBlocks.MORKITE_BLOCK.get(), ModSounds.PING_MORKITE.get());


        //Dystrum
        map.put(ModBlocks.DYSTRUM_BLOCK.get(), ModSounds.PING_DYSTRUM.get());
        map.put(ModBlocks.DYSTRUM_ORE.get(), ModSounds.PING_DYSTRUM.get());
        map.put(ModBlocks.RAW_DYSTRUM_BLOCK.get(), ModSounds.PING_DYSTRUM.get());

        map.put(ModBlocks.RED_SUGAR.get(), ModSounds.PING_RED_SUGAR.get());

        //Worthless
        map.put(Blocks.GRAVEL, ModSounds.PING_WORTHLESS.get());
        map.put(Blocks.COBBLESTONE, ModSounds.PING_WORTHLESS.get());

        //Mushroom
        map.put(Blocks.MUSHROOM_STEM, ModSounds.PING_MUSHROOM.get());
        map.put(Blocks.BROWN_MUSHROOM_BLOCK, ModSounds.PING_MUSHROOM.get());
        map.put(Blocks.BROWN_MUSHROOM, ModSounds.PING_MUSHROOM.get());
        map.put(Blocks.RED_MUSHROOM_BLOCK, ModSounds.PING_MUSHROOM.get());
        map.put(Blocks.RED_MUSHROOM, ModSounds.PING_MUSHROOM.get());
        map.put(Blocks.POTTED_BROWN_MUSHROOM, ModSounds.PING_MUSHROOM.get());
        map.put(Blocks.POTTED_RED_MUSHROOM, ModSounds.PING_MUSHROOM.get());

        map.put(ModBlocks.OMMORAN_HEARTHSTONE.get(), ModSounds.MARK_OMMORAN_HEARTHSTONE.get());

        return map;
    }

    private static SoundEvent entityPingSound(LivingEntity le)
    {
        SoundEvent pingSoundEvent = getEntityPing(LootdebugsServerConfig.LOOTBUG_PACIFIST_MODE.get()).get(le.getType());

        if(pingSoundEvent == null && le instanceof Monster)
        {
            pingSoundEvent = ModSounds.PING_ENEMY.get();
        }

        if(pingSoundEvent == null)
        {
            pingSoundEvent = ModSounds.PING_STRANGE_SPOT.get();
        }
        return pingSoundEvent;
    }

    public static Map<EntityType<?>, SoundEvent> getEntityPing(boolean pacifist)
    {
        Map<EntityType<?>, SoundEvent> map = Maps.newLinkedHashMap();

        //Lootbug
        if(pacifist)
        {
            map.put(ModEntities.LOOTBUG.get(), ModSounds.PING_LOOTBUG_PACIFIST.get());
            map.put(ModEntities.LOOTBUG_GOLDEN.get(), ModSounds.PING_LOOTBUG_PACIFIST.get());
        }
        else
        {
            map.put(ModEntities.LOOTBUG.get(), ModSounds.PING_LOOTBUG_NORMAL.get());
            map.put(ModEntities.LOOTBUG_GOLDEN.get(), ModSounds.PING_LOOTBUG_NORMAL.get());
        }

        map.put(ModEntities.LOOTBUG_OLD.get(), ModSounds.PING_STRANGE_SPOT.get());

        //Monster
        return map;
    }

    @Override
    protected boolean shouldFall() {

        return !lock && super.shouldFall();
    }
}
