package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Jukebox;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.Button.BasicButton;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.TileEntity.Lloyd.LloydBrewBeerPacket;

public class JukeboxScreen extends AbstractContainerScreen<JukeboxContainer> {

    JukeboxTile jukeboxTile;

    private static final ResourceLocation GUI = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/lloyd_gui.png");
    private static final ResourceLocation CONTINUE_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/recipe_button.png");

    public JukeboxScreen(JukeboxContainer pMenu, Inventory pPlayerInventory, Component pTitle) {

        super(pMenu, pPlayerInventory, pTitle);
        jukeboxTile = (JukeboxTile)pMenu.blockEntity;
    }

    @Override
    protected void init() {

        super.init();
        this.addRenderableWidget(new BasicButton(this.leftPos + 140, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.sendToServer(new LloydBrewBeerPacket(jukeboxTile.getBlockPos()));


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
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);



    }
}