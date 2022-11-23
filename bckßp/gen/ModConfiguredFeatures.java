package net.the_goldbeards.lootdebugs.world.gen;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.fml.common.Mod;
import net.the_goldbeards.lootdebugs.init.ModBlocks;

public class ModConfiguredFeatures
{
    public static final ConfiguredFeature< ?, ?> Ingredients = Feature.FLOWER.withConfiguration((
                    new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider()
                            .addWeightedBlockstate(ModBlocks.BARLEY_PLANT.get().getDefaultState(), 8)
                            .addWeightedBlockstate(ModBlocks.STARCH_PLANT.get().getDefaultState(), 6)
                            .addWeightedBlockstate(ModBlocks.YEAST_PLANT.get().getDefaultState(), 4)
                            .addWeightedBlockstate(ModBlocks.MALT_PLANT.get().getDefaultState(), 5)
                            .addWeightedBlockstate(ModBlocks.APOCA_BLOOM.get().getDefaultState(), 5)
                            .addWeightedBlockstate(ModBlocks.BOOLO_CAP.get().getDefaultState(), 6),
                            SimpleBlockPlacer.PLACER)).tries(20).build())
            .withPlacement(Features.Placements.VEGETATION_PLACEMENT).count(2);

    public static final ConfiguredFeature< ?, ?> Collectables = Feature.FLOWER.withConfiguration((
                    new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider()
                            .addWeightedBlockstate(ModBlocks.APOCA_BLOOM.get().getDefaultState(), 1)
                            .addWeightedBlockstate(ModBlocks.BOOLO_CAP.get().getDefaultState(), 1),
                            SimpleBlockPlacer.PLACER)).tries(10).build())
            .withPlacement(Features.Placements.VEGETATION_PLACEMENT).count(1);

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
