package net.the_goldbeards.lootdebugs.capability.Flare;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class FlareDataCap
{

    public static final Capability<IFlareData> FLARE_DATA = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation KEY = new ResourceLocation(LootDebugsMain.MOD_ID, "flare_data");

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IFlareData.class);
    }
}
