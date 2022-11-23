package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

public class SkullCrusherItem extends BeerItem
{



    public SkullCrusherItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }


    @Override
    public void addEffects(LivingEntity entityLiving)
    {

        entityLiving.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 0));
        entityLiving.addEffect(new MobEffectInstance(MobEffects.SATURATION,4, 0));

        super.addEffects(entityLiving);
    }
}
