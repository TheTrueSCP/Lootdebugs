package net.the_goldbeards.lootdebugs.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.Effects.*;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ModEffects
{
    public static DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LootDebugsMain.MOD_ID);

    public static RegistryObject<MobEffect> EXLOSIVE_DIARRHOEA = EFFECTS.register("explosive_diarrhoea",
            ()-> new ExplosiveDiarrhoeaEffect(MobEffectCategory.NEUTRAL,11345123));

    public static RegistryObject<MobEffect> DRUNKNESS = EFFECTS.register("drunkness",
            ()-> new DrunknessEffect(MobEffectCategory.HARMFUL,1123123));

    public static RegistryObject<MobEffect> SLOW_DEATH = EFFECTS.register("slow_death",
            ()-> new SlowDeathEffect(MobEffectCategory.HARMFUL,11345123));

    public static RegistryObject<MobEffect> GOLD_TOUCH = EFFECTS.register("gold_touch",
            ()-> new GoldTouchEffect(MobEffectCategory.NEUTRAL,11345123));

     public static RegistryObject<MobEffect> FREEZE = EFFECTS.register("freeze",
            ()-> new FreezeEffect(MobEffectCategory.HARMFUL,11345123).addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-1.00F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus)
    {
        EFFECTS.register(eventBus);
    }
}
