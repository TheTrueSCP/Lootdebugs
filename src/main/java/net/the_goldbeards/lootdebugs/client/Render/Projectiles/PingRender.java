package net.the_goldbeards.lootdebugs.client.Render.Projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Tools.PingEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Projectiles.PingModel;

@OnlyIn(Dist.CLIENT)
public class PingRender extends EntityRenderer<PingEntity>
{
    protected final ResourceLocation TEXTURE_LEAF_LOVER = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/projectiles/pings/ping.png");
    protected final ResourceLocation TEXTURE_YELLOW = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/projectiles/pings/ping_yellow.png");
    protected final ResourceLocation TEXTURE_BLUE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/projectiles/pings/ping_blue.png");
    protected final ResourceLocation TEXTURE_RED = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/projectiles/pings/ping_red.png");
    protected final ResourceLocation TEXTURE_GREEN = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/projectiles/pings/ping_green.png");
    private final PingModel<PingEntity> model;

    public PingRender(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PingModel<>(context.bakeLayer(PingModel.LAYER_LOCATION));
    }

    @Override
    public void render(PingEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.inGround)
        {
            pMatrixStack.pushPose();
            //   pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
          //  pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));

            pMatrixStack.translate(0.0D, (double) -0.7D, 0.0D);


            this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(getTextureLocation(pEntity)));
            vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pEntity)));
            this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            pMatrixStack.popPose();
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }



    @Override
    public ResourceLocation getTextureLocation(PingEntity pEntity) {

        return switch (pEntity.getDwarfClass())
                {
            case Driller, TheTrueSCP -> TEXTURE_YELLOW;
            case Engineer -> TEXTURE_RED;
            case Scout, MonsieurHannes -> TEXTURE_BLUE;
            case Gunner -> TEXTURE_GREEN;
            default -> TEXTURE_LEAF_LOVER;
        };

    }




}
