package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.init.ModEffects;

public class FlintLockesDelightItem extends BeerItem
{

    public FlintLockesDelightItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void addEffects(LivingEntity entityLiving) {



        entityLiving.addEffect(new MobEffectInstance(MobEffects.SATURATION,4, 0));
        entityLiving.addEffect(new MobEffectInstance(ModEffects.EXLOSIVE_DIARRHOEA.get(),600, 0));


        super.addEffects(entityLiving);
    }
}
