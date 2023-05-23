package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.Lloyd;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class ModBarleyBulbSlot extends SlotItemHandler
{
    public ModBarleyBulbSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ModItems.BARLEY_BULB.get()) || stack.is(Items.EMERALD);
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return 64;
    }


}
