package net.the_goldbeards.lootdebugs.world.gen;



import net.minecraft.block.FlowerBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ModIngredientsGeneration
{

    public static void generateIngredients(final BiomeLoadingEvent event)
    {
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());

        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if(types.contains(BiomeDictionary.Type.OVERWORLD))
        {
            List<Supplier<ConfiguredFeature<?,?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);

            base.add(() -> ModConfiguredFeatures.Ingredients);
        }
    }

}
