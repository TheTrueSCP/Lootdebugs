package net.the_goldbeards.lootdebugs.world.feature.custom.SimpleIngredientsGeneration;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

public class SimpleIngredientsFeature extends Feature<SimpleIngredientsConfiguration>
{

    public SimpleIngredientsFeature(Codec<SimpleIngredientsConfiguration> p_66808_) {
        super(p_66808_);
    }

    public boolean place(FeaturePlaceContext<SimpleIngredientsConfiguration> featurePlaceContext)
    {
        SimpleIngredientsConfiguration simpleIngredientsPlaceConfiguration = featurePlaceContext.config();
        WorldGenLevel worldgenlevel = featurePlaceContext.level();
        BlockPos blockpos = featurePlaceContext.origin();
        Random random = featurePlaceContext.random();

        BlockPos.MutableBlockPos offsetBlockPos = new BlockPos.MutableBlockPos();

        offsetBlockPos.setWithOffset(blockpos, random.nextInt(-6, 6), random.nextInt(-6, 6), random.nextInt(-6, 6));

        return simpleIngredientsPlaceConfiguration.feature().value().place(worldgenlevel, featurePlaceContext.chunkGenerator(), random, offsetBlockPos);
    }
}