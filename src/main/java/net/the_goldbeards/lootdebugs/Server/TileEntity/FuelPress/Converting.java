package net.the_goldbeards.lootdebugs.Server.TileEntity.FuelPress;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile;

import java.util.function.Supplier;

public final class Converting {


    private BlockPos pos;




    public Converting(BlockPos pos) {

        this.pos = pos;


    }

    public static void encode(Converting msg, ByteBuf buf) {

        buf.writeInt(msg.pos.getX());
        buf.writeInt(msg.pos.getY());
        buf.writeInt(msg.pos.getZ());

    }

    public static Converting decode(ByteBuf buf) {

        int posx = buf.readInt();
        int posy = buf.readInt();
        int posz = buf.readInt();

        BlockPos p = new BlockPos(posx,posy,posz);

        return new Converting(p);
    }

    public static class Handler {
        public static void handle(final Converting message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                BlockEntity te = ctx.get().getSender().level.getBlockEntity(message.pos);

                if(te instanceof FuelRefineryTile) {
                    ((FuelRefineryTile)te).setConvertingToTrue();
                }
                else {
                }
            });
        }
    }
}
