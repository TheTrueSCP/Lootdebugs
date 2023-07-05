package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.HeartstoneDefender;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.TileEntity.OmmoranHeartstoneDefender.OmmoranHeartstoneDefenderEntitySyncPacket;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class HeartstoneDefenderTile extends BlockEntity
{
    @Nullable
    private int targetID;

    private static int searchRadiusInBlocks = 40;

    private static final Predicate<LivingEntity> ENEMY_AND_NEUTRAL_PREDECATE = (p_30636_) ->
    {
        return p_30636_ instanceof LivingEntity;
    };

    private static final Predicate<BlockState> BLOCK_COLLISION_EXCEPTIONS = (blockState ->
    {
        if(blockState.is(ModBlocks.HEARTSTONE_DEFENDER.get()))
        {
            return false;
        }
        else
        {
            return !blockState.isAir();
        }
    });

    private static final TargetingConditions TARGETING = TargetingConditions.forCombat().range(searchRadiusInBlocks).selector(ENEMY_AND_NEUTRAL_PREDECATE);

    private int damageCooldown = 10;

    public HeartstoneDefenderTile(BlockPos pWorldPosition, BlockState pBlockState)
    {
        super(ModTileEntities.HEARTSTONE_DEFENDER_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, HeartstoneDefenderTile hearhstoneDefenderTile)
    {
        LivingEntity target = level.getNearestEntity(level.getEntitiesOfClass(LivingEntity.class, getSearchBound(pos)), TARGETING, null, pos.getX(), pos.getY(), pos.getZ());

        if (target != null)
        {
            if(!ModUtils.BlockHelpers.isBlockBetween(level, target.blockPosition().above(), pos, BLOCK_COLLISION_EXCEPTIONS))
            {
                hearhstoneDefenderTile.setTarget(target);

                hearhstoneDefenderTile.damageCooldown--;

                if(hearhstoneDefenderTile.damageCooldown <= 0)
                {
                    target.hurt(ModDamageSources.OMMORAN_HEARTSTONE_DEFENDER, 2);
                    hearhstoneDefenderTile.damageCooldown = 10;
                }
            }
            else
            {
                hearhstoneDefenderTile.setTargetID(0);
            }
        }
        else
        {
            hearhstoneDefenderTile.setTargetID(0);
        }

    }

    @Nullable
    public Entity getTarget()
    {
      return level.getEntity(targetID);
    }

    public void setTarget(Entity target) {
       setTargetID(target.getId());
    }

    private static AABB getSearchBound(BlockPos pos)
    {
        return new AABB(pos.getX() - searchRadiusInBlocks, pos.getY() - searchRadiusInBlocks, pos.getZ() - searchRadiusInBlocks, pos.getX() + searchRadiusInBlocks, pos.getY() + searchRadiusInBlocks, pos.getZ() + searchRadiusInBlocks);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
            pTag.putInt("target", targetID);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag)
    {
        targetID = pTag.getInt("target");
        super.load(pTag);
    }

    public int getTargetID()
    {
        return targetID;
    }

    public void setTargetID(int targetID)
    {
        this.targetID = targetID;
        PacketHandler.sendToClients(new OmmoranHeartstoneDefenderEntitySyncPacket(targetID, this.getBlockPos()));
    }
    public void setTargetIDSync(int targetID)
    {
        this.targetID = targetID;
    }
}
