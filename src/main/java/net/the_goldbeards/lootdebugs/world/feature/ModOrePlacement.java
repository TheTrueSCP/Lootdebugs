package net.the_goldbeards.lootdebugs.world.feature;

import net.minecraft.world.level.levelgen.placement.*;
import java.util.List;

public class ModOrePlacement
{
    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> orePlacement2(PlacementModifier p_195347_, PlacementModifier p_195348_, PlacementModifier p_19532348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_,p_19532348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacementInSquare(int ChunkDistance, int VeinsPerChunk, PlacementModifier Height) {
        return List.of(CountPlacement.of(VeinsPerChunk), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(ChunkDistance), BiomeFilter.biome(),Height);
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}
