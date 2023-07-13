package net.the_goldbeards.lootdebugs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Tools.ResupplyPod.ResupplyDropEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
public class ResupplyDropModel<T extends ResupplyDropEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "resupply_drop"), "main");
	private final ModelPart thing;

	public ResupplyDropModel(ModelPart root) {
		this.thing = root.getChild("thing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition thing = partdefinition.addOrReplaceChild("thing", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -30.0F, -8.0F, 16.0F, 28.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 44).addBox(-7.0F, -32.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(42, 46).addBox(-7.0F, -2.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition arm_1 = thing.addOrReplaceChild("arm_1", CubeListBuilder.create().texOffs(64, 14).addBox(8.0F, -18.0F, -3.0F, 11.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm_2 = thing.addOrReplaceChild("arm_2", CubeListBuilder.create().texOffs(48, 0).addBox(8.0F, -18.0F, -3.0F, 11.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-27.0F, 0.0F, 0.0F));

		PartDefinition arm_3 = thing.addOrReplaceChild("arm_3", CubeListBuilder.create().texOffs(34, 62).addBox(-3.0F, -18.0F, -19.0F, 6.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm_4 = thing.addOrReplaceChild("arm_4", CubeListBuilder.create().texOffs(0, 60).addBox(-3.0F, -18.0F, -19.0F, 6.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 27.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {


		thing.getChild("arm_1").visible = pEntity.arm0Visible();
		thing.getChild("arm_2").visible = pEntity.arm1Visible();
		thing.getChild("arm_3").visible = pEntity.arm2Visible();
		thing.getChild("arm_4").visible = pEntity.arm3Visible();



		super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		thing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}