package net.the_goldbeards.lootdebugs.Entities.Tools.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplineBlock;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplineTile;
import net.the_goldbeards.lootdebugs.init.ModEntities;

import javax.annotation.Nullable;

public class ZiplineEntity extends ThrowableProjectile {



    private BlockPos linkedBlockPos;

    public ZiplineEntity(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public ZiplineEntity(LivingEntity pShooter, Level pLevel, BlockPos linkedBlockPos) {
        super(ModEntities.ZIPLINE_ENTITY.get(), pShooter, pLevel);
        this.linkedBlockPos = linkedBlockPos;
    }

    @Deprecated(forRemoval = true, since = "2.1")
    public ZiplineEntity(LivingEntity pShooter, Level pLevel, BlockPos linkedBlockPos, boolean secondPlace, ItemStack usedItem) {
        super(ModEntities.ZIPLINE_ENTITY.get(), pShooter, pLevel);
        this.linkedBlockPos = linkedBlockPos;

    }

    @Override
    protected void defineSynchedData()
    {

    }

    @Override
    public void tick() {
        super.tick();
        if(this.isInWater())
        {
            this.kill();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult)
    {


        BlockPos placePos = new BlockPos(hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ());

        if(placePos != null && linkedBlockPos != null)//If all required val are given
        {



            placePos = this.tryToSetDoubleBlockAroundBlock(this.level, placePos);//Place this Block
            ZiplineBlock.placeBlock(level, linkedBlockPos.below(1));//Place second block at link

            ZiplineStringAnchor anchorLink = ZiplineBlock.placeAnchor(level, linkedBlockPos);
            ZiplineStringAnchor anchorThis = ZiplineBlock.placeAnchor(level, placePos.above(1));

            //Set other blocks link pos
            if (this.level.getBlockEntity(linkedBlockPos) instanceof ZiplineTile ziplineTile)
            {
                ziplineTile.setLinkedPos(placePos.above(1));
                anchorLink.setLinkedPos(placePos.above(1));
                anchorLink.setStringRender(false);
            }

            //set this block link
            if (this.level.getBlockEntity(placePos.above(1)) instanceof ZiplineTile ziplineTile)
            {
                ziplineTile.setLinkedPos(linkedBlockPos);
                anchorThis.setLinkedPos(linkedBlockPos);
                anchorThis.setStringRender(true);
            }

        }

        this.discard();
    }

    @Nullable
    private BlockPos tryToSetDoubleBlockAroundBlock(Level pLevel, BlockPos pPos)
    {
        if (pLevel.isEmptyBlock(pPos))
        {
            ZiplineBlock.placeBlock(pLevel, pPos);
            return pPos;
        }

        else if (pLevel.isEmptyBlock(pPos.below(1)))
        {
            ZiplineBlock.placeBlock(pLevel, pPos.below(1));
            return pPos.below(1);
        }

        else if (pLevel.isEmptyBlock(pPos.above(1)))
        {
            ZiplineBlock.placeBlock(pLevel, pPos.above(1));
            return pPos.above(1);
        }

        else if (pLevel.isEmptyBlock(pPos.north(1)))
        {
            ZiplineBlock.placeBlock(pLevel, pPos.north(1));
            return pPos.north(1);
        }

        else if (pLevel.isEmptyBlock(pPos.east(1)))
        {
            ZiplineBlock.placeBlock(pLevel, pPos.east(1));
            return pPos.east(1);
        }

        else if (pLevel.isEmptyBlock(pPos.south(1)))
        {
            ZiplineBlock.placeBlock(pLevel, pPos.south(1));
            return pPos.south(1);
        }

        else if (pLevel.isEmptyBlock(pPos.west(1)))
        {
            ZiplineBlock.placeBlock(pLevel, pPos.west(1));
            return pPos.west(1);
        }

        else
        {
            this.kill();
            return null;
        }
    }
}
