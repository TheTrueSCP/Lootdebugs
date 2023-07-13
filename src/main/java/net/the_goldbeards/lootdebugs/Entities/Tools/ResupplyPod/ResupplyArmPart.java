package net.the_goldbeards.lootdebugs.Entities.Tools.ResupplyPod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class ResupplyArmPart extends net.minecraftforge.entity.PartEntity<ResupplyDropEntity> {

    public final ResupplyDropEntity parentEntity;
    public final String name;
    public final int id;
    private final EntityDimensions size;

    public ResupplyArmPart(ResupplyDropEntity parent, String pName, int id, float pWidth, float pHeight) {
        super(parent);
        this.size = EntityDimensions.scalable(pWidth, pHeight);
        this.refreshDimensions();
        this.parentEntity = parent;
        this.name = pName;
        this.id = id;
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        if(ModUtils.DwarfClasses.isPlayerDwarf(pPlayer))
        {
            if(parentEntity.getSupplyArmState(id))
            {
                parentEntity.clearArm(this.id);
                parentEntity.handlePlayerInteract(pPlayer);
            }
        }
        else
        {
            pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.resupply_pod.no_dwarf"), true);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return this.size;
    }

    @Override
    public boolean shouldBeSaved() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}
