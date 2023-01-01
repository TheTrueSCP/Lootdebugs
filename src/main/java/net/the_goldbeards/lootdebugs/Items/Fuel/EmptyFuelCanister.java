package net.the_goldbeards.lootdebugs.Items.Fuel;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmptyFuelCanister extends Item
{
    public EmptyFuelCanister(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
       if(Screen.hasShiftDown())
       {
           pTooltipComponents.add(new TranslatableComponent("tooltip.lootdebugs.fuel_canister.refuel"));
       }
       else
       {
           pTooltipComponents.add(new TranslatableComponent("tooltip.lootdebugs.more_information"));
       }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
