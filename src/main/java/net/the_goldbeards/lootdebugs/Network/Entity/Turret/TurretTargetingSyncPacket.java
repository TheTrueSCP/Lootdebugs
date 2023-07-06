package net.the_goldbeards.lootdebugs.Network.Entity.Turret;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity;

import java.util.function.Supplier;

public class TurretTargetingSyncPacket
{
    private final TurretEntity.TargetingModes targetingMode;
    private final int turretID;




    public TurretTargetingSyncPacket(int turretID, TurretEntity.TargetingModes targetingMode) {

        this.turretID = turretID;
        this.targetingMode = targetingMode;


    }

    public static void encode(TurretTargetingSyncPacket msg, ByteBuf buf) {

        buf.writeInt(msg.turretID);
        buf.writeFloat(msg.targetingMode.getId());

    }

    public static TurretTargetingSyncPacket decode(ByteBuf buf) {

        return new TurretTargetingSyncPacket(buf.readInt(), TurretEntity.TargetingModes.byId((int) buf.readFloat()));
    }

    public static void handle(final TurretTargetingSyncPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            Level level = ctx.get().getSender().level;

            if(level instanceof ServerLevel serverLevel)
            {
                if(serverLevel.getEntity(message.turretID) instanceof TurretEntity turretEntity)
                {
                turretEntity.setTargetingMode(message.targetingMode);
                }
            }
        });
    }
}
