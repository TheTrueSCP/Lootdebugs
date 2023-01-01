package net.the_goldbeards.lootdebugs.Server.TileEntity.FuelPress;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile;

import java.util.function.Supplier;

public final class RefuelCanister {


    private BlockPos pos;




    public RefuelCanister(BlockPos pos) {

        this.pos = pos;


    }

    public static void encode(RefuelCanister msg, ByteBuf buf) {

        buf.writeInt(msg.pos.getX());
        buf.writeInt(msg.pos.getY());
        buf.writeInt(msg.pos.getZ());

    }

    public static RefuelCanister decode(ByteBuf buf) {

        int posx = buf.readInt();
        int posy = buf.readInt();
        int posz = buf.readInt();

        BlockPos p = new BlockPos(posx,posy,posz);

        return new RefuelCanister(p);
    }

    public static class Handler {
        public static void handle(final RefuelCanister message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                BlockEntity te = ctx.get().getSender().level.getBlockEntity(message.pos);

                if(te instanceof FuelRefineryTile) {
                    //System.out.println("TE updated");
                    FuelRefineryTile fuelPressTile = (FuelRefineryTile) ctx.get().getSender().level.getBlockEntity(message.pos);
                    ((FuelRefineryTile)te).fillCanister();
                }
                else {
                    //System.out.println("No TE!?");
                }
            });
        }
    }
}
