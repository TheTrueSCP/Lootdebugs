package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.recipe.EquipmentTableRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EquipmentTableTile extends BlockEntity implements MenuProvider {


    private boolean isDeleted = true;
    private final ItemStackHandler itemHandler = new ItemStackHandler(10)
    {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public EquipmentTableTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.EQUIPMENT_TERMINAL_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Equipment Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new EquipmentTableContainer(pContainerId, pInventory, this);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
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


    private static boolean hasRecipe(EquipmentTableTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EquipmentTableRecipe> match = level.getRecipeManager()
                .getRecipeFor(EquipmentTableRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }



    public <E extends BlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, EquipmentTableTile pEquipmentTableTile)
    {

        if(hasRecipe(pEquipmentTableTile))
        {
           craftItem(pEquipmentTableTile);
        }




    }
    private static void craftItem(EquipmentTableTile entity) {

        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EquipmentTableRecipe> match = level.getRecipeManager()
                .getRecipeFor(EquipmentTableRecipe.Type.INSTANCE, inventory, level);




        if(entity.isDeleted && match.isPresent()) {
            entity.itemHandler.setStackInSlot(0, new ItemStack(match.get().getResultItem().getItem(),
                    1));

            entity.isDeleted = false;
        }

        if(match.isPresent() && entity.itemHandler.getStackInSlot(0).isEmpty()) {
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.extractItem(2,1, false);
            entity.itemHandler.extractItem(3,1, false);
            entity.itemHandler.extractItem(4,1, false);
            entity.itemHandler.extractItem(5,1, false);
            entity.itemHandler.extractItem(6,1, false);
            entity.itemHandler.extractItem(7,1, false);
            entity.itemHandler.extractItem(8,1, false);
            entity.itemHandler.extractItem(9,1, false);

           entity.isDeleted = true;

        }




    }


}
