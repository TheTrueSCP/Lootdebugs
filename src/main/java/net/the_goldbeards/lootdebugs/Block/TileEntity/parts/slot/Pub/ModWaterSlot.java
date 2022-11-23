package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.Pub;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModWaterSlot extends SlotItemHandler
{
    public ModWaterSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return pStack.getMaxStackSize();
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.is(Items.BUCKET);
    }
}
