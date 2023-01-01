package net.the_goldbeards.lootdebugs.client.Render.Projectiles.Zipline;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
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
    public void render(ZiplineEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)-0.0F, 0.0D);
        //pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 10.0F));
      //  pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }

    @Override
    public ResourceLocation getTextureLocation(ZiplineEntity pEntity) {


        return TEXTURE;
    }


}
