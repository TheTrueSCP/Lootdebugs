package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModEffects;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class BlackoutStoutItem extends BeerItem
{


    public BlackoutStoutItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }


    @Override
    public void addDrunkness(LivingEntity entityLiving, ItemStack pStack) {

        if(entityLiving instanceof Player player)
        {
            if (!ModUtils.DwarfClasses.isOnlyPlayerClass(player, IClassData.Classes.Karl))
            {
                entityLiving.addEffect(new MobEffectInstance(ModEffects.SLOW_DEATH.get(),100,5));
            }
            else
            {
                pStack.setCount(0);
                player.getInventory().add(new ItemStack(ModItems.LOOTBUG_GOLD.get(), 1));
            }
        }
        super.addDrunkness(entityLiving, pStack);
    }
}
