package net.the_goldbeards.lootdebugs.Network.Entity.Turret;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity;

import java.util.function.Supplier;

public class TurretRemovePacket
{
    private final int turretID;




    public TurretRemovePacket(int turretID) {

        this.turretID = turretID;

    }

    public static void encode(TurretRemovePacket msg, ByteBuf buf) {

        buf.writeInt(msg.turretID);
    }

    public static TurretRemovePacket decode(ByteBuf buf) {

        return new TurretRemovePacket(buf.readInt());
    }

    public static void handle(final TurretRemovePacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            Level level = ctx.get().getSender().level;

            if(level instanceof ServerLevel serverLevel)
            {
                if(serverLevel.getEntity(message.turretID) instanceof TurretEntity turretEntity)
                {
                    turretEntity.dropTurret();
                }
            }
        });
    }
}
