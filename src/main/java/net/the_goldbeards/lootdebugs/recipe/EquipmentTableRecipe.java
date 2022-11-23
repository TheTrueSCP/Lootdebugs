package net.the_goldbeards.lootdebugs.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.init.ModItems;

import javax.annotation.Nullable;

public class EquipmentTableRecipe implements Recipe<SimpleContainer>
{
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public EquipmentTableRecipe(ResourceLocation id, ItemStack output,
                               NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, net.minecraft.world.level.Level pLevel) {
        if(recipeItems.get(0).test(pContainer.getItem(1)))
        {
            if(recipeItems.get(1).test(pContainer.getItem(2)))
            {
                if(recipeItems.get(2).test(pContainer.getItem(3)))
                {
                    if(recipeItems.get(3).test(pContainer.getItem(4)))
                    {
                        if(recipeItems.get(4).test(pContainer.getItem(5)))
                        {
                            if(recipeItems.get(5).test(pContainer.getItem(6)))
                            {
                                if(recipeItems.get(6).test(pContainer.getItem(7)))
                                {
                                    if(recipeItems.get(7).test(pContainer.getItem(8)))
                                    {
                                        if(recipeItems.get(8).test(pContainer.getItem(9)))
                                        {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
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


    public static class Type implements RecipeType<EquipmentTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "equipment_crafting";
    }

    public static class Serializer implements RecipeSerializer<EquipmentTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(LootDebugsMain.MOD_ID,"equipment_crafting");

        @Override
        public EquipmentTableRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(9, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                if(Ingredient.fromJson(ingredients.get(i)).test(new ItemStack(ModItems.EMPTY.get())))
                {
                    inputs.set(i, Ingredient.EMPTY);
                }
                else
                {
                    inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
                }
            }

            return new EquipmentTableRecipe(id, output, inputs);
        }

        @Override
        public EquipmentTableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new EquipmentTableRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, EquipmentTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
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
