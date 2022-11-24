package net.the_goldbeards.lootdebugs.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.util.ModDamageSources;

public class DrunknessEffect extends MobEffect
{
    public DrunknessEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }
    int tickCount = 0;

    @Override
    public void applyEffectTick(LivingEntity entityLiving, int p_19468_) {
        super.applyEffectTick(entityLiving, p_19468_);

        this.tickCount++;

        if(tickCount >= (20 * 4))
        {
            Vec3 randVec = new Vec3(entityLiving.level.random.nextFloat(-0.75f, 0.75f),0,entityLiving.level.random.nextFloat(-0.6f, 0.6f));
            entityLiving.setDeltaMovement(entityLiving.getDeltaMovement().add(randVec).normalize());
            this.tickCount = 0;
        }
    }




    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
