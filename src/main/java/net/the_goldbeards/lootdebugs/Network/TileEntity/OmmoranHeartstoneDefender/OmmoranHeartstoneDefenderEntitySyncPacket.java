package net.the_goldbeards.lootdebugs.Network.TileEntity.OmmoranHeartstoneDefender;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.HeartstoneDefender.HeartstoneDefenderTile;

import java.util.function.Supplier;

public class OmmoranHeartstoneDefenderEntitySyncPacket
{
    private final int id;
    private final BlockPos pos;

    public OmmoranHeartstoneDefenderEntitySyncPacket(int entityId, BlockPos pos) {
        this.id = entityId;
        this.pos = pos;
    }

    public static OmmoranHeartstoneDefenderEntitySyncPacket decode(FriendlyByteBuf buf)
    {
        return new OmmoranHeartstoneDefenderEntitySyncPacket(buf.readInt(), buf.readBlockPos());
    }

    public static void encode(OmmoranHeartstoneDefenderEntitySyncPacket msg, FriendlyByteBuf buf)
    {
        buf.writeInt(msg.id);
        buf.writeBlockPos(msg.pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof HeartstoneDefenderTile blockEntity) {
                blockEntity.setTargetIDSync(id);
            }
        });
        return true;
    }
}
