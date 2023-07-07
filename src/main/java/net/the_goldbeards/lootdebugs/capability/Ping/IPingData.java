package net.the_goldbeards.lootdebugs.capability.Ping;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public class IPingData implements ICapabilitySerializable<CompoundTag>
{

    private final LazyOptional<IPingData> capability = LazyOptional.of(() -> this);


    public IPingData()
    {
    }
    private PingClasses pingPlayerClass = PingClasses.None;


    public enum PingClasses implements StringRepresentable
    {

        None(0, "none"),
        Driller(1, "driller"),
        Scout(2, "scout"),
        Engineer(3, "engineer"),
        Gunner(4, "gunner");

        private static final PingClasses[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(PingClasses::getId)).toArray((p_41067_) -> {
            return new PingClasses[p_41067_];
        });
        final int id;
        private final String name;

        private PingClasses(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public static PingClasses byId(int classId) {
            if (classId < 0 || classId >= BY_ID.length) {
                classId = 0;
            }

            return BY_ID[classId];
        }

        public String getSerializedName() {
            return this.name;
        }
    }

    public PingClasses getPingPlayerClass()
    {
        return pingPlayerClass;
    }

    public void setPingPlayerClass(PingClasses pingPlayerClass)
    {
        this.pingPlayerClass = pingPlayerClass;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PingDataCap.PING_DATA.orEmpty(cap, capability);
    }

    @Override
    public CompoundTag serializeNBT() {

       CompoundTag compoundTag = new CompoundTag();
       compoundTag.putString("pingPlayerClass",getPingPlayerClass().name());
       return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        PingClasses readDwarfClass;
        try
        {
            readDwarfClass = PingClasses.valueOf(nbt.getString("pingPlayerClass"));
        }
        catch (IllegalArgumentException ignored)
        {
            readDwarfClass = PingClasses.None;
        }
        setPingPlayerClass(readDwarfClass);
    }
}
