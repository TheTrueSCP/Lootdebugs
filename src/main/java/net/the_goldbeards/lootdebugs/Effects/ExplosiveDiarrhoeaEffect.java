package net.the_goldbeards.lootdebugs.Effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ExplosiveDiarrhoeaEffect extends MobEffect {
    public ExplosiveDiarrhoeaEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        super.applyEffectTick(pLivingEntity, pAmplifier);

        Random rand = pLivingEntity.getRandom();
        Vec3 entityPos = pLivingEntity.position();
        Level level = pLivingEntity.getLevel();

        int randValue = rand.nextInt(0, 50);//Create rand value, to get rand explosive trigger
        if (randValue == 11) {

            if(level instanceof ServerLevel serverLevel && pLivingEntity instanceof ServerPlayer serverPlayer)
            {
                serverLevel.sendParticles(ParticleTypes.EXPLOSION, entityPos.x, entityPos.y(), entityPos.z(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

            // Minecraft.getInstance().level.addParticle(ParticleTypes.EXPLOSION,entityPos.x(), entityPos.y(), entityPos.z(), 1,1,1);
            level.playSound(null, entityPos.x(), entityPos.y(), entityPos.z(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1, 1);

            Vec3 randMove = new Vec3(rand.nextInt(2), rand.nextInt(1, 3), rand.nextInt(2));//Set Random numbers for move
            pLivingEntity.setDeltaMovement(randMove);


        }



    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}