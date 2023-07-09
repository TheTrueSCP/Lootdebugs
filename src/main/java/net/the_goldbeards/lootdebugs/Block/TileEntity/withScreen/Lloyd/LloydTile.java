package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Lloyd;

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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.recipe.LloydRecipe;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class LloydTile extends BlockEntity implements MenuProvider{


    //Itemhandler
    private final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    //Variables
    protected final ContainerData data;
    public boolean isBrewing = false;
    private int progress = 0;
    private int maxProgress = 200;


    //Constructor
    public LloydTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.LLOYD_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);

        this.data = new ContainerData() {

            public int get(int index) {
                switch (index) {
                    case 0: return LloydTile.this.progress;
                    case 1: return LloydTile.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: LloydTile.this.progress = value; break;
                    case 1: LloydTile.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.lootdebugs.lloyd");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new LloydContainer(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        progress = nbt.getInt("progress");
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    //Call, when you press the Butten in the GUI
    public void SetBrewToTrue(LloydTile te)
    {
        isBrewing = true;//Set the Variable to true
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, LloydTile pBlockEntity) {
        if (hasRecipe(pBlockEntity) && isMugAndWaterInSlot(pBlockEntity) && pBlockEntity.isBrewing) {//If the ingredients in the slots match with one recipe in the folder and you have pressed the button

            pBlockEntity.progress++;//progess pro tick +1
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);//Craft the item, if the progress is finished
                pBlockEntity.isBrewing = false;//Set the brew variable to false
            }

        }
        else
        {//else, reset all
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
            pBlockEntity.isBrewing = false;


        }
    }
    private static void craftItem(LloydTile entity) {

        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<LloydRecipe> match = level.getRecipeManager()
                .getRecipeFor(LloydRecipe.Type.INSTANCE, inventory, level);




        if(match.isPresent()) {



            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));

            entity.itemHandler.extractItem(1,5,false);
            entity.itemHandler.extractItem(2,5,false);
            entity.itemHandler.extractItem(3,5,false);
            entity.itemHandler.extractItem(4,5,false);

            entity.itemHandler.extractItem(5,1, false);
            entity.itemHandler.setStackInSlot(5, new ItemStack(match.get().getResultItem().getItem(),
                    inventory.getItem(5).getCount()));

        }
    }
    private static boolean isMugAndWaterInSlot(LloydTile pBlockEntity)
    {
        return pBlockEntity.itemHandler.getStackInSlot(0).is(Items.WATER_BUCKET) &&  pBlockEntity.itemHandler.getStackInSlot(5).is(ModItems.MUG.get());

    }

    private static boolean hasRecipe(LloydTile entity)
    {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<LloydRecipe> match = level.getRecipeManager()
                .getRecipeFor(LloydRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }

    private void resetProgress()
    {
        this.progress = 0;
    }

}
