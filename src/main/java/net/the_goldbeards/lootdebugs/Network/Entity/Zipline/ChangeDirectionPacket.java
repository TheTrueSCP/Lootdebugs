package net.the_goldbeards.lootdebugs.Network.Entity.Zipline;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineMoveEntity;

import java.util.function.Supplier;

public class ChangeDirectionPacket
{

    public void handle(Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        if (player != null)
        {
            Level world = context.getSender().level;
            if (!world.isClientSide)
            {
                if(player.getVehicle() == null)
                {
                    return;
                }

                if(player.getVehicle() instanceof ZiplineMoveEntity ziplineMoveEntity)
                {
                    ziplineMoveEntity.changeDirection();
                }
            }
        }
    }
}
