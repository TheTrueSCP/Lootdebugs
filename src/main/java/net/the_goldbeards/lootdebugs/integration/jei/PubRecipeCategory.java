package net.the_goldbeards.lootdebugs.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.recipe.LloydRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


public class PubRecipeCategory implements IRecipeCategory<LloydRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(LootDebugsMain.MOD_ID, "pub_brewing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/lloyd_gui.png");

    private final IDrawable background;
    private final IDrawable icon;


    public PubRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 168);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.LLOYD.get()));
    }


    @Override
    public RecipeType<LloydRecipe> getRecipeType() {
        return new RecipeType<>(UID, LloydRecipe.class);
    }


    @Override
    public Component getTitle() {
        return new TranslatableComponent("jei.lootdebugs.pub_brewing");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull LloydRecipe recipe, @Nonnull IFocusGroup focusGroup)
    {
        List<ItemStack> outputs = new ArrayList<ItemStack>(2);

        outputs.add(recipe.getResultItem());
        outputs.add(new ItemStack(ModItems.MUG.get(), 1));

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 8).addItemStack(new ItemStack(Items.WATER_BUCKET,1));
        builder.addSlot(RecipeIngredientRole.INPUT, 48,35).addItemStack(new ItemStack(BarleyOrEmerald(recipe.emerald), (int)recipe.counts[0]));
        builder.addSlot(RecipeIngredientRole.INPUT, 69,35).addItemStack(new ItemStack(ModItems.YEAST_CONE.get(), (int)recipe.counts[1]));

        builder.addSlot(RecipeIngredientRole.INPUT, 90,35).addItemStack(new ItemStack(ModItems.MALT_STAR.get(), (int)recipe.counts[2]));
        builder.addSlot(RecipeIngredientRole.INPUT, 111,35).addItemStack(new ItemStack(ModItems.STARCH_NUT.get(), (int)recipe.counts[3]));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80,65).addItemStacks(outputs);
    }


    private Item BarleyOrEmerald(boolean emerald)
    {
        return emerald ? Items.EMERALD : ModItems.BARLEY_BULB.get();
    }

    @Override
    public ResourceLocation getUid() {
        return getRecipeType().getUid();
    }

    @Override
    public Class<? extends LloydRecipe> getRecipeClass() {
        return getRecipeType().getRecipeClass();
    }
}