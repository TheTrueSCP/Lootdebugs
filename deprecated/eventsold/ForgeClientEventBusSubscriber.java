package net.the_goldbeards.lootdebugs.eventsold;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawSelectionEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Items.Tools.Drills.DrillsItem;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.Flare.ThrowFlarePacket;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.RockAndStone.RockAndStonePacket;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Flare.FlareDataCap;
import net.the_goldbeards.lootdebugs.capability.Salute.SaluteDataCap;
import net.the_goldbeards.lootdebugs.init.ModEffects;
import net.the_goldbeards.lootdebugs.util.HelpfullStuff;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;
import net.the_goldbeards.lootdebugs.util.ModTags;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ForgeClientEventBusSubscriber
{

    public static final KeyMapping ROCK_AND_STONE = new KeyMapping("lootdebugs.key.rock_and_stone", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, "Lootdebugs");
    public static final KeyMapping THROW_FLARE = new KeyMapping("lootdebugs.key.throw_flare", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "Lootdebugs");


    private static final ResourceLocation NAUSEA_LOCATION = new ResourceLocation("textures/misc/nausea.png");


    public static Player getPlayer()
    {
        return Minecraft.getInstance().player;
    }

    @SubscribeEvent
    public static void onKeyEvent(InputEvent.KeyInputEvent event)
    {
        if (ROCK_AND_STONE.isDown()) {


            Player player = Minecraft.getInstance().player;

            player.getCapability(SaluteDataCap.SALUTE_DATA).ifPresent(saluteCap ->
            {

                if(saluteCap.getDwarfSaluteCooldown() <= 0)
                {
                    player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
                    {
                        player.playSound(ModSounds.ROCK_AND_STONE.get(),1,classCap.getDwarfClass());
                        PacketHandler.send(PacketDistributor.SERVER.noArg(), new RockAndStonePacket());
                    });
                }

            });




        }

        if (THROW_FLARE.isDown())
        {

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ThrowFlarePacket());

        }




    }

       /* @SubscribeEvent
        public  static void drawImageEvent(RenderGameOverlayEvent.Post event)
        {
            IIngameOverlay myHudOverlay = OverlayRegistry.registerOverlayTop("MyHudOverlay", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.gameMode == null) return;
                if (minecraft.level == null) return;
                TextureManager tm = minecraft.getTextureManager();
                gui.setupOverlayRenderState(true, false, rl);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                tm.bindForSetup(rl);
                RenderSystem.enableTexture();
                RenderSystem.bindTexture(tm.getTexture(rl).getId());
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);
                GuiUtils.drawTexturedModalRect(new PoseStack(), 0, 0, 0, 0, 16, 16, 0);
            });

           /*
            if (Minecraft.getInstance().player != null) {

                if (Minecraft.getInstance().player.hasEffect(ModEffects.DRUNKNESS.get()))
                {

                    Random random = Minecraft.getInstance().player.getRandom();


                    int amp = Minecraft.getInstance().player.getEffect(ModEffects.DRUNKNESS.get()).getAmplifier();



                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    RenderSystem.enableBlend();
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                    RenderSystem.setShaderColor(233, 233, 233, 1.0F);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, NAUSEA_LOCATION);

                    Tesselator tesselator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tesselator.getBuilder();
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    tesselator.end();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.disableBlend();

                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                }
            }

        }*/

    @SubscribeEvent
    public static void drawTextEvent(RenderGameOverlayEvent.Text event)
    {
        Player player = getPlayer();


        if(player != null) {

            player.getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap ->
            {
                event.getLeft().add(new TranslatableComponent("player.flares").getString() +": " + flareCap.getStoredFlares() + "/" + LootdebugsConfig.MAX_FLARE_AMOUNT.get());
            });

            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap -> {

                int dwarfClass = classCap.getDwarfClass();
                event.getLeft().add(HelpfullStuff.ClassTranslator.getClassColor(dwarfClass) + HelpfullStuff.ClassTranslator.getClassTranslate(dwarfClass).getString());

            });



        }




    }


    @SubscribeEvent
    public static void handleUnderwaterDrill(PlayerEvent.HarvestCheck ev)
    {
        if(!(ev.getEntityLiving() instanceof Player player))
            return;
        ItemStack drill = player.getInventory().getSelected();
        if(!(drill.getItem() instanceof DrillsItem drillItem))
            return;
        if(player.isEyeInFluid(FluidTags.WATER))
            ev.setCanHarvest(false);
    }


    @SubscribeEvent
    public static void renderAdditionalBlockBounds(DrawSelectionEvent.HighlightBlock event)
    {
        if(event.getTarget().getType()== HitResult.Type.BLOCK && event.getCamera().getEntity() instanceof LivingEntity player)
        {
            PoseStack transform = event.getPoseStack();
            MultiBufferSource buffer = event.getMultiBufferSource();
            BlockHitResult rtr = event.getTarget();
            BlockPos pos = rtr.getBlockPos();
            Vec3 renderView = event.getCamera().getPosition();
            transform.pushPose();
            transform.translate(-renderView.x, -renderView.y, -renderView.z);
            transform.translate(pos.getX(), pos.getY(), pos.getZ());
            float eps = 0.002F;
            BlockEntity tile = player.level.getBlockEntity(rtr.getBlockPos());
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            Level world = player.level;

            transform.popPose();
            if(!stack.isEmpty() && stack.getItem() instanceof DrillsItem)
            {

                if(player instanceof Player &&!player.isShiftKeyDown() && !HelpfullStuff.ItemNBTHelper.getBoolean(stack, "onBlockMode"))
                {
                    //Render for every Block which is breaking the block-break animation
                    ImmutableList<BlockPos> blocks = ((DrillsItem)stack.getItem()).getExtraBlocksDug(world, (Player)player, event.getTarget());
                    HelpfullStuff.drawAdditionalBlockbreak(event, (Player)player, blocks);
                }
            }
        }
    }



    //GoldLootbugEffect
    @SubscribeEvent
    public static void playerLootbugGoldEffect(PlayerInteractEvent event)
    {

        Level level = event.getWorld();
        if(event.getPlayer().hasEffect(ModEffects.GOLD_TOUCH.get()))
        {
            if (level.getBlockState(event.getPos()).is(ModTags.Blocks.GOLD_BLOCK_REPLACEABLE_BLOCKS))
            {
                level.setBlock(event.getPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
            }
        }
    }

    @SubscribeEvent
    public static void playerLootbugGoldEffect(AttackEntityEvent event)
    {
        Level level = event.getEntity().level;
        if(event.getPlayer().hasEffect(ModEffects.GOLD_TOUCH.get()))
        {
            if(level.getBlockState(event.getTarget().getOnPos().above()).is(ModTags.Blocks.GOLD_BLOCK_REPLACEABLE_BLOCKS))
            {
                event.getTarget().remove(Entity.RemovalReason.DISCARDED);
                level.setBlock(event.getTarget().getOnPos().above(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
            }
        }
    }

    //Stuff



}