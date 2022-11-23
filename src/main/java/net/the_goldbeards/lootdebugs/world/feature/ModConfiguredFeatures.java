package net.the_goldbeards.lootdebugs.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.the_goldbeards.lootdebugs.init.ModBlocks;

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

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_BISMOR_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.BISMOR_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.BISMOR_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_OIL_SHALE_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.OIL_SHALE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.OIL_SHALE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_CROPPER_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.CROPPER_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.CROPPER_ORE.get().defaultBlockState()));


    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> NITRA_ORE = FeatureUtils.register("nitra_ore_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_NITRA_ORES, 12, 0.0F));

    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> MORKITE_ORE = FeatureUtils.register("morkite_ore_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_MORKITE_ORES, 8, 0.0f));

    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> DYSTRUM_ORE = FeatureUtils.register("dystrum_ore_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_DYSTRUM_ORES, 6, 0.0f));

    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> OIL_SHALE_ORE = FeatureUtils.register("oil_shale_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_OIL_SHALE_ORES, 17, 0.0f));



    public static final Holder<? extends ConfiguredFeature<OreConfiguration, ?>> CROPPER_ORE = FeatureUtils.register("cropper_configured",
            Feature.ORE, new OreConfiguration(OVERWORLD_CROPPER_ORES,  5,0.0f));



    public static final Holder<? extends ConfiguredFeature<RandomPatchConfiguration, ?>>Ingredients = FeatureUtils.register("ingredients_configured",//Set, which plant has more weight
           Feature.RANDOM_PATCH, new RandomPatchConfiguration(5, 10, 4, //32 Default -> Test with 64 tries
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                            .add(ModBlocks.BARLEY_PLANT.get().defaultBlockState(), 10)
                            .add(ModBlocks.STARCH_PLANT.get().defaultBlockState(), 8)
                            .add(ModBlocks.YEAST_PLANT.get().defaultBlockState(), 6)
                            .add(ModBlocks.MALT_PLANT.get().defaultBlockState(), 7)
                            .add(ModBlocks.APOCA_BLOOM.get().defaultBlockState(),7)
                            .add(ModBlocks.BOOLO_CAP.get().defaultBlockState(), 9))))));


    public static final Holder<? extends ConfiguredFeature<RandomPatchConfiguration, ?>>RED_SUGAR = FeatureUtils.register("red_sugar_configured",//Set, which plant has more weight
            Feature.RANDOM_PATCH, new RandomPatchConfiguration(40, 12, 12, PlacementUtils.onlyWhenEmpty(//32 Default -> Test with 64 tries
                    Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.RED_SUGAR.get())))));

    public static final Holder<? extends ConfiguredFeature<RandomPatchConfiguration, ?>> BISMOR_EXPOSED = FeatureUtils.register("bismor_exposed_configured",//Set, which plant has more weight
            Feature.RANDOM_PATCH, new RandomPatchConfiguration(11, 12, 3, PlacementUtils.onlyWhenEmpty( //32 Default -> Test with 64 tries
                    Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.BISMOR_ORE.get())))));


}
