package net.the_goldbeards.lootdebugs.init;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.world.structures.OmmoranHearthstoneStructure;

public class ModStructures
{


    public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, LootDebugsMain.MOD_ID);

    public static final RegistryObject<StructureFeature<?>> OMMORAN_HEARTHSTONE =
            DEFERRED_REGISTRY_STRUCTURE.register("ommoran_hearthstone", OmmoranHearthstoneStructure::new);

    public static void register(IEventBus eventBus)
    {
        DEFERRED_REGISTRY_STRUCTURE.register(eventBus);
    }
}
