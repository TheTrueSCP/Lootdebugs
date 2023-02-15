package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.the_goldbeards.lootdebugs.Items.Fuel.FuelItem;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefinerySyncFluidPacket;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefinerySyncItemStackPacket;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModFluids;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModLists;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FuelRefineryTile extends BlockEntity implements MenuProvider {

    public static final int MAX_FUEL = 10000;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public boolean isConverting = false;


    //REFUEL VARIABLES

    public FuelRefineryTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.FUEL_PRESS_ENTITY.get(), pWorldPosition, pBlockState);


        this.data = new ContainerData() {

            public int get(int index) {
                switch (index) {
                    case 0: return FuelRefineryTile.this.progress;
                    case 1: return FuelRefineryTile.this.maxProgress;
                    case 2: return FuelRefineryTile.this.fluid_tank.getFluidAmount();
                    case 3: return FuelRefineryTile.this.fluid_tank.getCapacity();
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index)
                {
                    case 0: FuelRefineryTile.this.progress = value; break;
                    case 1: FuelRefineryTile.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 4;
            }
        };
    }


    private final ItemStackHandler itemHandler = new ItemStackHandler(5)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
            if(!level.isClientSide()) {
                PacketHandler.sendToClients(new FuelRefinerySyncItemStackPacket(this, worldPosition));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack)
        {
            return switch (slot) {
                case 0 -> stack.is(ModItems.FUEL.get());
                case 1, 2 -> stack.is(ModTags.Items.LIQUID_FUEL);
                case 3, 4 -> stack.is(ModTags.Items.SOLID_FUEL);
                default -> false;
            };
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
        {
            if(((slot == 1 || slot == 2) && stack.is(ModTags.Items.LIQUID_FUEL)) || ((slot == 3|| slot == 4) && stack.is(ModTags.Items.SOLID_FUEL)))
            {
                return super.insertItem(slot, stack, simulate);
            }
            else if(slot == 0 && stack.is(ModItems.FUEL.get()))
            {
                if(UsefullStuff.ItemNBTHelper.getFloat(stack, "fuelAmount") < FuelItem.maxFuel)
                {
                    return super.insertItem(slot, stack, simulate);
                }
            }
            return stack;
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {

            if(simulate)
            {
                if(slot == 0 && this.getStackInSlot(slot).is(ModItems.FUEL.get()))
                {
                 //   return ItemStack.EMPTY;
                }
            }

            return super.extractItem(slot, amount, simulate);
        }
    };

    public final FluidTank fluid_tank = new FluidTank(MAX_FUEL)
    {
        @Override
        protected void onContentsChanged()
        {
            setChanged();
            if(!level.isClientSide())
            {
                PacketHandler.sendToClients(new FuelRefinerySyncFluidPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.LIQUID_MORKITE.get();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.lootdebugs.fuel_refinery");
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new FuelRefineryContainer(pContainerId, pInventory, this, this.data);
    }

    public void setHandler(ItemStackHandler itemStackHandler)
    {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluid_tank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        pTag.putInt("fuelpress.progress", progress);
        pTag.putInt("fuelpress.maxprogress", maxProgress);

        pTag = fluid_tank.writeToNBT(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));

        progress = pTag.getInt("fuelpress.progress");
        maxProgress = pTag.getInt("fuelpress.maxprogress");
        fluid_tank.readFromNBT(pTag);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, FuelRefineryTile fuelPressTile)
    {
        if (areItemsInSlots(fuelPressTile) && fuelPressTile.isConverting)
        {
            //fuelPressTile.progress++;//progess each tick +1

            if (fuelPressTile.progress > fuelPressTile.maxProgress || true)
            {
                handelFinishRefining(fuelPressTile);
                fuelPressTile.resetProgress();
                setChanged(pLevel, pPos, pState);
            }
        }
        else
        {
            fuelPressTile.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static void handelFinishRefining(FuelRefineryTile fuelPressTile)
    {
        for(int i = 1; i <= fuelPressTile.itemHandler.getSlots() -1; i++)
        {
            int optimalFuelAmount = getFluidAmount(fuelPressTile.itemHandler.getStackInSlot(i));
            FluidStack optimalFuel = new FluidStack(ModFluids.LIQUID_MORKITE.get(), optimalFuelAmount);

            int realFuelAmount = fuelPressTile.fluid_tank.fill(optimalFuel, IFluidHandler.FluidAction.SIMULATE);



            if(realFuelAmount == optimalFuelAmount)
            {
                if(!fuelPressTile.addedBucketToSlot(i, fuelPressTile))
                {
                    fuelPressTile.itemHandler.extractItem(i, fuelPressTile.itemHandler.getStackInSlot(i).getCount(), false);
                }
                fuelPressTile.fluid_tank.fill(optimalFuel, IFluidHandler.FluidAction.EXECUTE);
            }
            else if(realFuelAmount >= (optimalFuelAmount / 2))
            {
                if(!fuelPressTile.addedBucketToSlot(i, fuelPressTile))
                {
                    fuelPressTile.itemHandler.extractItem(i, fuelPressTile.itemHandler.getStackInSlot(i).getCount() / 2, false);
                }
                fuelPressTile.fluid_tank.fill(new FluidStack(ModFluids.LIQUID_MORKITE.get(), optimalFuelAmount / 2), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }

    public boolean addedBucketToSlot(int i, FuelRefineryTile fuelRefineryTile)
    {
        if(fuelRefineryTile.itemHandler.getStackInSlot(i).is(ModItems.LIQUID_MORKITE_BUCKET.get()) || fuelRefineryTile.itemHandler.getStackInSlot(i).is(Items.LAVA_BUCKET))
        {
            fuelRefineryTile.itemHandler.setStackInSlot(i, new ItemStack(Items.BUCKET, 1));
        return true;
        }

        return false;
    }

    public static int getFluidAmount(ItemStack pStack)
    {
        if(ModLists.TileEntities.FuelRefinery.fuelAmountItems().containsKey(pStack.getItem()))
        {
            return ModLists.TileEntities.FuelRefinery.fuelAmountItems().get(pStack.getItem()) * pStack.getCount();
        }
        return 0;
    }

    private void resetProgress()
    {
        this.isConverting = false;
        this.progress = 0;  
    }

    public void setConvertingToTrue()
    {
        isConverting = true;//Set the Variable to true
    }


    private static boolean areItemsInSlots(FuelRefineryTile fuelRefineryTile)
    {

        if(!fuelRefineryTile.itemHandler.getStackInSlot(1).isEmpty() && fuelRefineryTile.itemHandler.getStackInSlot(1).is(ModTags.Items.LIQUID_FUEL))
        {
            return true;
        }
        else if(!fuelRefineryTile.itemHandler.getStackInSlot(2).isEmpty() && fuelRefineryTile.itemHandler.getStackInSlot(2).is(ModTags.Items.LIQUID_FUEL))
        {
            return true;
        }
        else if(!fuelRefineryTile.itemHandler.getStackInSlot(3).isEmpty() && fuelRefineryTile.itemHandler.getStackInSlot(3).is(ModTags.Items.SOLID_FUEL))
        {
            return true;
        }
        else if(!fuelRefineryTile.itemHandler.getStackInSlot(4).isEmpty() && fuelRefineryTile.itemHandler.getStackInSlot(4).is(ModTags.Items.SOLID_FUEL))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    //Canister
    public static void fillCanister(FuelRefineryTile fuelRefineryTile)//Call via Button
    {
            if(fuelRefineryTile.itemHandler.getStackInSlot(0).is(ModItems.FUEL.get()))
            {
                ItemStack fuel = fuelRefineryTile.itemHandler.getStackInSlot(0);

                if(UsefullStuff.ItemNBTHelper.hasKey(fuel, "fuelAmount"))
                {
                    float fuelInItem = UsefullStuff.ItemNBTHelper.getFloat(fuel, "fuelAmount");

                    int optimalRefuelAmount = (int) (FuelItem.maxFuel - fuelInItem);

                    int givenRefuelAmount = fuelRefineryTile.fluid_tank.drain(optimalRefuelAmount, IFluidHandler.FluidAction.EXECUTE).getAmount();

                    UsefullStuff.ItemNBTHelper.putFloat(fuel, "fuelAmount", givenRefuelAmount + fuelInItem);

                }

            }
    }

    public void setFluid(FluidStack stack) {
        this.fluid_tank.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.fluid_tank.getFluid();
    }
}
