package net.the_goldbeards.lootdebugs.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.the_goldbeards.lootdebugs.init.ModEffects;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;

public class SlowDeathEffect extends MobEffect
{
    public SlowDeathEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if(pLivingEntity.getEffect(ModEffects.SLOW_DEATH.get()).getDuration() <= 10)
        {
            pLivingEntity.hurt(ModDamageSources.BLACKOUTSTOUT, Float.MAX_VALUE);
        }
    }


    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
