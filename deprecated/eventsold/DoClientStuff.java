package net.the_goldbeards.lootdebugs.eventsold;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal.ClassChangeScreen;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableScreen;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelPressScreen;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub.PubScreen;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModMenuTypes;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModFluids;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.HelpfullStuff;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class DoClientStuff
{
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


        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_MORKITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_LQUID_MORKITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_MORKITE_BLOCK.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerScreens(final FMLClientSetupEvent event)
    {
        MenuScreens.register(ModMenuTypes.PUB_CONTAINER.get(), PubScreen::new);
        MenuScreens.register(ModMenuTypes.EQUIPMENT_TERMINAL_CONTAINER.get(), EquipmentTableScreen::new);
        MenuScreens.register(ModMenuTypes.FUEL_PRESS_CONTAINER.get(), FuelPressScreen::new);
        MenuScreens.register(ModMenuTypes.CLASS_CHANGE_CONTAINER.get(), ClassChangeScreen::new);
    }



    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerItemProperties(final FMLClientSetupEvent event)
    {

        ItemProperties.register(ModItems.OMMORAN_HEARTHSTONE_LOCATOR.get(), new ResourceLocation(LootDebugsMain.MOD_ID,"ommoran_angle"), new ClampedItemPropertyFunction()
        {
            private final DoClientStuff.CompassWobble wobble = new DoClientStuff.CompassWobble();

            public float unclampedCall(ItemStack pStack, @javax.annotation.Nullable ClientLevel pClientLevel, @javax.annotation.Nullable LivingEntity pEntity, int p_174675_) {

                Entity entity = (Entity)(pEntity != null ? pEntity : pStack.getEntityRepresentation());


                if (entity == null)
                {
                    return 0.0F;
                }
                else
                {
                    if (pClientLevel == null && entity.level instanceof ClientLevel) {
                        pClientLevel = (ClientLevel)entity.level;
                    }

                    BlockPos blockpos = this.getOmmoranPosition(pStack.getOrCreateTag());
                    long i = pClientLevel.getGameTime();
                    if (blockpos != null && !(entity.position().distanceToSqr((double)blockpos.getX() + 0.5D, entity.position().y(), (double)blockpos.getZ() + 0.5D) < (double)1.0E-5F))
                    {
                        boolean flag = pEntity instanceof Player && ((Player)pEntity).isLocalPlayer();
                        double d1 = 0.0D;
                        if (flag) {
                            d1 = (double)pEntity.getYRot();
                        } else if (entity instanceof ItemFrame) {
                            d1 = this.getFrameRotation((ItemFrame)entity);
                        } else if (entity instanceof ItemEntity) {
                            d1 = (double)(180.0F - ((ItemEntity)entity).getSpin(0.5F) / ((float)Math.PI * 2F) * 360.0F);
                        } else if (pEntity != null) {
                            d1 = (double)pEntity.yBodyRot;
                        }

                        d1 = Mth.positiveModulo(d1 / 360.0D, 1.0D);
                        double d2 = this.getAngleTo(Vec3.atCenterOf(blockpos), entity) / (double)((float)Math.PI * 2F);
                        double d3;
                        if (flag) {
                            if (this.wobble.shouldUpdate(i)) {
                                this.wobble.update(i, 0.5D - (d1 - 0.25D));
                            }

                            d3 = d2 + this.wobble.rotation;
                        } else {
                            d3 = 0.5D - (d1 - 0.25D - d2);
                        }

                        return Mth.positiveModulo((float)d3, 1.0F);
                    }
                    else
                    {
                       return 0.2f;
                    }
                }
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
                    if(pEntity.getItemInHand(pEntity.getUsedItemHand()) == pStack)
                    {
                        boolean flag = HelpfullStuff.ItemNBTHelper.hasKey(pStack, "lootdebugs.drills_rot");
                        if(flag)
                        {
                            return HelpfullStuff.ItemNBTHelper.getFloat(pStack, "lootdebugs.drills_rot");
                        }
                    }

                    return 0.0f;
                }
            }




        });

        ItemProperties.register(ModItems.FUEL.get(), new ResourceLocation(LootDebugsMain.MOD_ID,"fuel_level"), (pStack, pLevel, pEntity, p_174633_) -> {
            if(pEntity == null) {
                return 0.0F;
            }
            else
            {
                if(pStack.isDamaged() && pStack.isDamageableItem())
                {
                    float val = ((float)pStack.getMaxDamage() - (float)pStack.getDamageValue()) / (float)pStack.getMaxDamage();
                    return val;


                }
                return 0.0f;
            }


        });



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
