package net.the_goldbeards.lootdebugs.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.init.ModItems;

import javax.annotation.Nullable;

public class LloydRecipe implements Recipe<SimpleContainer>
{
    private final ResourceLocation id;
    private final ItemStack output;
    public long[] counts = new long[4];
    public boolean emerald = false;

    public LloydRecipe(ResourceLocation id, ItemStack output, long[] counts, boolean emerald) {
        this.id = id;
        this.output = output;
        this.counts = counts;
        this.emerald = emerald;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, net.minecraft.world.level.Level pLevel) {
        if(counts[0] == pContainer.getItem(1).getCount() && isEmeraldOrBarley(pContainer))//wenn emerald true, dann checke ob in container emerald und adserrum
        {
            if(counts[1] == pContainer.getItem(2).getCount())
            {
                if(counts[2] == pContainer.getItem(3).getCount())
                {
                    if(counts[3] == pContainer.getItem(4).getCount())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isEmeraldOrBarley(SimpleContainer pContainer)
    {
        if(pContainer.getItem(1).is(Items.EMERALD) && emerald)
        {
            return true;
        }
        else return pContainer.getItem(1).is(ModItems.BARLEY_BULB.get()) && !emerald;


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

    public static class Type implements RecipeType<LloydRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "pub_brewing";
    }

    public static class Serializer implements RecipeSerializer<LloydRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(LootDebugsMain.MOD_ID,"pub_brewing");


        @Override
        public LloydRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            long[] countsJson = new long[4];
            boolean emerald = false;

            countsJson[0] = GsonHelper.getAsInt(json, "count1", 0);
            countsJson[1] = GsonHelper.getAsInt(json, "count2", 0);
            countsJson[2] = GsonHelper.getAsInt(json, "count3", 0);
            countsJson[3] = GsonHelper.getAsInt(json, "count4", 0);

            emerald = GsonHelper.getAsBoolean(json, "emerald");

            return new LloydRecipe(id, output, countsJson, emerald);
        }

        @Override
        public LloydRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
        {
            ItemStack output = buf.readItem();
            long[] counts = buf.readLongArray();
            boolean emerald = buf.readBoolean();



            return new LloydRecipe(id, output, counts, emerald);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, LloydRecipe recipe)
        {
            buf.writeItemStack(recipe.output, false);
            buf.writeLongArray(recipe.counts);
            buf.writeBoolean(recipe.emerald);
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
