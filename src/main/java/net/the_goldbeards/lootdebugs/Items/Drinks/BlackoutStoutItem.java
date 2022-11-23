package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.init.ModEffects;

public class BlackoutStoutItem extends BeerItem
{


    public BlackoutStoutItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }


    @Override
    public void addDrunkness(LivingEntity entityLiving) {

        entityLiving.addEffect(new MobEffectInstance(ModEffects.SLOW_DEATH.get(),100,5));

        super.addDrunkness(entityLiving);
    }
}
