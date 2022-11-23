package net.the_goldbeards.lootdebugs.Events;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.recipe.EquipmentTableRecipe;
import net.the_goldbeards.lootdebugs.recipe.PubRecipe;

@Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class ModServerEventBusSubscriber
{

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, EquipmentTableRecipe.Type.ID, EquipmentTableRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, PubRecipe.Type.ID, PubRecipe.Type.INSTANCE);
    }

}
