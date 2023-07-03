package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable;

import mezz.jei.api.constants.RecipeTypes;
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
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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

    public final ItemStackHandler itemHandler = new ItemStackHandler(10)
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

    private static boolean hasRecipeVanilla(EquipmentTableTile entity) {
        Level level = entity.level;

        CraftingContainer inventory = new CraftingContainer(new AbstractContainerMenu(null, -1) {
            @Override
            public boolean stillValid(Player pPlayer) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < entity.itemHandler.getSlots() - 1; i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<CraftingRecipe> match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, inventory, level);


        return match.isPresent();
    }



    public <E extends BlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, EquipmentTableTile pEquipmentTableTile)
    {

        if(hasRecipe(pEquipmentTableTile))
        {
            craftItem(pEquipmentTableTile);
        }
        else if(hasRecipeVanilla(pEquipmentTableTile))
        {
            craftItemVanilla(pEquipmentTableTile);
        }
        else
        {
            pEquipmentTableTile.itemHandler.setStackInSlot(9, ItemStack.EMPTY);
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

        if(match.isPresent())
        {
            entity.itemHandler.setStackInSlot(9, new ItemStack(match.get().getResultItem().getItem(),
                    match.get().getResultItem().getCount()));
        }
    }

    private static void craftItemVanilla(EquipmentTableTile entity) {

        Level level = entity.level;

        CraftingContainer inventory = new CraftingContainer(new AbstractContainerMenu(null, -1) {
            @Override
            public boolean stillValid(Player pPlayer) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < entity.itemHandler.getSlots() - 1; i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<CraftingRecipe> match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, inventory, level);



        if(match.isPresent())
        {
            entity.itemHandler.setStackInSlot(9, new ItemStack(match.get().getResultItem().getItem(),
                    match.get().getResultItem().getCount()));
        }
    }

    public static void removeIngredients(IItemHandler itemHandler, int count)
    {
        itemHandler.extractItem(0,count, false);
        itemHandler.extractItem(1,count, false);
        itemHandler.extractItem(2,count, false);
        itemHandler.extractItem(3,count, false);
        itemHandler.extractItem(4,count, false);
        itemHandler.extractItem(5,count, false);
        itemHandler.extractItem(6,count, false);
        itemHandler.extractItem(7,count, false);
        itemHandler.extractItem(8,count, false);
    }
}
