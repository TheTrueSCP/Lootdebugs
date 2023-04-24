package net.the_goldbeards.lootdebugs.init;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.Block.Beers.*;
import net.the_goldbeards.lootdebugs.Block.GenBlocks.OreBlocks;
import net.the_goldbeards.lootdebugs.Block.GenBlocks.plants.*;
import net.the_goldbeards.lootdebugs.Block.GlyphidShitBlock;
import net.the_goldbeards.lootdebugs.Block.OmmoranHearthstone.HearthstoneBlock;
import net.the_goldbeards.lootdebugs.Block.TestBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge.SatchelChargeBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplinePoleBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal.ClassChangerBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub.PubBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.LightBlock.LightBlock;
import net.the_goldbeards.lootdebugs.Block.Tools.PlatformGun.PlascreteFoamMKI;
import net.the_goldbeards.lootdebugs.Block.Tools.PlatformGun.PlascreteFoamMKII;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Shield.ShieldBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Shield.ShieldEmitterBlock;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.ModGroup;
import net.the_goldbeards.lootdebugs.init.Sound.ModSoundTypes;

import java.util.function.Supplier;

public class ModBlocks {


    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, LootDebugsMain.MOD_ID);

    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T> block, boolean registerItem) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        if(registerItem)
        {
            registerBlockItem(name, toReturn);
        }

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModGroup.LOOTDEBUGS_TAB)));

    }

    public static void register(IEventBus eventBus) {

        BLOCKS.register(eventBus);
    }

    //Materials
    public static final Material STONE = (new Material.Builder(MaterialColor.STONE)).build();



    //Block Behaviors

    public static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    public static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }

    //Important Stuff
    public static final RegistryObject<Block> GLYPHID_SHIT = registryBlock("glyphid_shit",
            () -> new GlyphidShitBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1.5f)),false);

    public static final RegistryObject<Block> TEST_BLOCK = registryBlock("test_block",
            () -> new TestBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4f)),false);

    public static final RegistryObject<Block> PLASCRETE_FOAM_MKI = registryBlock("plascrete_foam",
            () -> new PlascreteFoamMKI(BlockBehaviour.Properties.of(Material.STONE).strength(0.15f).sound(ModSoundTypes.PLASCRETE_FOAM).noDrops()),true);


    public static final RegistryObject<Block> PLASCRETE_FOAM_MKII = registryBlock("plascrete_foam_mk2",
            () -> new PlascreteFoamMKII(BlockBehaviour.Properties.of(Material.STONE).strength(0.15f).sound(ModSoundTypes.PLASCRETE_FOAM).noDrops()),true);

    public static final RegistryObject<Block> LIGHT_BLOCK = registryBlock("light_block",
            () -> new LightBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).instabreak().noCollission().noDrops()),false);

    public static final RegistryObject<Block> SATCHEL_CHARGE = registryBlock("satchel_charge",
           () -> new SatchelChargeBlock(BlockBehaviour.Properties.of(Material.STONE)),false);

    public static final RegistryObject<Block> ZIPLINE_POLE_BLOCK = registryBlock("zipline_pole_block",
            () -> new ZiplinePoleBlock(BlockBehaviour.Properties.of(Material.STONE).isSuffocating(ModBlocks::never).strength(2f).noOcclusion().isViewBlocking(ModBlocks::never)),false);




    //Shield
    public static final RegistryObject<Block> SHIELD_BLOCK = registryBlock("shield_block",
            () -> new ShieldBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).strength(0.3F,1200000.0f).noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never).noDrops()),false);

    public static final RegistryObject<Block> SHIELD_EMITTER_BLOCK = registryBlock("shield_emitter_block",
            () -> new ShieldEmitterBlock(BlockBehaviour.Properties.of(Material.STONE)),false);

// Ores

    public static final RegistryObject<Block> MORKITE_ORE =registryBlock("morkite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(0.9f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> DEEPSLATE_MORKITE_ORE =registryBlock("deepslate_morkite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.3f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> MORKITE_BLOCK =registryBlock("morkite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.0f)),false);


    public static final RegistryObject<Block> NITRA_ORE = registryBlock("nitra_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> DEEPSLATE_NITRA_ORE =registryBlock("deepslate_nitra_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.3f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> NITRA_BLOCK = registryBlock("nitra_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f)),true);


    public static final RegistryObject<Block> RAW_DYSTRUM_BLOCK = registryBlock("raw_dystrum_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> DYSTRUM_ORE = registryBlock("dystrum_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> DEEPSLATE_DYSTRUM_ORE = registryBlock("deepslate_dystrum_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> DYSTRUM_BLOCK = registryBlock("dystrum_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f)),true);



    public static final RegistryObject<Block> BISMOR_ORE = registryBlock("bismor_ore",
            () -> new OreBlocks(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> BISMOR_BLOCK = registryBlock("bismor_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f)),true);



    public static final RegistryObject<Block> CROPPER_ORE = registryBlock("cropper_ore",
            () -> new OreBlocks(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> CROPPER_BLOCK = registryBlock("cropper_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5f)),true);


    public static final RegistryObject<Block> RED_SUGAR = registryBlock("red_sugar",
            () -> new RedSugarBlock(BlockBehaviour.Properties.of(Material.STONE).strength(0.5f).lightLevel((state) -> 8).sound(ModSoundTypes.GENERIC_MINERAL)),true);

    public static final RegistryObject<Block> OIL_SHALE = registryBlock("oil_shale",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST).strength(4f).requiresCorrectToolForDrops().lightLevel((state) -> 8)),false);



    //Ommoran Hearhstone
    public static final RegistryObject<Block> OMMORAN_HEARTHSTONE = registryBlock("ommoran_hearthstone",
            () -> new HearthstoneBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak().lightLevel((state) -> 30)), false);

    public static final RegistryObject<Block> OMMORAN_LAYER_4_INNERST = registryBlock("ommoran_layer_4_innerst",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(100f).lightLevel((state) -> 12)), true);

    public static final RegistryObject<Block> OMMORAN_LAYER_3 = registryBlock("ommoran_layer_3",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(150f).lightLevel((state) -> 10)), true);

    public static final RegistryObject<Block> OMMORAN_LAYER_2 = registryBlock("ommoran_layer_2",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(200f).lightLevel((state) -> 9)), true);

    public static final RegistryObject<Block> OMMORAN_LAYER_1_OUTERST = registryBlock("ommoran_layer_1_outerst",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(300f).lightLevel((state) -> 6)),true);

   // public static final RegistryObject<Block> HEARTHSTONE_DEFENDER = registryBlock("hearthstone_defender",
       //     () -> new HearthstoneDefenderBlock(BlockBehaviour.Properties.of(Material.METAL)), false);

    //TileEnititys

    public static final RegistryObject<Block> PUB = registryBlock("pub",
            () -> new PubBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f).lightLevel((state) -> 8)),true);

    public static final RegistryObject<Block> EQUIPMENT_TABLE = registryBlock("equipment_table",
            () -> new EquipmentTableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f).lightLevel((state) -> 8)),true);

    public static final RegistryObject<Block> FUEL_REFINERY = registryBlock("fuel_refinery",
            () -> new FuelRefineryBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f).lightLevel((state) -> 8)),true);

    public static final RegistryObject<Block> CLASS_CHANGER = registryBlock("class_changer",
            () -> new ClassChangerBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f).lightLevel((state) -> 15)),true);

    ////BLocksWithoutItemRegistry


    //Drinks
    public static final RegistryObject<Block> MUG =registryBlock("mug",
            () -> new MugBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()), false);

    public static final RegistryObject<Block> SKULL_CRUSHER =registryBlock("skull_crusher",
            () -> new SkullCrusherBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()), false);

    public static final RegistryObject<Block> LEAF_LOVER_SPECIAL =registryBlock("leaf_lover_special",
            () -> new LeafLoverSpecialBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> OILY_OAF =registryBlock("oily_oaf",
            () -> new OilyOafBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> TUNNEL_RAT =registryBlock("tunnel_rat",
            () -> new TunnelRatBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> BLACKOUT_STOUT =registryBlock("blackout_stout",
            () -> new BlackoutStoutBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> ARKENSTOUT =registryBlock("arkenstout",
            () -> new ArkenStoutBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> FLINTLOCKES_DELIGHT =registryBlock("flintlockes_delight",
            () -> new FlintLockesDelightBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> BACKBREAKER_STOUT =registryBlock("backbreaker_stout",
            () -> new BackbreakerStoutBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> DARK_MORKITE =registryBlock("dark_morkite",
            () -> new DarkMorkiteBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> GLYPHID_SLAMMER =registryBlock("glyphid_slammer",
            () -> new GlyphidSlammerBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> SEASONED_MOONRIDER =registryBlock("seasoned_moonrider",
            () -> new SeasonedMoonriderBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> SMARTSTOUT =registryBlock("smartstout",
            () -> new SmartStoutBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()), false);

    public static final RegistryObject<Block> LOOTBUG_GOLD =registryBlock("lootbug_gold",
            () -> new LootbugGoldBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()), false);

    //DrinkReceips

    public static final RegistryObject<Block> BARLEY_PLANT =registryBlock("barley_plant",
            () -> new BarleyPlantBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> BOOLO_CAP =registryBlock("boolo_cap",
            () -> new BooloCapBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> YEAST_PLANT =registryBlock("yeast_plant",
            () -> new YeastPlantBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> APOCA_BLOOM =registryBlock("apoca_bloom",
            () -> new ApocaBloomBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> MALT_PLANT =registryBlock("malt_plant",
            () -> new MaltPlantBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);

    public static final RegistryObject<Block> STARCH_PLANT =registryBlock("starch_plant",
            () -> new StarchPlantBlock(BlockBehaviour.Properties.of(Material.METAL).instabreak()),false);


}
