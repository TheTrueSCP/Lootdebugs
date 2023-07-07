package net.the_goldbeards.lootdebugs.capability.Ping;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class PingDataCap
{

    public static final Capability<IPingData> PING_DATA = CapabilityManager.get(new CapabilityToken<>(){});

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IPingData.class);
    }
}
