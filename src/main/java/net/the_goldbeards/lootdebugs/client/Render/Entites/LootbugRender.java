package net.the_goldbeards.lootdebugs.client.Render.Entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugModel;


public class LootbugRender extends MobRenderer<LootbugEntity, LootbugModel<LootbugEntity>>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/lootbug.png");
    public static final ResourceLocation CRYSTAL_BEAM_LOCATION = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
    private static final RenderType BEAM = RenderType.entitySmoothCutout(CRYSTAL_BEAM_LOCATION);

    public LootbugRender(EntityRendererProvider.Context context) {
        super(context, new LootbugModel<>(context.bakeLayer(LootbugModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public void render(LootbugEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        Entity nearestCrystal = pEntity;
        if (nearestCrystal != null) {
            pMatrixStack.pushPose();
            float f6 = (float)(nearestCrystal.getX() + 5 - Mth.lerp((double)pPartialTicks, pEntity.xo, pEntity.getX()));
            float f8 = (float)(nearestCrystal.getY() + 5 - Mth.lerp((double)pPartialTicks, pEntity.yo, pEntity.getY()));
            float f9 = (float)(nearestCrystal.getZ() + 5 - Mth.lerp((double)pPartialTicks, pEntity.zo, pEntity.getZ()));
           // renderCrystalBeams(f6, f8 , f9, pPartialTicks, pEntity.tickCount, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
        //renderBeams(pEntity.blockPosition(), pEntity.blockPosition().east(12).west(5), BEAM, pPartialTicks, 5, pMatrixStack, pBuffer, pPackedLight);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(LootbugEntity p_114482_) {
        return TEXTURE;
    }

    public static void renderBeams(BlockPos blockpos1, BlockPos blockPos2, RenderType texture, float pPartialTicks, int time, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight)
    {
        //EnderDragonRenderer.renderCrystalBeams(-f6, -f7 + f, -f8, pPartialTicks, pEntity.time, pMatrixStack, pBuffer, pPackedLight);
        if (blockpos1 != null && blockPos2 != null)
        {
            //Calculate
            float posx = (float)((double)blockpos1.getX() - blockPos2.getX()) * -1;
            float posy = (float)((double)blockpos1.getY() - blockPos2.getY() * -1 + getY(time, pPartialTicks));
            float posz = (float)((double)blockpos1.getZ() - blockPos2.getZ() * -1);
            pMatrixStack.translate((double)posx, (double)posy, (double)posz);


            //Rendering
            float f = Mth.sqrt(posx * posx + posz * posz);
            float f1 = Mth.sqrt(posx * posx + posy * posy + posz * posz);
            pMatrixStack.pushPose();
            pMatrixStack.translate(0.0D, 2.0D, 0.0D);
            pMatrixStack.mulPose(Vector3f.YP.rotation((float)(-Math.atan2((double)posz, (double)f1)) - ((float)Math.PI / 2F)));
            pMatrixStack.mulPose(Vector3f.XP.rotation((float)(-Math.atan2((double)f, (double)posy)) - ((float)Math.PI / 2F)));
            VertexConsumer vertexconsumer = pBuffer.getBuffer(texture);
            float f2 = 0.0F - ((float)time + pPartialTicks) * 0.01F;
            float f3 = Mth.sqrt(posx * posx + posy * posy + posz * posz) / 32.0F - ((float) time + pPartialTicks) * 0.01F;
            int i = 8;
            float f4 = 0.0F;
            float f5 = 0.75F;
            float f6 = 0.0F;
            PoseStack.Pose posestack$pose = pMatrixStack.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();

            for(int j = 1; j <= 8; ++j) {
                float f7 = Mth.sin((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
                float f8 = Mth.cos((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
                float f9 = (float)j / 8.0F;
                vertexconsumer.vertex(matrix4f, f4 * 0.2F, f5 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f6, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
                vertexconsumer.vertex(matrix4f, f4, f5, f1).color(255, 255, 255, 255).uv(f6, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
                vertexconsumer.vertex(matrix4f, f7, f8, f1).color(255, 255, 255, 255).uv(f9, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
                vertexconsumer.vertex(matrix4f, f7 * 0.2F, f8 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f9, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
                f4 = f7;
                f5 = f8;
                f6 = f9;
            }

            pMatrixStack.popPose();
        }
    }

    public static float getY(int time, float p_114160_) {
        float f = (float)time + p_114160_;
        float f1 = Mth.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = (f1 * f1 + f1) * 0.4F;
        return f1 - 1.4F;
    }

    public static void renderCrystalBeams(float x, float y, float z, float partialTicks, int tickCount, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        float f = Mth.sqrt(x * x + z * z);
        float f1 = Mth.sqrt(x * x + y * y + z * z);
        matrixStack.pushPose();
        matrixStack.translate(0.0D, 2.0D, 0.0D);
        matrixStack.mulPose(Vector3f.YP.rotation((float)(-Math.atan2((double)z, (double)x)) - ((float)Math.PI / 2F)));
        matrixStack.mulPose(Vector3f.XP.rotation((float)(-Math.atan2((double)f, (double)y)) - ((float)Math.PI / 2F)));
        VertexConsumer vertexconsumer = buffer.getBuffer(BEAM);
        float f2 = 0.0F - ((float)tickCount + partialTicks) * 0.01F;
        float f3 = Mth.sqrt(x * x + y * y + z * z) / 32.0F - ((float)tickCount + partialTicks) * 0.01F;
        int i = 8;
        float f4 = 0.0F;
        float f5 = 0.75F;
        float f6 = 0.0F;
        PoseStack.Pose posestack$pose = matrixStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        for(int j = 1; j <= 8; ++j) {
            float f7 = Mth.sin((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
            float f8 = Mth.cos((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
            float f9 = (float)j / 8.0F;
           // vertexconsumer.vertex(matrix4f, f4 * 0.2F, f5 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f6, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();//vertexconsumer.vertex(matrix4f, f4, f5, f1).color(255, 255, 255, 255).uv(f6, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, f7, f8, f1).color(255, 255, 255, 255).uv(f9, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
           // vertexconsumer.vertex(matrix4f, f7 * 0.2F, f8 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f9, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            f4 = f7;
            f5 = f8;
            f6 = f9;
        }

        matrixStack.popPose();
    }
}
