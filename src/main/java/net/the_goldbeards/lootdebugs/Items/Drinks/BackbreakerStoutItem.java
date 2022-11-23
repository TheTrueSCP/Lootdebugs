package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

public class BackbreakerStoutItem extends BeerItem
{


    public BackbreakerStoutItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void addEffects(LivingEntity entityLiving) {
        entityLiving.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1));
        entityLiving.addEffect(new MobEffectInstance(MobEffects.SATURATION,4, 0));
        super.addEffects(entityLiving);
    }
}
