package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.Pub;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class ModPubResultSlot extends SlotItemHandler {
    public ModPubResultSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ModItems.MUG.get());
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}