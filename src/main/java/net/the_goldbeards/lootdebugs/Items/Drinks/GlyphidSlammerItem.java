package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

public class GlyphidSlammerItem extends BeerItem
{

    public GlyphidSlammerItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void addEffects(LivingEntity entityLiving) {
        entityLiving.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
        entityLiving.addEffect(new MobEffectInstance(MobEffects.SATURATION,4, 0));
        super.addEffects(entityLiving);
    }

    @Override
    public void addDrunkness(LivingEntity entityLiving) {

        entityLiving.addEffect(new MobEffectInstance(MobEffects.CONFUSION,6000,getDrunkenAmplifier(entityLiving) + 2));

        super.addDrunkness(entityLiving);
    }
}
