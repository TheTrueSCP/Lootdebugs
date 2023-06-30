package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModMenuTypes;
import net.the_goldbeards.lootdebugs.init.ModBlocks;

public class ClassChangerContainer extends AbstractContainerMenu {

    public final ClassChangerTile blockEntity;

    public ClassChangerContainer(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public ClassChangerContainer(int windowId, Inventory inv ,BlockEntity entity) {
        super(ModMenuTypes.CLASS_CHANGER_CONTAINER.get(), windowId);
        blockEntity = ((ClassChangerTile) entity);

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.getLevel(), blockEntity.getBlockPos()),
                pPlayer, ModBlocks.CLASS_CHANGER.get());
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

