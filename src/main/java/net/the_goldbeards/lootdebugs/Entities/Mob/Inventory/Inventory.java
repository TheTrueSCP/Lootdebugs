package net.the_goldbeards.lootdebugs.Entities.Mob.Inventory;

import net.minecraft.world.item.ItemStack;

public class Inventory
{
    private final ItemStack[] inventory;

    public Inventory(int slotSize)
    {
        inventory = new ItemStack[slotSize];

        for (int i = 0; i <= inventory.length - 1; i++)
        {
            inventory[i] = ItemStack.EMPTY;
        }
    }

    public void deleteAll()
    {
        for (int i = 0; i <= inventory.length - 1; i++)
        {
            inventory[i] = ItemStack.EMPTY;
        }

    }

    public void delete(int index)
    {
        inventory[index] = ItemStack.EMPTY;
    }

    public void delete(ItemStack itemStack)
    {
        for (int i = 0; i <= inventory.length - 1; i++)
        {
            if(inventory[i] == itemStack)
            {
                inventory[i] = ItemStack.EMPTY;
                return;
            }
        }
    }

    public ItemStack get(int index)
    {
        return inventory[index];
    }

    public ItemStack[] getAll()
    {
       return inventory;
    }

    public boolean add(ItemStack itemStack)
    {
        if(itemStack == null)
        {
            System.out.println("null");
            return false;
        }

        for (int i = 0; i <= inventory.length - 1; i++)
        {
            if(inventory[i] == ItemStack.EMPTY || inventory[i].getItem() == itemStack.getItem())
            {
                inventory[i] = itemStack;
                return true;
            }
        }
        System.out.println("cant pick up");
        return false;
    }
}
