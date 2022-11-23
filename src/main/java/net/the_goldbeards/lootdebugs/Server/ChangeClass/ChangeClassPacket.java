package net.the_goldbeards.lootdebugs.Server.ChangeClass;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Server.PacketHandler;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class ChangeClassPacket
{
    private IClassData.Classes pClass;

   public ChangeClassPacket(IClassData.Classes pClass)
    {
        this.pClass = pClass;
    }

    public static void encode(ChangeClassPacket msg, ByteBuf buf) {

        buf.writeBytes(msg.pClass.name().getBytes(StandardCharsets.UTF_8));

    }

    public static ChangeClassPacket decode(ByteBuf buf)
    {
        return new ChangeClassPacket(IClassData.Classes.valueOf(new String(ByteBufUtil.getBytes(buf), StandardCharsets.UTF_8)));
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        if (player != null)
        {
            Level world = context.getSender().level;
            if (!world.isClientSide)
            {
                player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap -> {

                    if (player.isCreative() || (player.isSpectator()) && classCap.getDwarfClass() != pClass )
                    {

                        classCap.setDwarfClass(pClass);

                        PacketHandler.send(PacketDistributor.PLAYER.with(context::getSender), new PlayerClassSyncPacket(classCap.getDwarfClass()));
                        return;
                    }


                    if(classCap.getDwarfChangeCooldown() <= 0 && classCap.getDwarfClass() != pClass)
                    {
                        classCap.setDwarfClass(pClass);

                        classCap.setDwarfChangeCooldown(LootdebugsConfig.DWARF_CHANGE_COOLDOWN.get());
                    }



                        PacketHandler.send(PacketDistributor.PLAYER.with(context::getSender), new PlayerClassSyncPacket(classCap.getDwarfClass()));//Sync Class with Client
                    });

            }
        }
    }
}
