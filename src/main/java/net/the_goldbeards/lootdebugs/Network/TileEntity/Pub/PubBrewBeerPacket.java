package net.the_goldbeards.lootdebugs.Network.TileEntity.Pub;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub.PubTile;

import java.util.function.Supplier;

public final class PubBrewBeerPacket {


    private final BlockPos pos;




    public PubBrewBeerPacket(BlockPos pos) {

        this.pos = pos;


    }

    public static void encode(PubBrewBeerPacket msg, ByteBuf buf) {

        buf.writeInt(msg.pos.getX());
        buf.writeInt(msg.pos.getY());
        buf.writeInt(msg.pos.getZ());

    }

    public static PubBrewBeerPacket decode(ByteBuf buf) {

        int posx = buf.readInt();
        int posy = buf.readInt();
        int posz = buf.readInt();

        BlockPos p = new BlockPos(posx,posy,posz);

        return new PubBrewBeerPacket(p);
    }
        public static void handle(final PubBrewBeerPacket message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                BlockEntity te = ctx.get().getSender().level.getBlockEntity(message.pos);

                if(te instanceof PubTile) {
                    //System.out.println("TE updated");
                    PubTile pub = (PubTile) ctx.get().getSender().level.getBlockEntity(message.pos);
                    ((PubTile)te).SetBrewToTrue(pub);
                }
                else {
                    //System.out.println("No TE!?");
                }
            });
        }
}
