package net.the_goldbeards.lootdebugs.Items.Tools.LiquidMorkiteCollector;

import net.minecraft.world.item.ItemStack;

public interface IFuelCountRefinery
{
    /**
     * The refinery will use this value if present on item
     * @return
     */
    int getFuelCountRefinery(ItemStack pStack);
}
