package net.the_goldbeards.lootdebugs.util.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class LootdebugsClientConfig
{
 public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
 public static final ForgeConfigSpec SPEC;

 static
 {
     //WARING, CONFIG IS NOT REGISTERED IN LootDebugsMain.class
     BUILDER.push("Lootdebugs Config - Client");
     BUILDER.pop();
     SPEC = BUILDER.build();
 }
}
