package net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile;

import java.util.function.Supplier;

public final class FuelRefinerySyncFluidPacket {


    private final FluidStack fluidStack;
    private final BlockPos pos;

    public FuelRefinerySyncFluidPacket(FluidStack fluidStack, BlockPos pos) {
        this.fluidStack = fluidStack;
        this.pos = pos;
    }

    public FuelRefinerySyncFluidPacket(FriendlyByteBuf buf) {
        this.fluidStack = buf.readFluidStack();
        this.pos = buf.readBlockPos();
    }

    public static void encode(FuelRefinerySyncFluidPacket msg, FriendlyByteBuf buf)
    {
        buf.writeFluidStack(msg.fluidStack);
        buf.writeBlockPos(msg.pos);

    }

    public static FuelRefinerySyncFluidPacket decode(FriendlyByteBuf buf)
    {
        return new FuelRefinerySyncFluidPacket(buf.readFluidStack(), buf.readBlockPos());
    }

        public boolean handle(Supplier<NetworkEvent.Context> supplier)
        {
            NetworkEvent.Context context = supplier.get();

            context.enqueueWork(() -> {
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof FuelRefineryTile blockEntity)
                {
                    blockEntity.setFluid(this.fluidStack);

                    if (Minecraft.getInstance().player.containerMenu instanceof FuelRefineryContainer menu && menu.getFuelRefineryTile().getBlockPos().equals(pos))
                    {
                        menu.setFluid(this.fluidStack);
                    }
                }
            });
            return true;
        }
}
