package net.the_goldbeards.lootdebugs.client.Render.Projectiles.Turret;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.NameTagItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Tools.Flare.FlareEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Turret.BulletEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Projectiles.FlareModel;
import net.the_goldbeards.lootdebugs.client.model.TurretModel;

@OnlyIn(Dist.CLIENT)
public class TurretRender extends EntityRenderer<TurretEntity>
{
    protected final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/entity/turret/turret.png");

    private TurretModel<TurretEntity> model;

    public TurretRender(EntityRendererProvider.Context context)
    {
        super(context);

        this.model = new TurretModel<>(context.bakeLayer(TurretModel.LAYER_LOCATION));

    }

    @Override
    public void render(TurretEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {


        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)1.5F, 0.0D);

        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 10.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(180f));
        this.model.prepareMobModel(pEntity, 0, 0, pPartialTicks);
        this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(getTextureLocation(pEntity)));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }



    @Override
    public ResourceLocation getTextureLocation(TurretEntity pEntity) {
        return TEXTURE;
    }


}
