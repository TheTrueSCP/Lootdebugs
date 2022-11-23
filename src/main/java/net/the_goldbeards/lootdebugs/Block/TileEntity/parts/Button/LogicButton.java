package net.the_goldbeards.lootdebugs.Block.TileEntity.parts.Button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class LogicButton extends Button {
    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffTex;
    private final int textureWidth;
    private final int textureHeight;
    public boolean press;

    public LogicButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, ResourceLocation pResourceLocation, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pHeight, pResourceLocation, 256, 256, pOnPress);
    }

    public LogicButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, 256, 256, pOnPress);
    }

    public LogicButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, TextComponent.EMPTY);
    }

    public LogicButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, net.minecraft.client.gui.components.Button.OnPress pOnPress, Component pMessage) {
        this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, NO_TOOLTIP, pMessage);
    }

    public LogicButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, net.minecraft.client.gui.components.Button.OnPress pOnPress, net.minecraft.client.gui.components.Button.OnTooltip pOnTooltip, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
        this.textureWidth = pTextureWidth;
        this.textureHeight = pTextureHeight;
        this.xTexStart = pXTexStart;
        this.yTexStart = pYTexStart;
        this.yDiffTex = pYDiffTex;
        this.resourceLocation = pResourceLocation;
    }

    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        int i = this.yTexStart;


        RenderSystem.enableDepthTest();
        blit(pPoseStack, this.x, this.y, (float) this.xTexStart, (float) i, this.width, this.height, this.textureWidth, this.textureHeight);
        int $$4 = this.yTexStart;
        if(this.press)
        {
            $$4 += this.yDiffTex;
        }
        blit(pPoseStack, this.x, this.y, (float)this.xTexStart, (float)$$4, this.width, this.height, this.textureWidth, this.textureHeight);

    }


    @Override
    public void onPress() {
            this.press = true;

        super.onPress();
    }

    @Override
    public void onRelease(double pMouseX, double pMouseY) {

        this.press = false;
        super.onRelease(pMouseX, pMouseY);
    }
}
