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

public class ScoutMK1ArmorModel<T extends Entity> extends EntityModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(LootDebugsMain.MOD_ID, "scout_mk1_armor"), "main");
    public final ModelPart Head;
    public final ModelPart Body;
    public final ModelPart RightArm;
    public final ModelPart LeftArm;
    public final ModelPart RightLeg;
    public final ModelPart LeftLeg;
    public final ModelPart NO;

    public ScoutMK1ArmorModel(ModelPart root) {
        this.Head = root.getChild("Head");
        this.Body = root.getChild("Body");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
        this.NO = root.getChild("NO");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(28, 97).addBox(-6.45F, -6.5F, -1.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(62, 121).addBox(-5.65F, -5.95F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 110).addBox(-5.45F, -5.75F, -5.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 104).addBox(-4.45F, -5.75F, -5.75F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 79).addBox(-5.75F, -7.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(12, 79).addBox(4.0F, -7.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 97).addBox(5.45F, -6.5F, -1.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 88).addBox(-4.25F, -9.0F, -2.75F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 88).addBox(4.25F, -9.0F, -2.75F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-4.75F, -9.25F, -1.25F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(-3.5F, 4.0F, -6.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(74, 0).addBox(-4.5F, 2.0F, -5.5F, 9.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 72).addBox(-2.5F, 3.0F, -6.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 72).addBox(-2.5F, 6.5F, -6.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 76).addBox(-3.5F, 8.5F, -4.75F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 59).addBox(-3.5F, -0.5F, -5.0F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 104).addBox(-7.0F, -0.5F, -4.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 104).addBox(-6.0F, -1.75F, -5.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 79).addBox(-4.75F, -2.25F, -5.25F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(48, 79).addBox(-3.5F, -2.75F, -5.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(38, 88).addBox(-1.5F, -2.5F, -5.25F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 81).addBox(0.5F, -2.75F, -5.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(68, 72).addBox(1.75F, -2.25F, -5.25F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 104).addBox(4.0F, -1.75F, -5.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(62, 97).addBox(5.0F, -0.5F, -4.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyebrows_r1 = Head.addOrReplaceChild("eyebrows_r1", CubeListBuilder.create().texOffs(62, 124).addBox(0.25F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 121).addBox(-1.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.75F, -4.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition eyebrows_r2 = Head.addOrReplaceChild("eyebrows_r2", CubeListBuilder.create().texOffs(24, 121).addBox(-1.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 124).addBox(-1.25F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -5.75F, -4.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition Headsetleft_r1 = Head.addOrReplaceChild("Headsetleft_r1", CubeListBuilder.create().texOffs(50, 116).addBox(-0.5F, -4.8F, 0.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(58, 121).addBox(-0.75F, -0.8F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 121).addBox(-11.15F, -0.8F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 116).addBox(-10.9F, -4.8F, 0.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2F, -6.75F, 1.25F, -1.0908F, 0.0F, 0.0F));

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(24, 0).addBox(-4.4068F, 7.8149F, 2.5032F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(21, 102).addBox(-2.9715F, 8.5855F, 6.5268F, 6.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(24, 101).addBox(3.0285F, 9.0649F, 6.5268F, 0.0F, 3.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(26, 101).addBox(-2.9834F, 9.0649F, 6.5268F, 0.0F, 3.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(40, 38).addBox(-4.9117F, 8.7198F, 3.4984F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 67).addBox(-4.8843F, 11.3375F, 3.7935F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 67).addBox(-4.8843F, 10.3375F, 3.7935F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 101).addBox(3.0285F, 12.0875F, 5.0337F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(21, 101).addBox(-2.9834F, 12.0875F, 5.0337F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(1.1657F, 8.5845F, 6.6758F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.35F))
                .texOffs(2, 126).addBox(-0.9206F, 8.5845F, 6.6758F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.35F))
                .texOffs(33, 79).addBox(-0.5392F, 9.5973F, 6.1062F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 79).addBox(3.4608F, 9.5973F, 5.1062F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 79).addBox(3.4608F, 9.5973F, 4.1062F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 118).addBox(4.5F, 9.2F, 3.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.1F, -0.1F, -2.1F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(40, 32).addBox(-4.0F, 8.25F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(58, 116).addBox(-1.0F, 8.75F, -2.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tankthing_r1 = Body.addOrReplaceChild("tankthing_r1", CubeListBuilder.create().texOffs(51, 105).addBox(-2.634F, -19.0651F, -4.6909F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
                .texOffs(43, 105).addBox(1.9412F, -19.0651F, -4.6909F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
                .texOffs(36, 72).addBox(-3.4052F, -19.0419F, -4.6641F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 127).addBox(-0.685F, -19.2292F, -1.5115F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(0, 127).addBox(-1.185F, -21.7281F, -1.5115F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(-1.165F, -21.2282F, -1.5115F, 0.0F, 2.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(1.3186F, -21.7282F, -1.5115F, 0.0F, 2.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(0, 121).addBox(1.3219F, -21.0596F, -2.2615F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 121).addBox(-3.0997F, -21.0596F, -2.2615F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 124).addBox(1.9719F, -24.0862F, -2.4151F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.35F))
                .texOffs(42, 124).addBox(-2.6533F, -24.0862F, -2.4151F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.35F))
                .texOffs(20, 88).addBox(-1.366F, -23.7803F, -6.8906F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 72).addBox(-1.366F, -20.3063F, -5.7677F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 110).addBox(-1.634F, -24.2741F, -2.6765F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 59).addBox(-2.634F, -27.5563F, -5.6445F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(32, 59).addBox(1.9412F, -27.5563F, -5.6445F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(12, 121).addBox(4.2474F, -22.0285F, -4.372F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(37, 98).addBox(4.2167F, -24.3704F, -4.3412F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.05F))
                .texOffs(34, 104).addBox(4.086F, -24.2472F, -5.287F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(0, 97).addBox(3.8899F, -23.6328F, -5.2909F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(46, 124).addBox(-5.1095F, -22.2475F, -4.1105F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 116).addBox(-4.8559F, -21.5182F, -4.618F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 112).addBox(-4.8559F, -23.5743F, -4.6141F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(44, 116).addBox(-4.8559F, -21.2828F, -4.6141F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(0, 42).addBox(-3.3748F, -24.4251F, -5.2909F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.25F))
                .texOffs(50, 1).addBox(-3.902F, -27.6395F, -5.6177F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(-3.848F, -24.4831F, -5.6177F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.25F, 7.05F, -0.0873F, 0.0F, 0.0F));

        PartDefinition Backpack_r1 = Body.addOrReplaceChild("Backpack_r1", CubeListBuilder.create().texOffs(2, 126).addBox(3.4755F, -24.0437F, 5.4117F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 24.25F, 0.05F, -0.0873F, 0.0F, 0.0F));

        PartDefinition Leftsidepocket_r1 = Body.addOrReplaceChild("Leftsidepocket_r1", CubeListBuilder.create().texOffs(44, 97).addBox(-2.0F, -14.65F, -5.95F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(8, 114).addBox(-2.15F, -16.15F, -6.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(28, 110).addBox(-1.75F, -16.55F, -6.55F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 124).addBox(-1.85F, -14.3F, -6.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 97).addBox(-2.4F, -12.8F, -6.35F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 124).addBox(0.75F, -12.85F, 4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 127).addBox(-1.4F, -13.35F, 5.75F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(0, 127).addBox(-1.4F, -15.95F, 5.75F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(5, 126).addBox(-1.15F, -15.45F, 5.65F, 0.0F, 1.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(54, 97).addBox(-2.5F, -16.5F, 4.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 110).addBox(-2.5F, -14.65F, 3.95F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(6, 116).addBox(-2.5F, -16.35F, 3.95F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 27.75F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition Leftsidepocket_r2 = Body.addOrReplaceChild("Leftsidepocket_r2", CubeListBuilder.create().texOffs(34, 110).addBox(-1.75F, -16.55F, 4.05F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 124).addBox(-1.85F, -14.3F, 5.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition rightsidepocket_r1 = Body.addOrReplaceChild("rightsidepocket_r1", CubeListBuilder.create().texOffs(50, 121).addBox(-5.25F, -10.1F, 4.4F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 121).addBox(-5.6F, -12.35F, 4.05F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 7.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(42, 50).addBox(-3.5F, 4.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F))
                .texOffs(78, 50).addBox(-3.0F, 5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.4F))
                .texOffs(34, 124).addBox(0.716F, -2.13F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 124).addBox(-0.534F, 0.12F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 16).addBox(-3.1F, -2.1F, -2.1F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(22, 124).addBox(-3.034F, -0.38F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 116).addBox(-3.25F, 2.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition Rightshoulderplate_r1 = RightArm.addOrReplaceChild("Rightshoulderplate_r1", CubeListBuilder.create().texOffs(26, 124).addBox(-16.284F, -18.88F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 22.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Rightshoulderplate_r2 = RightArm.addOrReplaceChild("Rightshoulderplate_r2", CubeListBuilder.create().texOffs(20, 32).addBox(15.75F, -16.75F, -2.75F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 22.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

        PartDefinition Rightshoulderplate_r3 = RightArm.addOrReplaceChild("Rightshoulderplate_r3", CubeListBuilder.create().texOffs(68, 42).addBox(-5.284F, -26.13F, -2.75F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 22.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(26, 116).addBox(2.5F, 2.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 124).addBox(-1.716F, -2.13F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 124).addBox(-0.466F, 0.12F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 124).addBox(2.034F, -0.38F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 50).addBox(-1.5F, 4.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F))
                .texOffs(62, 50).addBox(-1.0F, 5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.4F))
                .texOffs(32, 16).addBox(-1.1F, -2.1F, -2.1F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition Lamp_r1 = LeftArm.addOrReplaceChild("Lamp_r1", CubeListBuilder.create().texOffs(20, 116).addBox(-1.35F, -1.35F, -1.075F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(2, 125).addBox(-1.1F, 1.15F, -1.275F, 1.0F, -1.0F, -1.0F, new CubeDeformation(0.25F))
                .texOffs(1, 123).addBox(1.6F, -1.05F, -1.775F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(1, 123).addBox(-1.6F, -1.05F, -1.775F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(0, 124).addBox(-1.15F, -1.5F, -1.775F, 2.0F, -1.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(2, 125).addBox(-0.65F, -1.1F, -1.275F, 1.0F, -1.0F, -1.0F, new CubeDeformation(0.25F))
                .texOffs(36, 97).addBox(-1.366F, -1.355F, 0.125F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -4.775F, -0.725F, 0.0F, 0.0F, 0.4363F));

        PartDefinition LampRim_r1 = LeftArm.addOrReplaceChild("LampRim_r1", CubeListBuilder.create().texOffs(2, 126).addBox(-1.1F, 1.15F, -1.275F, 1.0F, -1.0F, -1.0F, new CubeDeformation(0.25F))
                .texOffs(2, 125).addBox(-0.65F, -1.1F, -1.275F, 1.0F, -1.0F, -1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(3.0F, -4.775F, -0.725F, 0.0F, 0.0F, -1.1345F));

        PartDefinition Leftshoulderplate_r1 = LeftArm.addOrReplaceChild("Leftshoulderplate_r1", CubeListBuilder.create().texOffs(10, 124).addBox(15.284F, -18.88F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 22.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition Leftshoulderplate_r2 = LeftArm.addOrReplaceChild("Leftshoulderplate_r2", CubeListBuilder.create().texOffs(48, 42).addBox(0.284F, -26.13F, -2.75F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition Leftshoulderplate_r3 = LeftArm.addOrReplaceChild("Leftshoulderplate_r3", CubeListBuilder.create().texOffs(0, 32).addBox(-20.75F, -16.75F, -2.75F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 22.0F, 0.0F, 0.0F, 0.0F, 1.1781F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(60, 88).addBox(-2.25F, 2.0F, -2.25F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(16, 16).addBox(-2.1F, 0.0F, -2.1F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(40, 110).addBox(-3.5F, 5.0F, -0.25F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 116).addBox(-3.75F, 6.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 118).addBox(-3.75F, 8.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 107).addBox(-2.15F, 9.75F, -2.15F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.9F, 0.0F, -2.1F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(50, 88).addBox(-1.75F, 2.0F, -2.25F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(8, 110).addBox(2.0F, 8.0F, -1.8F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 121).addBox(2.0F, 7.25F, -1.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 121).addBox(2.0F, 7.25F, 0.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 104).addBox(-1.85F, 9.75F, -2.15F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition NO = partdefinition.addOrReplaceChild("NO", CubeListBuilder.create().texOffs(24, 0).addBox(-4.8432F, -15.0057F, -5.7768F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(21, 102).addBox(-3.0285F, -14.1596F, -1.5032F, 6.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(24, 101).addBox(2.7834F, -13.4145F, -1.5032F, 0.0F, 2.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(26, 101).addBox(-3.0285F, -13.4145F, -1.5032F, 0.0F, 2.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(40, 38).addBox(-5.5883F, -14.2606F, -5.0316F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 67).addBox(-5.2157F, -11.2802F, -4.2865F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 101).addBox(2.7834F, -10.6576F, -2.5463F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(21, 101).addBox(-3.0285F, -10.6576F, -2.5463F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(0.9206F, -14.3086F, -1.3542F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(-1.1657F, -14.3086F, -1.3542F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(32, 79).addBox(-1.8628F, -13.1429F, -2.4238F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 116).addBox(4.4706F, -13.888F, -4.6591F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 7.98F));

        PartDefinition tankthing_r2 = NO.addOrReplaceChild("tankthing_r2", CubeListBuilder.create().texOffs(50, 104).addBox(-2.9412F, -18.0563F, -5.2909F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 104).addBox(1.634F, -18.0563F, -5.2909F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 72).addBox(-3.5948F, -18.0563F, -4.9641F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 127).addBox(-0.665F, -18.9828F, -1.5115F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(0, 127).addBox(-0.665F, -21.8586F, -1.5115F, 1.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(-1.3186F, -19.3096F, -1.5115F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(-1.3186F, -21.8586F, -1.5115F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(1.165F, -19.3096F, -1.5115F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(2, 126).addBox(1.165F, -21.8586F, -1.5115F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F))
                .texOffs(0, 121).addBox(0.8497F, -21.1282F, -2.4151F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 121).addBox(-3.0719F, -21.1282F, -2.4151F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 124).addBox(1.5033F, -24.4048F, -2.4151F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 124).addBox(-3.0719F, -24.4048F, -2.4151F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 88).addBox(-1.634F, -23.6947F, -7.1406F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 72).addBox(-1.634F, -19.3635F, -5.6177F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 110).addBox(-1.634F, -24.2741F, -2.6765F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 59).addBox(-2.9412F, -27.9807F, -5.9445F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 59).addBox(1.634F, -27.9807F, -5.9445F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(12, 121).addBox(4.7059F, -21.7164F, -4.572F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 121).addBox(4.7059F, -24.1348F, -4.4412F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 104).addBox(4.5752F, -24.004F, -5.487F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 97).addBox(4.7059F, -23.808F, -5.2909F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(46, 124).addBox(-6.6667F, -22.9583F, -4.3105F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 116).addBox(-6.1438F, -21.5754F, -4.768F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 112).addBox(-6.3399F, -23.9387F, -4.9641F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 116).addBox(-6.3399F, -21.1936F, -4.9641F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-4.5752F, -25.0395F, -5.2909F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 42).addBox(-4.902F, -27.6539F, -5.6177F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(-4.902F, -24.7127F, -5.6177F, 9.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.98F, -0.0873F, 0.0F, 0.0F));

        PartDefinition Backpack_r2 = NO.addOrReplaceChild("Backpack_r2", CubeListBuilder.create().texOffs(2, 126).addBox(3.3219F, -24.1973F, 5.1617F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.98F, -0.0873F, 0.0F, 0.0F));

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
        NO.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}