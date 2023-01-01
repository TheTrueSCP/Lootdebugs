package net.the_goldbeards.lootdebugs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugGoldenEntity;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugOldEntity;
import net.the_goldbeards.lootdebugs.Server.PacketHandler;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.BeardSlot.BeardSlotCap;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Flare.FlareDataCap;
import net.the_goldbeards.lootdebugs.capability.Salute.SaluteDataCap;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModMenuTypes;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.*;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LootDebugsMain.MOD_ID)
@Mod.EventBusSubscriber
public class LootDebugsMain
{
    ResourceLocation rl = new ResourceLocation("minecraft", "textures/gui/recipe_book.png");
    public static final String MOD_ID = "lootdebugs";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public LootDebugsMain() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEntities.register(eventBus);
        ModSounds.register(eventBus);
        ModPaintings.register(eventBus);
        ModTileEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModStructures.register(eventBus);
        ModEffects.register(eventBus);
        ModPotions.register(eventBus);
        ModRecipes.register(eventBus);
        ModFluids.register(eventBus);
        ModFeatures.register(eventBus);



        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LootdebugsConfig.SPEC, "lootdebugs_config.toml");


        PacketHandler.register();

        eventBus.addListener(this::setup);

        eventBus.addListener(this::registerCapabilities);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);


    }

    public void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        FlareDataCap.register(event);
        ClassDataCap.register(event);
        SaluteDataCap.register(event);
        BeardSlotCap.register(event);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        SpawnPlacements.register(ModEntities.LOOTBUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LootbugEntity::checkLootbugSpawnRules);
        SpawnPlacements.register(ModEntities.LOOTBUG_GOLDEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LootbugGoldenEntity::checkLootbugGoldenSpawnRules);
        SpawnPlacements.register(ModEntities.LOOTBUG_OLD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LootbugOldEntity::checkLootbugOldSpawnRules);

       /* event.enqueueWork(() -> {

            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.WATER,
                    ModItems.GLYPHID_SLAMMER.get(), ModPotions.DRUNKNESS_POTION.get()));
        });*/
    }




    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }





}
