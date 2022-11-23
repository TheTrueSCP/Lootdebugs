package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
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
import net.the_goldbeards.lootdebugs.recipe.PubRecipe;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class PubTile extends BlockEntity implements MenuProvider, WorldlyContainer {


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
    private static final int[] SLOTS_FOR_UP = new int[]{0};//Water
    private static final int[] SLOTS_FOR_DOWN = new int[]{ 5};///Bucket and Beer
    private static final int[] SLOTS_FOR_SIDES = new int[]{1,2,3,4};
    private int progress = 0;
    private int maxProgress = 200;


    //Constructor
    public PubTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.PUB_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);

        this.data = new ContainerData() {

            public int get(int index) {
                switch (index) {
                    case 0: return PubTile.this.progress;
                    case 1: return PubTile.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: PubTile.this.progress = value; break;
                    case 1: PubTile.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return new TextComponent("");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new PubContainer(pContainerId, pInventory, this, this.data);
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
        tag.putInt("pub.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        progress = nbt.getInt("pub.progress");
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
    public void SetBrewToTrue(PubTile te)
        {
                isBrewing = true;//Set the Variable to true
            }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, PubTile pBlockEntity) {
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
    private static void craftItem(PubTile entity) {

        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PubRecipe> match = level.getRecipeManager()
                .getRecipeFor(PubRecipe.Type.INSTANCE, inventory, level);




        if(match.isPresent()) {



            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));

            entity.itemHandler.extractItem(1,5,false);
            entity.itemHandler.extractItem(2,5,false);
            entity.itemHandler.extractItem(3,5,false);
            entity.itemHandler.extractItem(4,5,false);

            entity.itemHandler.extractItem(5,1, false);
            entity.itemHandler.setStackInSlot(5, new ItemStack(match.get().getResultItem().getItem(),
                    match.get().getResultItem().getCount()));

        }
    }
    private static boolean isMugAndWaterInSlot(PubTile pBlockEntity)
    {
        return pBlockEntity.itemHandler.getStackInSlot(0).is(Items.WATER_BUCKET) &&  pBlockEntity.itemHandler.getStackInSlot(5).is(ModItems.MUG.get());

    }

    private static boolean hasRecipe(PubTile entity)
    {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PubRecipe> match = level.getRecipeManager()
                .getRecipeFor(PubRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }

    private void resetProgress()
    {
        this.progress = 0;
    }

    //Hopperstuff
    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if(pSide == Direction.DOWN)
        {
            return SLOTS_FOR_DOWN;
        }
        else
        {
            return pSide == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @org.jetbrains.annotations.Nullable Direction pDirection) {
        if(pIndex == 0 && pItemStack.is(Items.WATER_BUCKET))
        {
            return true;
        }
        else if(pIndex == 1 && pItemStack.is(ModItems.BARLEY_BULB.get()) || pIndex == 1 && pItemStack.is(Items.EMERALD))
        {
            return true;
        }
        else if(pIndex == 2 && pItemStack.is(ModItems.YEAST_CONE.get()))
        {
            return true;
        }
        else if(pIndex == 3 && pItemStack.is(ModItems.MALT_STAR.get()))
        {
            return true;
        }
        else if(pIndex == 4 && pItemStack.is(ModItems.STARCH_NUT.get()))
        {
            return true;
        }
        else if(pIndex == 5 && pItemStack.is(ModItems.MUG.get()))
        {
            return true;
        }
        else
        {

            return false;
        }
    }


    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return canPlaceItemThroughFace(pIndex, pStack, null);
    }



    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        if(pIndex == 0 && pStack.is(Items.BUCKET))
        {
            return true;
        }
        else if(pIndex == 5 && !pStack.is(ModItems.MUG.get()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int getContainerSize() {
        return this.itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.itemHandler.getStackInSlot(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return this.itemHandler.extractItem(pIndex, pCount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return this.itemHandler.extractItem(pIndex, this.itemHandler.getStackInSlot(pIndex).getCount(), false);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack)
    {
        this.itemHandler.insertItem(pIndex, pStack, false);
        if (pStack.getCount() > this.getMaxStackSize())
        {
            pStack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent()
    {
        for (int i = 0; i < this.itemHandler.getSlots(); i++)
        {

            this.itemHandler.extractItem(i, this.itemHandler.getStackInSlot(i).getCount(),false);
        }
    }

}
