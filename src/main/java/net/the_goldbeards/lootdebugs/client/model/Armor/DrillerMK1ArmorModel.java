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

public class DrillerMK1ArmorModel <T extends Entity> extends EntityModel<T>
{// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "driller_mk1_armor"), "main");
    public final ModelPart Head;
    public final ModelPart Body;
    public final ModelPart RightArm;
    public final ModelPart LeftArm;
    public final ModelPart RightLeg;
    public final ModelPart LeftLeg;

    public DrillerMK1ArmorModel(ModelPart root) {
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

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(28, 41).addBox(-4.0F, -9.5F, -3.25F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-5.0F, -9.0F, -4.25F, 10.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.5F, -8.0F, -6.0F, 11.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(24, 135).addBox(0.5F, -7.0F, -5.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 135).addBox(-3.5F, -7.0F, -5.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(110, 6).addBox(-2.75F, -6.75F, -5.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(-3.25F, -4.25F, -5.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(0.75F, -4.25F, -5.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(1.25F, -6.75F, -5.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(46, 156).addBox(3.5F, -7.0F, -5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(38, 156).addBox(-4.5F, -7.0F, -5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 135).addBox(-1.5F, -11.25F, -6.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(86, 162).addBox(1.25F, -11.25F, -5.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(80, 162).addBox(-2.25F, -11.25F, -5.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(110, 6).addBox(-0.75F, -11.0F, -6.25F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(-1.25F, -8.5F, -6.25F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition LampRim_r1 = Head.addOrReplaceChild("LampRim_r1", CubeListBuilder.create().texOffs(110, 6).addBox(4.25F, 0.75F, -6.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(3.75F, 3.25F, -6.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-2.0F, -4.75F, 0.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition Lamp_r1 = Head.addOrReplaceChild("Lamp_r1", CubeListBuilder.create().texOffs(104, 121).addBox(-2.0F, -11.5F, -7.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition BlackFlap_r1 = Head.addOrReplaceChild("BlackFlap_r1", CubeListBuilder.create().texOffs(94, 68).addBox(5.25F, -6.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, -1.3963F, -1.5708F));

        PartDefinition RightSideflap_r1 = Head.addOrReplaceChild("RightSideflap_r1", CubeListBuilder.create().texOffs(90, 149).addBox(4.25F, -5.15F, 4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.7746F, -1.3705F, -1.5908F));

        PartDefinition RightSideflap_r2 = Head.addOrReplaceChild("RightSideflap_r2", CubeListBuilder.create().texOffs(76, 68).addBox(-6.25F, -6.0F, -3.75F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 55).addBox(-6.0F, -6.25F, -4.25F, 2.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition LeftSideflap_r1 = Head.addOrReplaceChild("LeftSideflap_r1", CubeListBuilder.create().texOffs(82, 149).addBox(-6.25F, -5.15F, 4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.7746F, 1.3705F, 1.5908F));

        PartDefinition LeftSideflap_r2 = Head.addOrReplaceChild("LeftSideflap_r2", CubeListBuilder.create().texOffs(0, 81).addBox(5.25F, -6.0F, -3.75F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(46, 55).addBox(4.0F, -6.25F, -4.25F, 2.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition nosething_r1 = Head.addOrReplaceChild("nosething_r1", CubeListBuilder.create().texOffs(62, 156).addBox(-4.8F, -5.0F, -4.8F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 156).addBox(-4.5F, -7.0F, -4.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition GoggleRim_r1 = Head.addOrReplaceChild("GoggleRim_r1", CubeListBuilder.create().texOffs(110, 6).addBox(3.75F, 3.25F, -6.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(4.25F, 0.75F, -6.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(3.75F, -0.75F, -6.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(110, 6).addBox(4.25F, -3.25F, -6.75F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -0.5F, 1.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 22).addBox(-3.5F, 4.0F, -3.6F, 7.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 156).addBox(-2.0F, 8.0F, -4.1F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 162).addBox(-1.5F, 7.0F, -4.1F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(80, 113).addBox(-4.0F, 9.0F, -4.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(94, 172).addBox(2.15F, 8.75F, -4.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(86, 172).addBox(3.15F, 8.75F, -3.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(90, 172).addBox(-4.15F, 8.75F, -3.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(105, 4).addBox(5.25F, 0.405F, -3.4F, 0.0F, 0.0F, 6.0F, new CubeDeformation(0.25F))
                .texOffs(105, 4).addBox(-5.25F, 0.405F, -3.4F, 0.0F, 0.0F, 6.0F, new CubeDeformation(0.25F))
                .texOffs(98, 172).addBox(-3.15F, 8.75F, -4.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 81).addBox(-5.25F, -1.1F, -4.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(40, 81).addBox(4.25F, -1.1F, -4.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(18, 81).addBox(-4.5F, -1.1F, 3.0F, 9.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 93).addBox(-4.5F, 3.05F, -4.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(48, 113).addBox(-4.5F, 7.05F, -4.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(90, 121).addBox(-4.5F, 8.05F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 149).addBox(-4.5F, 9.05F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 81).addBox(3.5F, 3.05F, -4.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(32, 113).addBox(3.5F, 7.05F, -4.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(76, 121).addBox(3.5F, 8.05F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(30, 149).addBox(3.5F, 9.05F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(72, 81).addBox(-3.5F, 3.05F, 2.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(78, 99).addBox(-4.5F, 9.75F, -5.1F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(80, 116).addBox(-4.0F, 10.0F, -4.6F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 135).addBox(-3.0F, 14.0F, -4.6F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 174).addBox(-2.5F, 14.5F, -4.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 174).addBox(1.5F, 14.5F, -4.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Pocketthingidkineedsleepmangoddammitihateeverything_r1 = Body.addOrReplaceChild("Pocketthingidkineedsleepmangoddammitihateeverything_r1", CubeListBuilder.create().texOffs(62, 172).addBox(0.5F, -14.75F, 7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 172).addBox(-1.5F, -14.75F, 7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(118, 4).addBox(-1.0F, -16.7F, 7.15F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(36, 129).addBox(-2.5F, -16.6F, 4.95F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.25F))
                .texOffs(0, 113).addBox(-2.5F, -16.75F, 5.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 121).addBox(-2.5F, -14.9F, 4.95F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.25F))
                .texOffs(120, 4).addBox(1.0F, -16.7F, 7.15F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(118, 7).addBox(-0.3F, -14.2F, 8.15F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(0, 162).addBox(-6.5F, -12.45F, 3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 129).addBox(-6.5F, -11.45F, 3.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 156).addBox(-2.5F, -14.45F, 3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(98, 149).addBox(0.5F, -14.45F, 3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 129).addBox(0.5F, -11.45F, 3.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(84, 156).addBox(4.5F, -12.45F, 3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 172).addBox(1.25F, -29.2F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 172).addBox(0.75F, -29.2F, 4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 172).addBox(-2.25F, -29.2F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 172).addBox(-1.75F, -29.2F, 4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 22).addBox(-3.0F, -28.95F, 2.0F, 6.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 113).addBox(-2.0F, -26.95F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 156).addBox(-1.0F, -23.7F, 7.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 113).addBox(-2.0F, -20.95F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(76, 156).addBox(-1.5F, -20.45F, 6.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(112, 6).addBox(1.0F, -18.2F, 7.4F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(112, 6).addBox(-1.0F, -18.2F, 7.4F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(98, 162).addBox(8.4F, -26.95F, 4.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(92, 162).addBox(8.4F, -16.95F, 4.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(78, 172).addBox(7.7F, -21.95F, 4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 142).addBox(4.0F, -13.45F, 2.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 142).addBox(-7.0F, -13.45F, 2.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 166).addBox(-9.4F, -16.95F, 4.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 156).addBox(-9.15F, -16.95F, 5.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(102, 22).addBox(-9.0F, -17.95F, 0.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(24, 162).addBox(-6.4F, -17.45F, 7.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 172).addBox(-5.2F, -21.95F, 6.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(92, 55).addBox(-8.5F, -22.95F, 1.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 172).addBox(-8.7F, -21.95F, 4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 169).addBox(-9.4F, -26.95F, 4.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-9.0F, -27.95F, 0.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(18, 159).addBox(-9.15F, -26.95F, 5.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 162).addBox(-6.4F, -27.45F, 7.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 0).addBox(-8.0F, -29.95F, 1.5F, 5.0F, 17.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(46, 22).addBox(2.0F, -17.95F, 0.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(8, 156).addBox(6.15F, -16.95F, 5.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 162).addBox(3.4F, -17.45F, 7.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(74, 172).addBox(4.2F, -21.95F, 6.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 55).addBox(2.5F, -22.95F, 1.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(74, 22).addBox(2.0F, -27.95F, 0.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(8, 159).addBox(6.15F, -26.95F, 5.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 162).addBox(3.4F, -27.45F, 7.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 0).addBox(3.0F, -29.95F, 1.5F, 5.0F, 17.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition Tankconnector_r1 = Body.addOrReplaceChild("Tankconnector_r1", CubeListBuilder.create().texOffs(6, 166).addBox(-1.0F, -23.405F, 11.94F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition MiddleSegment_r1 = Body.addOrReplaceChild("MiddleSegment_r1", CubeListBuilder.create().texOffs(36, 168).addBox(2.5F, -17.0F, -7.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 164).addBox(1.5F, -18.0F, -7.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 104).addBox(-4.5F, -22.0F, -7.75F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 162).addBox(-4.5F, -18.0F, -7.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 166).addBox(-4.5F, -17.0F, -7.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition BIGGSegment_r1 = Body.addOrReplaceChild("BIGGSegment_r1", CubeListBuilder.create().texOffs(1, 3).addBox(3.0F, -19.0F, -9.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(0, 3).addBox(-5.0F, -19.0F, -9.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(16, 93).addBox(-5.0F, -23.5F, -9.5F, 10.0F, 4.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(112, 6).addBox(-5.25F, -22.25F, -9.8F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(108, 5).addBox(5.25F, -22.25F, -9.8F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(56, 172).addBox(-5.0F, -21.45F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 172).addBox(-3.2F, -21.45F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(109, 9).addBox(2.0F, -22.25F, -9.8F, 3.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(106, 10).addBox(-5.0F, -22.25F, -9.8F, 3.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(36, 172).addBox(2.45F, -23.95F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 172).addBox(-3.45F, -23.95F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(87, 6).addBox(-5.0F, -23.25F, -9.6F, 10.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(87, 7).addBox(-5.0F, -22.75F, -9.6F, 10.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(93, 6).addBox(-1.5F, -22.25F, -9.6F, 3.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(10, 175).addBox(-0.5F, -22.25F, -9.8F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(105, 10).addBox(-2.0F, -21.75F, -9.8F, 4.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(108, 9).addBox(-1.0F, -21.25F, -9.8F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(42, 172).addBox(2.2F, -21.45F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 172).addBox(4.0F, -21.45F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(36, 104).addBox(-3.0F, 5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(58, 93).addBox(-3.5F, 4.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition BIGGshoulderplate_r1 = RightArm.addOrReplaceChild("BIGGshoulderplate_r1", CubeListBuilder.create().texOffs(58, 41).addBox(-4.0F, -3.1F, -4.5F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-2.0F, -2.5F, -4.5F, 1.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(40, 68).addBox(-4.25F, -3.5F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0036F));

        PartDefinition polster_r1 = RightArm.addOrReplaceChild("polster_r1", CubeListBuilder.create().texOffs(102, 41).addBox(-4.85F, -3.15F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.1781F));

        PartDefinition Middleshoulderplate_r1 = RightArm.addOrReplaceChild("Middleshoulderplate_r1", CubeListBuilder.create().texOffs(52, 104).addBox(-3.25F, 1.5F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition smolshoulderplate_r1 = RightArm.addOrReplaceChild("smolshoulderplate_r1", CubeListBuilder.create().texOffs(48, 121).addBox(-3.25F, 2.0F, -2.5F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(20, 104).addBox(-1.0F, 5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(78, 93).addBox(-1.5F, 4.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition smolshoulderplate_r2 = LeftArm.addOrReplaceChild("smolshoulderplate_r2", CubeListBuilder.create().texOffs(62, 121).addBox(1.25F, 2.0F, -2.5F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition Middleshoulderplate_r2 = LeftArm.addOrReplaceChild("Middleshoulderplate_r2", CubeListBuilder.create().texOffs(68, 104).addBox(1.25F, 1.5F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition polster_r2 = LeftArm.addOrReplaceChild("polster_r2", CubeListBuilder.create().texOffs(0, 55).addBox(2.85F, -3.15F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

        PartDefinition BIGGshoulderplate_r2 = LeftArm.addOrReplaceChild("BIGGshoulderplate_r2", CubeListBuilder.create().texOffs(58, 68).addBox(2.25F, -3.5F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(20, 68).addBox(1.0F, -2.5F, -4.5F, 1.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(80, 41).addBox(2.0F, -3.1F, -4.5F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0036F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 121).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(42, 166).addBox(-2.5F, 9.34F, -3.235F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 142).addBox(-2.5F, 10.34F, -3.985F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 142).addBox(-2.5F, 7.04F, -2.975F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(66, 142).addBox(-2.5F, 7.04F, -3.4F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(78, 142).addBox(1.5F, 10.34F, -3.985F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(42, 168).addBox(1.5F, 9.34F, -3.235F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 142).addBox(1.5F, 7.04F, -2.975F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 170).addBox(1.75F, 7.44F, -2.975F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 166).addBox(-2.75F, 7.44F, -2.975F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 98).addBox(-3.0F, 7.34F, -1.575F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(52, 129).addBox(-2.5F, 9.84F, 0.425F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(70, 135).addBox(-2.5F, 3.69F, -3.35F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 166).addBox(-2.5F, 3.69F, -2.35F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 166).addBox(1.5F, 3.69F, -2.35F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(84, 104).addBox(-2.5F, 4.34F, -1.325F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 135).addBox(-2.5F, -0.66F, -0.575F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(82, 135).addBox(-2.5F, -0.91F, -2.575F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 168).addBox(-2.75F, 1.94F, 0.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 170).addBox(-2.25F, 1.69F, -2.825F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition Knee_thing_r1 = RightLeg.addOrReplaceChild("Knee_thing_r1", CubeListBuilder.create().texOffs(46, 162).addBox(0.45F, 2.19F, -6.35F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 162).addBox(-4.35F, 2.19F, -6.35F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 149).addBox(0.3F, 1.69F, -6.85F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(50, 149).addBox(-4.2F, 1.69F, -6.85F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0F, 0.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition FootSegment_r1 = RightLeg.addOrReplaceChild("FootSegment_r1", CubeListBuilder.create().texOffs(56, 138).addBox(-4.0F, 9.5F, 2.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.7F, 0.85F, -0.6545F, 0.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(16, 121).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(12, 172).addBox(1.5F, 9.34F, -3.235F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 149).addBox(1.5F, 10.34F, -3.985F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(10, 149).addBox(1.5F, 7.04F, -2.975F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 149).addBox(-2.5F, 7.04F, -3.4F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 142).addBox(-2.5F, 10.34F, -3.985F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 172).addBox(-2.5F, 9.34F, -3.235F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 142).addBox(-2.5F, 7.04F, -2.975F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 172).addBox(-2.75F, 7.44F, -2.975F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 172).addBox(1.75F, 7.44F, -2.975F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 93).addBox(-3.0F, 7.34F, -1.575F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 129).addBox(-2.5F, 9.84F, 0.425F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 142).addBox(-2.5F, 3.69F, -3.35F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 166).addBox(1.5F, 3.69F, -2.35F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 166).addBox(-2.5F, 3.69F, -2.35F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 104).addBox(-2.5F, 4.34F, -1.325F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 135).addBox(0.5F, -0.66F, -0.575F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(92, 135).addBox(-0.5F, -0.91F, -2.575F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 168).addBox(1.75F, 1.94F, 0.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 166).addBox(1.25F, 1.69F, -2.825F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition Knee_thing_r2 = LeftLeg.addOrReplaceChild("Knee_thing_r2", CubeListBuilder.create().texOffs(52, 162).addBox(-1.45F, 2.19F, -6.35F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 162).addBox(3.35F, 2.19F, -6.35F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(66, 149).addBox(-1.3F, 1.69F, -6.85F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(74, 149).addBox(3.2F, 1.69F, -6.85F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition FootSegment_r2 = LeftLeg.addOrReplaceChild("FootSegment_r2", CubeListBuilder.create().texOffs(12, 142).addBox(-1.0F, 9.5F, 2.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.7F, 0.85F, -0.6545F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 176, 176);
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
