package net.the_goldbeards.lootdebugs.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.world.gen.ModEntityGeneration;
import net.the_goldbeards.lootdebugs.world.gen.ModIngredientsGeneration;
import net.the_goldbeards.lootdebugs.world.gen.ModOreGeneration;

@Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID)
public class ModWorldEvents
{
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event)
    {
        ModOreGeneration.generateOres(event);
        ModEntityGeneration.onEntitySpawn(event);
        ModIngredientsGeneration.generateIngredients(event);

    }



}
