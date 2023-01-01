package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.Button.LogicButton;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Server.PacketHandler;
import net.the_goldbeards.lootdebugs.Server.TileEntity.FuelPress.Converting;
import net.the_goldbeards.lootdebugs.Server.TileEntity.FuelPress.RefuelCanister;

public class FuelRefineryScreen extends AbstractContainerScreen<FuelRefineryContainer>
{

    FuelRefineryTile fuelPressTile;

    private static final ResourceLocation GUI = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/pub_gui.png");
    private static final ResourceLocation CONTINUE_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/recipe_button.png");

    public FuelRefineryScreen(FuelRefineryContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        fuelPressTile = (FuelRefineryTile)pMenu.blockEntity;

    }

    @Override
    protected void init() {

        super.init();
        this.addRenderableWidget(new LogicButton(this.leftPos + 140, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.sendToServer(new RefuelCanister(fuelPressTile.getBlockPos()));

        }));


        this.addRenderableWidget(new LogicButton(this.leftPos + 40, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.sendToServer(new Converting(fuelPressTile.getBlockPos()));

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

        if(menu.isCompressing())
        {
            blit(pPoseStack, x + 48, y + 13, 176, 2, menu.getScaledProgress() + 1, 63);

            int k = menu.getScaledProgress();
           //this.blit(pPoseStack, x + 127 - k, y + 13, 255 - k, 2 , k + 1, 63);//Right to left


        }


            int k = menu.getScaledFluidLevel();
            this.blit(pPoseStack, x + 48, y + 76 - k, 176, 65 - k, 79, k + 1);










    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);




    }
}