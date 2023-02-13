package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class SmartStoutItem extends BeerItem
{
    public SmartStoutItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {

        BlockPos entityPos = new BlockPos(entityLiving.getX(),entityLiving.getY(),entityLiving.getZ());

        if (!worldIn.isClientSide) {

            entityLiving.playSound(ModSounds.BEER_SMARTSTOUT.get(),3,1);
        }
        if(worldIn.isClientSide)
        {

            entityLiving.playSound(ModSounds.BEER_SMARTSTOUT.get(),3,1);
        }
        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof Player && !((Player) entityLiving).getAbilities().invulnerable) {
            stack.shrink(1);
        }

        return stack.isEmpty() ? new ItemStack(ModItems.MUG.get()) : stack;


    }
}
