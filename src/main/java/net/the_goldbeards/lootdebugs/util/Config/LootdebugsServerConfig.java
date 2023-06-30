package net.the_goldbeards.lootdebugs.util.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class LootdebugsServerConfig
{
 public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
 public static final ForgeConfigSpec SPEC;

 public static final ForgeConfigSpec.ConfigValue<Boolean> SATCHEL_CHARGE_BLINK;
 public static final ForgeConfigSpec.ConfigValue<Boolean> LOOTBUG_PACIFIST_MODE;
 public static final ForgeConfigSpec.ConfigValue<Integer> FLARE_COOLDOWN;
 public static final ForgeConfigSpec.ConfigValue<Integer> FLARE_REFILL_TIME;
 public static final ForgeConfigSpec.ConfigValue<Integer> DWARF_CHANGE_COOLDOWN;
 public static final ForgeConfigSpec.ConfigValue<Integer> DWARF_SALUTE_COOLDOWN;

 static
 {
     BUILDER.push("Lootdebugs Config - Server");

     SATCHEL_CHARGE_BLINK = BUILDER.comment("Satchel Charge blink may CAN cause lags if too many satchel charges are placed, so here you can disable it \n The Satchel Charge will then only be lit").define("satchel_charge_blink", true);

     LOOTBUG_PACIFIST_MODE = BUILDER.comment("When true disable any violence to lootbugs on the server, that means no player can hurt lootbugs").define("Lootbug Pacifist", false);

     BUILDER.comment("\nCooldowns are calculated by multiply your seconds with 20, Example: 2 Seconds = 2*20 = 40, Dwarf Change Cooldown = 40");

     FLARE_COOLDOWN = BUILDER.define("Flare Throw Cooldown", 5);

     FLARE_REFILL_TIME = BUILDER.define("Flare Refill Time", 200);

     DWARF_CHANGE_COOLDOWN = BUILDER.define("Dwarf Change Cooldown", 60);

     DWARF_SALUTE_COOLDOWN = BUILDER.define("Dwarf Salute Cooldown", 40);

     BUILDER.pop();
     SPEC = BUILDER.build();
 }
}
