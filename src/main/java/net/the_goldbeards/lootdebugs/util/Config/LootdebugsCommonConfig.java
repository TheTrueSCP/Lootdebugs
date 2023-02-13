package net.the_goldbeards.lootdebugs.util.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class LootdebugsCommonConfig
{
 public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
 public static final ForgeConfigSpec SPEC;
 public static final ForgeConfigSpec.ConfigValue<Boolean> LOOTBUG_PACIFIST_MODE;

 static
 {
     BUILDER.push("Lootdebugs Config - Client");

     LOOTBUG_PACIFIST_MODE = BUILDER.comment("When true disable any violence to lootbugs on the client, that means only YOU can not hurt lootbug").define("Lootbug Pacifist", false);

     BUILDER.pop();
     SPEC = BUILDER.build();
 }
}
