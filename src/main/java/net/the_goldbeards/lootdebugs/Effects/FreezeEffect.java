package net.the_goldbeards.lootdebugs.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class FreezeEffect extends MobEffect
{
    public FreezeEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        pLivingEntity.setTicksFrozen(20);

        Vec3 deltaMovement = pLivingEntity.getDeltaMovement();

        if(deltaMovement.y > 0)
        {
            pLivingEntity.setDeltaMovement(deltaMovement.x, -9000, deltaMovement.y);
        }
    }


    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
