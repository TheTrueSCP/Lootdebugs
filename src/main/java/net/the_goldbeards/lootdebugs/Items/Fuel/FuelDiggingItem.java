package net.the_goldbeards.lootdebugs.Items.Fuel;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.the_goldbeards.lootdebugs.init.ModItems;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public abstract class FuelDiggingItem extends FuelConsumeItem {

    public static final Predicate<ItemStack> FUEL = (p_43017_) -> {
        return p_43017_.is(ModItems.FUEL_CANISTER.get());
    };

    public FuelDiggingItem(Item.Properties pProperties) {
        super(pProperties);
    }


    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        Tier tier = getHarvestLevel(stack, null);
        if(tier==null)
            return false;
        return TierSortingRegistry.isCorrectTierForDrops(tier, state);

    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        if(pAttacker instanceof Player)
        {
            consumeFuel((Player) pAttacker, FUEL, 1);
        }


        return true;
    }

    //Disable Swing animation
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    public abstract Tier getHarvestLevel(ItemStack stack, @Nullable Player player);
}