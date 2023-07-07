package net.the_goldbeards.lootdebugs.Network.Capabillity.PingClasses;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.capability.Ping.IPingData;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class PingPlayerClassSyncPacket
{
    private final int entityID;

    private final IPingData.PingClasses pingPlayerClass;




    public PingPlayerClassSyncPacket(int entityID, IPingData.PingClasses pingClass) {

        this.entityID = entityID;
        this.pingPlayerClass = pingClass;
    }

    public static void encode(PingPlayerClassSyncPacket msg, ByteBuf buf) {

        buf.writeInt(msg.entityID);
        buf.writeBytes(msg.pingPlayerClass.name().getBytes(StandardCharsets.UTF_8));
    }

    public static PingPlayerClassSyncPacket decode(ByteBuf buf)
    {
        return new PingPlayerClassSyncPacket(buf.readInt(), IPingData.PingClasses.valueOf(new String(ByteBufUtil.getBytes(buf), StandardCharsets.UTF_8)));
    }

    public static void handle(final PingPlayerClassSyncPacket message, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            if(Minecraft.getInstance() != null)
            {
                if (Minecraft.getInstance().level.getEntity(message.entityID) instanceof LivingEntity livingEntity) {
                    ModUtils.PingClasses.setPlayerPingClass(livingEntity, message.pingPlayerClass);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
