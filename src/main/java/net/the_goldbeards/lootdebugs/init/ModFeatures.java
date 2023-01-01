package net.the_goldbeards.lootdebugs.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.world.feature.custom.SimpleIngredientsGeneration.SimpleIngredientsConfiguration;
import net.the_goldbeards.lootdebugs.world.feature.custom.SimpleIngredientsGeneration.SimpleIngredientsFeature;

public class ModFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURE
            = DeferredRegister.create(ForgeRegistries.FEATURES, LootDebugsMain.MOD_ID);

    public static final RegistryObject<Feature<SimpleIngredientsConfiguration>> SIMPLE_INGEREDIENT_GENERATION = FEATURE.register("simple_ingredient_feature",
            () -> new SimpleIngredientsFeature(SimpleIngredientsConfiguration.CODEC));

    public static void register(IEventBus eventBus)
    {
        FEATURE.register(eventBus);
    }
}
