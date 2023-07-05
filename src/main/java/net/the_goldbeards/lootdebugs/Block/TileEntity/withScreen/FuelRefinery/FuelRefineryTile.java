package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery;

import com.google.common.collect.Maps;
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
import net.minecraft.world.item.Item;
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
import net.the_goldbeards.lootdebugs.Items.Fuel.FuelCanisterItem;
import net.the_goldbeards.lootdebugs.Items.Tools.LiquidMorkiteCollector.IFuelCountRefinery;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefinerySyncFluidPacket;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefinerySyncItemStackPacket;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModFluids;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

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


    public final ItemStackHandler itemHandler = new ItemStackHandler(5)
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
                case 0 -> stack.is(ModItems.FUEL_CANISTER.get());
                case 1, 2 -> stack.is(ModTags.Items.LIQUID_FUEL);
                case 3, 4 -> stack.is(ModTags.Items.SOLID_FUEL);
                default -> false;
            };
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
            return stack.getFluid() == ModFluids.FUEL.get();
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
        if (fuelPressTile.isConverting)
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

    private static void handelFinishRefining(FuelRefineryTile fuelRefineryTile)
    {
        int missingFuel = fuelRefineryTile.fluid_tank.getCapacity() - fuelRefineryTile.fluid_tank.getFluidAmount();

        int finalFuel = 0;

        for(int i = 1; i < fuelRefineryTile.itemHandler.getSlots(); i++)
        {

               int fuelAmountSingle = getFuelAmountSingle(fuelRefineryTile.itemHandler.getStackInSlot(i));

               int removeItemCount = 0;

               for (int r = 1; r <= fuelRefineryTile.itemHandler.getStackInSlot(i).getCount(); r++)
               {
                   if (fuelAmountSingle + finalFuel <= missingFuel) {
                       finalFuel += fuelAmountSingle;
                       removeItemCount++;
                   }


               }

               fuelRefineryTile.itemHandler.extractItem(i, removeItemCount, false);

               if((i == 1 || i == 2) && removeItemCount > 0)
               {
                   fuelRefineryTile.itemHandler.setStackInSlot(i, new ItemStack(Items.BUCKET, 1));
               }
        }

        fuelRefineryTile.fluid_tank.fill(new FluidStack(ModFluids.FUEL.get(), finalFuel), IFluidHandler.FluidAction.EXECUTE);
    }

    public static Map<Item, Integer> itemFluidAmount() {
        Map<Item, Integer> fuelAmountItems = Maps.newLinkedHashMap();

        fuelAmountItems.put(Items.COAL, 16);
        fuelAmountItems.put(Items.COAL_BLOCK, 160);

        fuelAmountItems.put(ModItems.MORKITE.get(), 24);
        fuelAmountItems.put(ModBlocks.MORKITE_BLOCK.get().asItem(), 240);
        fuelAmountItems.put(ModBlocks.OIL_SHALE.get().asItem(), 250);

        return fuelAmountItems;
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

    //Canister
    public static void fillCanister(FuelRefineryTile fuelRefineryTile)//Call via Button
    {
            if(fuelRefineryTile.itemHandler.getStackInSlot(FuelRefineryContainer.getOutputSlot()).is(ModItems.FUEL_CANISTER.get()))
            {
                ItemStack fuel = fuelRefineryTile.itemHandler.getStackInSlot(FuelRefineryContainer.getOutputSlot());

                if(ModUtils.ItemNBTHelper.hasKey(fuel, "fuelAmount"))
                {
                    float fuelInItem = ModUtils.ItemNBTHelper.getFloat(fuel, "fuelAmount");

                    int optimalRefuelAmount = (int) (FuelCanisterItem.maxFuel - fuelInItem);

                    int givenRefuelAmount = fuelRefineryTile.fluid_tank.drain(optimalRefuelAmount, IFluidHandler.FluidAction.EXECUTE).getAmount();

                    ModUtils.ItemNBTHelper.putFloat(fuel, "fuelAmount", givenRefuelAmount + fuelInItem);

                }

            }
    }

    private static int getFuelAmountSingle(ItemStack pStack)
    {
        if(itemFluidAmount().get(pStack.getItem()) != null)
        {
            return itemFluidAmount().get(pStack.getItem());
        }
        else if(pStack.getItem() instanceof IFuelCountRefinery iFuelCountRefinery)
        {
            return iFuelCountRefinery.getFuelCountRefinery(pStack);
        }

        return 0;
    }

    public void setFluid(FluidStack stack) {
        this.fluid_tank.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.fluid_tank.getFluid();
    }
}
