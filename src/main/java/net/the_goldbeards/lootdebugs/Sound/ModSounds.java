package net.the_goldbeards.lootdebugs.Sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ModSounds
{

    public static final DeferredRegister<SoundEvent> SOUND_REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LootDebugsMain.MOD_ID);



    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_REGISTER.register(name, () -> new SoundEvent(new ResourceLocation(LootDebugsMain.MOD_ID, name)));
    }

    public static final RegistryObject<SoundEvent> LLOYD_INTERACTION = registerSoundEvent("pub.pub_interaction");

    public static final RegistryObject<SoundEvent> LOOTBUG_IDLE = registerSoundEvent("lootbug.lootbug_idle");

    public static final RegistryObject<SoundEvent> LOOTBUG_REACTION =registerSoundEvent("lootbug.lootbug_reaction");

    public static final RegistryObject<SoundEvent> LOOTBUG_HURT = registerSoundEvent("lootbug.lootbug_hurt");

    public static final RegistryObject<SoundEvent> LOOTBUG_DEATH = registerSoundEvent("lootbug.lootbug_death");


    public static final RegistryObject<SoundEvent> MORKITE_MINING = registerSoundEvent("ore.morkite_mining");

    public static final RegistryObject<SoundEvent> MINING_SHANTY = registerSoundEvent("rockandstone.mining_shanty");

    public static final RegistryObject<SoundEvent> SMARTSTOUT = registerSoundEvent("beer.smartstout");

    public static final RegistryObject<SoundEvent> RICKROLL = registerSoundEvent("rickroll.rickroll");

    public static final RegistryObject<SoundEvent> ROCK_AND_STONE = registerSoundEvent("rockandstone.salute");

    public static final RegistryObject<SoundEvent> TOOL_SHOOT = registerSoundEvent("tools.shoot");

    public static final RegistryObject<SoundEvent> DRILLS = registerSoundEvent("tools.drills");

    public static final RegistryObject<SoundEvent> TOOL_FOAM_HARDEN = registerSoundEvent("tools.foam.harden");

    public static final RegistryObject<SoundEvent> POWERATTACK = registerSoundEvent("tools.pickaxe.powerattack");


    //Ping

        //Ping Blocks
    public static final RegistryObject<SoundEvent> PING_SETMARK = registerSoundEvent("tools.ping.setmark");

    public static final RegistryObject<SoundEvent> PING_DIRT = registerSoundEvent("tools.ping.dirt");

    public static final RegistryObject<SoundEvent> PING_MORKITE = registerSoundEvent("tools.ping.morkite");

    public static final RegistryObject<SoundEvent> PING_DYSTRUM = registerSoundEvent("tools.ping.dystrum");

    public static final RegistryObject<SoundEvent> PING_RED_SUGAR = registerSoundEvent("tools.ping.red_sugar");

    public static final RegistryObject<SoundEvent> PING_GOLD = registerSoundEvent("tools.ping.gold");

    public static final RegistryObject<SoundEvent> PING_GOLD_WERE_RICH = registerSoundEvent("tools.ping.gold.were_rich");

    public static final RegistryObject<SoundEvent> PING_NITRA = registerSoundEvent("tools.ping.nitra");

    public static final RegistryObject<SoundEvent> PING_MUSHROOM = registerSoundEvent("tools.ping.mushroom");

    public static final RegistryObject<SoundEvent> PING_WORTHLESS = registerSoundEvent("tools.ping.worthless");

    public static final RegistryObject<SoundEvent> PING_MARK = registerSoundEvent("tools.ping.mark");

        //Ping Entitys


    public static final RegistryObject<SoundEvent> PING_LOOTBUG_NORMAL = registerSoundEvent("tools.ping.lootbug_normal");
    public static final RegistryObject<SoundEvent> PING_LOOTBUG_PACIFIST = registerSoundEvent("tools.ping.lootbug_pacifist");





    public static void register(IEventBus eventBus)
    {
        SOUND_REGISTER.register(eventBus);
    }

}