package net.the_goldbeards.lootdebugs.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Config;
import net.the_goldbeards.lootdebugs.Entities.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

import net.the_goldbeards.lootdebugs.Server.BrewBeer;
import net.the_goldbeards.lootdebugs.Server.FlareCountSyncPacket;
import net.the_goldbeards.lootdebugs.Server.PacketHandler;
import net.the_goldbeards.lootdebugs.Server.ThrowFlarePacket;
import net.the_goldbeards.lootdebugs.capability.FlareData;
import net.the_goldbeards.lootdebugs.capability.FlareDataCap;
import net.the_goldbeards.lootdebugs.client.Render.FlairRenderer;
import net.the_goldbeards.lootdebugs.client.Render.LootbugRender;
import net.the_goldbeards.lootdebugs.client.Render.Lootbug_GoldenRender;

import net.the_goldbeards.lootdebugs.init.ModEntities;
import org.lwjgl.glfw.GLFW;

public class ClientEventBusSubscriber
{
    public static final KeyMapping ROCK_AND_STONE = new KeyMapping("lootdebugs.key.rock_and_stone", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, "Sound");

    private static final ResourceLocation CAPABILITY_FLARES = new ResourceLocation("drgflares", "flares");

    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(value = Dist.CLIENT, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
    public static class Mod
    {

        @SubscribeEvent
        public static void clientRendering(FMLClientSetupEvent event)
        {
            ClientRegistry.registerKeyBinding(ROCK_AND_STONE);
        }
    }

    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ModEvents
    {

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event)
        {
            event.put(ModEntities.LOOTBUG.get(), LootbugEntity.createAttributes().build());
            event.put(ModEntities.LOOTBUG_GOLDEN.get(), LootbugEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
        {

            event.registerEntityRenderer(ModEntities.LOOTBUG.get(), LootbugRender::new);
            event.registerEntityRenderer(ModEntities.LOOTBUG_GOLDEN.get(), Lootbug_GoldenRender::new);
            event.registerEntityRenderer(ModEntities.FlAIR_ENTITY.get(), FlairRenderer::new);

        }
    }


    // Catch our key press, fire off a packet to the server for handling.
    @SubscribeEvent
    public static void onKeyEvent(InputEvent.KeyInputEvent event, Level level, Player player)
    {
        if (ROCK_AND_STONE.isDown())
        {
            level.playSound(player,player,ModSounds.LLOYD_INTERACTION.get(), SoundSource.PLAYERS,12,1);
            System.out.println("ROCK AND STONE");
        }
    }

    @SubscribeEvent
    public static void attachFlareCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player)
        {
            event.addCapability(CAPABILITY_FLARES, new FlareData());
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START))
            event.player.getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap -> {
                int storedFlares = flareCap.getStoredFlares();
                int maxFlares = Config.GENERAL.flareQuantity.get();

                if (event.player.isCreative() || (event.player.isSpectator() && !Config.GENERAL.spectatorsRequiredToGenerateFlares.get()) && flareCap.getStoredFlares() != maxFlares)
                {
                    flareCap.setStoredFlares(maxFlares);
                    flareCap.setFlareThrowCoolDown(0);
                    flareCap.setReplenishTickCounter(0);
                    PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new FlareCountSyncPacket(maxFlares));
                    return;
                }

                if (flareCap.getReplenishTickCounter() >= Config.GENERAL.flareReplenishTime.get() && storedFlares < maxFlares)
                {
                    int totalFlares = storedFlares + Config.GENERAL.flareReplenishQuantity.get();

                    flareCap.setStoredFlares(totalFlares);
                    PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new FlareCountSyncPacket(totalFlares));
                    flareCap.setReplenishTickCounter(0);
                }
                flareCap.incrementReplenishTickCounter();

                if (flareCap.getFlareThrowCoolDown() > 0)
                {
                    flareCap.decrementFlareThrowCoolDown();
                }
            });
    }

    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event)
    {
        // need this to sync flare data on join or the client is stupid and displays max.
        if (!event.getWorld().isClientSide && event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            player.getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap -> {
                int storedFlares = flareCap.getStoredFlares();
                PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new FlareCountSyncPacket(storedFlares));
            });
        }
    }

    /**
     * Avoids IDE warnings by returning null for fields that are injected in by forge.
     *
     * @return Not null!
     */
}
