package net.the_goldbeards.lootdebugs.client.Render.Projectiles.Zipline;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplineBlock;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

@OnlyIn(Dist.CLIENT)
public class ZiplineRender extends EntityRenderer<ZiplineEntity>
{
    protected final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/entity/flare/flare_yellow.png");

    public ZiplineRender(EntityRendererProvider.Context context)
    {
        super(context);

    }

    @Override
    public boolean shouldRender(ZiplineEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(ZiplineEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)-0.0F, 0.0D);
        //pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 10.0F));
      //  pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        pMatrixStack.popPose();

        if(pEntity.getZiplineMountBase() != null && pEntity.getIsLocked())
        {
            renderString(pEntity.blockPosition(), pMatrixStack, pBuffer, pEntity.getZiplineMountBase().above());
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }

    private <E extends Entity> void renderString(Entity pEntity, PoseStack pMatrixStack, float pPartialTicks , MultiBufferSource pBuffer, Entity linkEntity)
    {
        pMatrixStack.pushPose();

        double d4 = Mth.lerp((double)pPartialTicks, linkEntity.xo, linkEntity.getX());
        double d5 = Mth.lerp((double)pPartialTicks, linkEntity.yo, linkEntity.getY());
        double d6 = Mth.lerp((double)pPartialTicks, linkEntity.zo, linkEntity.getZ());

        double d9 = Mth.lerp((double)pPartialTicks, pEntity.xo, pEntity.getX());
        double d10 = Mth.lerp((double)pPartialTicks, pEntity.yo, pEntity.getY());
        double d8 = Mth.lerp((double)pPartialTicks, pEntity.zo, pEntity.getZ());

        float f4 = (float)(d4 - d9);
        float f5 = (float)(d5 - d10);
        float f6 = (float)(d6 - d8);

        VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.lineStrip());
        PoseStack.Pose posestack$pose1 = pMatrixStack.last();

        for(int k = 0; k <= 1; ++k)
        {
            stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1, fraction(k, 1), fraction(k + 1, 1));
        }

        pMatrixStack.popPose();
    }

    private <E extends Entity> void renderString(BlockPos thisPos, PoseStack pMatrixStack, MultiBufferSource pBuffer, BlockPos linkedAnchor)
    {
        pMatrixStack.pushPose();

        float f4 = (float)(linkedAnchor.getX() - thisPos.getX());
        float f5 = (float)(linkedAnchor.getY() - thisPos.getY());
        float f6 = (float)(linkedAnchor.getZ() - thisPos.getZ());

        VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.lineStrip());
        PoseStack.Pose posestack$pose1 = pMatrixStack.last();

        for(int k = 0; k <= 1; ++k)
        {
            stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1, fraction(k, 1), fraction(k + 1, 1));
        }

        pMatrixStack.popPose();
    }


    private static float fraction(int p_114691_, int p_114692_) {
        return (float)p_114691_ / (float)p_114692_;
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
