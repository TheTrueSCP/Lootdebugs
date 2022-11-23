package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModSlot extends SlotItemHandler {
    public ModSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }
    @Override
    public boolean mayPlace(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return 64;
    }
}