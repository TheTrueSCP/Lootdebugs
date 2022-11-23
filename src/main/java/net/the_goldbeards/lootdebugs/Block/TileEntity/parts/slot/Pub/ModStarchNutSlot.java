package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.Pub;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.the_goldbeards.lootdebugs.init.ModItems;

import java.util.Objects;

public class ModStarchNutSlot extends SlotItemHandler
{
    public ModStarchNutSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ModItems.STARCH_NUT.get());
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return 64;
    }


}
