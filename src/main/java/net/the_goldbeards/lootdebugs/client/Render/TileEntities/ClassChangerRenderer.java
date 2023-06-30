package net.the_goldbeards.lootdebugs.client.Render.TileEntities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal.ClassChangerTile;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import static net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal.ClassChangerBlock.LAST_INTERACTED_CLASS;

public class ClassChangerRenderer implements BlockEntityRenderer<ClassChangerTile>
{
public ClassChangerRenderer(BlockEntityRendererProvider.Context context)
{

}
    @Override
    public void render(ClassChangerTile pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        if(ModUtils.DwarfClasses.getPlayerClass(Minecraft.getInstance().player) != IClassData.Classes.LeafLover && ModUtils.DwarfClasses.getPlayerClass(Minecraft.getInstance().player) != null && !pBlockEntity.isRemoved())
        {
            switch(ModUtils.DwarfClasses.getPlayerClass(Minecraft.getInstance().player))
            {
                case Driller, TheTrueSCP -> pBlockEntity.getLevel().setBlock(pBlockEntity.getBlockPos(), pBlockEntity.getBlockState().setValue(LAST_INTERACTED_CLASS, 1), 3);
                case Engineer -> pBlockEntity.getLevel().setBlock(pBlockEntity.getBlockPos(), pBlockEntity.getBlockState().setValue(LAST_INTERACTED_CLASS, 2), 3);
                case Gunner -> pBlockEntity.getLevel().setBlock(pBlockEntity.getBlockPos(), pBlockEntity.getBlockState().setValue(LAST_INTERACTED_CLASS, 3), 3);
                case Scout, MonsieurHannes -> pBlockEntity.getLevel().setBlock(pBlockEntity.getBlockPos(), pBlockEntity.getBlockState().setValue(LAST_INTERACTED_CLASS, 4), 3);
                default -> pBlockEntity.getLevel().setBlock(pBlockEntity.getBlockPos(), pBlockEntity.getBlockState().setValue(LAST_INTERACTED_CLASS, 0), 3);


            }
        }
    }
}
