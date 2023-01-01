package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.recipe.FuelRefineryRecipe;
import net.the_goldbeards.lootdebugs.util.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FuelRefineryTile extends BlockEntity implements MenuProvider {

    public int Coal = 5;
    public int Morkite = 8;
    public int LavaBucket = 15;
    public int LiquidMorkite = 12;
    public int OilShale = 20;

    public  int maxFuelAmount = 3000;
    public  int REFUEL_AMOUNT = 200;
    public  int MIN_FUEL = 0;



    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public boolean isConverting = false;
    private int fuelAmount = 0;


    //REFUEL VARIABLES


    private final ItemStackHandler itemHandler = new ItemStackHandler(5)
    {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public FuelRefineryTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.FUEL_PRESS_ENTITY.get(), pWorldPosition, pBlockState);


        this.data = new ContainerData() {

            public int get(int index) {
                switch (index) {
                    case 0: return FuelRefineryTile.this.fuelAmount;
                    case 1: return FuelRefineryTile.this.maxFuelAmount;
                    case 2: return FuelRefineryTile.this.progress;
                    case 3: return FuelRefineryTile.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: FuelRefineryTile.this.fuelAmount = value; break;
                    case 1: FuelRefineryTile.this.maxFuelAmount = value; break;
                    case 2: FuelRefineryTile.this.progress = value; break;
                    case 3: FuelRefineryTile.this.maxProgress = value; break;

                }
            }

            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Fuel Press");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new FuelRefineryContainer(pContainerId, pInventory, this, this.data);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        pTag.putInt("fuelpress.fuelamount", fuelAmount);
        pTag.putInt("fuelpress.maxfuelamount", maxFuelAmount);
        pTag.putInt("fuelpress.progress", progress);
        pTag.putInt("fuelpress.maxprogress", maxProgress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));

        fuelAmount = pTag.getInt("fuelpress.fuelamount");
        maxFuelAmount = pTag.getInt("fuelpress.maxfuelamount");
        progress = pTag.getInt("fuelpress.progress");
        maxProgress = pTag.getInt("fuelpress.maxprogress");
        super.load(pTag);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    public <E extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, FuelRefineryTile fuelPressTile)
    {

        if (areItemsInSlots() && fuelPressTile.isConverting)
        {//If the ingredients in the slots match with one recipe in the folder and you have pressed the button

            fuelPressTile.progress++;//progess each tick +1
            setChanged(pLevel, pPos, pState);

            if (fuelPressTile.progress > fuelPressTile.maxProgress)
            {
                fuelPressTile.fuelAmount += fuelPressTile.calculateFuelAmountAndExtractItem();
                fuelPressTile.resetProgress();
                fuelPressTile.isConverting = false;
            }
        }
        else
        {//else, reset all
           fuelPressTile.resetProgress();
            setChanged(pLevel, pPos, pState);
            fuelPressTile.isConverting = false;
        }

        if(fuelPressTile.fuelAmount > fuelPressTile.maxFuelAmount)
        {
            fuelPressTile.fuelAmount = fuelPressTile.maxFuelAmount;
        }
    }

    private static boolean hasRecipe(FuelRefineryTile entity)
    {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FuelRefineryRecipe> match = level.getRecipeManager()
                .getRecipeFor(FuelRefineryRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }

    private void resetProgress()
    {
        this.progress = 0;  
    }

    public void setConvertingToTrue()
    {
        isConverting = true;//Set the Variable to true
    }

    private int calculateFuelAmountAndExtractItem()
    {

        int fuelAmount = 0;//Defaut Variable

        ItemStack[] slots =
                {
                        this.itemHandler.getStackInSlot(1),
                        this.itemHandler.getStackInSlot(2),
                        this.itemHandler.getStackInSlot(3),
                        this.itemHandler.getStackInSlot(4)
                };

        for (int i = 0; i <= slots.length - 1; i++)
        {

            ItemStack slot = slots[i];

            if(!slot.isEmpty())
            {
                fuelAmount += getFuelAmount(slot);

                if (slot.is(ModTags.Items.LIQUID_FUEL))
                {
                    this.itemHandler.extractItem(i + 1, slot.getCount(), false);
                    this.itemHandler.setStackInSlot(i + 1, new ItemStack(Items.BUCKET, 1));
                }
                else
                {
                    this.itemHandler.extractItem(i + 1, slot.getCount(), false);
                }
            }

        }

        return fuelAmount;
    }

    private int getFuelAmount(ItemStack item)
    {
        if(item.is(Items.LAVA_BUCKET))
        {
            return this.LavaBucket;
        }

        if(item.is(ModItems.LIQUID_MORKITE_BUCKET.get()))
        {
            return this.LiquidMorkite;
        }

        if(item.is(Items.COAL))
        {
            return this.Coal;
        }

        if(item.is(Items.COAL_BLOCK))
        {
            return (this.Coal * 9);
        }

        if(item.is(ModItems.MORKITE.get()))
        {
            return this.Morkite;
        }

        if(item.is(ModItems.MORKITE_BLOCK.get()))
        {
            return (this.Morkite * 9);
        }

        if(item.is(ModBlocks.OIL_SHALE.get().asItem()))
        {
            return this.OilShale;
        }
        return 0;
    }


    private boolean areItemsInSlots()
    {

        if(!this.itemHandler.getStackInSlot(1).isEmpty())
        {
            return true;
        }
        else if(!this.itemHandler.getStackInSlot(2).isEmpty())
        {
            return true;
        }
        else if(!this.itemHandler.getStackInSlot(3).isEmpty())
        {
            return true;
        }
        else if(!this.itemHandler.getStackInSlot(4).isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    //Canister
    public void fillCanister()//Call via Button
    {
        ItemStackHandler handler = this.itemHandler;

        if(!handler.getStackInSlot(0).isEmpty())
        {
           if(areEnoughFuelToRefuelCanister() && handler.getStackInSlot(0).is(ModItems.EMPTY_FUEL_CANISTER.get()))
           {
               handler.setStackInSlot(0, new ItemStack(ModItems.FUEL.get(), 1));
           }
        }
    }

    private boolean areEnoughFuelToRefuelCanister()
    {
        float test = this.fuelAmount - REFUEL_AMOUNT;
        return test >= MIN_FUEL;
    }
}
