package net.the_goldbeards.lootdebugs.client.model.Weapons;// Made with Blockbench 4.1.3
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Weapons.SatchelChargeEntity;


public class SatchelChargeModel<T extends SatchelChargeEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "satchel_charge"), "main");
	private final ModelPart Satchelcharge;

	public SatchelChargeModel(ModelPart root) {
		this.Satchelcharge = root.getChild("Satchelcharge");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Satchelcharge = partdefinition.addOrReplaceChild("Satchelcharge", CubeListBuilder.create().texOffs(0, 22).addBox(-1.375F, -2.5F, -1.85F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-1.375F, 1.5F, -1.85F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-4.25F, -2.25F, -3.5F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 17).addBox(-4.25F, -2.25F, 1.75F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(-4.05F, -2.0F, -4.5F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.05F, -2.0F, -4.5F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(10, 22).addBox(-3.3F, -2.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 22).addBox(1.3F, -2.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(34, 22).addBox(-3.3F, 1.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 22).addBox(1.3F, 1.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(1.75F, -2.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 26).addBox(-2.75F, -2.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 26).addBox(1.75F, 1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 26).addBox(-2.75F, 1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 37).addBox(-3.5F, 0.0F, -7.5F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition cable_r1 = Satchelcharge.addOrReplaceChild("cable_r1", CubeListBuilder.create().texOffs(10, 37).addBox(-3.5F, 0.0F, -2.0F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, -3.1416F, 0.0F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public ModelPart root() {
		return this.Satchelcharge;
	}
}