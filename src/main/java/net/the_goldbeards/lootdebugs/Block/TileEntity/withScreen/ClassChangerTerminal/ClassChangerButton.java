package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.Button.BasicButton;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;

public class ClassChangerButton extends BasicButton
{
    private final IClassData.Classes buttonClass;

    public ClassChangerButton(IClassData.Classes buttonClass, int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, 256, 256, pOnPress);
        this.buttonClass = buttonClass;
    }

    public IClassData.Classes getButtonClass()
    {
        return buttonClass;
    }

}
