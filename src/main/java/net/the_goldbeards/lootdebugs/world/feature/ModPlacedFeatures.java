package net.the_goldbeards.lootdebugs.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures
{
    public static final PlacementModifier RANGE_BOTTOM_TO_60 = HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(60));



    //Ores
    public static final Holder<PlacedFeature> NITRA_ORE_PLACED = PlacementUtils.register("nitra_ore_placed",
            ModConfiguredFeatures.NITRA_ORE, ModOrePlacement.commonOrePlacement(6,  // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(120))));



    public static final Holder<PlacedFeature> MORKITE_ORE_PLACED = PlacementUtils.register("morkite_ore_placed",
            ModConfiguredFeatures.MORKITE_ORE, ModOrePlacement.commonOrePlacement(4, // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(90))));

    public static final Holder<PlacedFeature> DYSTRUM_ORE_PLACED = PlacementUtils.register("dystrum_ore_placed",
            ModConfiguredFeatures.DYSTRUM_ORE, (ModOrePlacement.commonOrePlacement(5, // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(60)))));

    public static final Holder<PlacedFeature> OIL_SHALE_ORE_PLACED = PlacementUtils.register("oil_shale_ore_placed",
            ModConfiguredFeatures.OIL_SHALE_ORE, (ModOrePlacement.commonOrePlacement(4, // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0),VerticalAnchor.aboveBottom(40)))));

    public static final Holder<PlacedFeature> CROPPER_ORE_PLACED = PlacementUtils.register("cropper_ore_placed",
            ModConfiguredFeatures.CROPPER_ORE, (ModOrePlacement.commonOrePlacementInSquare(4,2, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(-30),VerticalAnchor.absolute(0)))));





    public static final Holder<PlacedFeature> INGREDIENTS = PlacementUtils.register("ingredients_placed",
            ModConfiguredFeatures.Ingredients, RarityFilter.onAverageOnceEvery(1) ,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-60), VerticalAnchor.aboveBottom(110)));

    public static final Holder<PlacedFeature> RED_SUGAR = PlacementUtils.register("red_sugar_placed",
            ModConfiguredFeatures.RED_SUGAR, RarityFilter.onAverageOnceEvery(3), HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-60), VerticalAnchor.aboveBottom(100)));

    public static final Holder<PlacedFeature> BISMOR_EXPOSED = PlacementUtils.register("bismor_placed",
            ModConfiguredFeatures.BISMOR_EXPOSED, RarityFilter.onAverageOnceEvery(4), HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(110)));//old 60



}
