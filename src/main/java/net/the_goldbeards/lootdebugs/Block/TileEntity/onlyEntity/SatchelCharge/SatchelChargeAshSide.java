package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge;

import net.minecraft.util.StringRepresentable;

public enum SatchelChargeAshSide implements StringRepresentable {
    NONE("none"),
    UP("up");
    private final String name;

    private SatchelChargeAshSide(String pName) {
        this.name = pName;
    }

    public String toString() {
        return this.getSerializedName();
    }

    public String getSerializedName() {
        return this.name;
    }

    public boolean isConnected() {
        return this != NONE;
    }
}
