package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class LeafLoverSpecialItem extends BeerItem {


    public LeafLoverSpecialItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void addEffects(LivingEntity entityLiving)
    {
        entityLiving.removeAllEffects();
        if(entityLiving instanceof Player player)
        {
            if(entityLiving.level instanceof ServerLevel)
            {
                ModUtils.DwarfClasses.setPlayerClass(player, IClassData.Classes.LeafLover);
            }
            player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("message.lootdebugs.beer.no_longer_dwarf").getString()), true);
        }
        entityLiving.addEffect(new MobEffectInstance(MobEffects.SATURATION,4, 0));

        super.addEffects(entityLiving);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(pEntity instanceof Player player)
        {
            if (ModUtils.DwarfClasses.isPlayerClass(player, IClassData.Classes.Karl))
            {
                pStack.setCount(0);
                player.getInventory().setItem(pSlotId, new ItemStack(ModItems.BLACKOUT_STOUT.get(), 1));
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public boolean addDrunkness() {
        return false;
    }
}