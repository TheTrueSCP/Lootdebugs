package net.the_goldbeards.lootdebugs.Items.Food;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class RedSugarItem extends Item {

    private final int HealAmount;
    public RedSugarItem(Properties pProperties, int HealAmount) {
        super(pProperties);
        this.HealAmount = HealAmount;
    }


    @Override
    public int getUseDuration(ItemStack stack) {
        return  42;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player,hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {

        pLivingEntity.heal(HealAmount);
        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.SATURATION,1,-1));

        pStack.shrink(1);
        return pStack;
    }
}
