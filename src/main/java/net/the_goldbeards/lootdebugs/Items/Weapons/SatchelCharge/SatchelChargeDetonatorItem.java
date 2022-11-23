package net.the_goldbeards.lootdebugs.Items.Weapons.SatchelCharge;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SatchelChargeDetonatorItem extends Item {
    private int frequence = 1;
    public SatchelChargeDetonatorItem(Properties pProperties) {
        super(pProperties);
    }



    public void setFrequence(int frequence)
    {
        this.frequence = frequence;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {




        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
