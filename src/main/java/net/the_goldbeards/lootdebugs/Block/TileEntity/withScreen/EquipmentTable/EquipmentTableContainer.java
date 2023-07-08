package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.ModResultSlot;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.ModSlot;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModMenuTypes;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.recipe.EquipmentTableRecipe;

import java.util.List;
import java.util.Optional;

public class EquipmentTableContainer extends AbstractContainerMenu {

    public final EquipmentTableTile blockEntity;

    //Transfer Stack in Slot Values
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 10;  // must be the number of slots you have!

    public EquipmentTableContainer(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public EquipmentTableContainer(int windowId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.EQUIPMENT_TERMINAL_CONTAINER.get(), windowId);
        checkContainerSize(inv, 10);
        blockEntity = ((EquipmentTableTile) entity);

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {

            this.addSlot(new ModSlot(handler, 0, 30, 17));
            this.addSlot(new ModSlot(handler, 1, 48, 17));
            this.addSlot(new ModSlot(handler, 2, 66, 17));

            this.addSlot(new ModSlot(handler, 3, 30, 35));
            this.addSlot(new ModSlot(handler, 4, 48, 35));
            this.addSlot(new ModSlot(handler, 5, 66, 35));

            this.addSlot(new ModSlot(handler, 6, 30, 53));
            this.addSlot(new ModSlot(handler, 7, 48, 53));
            this.addSlot(new ModSlot(handler, 8, 66, 53));

            this.addSlot(new ModResultSlot(handler, 9, 124, 35));
        });
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();


        if(sourceSlot instanceof ModResultSlot slot)
        {
            int maxPossibleOutput = getMaxPossibleItemOutput(this.blockEntity.itemHandler, this.blockEntity.getLevel());

            ItemStack matchingRecipe = getMatchingRecipe(this.blockEntity.itemHandler, this.blockEntity.getLevel());

            System.out.println(matchingRecipe.getCount()+ " " + maxPossibleOutput);

            int itemTransferCount = matchingRecipe.getCount() * maxPossibleOutput;

            int itemTransferCountCopy = itemTransferCount;

            List<ItemStack> addItems = NonNullList.create();

            System.out.println("itemtransfercount" + itemTransferCount);

            while(itemTransferCountCopy > 0)
            {
                if (itemTransferCountCopy >= 64) {
                    addItems.add(new ItemStack(matchingRecipe.getItem(), 64));
                    itemTransferCountCopy -= 64;
                } else
                {
                    addItems.add(new ItemStack(matchingRecipe.getItem(), itemTransferCountCopy));
                    itemTransferCountCopy -= itemTransferCountCopy;
                }
            }


            System.out.println("addItemsCount: " + addItems.size());


            for (ItemStack itemStack : addItems)
            {
                playerIn.addItem(itemStack);
            }

            slot.removeIngredients(maxPossibleOutput);

            return ItemStack.EMPTY;
        }

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT)
        {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;  // EMPTY_ITEM


            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory

            sourceStack.setCount(getMaxPossibleItemOutput(this.blockEntity.itemHandler, this.blockEntity.getLevel()));

            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.getLevel(), blockEntity.getBlockPos()),
                pPlayer, ModBlocks.EQUIPMENT_TABLE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public static int getMaxPossibleItemOutput(IItemHandler itemHandler, Level level)
    {
        int itemAmount = 0;

        while(true)
        {
            CraftingContainer inventory2 = new CraftingContainer(new AbstractContainerMenu(null, -1) {
                @Override
                public boolean stillValid(Player pPlayer) {
                    return false;
                }
            }, 3, 3);


            for (int i = 0; i < itemHandler.getSlots() - 1; i++)
            {
                ItemStack addStack = itemHandler.getStackInSlot(i).copy();
                addStack.setCount(addStack.getCount() - itemAmount);
                inventory2.setItem(i, addStack);
            }


            SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
            for (int i = 0; i < itemHandler.getSlots(); i++)
            {
                ItemStack addStack = itemHandler.getStackInSlot(i).copy();
                addStack.setCount(addStack.getCount() - itemAmount);
                inventory.setItem(i, addStack);
            }

            Optional<EquipmentTableRecipe> match = level.getRecipeManager()
                    .getRecipeFor(EquipmentTableRecipe.Type.INSTANCE, inventory, level);

            Optional<CraftingRecipe> match2 = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, inventory2, level);

            if(!match.isPresent() && !match2.isPresent())
            {
                return itemAmount;
            }
            else
            {
                itemAmount++;
            }
        }

    }

    public static ItemStack getMatchingRecipe(IItemHandler itemHandler, Level level)
    {
        CraftingContainer inventory2 = new CraftingContainer(new AbstractContainerMenu(null, -1) {
            @Override
            public boolean stillValid(Player pPlayer) {
                return false;
            }
        }, 3, 3);


        for (int i = 0; i < itemHandler.getSlots() - 1; i++)
        {
            ItemStack addStack = itemHandler.getStackInSlot(i).copy();
            inventory2.setItem(i, addStack);
        }


        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            ItemStack addStack = itemHandler.getStackInSlot(i).copy();
            inventory.setItem(i, addStack);
        }

        Optional<EquipmentTableRecipe> match = level.getRecipeManager()
                .getRecipeFor(EquipmentTableRecipe.Type.INSTANCE, inventory, level);

        Optional<CraftingRecipe> match2 = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, inventory2, level);

        if(match.isPresent())
        {
            return match.get().getResultItem();
        }
        if(match2.isPresent())
        {
            return match2.get().getResultItem();
        }

        return ItemStack.EMPTY;
    }
}

