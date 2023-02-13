package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModMenuTypes;
import net.the_goldbeards.lootdebugs.init.ModBlocks;

public class ClassChangerContainer extends AbstractContainerMenu {

    public final ClassChangerTile blockEntity;

    public ClassChangerContainer(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public ClassChangerContainer(int windowId, BlockEntity entity) {
        super(ModMenuTypes.CLASS_CHANGER_CONTAINER.get(), windowId);
        blockEntity = ((ClassChangerTile) entity);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.getLevel(), blockEntity.getBlockPos()),
                pPlayer, ModBlocks.CLASS_CHANGER.get());
    }
}

