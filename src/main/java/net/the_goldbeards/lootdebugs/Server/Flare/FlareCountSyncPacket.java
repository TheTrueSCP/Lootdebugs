package net.the_goldbeards.lootdebugs.Server.Flare;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Events.ModClientEventBusSubscriber;
import net.the_goldbeards.lootdebugs.capability.Flare.FlareDataCap;

import java.util.function.Supplier;

public class FlareCountSyncPacket
{
    int flareCount;

    public FlareCountSyncPacket(FriendlyByteBuf buffer)
    {
        flareCount = buffer.readInt();
    }

    public FlareCountSyncPacket(int flareCount)
    {
        this.flareCount = flareCount;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeInt(flareCount);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier)
    {
        contextSupplier.get().enqueueWork(() -> {
            ModClientEventBusSubscriber.getPlayer().getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap -> flareCap.setStoredFlares(flareCount));
        });
        contextSupplier.get().setPacketHandled(true);
    }

}
