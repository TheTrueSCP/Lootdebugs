package net.the_goldbeards.lootdebugs.capability.Class;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ClassDataCap
{

    public static final Capability<IClassData> CLASS_DATA = CapabilityManager.get(new CapabilityToken<>(){});

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IClassData.class);
    }
}
