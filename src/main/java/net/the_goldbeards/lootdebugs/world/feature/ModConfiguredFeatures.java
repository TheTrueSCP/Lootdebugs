package net.the_goldbeards.lootdebugs.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluids;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModFeatures;
import net.the_goldbeards.lootdebugs.init.ModFluids;
import net.the_goldbeards.lootdebugs.world.feature.custom.SimpleIngredientsGeneration.SimpleIngredientsConfiguration;

import java.util.List;

public class ModConfiguredFeatures
{
    public static final RuleTest AIR = new BlockMatchTest(Blocks.AIR);

    //Define, what kind of Stone should be replaced with the Right Modblock
    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_NITRA_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.NITRA_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_NITRA_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_MORKITE_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.MORKITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_MORKITE_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_DYSTRUM_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.DYSTRUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DYSTRUM_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_OIL_SHALE_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.OIL_SHALE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.OIL_SHALE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_CROPPER_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.CROPPER_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.CROPPER_ORE.get().defaultBlockState()));


    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> NITRA_ORE = FeatureUtils.register("nitra_ore_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_NITRA_ORES, 8, 0.0F));

    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> MORKITE_ORE = FeatureUtils.register("morkite_ore_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_MORKITE_ORES, 8, 0.0f));

    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> DYSTRUM_ORE = FeatureUtils.register("dystrum_ore_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_DYSTRUM_ORES, 6, 0.0f));

    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> OIL_SHALE_ORE = FeatureUtils.register("oil_shale_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_OIL_SHALE_ORES, 6, 0.0f));



    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> CROPPER_ORE = FeatureUtils.register("cropper_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_CROPPER_ORES,  5,0.0f));



    public static final Holder<? extends ConfiguredFeature<SimpleIngredientsConfiguration, ?>> INGREDIENTS = FeatureUtils.register("ingredients_configured",//Set, which plant has more weight
            ModFeatures.SIMPLE_INGEREDIENT_GENERATION.get(), new SimpleIngredientsConfiguration(PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,//32 Default -> Test with 64 tries
                     new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                            .add(ModBlocks.BARLEY_PLANT.get().defaultBlockState(), 10)
                            .add(ModBlocks.STARCH_PLANT.get().defaultBlockState(), 8)
                            .add(ModBlocks.YEAST_PLANT.get().defaultBlockState(), 6)
                            .add(ModBlocks.MALT_PLANT.get().defaultBlockState(), 7)
                            .add(ModBlocks.APOCA_BLOOM.get().defaultBlockState(),7)
                            .add(ModBlocks.BOOLO_CAP.get().defaultBlockState(), 9))))));


    public static final Holder<? extends ConfiguredFeature<SimpleIngredientsConfiguration, ?>> RED_SUGAR = FeatureUtils.register("red_sugar_configured",//Set, which plant has more weight
            ModFeatures.SIMPLE_INGEREDIENT_GENERATION.get(), new SimpleIngredientsConfiguration(  PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 3), BlockStateProvider.simple(ModBlocks.RED_SUGAR.get())), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO), BlockPredicate.wouldSurvive(ModBlocks.RED_SUGAR.get().defaultBlockState(), BlockPos.ZERO))))));


    public static final Holder<? extends ConfiguredFeature<SimpleIngredientsConfiguration, ?>> MINERALS = FeatureUtils.register("minerals_configured",//Set, which plant has more weight
            ModFeatures.SIMPLE_INGEREDIENT_GENERATION.get(), new SimpleIngredientsConfiguration(
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                            .add(ModBlocks.CROPPER_ORE.get().defaultBlockState(), 10)
                            .add(ModBlocks.BISMOR_ORE.get().defaultBlockState(), 10))))));



    public static final Holder<? extends ConfiguredFeature<SpringConfiguration, ?>>LIQUID_MORKITE_SPRING = FeatureUtils.register("liquid_morkite_spring_configured",
            Feature.SPRING, new SpringConfiguration(ModFluids.LIQUID_MORKITE.get().defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, Blocks.DEEPSLATE)));

    public static final Holder<? extends ConfiguredFeature<LakeFeature.Configuration, ?>>LIQUID_MORKITE_LAKE = FeatureUtils.register("liquid_morkite_lake_configured",
            Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(ModFluids.LIQUID_MORKITE_BLOCK.get().defaultBlockState()), BlockStateProvider.simple(Blocks.DEEPSLATE)));

}
