package net.the_goldbeards.lootdebugs.client.model.Projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Tools.PingEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class PingModel<T extends PingEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "ping"), "main");
    private final ModelPart bone;

    public PingModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(86, 127).addBox(-1.0F, 14.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(-1.0F, 15.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(36, 9).addBox(1.0F, 15.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 6).addBox(-3.0F, 15.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(1.0F, 14.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(-5.0F, 14.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(120, 82).addBox(-6.0F, 13.0F, -2.0F, 12.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 118).addBox(-2.0F, 13.0F, -6.0F, 4.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(66, 116).addBox(-4.0F, 13.0F, -5.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(122, 98).addBox(-5.0F, 13.0F, -4.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(64, 127).addBox(-7.0F, 12.0F, -1.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 110).addBox(-6.0F, 12.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(-4.0F, 14.0F, 1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 30).addBox(-3.0F, 14.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 28).addBox(1.0F, 14.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(1.0F, 14.0F, 1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 22).addBox(1.0F, 14.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(1.0F, 14.0F, -3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-4.0F, 14.0F, -3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 26).addBox(-3.0F, 14.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 35).addBox(-2.0F, 15.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 32).addBox(1.0F, 15.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 29).addBox(1.0F, 15.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 26).addBox(-2.0F, 15.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(122, 119).addBox(-1.0F, 1.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 3).addBox(-3.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 13).addBox(-2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 19).addBox(1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 22).addBox(-2.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 50).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, 1.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(1.0F, 1.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-4.0F, 1.0F, 1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 14).addBox(-3.0F, 1.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(1.0F, 1.0F, 1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(1.0F, 1.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 18).addBox(-3.0F, 1.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-4.0F, 1.0F, -3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(1.0F, 1.0F, -3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 20).addBox(1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 91).addBox(-6.0F, 2.0F, -2.0F, 12.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 117).addBox(-2.0F, 2.0F, -6.0F, 4.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 115).addBox(-4.0F, 2.0F, -5.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(118, 67).addBox(-5.0F, 2.0F, -4.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(76, 43).addBox(-7.0F, 3.0F, -1.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 108).addBox(-6.0F, 3.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-6.0F, 7.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(36, 26).addBox(-7.0F, 7.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(36, 14).addBox(-7.0F, 7.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(102, 60).addBox(-8.0F, 7.0F, -3.0F, 16.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(82, 82).addBox(-8.0F, 7.0F, -3.0F, 16.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 73).addBox(-7.0F, 6.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(112, 7).addBox(-8.0F, 6.0F, -2.0F, 16.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 48).addBox(-5.0F, 5.0F, -6.0F, 10.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(68, 48).addBox(-6.0F, 5.0F, -5.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(74, 25).addBox(-7.0F, 5.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(-1.0F, 5.0F, -8.0F, 2.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(34, 61).addBox(-6.0F, 4.0F, -5.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(112, 33).addBox(-7.0F, 4.0F, -3.0F, 14.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 6.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(76, 34).addBox(-7.0F, 6.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(116, 54).addBox(-8.0F, 6.0F, -2.0F, 16.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 83).addBox(-7.0F, 4.0F, -3.0F, 14.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 99).addBox(-5.0F, 3.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(108, 89).addBox(-6.0F, 3.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(74, 14).addBox(-7.0F, 5.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(62, 73).addBox(-1.0F, 5.0F, -8.0F, 2.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-6.0F, 4.0F, -5.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(100, 125).addBox(-7.0F, 3.0F, -1.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 126).addBox(-7.0F, 12.0F, -1.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(96, 43).addBox(-1.0F, -6.0F, -8.0F, 2.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(-5.0F, -6.0F, -6.0F, 10.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 108).addBox(-8.0F, -8.0F, -3.0F, 16.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 90).addBox(-7.0F, -7.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(92, 119).addBox(-8.0F, -7.0F, -2.0F, 16.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(68, 62).addBox(-6.0F, -6.0F, -5.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 99).addBox(-7.0F, -6.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(34, 72).addBox(-6.0F, -5.0F, -5.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(116, 40).addBox(-7.0F, -5.0F, -3.0F, 14.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r8 = bone.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(36, 1).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(72, 90).addBox(-7.0F, -7.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(118, 76).addBox(-8.0F, -7.0F, -2.0F, 16.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(116, 47).addBox(-7.0F, -5.0F, -3.0F, 14.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(108, 13).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(110, 24).addBox(-6.0F, -4.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r9 = bone.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 26).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(38, 37).addBox(-7.0F, -8.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r10 = bone.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 39).addBox(-7.0F, -8.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(106, 0).addBox(-8.0F, -8.0F, -3.0F, 16.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r11 = bone.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(44, 99).addBox(-7.0F, -6.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(72, 99).addBox(-1.0F, -6.0F, -8.0F, 2.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(72, 0).addBox(-6.0F, -5.0F, -5.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        bone.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
    }
}