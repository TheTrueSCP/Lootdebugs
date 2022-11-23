package net.the_goldbeards.lootdebugs.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.recipe.EquipmentTableRecipe;

import javax.annotation.Nonnull;


public class EquipmentTableRecipeCategory implements IRecipeCategory<EquipmentTableRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(LootDebugsMain.MOD_ID, "equipment_crafting");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/equipment_table_gui.png");

    private final IDrawable background;
    private final IDrawable icon;


    public EquipmentTableRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 166);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EQUIPMENT_TABLE.get()));
    }


    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends EquipmentTableRecipe> getRecipeClass() {
        return EquipmentTableRecipe.class;
    }


    @Override
    public Component getTitle() {
        return new TranslatableComponent("jei.lootdebugs.equipment_table_crafting");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull EquipmentTableRecipe recipe, @Nonnull IFocusGroup focusGroup)
    {

        builder.addSlot(RecipeIngredientRole.INPUT, 30, 17).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 17).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 17).addIngredients(recipe.getIngredients().get(2));

        builder.addSlot(RecipeIngredientRole.INPUT, 30, 35).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 35).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 35).addIngredients(recipe.getIngredients().get(5));

        builder.addSlot(RecipeIngredientRole.INPUT, 30, 53).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 53).addIngredients(recipe.getIngredients().get(7));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 53).addIngredients(recipe.getIngredients().get(8));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 35).addItemStack(recipe.getResultItem());

    }
}
