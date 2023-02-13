package net.the_goldbeards.lootdebugs.world.gen;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.the_goldbeards.lootdebugs.init.ModStructures;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ModStructureGeneration {
    public static void generateStructures(final BiomeLoadingEvent event) {
    RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
    Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

    if(types.contains(BiomeDictionary.Type.OVERWORLD)) {
        List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();

        structures.add(() -> ModStructures.OMMORAN_HEARTHSTONE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    }
}
}
