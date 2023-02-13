package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.Button.LogicButton;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.TileEntity.Pub.PubBrewBeerPacket;

public class PubScreen extends AbstractContainerScreen<PubContainer> {

    PubTile pubTile;

    private static final ResourceLocation GUI = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/pub_gui.png");
    private static final ResourceLocation CONTINUE_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/recipe_button.png");

    public PubScreen(PubContainer pMenu, Inventory pPlayerInventory, Component pTitle) {

        super(pMenu, pPlayerInventory, pTitle);
        pubTile = (PubTile)pMenu.blockEntity;
    }

    @Override
    protected void init() {

        super.init();
        this.addRenderableWidget(new LogicButton(this.leftPos + 140, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.sendToServer(new PubBrewBeerPacket(pubTile.getBlockPos()));


        }));
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;



        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if(menu.isCrafting())
        {
            blit(pPoseStack, x + 48, y + 13, 176, 2, 79, menu.getScaledProgress());


        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);



    }
}