package net.the_goldbeards.lootdebugs.integration.jei.customRecipeTransferHandler;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub.PubContainer;
import net.the_goldbeards.lootdebugs.recipe.PubRecipe;
import org.jetbrains.annotations.Nullable;

public class PubRecipeTransferHandler implements IRecipeTransferHandler {
    @Override
    public Class getContainerClass()
    {
        return PubContainer.class;
    }
    @Override
    public Class getRecipeClass() {
        return PubRecipe.class;
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(AbstractContainerMenu container, Object recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {



        return IRecipeTransferHandler.super.transferRecipe(container, recipe, recipeSlots, player, maxTransfer, doTransfer);
    }
}
