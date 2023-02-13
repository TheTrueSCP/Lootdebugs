package net.the_goldbeards.lootdebugs.Network.Capabillity.ChangeClass;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Events.ModClientEventBusSubscriber;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class PlayerClassSyncPacket
{
    IClassData.Classes dwarfClass;

    public PlayerClassSyncPacket(FriendlyByteBuf buffer)
    {
        dwarfClass = IClassData.Classes.valueOf(new String(buffer.readByteArray(), StandardCharsets.UTF_8));
    }

    public PlayerClassSyncPacket(IClassData.Classes dwarfClass)
    {
        this.dwarfClass = dwarfClass;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeByteArray( dwarfClass.name().getBytes(StandardCharsets.UTF_8));
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier)
    {
        contextSupplier.get().enqueueWork(() -> {
            ModClientEventBusSubscriber.getPlayer().getCapability(ClassDataCap.CLASS_DATA).ifPresent(flareCap -> flareCap.setDwarfClass(dwarfClass));
        });
        contextSupplier.get().setPacketHandled(true);
    }

}
