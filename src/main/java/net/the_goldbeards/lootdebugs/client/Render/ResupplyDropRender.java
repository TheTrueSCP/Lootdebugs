package net.the_goldbeards.lootdebugs.client.Render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Tools.ResupplyPod.ResupplyDropEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.ResupplyDropModel;


public class ResupplyDropRender extends EntityRenderer<ResupplyDropEntity>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/supply_pod.png");

    private ResupplyDropModel<ResupplyDropEntity> model;

    public ResupplyDropRender(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new ResupplyDropModel<>(context.bakeLayer(ResupplyDropModel.LAYER_LOCATION));
    }

    @Override
    public void render(ResupplyDropEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)0.5F, 0.0D);

        this.model.prepareMobModel(pEntity, 0, 0, pPartialTicks);
        this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(getTextureLocation(pEntity)));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        pMatrixStack.popPose();

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ResupplyDropEntity pEntity) {
        return TEXTURE;
    }


}
