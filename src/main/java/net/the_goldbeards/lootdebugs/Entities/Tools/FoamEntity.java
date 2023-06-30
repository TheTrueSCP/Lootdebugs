package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.util.ModTags;

public class FoamEntity extends ThrowableProjectile {


    public FoamEntity(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public FoamEntity( LivingEntity pShooter, Level pLevel) {
        super(ModEntities.FOAM.get(), pShooter, pLevel);

    }

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




    private void placeBlocks(BlockPos HitPos, boolean mkII)
    {
        BlockPos pPos = HitPos;

        BlockPos poseast = pPos.east(1);
        BlockPos poseast1 = pPos.east(2);//
//                                           #x#
        BlockPos poswest = pPos.west(1);// xxXxx
        BlockPos poswest1 = pPos.west(2);// #X#

        BlockPos posnorth = pPos.north(1);
        BlockPos posnorth1 = pPos.north(2);

        BlockPos possouth = pPos.south(1);
        BlockPos possouth1 = pPos.south(2);


        BlockPos northeast = pPos.east(1).north(1);

        BlockPos northwest = pPos.west(1).north(1);

        BlockPos southwest = pPos.west(1).south(1);

        BlockPos southeast = pPos.east(1).south(1);


        BlockPos north_northeast = pPos.east(1).north(2);
        BlockPos north_northwest = pPos.west(1).north(2);

        BlockPos south_southwest = pPos.west(1).south(2);
        BlockPos south_southeast = pPos.east(1).south(2);

        BlockPos east_eastnord = pPos.east(2).north(1);
        BlockPos east_eastsouth = pPos.east(2).south(1);

        BlockPos west_westsouth = pPos.west(2).south(1);
        BlockPos west_westnord = pPos.west(2).north(1);


        //Inner Ring

        placeBlock(posnorth, level, mkII);
        placeBlock(posnorth1, level, mkII);

        placeBlock(poseast, level, mkII);
        placeBlock(poseast1, level, mkII);

        placeBlock(possouth, level, mkII);
        placeBlock(possouth1, level, mkII);

        placeBlock(poswest, level, mkII);
        placeBlock(poswest1, level, mkII);

        //Middlering
        placeBlock(northeast, level, mkII);
        placeBlock(northwest, level, mkII);
        placeBlock(southeast, level, mkII);
        placeBlock(southwest, level, mkII);

        //outer Ring

        placeBlock(north_northeast, level, mkII);
        placeBlock(north_northwest, level, mkII);

        placeBlock(south_southeast, level, mkII);
        placeBlock(south_southwest, level, mkII);

        placeBlock(east_eastnord, level, mkII);
        placeBlock(east_eastsouth, level, mkII);

        placeBlock(west_westsouth, level, mkII);
        placeBlock(west_westnord, level, mkII);

        //central
        placeBlock(pPos, level, mkII);
    }

    //only one block
    private void placeBlock(BlockPos pPos, Level pLevel, boolean mkII)
    {
        if (pLevel.isEmptyBlock(pPos)|| pLevel.getBlockState(pPos).is(ModTags.Blocks.REPLACEABLE_BLOCKS))
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
