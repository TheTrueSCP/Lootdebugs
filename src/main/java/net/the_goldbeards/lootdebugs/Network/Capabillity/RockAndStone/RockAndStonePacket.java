package net.the_goldbeards.lootdebugs.Network.Capabillity.RockAndStone;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.capability.Salute.SaluteDataCap;
import net.the_goldbeards.lootdebugs.util.Config.LootdebugsServerConfig;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.util.function.Supplier;

public class RockAndStonePacket
{

    public void handle(Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        ServerLevel level = context.getSender().getLevel();

        if (player != null)
        {
            Level world = context.getSender().level;
            if (!world.isClientSide)
            {
                if(!UsefullStuff.DwarfClasses.isPlayerClass(player, IClassData.Classes.LeafLover))
                {
                    player.getCapability(SaluteDataCap.SALUTE_DATA).ifPresent(saluteCap ->
                    {

                        if (saluteCap.getDwarfSaluteCooldown() <= 0)
                        {
                            level.playSound(null, player.blockPosition(), ModSounds.SALUTE.get(), SoundSource.PLAYERS, 3, UsefullStuff.DwarfClasses.getSalutePitch(player));

                            saluteCap.setDwarfSaluteCooldown(LootdebugsServerConfig.DWARF_SALUTE_COOLDOWN.get());

                            PacketHandler.send(PacketDistributor.PLAYER.with(context::getSender), new RockAndStoneSyncPacket(saluteCap.getDwarfSaluteCooldown()));
                        }
                    });
                }
                else
                {
                    player.displayClientMessage(new TranslatableComponent("message.action.salute.must_be_dwarf"), true);
                }
            }

        }
    }
}
