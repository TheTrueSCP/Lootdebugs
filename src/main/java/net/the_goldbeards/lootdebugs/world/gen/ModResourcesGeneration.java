package net.the_goldbeards.lootdebugs.world.gen;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.the_goldbeards.lootdebugs.world.feature.ModPlacedFeatures;

import java.util.List;
import java.util.Set;

public class ModResourcesGeneration
{
    public static void generateResources(final BiomeLoadingEvent event) {

        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        List<Holder<PlacedFeature>> baseDec =
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        if(types.contains(BiomeDictionary.Type.OVERWORLD))
        {

            baseDec.add(ModPlacedFeatures.INGREDIENTS);
            baseDec.add( ModPlacedFeatures.RED_SUGAR);
            baseDec.add( ModPlacedFeatures.MINERALS);
        }
    }
}
