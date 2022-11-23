package net.the_goldbeards.lootdebugs.Events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

@Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ForgeServerEventBusSubscriber
{

}
