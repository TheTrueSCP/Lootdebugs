package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
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
            ModUtils.DwarfClasses.setPlayerClass(player, IClassData.Classes.LeafLover);
            player.displayClientMessage(new TranslatableComponent("message.lootdebugs.beer.no_longer_dwarf"), true);
        }
        entityLiving.addEffect(new MobEffectInstance(MobEffects.SATURATION,4, 0));

        super.addEffects(entityLiving);
    }
}