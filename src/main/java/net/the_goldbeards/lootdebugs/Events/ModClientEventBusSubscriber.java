package net.the_goldbeards.lootdebugs.Events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal.ClassChangerScreen;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableScreen;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryScreen;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub.PubScreen;
import net.the_goldbeards.lootdebugs.Items.Fuel.FuelItem;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.Entites.LootbugGoldenRender;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.Entites.LootbugOldRender;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.Entites.LootbugRender;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.*;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.Turret.BulletRender;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.Zipline.ZiplineMoveRender;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.Zipline.ZiplineRender;
import net.the_goldbeards.lootdebugs.client.Render.Projectiles.Zipline.ZiplineStringRender;
import net.the_goldbeards.lootdebugs.client.Render.TileEntities.ClassChangerRenderer;
import net.the_goldbeards.lootdebugs.client.model.Armor.DrillerMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Armor.EngineerMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Armor.GunnerMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Armor.ScoutMK1ArmorModel;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugModel;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugOldModel;
import net.the_goldbeards.lootdebugs.client.model.Projectiles.*;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModMenuTypes;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.*;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;


@Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventBusSubscriber
{
    public static final KeyMapping ROCK_AND_STONE = new KeyMapping("key.lootdebugs.rock_and_stone", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, "keyGroup.lootdebugs");
    public static final KeyMapping THROW_FLARE = new KeyMapping("key.lootdebugs.throw_flare", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "keyGroup.lootdebugs");
    public static final KeyMapping CHANGE_DIRECTION = new KeyMapping("key.lootdebugs.zipline.change_direction", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, "keyGroup.lootdebugs");


    private static final ResourceLocation NAUSEA_LOCATION = new ResourceLocation("textures/misc/nausea.png");

    public static final ResourceLocation drunknessOverlay = new ResourceLocation("lootdebugs", "textures/gui/drunkness_overlay.png");
    public static Player getPlayer()
    {
        return Minecraft.getInstance().player;
    }


    //Register
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
        event.registerEntityRenderer(ModEntities.ZIPLINE_MOVE_ENTITY.get(), ZiplineMoveRender::new);
        event.registerEntityRenderer(ModEntities.ZIPLINE_ENTITY.get(), ZiplineRender::new);
        event.registerEntityRenderer(ModEntities.STRING_ANCHOR_ENTITY.get(), ZiplineStringRender::new);
        event.registerEntityRenderer(ModEntities.BULLET_ENTITY.get(), BulletRender::new);


        //Weapons
        event.registerEntityRenderer(ModEntities.SATCHEL_CHARGE.get(), SatchelChargeRender::new);

        //TileEntities

        event.registerBlockEntityRenderer(ModTileEntities.CLASS_CHANGER_ENTITY.get(), ClassChangerRenderer::new);

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

        event.registerLayerDefinition(ShieldEmitterModel.LAYER_LOCATION, ShieldEmitterModel::createBodyLayer);
    }


    @SubscribeEvent
    public static void registerScreens(final FMLClientSetupEvent event)
    {
        MenuScreens.register(ModMenuTypes.PUB_CONTAINER.get(), PubScreen::new);
        MenuScreens.register(ModMenuTypes.EQUIPMENT_TERMINAL_CONTAINER.get(), EquipmentTableScreen::new);
        MenuScreens.register(ModMenuTypes.FUEL_REFINERY_CONTAINER.get(), FuelRefineryScreen::new);
        MenuScreens.register(ModMenuTypes.CLASS_CHANGER_CONTAINER.get(), ClassChangerScreen::new);
    }

    @SubscribeEvent
    public static void registerKeys(final FMLClientSetupEvent event)
    {
        ClientRegistry.registerKeyBinding(ROCK_AND_STONE);
        ClientRegistry.registerKeyBinding(THROW_FLARE);
        ClientRegistry.registerKeyBinding(CHANGE_DIRECTION);

    }

    @SubscribeEvent
    public static void renderDrunknessOverlay(FMLClientSetupEvent event)
    {

        IIngameOverlay MyHudOverlay = OverlayRegistry.registerOverlayTop("drunkness", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {

            if (Minecraft.getInstance().player.hasEffect(ModEffects.DRUNKNESS.get()) && false )
            {
            float pScalar = (1.0F - Minecraft.getInstance().options.screenEffectScale);
            Minecraft minecraft = Minecraft.getInstance();
            int i = minecraft.getWindow().getGuiScaledWidth();
            int j = minecraft.getWindow().getGuiScaledHeight();
            double d0 = Mth.lerp((double)pScalar, 2.0D, 1.0D);
            float f = 0.2F * pScalar;
            float f1 = 0.4F * pScalar;
            float f2 = 0.2F * pScalar;
            double d1 = (double)i * d0;
            double d2 = (double)j * d0;
            double d3 = ((double)i - d1) / 2.0D;
            double d4 = ((double)j - d2) / 2.0D;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, NAUSEA_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(d3, d4 + d2, -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(d3 + d1, d4 + d2, -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(d3 + d1, d4, -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, 0.0F).endVertex();
            tesselator.end();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();

               /* if (minecraft.gameMode == null) return;
                if (minecraft.level == null) return;
                TextureManager tm = minecraft.getTextureManager();
                int i = minecraft.screen.width;
                int j = minecraft.screen.height;
                NativeImage nativeimage = new NativeImage(i, j, false);
            //    gui.setupOverlayRenderState(true, false, drunknessOverlay-);
                GuiUtils.drawTexturedModalRect(new PoseStack(), 0, 0, 0, 0, screenWidth, screenHeight, 0);*/

            }
        });
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemProperties(final FMLClientSetupEvent event)
    {

        ItemProperties.register(ModItems.OMMORAN_HEARTHSTONE_LOCATOR.get(), new ResourceLocation(LootDebugsMain.MOD_ID,"ommoran_angle"), new ClampedItemPropertyFunction()
        {
            private final CompassWobble wobble = new CompassWobble();

            public float unclampedCall(ItemStack pStack, @javax.annotation.Nullable ClientLevel pClientLevel, @javax.annotation.Nullable LivingEntity pEntity, int p_174675_) {

                Entity entity = (Entity)(pEntity != null ? pEntity : pStack.getEntityRepresentation());

                if(entity instanceof Player player)
                {

                    if (entity == null || !UsefullStuff.DwarfClasses.canPlayerUseItem(pStack, player, IClassData.Classes.LeafLover)) {
                        return 0.0F;
                    } else {
                        if (pClientLevel == null && entity.level instanceof ClientLevel) {
                            pClientLevel = (ClientLevel) entity.level;
                        }

                        BlockPos blockpos = this.getOmmoranPosition(pStack.getOrCreateTag());
                        long i = pClientLevel.getGameTime();
                        if (!UsefullStuff.DwarfClasses.canPlayerUseItem(pStack, getPlayer(), IClassData.Classes.LeafLover) && blockpos != null && !(entity.position().distanceToSqr((double) blockpos.getX() + 0.5D, entity.position().y(), (double) blockpos.getZ() + 0.5D) < (double) 1.0E-5F)) {
                            boolean flag = pEntity instanceof Player && ((Player) pEntity).isLocalPlayer();
                            double d1 = 0.0D;
                            if (flag) {
                                d1 = (double) pEntity.getYRot();
                            } else if (entity instanceof ItemFrame) {
                                d1 = this.getFrameRotation((ItemFrame) entity);
                            } else if (entity instanceof ItemEntity) {
                                d1 = (double) (180.0F - ((ItemEntity) entity).getSpin(0.5F) / ((float) Math.PI * 2F) * 360.0F);
                            } else if (pEntity != null) {
                                d1 = (double) pEntity.yBodyRot;
                            }

                            d1 = Mth.positiveModulo(d1 / 360.0D, 1.0D);
                            double d2 = this.getAngleTo(Vec3.atCenterOf(blockpos), entity) / (double) ((float) Math.PI * 2F);
                            double d3;
                            if (flag) {
                                if (this.wobble.shouldUpdate(i)) {
                                    this.wobble.update(i, 0.5D - (d1 - 0.25D));
                                }

                                d3 = d2 + this.wobble.rotation;
                            } else {
                                d3 = 0.5D - (d1 - 0.25D - d2);
                            }

                            return Mth.positiveModulo((float) d3, 1.0F);
                        } else {
                            return 0.2f;
                        }
                    }
                }
                return 0.0f;
            }

            private int hash(int p_174670_) {
                return p_174670_ * 1327217883;
            }


            @Nullable
            private BlockPos getOmmoranPosition(CompoundTag pCompoundTag) {

                boolean flag = pCompoundTag.contains("OmmoranPos");
                if (flag) {
                    return NbtUtils.readBlockPos(pCompoundTag.getCompound("OmmoranPos"));
                }
                return null;
            }

            private double getFrameRotation(ItemFrame p_117914_) {
                Direction direction = p_117914_.getDirection();
                int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
                return (double)Mth.wrapDegrees(180 + direction.get2DDataValue() * 90 + p_117914_.getRotation() * 45 + i);
            }

            private double getAngleTo(Vec3 p_117919_, Entity p_117920_) {
                return Math.atan2(p_117919_.z() - p_117920_.getZ(), p_117919_.x() - p_117920_.getX());
            }
        });

        ItemProperties.register(ModItems.DRILLS.get(), new ResourceLocation(LootDebugsMain.MOD_ID,"drills_rot"),  new ClampedItemPropertyFunction()
        {

            @Override
            public float unclampedCall(ItemStack pStack, @org.jetbrains.annotations.Nullable ClientLevel pLevel, @org.jetbrains.annotations.Nullable LivingEntity pEntity, int pSeed) {
                if(pEntity == null) {
                    return 0.0F;
                }
                else
                {
                        boolean flag = UsefullStuff.ItemNBTHelper.hasKey(pStack, "lootdebugs.drills_rot");
                        if(flag)
                        {
                            return UsefullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.drills_rot");
                        }

                    return 0.0f;
                }
            }




        });

        ItemProperties.register(ModItems.FUEL.get(), new ResourceLocation(LootDebugsMain.MOD_ID,"fuel_level"), (pStack, pLevel, pEntity, p_174633_) -> {
                if(pEntity == null)
                {
                    return 0.0F;
                }
                else
                {
                    return UsefullStuff.ItemNBTHelper.getFloat(pStack, "fuelAmount") / FuelItem.maxFuel;

                }
        });


    }

    @SubscribeEvent
    public static void setRenderLayer(final FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OMMORAN_HEARTHSTONE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHIELD_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHIELD_EMITTER_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NITRA_ORE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OMMORAN_LAYER_1_OUTERST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OMMORAN_LAYER_2.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OMMORAN_LAYER_3.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OMMORAN_LAYER_4_INNERST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SATCHEL_CHARGE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LIGHT_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EQUIPMENT_TABLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.APOCA_BLOOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BOOLO_CAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GLYPHID_SHIT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BARLEY_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MALT_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.YEAST_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STARCH_PLANT.get(), RenderType.cutout());


        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_MORKITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_LQUID_MORKITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_MORKITE_BLOCK.get(), RenderType.translucent());
    }

    @OnlyIn(Dist.CLIENT)
    static class CompassWobble {
        double rotation;
        private double deltaRotation;
        private long lastUpdateTick;

        boolean shouldUpdate(long pGameTime) {
            return this.lastUpdateTick != pGameTime;
        }

        void update(long pGameTime, double pWobbleAmount) {
            this.lastUpdateTick = pGameTime;
            double d0 = pWobbleAmount - this.rotation;
            d0 = Mth.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
            this.deltaRotation += d0 * 0.1D;
            this.deltaRotation *= 0.8D;
            this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0D);
        }
    }


}
