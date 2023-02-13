package net.the_goldbeards.lootdebugs.eventsold;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.the_goldbeards.lootdebugs.Items.Tools.Drills.DrillsItem;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Flare.FlareDataCap;
import net.the_goldbeards.lootdebugs.client.Render.Entites.LootbugGoldenRender;
import net.the_goldbeards.lootdebugs.client.Render.Entites.LootbugOldRender;
import net.the_goldbeards.lootdebugs.client.Render.Entites.LootbugRender;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.*;
import net.the_goldbeards.lootdebugs.client.Render.Weapons.SatchelChargeRender;
import net.the_goldbeards.lootdebugs.client.model.Armor.DrillerMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Armor.EngineerMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Armor.GunnerMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Armor.ScoutMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugModel;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugOldModel;
import net.the_goldbeards.lootdebugs.client.model.Projectiles.*;
import net.the_goldbeards.lootdebugs.client.model.Weapons.SatchelChargeModel;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.util.HelpfullStuff;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;

import static net.the_goldbeards.lootdebugs.eventsold.ForgeClientEventBusSubscriber.ROCK_AND_STONE;
import static net.the_goldbeards.lootdebugs.eventsold.ForgeClientEventBusSubscriber.THROW_FLARE;

@OnlyIn(Dist.CLIENT)
public class ClientEventBusSubscriber
{
    private static final ResourceLocation NAUSEA_LOCATION = new ResourceLocation("textures/misc/nausea.png");


    public static Player getPlayer()
    {
        return Minecraft.getInstance().player;
    }



 @Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ModEvents
    {
        int widthMid = Minecraft.getInstance().screen.width / 2;
        int heightMid = Minecraft.getInstance().screen.height / 2;


        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
        {

            //Mobs
            event.registerEntityRenderer(ModEntities.LOOTBUG.get(), LootbugRender::new);
            event.registerEntityRenderer(ModEntities.LOOTBUG_GOLDEN.get(), LootbugGoldenRender::new);
            event.registerEntityRenderer(ModEntities.LOOTBUG_OLD.get(), LootbugOldRender::new);

            //Shoots
            event.registerEntityRenderer(ModEntities.FOAM.get(), FoamRender::new);
            event.registerEntityRenderer(ModEntities.SHOOT_FLARE.get(), ShootFlareRender::new);
            event.registerEntityRenderer(ModEntities.FLARE.get(), FlareRender::new);
            event.registerEntityRenderer(ModEntities.GRAPPLING_HOOK_HOOK.get(), GrapplingHookHookRender::new);
            event.registerEntityRenderer(ModEntities.PING_ENTITY.get(), PingRender::new);
            event.registerEntityRenderer(ModEntities.SHIELD_ENTITY.get(), ShieldRender::new);

            //Weapons
            event.registerEntityRenderer(ModEntities.SATCHEL_CHARGE.get(), SatchelChargeRender::new);

        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            event.registerLayerDefinition(LootbugModel.LAYER_LOCATION, LootbugModel::createBodyLayer);
            event.registerLayerDefinition(LootbugModel.LAYER_LOCATION_GOLDEN, LootbugModel::createBodyLayer);
            event.registerLayerDefinition(LootbugOldModel.LAYER_LOCATION, LootbugOldModel::createBodyLayer);


            event.registerLayerDefinition(FoamModel.LAYER_LOCATION, FoamModel::createBodyLayer);


            event.registerLayerDefinition(FlareModel.LAYER_LOCATION, FlareModel::createBodyLayer);

            event.registerLayerDefinition(ShootFlareModel.LAYER_LOCATION, ShootFlareModel::createBodyLayer);

            event.registerLayerDefinition(FoamModel.LAYER_LOCATION, FoamModel::createBodyLayer);//grappling hook hook

            event.registerLayerDefinition(SatchelChargeModel.LAYER_LOCATION, SatchelChargeModel::createBodyLayer);

            event.registerLayerDefinition(PingModel.LAYER_LOCATION, PingModel::createBodyLayer);

            event.registerLayerDefinition(GrapplingHookHookModel.LAYER_LOCATION, GrapplingHookHookModel::createBodyLayer);


            event.registerLayerDefinition(DrillerMK1ArmorModel.LAYER_LOCATION, DrillerMK1ArmorModel::createBodyLayer);

            event.registerLayerDefinition(ScoutMK1ArmorModel.LAYER_LOCATION, ScoutMK1ArmorModel::createBodyLayer);

            event.registerLayerDefinition(EngineerMK1ArmorModel.LAYER_LOCATION, EngineerMK1ArmorModel::createBodyLayer);

            event.registerLayerDefinition(GunnerMK1ArmorModel.LAYER_LOCATION, GunnerMK1ArmorModel::createBodyLayer);
        }


        @SubscribeEvent
        public static void registerKeys(FMLClientSetupEvent event)
        {
            ClientRegistry.registerKeyBinding(ROCK_AND_STONE);
            ClientRegistry.registerKeyBinding(THROW_FLARE);

        }

    }

/*
    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Forge
    {
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


    }





