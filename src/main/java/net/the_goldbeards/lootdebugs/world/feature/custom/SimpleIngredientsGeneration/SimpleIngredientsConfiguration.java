package net.the_goldbeards.lootdebugs.world.feature.custom.SimpleIngredientsGeneration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record SimpleIngredientsConfiguration( Holder<PlacedFeature> feature) implements FeatureConfiguration
{

    public static final Codec<SimpleIngredientsConfiguration> CODEC = RecordCodecBuilder.create((p_191331_) -> {
        return p_191331_.group(PlacedFeature.CODEC.fieldOf("feature").forGetter(SimpleIngredientsConfiguration::feature)).apply(p_191331_, SimpleIngredientsConfiguration::new);
    });
}
