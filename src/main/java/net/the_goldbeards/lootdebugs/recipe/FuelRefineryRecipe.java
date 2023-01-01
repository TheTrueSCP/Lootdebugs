package net.the_goldbeards.lootdebugs.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

import javax.annotation.Nullable;

public class FuelRefineryRecipe implements Recipe<SimpleContainer>
{
    private final ResourceLocation id;
    public final Ingredient recipeItem;
    public final float outputAmount;

    public FuelRefineryRecipe(ResourceLocation id, Ingredient recipeItem, float outputAmount) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.outputAmount = outputAmount;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, net.minecraft.world.level.Level pLevel) {


        for (int i = 0; i < 3; i++)
        {
            if (recipeItem.test(pContainer.getItem(i + 1)))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> recipeItemInArray = NonNullList.withSize(1, recipeItem);
        recipeItemInArray.set(1, recipeItem);
        return recipeItemInArray;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public static class Type implements RecipeType<FuelRefineryRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fuel_refining";
    }

    public static class Serializer implements RecipeSerializer<FuelRefineryRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(LootDebugsMain.MOD_ID,"fuel_refining");

        @Override
        public FuelRefineryRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "input"));

            float outputAmount = GsonHelper.getAsFloat(json, "outputAmount");
            int convertTime = GsonHelper.getAsInt(json, "convertTime");


            return new FuelRefineryRecipe(id, Ingredient.of(input), outputAmount);
        }

        @Override
        public FuelRefineryRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
        {
            ItemStack input = buf.readItem();

            return new FuelRefineryRecipe(id, Ingredient.of(input), buf.readFloat());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FuelRefineryRecipe recipe)
        {
            buf.writeFloat(recipe.outputAmount);

            buf.writeItemStack(recipe.getResultItem(), false);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
