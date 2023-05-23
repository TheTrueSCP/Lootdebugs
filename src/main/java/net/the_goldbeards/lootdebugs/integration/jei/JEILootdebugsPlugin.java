package net.the_goldbeards.lootdebugs.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableContainer;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.recipe.EquipmentTableRecipe;
import net.the_goldbeards.lootdebugs.recipe.LloydRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEILootdebugsPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(LootDebugsMain.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                EquipmentTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                PubRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();


        List<EquipmentTableRecipe> recipes = rm.getAllRecipesFor(EquipmentTableRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(EquipmentTableRecipeCategory.UID, EquipmentTableRecipe.class), recipes);

        List<LloydRecipe> recipes1 = rm.getAllRecipesFor(LloydRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(PubRecipeCategory.UID, LloydRecipe.class), recipes1);

    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        //registration.addRecipeTransferHandler(EquipmentTableContainer.class, new RecipeType<>(EquipmentTableRecipeCategory.UID,EquipmentTableRecipe.class), 1, 9, 10, 36);
        // registration.addRecipeTransferHandler(new PubRecipeTransferHandler(),  new RecipeType<>(PubRecipeCategory.UID, PubRecipe.class));

    }
}
