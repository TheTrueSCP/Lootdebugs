package net.the_goldbeards.lootdebugs.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugGoldenEntity;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugOldEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.*;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineMoveEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
public class ModEntities {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, LootDebugsMain.MOD_ID);

//Mobs
    public static final RegistryObject<EntityType<LootbugEntity>> LOOTBUG =
            ENTITY_TYPES.register("lootbug", () -> EntityType.Builder.<LootbugEntity>of(LootbugEntity::new, MobCategory.CREATURE).sized(1.85f, 1.1f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "lootbug").toString()));

    public static final RegistryObject<EntityType<LootbugGoldenEntity>> LOOTBUG_GOLDEN =
            ENTITY_TYPES.register("lootbug_golden", () -> EntityType.Builder.<LootbugGoldenEntity>of(LootbugGoldenEntity::new, MobCategory.CREATURE).sized(1.85f, 1.1f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "lootbug_golden").toString()));

    public static final RegistryObject<EntityType<LootbugOldEntity>> LOOTBUG_OLD =
            ENTITY_TYPES.register("lootbug_old", () -> EntityType.Builder.<LootbugOldEntity>of(LootbugOldEntity::new, MobCategory.CREATURE).sized(2.78f, 1.8f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "lootbug_old").toString()));


    //Shoots
    public static final RegistryObject<EntityType<FoamEntity>> FOAM =
            ENTITY_TYPES.register("foam", () -> EntityType.Builder.<FoamEntity>of(FoamEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "foam").toString()));

    public static final RegistryObject<EntityType<FlareEntity>> FLARE =
            ENTITY_TYPES.register("flare", () -> EntityType.Builder.<FlareEntity>of(FlareEntity::new, MobCategory.MISC).sized(0.5f, 0.7f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "flare").toString()));

    public static final RegistryObject<EntityType<ShootFlareEntity>> SHOOT_FLARE =
            ENTITY_TYPES.register("shoot_flare", () -> EntityType.Builder.<ShootFlareEntity>of(ShootFlareEntity::new, MobCategory.MISC).sized(0.8f, 0.5f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "shoot_flare").toString()));

    public static final RegistryObject<EntityType<GrapplingHookHookEntity>> GRAPPLING_HOOK_HOOK =
            ENTITY_TYPES.register("grappling_hook_hook", () -> EntityType.Builder.<GrapplingHookHookEntity>of(GrapplingHookHookEntity::new, MobCategory.MISC).sized(0.5f, 0.8f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "grappling_hook_hook").toString()));

    public static final RegistryObject<EntityType<ShieldEntity>> SHIELD_ENTITY =
            ENTITY_TYPES.register("shield", () -> EntityType.Builder.<ShieldEntity>of(ShieldEntity::new, MobCategory.MISC).sized(0.5f, 0.8f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "shield").toString()));

    public static final RegistryObject<EntityType<PingEntity>> PING_ENTITY =
            ENTITY_TYPES.register("ping", () -> EntityType.Builder.<PingEntity>of(PingEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "ping").toString()));

    public static final RegistryObject<EntityType<ZiplineMoveEntity>> ZIPLINE_MOVE_ENTITY =
            ENTITY_TYPES.register("zipline_move", () -> EntityType.Builder.<ZiplineMoveEntity>of(ZiplineMoveEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "zipline_move").toString()));

    public static final RegistryObject<EntityType<ZiplineEntity>> ZIPLINE_ENTITY =
            ENTITY_TYPES.register("zipline", () -> EntityType.Builder.<ZiplineEntity>of(ZiplineEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "zipline").toString()));

    public static final RegistryObject<EntityType<ZiplineStringAnchor>> STRING_ANCHOR_ENTITY =
            ENTITY_TYPES.register("zipline_anchor", () -> EntityType.Builder.<ZiplineStringAnchor>of(ZiplineStringAnchor::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "zipline_anchor").toString()));





    //Weapons
    public static final RegistryObject<EntityType<SatchelChargeEntity>> SATCHEL_CHARGE =
            ENTITY_TYPES.register("satchel_charge", () -> EntityType.Builder.<SatchelChargeEntity>of(SatchelChargeEntity::new, MobCategory.MISC).sized(0.5f, 0.2f).build(new ResourceLocation(LootDebugsMain.MOD_ID, "satchel_charge").toString()));


    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }

}
