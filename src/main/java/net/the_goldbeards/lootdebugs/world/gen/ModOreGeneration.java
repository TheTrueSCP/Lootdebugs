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

public class ModOreGeneration
{
    public static void generateOres(final BiomeLoadingEvent event)
    {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        List<Holder<PlacedFeature>> base =
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        if(types.contains(BiomeDictionary.Type.OVERWORLD)) {
            base.add(ModPlacedFeatures.NITRA_ORE_PLACED);
            base.add( ModPlacedFeatures.MORKITE_ORE_PLACED);
            base.add( ModPlacedFeatures.DYSTRUM_ORE_PLACED);
            //base.add( ModPlacedFeatures.BISMOR_ORE_PLACED);
            //base.add( ModPlacedFeatures.BISMOR_EXPOSED);
           // base.add(ModPlacedFeatures.CROPPER_ORE_PLACED);
            base.add( ModPlacedFeatures.OIL_SHALE_ORE_PLACED);

        }
    }
}
