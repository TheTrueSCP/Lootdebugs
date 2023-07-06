package net.the_goldbeards.lootdebugs.capability.Class;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.the_goldbeards.lootdebugs.util.Config.LootdebugsServerConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public class IClassData implements ICapabilitySerializable<CompoundTag>
{

    private final LazyOptional<IClassData> capability = LazyOptional.of(() -> this);


    public IClassData()
    {
        this.cooldown = LootdebugsServerConfig.DWARF_CHANGE_COOLDOWN.get();
    }
    private int cooldown;
    private Classes dwarfClass = Classes.LeafLover;


    public enum Classes implements StringRepresentable
    {
        LeafLover(0, "leaf_lover"),
        Driller(1, "driller"),
        Scout(2, "scout"),
        Engineer(3, "engineer"),
        Gunner(4, "gunner"),
        TheTrueSCP(5, "the_true_scp"),
        MonsieurHannes(420, "monsieur_hannes"),
        Karl(69, "karl");

        private static final Classes[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Classes::getId)).toArray((p_41067_) -> {
            return new Classes[p_41067_];
        });
        final int id;
        private final String name;

        private Classes(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public static Classes byId(int classId) {
            if (classId < 0 || classId >= BY_ID.length) {
                classId = 0;
            }

            return BY_ID[classId];
        }

        public String getSerializedName() {
            return this.name;
        }
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
        Classes readDwarfClass;
        try
        {
            readDwarfClass = Classes.valueOf(nbt.getString("dwarfClass"));
        }
        catch (IllegalArgumentException ignored)
        {
            readDwarfClass = Classes.LeafLover;
        }


    setDwarfClass(readDwarfClass);
    setDwarfChangeCooldown(nbt.getInt("dwarfChangeCooldown"));

    }
}
