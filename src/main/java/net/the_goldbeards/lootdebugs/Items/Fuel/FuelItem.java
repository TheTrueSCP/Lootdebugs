package net.the_goldbeards.lootdebugs.Items.Fuel;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class FuelItem extends Item
{
    public FuelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1000;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }



    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if( pStack.hasTag() && this.getDamage(pStack) >= 998)
        {
            int slot = pSlotId;
            ((Player)pEntity).getInventory().removeItem(slot,1);
            ((Player)pEntity).getInventory().add(slot,new ItemStack(ModItems.EMPTY_FUEL_CANISTER.get()));

        }



    }

}