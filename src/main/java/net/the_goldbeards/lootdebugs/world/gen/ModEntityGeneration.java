package net.the_goldbeards.lootdebugs.world.gen;


import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.the_goldbeards.lootdebugs.init.ModEntities;

import java.util.Arrays;
import java.util.List;

public class ModEntityGeneration
{
    public static void onEntitySpawn(final BiomeLoadingEvent event) {

        addEntityToAllUndergroundBiomesExceptThese(event, ModEntities.LOOTBUG.get(),  60, 1, 3,
                //Oceans
                Biomes.OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.WARM_OCEAN,
                Biomes.RIVER,
                Biomes.FROZEN_RIVER);
        /*

        addEntityToAllBiomesExceptTheseinOverworld(event, ModEntities.LOOTBUG.get(),
                60, 1, 3,
                //Oceans
                Biomes.OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.WARM_OCEAN,
                Biomes.RIVER,
                Biomes.FROZEN_RIVER);


        addEntityToSpecificBiomes(event, ModEntities.LOOTBUG_OLD.get(),
                1,0,1,

                Biomes.DESERT,
                Biomes.ERODED_BADLANDS,
                Biomes.WINDSWEPT_GRAVELLY_HILLS,
                Biomes.WINDSWEPT_SAVANNA,
                Biomes.SOUL_SAND_VALLEY,
                Biomes.LUSH_CAVES);

        addEntityToAllBiomesExceptTheseinOverworld(event, ModEntities.LOOTBUG_GOLDEN.get(),
                10, 0, 1,
                //Oceans
                Biomes.OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.WARM_OCEAN,
                Biomes.RIVER,
                Biomes.FROZEN_RIVER);

        ;*/
    }

    private static void addEntityToAllBiomesExceptThese(BiomeLoadingEvent event, EntityType<?> type,
                                                        int weight, int minCount, int maxCount, ResourceKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(ResourceKey::location)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(!isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }


    private static void addEntityToAllBiomesExceptWaterRegionsAndTheseInOverworld(BiomeLoadingEvent event, EntityType<?> type,
                                                                                  int weight, int minCount, int maxCount, ResourceKey<Biome>... biomes)
    {
        boolean isBiomeSelected = Arrays.stream(biomes).map(ResourceKey::location)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        boolean isBiomeWater = Arrays.stream(biomes).map(ResourceKey::location)
                .map(Object::toString).anyMatch(s -> s.equals(List.of(Biomes.OCEAN,
                        Biomes.COLD_OCEAN,
                        Biomes.DEEP_COLD_OCEAN,
                        Biomes.DEEP_FROZEN_OCEAN,
                        Biomes.DEEP_OCEAN,
                        Biomes.DEEP_LUKEWARM_OCEAN,
                        Biomes.FROZEN_OCEAN,
                        Biomes.LUKEWARM_OCEAN,
                        Biomes.WARM_OCEAN,
                        Biomes.RIVER,
                        Biomes.FROZEN_RIVER)));

        if(!isBiomeSelected && !isBiomeWater) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomesExceptTheseinOverworld(BiomeLoadingEvent event, EntityType<?> type,
                                                                   int weight, int minCount, int maxCount, ResourceKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(ResourceKey::location)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(!isBiomeSelected && !event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(type.getCategory());


            base.add(new MobSpawnSettings.SpawnerData(type,weight, minCount, maxCount));
        }
    }


    @Deprecated(forRemoval = true, since = "2.0")
    private static void addEntityToSpecificBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount, ResourceKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(ResourceKey::location)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllOverworldBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                      int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.BiomeCategory.THEEND) && !event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(type.getCategory());
            base.add(new MobSpawnSettings.SpawnerData(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomesNoNether(BiomeLoadingEvent event, EntityType<?> type,
                                                     int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(type.getCategory());
            base.add(new MobSpawnSettings.SpawnerData(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomesNoEnd(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.BiomeCategory.THEEND)) {
            List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(type.getCategory());
            base.add(new MobSpawnSettings.SpawnerData(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                             int weight, int minCount, int maxCount) {
        List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(type.getCategory());
        base.add(new MobSpawnSettings.SpawnerData(type,weight, minCount, maxCount));
    }

    private static void addEntityToAllUndergroundBiomesExceptThese(BiomeLoadingEvent event, EntityType<?> type,
                                                        int weight, int minCount, int maxCount, ResourceKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(ResourceKey::location)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));
        if(isBiomeSelected) {
            event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(type, weight, minCount, maxCount));
        }
    }

}
