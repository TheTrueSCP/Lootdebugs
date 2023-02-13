package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Block.TileEntity.parts.Button.BasicButton;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.Capabillity.ChangeClass.ChangeClassPacket;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;

public class ClassChangerScreen extends AbstractContainerScreen<ClassChangerContainer>
{

    private ClassChangerTile classChangeTile;
    private Player player;
    private Level level;
    private BlockPos pos;

    private static final ResourceLocation GUI = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer_gui.png");
    private static final ResourceLocation CONTINUE_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/recipe_button.png");

    public ClassChangerScreen(ClassChangerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
       classChangeTile = (ClassChangerTile) pMenu.blockEntity;
       player = pPlayerInventory.player;
       level = classChangeTile.getLevel();
       pos = classChangeTile.getBlockPos();

    }

    @Override
    protected void init() {

        super.init();

        this.addRenderableWidget(new BasicButton(this.leftPos + 40, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Driller));//Driller
        }));

        this.addRenderableWidget(new BasicButton(this.leftPos + 60, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Engineer));
        }));

        this.addRenderableWidget(new BasicButton(this.leftPos + 80, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Gunner));
        }));

        this.addRenderableWidget(new BasicButton(this.leftPos + 100, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Scout));
        }));



        if(player.getUUID().toString().equals("3398160a-6def-43d2-b280-6ce5c84b8839") ||player.getUUID().toString().equals("3398160a6def43d2b2806ce5c84b8839"))//TheTrueSCP
        {

            this.addRenderableWidget(new BasicButton(this.leftPos + 40, this.height / 2 - 60, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

                PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.TheTrueSCP));//TheTrueSCP

            }));
        }


        if(player.getUUID().toString().equals("91da03dc-5ba2-43cb-a3f3-8f0f4dfa2b3f") ||player.getUUID().toString().equals("91da03dc5ba243cba3f38f0f4dfa2b3f"))//MonsieurHannes
        {
            this.addRenderableWidget(new BasicButton(this.leftPos + 100, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

                PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.MonsieurHannes));

            }));
        }
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