package net.the_goldbeards.lootdebugs.init;

import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ModPaintings
{
    public static final DeferredRegister<Motive> PAINTINGS =
            DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, LootDebugsMain.MOD_ID);

    public static final RegistryObject<Motive> TOGETHER_WE_CAN_DO_IT = PAINTINGS.register("together_we_can_do_it",
            ()-> new Motive(32,32));

    public static final RegistryObject<Motive> WE_FORGE_TOMORROW = PAINTINGS.register("we_forge_tomorrow",
            ()-> new Motive(32,32));

    public static void register(IEventBus eventBus)
    {
        PAINTINGS.register(eventBus);
    }
}
