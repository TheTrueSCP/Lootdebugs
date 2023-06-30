package net.the_goldbeards.lootdebugs.Items.Tools.PlatformGun;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Entities.Tools.FoamEntity;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicToolItem;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.util.function.Predicate;


public class PlatformGunItem extends BasicToolItem
{


    public static final Predicate<ItemStack> FOAM = (p_43017_) -> {
        return p_43017_.is(ModItems.PLATFORM_GUN_AMMO.get());
    };


    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    public PlatformGunItem(Properties properties) {
        super(properties);
    }


    @Override
    public IClassData.Classes getDwarfClassToUse() {
        return IClassData.Classes.Engineer;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {

        ItemStack pUsedStack = pPlayer.getItemInHand(pHand);

        if(!ModUtils.DwarfClasses.canPlayerUseItem(pUsedStack, pPlayer, getDwarfClassToUse()))
        {
            return InteractionResultHolder.pass(pUsedStack);
        }

        ItemStack foam = getAmmo(pPlayer);

        pPlayer.getCooldowns().addCooldown(this, 15);
        if (!foam.isEmpty()  || pPlayer.isCreative()) {

            FoamEntity foamEntity = new FoamEntity(pPlayer, pLevel);
            foamEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 5.0F, 0.0F);
            pPlayer.playSound(ModSounds.TOOL_FOAM_HARDEN.get(), 1, 1);
            pLevel.addFreshEntity(foamEntity);

            if (!pPlayer.isCreative()) {
                foam.shrink(1);
            }

            if (foam.isEmpty()) {
                pPlayer.getInventory().removeItem(foam);
            }

        }
        else
        {
            pPlayer.getInventory().removeItem(foam);
            pPlayer.playSound(SoundEvents.DISPENSER_FAIL, 1, 1);
        }

        pUsedStack.releaseUsing(pLevel,pPlayer , 0);
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pPlayer.getUsedItemHand()));
    }

    public ItemStack getAmmo(Player player) {
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack1 = player.getInventory().getItem(i);
            if (FOAM.test(itemstack1)) {
                return itemstack1;
            }
        }

        return ItemStack.EMPTY;
    }
}