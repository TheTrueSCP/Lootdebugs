package net.the_goldbeards.lootdebugs.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;

public class DrunknessEffect extends MobEffect
{
    public DrunknessEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        if(pLivingEntity instanceof Player player)
        {
        }


        if(pAmplifier >= 6)
        {
            pLivingEntity.hurt(ModDamageSources.TOODRUNKIN, Float.MAX_VALUE);
        }


        super.applyEffectTick(pLivingEntity, pAmplifier);
    }



    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
