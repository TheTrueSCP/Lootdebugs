package net.the_goldbeards.lootdebugs.Network.TileEntity.Lloyd;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Lloyd.LloydTile;

import java.util.function.Supplier;

public final class LloydBrewBeerPacket {


    private final BlockPos pos;




    public LloydBrewBeerPacket(BlockPos pos) {

        this.pos = pos;


    }

    public static void encode(LloydBrewBeerPacket msg, ByteBuf buf) {

        buf.writeInt(msg.pos.getX());
        buf.writeInt(msg.pos.getY());
        buf.writeInt(msg.pos.getZ());

    }

    public static LloydBrewBeerPacket decode(ByteBuf buf) {

        int posx = buf.readInt();
        int posy = buf.readInt();
        int posz = buf.readInt();

        BlockPos p = new BlockPos(posx,posy,posz);

        return new LloydBrewBeerPacket(p);
    }
        public static void handle(final LloydBrewBeerPacket message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                BlockEntity te = ctx.get().getSender().level.getBlockEntity(message.pos);

                if(te instanceof LloydTile) {
                    //System.out.println("TE updated");
                    LloydTile pub = (LloydTile) ctx.get().getSender().level.getBlockEntity(message.pos);
                    ((LloydTile)te).SetBrewToTrue(pub);
                }
                else {
                    //System.out.println("No TE!?");
                }
            });
        }
}
