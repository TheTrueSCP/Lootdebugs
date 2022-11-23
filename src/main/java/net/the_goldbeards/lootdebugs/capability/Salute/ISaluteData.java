package net.the_goldbeards.lootdebugs.capability.Salute;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ISaluteData implements ICapabilitySerializable<CompoundTag>
{

    private final LazyOptional<ISaluteData> capability = LazyOptional.of(() -> this);

    private int cooldown;

    public ISaluteData()
    {
        this.cooldown = LootdebugsConfig.DWARF_SALUTE_COOLDOWN.get();
    }





    public void setDwarfSaluteCooldown(int cooldown)
    {
        this.cooldown = cooldown;
    }

    public int getDwarfSaluteCooldown()
    {
        return cooldown;
    }

    public void decreasementDwarfSaluteCooldown()
    {
        cooldown--;
    }


    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return SaluteDataCap.SALUTE_DATA.orEmpty(cap, capability);
    }

    @Override
    public CompoundTag serializeNBT() {

       CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("dwarfSaluteCooldown", getDwarfSaluteCooldown());
       return compoundTag;
    }



    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
    setDwarfSaluteCooldown(nbt.getInt("dwarfSaluteCooldown"));
    }
}
