package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.util.function.Predicate;

public class FoamEntity extends ThrowableProjectile {


    public FoamEntity(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public FoamEntity( LivingEntity pShooter, Level pLevel) {
        super(ModEntities.FOAM.get(), pShooter, pLevel);

    }

    private final Predicate<BlockState> CHECK_PLACE_BLOCK_BETWEEN = ((blockState) ->
    {
        if(blockState.isAir())
        {
            return false;
        }

        if(blockState.is(ModBlocks.PLASCRETE_FOAM_MKI.get()) || blockState.is(ModBlocks.PLASCRETE_FOAM_MKII.get()))
        {
            return false;
        }

        return true;
    });

    @Override
    protected void onHitEntity(EntityHitResult pResult)
    {
        placeBlocks(pResult.getEntity().blockPosition(), true);
        this.kill();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if(this.isInWater() || this.isInLava())
        {
            this.kill();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {

        if(!level.isClientSide())
        {
            if (this.getOwner() == null) {
                this.kill();

            }
            switch (hitResult.getDirection()) {
                case UP -> placeBlocks(hitResult.getBlockPos().above(), true);
                case DOWN -> placeBlocks(hitResult.getBlockPos().below(), true);
                default -> placeBlocks(hitResult.getBlockPos(), true);
            }


            this.kill();
        }
    }

    private void placeBlocks(BlockPos hitPos, boolean mkII)
    {
        //Square
        int diameter = 5;
        if(diameter % 2 == 0)
        {
            diameter+= 1;
        }

        int forStart = diameter / 2 * -1;
        int forEnd = diameter / 2;

        for(int x = forStart; x <= forEnd; x++)
        {
            for(int y = forStart; y <= forEnd; y++)
            {
                placeBlock(hitPos.north(x).east(y), hitPos, level, mkII);
            }
        }

        //Additional things
        placeBlock(hitPos.north(3).east(-1), hitPos, level, mkII);
        placeBlock(hitPos.north(3).east(0), hitPos, level, mkII);
        placeBlock(hitPos.north(3).east(1), hitPos, level, mkII);

        placeBlock(hitPos.south(3).east(-1), hitPos, level, mkII);
        placeBlock(hitPos.south(3).east(0), hitPos, level, mkII);
        placeBlock(hitPos.south(3).east(1), hitPos, level, mkII);

        placeBlock(hitPos.north(1).east(3), hitPos, level, mkII);
        placeBlock(hitPos.north(0).east(3), hitPos, level, mkII);
        placeBlock(hitPos.north(-1).east(3), hitPos, level, mkII);

        placeBlock(hitPos.north(1).west(3), hitPos, level, mkII);
        placeBlock(hitPos.north(0).west(3), hitPos, level, mkII);
        placeBlock(hitPos.north(-1).west(3), hitPos, level, mkII);
    }

    //only one block
    private void placeBlock(BlockPos pPos, BlockPos origin, Level pLevel, boolean mkII)
    {
        if ((pLevel.isEmptyBlock(pPos)|| pLevel.getBlockState(pPos).is(ModTags.Blocks.REPLACEABLE_BLOCKS)) && (!ModUtils.BlockHelpers.isBlockBetween(pLevel, pPos, origin, CHECK_PLACE_BLOCK_BETWEEN)))
        {

            if(pLevel.getEntities(this, new AABB(pPos)).isEmpty() || pLevel.getEntities(this, new AABB(pPos)).contains(this))
            {
                if (mkII)
                {
                    pLevel.setBlock(pPos, ModBlocks.PLASCRETE_FOAM_MKII.get().defaultBlockState(), 3);
                }
                else
                {
                    pLevel.setBlock(pPos, ModBlocks.PLASCRETE_FOAM_MKI.get().defaultBlockState(), 3);
                }
            }
        }
    }
}
