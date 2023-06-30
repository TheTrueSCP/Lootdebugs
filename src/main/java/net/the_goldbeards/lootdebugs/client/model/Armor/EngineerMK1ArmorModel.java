package net.the_goldbeards.lootdebugs.client.model.Armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class EngineerMK1ArmorModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "engineer_mk1_armor"), "main");
	public final ModelPart Head;
	public final ModelPart Body;
	public final ModelPart RightArm;
	public final ModelPart LeftArm;
	public final ModelPart RightLeg;
	public final ModelPart LeftLeg;

	public EngineerMK1ArmorModel(ModelPart root) {
		this.Head = root.getChild("Head");
		this.Body = root.getChild("Body");
		this.RightArm = root.getChild("RightArm");
		this.LeftArm = root.getChild("LeftArm");
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(24, 9).addBox(-4.35F, -10.1F, -3.0F, 8.7F, 3.0F, 6.25F, new CubeDeformation(0.0F))
		.texOffs(18, 19).addBox(-4.6F, -8.1F, -5.6F, 9.2F, 1.0F, 6.85F, new CubeDeformation(0.0F))
		.texOffs(47, 0).addBox(-3.5F, -11.1F, -2.75F, 7.0F, 1.0F, 4.75F, new CubeDeformation(0.0F))
		.texOffs(72, 51).addBox(0.75F, -9.1F, 3.25F, 3.6F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 57).addBox(-1.0F, -10.6F, -3.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 50).addBox(-4.35F, -9.1F, 3.25F, 3.6F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 77).addBox(-0.75F, -9.1F, 3.25F, 1.5F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 122).addBox(-3.6F, -6.75F, -5.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(25, 121).addBox(-0.6F, -6.75F, -5.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(27, 121).addBox(-3.1F, -6.75F, -5.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(26, 123).addBox(-3.1F, -4.75F, -5.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(12, 42).addBox(0.45F, -6.9F, -5.0F, 3.3F, 2.3F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(31, 123).addBox(0.6F, -6.75F, -5.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(26, 123).addBox(1.1F, -4.75F, -5.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(28, 126).addBox(1.1F, -6.75F, -5.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(26, 123).addBox(3.6F, -6.75F, -5.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(28, 26).addBox(-3.75F, -6.9F, -5.0F, 3.3F, 2.3F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.35F, -7.1F, -4.35F, 8.7F, 1.0F, 8.7F, new CubeDeformation(0.0F))
		.texOffs(35, 65).addBox(-3.5F, -3.75F, -5.5F, 3.0F, 4.0F, 3.5F, new CubeDeformation(0.0F))
		.texOffs(23, 65).addBox(-4.75F, -3.25F, -5.25F, 3.0F, 4.25F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(73, 19).addBox(-6.0F, -2.75F, -5.0F, 2.0F, 4.25F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(32, 72).addBox(-7.0F, -1.5F, -4.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 64).addBox(0.5F, -3.75F, -5.5F, 3.0F, 4.0F, 3.5F, new CubeDeformation(0.0F))
		.texOffs(63, 52).addBox(-1.5F, -3.5F, -5.25F, 3.0F, 2.5F, 3.5F, new CubeDeformation(0.0F))
		.texOffs(62, 63).addBox(1.75F, -3.25F, -5.25F, 3.0F, 4.25F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 72).addBox(4.0F, -2.75F, -5.0F, 2.0F, 4.25F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 71).addBox(5.0F, -1.5F, -4.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 9).addBox(-4.35F, -0.1F, -2.35F, 8.7F, 12.1F, 4.7F, new CubeDeformation(0.0F))
		.texOffs(40, 59).addBox(-3.1F, 0.9F, 2.15F, 6.2F, 5.1F, 1.7F, new CubeDeformation(0.0F))
		.texOffs(45, 70).addBox(-4.6F, -0.75F, 3.6F, 1.0F, 8.5F, 2.7F, new CubeDeformation(0.0F))
		.texOffs(72, 74).addBox(-4.6F, 0.25F, 6.3F, 1.0F, 6.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 70).addBox(-3.6F, -0.5F, 3.85F, 1.25F, 8.0F, 2.2F, new CubeDeformation(0.0F))
		.texOffs(68, 74).addBox(2.4F, 0.5F, 6.05F, 1.25F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 72).addBox(3.6F, 0.25F, 6.3F, 1.0F, 6.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(70, 9).addBox(3.6F, -0.75F, 3.6F, 1.0F, 8.5F, 2.7F, new CubeDeformation(0.0F))
		.texOffs(8, 71).addBox(-3.65F, 0.5F, 6.05F, 1.25F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 33).addBox(-2.3F, 4.5F, 6.05F, 4.6F, 2.25F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(12, 68).addBox(2.4F, -0.5F, 3.85F, 1.25F, 8.0F, 2.2F, new CubeDeformation(0.0F))
		.texOffs(14, 59).addBox(-2.35F, -0.5F, 3.85F, 4.75F, 7.0F, 2.2F, new CubeDeformation(0.0F))
		.texOffs(70, 48).addBox(-2.3F, 6.5F, 3.85F, 4.6F, 1.25F, 2.45F, new CubeDeformation(0.0F))
		.texOffs(13, 122).addBox(-2.4F, 0.5F, 6.05F, 4.8F, 0.9F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 121).addBox(-2.4F, 3.5F, 6.05F, 4.8F, 0.9F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 76).addBox(-2.3F, 1.5F, 6.05F, 1.25F, 1.9F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(51, 70).addBox(-2.55F, 1.25F, 5.55F, 5.25F, 2.4F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 74).addBox(0.85F, 1.5F, 5.8F, 1.5F, 1.9F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(61, 76).addBox(-0.65F, 1.5F, 5.8F, 1.0F, 1.9F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(75, 36).addBox(0.35F, 2.0F, 5.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(38, 122).addBox(-1.4F, 2.75F, 6.9F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
		.texOffs(38, 122).addBox(-1.4F, 2.0F, 6.9F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
		.texOffs(0, 0).addBox(1.35F, 2.25F, 6.65F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.25F))
		.texOffs(44, 26).addBox(-2.1F, -0.75F, 5.05F, 2.75F, 1.25F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(9, 64).addBox(1.1F, 10.75F, 4.0F, 1.3F, 1.25F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(66, 11).addBox(-2.4F, 10.75F, 4.0F, 1.3F, 1.25F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.25F, 8.0F, 1.8F, 1.0F, 3.5F, 3.35F, new CubeDeformation(0.0F))
		.texOffs(60, 70).addBox(1.25F, 8.0F, 1.8F, 1.0F, 3.5F, 3.35F, new CubeDeformation(0.0F))
		.texOffs(51, 42).addBox(-3.25F, 12.5F, 1.8F, 6.5F, 1.0F, 3.1F, new CubeDeformation(0.0F))
		.texOffs(48, 25).addBox(-3.15F, 8.25F, 1.8F, 6.3F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 50).addBox(-3.4F, 8.15F, 2.05F, 6.8F, 2.5F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 57).addBox(4.25F, 13.7F, -2.35F, 3.0F, 3.0F, 4.7F, new CubeDeformation(0.0F))
		.texOffs(63, 22).addBox(4.25F, 12.6F, -2.35F, 3.0F, 1.0F, 4.7F, new CubeDeformation(0.0F))
		.texOffs(50, 63).addBox(4.35F, 12.7F, -2.25F, 2.8F, 3.0F, 4.5F, new CubeDeformation(0.0F))
		.texOffs(74, 64).addBox(4.0F, 12.35F, 0.6F, 3.5F, 4.6F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(51, 73).addBox(4.0F, 12.35F, -1.6F, 3.5F, 4.6F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 60).addBox(6.6F, 14.25F, -1.7F, 1.0F, 1.2F, 1.2F, new CubeDeformation(0.0F))
		.texOffs(35, 59).addBox(6.6F, 14.25F, 0.5F, 1.0F, 1.2F, 1.2F, new CubeDeformation(0.0F))
		.texOffs(31, 42).addBox(-7.55F, 11.7F, -2.1F, 1.0F, 1.0F, 4.2F, new CubeDeformation(0.0F))
		.texOffs(26, 62).addBox(-6.55F, 11.7F, -2.1F, 1.25F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(63, 24).addBox(-6.55F, 11.7F, 1.1F, 1.25F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 59).addBox(-6.5F, 12.35F, -2.85F, 2.0F, 1.0F, 5.7F, new CubeDeformation(0.0F))
		.texOffs(48, 34).addBox(-7.0F, 13.35F, -2.85F, 3.0F, 3.0F, 5.7F, new CubeDeformation(0.0F))
		.texOffs(74, 0).addBox(-3.5F, 8.0F, -3.45F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 123).addBox(-3.0F, 9.25F, -3.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 46).addBox(-3.5F, 9.0F, -3.2F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 7).addBox(-1.0F, 8.5F, -3.45F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 65).addBox(0.5F, 8.0F, -3.45F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 123).addBox(2.0F, 9.25F, -3.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 5).addBox(-2.5F, 12.0F, -3.2F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 7).addBox(-2.65F, 12.75F, -2.95F, 5.3F, 1.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(61, 9).addBox(-2.15F, 13.75F, -2.95F, 4.3F, 1.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(63, 50).addBox(-1.5F, 13.0F, -3.2F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 13).addBox(-1.25F, 14.75F, -2.95F, 2.5F, 1.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(-4.6F, 7.9F, -2.6F, 9.2F, 2.0F, 5.2F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Backpack_r1 = Body.addOrReplaceChild("Backpack_r1", CubeListBuilder.create().texOffs(66, 57).addBox(1.4F, -29.0F, -2.2F, 1.25F, 1.25F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(16, 68).addBox(1.5F, -27.75F, -2.1F, 1.05F, 1.0F, 1.05F, new CubeDeformation(0.0F))
		.texOffs(70, 19).addBox(1.4F, -26.75F, -2.2F, 1.25F, 1.25F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(30, 72).addBox(1.15F, -25.5F, -2.45F, 1.75F, 1.25F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition Backpack_r2 = Body.addOrReplaceChild("Backpack_r2", CubeListBuilder.create().texOffs(65, 0).addBox(4.6F, -22.45F, -9.25F, 3.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 23.0F, 0.0F, -0.4305F, -0.0735F, 0.0161F));

		PartDefinition Backpack_r3 = Body.addOrReplaceChild("Backpack_r3", CubeListBuilder.create().texOffs(67, 57).addBox(-8.35F, -22.45F, -9.25F, 3.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 23.0F, 0.0F, -0.4305F, 0.0735F, -0.0161F));

		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(57, 76).addBox(-4.2F, 0.05F, -1.25F, 1.1F, 1.35F, 2.7F, new CubeDeformation(0.0F))
		.texOffs(28, 123).addBox(-4.35F, 0.9F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 8).addBox(-4.1F, 1.15F, -1.15F, 1.0F, 2.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(36, 42).addBox(-3.75F, 4.5F, -2.75F, 5.5F, 3.0F, 5.5F, new CubeDeformation(0.0F))
		.texOffs(32, 50).addBox(-3.25F, 5.0F, -2.25F, 4.5F, 5.0F, 4.5F, new CubeDeformation(0.25F))
		.texOffs(37, 42).addBox(0.5F, 2.75F, 1.5F, 1.5F, 2.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(68, 70).addBox(-3.0F, 2.25F, 1.5F, 4.0F, 3.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(46, 9).addBox(-4.0F, 2.75F, 1.5F, 1.5F, 2.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-4.1F, 5.15F, -0.65F, 1.0F, 3.5F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(76, 12).addBox(-4.5F, 5.65F, -1.15F, 1.0F, 1.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(75, 54).addBox(-4.5F, 7.15F, -1.15F, 1.0F, 1.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-3.35F, -2.0F, -2.35F, 4.7F, 12.1F, 4.7F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition shoulderplate_r1 = RightArm.addOrReplaceChild("shoulderplate_r1", CubeListBuilder.create().texOffs(28, 123).addBox(-9.35F, -23.8F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(61, 5).addBox(-10.1F, -23.55F, 6.05F, 5.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 21.0F, 0.0F, 0.4363F, 0.0F, -0.1745F));

		PartDefinition shoulderplate_r2 = RightArm.addOrReplaceChild("shoulderplate_r2", CubeListBuilder.create().texOffs(28, 123).addBox(-9.35F, -25.5F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 120).addBox(-9.35F, -25.5F, 0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 55).addBox(-10.1F, -25.25F, -1.6F, 6.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 21.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition shoulderplate_r3 = RightArm.addOrReplaceChild("shoulderplate_r3", CubeListBuilder.create().texOffs(28, 121).addBox(-9.35F, -23.8F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 59).addBox(-10.1F, -23.55F, -9.25F, 5.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 21.0F, 0.0F, -0.4363F, 0.0F, -0.1745F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 26).addBox(-1.35F, -2.0F, -2.35F, 4.7F, 12.1F, 4.7F, new CubeDeformation(0.0F))
		.texOffs(16, 42).addBox(-1.75F, 4.5F, -2.75F, 5.5F, 3.0F, 5.5F, new CubeDeformation(0.0F))
		.texOffs(16, 50).addBox(-1.25F, 5.0F, -2.25F, 4.5F, 5.0F, 4.5F, new CubeDeformation(0.25F))
		.texOffs(42, 18).addBox(-2.0F, 2.75F, 1.5F, 1.5F, 2.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(20, 9).addBox(-1.0F, 2.25F, 1.5F, 4.0F, 3.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(31, 42).addBox(2.5F, 2.75F, 1.5F, 1.5F, 2.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(74, 4).addBox(3.1F, 1.15F, -1.15F, 1.0F, 2.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(76, 15).addBox(3.1F, 0.05F, -1.25F, 1.1F, 1.35F, 2.7F, new CubeDeformation(0.0F))
		.texOffs(28, 123).addBox(3.35F, 0.9F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 63).addBox(3.5F, 5.65F, -1.15F, 1.0F, 1.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(32, 65).addBox(3.5F, 7.15F, -1.15F, 1.0F, 1.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 25).addBox(3.1F, 5.15F, -0.65F, 1.0F, 3.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition Leftshoulderplate_r1 = LeftArm.addOrReplaceChild("Leftshoulderplate_r1", CubeListBuilder.create().texOffs(12, 25).addBox(-2.366F, 1.645F, -1.375F, 1.0F, 1.0F, 3.35F, new CubeDeformation(0.0F))
		.texOffs(10, 57).addBox(-2.366F, -0.355F, -0.875F, 1.0F, 2.0F, 2.35F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -4.775F, -0.725F, 0.0F, 0.0F, 0.2182F));

		PartDefinition Lamp_r1 = LeftArm.addOrReplaceChild("Lamp_r1", CubeListBuilder.create().texOffs(5, 122).addBox(-0.25F, -0.5F, 2.75F, 0.5F, 2.25F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(5, 122).addBox(-0.25F, -1.0F, 0.75F, 0.5F, 0.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(5, 122).addBox(-0.25F, -1.0F, 0.25F, 0.5F, 1.75F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3247F, -5.7394F, -0.3F, -1.1781F, 0.0F, 0.2182F));

		PartDefinition Lamp_r2 = LeftArm.addOrReplaceChild("Lamp_r2", CubeListBuilder.create().texOffs(0, 6).addBox(-2.75F, -0.5F, -0.075F, 3.25F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 50).addBox(-1.35F, -1.35F, -1.075F, 2.7F, 2.7F, 1.2F, new CubeDeformation(0.0F))
		.texOffs(28, 122).mirror().addBox(-1.1F, 1.15F, -1.275F, 1.75F, -0.05F, -0.05F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(28, 122).mirror().addBox(-0.65F, -1.1F, -1.275F, 1.75F, -0.05F, -0.05F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(3.0F, -4.275F, -0.725F, 0.0F, 0.0F, 0.2182F));

		PartDefinition LampRim_r1 = LeftArm.addOrReplaceChild("LampRim_r1", CubeListBuilder.create().texOffs(28, 122).mirror().addBox(-1.1F, 1.15F, -1.275F, 1.75F, -0.05F, -0.05F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(28, 122).mirror().addBox(-0.65F, -1.1F, -1.275F, 1.75F, -0.05F, -0.05F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(3.0F, -4.275F, -0.725F, 0.0F, 0.0F, -1.3526F));

		PartDefinition shoulderplate_r4 = LeftArm.addOrReplaceChild("shoulderplate_r4", CubeListBuilder.create().texOffs(28, 123).addBox(8.35F, -23.8F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(59, 34).addBox(4.35F, -23.55F, -9.25F, 5.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.5F, 21.0F, 0.0F, -0.4363F, 0.0F, 0.1745F));

		PartDefinition shoulderplate_r5 = LeftArm.addOrReplaceChild("shoulderplate_r5", CubeListBuilder.create().texOffs(28, 123).addBox(8.35F, -25.5F, 0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 121).addBox(8.35F, -25.5F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 14).addBox(3.35F, -25.25F, -1.6F, 6.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.5F, 21.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition shoulderplate_r6 = LeftArm.addOrReplaceChild("shoulderplate_r6", CubeListBuilder.create().texOffs(28, 122).addBox(8.35F, -23.8F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 18).addBox(4.35F, -23.55F, 6.05F, 5.75F, 1.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.5F, 21.0F, 0.0F, 0.4363F, 0.0F, 0.1745F));

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(16, 26).addBox(-2.35F, 0.0F, -2.35F, 4.7F, 12.1F, 4.7F, new CubeDeformation(0.0F))
		.texOffs(46, 7).addBox(-2.6F, 7.25F, -2.6F, 5.2F, 2.1F, 5.2F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(-3.65F, 6.1F, -0.6F, 1.25F, 4.1F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(24, 59).addBox(-3.75F, 6.85F, -0.7F, 1.35F, 1.0F, 1.45F, new CubeDeformation(0.0F))
		.texOffs(0, 59).addBox(-3.75F, 8.35F, -0.7F, 1.35F, 1.0F, 1.45F, new CubeDeformation(0.0F))
		.texOffs(66, 38).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 5.0F, 1.75F, new CubeDeformation(0.0F))
		.texOffs(71, 61).addBox(-2.4F, 9.75F, -2.4F, 4.8F, 2.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition detailthing_r1 = RightLeg.addOrReplaceChild("detailthing_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.9F, -1.8F, 3.3F, 1.25F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.65F, 12.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 25).addBox(-2.35F, 0.0F, -2.35F, 4.7F, 12.1F, 4.7F, new CubeDeformation(0.0F))
		.texOffs(71, 44).addBox(-2.4F, 9.75F, -2.4F, 4.8F, 2.0F, 1.75F, new CubeDeformation(0.0F))
		.texOffs(42, 18).addBox(-2.6F, 7.25F, -2.6F, 5.2F, 2.1F, 5.2F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(2.1F, 6.5F, -0.85F, 1.0F, 3.35F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(0, 57).addBox(2.3F, 6.85F, -0.63F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 52).addBox(2.3F, 8.5F, -0.63F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(66, 27).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 5.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.RightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.LeftLeg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.LeftArm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.RightLeg.yRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}