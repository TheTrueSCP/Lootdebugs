package net.the_goldbeards.lootdebugs.Items.TileEntities;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.Items.Fuel.FuelCanisterItem;
import net.the_goldbeards.lootdebugs.util.ModUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FuelRefineryItem extends BlockItem {
    public FuelRefineryItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {

            pTooltip.add(new TranslatableComponent("tooltip.lootdebugs.fuel_refinery.function"));

            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

}
