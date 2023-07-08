package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableTile;

public class ModResultSlot extends SlotItemHandler
{
    IItemHandler itemHandler;

    public ModResultSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);

        this.itemHandler = itemHandler;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public void removeIngredients(int count)
    {
        EquipmentTableTile.removeIngredients(itemHandler, count);
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {

        removeIngredients(1);

        super.onTake(pPlayer, pStack);
    }
}