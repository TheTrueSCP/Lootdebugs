package net.the_goldbeards.lootdebugs.capability.Salute;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class SaluteDataCap
{

    public static final Capability<ISaluteData> SALUTE_DATA = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation KEY = new ResourceLocation(LootDebugsMain.MOD_ID, "salute_data");

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(ISaluteData.class);
    }
}
