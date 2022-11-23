package net.the_goldbeards.lootdebugs.client.model.Entities;// Made with Blockbench 4.1.3
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class LootbugModel<T extends LootbugEntity> extends AgeableListModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "lootbug"), "main");
	public static final ModelLayerLocation LAYER_LOCATION_GOLDEN = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "lootbug_golden"), "main");
	private final ModelPart body;
	private final ModelPart Head2;
	private final ModelPart head1;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart Leg1;
	private final ModelPart Leg2;
	private final ModelPart Leg3;
	private final ModelPart Leg4;

	public LootbugModel(ModelPart root)
	{


		super(true, 4.0F, 4.0F, 2.0F, 2.0F, 24);
		this.body = root.getChild("body");
		this.Head2 = root.getChild("Head2");
		this.head1 = root.getChild("head1");
		this.tail1 = root.getChild("tail1");
		this.tail2 = root.getChild("tail2");
		this.tail3 = root.getChild("tail3");
		this.Leg1 = root.getChild("Leg1");
		this.Leg2 = root.getChild("Leg2");
		this.Leg3 = root.getChild("Leg3");
		this.Leg4 = root.getChild("Leg4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -9.0F, -8.0F, 24.0F, 18.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -4.0F));

		partdefinition.addOrReplaceChild("Head2", CubeListBuilder.create().texOffs(58, 52).addBox(-8.0F, -7.0F, -5.0F, 16.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -11.0F));

		partdefinition.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(64, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, -14.0F));

		partdefinition.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 34).addBox(-10.0F, -7.0F, -7.0F, 20.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 5.0F));

		partdefinition.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 62).addBox(-6.0F, -5.0F, -7.0F, 12.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 11.0F));

		partdefinition.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(68, 22).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 14.0F));

		partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(72, 76).addBox(8.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(54, 40).addBox(-14.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -8.0F));

		partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(48, 84).addBox(8.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(38, 62).addBox(-14.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -2.0F));

		partdefinition.addOrReplaceChild("Leg3", CubeListBuilder.create().texOffs(68, 84).addBox(8.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(70, 44).addBox(-14.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 4.0F));

		partdefinition.addOrReplaceChild("Leg4", CubeListBuilder.create().texOffs(68, 84).addBox(6.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(70, 44).addBox(-12.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);

		this.tail1.yRot = pEntity.getTailRotation(pPartialTick, 0.0f);
			this.tail2.yRot =pEntity.getTailRotation(pPartialTick, 0.0f);
		this.tail3.yRot =pEntity.getTailRotation(pPartialTick, 0.0f);

	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.Leg1.xRot = Mth.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.2F * limbSwingAmount;
		this.Leg1.yRot = Mth.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.2F * limbSwingAmount;

		this.Leg2.xRot = Mth.sin(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
		this.Leg2.yRot = Mth.sin(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;

		this.Leg3.xRot = Mth.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.2F * limbSwingAmount;
		this.Leg3.yRot = Mth.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.2F * limbSwingAmount;

		this.Leg4.xRot = Mth.sin(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
		this.Leg4.yRot = Mth.sin(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;

		this.head1.xRot = headPitch * ((float)Math.PI / 180F);
		this.head1.yRot = netHeadYaw * ((float)Math.PI / 180F) * 0.6f;

		this.body.zRot = Mth.sin(limbSwing * 0.6662F) * 0.1F * limbSwingAmount;
		this.tail1.zRot = Mth.sin(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * -1;
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(tail1,tail2,tail3,Leg1,Leg2,Leg3,Leg4,body, Head2, head1);
	}

}