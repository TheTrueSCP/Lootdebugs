package net.the_goldbeards.lootdebugs.Network.Capabillity.Flare;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Entities.Tools.Flare.FlareEntity;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.capability.Flare.FlareDataCap;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.util.Config.LootdebugsServerConfig;

import java.util.function.Supplier;

public class ThrowFlarePacket
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
                player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
                {
                    if(classCap.getDwarfClass() != IClassData.Classes.LeafLover)
                    {
                        player.getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap -> {


                            if (player.isCreative() || (player.isSpectator()))
                            {

                                world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.FLARE_THROW.get(), SoundSource.PLAYERS, 0.5F, 0.1f);
                                FlareEntity flareEntity = new FlareEntity( player, world);
                                flareEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                                world.addFreshEntity(flareEntity);
                                return;
                            }

                            if (flareCap.getStoredFlares() > 0 && flareCap.getFlareThrowCoolDown() == 0)
                            {
                                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 0.5F, 0.4F / world.getRandom().nextFloat() * 0.4F + 0.8F);
                                FlareEntity flareEntity = new FlareEntity(player, world);
                                flareEntity.shootFromRotation(player, player.getXRot(), player.getYRot(),0.0F, 1.5F, 1.0F);
                                world.addFreshEntity(flareEntity);
                                if (!player.isCreative())
                                {
                                    flareCap.setStoredFlares(flareCap.getStoredFlares() - 1);
                                    flareCap.setFlareThrowCoolDown(LootdebugsServerConfig.FLARE_COOLDOWN.get());
                                }
                                PacketHandler.send(PacketDistributor.PLAYER.with(context::getSender), new FlareCountSyncPacket(flareCap.getStoredFlares()));
                            }
                        });
                    }
                    else
                    {
                        player.displayClientMessage(new TranslatableComponent("player.class.flare.wrong_class"), true);
                    }
                });
            }
        }
    }
}
