package net.the_goldbeards.lootdebugs.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Sheep;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class TurretModel <T extends TurretEntity> extends EntityModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "turret"), "main");

    private final ModelPart body;
    private final ModelPart stand;

    public TurretModel(ModelPart root) {
    this.body = root.getChild("body");
    this.stand = root.getChild("stand");
}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 39).addBox(-1.0699F, -4.469F, -1.0344F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 19).addBox(1.1801F, -11.469F, -3.1594F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-2.5699F, -11.469F, 3.8406F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-3.0699F, -8.469F, -0.6594F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(12, 29).addBox(-1.5699F, -2.969F, -1.5594F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-2.3199F, -11.469F, -3.1594F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-1.5699F, -5.719F, -3.1594F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.5699F, -11.469F, -0.1594F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 29).addBox(2.6801F, -9.469F, -2.1594F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 66).addBox(1.6801F, -9.469F, 0.8406F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 11).addBox(-3.3199F, -4.609F, -4.7894F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 66).addBox(-2.8199F, -9.469F, 0.8406F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 29).addBox(-3.8199F, -9.469F, -2.1594F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 56).addBox(2.6801F, -5.969F, -3.1594F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 66).addBox(1.6801F, -5.969F, -2.1594F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 53).addBox(-3.8199F, -5.969F, -3.1594F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 66).addBox(-2.8199F, -5.969F, -2.1594F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 64).addBox(-3.3199F, -5.469F, 0.0906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 64).addBox(-3.1699F, -5.969F, 2.6906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 64).addBox(2.0301F, -5.969F, 2.6906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 64).addBox(2.1801F, -5.469F, 0.0906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 60).addBox(-1.0699F, -11.969F, -0.0594F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).addBox(-1.0699F, -11.969F, 0.4406F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 29).addBox(-2.0699F, -5.969F, 3.0906F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 42).addBox(-1.5699F, -4.969F, 2.5906F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 39).addBox(-1.3199F, -5.469F, 2.8406F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 53).addBox(-1.8199F, -5.469F, 2.8406F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 64).addBox(0.6801F, -12.069F, 4.5906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 64).addBox(0.1801F, -12.069F, 4.5906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 64).addBox(0.6801F, -12.069F, 4.0906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 64).addBox(0.1801F, -12.069F, 4.0906F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 45).addBox(-1.8199F, -12.319F, 3.8406F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-1.3199F, -13.069F, 4.3406F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 66).addBox(-3.8199F, -4.609F, -4.7894F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0699F, 20.469F, -0.0906F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 53).addBox(0.25F, 1.25F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 56).addBox(2.0F, -0.5F, -0.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 60).addBox(0.5F, 1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 45).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0699F, -9.969F, 6.3406F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 53).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9301F, -13.134F, 4.7863F, 0.0F, 1.5708F, -0.6109F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 64).addBox(-0.41F, -0.1058F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4301F, -13.0282F, 4.8763F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(20, 60).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4301F, -10.179F, 5.4656F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(12, 53).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4301F, -12.1282F, 5.7763F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(26, 45).addBox(-1.5F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 53).addBox(-1.0F, -0.5F, -0.8F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4301F, -5.719F, 5.8406F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(52, 45).addBox(-1.5F, -0.9F, -0.35F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 60).addBox(-0.4F, -1.0F, -0.45F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 60).addBox(-0.4F, -1.0F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 60).addBox(-0.6F, -1.0F, -0.45F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 60).addBox(-0.6F, -1.0F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0699F, -12.469F, 0.9406F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 66).addBox(-0.4F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5699F, -6.969F, 2.0906F, -0.3123F, 0.1586F, 0.4549F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(4, 66).addBox(-0.4F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5699F, -6.719F, 2.0906F, -0.3123F, 0.1586F, 0.4549F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(8, 66).addBox(-0.4F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5699F, -6.469F, 2.0906F, -0.3123F, 0.1586F, 0.4549F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(12, 60).addBox(-2.0F, -1.0F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 60).addBox(-2.0F, -1.0F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 49).addBox(-1.25F, -1.25F, 1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 49).addBox(-1.25F, -1.25F, -2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 19).addBox(-1.75F, -1.25F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(38, 19).addBox(0.75F, -1.25F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5809F, -9.9773F, 3.3406F, 0.0F, 0.0F, -0.9163F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(14, 11).addBox(-0.5F, 0.0F, -6.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 11).addBox(6.0F, 0.0F, -6.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3199F, -9.469F, -2.1594F, 1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(6, 56).addBox(0.1F, -1.25F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9301F, -8.219F, 4.5906F, 0.0F, 0.0F, -0.6545F));

        PartDefinition cube_r14 = body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(12, 56).addBox(0.1F, -1.25F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9301F, -8.469F, 4.5906F, 0.0F, 0.0F, -0.6545F));

        PartDefinition cube_r15 = body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(18, 56).addBox(0.1F, -1.25F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9301F, -8.719F, 4.5906F, 0.0F, 0.0F, -0.6545F));

        PartDefinition cube_r16 = body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(24, 56).addBox(0.1F, -1.25F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9301F, -8.969F, 4.5906F, 0.0F, 0.0F, -0.6545F));

        PartDefinition cube_r17 = body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(32, 66).addBox(-0.6F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4301F, -6.469F, 2.0906F, -0.3123F, -0.1586F, -0.4549F));

        PartDefinition cube_r18 = body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(36, 66).addBox(-0.6F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4301F, -6.719F, 2.0906F, -0.3123F, -0.1586F, -0.4549F));

        PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(40, 66).addBox(-0.6F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4301F, -6.969F, 2.0906F, -0.3123F, -0.1586F, -0.4549F));

        PartDefinition barrel = body.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(18, 0).addBox(-1.5F, -2.5F, -4.3125F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-1.5F, -2.5F, -5.5625F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 34).addBox(-1.0F, -2.25F, -7.3125F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-1.0F, 0.25F, -7.3125F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0699F, -8.469F, -0.8469F));

        PartDefinition stand = partdefinition.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(0, 45).addBox(-1.5F, -1.0F, 5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 51).addBox(-1.5F, -1.1F, 4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 49).addBox(-1.5F, -1.1F, 6.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 19).addBox(-1.5F, -5.0F, -1.625F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r20 = stand.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(26, 39).addBox(-1.5F, -1.0F, 5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r21 = stand.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(20, 34).addBox(-1.5F, -2.1492F, 1.3736F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 2.5744F, -1.0472F, 3.1416F));

        PartDefinition cube_r22 = stand.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(36, 53).addBox(-0.75F, -2.8993F, -0.6764F, 1.0F, 1.0F, 2.25F, new CubeDeformation(0.0F))
                .texOffs(30, 56).addBox(-0.25F, -2.8993F, -0.6764F, 1.0F, 1.0F, 2.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 2.2253F, -1.0472F, 3.1416F));

        PartDefinition cube_r23 = stand.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(26, 42).addBox(-1.5F, -1.0F, 5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r24 = stand.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(32, 34).addBox(-1.5F, -2.1492F, 1.3736F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 2.5744F, 1.0472F, 3.1416F));

        PartDefinition cube_r25 = stand.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(30, 53).addBox(-0.75F, -2.8993F, -0.6764F, 1.0F, 1.0F, 2.25F, new CubeDeformation(0.0F))
                .texOffs(36, 56).addBox(-0.25F, -2.8993F, -0.6764F, 1.0F, 1.0F, 2.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 2.2253F, 1.0472F, 3.1416F));

        PartDefinition cube_r26 = stand.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(44, 34).addBox(-1.5F, -2.1492F, 1.3736F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r27 = stand.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(42, 53).addBox(-0.75F, -2.8993F, -0.6764F, 1.0F, 1.0F, 2.25F, new CubeDeformation(0.0F))
                .texOffs(42, 56).addBox(-0.25F, -2.8993F, -0.6764F, 1.0F, 1.0F, 2.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r28 = stand.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(0, 51).addBox(-1.5F, -1.1F, 6.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 49).addBox(-1.5F, -1.1F, 4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r29 = stand.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(24, 64).addBox(-0.4971F, 1.9F, -4.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 1.0472F, 3.1416F));

        PartDefinition cube_r30 = stand.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(28, 64).addBox(-0.4971F, 1.9F, -4.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7489F, 0.0F, 0.0F));

        PartDefinition cube_r31 = stand.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(32, 64).addBox(-0.4971F, 1.9F, -4.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, -1.0472F, 3.1416F));

        PartDefinition cube_r32 = stand.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(8, 51).addBox(-1.5F, -1.1F, 4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 49).addBox(-1.5F, -1.1F, 6.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        return LayerDefinition.create(meshdefinition, 80, 80);
    }
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stand.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }



    @Override
    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {


       this.body.yRot = 0;//0 - 6.---

       System.out.println(this.body.yRot);

        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
    }
}
