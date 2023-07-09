package net.the_goldbeards.lootdebugs.init.BlockEntity;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal.ClassChangerContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable.EquipmentTableContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Jukebox.JukeboxContainer;
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Lloyd.LloydContainer;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, LootDebugsMain.MOD_ID);

    public static final RegistryObject<MenuType<LloydContainer>> LLOYD_CONTAINER =
            registerMenuType(LloydContainer::new, "lloyd_container");

    public static final RegistryObject<MenuType<EquipmentTableContainer>> EQUIPMENT_TERMINAL_CONTAINER =
            registerMenuType(EquipmentTableContainer::new, "equipment_terminal_container");

    public static final RegistryObject<MenuType<FuelRefineryContainer>> FUEL_REFINERY_CONTAINER =
            registerMenuType(FuelRefineryContainer::new, "fuel_press_container");

    public static final RegistryObject<MenuType<ClassChangerContainer>> CLASS_CHANGER_CONTAINER =
            registerMenuType(ClassChangerContainer::new, "class_changer_container");

    public static final RegistryObject<MenuType<JukeboxContainer>> JUKEBOX_CONTAINER =
            registerMenuType(JukeboxContainer::new, "jukebox_container");





    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}