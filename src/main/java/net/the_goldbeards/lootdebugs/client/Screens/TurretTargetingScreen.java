package net.the_goldbeards.lootdebugs.client.Screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.Entity.Turret.TurretRemovePacket;
import net.the_goldbeards.lootdebugs.Network.Entity.Turret.TurretTargetingSyncPacket;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity.TargetingModes.ENEMYS;
import static net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity.TargetingModes.NEUTRAL_AND_ENEMYS;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

@OnlyIn(Dist.CLIENT)
public class TurretTargetingScreen extends Screen
{
    static final ResourceLocation TURRET_TARGETING_SWITCHER_TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID, "textures/gui/turret_targeting_switcher.png");


    private static final int ALL_SLOTS_WIDTH = 88;

    private Optional<TargetingModeIcon> currentlyHovered = Optional.empty();
    private final List<TargetingModeSlot> slots = Lists.newArrayList();

    private RemoveSlot removeSlot;
    private int firstMouseX;
    private int firstMouseY;
    private boolean setFirstMousePos;

    private final TurretEntity turret;

    public TurretTargetingScreen(TurretEntity turret, TurretEntity.TargetingModes targetingMode) {
        super(NarratorChatListener.NO_TITLE);
        this.currentlyHovered = Optional.of(TargetingModeIcon.byTargetingMode(targetingMode));
        this.turret = turret;
    }

    @Override
    protected void init() {
        super.init();

        for(int i = 0; i < TargetingModeIcon.VALUES.length - 1; ++i) {
             TargetingModeIcon targetingModeIcon = TargetingModeIcon.VALUES[i];

             if(targetingModeIcon.getShouldAdded()) {
                 this.slots.add(new TargetingModeSlot(targetingModeIcon, this.width / 2 - ALL_SLOTS_WIDTH / 2 + i * 31 - 20, this.height / 2 - 46, i));

             }
        }

        removeSlot = new RemoveSlot(TargetingModeIcon.REMOVE, this.width / 2 - ALL_SLOTS_WIDTH / 2 + 83, this.height / 2 - 46, new TranslatableComponent("slot.lootdebugs.turret.remove"), TargetingModeIcon.VALUES.length - 1);


    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (!this.checkToClose()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            pPoseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, TURRET_TARGETING_SWITCHER_TEXTURE);
            int i = this.width / 2 - 76;
            int j = this.height / 2 - 31 - 27;
            blit(pPoseStack, i, j, 0.0F, 0.0F, 153, 50, 256, 256);
            pPoseStack.popPose();
            super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

            this.currentlyHovered.ifPresent((p_97563_) -> {
                drawCenteredString(pPoseStack, this.font, p_97563_.getName(), this.width / 2, this.height / 2 - 31 - 34, -1);
            });

            if (!this.setFirstMousePos) {
                this.firstMouseX = pMouseX;
                this.firstMouseY = pMouseY;
                this.setFirstMousePos = true;
            }

            boolean flag = this.firstMouseX == pMouseX && this.firstMouseY == pMouseY;

            for(TargetingModeSlot targetingModeSlot : this.slots) {
                targetingModeSlot.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
                this.currentlyHovered.ifPresent((p_97569_) -> {
                    targetingModeSlot.setSelected(p_97569_ == targetingModeSlot.icon);
                });
                if (!flag && targetingModeSlot.isHoveredOrFocused()) {
                    this.currentlyHovered = Optional.of(targetingModeSlot.icon);
                }
            }
            removeSlot.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

            this.currentlyHovered.ifPresent((p_97569_) -> {
                removeSlot.setSelected(p_97569_ == removeSlot.icon);
            });
            if (!flag && removeSlot.isHoveredOrFocused()) {
                this.currentlyHovered = Optional.of(removeSlot.icon);
            }

        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private boolean checkToClose()
    {
        int mouseLeftState = GLFW.glfwGetMouseButton(this.minecraft.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_RIGHT);
        if (mouseLeftState != GLFW_PRESS) {
            this.minecraft.setScreen((Screen)null);
            switchToHoveredTargetingType(turret, this.minecraft, this.currentlyHovered);
            return true;
        } else {
            return false;
        }
    }

    private static void switchToHoveredTargetingType(TurretEntity turret, Minecraft pMinecraft, Optional<TargetingModeIcon> pIcon) {
        if (pMinecraft.gameMode != null && pMinecraft.player != null && pIcon.isPresent())
        {
            if(TargetingModeIcon.byId(pIcon.get().id) == TargetingModeIcon.REMOVE)
            {
                PacketHandler.sendToServer(new TurretRemovePacket(turret.getId()));
                turret.dropTurret();
            }
            else
            {
                turret.setTargetingMode(TargetingModeIcon.getTargetingMode(pIcon.get()));
                PacketHandler.sendToServer(new TurretTargetingSyncPacket(turret.getId(), TargetingModeIcon.getTargetingMode(pIcon.get())));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public enum TargetingModeIcon {
        ENEMY(0, new TranslatableComponent("slot.lootdebugs.turret.enemy"),   true),

        PASSIVE(1, new TranslatableComponent("slot.lootdebugs.turret.passive"),   true),

        NEUTRAL_AND_ENEMY(2, new TranslatableComponent("slot.lootdebugs.turret.neutral_and_enemy"), true),

        REMOVE(3, new TranslatableComponent("slot.lootdebugs.turret.remove"), false);


        protected static final TargetingModeIcon[] VALUES = values();
        private static final int ICON_AREA = 16;
        protected static final int ICON_TOP_LEFT = 5;
        final Component name;

        final boolean shouldRender;

        final int id;

        private static final TargetingModeIcon[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(TargetingModeIcon::getId)).toArray((p_41067_) -> {
            return new TargetingModeIcon[p_41067_];
        });

        private TargetingModeIcon(int id, Component pName, boolean shouldRender) {

            this.id = id;
            this.name = pName;
            this.shouldRender = shouldRender;
        }

        public int getId() {
            return this.id;
        }

        public boolean getShouldAdded()
        {
            return shouldRender;
        }

        public static TargetingModeIcon byTargetingMode(TurretEntity.TargetingModes targetingMode)
        {
            switch (targetingMode)
            {
                case PASSIVE -> {return PASSIVE;}
                case ENEMYS -> {return ENEMY;}
                case NEUTRAL_AND_ENEMYS -> {return NEUTRAL_AND_ENEMY;}
                default -> { return ENEMY;}
            }
        }

        public static TargetingModeIcon byId(int classId) {
            if (classId < 0 || classId >= BY_ID.length) {
                classId = 0;
            }

            return BY_ID[classId];
        }

        public static TurretEntity.TargetingModes getTargetingMode(TargetingModeIcon targetingModeIcon)
        {
            switch (targetingModeIcon)
            {
                case PASSIVE -> {return TurretEntity.TargetingModes.PASSIVE;}
                case ENEMY -> {return ENEMYS;}
                case NEUTRAL_AND_ENEMY -> {return NEUTRAL_AND_ENEMYS;}
                default -> { return ENEMYS;}
            }
        }

        Component getName() {
            return this.name;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class RemoveSlot extends AbstractWidget
    {

        final TargetingModeIcon icon;

        final int slot;

        public RemoveSlot(TargetingModeIcon icon, int pX, int pY, Component name, int slot) {
            super(pX, pY, 26, 26, name);
            this.icon = icon;
            this.slot = slot;
        }
        private boolean isSelected;


        public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TurretTargetingScreen.TURRET_TARGETING_SWITCHER_TEXTURE);
            pPoseStack.pushPose();

            Minecraft minecraft = Minecraft.getInstance();
            this.drawSlot(pPoseStack, minecraft.getTextureManager(), slot);
            if (this.isSelected) {
                this.drawSelection(pPoseStack, minecraft.getTextureManager(), slot);
            }

        }

        public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }

        public boolean isHoveredOrFocused() {
            return super.isHoveredOrFocused() || this.isSelected;
        }

        public void setSelected(boolean pIsSelected) {
            this.isSelected = pIsSelected;
        }

        private void drawSlot(PoseStack pPoseStack, TextureManager pTextureManager, int slot) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TurretTargetingScreen.TURRET_TARGETING_SWITCHER_TEXTURE);
            pPoseStack.pushPose();
            pPoseStack.translate((double)this.x, (double)this.y, 0.0D);
            blit(pPoseStack, 0, 0, 26 * slot, 50.0F, 26, 26, 256, 256);
            pPoseStack.popPose();
        }

        private void drawSelection(PoseStack pPoseStack, TextureManager pTextureManager, int slot) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TurretTargetingScreen.TURRET_TARGETING_SWITCHER_TEXTURE);
            pPoseStack.pushPose();
            pPoseStack.translate((double)this.x, (double)this.y, 0.0D);
            blit(pPoseStack, 0, 0, 26 * slot, 76.0F, 26, 26, 256, 256);
            pPoseStack.popPose();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class TargetingModeSlot extends AbstractWidget {
        final TargetingModeIcon icon;

        final int slot;
        private boolean isSelected;

        public TargetingModeSlot(TargetingModeIcon pIcon, int pX, int pY, int slot) {
            super(pX, pY, 26, 26, pIcon.getName());
            this.icon = pIcon;
            this.slot = slot;
        }

        public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            Minecraft minecraft = Minecraft.getInstance();
            this.drawSlot(pPoseStack, minecraft.getTextureManager(), slot);
            if (this.isSelected) {
                this.drawSelection(pPoseStack, minecraft.getTextureManager(), slot);
            }

        }

        public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }

        public boolean isHoveredOrFocused() {
            return super.isHoveredOrFocused() || this.isSelected;
        }

        public void setSelected(boolean pIsSelected) {
            this.isSelected = pIsSelected;
        }

        private void drawSlot(PoseStack pPoseStack, TextureManager pTextureManager, int slot) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TurretTargetingScreen.TURRET_TARGETING_SWITCHER_TEXTURE);
            pPoseStack.pushPose();
            pPoseStack.translate((double)this.x, (double)this.y, 0.0D);
            blit(pPoseStack, 0, 0, 26 * slot, 50.0F, 26, 26, 256, 256);
            pPoseStack.popPose();
        }

        private void drawSelection(PoseStack pPoseStack, TextureManager pTextureManager, int slot) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TurretTargetingScreen.TURRET_TARGETING_SWITCHER_TEXTURE);
            pPoseStack.pushPose();
            pPoseStack.translate((double)this.x, (double)this.y, 0.0D);
            blit(pPoseStack, 0, 0, 26 * slot, 76.0F, 26, 26, 256, 256);
            pPoseStack.popPose();
        }
    }
}
