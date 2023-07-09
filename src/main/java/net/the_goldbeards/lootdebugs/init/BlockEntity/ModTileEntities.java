package net.the_goldbeards.lootdebugs.init.BlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.HeartstoneDefender.HeartstoneDefenderTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge.SatchelChargeTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Shield.ShieldBlockTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Shield.ShieldEmitterBlockTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplinePoleTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal.ClassChangerTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Jukebox.JukeboxTile;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Lloyd.LloydTile;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.init.ModBlocks;

public class ModTileEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYS =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LootDebugsMain.MOD_ID);


    public static final RegistryObject<BlockEntityType<LloydTile>> LLOYD_BLOCK_ENTITY =
            BLOCK_ENTITYS.register("lloyd_block_entity", () ->
                    BlockEntityType.Builder.of(LloydTile::new, ModBlocks.LLOYD.get()).build(null));

    public static final RegistryObject<BlockEntityType<EquipmentTableTile>> EQUIPMENT_TERMINAL_BLOCK_ENTITY =
            BLOCK_ENTITYS.register("equipment_terminal_block_entity", () ->
                    BlockEntityType.Builder.of(EquipmentTableTile::new, ModBlocks.EQUIPMENT_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SatchelChargeTile>> SATCHEL_CHARGE_BLOCK_ENTITY =
            BLOCK_ENTITYS.register("satchel_charge_block_entity", () ->
                    BlockEntityType.Builder.of(SatchelChargeTile::new, ModBlocks.SATCHEL_CHARGE.get()).build(null));

    public static final RegistryObject<BlockEntityType<ShieldBlockTile>> SHIELD_BLOCK_ENTITY =
            BLOCK_ENTITYS.register("shield_block_entity", () ->
                    BlockEntityType.Builder.of(ShieldBlockTile::new, ModBlocks.SHIELD_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ShieldEmitterBlockTile>> SHIELD_EMITTER_BLOCK_ENTITY =
            BLOCK_ENTITYS.register("shield_emitter_block_entity", () ->
                    BlockEntityType.Builder.of(ShieldEmitterBlockTile::new, ModBlocks.SHIELD_EMITTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FuelRefineryTile>> FUEL_PRESS_ENTITY =
            BLOCK_ENTITYS.register("fuel_refinery_entity", () ->
                    BlockEntityType.Builder.of(FuelRefineryTile::new, ModBlocks.FUEL_REFINERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<ClassChangerTile>> CLASS_CHANGER_ENTITY =
            BLOCK_ENTITYS.register("class_changer_entity", () ->
                    BlockEntityType.Builder.of(ClassChangerTile::new, ModBlocks.CLASS_CHANGER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ZiplinePoleTile>> ZIPLINE_POLE_ENTITY =
            BLOCK_ENTITYS.register("zipline_pole_entity", () ->
                    BlockEntityType.Builder.of(ZiplinePoleTile::new, ModBlocks.ZIPLINE_POLE_BLOCK.get()).build(null));

     public static final RegistryObject<BlockEntityType<HeartstoneDefenderTile>> HEARTSTONE_DEFENDER_ENTITY =
            BLOCK_ENTITYS.register("heartstone_defender_entity", () ->
                    BlockEntityType.Builder.of(HeartstoneDefenderTile::new, ModBlocks.HEARTSTONE_DEFENDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<JukeboxTile>> JUKEBOX_ENTITY =
            BLOCK_ENTITYS.register("jukebox_entity", () ->
                    BlockEntityType.Builder.of(JukeboxTile::new, ModBlocks.JUKEBOX.get()).build(null));

    public static void register (IEventBus eventBus)
    {
        BLOCK_ENTITYS.register(eventBus);
    }


}
