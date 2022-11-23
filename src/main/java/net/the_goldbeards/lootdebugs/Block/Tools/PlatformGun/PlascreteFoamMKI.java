package net.the_goldbeards.lootdebugs.Block.Tools.PlatformGun;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlascreteFoamMKI extends Block
{

    public PlascreteFoamMKI(Properties p_49795_) {
        super(p_49795_);
    }

    //Bounce Up
    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float p_152430_)
    {
            pEntity.causeFallDamage(p_152430_, 0.75F, DamageSource.FALL);

    }







}
