package net.the_goldbeards.lootdebugs.client.Render.Projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Tools.ShieldEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

@OnlyIn(Dist.CLIENT)
public class ShieldRender extends EntityRenderer<ShieldEntity>
{
    protected final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/weapons/satchel_charge/satchel_charge.png");
  //  private final SatchelChargeModel<SatchelChargeEntity> model;

    public ShieldRender(EntityRendererProvider.Context context) {
        super(context);
        //model = new SatchelChargeModel<>(context.bakeLayer(SatchelChargeModel.LAYER_LOCATION));
    }

    @Override
    public void render(ShieldEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)-0.935353535F, 0.0D);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
       // this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        //VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        //this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ShieldEntity pEntity) {
        return TEXTURE;
    }


}
