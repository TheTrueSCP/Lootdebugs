package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
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
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class ClassChangerScreen extends AbstractContainerScreen<ClassChangerContainer>
{

    private ClassChangerTile classChangeTile;
    private Player player;
    private Level level;
    private BlockPos pos;

    private ClassChangerButton[] buttons = new ClassChangerButton[6];


    private static final ResourceLocation GUI = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer/class_changer_gui.png");
    private static final ResourceLocation GUNNER_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer/button_gunner.png");
    private static final ResourceLocation SCOUT_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer/button_scout.png");
    private static final ResourceLocation DRILLER_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer/button_driller.png");
    private static final ResourceLocation ENGINEER_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer/button_engineer.png");

    private static final ResourceLocation THE_TRUE_SCP_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer/button_thetruescp.png");
    private static final ResourceLocation MONSIEUR_HANNES_BUTTON = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/class_changer/button_monsieurhannes.png");

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

        this.buttons[0] = this.addRenderableWidget(new ClassChangerButton(IClassData.Classes.Driller,this.leftPos + 42, this.height / 2 - 76, 91, 16, 0, 0, 16, DRILLER_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Driller));//Driller
        }));

        this.buttons[1] = this.addRenderableWidget(new ClassChangerButton(IClassData.Classes.Engineer,this.leftPos + 37, this.height / 2 - 58, 101, 16, 0, 0, 16, ENGINEER_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Engineer));
        }));

        this.buttons[2] = this.addRenderableWidget(new ClassChangerButton(IClassData.Classes.Gunner,this.leftPos + 45, this.height / 2 - 40, 85, 16, 0, 0, 16, GUNNER_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Gunner));
        }));

        this.buttons[3] = this.addRenderableWidget(new ClassChangerButton(IClassData.Classes.Scout,this.leftPos + 49, this.height / 2 - 22, 79, 16, 0, 0, 16, SCOUT_BUTTON,(p_98484_) -> {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Scout));
        }));



        if(player.getUUID().toString().equals("3398160a-6def-43d2-b280-6ce5c84b8839") ||player.getUUID().toString().equals("3398160a6def43d2b2806ce5c84b8839"))//TheTrueSCP
        {

            this.buttons[4] = this.addRenderableWidget(new ClassChangerButton(IClassData.Classes.TheTrueSCP,this.leftPos + 141, this.height / 2 - 76, 28, 70, 0, 0, 70, THE_TRUE_SCP_BUTTON,(p_98484_) -> {

                PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.TheTrueSCP));//TheTrueSCP

            }));
        }


        if(player.getUUID().toString().equals("91da03dc-5ba2-43cb-a3f3-8f0f4dfa2b3f") ||player.getUUID().toString().equals("91da03dc5ba243cba3f38f0f4dfa2b3f"))//MonsieurHannes
        {
            this.buttons[5] = this.addRenderableWidget(new ClassChangerButton(IClassData.Classes.MonsieurHannes,this.leftPos + 7, this.height / 2 - 76, 28, 70, 0, 0, 70, MONSIEUR_HANNES_BUTTON,(p_98484_) -> {

                PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.MonsieurHannes));

            }));

        }

        if(player.isCreative())//Karl
        {
            this.addRenderableWidget(new BasicButton(this.leftPos + 140, this.height / 2 - 30, 20, 18, 0, 0, 19, CONTINUE_BUTTON,(p_98484_) -> {

                PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeClassPacket(IClassData.Classes.Karl));

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

        handleButtonGlowing(ModUtils.DwarfClasses.getPlayerClass(player));
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        return;
    }

    public void handleButtonGlowing(IClassData.Classes playerClass)
    {
        int matchingButton = -1;

        for (int i = 0; i < buttons.length; i++)
        {
            if(buttons[i] != null)
            {
                if (buttons[i].getButtonClass() == playerClass) {
                    matchingButton = i;
                }
            }
        }

        if(matchingButton > -1)
        {
            for (int i = 0; i < buttons.length; i++)
            {
                if(buttons[i] != null) {
                    if (i != matchingButton) {
                        buttons[i].press = false;
                    } else {
                        buttons[i].press = true;
                    }
                }
            }
        }
    }
}