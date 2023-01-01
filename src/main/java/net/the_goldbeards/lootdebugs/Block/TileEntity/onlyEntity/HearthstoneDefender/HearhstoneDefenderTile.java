package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.HearthstoneDefender;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class HearhstoneDefenderTile extends BlockEntity
{

    public HearhstoneDefenderTile(BlockPos pWorldPosition, BlockState pBlockState)
    {
        super(null, pWorldPosition, pBlockState);
    }


    public void tick(Level level, BlockPos pos, BlockState blockState, HearhstoneDefenderTile pEquipmentTableTile)
    {
        LivingEntity nearestPlayer = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, true);
        if(nearestPlayer == null)
        {
            return;
        }

        BlockPos nearestPlayerPos = nearestPlayer.getOnPos();
        Vec3 vec3 = new Vec3((pos.getX() - pos.getX()) * -1, (nearestPlayerPos.getY() - pos.getY()) * -1, (nearestPlayerPos.getZ() - pos.getZ()) * -1);//calculate Movement from Entity to hook


    }

}
