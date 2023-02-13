package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.Button.LogicButton;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefineryConverting;
import net.the_goldbeards.lootdebugs.Network.TileEntity.FuelRefinery.FuelRefineryRefuelCanister;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuelRefineryScreen extends AbstractContainerScreen<FuelRefineryContainer>
{

    FuelRefineryTile fuelRefineryTile;

    private static final NumberFormat nf = NumberFormat.getIntegerInstance();
    private static final ResourceLocation GUI = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/fuel_refinery_gui.png");
    private static final ResourceLocation START_REFINING_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/buttons/gui_button_steel.png");

    public FuelRefineryScreen(FuelRefineryContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        fuelRefineryTile = pMenu.fuelRefineryTile;

    }

    @Override
    protected void init() {

        super.init();

        this.addRenderableWidget(new LogicButton(this.leftPos + 88, this.height / 2 - 26, 20, 18, 0, 0, 19, START_REFINING_BUTTON,(p_98484_) -> {

            PacketHandler.sendToServer(new FuelRefineryConverting(fuelRefineryTile.getBlockPos()));

        }));


        this.addRenderableWidget(new LogicButton(this.leftPos + 88, this.height / 2 - 62, 20, 18, 0, 0, 19, START_REFINING_BUTTON,(p_98484_) -> {

            PacketHandler.sendToServer(new FuelRefineryRefuelCanister(fuelRefineryTile.getBlockPos()));

        }));
    }


    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY)
    {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;



        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if(menu.isCompressing() || false)
        {
            //blit(pPoseStack, x + 48, y + 13, 176, 2, menu.getScaledProgress() + 1, 63);

            int k = menu.getScaledProgress();
           //this.blit(pPoseStack, x + 127 - k, y + 13, 255 - k, 2 , k + 1, 63);//Right to left


        }
        int k = menu.getScaledFluidLevel();


        int drawXPos = 134;
        int drawYPos = 12;

        int cutoutYPos = 0;
        int cutoutXPos = 176;
        int cutoutXSize = 12;
        int cutoutYSize = 40;

        this.blit(pPoseStack, x + drawXPos, y + drawYPos + cutoutYSize - k, cutoutXPos, cutoutYPos + cutoutYSize - k, cutoutXSize, k);

    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);




    }


    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderFluidAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 134, 12))
        {
            renderTooltip(pPoseStack, getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL, this.fuelRefineryTile.fluid_tank.getCapacity()),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    public List<Component> getTooltip(FluidStack fluidStack, TooltipFlag tooltipFlag, int capacity) {
        List<Component> tooltip = new ArrayList<>();

        Fluid fluidType = fluidStack.getFluid();

            if (fluidType.isSame(Fluids.EMPTY)) {
                return tooltip;
            }

            Component displayName = fluidStack.getDisplayName();
            tooltip.add(displayName);

            long amount = fluidStack.getAmount();
            long milliBuckets = (amount * 1000) / 1000;
                MutableComponent amountString = new TranslatableComponent("tooltip.liquid.amount.with.capacity", nf.format(milliBuckets), nf.format(capacity));
                tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
        return tooltip;
    }


    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        return UsefullStuff.MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, 12, 40);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return UsefullStuff.MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}