package net.the_goldbeards.lootdebugs.Server;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.the_goldbeards.lootdebugs.Server.ChangeClass.ChangeClassPacket;
import net.the_goldbeards.lootdebugs.Server.ChangeClass.PlayerClassSyncPacket;
import net.the_goldbeards.lootdebugs.Server.Flare.FlareCountSyncPacket;
import net.the_goldbeards.lootdebugs.Server.Flare.ThrowFlarePacket;
import net.the_goldbeards.lootdebugs.Server.RockAndStone.RockAndStonePacket;
import net.the_goldbeards.lootdebugs.Server.RockAndStone.RockAndStoneSyncPacket;
import net.the_goldbeards.lootdebugs.Server.TileEntity.FuelPress.Converting;
import net.the_goldbeards.lootdebugs.Server.TileEntity.FuelPress.RefuelCanister;
import net.the_goldbeards.lootdebugs.Server.TileEntity.Pub.BrewBeerPacket;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class PacketHandler {

    private static final String VERSION = Integer.toString(1);
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID), () -> VERSION, VERSION::equals, VERSION::equals);

    public static SimpleChannel get()
    {
        return CHANNEL;
    }


    public static void register() {
        
        int disc = 0;

        //Throw Flare
        CHANNEL.registerMessage(disc++, ThrowFlarePacket.class,(packet, buf) -> {}, buffer -> new ThrowFlarePacket(),ThrowFlarePacket::handle);
        CHANNEL.registerMessage(disc++, FlareCountSyncPacket.class, FlareCountSyncPacket::encode,FlareCountSyncPacket::new, FlareCountSyncPacket::handle);

        //Tile Entity
        CHANNEL.registerMessage(disc++, Converting.class, Converting::encode,Converting::decode, Converting.Handler::handle);
        CHANNEL.registerMessage(disc++, RefuelCanister.class, RefuelCanister::encode,RefuelCanister::decode, RefuelCanister.Handler::handle);
        CHANNEL.registerMessage(disc++, BrewBeerPacket.class, BrewBeerPacket::encode, BrewBeerPacket::decode, BrewBeerPacket.Handler::handle);

        //ClassChange
        CHANNEL.registerMessage(disc++, PlayerClassSyncPacket.class, PlayerClassSyncPacket::encode,PlayerClassSyncPacket::new, PlayerClassSyncPacket::handle);
        CHANNEL.registerMessage(disc++, ChangeClassPacket.class, ChangeClassPacket::encode,ChangeClassPacket::decode, ChangeClassPacket::handle);


        //RockAndStone
        CHANNEL.registerMessage(disc++, RockAndStonePacket.class,(packet, buf) -> {},buffer -> new RockAndStonePacket(),RockAndStonePacket::handle);
        CHANNEL.registerMessage(disc++, RockAndStoneSyncPacket.class, RockAndStoneSyncPacket::encode, RockAndStoneSyncPacket::new,RockAndStoneSyncPacket::handle);


        //RockAndStone
      //  CHANNEL.registerMessage(disc++, OpenSlotPacket.class, (msg, buf) -> {}, buf -> new OpenSlotPacket(), OpenSlotPacket::handle);
       // CHANNEL.registerMessage(disc++, SyncSlotToClient.class, (msg, buf) -> buf.writeNbt(msg.getCompound()), buf -> new SyncSlotToClient(buf.readNbt()), SyncSlotToClient::handle);



    }

	/*
	public static void sendNonLocal(IMessage msg, EntityPlayerMP player)
	{
		if (player.server.isDedicatedServer() || !player.getName().equals(player.server.getServerOwner()))
		{
			CHANNEL.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		}
	}*/

    /**
     * Sends a packet to the server.<br>
     * Must be called Client side. 
     */
    public static <MSG> void sendToServer(MSG msg)
    {
        CHANNEL.sendToServer(msg);
    }

    /**
     * Send a packet to a specific player.<br>
     * Must be called Server side. 
     */
    public static <MSG> void sendTo(MSG msg, ServerPlayer player)
    {
        if (!(player instanceof FakePlayer))
        {
            CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    
    public static void send(PacketDistributor.PacketTarget target, Object message)
    {
        CHANNEL.send(target, message);
    }

}
