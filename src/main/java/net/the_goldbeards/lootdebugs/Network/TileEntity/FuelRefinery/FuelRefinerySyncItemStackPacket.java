package net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class FuelRefinerySyncItemStackPacket
{
    private final ItemStackHandler itemStackHandler;
    private final BlockPos pos;

    public FuelRefinerySyncItemStackPacket(ItemStackHandler itemStackHandler, BlockPos pos) {
        this.itemStackHandler = itemStackHandler;
        this.pos = pos;
    }

    public static FuelRefinerySyncItemStackPacket decode(FriendlyByteBuf buf)
    {
        List<ItemStack> collection = buf.readCollection(ArrayList::new, FriendlyByteBuf::readItem);
        ItemStackHandler itemStackHandler = new ItemStackHandler(collection.size());
        for (int i = 0; i < collection.size(); i++) {
            itemStackHandler.setStackInSlot(i, collection.get(i));
        }

        return new FuelRefinerySyncItemStackPacket(itemStackHandler, buf.readBlockPos());
    }

    public static void encode(FuelRefinerySyncItemStackPacket msg, FriendlyByteBuf buf)
    {
        Collection<ItemStack> list = new ArrayList<>();
        for(int i = 0; i < msg.itemStackHandler.getSlots(); i++) {
            list.add(msg.itemStackHandler.getStackInSlot(i));
        }

        buf.writeCollection(list, FriendlyByteBuf::writeItem);
        buf.writeBlockPos(msg.pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof FuelRefineryTile blockEntity) {
                blockEntity.setHandler(this.itemStackHandler);
            }
        });
        return true;
    }
}
