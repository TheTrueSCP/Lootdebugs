package net.the_goldbeards.lootdebugs.capability.BeardSlot;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class BeardSlotCap
{

    public static final Capability<IBeardSlotData> BEARD_SLOT = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation KEY = new ResourceLocation(LootDebugsMain.MOD_ID, "beard_slot_data");

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IBeardSlotData.class);
    }
}
