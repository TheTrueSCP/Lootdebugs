package net.the_goldbeards.lootdebugs.capability.Class;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IClassData implements ICapabilitySerializable<CompoundTag>
{

    private final LazyOptional<IClassData> capability = LazyOptional.of(() -> this);


    public IClassData()
    {
        this.cooldown = LootdebugsConfig.DWARF_CHANGE_COOLDOWN.get();
    }
    private int cooldown;
    private Classes dwarfClass = Classes.LeafLover;

    public enum Classes
    {
        LeafLover,
        Driller,
        Scout,
        Engineer,
        Gunner,
        TheTrueSCP,
        MonsieurHannes
    }


    public Classes getDwarfClass()
    {
        return dwarfClass;
    }

    public void setDwarfChangeCooldown(int cooldown)
    {
        this.cooldown = cooldown;
    }

    public int getDwarfChangeCooldown()
    {
        return cooldown;
    }

    public void decreasementDwarfChangeCooldown()
    {
        cooldown--;
    }

    public void setDwarfClass(Classes dwarfClass)
    {
        this.dwarfClass = dwarfClass;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ClassDataCap.CLASS_DATA.orEmpty(cap, capability);
    }

    @Override
    public CompoundTag serializeNBT() {

       CompoundTag compoundTag = new CompoundTag();
       compoundTag.putString("dwarfClass", getDwarfClass().name());
        compoundTag.putInt("dwarfChangeCooldown", getDwarfChangeCooldown());
       return compoundTag;
    }



    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
    setDwarfClass(Classes.valueOf(nbt.getString("dwarfClass")));
    setDwarfChangeCooldown(nbt.getInt("dwarfChangeCooldown"));

    }
}
