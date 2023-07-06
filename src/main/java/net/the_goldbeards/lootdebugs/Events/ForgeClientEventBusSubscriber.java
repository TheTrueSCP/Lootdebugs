package net.the_goldbeards.lootdebugs.Events;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawSelectionEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Items.Tools.Drills.DrillsItem;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Network.Entity.Zipline.ChangeDirectionPacket;
import net.the_goldbeards.lootdebugs.Network.Capabillity.Flare.ThrowFlarePacket;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.Capabillity.RockAndStone.RockAndStonePacket;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.capability.Flare.FlareDataCap;
import net.the_goldbeards.lootdebugs.capability.Salute.SaluteDataCap;
import net.the_goldbeards.lootdebugs.init.ModEffects;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import static net.the_goldbeards.lootdebugs.Events.ModClientEventBusSubscriber.*;

@Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEventBusSubscriber {

    private static float swingAngle = 0.0f;
    private static float yawAngle = 0.0f;
    private static float DEFAULT_SWING_SPEED = 1.0f;
    private static float DEFAULT_YAW_SPEED = 0.5f;
    private static final float MAX_SWING_ANGLE = 15.0f;
    private static final float MAX_YAW_ANGLE = 15.0f;


    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    //Key
    @SubscribeEvent
    public static void onKeyEvent(InputEvent.KeyInputEvent event) {
        if (ROCK_AND_STONE.isDown()) {


            Player player = Minecraft.getInstance().player;

            player.getCapability(SaluteDataCap.SALUTE_DATA).ifPresent(saluteCap ->
            {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new RockAndStonePacket());
            });
        }

        if (THROW_FLARE.isDown()) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ThrowFlarePacket());
        }

        if (CHANGE_DIRECTION.isDown()) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ChangeDirectionPacket());
        }
    }

    //UI
    @SubscribeEvent
    public static void drawTextEvent(RenderGameOverlayEvent.Text event) {
        Player player = getPlayer();


        if (player != null) {
            if (ModUtils.DwarfClasses.isPlayerDwarf(player)) {

                player.getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap ->
                {
                    event.getLeft().add(new TranslatableComponent("gui.lootdebugs.player.flares").getString() + ": " + flareCap.getStoredFlares() + "/" + 4);
                });

                player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap -> {

                    IClassData.Classes dwarfClass = classCap.getDwarfClass();
                    event.getLeft().add(ModUtils.DwarfClasses.getClassColor(dwarfClass) + ModUtils.DwarfClasses.getClassTranslate(dwarfClass).getString());

                });
            }
        }
    }


    //Drills
    @SubscribeEvent
    public static void handleUnderwaterDrill(PlayerEvent.HarvestCheck ev) {
        if (!(ev.getEntityLiving() instanceof Player player))
            return;
        ItemStack drill = player.getInventory().getSelected();
        if (!(drill.getItem() instanceof DrillsItem drillItem))
            return;
        if (player.isEyeInFluid(FluidTags.WATER))
            ev.setCanHarvest(false);
    }


    @SubscribeEvent
    public static void renderAdditionalBlockBounds(DrawSelectionEvent.HighlightBlock event) {
        if (event.getTarget().getType() == HitResult.Type.BLOCK && event.getCamera().getEntity() instanceof LivingEntity player) {
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
            if (!stack.isEmpty() && stack.getItem() instanceof DrillsItem) {

                if (player instanceof Player && !player.isShiftKeyDown() && !ModUtils.ItemNBTHelper.getBoolean(stack, "onBlockMode")) {
                    //Render for every Block which is breaking the block-break animation
                    ImmutableList<BlockPos> blocks = ((DrillsItem) stack.getItem()).getExtraBlocksDug(world, (Player) player, event.getTarget());
                    ModUtils.BlockHelpers.drawAdditionalBlockbreak(event, (Player) player, blocks);
                }
            }
        }
    }


    //GoldLootbugEffect
    @SubscribeEvent
    public static void playerLootbugGoldEffect(PlayerInteractEvent.RightClickBlock event) {

        Level level = event.getWorld();
        if (event.getPlayer().hasEffect(ModEffects.LOOTBUG_TOUCH.get())) {
            if (level.getBlockState(event.getPos()).is(ModTags.Blocks.GOLD_BLOCK_REPLACEABLE_BLOCKS)) {
                level.setBlock(event.getPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
            }
        }
    }

    @SubscribeEvent
    public static void playerLootbugGoldEffect(PlayerInteractEvent.LeftClickBlock event) {

        Level level = event.getWorld();
        if (event.getPlayer().hasEffect(ModEffects.LOOTBUG_TOUCH.get())) {
            if (level.getBlockState(event.getPos()).is(ModTags.Blocks.GOLD_BLOCK_REPLACEABLE_BLOCKS)) {
                level.setBlock(event.getPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
            }
        }
    }

    //Entity

    @SubscribeEvent
    public static void playerLootbugGoldEffect(PlayerInteractEvent.EntityInteract event) {

        Level level = event.getWorld();
        AABB aabb = event.getTarget().getType().getDimensions().makeBoundingBox(event.getTarget().position());


        //for loops aabb -> minX to maxX... level.getBlockstate([forloop values]).setBlock(Blocks.GOLD_BLOCK);
        if (event.getPlayer().hasEffect(ModEffects.LOOTBUG_TOUCH.get()) && event.getEntity().getType() != EntityType.PLAYER) {
            if (level.getBlockState(event.getPos()).is(ModTags.Blocks.GOLD_BLOCK_REPLACEABLE_BLOCKS)) {
                level.setBlock(event.getPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
            }
        }
    }


    @SubscribeEvent
    public static void renderDrunkness(EntityViewRenderEvent.CameraSetup event) {

        if (Minecraft.getInstance().player.hasEffect(ModEffects.DRUNKNESS.get()) && Minecraft.getInstance().options.screenEffectScale > 0) {
            float screenEffectsScale = Minecraft.getInstance().options.screenEffectScale;

            float drunknessAmp = Minecraft.getInstance().player.getEffect(ModEffects.DRUNKNESS.get()).getAmplifier();



            float swingSpeedModifier = 0.3f * drunknessAmp * screenEffectsScale;
            float swingAngelModifier = 0.2f * drunknessAmp * screenEffectsScale;//max is 1

            swingAngle += DEFAULT_SWING_SPEED * swingSpeedModifier;
            if (swingAngle >= MAX_SWING_ANGLE * swingAngelModifier || swingAngle <= -MAX_SWING_ANGLE * swingAngelModifier) {
                DEFAULT_SWING_SPEED *= -1;
            }

            yawAngle += DEFAULT_YAW_SPEED * swingSpeedModifier;
            if (yawAngle >= MAX_YAW_ANGLE * swingAngelModifier || yawAngle <= -MAX_YAW_ANGLE * swingAngelModifier) {
                DEFAULT_YAW_SPEED *= -1;
            }

            // Modify the field of view
            event.setRoll(event.getRoll() + swingAngle); // You can adjust the field of view or any other camera properties here
            event.setYaw(event.getYaw() + yawAngle);
        }
    }
}
