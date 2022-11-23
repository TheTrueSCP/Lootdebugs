package net.the_goldbeards.lootdebugs.capability.Flare;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IFlareData implements ICapabilitySerializable<CompoundTag>
{

    private final LazyOptional<IFlareData> capability = LazyOptional.of(() -> this);

    private int storedFlares;
    private int replenishTickCounter;
    private int flareThrowCoolDown;


    public IFlareData()
    {
        this.storedFlares = 4;
    }

    public int getStoredFlares()
    {
        return storedFlares;
    }

    public void setStoredFlares(int setToFlares)
    {
        this.storedFlares = setToFlares;
    }

    public int getReplenishTickCounter()
    {
        return replenishTickCounter;
    }

    public void setReplenishTickCounter(int replenishTickCounter)
    {
        this.replenishTickCounter = replenishTickCounter;
    }

    public void incrementReplenishTickCounter()
    {
        replenishTickCounter++;
    }


    public int getFlareThrowCoolDown()
    {
        return flareThrowCoolDown;
    }

    public void setFlareThrowCoolDown(int flareThrowCoolDown)
    {
        this.flareThrowCoolDown = flareThrowCoolDown;
    }

    public void decrementFlareThrowCoolDown()
    {
        flareThrowCoolDown--;
    }


    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return FlareDataCap.FLARE_DATA.orEmpty(cap, capability);
    }

    @Override
    public CompoundTag serializeNBT() {

       CompoundTag compoundTag = new CompoundTag();
       compoundTag.putInt("flareCount", getStoredFlares());
       compoundTag.putInt("replenishTickCounter", getReplenishTickCounter());
       compoundTag.putInt("throwCooldown", getFlareThrowCoolDown());
       return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
       setStoredFlares(nbt.getInt("flareCount"));
        setReplenishTickCounter(nbt.getInt("replenishTickCounter"));
       setFlareThrowCoolDown(nbt.getInt("throwCooldown"));

    }
}
