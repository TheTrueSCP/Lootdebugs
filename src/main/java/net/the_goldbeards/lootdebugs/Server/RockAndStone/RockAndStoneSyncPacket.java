package net.the_goldbeards.lootdebugs.Server.RockAndStone;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Events.ModClientEventBusSubscriber;
import net.the_goldbeards.lootdebugs.capability.Salute.SaluteDataCap;

import java.util.function.Supplier;

public final class RockAndStoneSyncPacket {



    int cooldown;

    public RockAndStoneSyncPacket(FriendlyByteBuf buffer)
    {
        cooldown = buffer.readInt();
    }

    public RockAndStoneSyncPacket(int cooldown)
    {
        this.cooldown = cooldown;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeInt(cooldown);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier)
    {
        contextSupplier.get().enqueueWork(() -> {
            ModClientEventBusSubscriber.getPlayer().getCapability(SaluteDataCap.SALUTE_DATA).ifPresent(saluteCap -> saluteCap.setDwarfSaluteCooldown(cooldown));
        });
        contextSupplier.get().setPacketHandled(true);
    }


}

