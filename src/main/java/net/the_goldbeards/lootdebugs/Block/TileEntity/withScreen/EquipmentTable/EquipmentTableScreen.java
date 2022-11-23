package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class EquipmentTableScreen extends AbstractContainerScreen<EquipmentTableContainer>
{

    private static final ResourceLocation GUI = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/equipment_table_gui.png");
    public EquipmentTableScreen(EquipmentTableContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {

        super.init();
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