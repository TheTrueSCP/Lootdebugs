package net.the_goldbeards.lootdebugs.init.Sound;

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


    public static final RegistryObject<SoundEvent> FLARE_IDLE = registerSoundEvent("flare.flare_idle");

    public static final RegistryObject<SoundEvent> FLARE_THROW = registerSoundEvent("flare.flare_throw");


    public static final RegistryObject<SoundEvent> BEER_DRINK = registerSoundEvent("beer.general.drink");

    public static final RegistryObject<SoundEvent> BEER_SMARTSTOUT = registerSoundEvent("beer.smartstout");

    public static final RegistryObject<SoundEvent> MINERAL_MINING = registerSoundEvent("ore.general.mining");

    public static final RegistryObject<SoundEvent> VEGETATION_PICKED = registerSoundEvent("vegetation.general.pick");

    public static final RegistryObject<SoundEvent> MINING_SHANTY = registerSoundEvent("rockandstone.mining_shanty");


    public static final RegistryObject<SoundEvent> RICKROLL = registerSoundEvent("rickroll.rickroll");

    public static final RegistryObject<SoundEvent> SALUTE = registerSoundEvent("rockandstone.salute");

    public static final RegistryObject<SoundEvent> TOOL_SHOOT = registerSoundEvent("tools.shoot");

    public static final RegistryObject<SoundEvent> DRILLS = registerSoundEvent("tools.drills");

    public static final RegistryObject<SoundEvent> DRILLS_OVERHEAT = registerSoundEvent("tools.drills.overheat");


    public static final RegistryObject<SoundEvent> TOOL_FOAM_HARDEN = registerSoundEvent("tools.foam.harden");

    public static final RegistryObject<SoundEvent> POWERATTACK = registerSoundEvent("tools.pickaxe.powerattack");


    //Ping

        //Ping Blocks

    public static final RegistryObject<SoundEvent> PING_DESTROY = registerSoundEvent("tools.ping.destroy");

    public static final RegistryObject<SoundEvent> PING_DIRT = registerSoundEvent("tools.ping.dirt");

    public static final RegistryObject<SoundEvent> PING_DYSTRUM = registerSoundEvent("tools.ping.dystrum");

    public static final RegistryObject<SoundEvent> PING_EGG = registerSoundEvent("tools.ping.egg");

    public static final RegistryObject<SoundEvent> PING_GOLD = registerSoundEvent("tools.ping.gold");

    public static final RegistryObject<SoundEvent> PING_MORKITE = registerSoundEvent("tools.ping.morkite");

    public static final RegistryObject<SoundEvent> PING_NITRA = registerSoundEvent("tools.ping.nitra");

    public static final RegistryObject<SoundEvent> PING_RED_SUGAR = registerSoundEvent("tools.ping.red_sugar");

    public static final RegistryObject<SoundEvent> PING_SETMARK = registerSoundEvent("tools.ping.set_marker");

    public static final RegistryObject<SoundEvent> PING_STRANGE_SPOT = registerSoundEvent("tools.ping.strange_spot");

    public static final RegistryObject<SoundEvent> PING_WORTHLESS = registerSoundEvent("tools.ping.worthless");

    public static final RegistryObject<SoundEvent> PING_IRON = registerSoundEvent("tools.ping.iron");

    public static final RegistryObject<SoundEvent> PING_MUSHROOM = registerSoundEvent("tools.ping.mushroom");

    public static final RegistryObject<SoundEvent> PING_GOLD_WERE_RICH = registerSoundEvent("tools.ping.gold.were_rich");

    public static final RegistryObject<SoundEvent> MARK_SOUND = registerSoundEvent("tools.ping.mark.sound");

    public static final RegistryObject<SoundEvent> MARK_OMMORAN_HEARTHSTONE = registerSoundEvent("tools.ping.ommoran_hearthstone");


    //Ping Entitys

    public static final RegistryObject<SoundEvent> PING_ENEMY = registerSoundEvent("tools.ping.enemy");

    public static final RegistryObject<SoundEvent> PING_LOOTBUG_NORMAL = registerSoundEvent("tools.ping.lootbug");
    public static final RegistryObject<SoundEvent> PING_LOOTBUG_PACIFIST = registerSoundEvent("tools.ping.lootbug.pacifist");

    public static final RegistryObject<SoundEvent> PING_MAGGOT = registerSoundEvent("tools.ping.maggot");



    public static void register(IEventBus eventBus)
    {
        SOUND_REGISTER.register(eventBus);
    }

}