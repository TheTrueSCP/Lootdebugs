package net.the_goldbeards.lootdebugs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class ModGroup {

    public static final CreativeModeTab LOOTDEBUGS_TAB = new CreativeModeTab("lootbuggroup") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.COMPANY_STANDARD_PICKAXE.get());
        }
    };
}

