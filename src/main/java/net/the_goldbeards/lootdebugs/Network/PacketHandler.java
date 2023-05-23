package net.the_goldbeards.lootdebugs.Network;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.Capabillity.ChangeClass.ChangeClassPacket;
import net.the_goldbeards.lootdebugs.Network.Capabillity.ChangeClass.PlayerClassSyncPacket;
import net.the_goldbeards.lootdebugs.Network.Entity.Zipline.ChangeDirectionPacket;
import net.the_goldbeards.lootdebugs.Network.Capabillity.Flare.FlareCountSyncPacket;
import net.the_goldbeards.lootdebugs.Network.Capabillity.Flare.ThrowFlarePacket;
import net.the_goldbeards.lootdebugs.Network.Capabillity.RockAndStone.RockAndStonePacket;
import net.the_goldbeards.lootdebugs.Network.Capabillity.RockAndStone.RockAndStoneSyncPacket;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefineryConverting;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefineryRefuelCanister;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefinerySyncFluidPacket;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefinerySyncItemStackPacket;
import net.the_goldbeards.lootdebugs.Network.TileEntity.Lloyd.LloydBrewBeerPacket;

public class PacketHandler
{
    private static SimpleChannel CHANNEL;


    public static void register()
    {

        int id = 0;

        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(LootDebugsMain.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        CHANNEL = net;

        id = registerCapabilityMessages(net, id);

        id = registerEntityMessages(net, id);

        id = registerTileEntitiesMessages(net, id);

    }


    public static int registerCapabilityMessages(SimpleChannel net, int id)
    {
        net.messageBuilder(ChangeClassPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(ChangeClassPacket::decode).encoder(ChangeClassPacket::encode).consumer(ChangeClassPacket::handle).add();
        net.messageBuilder(PlayerClassSyncPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(PlayerClassSyncPacket::new).encoder(PlayerClassSyncPacket::encode).consumer(PlayerClassSyncPacket::handle).add();

        net.messageBuilder(ThrowFlarePacket.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(buffer -> new ThrowFlarePacket()).encoder((packet, buf) -> {}).consumer(ThrowFlarePacket::handle).add();
        net.messageBuilder(FlareCountSyncPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(FlareCountSyncPacket::new).encoder(FlareCountSyncPacket::encode).consumer(FlareCountSyncPacket::handle).add();

        net.messageBuilder(RockAndStonePacket.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(buffer -> new RockAndStonePacket()).encoder((packet, buf) -> {}).consumer(RockAndStonePacket::handle).add();
        net.messageBuilder(RockAndStoneSyncPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(RockAndStoneSyncPacket::new).encoder(RockAndStoneSyncPacket::encode).consumer(RockAndStoneSyncPacket::handle).add();

        return id;
    }

    public static int registerEntityMessages(SimpleChannel net, int id)
    {
        net.messageBuilder(ChangeDirectionPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(buffer -> new ChangeDirectionPacket()).encoder((packet, buf) -> {}).consumer(ChangeDirectionPacket::handle).add();

        return id;
    }

    public static int registerTileEntitiesMessages(SimpleChannel net, int id)
    {
        net.messageBuilder(FuelRefineryConverting.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(FuelRefineryConverting::decode).encoder(FuelRefineryConverting::encode).consumer(FuelRefineryConverting::handle).add();
        net.messageBuilder(FuelRefineryRefuelCanister.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(FuelRefineryRefuelCanister::decode).encoder(FuelRefineryRefuelCanister::encode).consumer(FuelRefineryRefuelCanister::handle).add();
        net.messageBuilder(FuelRefinerySyncFluidPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(FuelRefinerySyncFluidPacket::decode).encoder(FuelRefinerySyncFluidPacket::encode).consumer(FuelRefinerySyncFluidPacket::handle).add();
        net.messageBuilder(FuelRefinerySyncItemStackPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(FuelRefinerySyncItemStackPacket::decode).encoder(FuelRefinerySyncItemStackPacket::encode).consumer(FuelRefinerySyncItemStackPacket::handle).add();


        net.messageBuilder(LloydBrewBeerPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(LloydBrewBeerPacket::decode).encoder(LloydBrewBeerPacket::encode).consumer(LloydBrewBeerPacket::handle).add();


        return id;
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

    public static <MSG> void sendToClients(MSG message) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }

}
