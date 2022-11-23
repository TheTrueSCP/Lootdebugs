package net.the_goldbeards.lootdebugs.Server.RockAndStone;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Server.PacketHandler;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Salute.SaluteDataCap;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;

import java.util.function.Supplier;

public class RockAndStonePacket
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
                player.getCapability(SaluteDataCap.SALUTE_DATA).ifPresent(saluteCap -> {


                    if(saluteCap.getDwarfSaluteCooldown() <= 0)
                    {
                        player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
                        {
                            player.playSound(ModSounds.ROCK_AND_STONE.get(), 1, 1);

                            saluteCap.setDwarfSaluteCooldown(LootdebugsConfig.DWARF_SALUTE_COOLDOWN.get());
                        });
                        PacketHandler.send(PacketDistributor.PLAYER.with(context::getSender), new RockAndStoneSyncPacket(saluteCap.getDwarfSaluteCooldown()));
                    }
                    });

            }
        }
    }
}
