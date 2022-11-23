package net.the_goldbeards.lootdebugs.world.gen;


import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.the_goldbeards.lootdebugs.init.ModEntities;


import java.util.Arrays;
import java.util.List;

public class ModEntityGeneration {
    public static void onEntitySpawn(final BiomeLoadingEvent event) {
        addEntityToAllBiomesExceptTheseinOverworld(event, ModEntities.LOOTBUG.get(),
                60, 2, 5,
                //Oceans
                Biomes.OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.DEEP_WARM_OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.WARM_OCEAN);

        addEntityToAllBiomesExceptTheseinOverworld(event, ModEntities.LOOTBUG_GOLDEN.get(),
                1, 0, 1,
                //Oceans
                Biomes.OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.DEEP_WARM_OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.WARM_OCEAN);

        ;
    }

    private static void addEntityToAllBiomesExceptThese(BiomeLoadingEvent event, EntityType<?> type,
                                                        int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(RegistryKey::getLocation)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(!isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomesExceptTheseinOverworld(BiomeLoadingEvent event, EntityType<?> type,
                                                                   int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(RegistryKey::getLocation)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(!isBiomeSelected && !event.getCategory().equals(Biome.Category.NETHER)) {
            List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
            base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
        }
    }
    private static void addEntityToSpecificBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(RegistryKey::getLocation)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllOverworldBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                      int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.Category.THEEND) && !event.getCategory().equals(Biome.Category.NETHER)) {
            List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
            base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomesNoNether(BiomeLoadingEvent event, EntityType<?> type,
                                                     int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.Category.NETHER)) {
            List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
            base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomesNoEnd(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.Category.THEEND)) {
            List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
            base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                             int weight, int minCount, int maxCount) {
        List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
        base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
    }
}
