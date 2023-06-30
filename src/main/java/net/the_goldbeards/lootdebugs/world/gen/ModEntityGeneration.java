package net.the_goldbeards.lootdebugs.world.gen;


import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.the_goldbeards.lootdebugs.init.ModEntities;

public class ModEntityGeneration
{
    public static void onEntitySpawn(final BiomeLoadingEvent event) {

        event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.LOOTBUG.get(), 100, 1, 3));
        event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.LOOTBUG_GOLDEN.get(), 2, 1, 1));
        event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.LOOTBUG_OLD.get(), 1, 1, 1));
    }

}
