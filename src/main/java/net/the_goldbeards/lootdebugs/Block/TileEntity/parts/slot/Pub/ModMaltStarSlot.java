package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.Pub;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class ModMaltStarSlot extends SlotItemHandler
{
    public ModMaltStarSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ModItems.MALT_STAR.get());
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return 64;
    }


}
