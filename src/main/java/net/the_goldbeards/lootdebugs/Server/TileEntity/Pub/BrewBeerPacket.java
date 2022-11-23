package net.the_goldbeards.lootdebugs.Server.TileEntity.Pub;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub.PubTile;


import java.util.function.Supplier;

public final class BrewBeerPacket {


    private BlockPos pos;




    public BrewBeerPacket(BlockPos pos) {

        this.pos = pos;


    }

    public static void encode(BrewBeerPacket msg, ByteBuf buf) {

        buf.writeInt(msg.pos.getX());
        buf.writeInt(msg.pos.getY());
        buf.writeInt(msg.pos.getZ());

    }

    public static BrewBeerPacket decode(ByteBuf buf) {

        int posx = buf.readInt();
        int posy = buf.readInt();
        int posz = buf.readInt();

        BlockPos p = new BlockPos(posx,posy,posz);

        return new BrewBeerPacket(p);
    }

    public static class Handler {
        public static void handle(final BrewBeerPacket message, Supplier<NetworkEvent.Context> ctx) {
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
}
