package net.the_goldbeards.lootdebugs.client.model.Projectiles;

// Made with Blockbench 4.5.0-beta.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Tools.ShieldEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ShieldEmitterModel <T extends ShieldEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "shield_emitter"), "main");
    private final ModelPart VoxelShapes;

    public ShieldEmitterModel(ModelPart root) {
        this.VoxelShapes = root.getChild("VoxelShapes");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition VoxelShapes = partdefinition.addOrReplaceChild("VoxelShapes", CubeListBuilder.create().texOffs(0, 19).addBox(-4.0F, -0.125F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-4.5F, 0.375F, -4.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, 2.125F, -4.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(32, 10).addBox(-4.0F, 2.625F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-4.25F, 1.375F, -4.25F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-1.0F, -0.375F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 28).addBox(-1.0F, -0.625F, -2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 28).addBox(-2.0F, -0.625F, 1.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 19).addBox(-2.0F, -0.625F, -2.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 19).addBox(1.0F, -0.625F, -1.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(8, 39).addBox(-0.75F, 1.025F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 39).addBox(4.0F, 1.025F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-0.75F, 1.025F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 34).addBox(-5.0F, 1.025F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 34).addBox(-3.1216F, -0.375F, 2.0784F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 34).addBox(2.0784F, -0.375F, 2.0784F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 34).addBox(2.0784F, -0.375F, -3.0784F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 34).addBox(-3.1216F, -0.375F, -3.0784F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 34).addBox(-4.75F, -0.225F, -0.65F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(2.75F, -0.225F, -0.65F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 28).addBox(-0.65F, -0.225F, 2.75F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 28).addBox(-0.65F, -0.225F, -4.75F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.375F, 0.0F));

        return LayerDefinition.create(meshdefinition, 80, 80);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        VoxelShapes.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}