package net.the_goldbeards.lootdebugs.client.Render.Projectiles.Zipline;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Tools.GrapplingHookHookEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Projectiles.GrapplingHookHookModel;

@OnlyIn(Dist.CLIENT)
public class ZiplineStringRender extends EntityRenderer<ZiplineStringAnchor>
{
    protected final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/entity/flare/flare_yellow.png");
    private final GrapplingHookHookModel<GrapplingHookHookEntity> model;

    public ZiplineStringRender(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new GrapplingHookHookModel<>(context.bakeLayer(GrapplingHookHookModel.LAYER_LOCATION));
    }

    @Override
    public void render(ZiplineStringAnchor pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)-0.0F, 0.0D);
        //pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 10.0F));
        //  pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        pMatrixStack.popPose();
        if (pEntity.getLinkedPos() != null && pEntity.getShouldStringRender())
        {
            renderString(pEntity.blockPosition(), pMatrixStack, pBuffer, pEntity.getLinkedPos());
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    private <E extends Entity> void renderString(BlockPos thisPos, PoseStack pMatrixStack, MultiBufferSource pBuffer, BlockPos linkedAnchor)
    {
        pMatrixStack.pushPose();

        float f4 = (float)(linkedAnchor.getX() - thisPos.getX());
        float f5 = (float)(linkedAnchor.getY() - thisPos.getY());
        float f6 = (float)(linkedAnchor.getZ() - thisPos.getZ());

        VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.lineStrip());
        PoseStack.Pose posestack$pose1 = pMatrixStack.last();

        for(int k = 0; k <= 16; ++k) {
            stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1, fraction(k, 16), fraction(k + 1, 16));
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
    public ResourceLocation getTextureLocation(ZiplineStringAnchor pEntity) {


        return TEXTURE;
    }


}
