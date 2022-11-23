package net.the_goldbeards.lootdebugs.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ModPotions
{
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, LootDebugsMain.MOD_ID);

    public static final RegistryObject<Potion> DRUNKNESS_POTION = POTIONS.register("drunkness_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DRUNKNESS.get(), 200, 0)));

    public static void register(IEventBus eventBus)
    {
        POTIONS.register(eventBus);
    }
}
