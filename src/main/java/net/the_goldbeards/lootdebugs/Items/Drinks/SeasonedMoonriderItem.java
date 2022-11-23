package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

public class SeasonedMoonriderItem extends BeerItem
{

    public SeasonedMoonriderItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void addEffects(LivingEntity entityLiving) {

        entityLiving.addEffect(new MobEffectInstance(MobEffects.LEVITATION,40,12));


        entityLiving.addEffect(new MobEffectInstance(MobEffects.SATURATION,4, 0));

        entityLiving.addEffect(new MobEffectInstance(MobEffects.HEAL,120, 1));



        BlockPos posPlayer = new BlockPos(entityLiving.getX(),entityLiving.getY(),entityLiving.getZ());

        super.addEffects(entityLiving);
    }
}
