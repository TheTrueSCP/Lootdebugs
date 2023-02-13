package net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile;

import java.util.function.Supplier;

import static net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile.fillCanister;

public final class FuelRefineryRefuelCanister {


    private final BlockPos pos;




    public FuelRefineryRefuelCanister(BlockPos pos) {

        this.pos = pos;


    }

    public static void encode(FuelRefineryRefuelCanister msg, ByteBuf buf) {

        buf.writeInt(msg.pos.getX());
        buf.writeInt(msg.pos.getY());
        buf.writeInt(msg.pos.getZ());

    }

    public static FuelRefineryRefuelCanister decode(ByteBuf buf) {

        int posx = buf.readInt();
        int posy = buf.readInt();
        int posz = buf.readInt();

        BlockPos p = new BlockPos(posx,posy,posz);

        return new FuelRefineryRefuelCanister(p);
    }

        public static void handle(final FuelRefineryRefuelCanister message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                BlockEntity te = ctx.get().getSender().level.getBlockEntity(message.pos);

                if(te instanceof FuelRefineryTile fuelRefineryTile)
                {
                    Level pLevel = ctx.get().getSender().level;
                    BlockPos pPos = message.pos;
                    BlockState pState = pLevel.getBlockState(pPos);
                    fillCanister(fuelRefineryTile);
                }
            });
        }
}
