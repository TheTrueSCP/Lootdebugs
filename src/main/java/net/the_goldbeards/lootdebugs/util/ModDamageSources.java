package net.the_goldbeards.lootdebugs.util;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSources
{
    public static DamageSource BLACKOUTSTOUT = (new DamageSource("blackoutstout")).bypassArmor();
    public static DamageSource TOODRUNKIN = (new DamageSource("toodrunkin")).bypassArmor();
    public static DamageSource BULLET = (new DamageSource("bullet"));

    public static DamageSource RESUPPLY_POD = (new DamageSource("resupply_pod"));

    public static DamageSource OMMORAN_HEARTSTONE_DEFENDER = (new DamageSource("ommoran_heartstone_defender"));
}
