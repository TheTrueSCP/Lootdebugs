package net.the_goldbeards.lootdebugs.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class LootdebugsConfig
{
 public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
 public static final ForgeConfigSpec SPEC;

 public static final ForgeConfigSpec.ConfigValue<Boolean> LOOTBUG_PACIFIST_MODE;

 public static final ForgeConfigSpec.ConfigValue<Integer> MAX_FLARE_AMOUNT;
 public static final ForgeConfigSpec.ConfigValue<Integer> FLARE_COOLDOWN;
 public static final ForgeConfigSpec.ConfigValue<Integer> FLARE_GENERATION_TIME;
 public static final ForgeConfigSpec.ConfigValue<Integer> DWARF_CHANGE_COOLDOWN;
 public static final ForgeConfigSpec.ConfigValue<Integer> DWARF_SALUTE_COOLDOWN;

 static
 {
     BUILDER.push("Lootdebugs Config");

     LOOTBUG_PACIFIST_MODE = BUILDER.comment("When true disable the kill-Lootbug ping voicelines").define("Lootbug Pacifist Mode", false);

     MAX_FLARE_AMOUNT = BUILDER.comment("Max Flare which can be carried").define("Max Flare Amount", 4);
     FLARE_COOLDOWN = BUILDER.comment("Flare Throw Cooldown").define("Flare Throw Cooldown in Ticks (20t/sec)", 5);
     FLARE_GENERATION_TIME = BUILDER.comment("Time to generation one flare").define("Flare generation time in Ticks(20t/sec)", 240);

     DWARF_CHANGE_COOLDOWN = BUILDER.comment("Cooldown for Dwarf Change").define("Dwarf Change Cooldown(20t/sec)", 60);

     DWARF_SALUTE_COOLDOWN = BUILDER.define("Dwarf Salute Cooldown(20/sec)", 70);

     BUILDER.pop();
     SPEC = BUILDER.build();
 }
}
