package net.the_goldbeards.lootdebugs.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.recipe.EquipmentTableRecipe;
import net.the_goldbeards.lootdebugs.recipe.FuelRefineryRecipe;
import net.the_goldbeards.lootdebugs.recipe.PubRecipe;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LootDebugsMain.MOD_ID);

    public static final RegistryObject<RecipeSerializer<EquipmentTableRecipe>> EQUIPMENT_TABLE_SERIALIZER =
            SERIALIZERS.register("equipment_crafting", () -> EquipmentTableRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<PubRecipe>> BEER_BREWING_SERIALIZER =
            SERIALIZERS.register("pub_brewing", () -> PubRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<FuelRefineryRecipe>> FUEL_REFINERY_SERIALIZER =
            SERIALIZERS.register("fuel_refining", () -> FuelRefineryRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
