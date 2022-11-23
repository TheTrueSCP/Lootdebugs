package net.the_goldbeards.lootdebugs.Server.BeardSlot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.capability.BeardSlot.BeardSlotCap;

import java.util.function.Supplier;

public class SyncSlotToClient
{
    private final CompoundTag compound;

    public SyncSlotToClient(CompoundTag compound) {
        this.compound = compound;
    }

    public CompoundTag getCompound() {
        return compound;
    }

    public static void handle(SyncSlotToClient message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.setPacketHandled(true);
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            player.getCapability(BeardSlotCap.BEARD_SLOT).ifPresent(capability -> capability.deserializeNBT(message.compound));
        });
    }
}
