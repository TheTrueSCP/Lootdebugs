package net.the_goldbeards.lootdebugs.Items.Tools.FlareGun;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Entities.Projectile.FlairEntity;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class FlairItem extends ArrowItem {

    public final float damage;

    public FlairItem(Item.Properties properties, float damage) {
        super(properties);
        this.damage = damage;
    }

    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        FlairEntity arrow = new FlairEntity(pShooter,pLevel, ModItems.FLAIR.get());
        arrow.setBaseDamage(this.damage);
        return arrow;
    }

    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
        int enchant = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, bow);
        return enchant <= 0 ? false : this.getClass() == FlairItem.class;
    }
}
