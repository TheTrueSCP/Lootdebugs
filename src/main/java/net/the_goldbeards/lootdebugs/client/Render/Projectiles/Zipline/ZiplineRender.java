package net.the_goldbeards.lootdebugs.client.Render.Projectiles.Zipline;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplinePoleTile;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Projectiles.ZiplineModel;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class ZiplineRender extends EntityRenderer<ZiplineEntity>
{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/entity/projectiles/zipline_hook.png");

    private final ZiplineModel<ZiplineEntity> model;

    public ZiplineRender(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new ZiplineModel<>(context.bakeLayer(ZiplineModel.LAYER_LOCATION));
    }

    @Override
    public boolean shouldRender(ZiplineEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(ZiplineEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));


        pMatrixStack.mulPose(Vector3f.YN.rotationDegrees(90.0F));
        pMatrixStack.translate(0.0D, -0.7D, 0.0D);
        this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack.Pose posestack$pose = pMatrixStack.last();

        for(int j = 0; j < 4; ++j) {
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        }

        pMatrixStack.popPose();

        //String

        if(pEntity.getZiplineMountBase() != null)
        {
            if(pEntity.level.getBlockEntity(pEntity.getZiplineMountBase()) instanceof ZiplinePoleTile) {
                Vec3 vec3 = new Vec3(0.5, 1.7, 0.5);

                double d4 = pEntity.getZiplineMountBase().getX() + vec3.x;
                double d5 = pEntity.getZiplineMountBase().getY() + vec3.y;
                double d6 = pEntity.getZiplineMountBase().getZ() + vec3.z;

                double d9 = Mth.lerp((double) pPartialTicks, pEntity.xo, pEntity.getX());
                double d10 = Mth.lerp((double) pPartialTicks, pEntity.yo, pEntity.getY());
                double d8 = Mth.lerp((double) pPartialTicks, pEntity.zo, pEntity.getZ());

                float f4 = (float) (d4 - d9);
                float f5 = (float) (d5 - d10);
                float f6 = (float) (d6 - d8);

                VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.lineStrip());
                PoseStack.Pose posestack$pose1 = pMatrixStack.last();

                for (int k = 0; k <= 1; ++k) {
                    stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1, fraction(k, 1), fraction(k + 1, 1));
                }
            }
        }
        pMatrixStack.popPose();

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }

    private void howToFuckMinecraftsRenderEngine(PoseStack poseStack, Vec3 pos1, Vec3 pos2)
    {
        var tesselator = Tesselator.getInstance();
        var buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(pos1.x(), pos1.y(), pos1.z()).color(1f, 1f, 1f, 1f).endVertex();
        buffer.vertex(pos2.x(), pos2.y(), pos1.z()).color(1f, 1f, 1f, 1f).endVertex();
        buffer.end();

        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        poseStack.pushPose();
    //    poseStack.translate(-view.x, -view.y, -view.z);
        var shader = GameRenderer.getPositionColorShader();
      //  vertexBuffer.drawWithShader(poseStack.last().pose(), event.getProjectionMatrix().copy(), shader);
        poseStack.popPose();

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.enableTexture();
    }

    private static float fraction(int pNumerator, int pDenominator) {
        return (float)pNumerator / (float)pDenominator;
    }

    private static void vertex(VertexConsumer p_114712_, Matrix4f p_114713_, Matrix3f p_114714_, int p_114715_, float p_114716_, int p_114717_, int p_114718_, int p_114719_) {
        p_114712_.vertex(p_114713_, p_114716_ - 0.5F, (float)p_114717_ - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float)p_114718_, (float)p_114719_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114715_).normal(p_114714_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void stringVertex(float p_174119_, float p_174120_, float p_174121_, VertexConsumer p_174122_, PoseStack.Pose p_174123_, float p_174124_, float p_174125_) {
        float f = p_174119_ * p_174124_;
        float f1 = p_174120_ * (p_174124_ * p_174124_ + p_174124_) * 0.5F + 0.25F;
        float f2 = p_174121_ * p_174124_;
        float f3 = p_174119_ * p_174125_ - f;
        float f4 = p_174120_ * (p_174125_ * p_174125_ + p_174125_) * 0.5F + 0.25F - f1;
        float f5 = p_174121_ * p_174125_ - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        p_174122_.vertex(p_174123_.pose(), f, f1, f2).color(0, 0, 0, 255).normal(p_174123_.normal(), f3, f4, f5).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(ZiplineEntity pEntity) {


        return TEXTURE;
    }


}
