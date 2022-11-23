package net.the_goldbeards.lootdebugs.Server.BeardSlot;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.the_goldbeards.lootdebugs.capability.BeardSlot.BeardSlotContainer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class OpenSlotPacket
{
    public OpenSlotPacket() {
    }

    public static void handle(OpenSlotPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.setPacketHandled(true);
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                MenuProvider provider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.slots.slots_inventory");
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                        return new BeardSlotContainer(containerId, player);
                    }
                };
                NetworkHooks.openGui(player, provider);
            }
        });
    }
}
