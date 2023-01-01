package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.FuelRefinery.ModFuelRefineryResultSlot;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.FuelRefinery.ModLiquidFuelSlot;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.slot.FuelRefinery.ModSolidFuelSlot;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModMenuTypes;

public class FuelRefineryContainer extends AbstractContainerMenu /*implements WorldlyContainer*/ {

    public final FuelRefineryTile blockEntity;
    private final ContainerData data;

    //Transfer Stack in Slot Values
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 5;  // must be the number of slots you have!

    public FuelRefineryContainer(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(5));
    }

    public FuelRefineryContainer(int windowId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.FUEL_PRESS_CONTAINER.get(), windowId);
        checkContainerSize(inv, 5);
        blockEntity = ((FuelRefineryTile) entity);
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->
        {
            this.addSlot(new ModFuelRefineryResultSlot(handler, 0, 124, 35));
            this.addSlot(new ModLiquidFuelSlot(handler, 1, 30, 17));
            this.addSlot(new ModLiquidFuelSlot(handler, 2, 48, 17));
            this.addSlot(new ModSolidFuelSlot(handler, 3, 66, 17));
            this.addSlot(new ModSolidFuelSlot(handler, 4, 86, 17));
        });

        addDataSlots(data);
    }

    public int getScaledProgress() {//Mixing Progress
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);  // Max Progress
        int progressArrowSize = 79; // This is the width in pixels of your arrow

        return maxProgress  != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledFluidLevel()
    {
        int fuelAmount = this.data.get(0);
        int maxFuelAmount = this.data.get(1);  // Max Progress
        int progressArrowSize = 63; // This is the width in pixels of your arrow

        return maxFuelAmount != 0 && fuelAmount != 0 ? (fuelAmount * progressArrowSize / maxFuelAmount) : 0;
    }




    public boolean isCompressing()
    {
        return this.data.get(2) > 0;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
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
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        //  return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
        //       pPlayer, ModBlocks.PUB.get());

        return true;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }


}

