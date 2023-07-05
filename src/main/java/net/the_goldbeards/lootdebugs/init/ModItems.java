package net.the_goldbeards.lootdebugs.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.Items.Armor.ModDrillerMK1ArmorItem;
import net.the_goldbeards.lootdebugs.Items.Armor.ModEngineerMK1ArmorItem;
import net.the_goldbeards.lootdebugs.Items.Armor.ModGunnerMK1ArmorItem;
import net.the_goldbeards.lootdebugs.Items.Armor.ModScoutMK1ArmorItem;
import net.the_goldbeards.lootdebugs.Items.Drinks.*;
import net.the_goldbeards.lootdebugs.Items.Food.RedSugarItem;
import net.the_goldbeards.lootdebugs.Items.Fuel.FuelCanisterItem;
import net.the_goldbeards.lootdebugs.Items.Materials.ModArmorMaterials;
import net.the_goldbeards.lootdebugs.Items.Materials.ModTiers;
import net.the_goldbeards.lootdebugs.Items.TileEntities.FuelRefineryItem;
import net.the_goldbeards.lootdebugs.Items.Tools.CompanyStandardPickaxe.CompanyPickAxeItem;
import net.the_goldbeards.lootdebugs.Items.Tools.Drills.DrillsItem;
import net.the_goldbeards.lootdebugs.Items.Tools.FlareGun.FlareGunItem;
import net.the_goldbeards.lootdebugs.Items.Tools.GrapplingHook.GrapplingHookItem;
import net.the_goldbeards.lootdebugs.Items.Tools.OmmoranHearthstoneLocator.OmmoranHeartstoneLocator;
import net.the_goldbeards.lootdebugs.Items.Tools.PingTool.PingItem;
import net.the_goldbeards.lootdebugs.Items.Tools.PlatformGun.PlatformGunItem;
import net.the_goldbeards.lootdebugs.Items.Tools.ResupplyCaller.ResupplyCallerItem;
import net.the_goldbeards.lootdebugs.Items.Tools.SatchelCharge.SatchelChargeDetonatorItem;
import net.the_goldbeards.lootdebugs.Items.Tools.SatchelCharge.SatchelChargeItem;
import net.the_goldbeards.lootdebugs.Items.Tools.Shield.ShieldItem;
import net.the_goldbeards.lootdebugs.Items.Tools.Turret.TurretAmmoItem;
import net.the_goldbeards.lootdebugs.Items.Tools.Turret.TurretItem;
import net.the_goldbeards.lootdebugs.Items.Tools.Zipline.ZiplineItem;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.ModGroup;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;

import static net.minecraft.world.item.Items.BUCKET;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LootDebugsMain.MOD_ID);


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

//Important Stuff
public static final RegistryObject<Item> LOGO = ITEMS.register("logo",
        () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> MINING_SHANTY = ITEMS.register("mining_shanty",
            () -> new RecordItem(15, ModSounds.MINING_SHANTY, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> JET = ITEMS.register("jet",
            () -> new Item( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> OMMORAN_HEARTSTONE_LOCATOR = ITEMS.register("ommoran_heartstone_locator",
            () -> new OmmoranHeartstoneLocator( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Ammo
    public static final RegistryObject<Item> PLATFORM_GUN_AMMO = ITEMS.register("platform_gun_ammo",
            () -> new Item( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> TURRET_AMMO = ITEMS.register("turret_ammo",
            () -> new TurretAmmoItem( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> FLARE_GUN_AMMO = ITEMS.register("flare_gun_ammo",
            () -> new Item( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> ZIPLINE_AMMO = ITEMS.register("zipline_ammo",
            () -> new Item( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> FUEL_CANISTER = ITEMS.register("fuel_canister",
            () -> new FuelCanisterItem( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Buckets
    public static final RegistryObject<Item> LIQUID_MORKITE_BUCKET = ITEMS.register("liquid_morkite_bucket",
            () -> new BucketItem(ModFluids.LIQUID_MORKITE,new Item.Properties().craftRemainder(BUCKET).stacksTo(1).tab(ModGroup.LOOTDEBUGS_TAB)));


    //Nothing
    public static final RegistryObject<Item> EMPTY = ITEMS.register("empty",
            () -> new Item(new Item.Properties()));


    //Tile Entities

    public static final RegistryObject<Item> FUEL_REFINERY = ITEMS.register("fuel_refinery",
            () -> new FuelRefineryItem(ModBlocks.FUEL_REFINERY.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Tools
    public static final RegistryObject<Item> COMPANY_STANDARD_PICKAXE = ITEMS.register("company_standard_pickaxe",
            () -> new CompanyPickAxeItem(ModTiers.COMPANY_STANDARD,3,4, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> DRILLS = ITEMS.register("drills",
            () -> new DrillsItem(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> PLATFORM_GUN = ITEMS.register("platform_gun",
            () -> new PlatformGunItem(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> FLARE_GUN = ITEMS.register("flare_gun",
            () -> new FlareGunItem( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> GRAPPLING_HOOK = ITEMS.register("grappling_hook",
            () -> new GrapplingHookItem( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> LASER_POINTER = ITEMS.register("laser_pointer",
            () -> new PingItem( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> SATCHEL_CHARGE = ITEMS.register("satchel_charge",
            () -> new SatchelChargeItem(ModBlocks.SATCHEL_CHARGE.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> SATCHEL_CHARGE_DETONATOR = ITEMS.register("satchel_charge_detonator",
            () -> new SatchelChargeDetonatorItem(new Item.Properties()));

    public static final RegistryObject<Item> SHIELD = ITEMS.register("shield",
            () -> new ShieldItem(ModBlocks.SHIELD_EMITTER_BLOCK.get() ,new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> ZIPLINE_GUN = ITEMS.register("zipline",
            () -> new ZiplineItem(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));


    public static final RegistryObject<Item> TURRET = ITEMS.register("turret",
            () -> new TurretItem(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> RESUPPLY_CALLER = ITEMS.register("resupply_caller",
            () -> new ResupplyCallerItem(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Toolparts

    public static final RegistryObject<Item> DRILL_HEAD = ITEMS.register("drill_head",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> COIL = ITEMS.register("coil",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> POWER_CELL = ITEMS.register("power_cell",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> FLARE_IGNITER = ITEMS.register("flare_igniter",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MIXER_BARREL = ITEMS.register("mixer_barrel",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> REMOTE_RECEIVER = ITEMS.register("remote_receiver",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> ZIPLINE_LAUNCHER = ITEMS.register("zipline_launcher",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));



    //Ores
    public static final RegistryObject<Item> NITRA = ITEMS.register("nitra",
            () -> new Item( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MORKITE = ITEMS.register("morkite",
            () -> new Item( new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> DYSTRUM_INGOT = ITEMS.register("dystrum_ingot",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> RED_SUGAR = ITEMS.register("red_sugar_item",
            () -> new RedSugarItem(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB),5));

    public static final RegistryObject<Item> RAW_DYSTRUM = ITEMS.register("raw_dystrum",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));


    public static final RegistryObject<Item> RAW_BISMOR = ITEMS.register("raw_bismor",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> BISMOR_INGOT = ITEMS.register("bismor_ingot",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));


    public static final RegistryObject<Item> RAW_CROPPER = ITEMS.register("raw_cropper",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> CROPPER_INGOT = ITEMS.register("cropper_ingot",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));



//Armor

    //Driller MK1
    public static final RegistryObject<Item> MK1_DRILLER_HELMET = ITEMS.register("mk1_company_standard_driller_helmet",
            () -> new ModDrillerMK1ArmorItem.Helmet(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.HEAD, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_DRILLER_CHESTPLATE = ITEMS.register("mk1_company_standard_driller_chestplate",
            () -> new ModDrillerMK1ArmorItem.Chestplate(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.CHEST, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_DRILLER_LEGGINGS = ITEMS.register("mk1_company_standard_driller_leggings",
            () -> new ModDrillerMK1ArmorItem.Leggings(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.LEGS, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_DRILLER_BOOTS = ITEMS.register("mk1_company_standard_driller_boots",
            () -> new ModDrillerMK1ArmorItem.Boots(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.FEET, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Scout MK1

    public static final RegistryObject<Item> MK1_SCOUT_HELMET = ITEMS.register("mk1_company_standard_scout_helmet",
            () -> new ModScoutMK1ArmorItem.Helmet(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.HEAD, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_SCOUT_CHESTPLATE = ITEMS.register("mk1_company_standard_scout_chestplate",
            () -> new ModScoutMK1ArmorItem.Chestplate(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.CHEST, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_SCOUT_LEGGINGS = ITEMS.register("mk1_company_standard_scout_leggings",
            () -> new ModScoutMK1ArmorItem.Leggings(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.LEGS, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_SCOUT_BOOTS = ITEMS.register("mk1_company_standard_scout_boots",
            () -> new ModScoutMK1ArmorItem.Boots(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.FEET, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Engineer MK1

    public static final RegistryObject<Item> MK1_ENGINEER_HELMET = ITEMS.register("mk1_company_standard_engineer_helmet",
            () -> new ModEngineerMK1ArmorItem.Helmet(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.HEAD, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_ENGINEER_CHESTPLATE = ITEMS.register("mk1_company_standard_engineer_chestplate",
            () -> new ModEngineerMK1ArmorItem.Chestplate(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.CHEST, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_ENGINEER_LEGGINGS = ITEMS.register("mk1_company_standard_engineer_leggings",
            () -> new ModEngineerMK1ArmorItem.Leggings(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.LEGS, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_ENGINEER_BOOTS = ITEMS.register("mk1_company_standard_engineer_boots",
            () -> new ModEngineerMK1ArmorItem.Boots(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.FEET, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Engineer MK1

    public static final RegistryObject<Item> MK1_GUNNER_HELMET = ITEMS.register("mk1_company_standard_gunner_helmet",
            () -> new ModGunnerMK1ArmorItem.Helmet(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.HEAD, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_GUNNER_CHESTPLATE = ITEMS.register("mk1_company_standard_gunner_chestplate",
            () -> new ModGunnerMK1ArmorItem.Chestplate(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.CHEST, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_GUNNER_LEGGINGS = ITEMS.register("mk1_company_standard_gunner_leggings",
            () -> new ModGunnerMK1ArmorItem.Leggings(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.LEGS, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MK1_GUNNER_BOOTS = ITEMS.register("mk1_company_standard_gunner_boots",
            () -> new ModGunnerMK1ArmorItem.Boots(ModArmorMaterials.MK1_COMPANY_STANDARD_DRILLER, EquipmentSlot.FEET, new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));



    public static final RegistryObject<Item> NITRA_ON_A_STICK = ITEMS.register("nitra_on_a_stick",
            () -> new Item(new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB).stacksTo(1)));


    //Spawn Eggs
    public static final RegistryObject<ForgeSpawnEggItem> LOOTBUG_SPAWN_EGG = ITEMS.register("lootbug_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LOOTBUG,0xCFB166,0xCFB166,new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> LOOTBUG_GOLDEN_SPAWN_EGG = ITEMS.register("lootbug_golden_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LOOTBUG_GOLDEN,0xFFEC00,0xCFB166,new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));


    //Manual ItemBlock Registration
    public static final RegistryObject<Item> OMM0RAN_HEARTSTONE = ITEMS.register("ommoran_hearthstone",
            () -> new BlockItem(ModBlocks.OMMORAN_HEARTSTONE.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> GLYPHID_SHIT = ITEMS.register("glyphid_shit",
            () -> new BlockItem(ModBlocks.GLYPHID_SHIT.get(), new Item.Properties()));

    public static final RegistryObject<Item> TEST_BLOCK = ITEMS.register("test_block",
            () -> new BlockItem(ModBlocks.TEST_BLOCK.get(), new Item.Properties()));


    public static final RegistryObject<Item> OIL_SHALE = ITEMS.register("oil_shale",
            () -> new BlockItem(ModBlocks.OIL_SHALE.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    //Drinks and Ingredients

    public static final RegistryObject<Item> LOOTBUG_GOLD = ITEMS.register("lootbug_gold",
            () -> new LootbugGoldItem(ModBlocks.LOOTBUG_GOLD.get(), new Item.Properties()));

    public static final RegistryObject<Item> OILY_OAF= ITEMS.register("oily_oaf",
            () -> new OilyOafItem(ModBlocks.OILY_OAF.get(),new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> LEAF_LOVER_SPECIAL = ITEMS.register("leaf_lover_special",
            () -> new LeafLoverSpecialItem(ModBlocks.LEAF_LOVER_SPECIAL.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MUG = ITEMS.register("mug",
            () -> new MugItem(ModBlocks.MUG.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> SKULL_CRUSHER = ITEMS.register("skull_crusher",
            () -> new SkullCrusherItem(ModBlocks.SKULL_CRUSHER.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> TUNNEL_RAT = ITEMS.register("tunnel_rat",
            () -> new TunnelRatItem(ModBlocks.TUNNEL_RAT.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> BLACKOUT_STOUT = ITEMS.register("blackout_stout",
            () -> new BlackoutStoutItem(ModBlocks.BLACKOUT_STOUT.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> ARKENSTOUT = ITEMS.register("arkenstout",
            () -> new ArkenStoutItem(ModBlocks.ARKENSTOUT.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> FLINTLOCKES_DELIGHT = ITEMS.register("flintlockes_delight",
            () -> new FlintLockesDelightItem(ModBlocks.FLINTLOCKES_DELIGHT.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> BACKBREAKER_STOUT = ITEMS.register("backbreaker_stout",
            () -> new BackbreakerStoutItem(ModBlocks.BACKBREAKER_STOUT.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> DARK_MORKITE = ITEMS.register("dark_morkite",
            () -> new DarkMorkiteItem(ModBlocks.DARK_MORKITE.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> GLYPHID_SLAMMER = ITEMS.register("glyphid_slammer",
            () -> new GlyphidSlammerItem(ModBlocks.GLYPHID_SLAMMER.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> SEASONED_MOONRIDER= ITEMS.register("seasoned_moonrider",
            () -> new SeasonedMoonriderItem(ModBlocks.SEASONED_MOONRIDER.get(),new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> SMARTSTOUT = ITEMS.register("smartstout",
            () -> new SmartStoutItem(ModBlocks.SMARTSTOUT.get(),new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));


    public static final RegistryObject<Item> BOOLO_CAP = ITEMS.register("boolo_cap",
            () -> new BlockItem(ModBlocks.BOOLO_CAP.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));


    public static final RegistryObject<Item> APOCA_BLOOM = ITEMS.register("apoca_bloom",
            () -> new BlockItem(ModBlocks.APOCA_BLOOM.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB).stacksTo(64)));

    public static final RegistryObject<Item> BARLEY_BULB = ITEMS.register("barley_bulb",
            () -> new BlockItem(ModBlocks.BARLEY_PLANT.get(),new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> STARCH_NUT = ITEMS.register("starch_nut",
            () -> new BlockItem(ModBlocks.STARCH_PLANT.get(),new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> MALT_STAR = ITEMS.register("malt_star",
            () -> new BlockItem(ModBlocks.MALT_PLANT.get(),new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    public static final RegistryObject<Item> YEAST_CONE = ITEMS.register("yeast_cone",
            () -> new BlockItem(ModBlocks.YEAST_PLANT.get(), new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

}
