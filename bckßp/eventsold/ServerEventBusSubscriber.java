package net.the_goldbeards.lootdebugs.eventsold;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.Server.ChangeClass.PlayerClassSyncPacket;
import net.the_goldbeards.lootdebugs.Server.Flare.FlareCountSyncPacket;
import net.the_goldbeards.lootdebugs.Server.PacketHandler;
import net.the_goldbeards.lootdebugs.Server.RockAndStone.RockAndStoneSyncPacket;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.capability.Flare.FlareDataCap;
import net.the_goldbeards.lootdebugs.capability.Flare.IFlareData;
import net.the_goldbeards.lootdebugs.capability.Salute.ISaluteData;
import net.the_goldbeards.lootdebugs.capability.Salute.SaluteDataCap;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.recipe.EquipmentTableRecipe;
import net.the_goldbeards.lootdebugs.recipe.PubRecipe;
import net.the_goldbeards.lootdebugs.util.LootdebugsConfig;

import java.util.Random;


public class ServerEventBusSubscriber {

    @Mod.EventBusSubscriber(modid = LootDebugsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class clientRendering
    {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.LOOTBUG.get(), LootbugEntity.createAttributes().build());
            event.put(ModEntities.LOOTBUG_GOLDEN.get(), LootbugEntity.createAttributes().build());
            event.put(ModEntities.LOOTBUG_OLD.get(), LootbugEntity.createAttributes().build());
        }


        @SubscribeEvent
        public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
            Registry.register(Registry.RECIPE_TYPE, EquipmentTableRecipe.Type.ID, EquipmentTableRecipe.Type.INSTANCE);
            Registry.register(Registry.RECIPE_TYPE, PubRecipe.Type.ID, PubRecipe.Type.INSTANCE);
        }
    }

    @Mod.EventBusSubscriber
   public static class EventBusSubscriberCap
   {
       @SubscribeEvent
       public static void attachCapabilityToPlayer(AttachCapabilitiesEvent<Entity> event) {
           ResourceLocation CAPABILITY_FLARES = new ResourceLocation(LootDebugsMain.MOD_ID, "flares_caps");
           ResourceLocation DWARF_CLASS = new ResourceLocation(LootDebugsMain.MOD_ID, "dwarf_class");
           ResourceLocation SALUTE_DWARF = new ResourceLocation(LootDebugsMain.MOD_ID, "salute_dwarf");



           if (event.getObject() instanceof Player || event.getObject().getType() == EntityType.PLAYER) {
               event.addCapability(CAPABILITY_FLARES, new IFlareData());
               event.addCapability(DWARF_CLASS,new IClassData());
               event.addCapability(SALUTE_DWARF, new ISaluteData());


           }

       }


       private boolean isMoving(Player player)
       {
           return player.getDeltaMovement() != Vec3.ZERO;
       }

       @SubscribeEvent
       public static void playerTick(TickEvent.PlayerTickEvent event)
       {

           Random random = event.player.getLevel().getRandom();
           Player player = (Player) event.player;



           //Salutestuff
           if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
               player.getCapability(SaluteDataCap.SALUTE_DATA).ifPresent(saluteCap -> {

                   if (player.isCreative() || player.isSpectator()) {
                       saluteCap.setDwarfSaluteCooldown(0);
                       PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new RockAndStoneSyncPacket(saluteCap.getDwarfSaluteCooldown()));
                   }

                   if (saluteCap.getDwarfSaluteCooldown() > 0) {
                       saluteCap.decreasementDwarfSaluteCooldown();
                       PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new RockAndStoneSyncPacket(saluteCap.getDwarfSaluteCooldown()));
                   }

                   if (saluteCap.getDwarfSaluteCooldown() < 0) {
                       saluteCap.setDwarfSaluteCooldown(0);
                       PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new RockAndStoneSyncPacket(saluteCap.getDwarfSaluteCooldown()));
                   }

               });
           }


           //Classstuff
               if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
                   player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap -> {

                       if (player.isCreative() || player.isSpectator()) {
                           classCap.setDwarfChangeCooldown(0);
                       }

                       if (classCap.getDwarfClass() > 4) {
                           int Class = 1;
                           classCap.setDwarfClass(Class);
                           PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new PlayerClassSyncPacket(Class));
                       }


                       if (classCap.getDwarfChangeCooldown() > 0) {
                           classCap.decreasementDwarfChangeCooldown();
                       }

                       if (classCap.getDwarfChangeCooldown() < 0) {
                           classCap.setDwarfChangeCooldown(0);
                       }

                   });
               }

               //FlareStuff
               if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START))
                   event.player.getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap -> {
                       int storedFlares = flareCap.getStoredFlares();
                       int maxFlares = LootdebugsConfig.MAX_FLARE_AMOUNT.get();

                       if (event.player.isCreative() || (event.player.isSpectator() && flareCap.getStoredFlares() != maxFlares)) {
                           flareCap.setStoredFlares(maxFlares);
                           flareCap.setFlareThrowCoolDown(0);
                           flareCap.setReplenishTickCounter(0);
                           PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new FlareCountSyncPacket(maxFlares));
                           return;
                       }


                       if (flareCap.getReplenishTickCounter() >= LootdebugsConfig.FLARE_GENERATION_TIME.get() && storedFlares < maxFlares) {
                           int totalFlares = storedFlares + 1;

                           flareCap.setStoredFlares(totalFlares);
                           PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new FlareCountSyncPacket(totalFlares));
                           flareCap.setReplenishTickCounter(0);
                       }

                       if (flareCap.getStoredFlares() == LootdebugsConfig.MAX_FLARE_AMOUNT.get()) {
                           flareCap.setReplenishTickCounter(0);
                       }

                       flareCap.incrementReplenishTickCounter();

                       if (flareCap.getFlareThrowCoolDown() > 0) {
                           flareCap.decrementFlareThrowCoolDown();
                       }


                   });
               

       }

       @SubscribeEvent
       public static void playerJoin(EntityJoinWorldEvent event) {
           // FlareSync
           if (!event.getWorld().isClientSide && event.getEntity() instanceof Player) {
               Player player = (Player) event.getEntity();
               player.getCapability(FlareDataCap.FLARE_DATA).ifPresent(flareCap -> {
                   int storedFlares = flareCap.getStoredFlares();
                   PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new FlareCountSyncPacket(storedFlares));
               });
           }

           //DwarfSync
           if (!event.getWorld().isClientSide && event.getEntity() instanceof Player) {
               Player player = (Player) event.getEntity();
               player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap -> {
                   int dwarfClass = classCap.getDwarfClass();
                   PacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new PlayerClassSyncPacket(dwarfClass));
               });
           }

       }
   }
}
