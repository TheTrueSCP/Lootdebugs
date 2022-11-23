package net.the_goldbeards.lootdebugs.client.Render.Projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Tools.FlareEntity;
import net.the_goldbeards.lootdebugs.client.model.Projectiles.FlareModel;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

@OnlyIn(Dist.CLIENT)
public class FlareRender extends EntityRenderer<FlareEntity>
{
    protected final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/entity/flare/flare.png");
    private final FlareModel<FlareEntity> model;

    public FlareRender(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new FlareModel<>(context.bakeLayer(FlareModel.LAYER_LOCATION));

    }

    @Override
    public void render(FlareEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)-0.80F, 0.0D);
        //pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 10.0F));
      //  pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }

    @Override
    public ResourceLocation getTextureLocation(FlareEntity pEntity) {


        return TEXTURE;
    }


}
