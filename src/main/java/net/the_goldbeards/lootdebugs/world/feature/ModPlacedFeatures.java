package net.the_goldbeards.lootdebugs.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures
{

    //Ores
    public static final Holder<PlacedFeature> NITRA_ORE_PLACED = PlacementUtils.register("nitra_ore_placed",
            ModConfiguredFeatures.NITRA_ORE, ModOrePlacement.commonOrePlacement(7,  // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(120))));



    public static final Holder<PlacedFeature> MORKITE_ORE_PLACED = PlacementUtils.register("morkite_ore_placed",
            ModConfiguredFeatures.MORKITE_ORE, ModOrePlacement.commonOrePlacement(5, // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(90))));

    public static final Holder<PlacedFeature> DYSTRUM_ORE_PLACED = PlacementUtils.register("dystrum_ore_placed",
            ModConfiguredFeatures.DYSTRUM_ORE, (ModOrePlacement.commonOrePlacement(4, // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(80)))));

    public static final Holder<PlacedFeature> OIL_SHALE_ORE_PLACED = PlacementUtils.register("oil_shale_ore_placed",
            ModConfiguredFeatures.OIL_SHALE_ORE, (ModOrePlacement.commonOrePlacement(2, // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0),VerticalAnchor.aboveBottom(40)))));




    public static final Holder<PlacedFeature> INGREDIENTS = PlacementUtils.register("ingredients_placed",
            ModConfiguredFeatures.INGREDIENTS, CountPlacement.of(16) ,HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(110)));


    public static final Holder<PlacedFeature> RED_SUGAR = PlacementUtils.register("red_sugar_placed",
            ModConfiguredFeatures.RED_SUGAR, CountPlacement.of(13), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(100)));


    public static final Holder<PlacedFeature> MINERALS = PlacementUtils.register("minerals_placed",
            ModConfiguredFeatures.MINERALS, CountPlacement.of(10), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(90)));

    public static final Holder<PlacedFeature> LIQUID_MORKITE_SPRING = PlacementUtils.register("liquid_morkite_spring_placed",
        ModConfiguredFeatures.LIQUID_MORKITE_SPRING, CountPlacement.of(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(40)));

    //public static final Holder<PlacedFeature> LIQUID_MORKITE_LAKE = PlacementUtils.register("liquid_morkite_lake_placed",
     //       ModConfiguredFeatures.LIQUID_MORKITE_LAKE, RarityFilter.onAverageOnceEvery(70), InSquarePlacement.spread(), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(-20))), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5), BiomeFilter.biome());


}
