package net.the_goldbeards.lootdebugs.init.BlockEntity;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangeTerminal.ClassChangeContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelPress.FuelRefineryContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub.PubContainer;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.capability.BeardSlot.BeardSlotContainer;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, LootDebugsMain.MOD_ID);

    public static final RegistryObject<MenuType<PubContainer>> PUB_CONTAINER =
            registerMenuType(PubContainer::new, "pub_container");

    public static final RegistryObject<MenuType<EquipmentTableContainer>> EQUIPMENT_TERMINAL_CONTAINER =
            registerMenuType(EquipmentTableContainer::new, "equipment_terminal_container");

    public static final RegistryObject<MenuType<FuelRefineryContainer>> FUEL_PRESS_CONTAINER =
            registerMenuType(FuelRefineryContainer::new, "fuel_press_container");

    public static final RegistryObject<MenuType<ClassChangeContainer>> CLASS_CHANGE_CONTAINER =
            registerMenuType(ClassChangeContainer::new, "class_change_container");

    public static final RegistryObject<MenuType<BeardSlotContainer>> BEARD_SLOT_CONTAINER =
            registerMenuType(BeardSlotContainer::new, "beard_slot_container");




    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}