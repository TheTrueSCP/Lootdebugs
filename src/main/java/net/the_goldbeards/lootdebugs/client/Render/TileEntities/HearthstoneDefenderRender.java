package net.the_goldbeards.lootdebugs.client.Render.TileEntities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.HeartstoneDefender.HeartstoneDefenderTile;

public class HearthstoneDefenderRender implements BlockEntityRenderer<HeartstoneDefenderTile>
{
    public HearthstoneDefenderRender(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(HeartstoneDefenderTile pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        BlockPos hearthstoneDefenderPos = pBlockEntity.getBlockPos();

        if (pBlockEntity.getTarget() != null)
        {
            if (pBlockEntity.getTarget() instanceof LivingEntity livingEntity)
            {
                Vec3 offsetLiving = new Vec3(0, 0 , 0);
                Vec3 offsetOmmoran = new Vec3(0, 0, 0);

                //Pos 1
                double d4 = livingEntity.eyeBlockPosition().getX() + offsetLiving.x;
                double d5 = livingEntity.eyeBlockPosition().getY() + offsetLiving.y;
                double d6 = livingEntity.eyeBlockPosition().getZ() + offsetLiving.z;

                //Pos 2
                double d9 = hearthstoneDefenderPos.getX() + offsetOmmoran.x;
                double d10 = hearthstoneDefenderPos.getY() + offsetOmmoran.y;
                double d8 = hearthstoneDefenderPos.getZ() + offsetOmmoran.z;

                float f4 = (float) (d4 - d9);
                float f5 = (float) (d5 - d10);
                float f6 = (float) (d6 - d8);

                VertexConsumer vertexconsumer1 = pBufferSource.getBuffer(RenderType.lineStrip());
                PoseStack.Pose posestack$pose1 = pPoseStack.last();

                for (int k = 0; k <= 5; ++k) {
                    stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1, fraction(k, 1), fraction(k + 1, 1));
                }
            }
        }
    }

    private static float fraction(int pNumerator, int pDenominator) {
        return (float)pNumerator / (float)pDenominator;
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
        p_174122_.vertex(p_174123_.pose(), f, f1, f2).color(255, 0, 0, 255).normal(p_174123_.normal(), f3, f4, f5).endVertex();
    }
}
