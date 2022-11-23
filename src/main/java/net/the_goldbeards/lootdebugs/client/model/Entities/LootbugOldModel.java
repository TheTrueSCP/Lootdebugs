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
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugOldEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class LootbugOldModel<T extends LootbugOldEntity> extends AgeableListModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "lootbug_old"), "main");
	private final ModelPart Bags;
	private final ModelPart Tail1;
	private final ModelPart Face;
	private final ModelPart Body;

	public LootbugOldModel(ModelPart root) {
		super(true, 4.0F, 4.0F, 2.0F, 2.0F, 24);
		this.Bags = root.getChild("Bags");
		this.Tail1 = root.getChild("Tail1");
		this.Face = root.getChild("Face");
		this.Body = root.getChild("Body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Bags = partdefinition.addOrReplaceChild("Bags", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -4.0F));

		PartDefinition Bag2 = Bags.addOrReplaceChild("Bag2", CubeListBuilder.create().texOffs(56, 112).addBox(-2.4F, -4.0F, -10.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(88, 100).addBox(-2.4F, -8.0F, -6.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(40, 80).addBox(-2.4F, -4.0F, -6.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(100, 72).addBox(-2.4F, 4.0F, -6.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(16, 112).addBox(-2.4F, -4.0F, 6.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.6F, -16.0F, -10.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition Bag1 = Bags.addOrReplaceChild("Bag1", CubeListBuilder.create().texOffs(0, 112).addBox(12.0F, -16.0F, -20.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 100).addBox(12.0F, -20.0F, -16.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 76).addBox(12.0F, -16.0F, -16.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(68, 96).addBox(12.0F, -8.0F, -16.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(108, 92).addBox(12.0F, -16.0F, -4.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition Tail1 = partdefinition.addOrReplaceChild("Tail1", CubeListBuilder.create().texOffs(68, 80).addBox(-4.0F, -8.0F, 16.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 104).addBox(-8.0F, -8.0F, 12.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -4.0F));

		PartDefinition Face = partdefinition.addOrReplaceChild("Face", CubeListBuilder.create().texOffs(28, 76).addBox(0.0F, -4.0F, -32.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(116, 20).addBox(-4.0F, -12.0F, -24.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(17, 29).addBox(0.0F, -8.0F, -28.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, -16.0F, -24.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(67, 31).addBox(0.0F, -18.0F, -22.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(0.0F, -12.0F, -24.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(116, 8).addBox(8.0F, -12.0F, -24.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(52, 64).addBox(-4.0F, -4.0F, -28.0F, 16.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 20.0F, -4.0F));

		PartDefinition Feets = Face.addOrReplaceChild("Feets", CubeListBuilder.create(), PartPose.offset(4.0F, 4.0F, 4.0F));

		PartDefinition Feets_Right = Feets.addOrReplaceChild("Feets_Right", CubeListBuilder.create().texOffs(68, 120).addBox(-12.0F, -4.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(120, 44).addBox(-12.0F, -4.0F, -20.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(44, 120).addBox(-12.0F, -4.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition Feets_Left = Feets.addOrReplaceChild("Feets_Left", CubeListBuilder.create().texOffs(116, 112).addBox(8.0F, -4.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(100, 116).addBox(8.0F, -4.0F, -20.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(84, 116).addBox(8.0F, -4.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition Feets_Back = Feets.addOrReplaceChild("Feets_Back", CubeListBuilder.create().texOffs(116, 52).addBox(4.0F, -4.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(116, 36).addBox(-8.0F, -4.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(32, 116).addBox(8.0F, -8.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(72, 112).addBox(8.0F, -8.0F, -16.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(112, 12).addBox(8.0F, -8.0F, -20.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(112, 0).addBox(8.0F, -8.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(108, 104).addBox(8.0F, -8.0F, -8.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -4.0F));

		PartDefinition bone5 = Body.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(96, 64).addBox(-8.0F, -8.0F, -12.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(96, 28).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(96, 20).addBox(-8.0F, -8.0F, -4.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 96).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(64, 40).addBox(-12.0F, -20.0F, -20.0F, 24.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-12.0F, -20.0F, -20.0F, 24.0F, 16.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(0, 60).addBox(-12.0F, -12.0F, 4.0F, 24.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(72, 12).addBox(-8.0F, -16.0F, 4.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(-12.0F, -24.0F, -20.0F, 24.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(72, 0).addBox(-8.0F, -28.0F, -16.0F, 16.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(56, 60).addBox(-15.0F, -22.0F, -4.0F, 31.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(80, 56).addBox(-8.0F, -14.0F, 8.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(80, 80).addBox(12.0F, -20.0F, -16.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(64, 100).addBox(12.0F, -16.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}


	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(Bags, Tail1, Face, Body);
	}
}