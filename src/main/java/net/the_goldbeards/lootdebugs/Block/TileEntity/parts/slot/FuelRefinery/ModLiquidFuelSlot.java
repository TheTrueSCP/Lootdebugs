package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.FuelRefinery;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.the_goldbeards.lootdebugs.util.ModTags;

public class ModLiquidFuelSlot extends SlotItemHandler
{
    public ModLiquidFuelSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ModTags.Items.LIQUID_FUEL);
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return 1;
    }


}
