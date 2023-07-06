package net.the_goldbeards.lootdebugs.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;

import java.util.Random;

public class DrunknessEffect extends MobEffect
{
    public DrunknessEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int amp) {
        super.applyEffectTick(pLivingEntity, amp);

        Random rand = pLivingEntity.getRandom();
        Vec3 entityPos = pLivingEntity.position();
        Level level = pLivingEntity.getLevel();

        if(amp > 6)
        {
            pLivingEntity.hurt(ModDamageSources.TOODRUNKIN, Float.MAX_VALUE);
            return;
        }

        if(pLivingEntity.isOnGround() && pLivingEntity.isAlive() && amp > 1)
        {

            pLivingEntity.setDeltaMovement(calculateDrunknessDeltaMovement(pLivingEntity.getDeltaMovement(), pLivingEntity.getRandom(), amp + 1));
        }

    }

    private static float getModDrunknessAmp(int amp)
    {
        switch (amp)
        {
            case 0:
            {
                return 0;
            }
            case 1:
            {
                return 1f;
            }
            case 2:
            {
                return 1f;
            }
            case 3:
            {
                return 1.2f;
            }
            case 4:
            {
                return 1.4f;
            }
            case 5:
            {
                return 1.6f;
            }
            default:
            {
                return 1;
            }
        }
    }

    private static Vec3 calculateDrunknessDeltaMovement(Vec3 deltaMovement, Random random, float modifier)
    {


        return deltaMovement
                .add(new Vec3(random.nextFloat(-0.02f * modifier, 0.02f * modifier), 0, random.nextFloat(-0.02f * modifier, 0.02f * modifier)))
                .multiply(random.nextFloat(0.4f, 1.8f), 1f, random.nextFloat(0.4f, 1.8f));
    }



    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

}
