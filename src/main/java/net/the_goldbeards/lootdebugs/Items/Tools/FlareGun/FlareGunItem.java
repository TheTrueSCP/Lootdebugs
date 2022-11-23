package net.the_goldbeards.lootdebugs.Items.Tools.FlareGun;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Entities.Tools.ShootFlareEntity;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.HelpfullStuff;

import java.util.function.Predicate;



public class FlareGunItem extends Item {

    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Scout;

    public FlareGunItem(Properties pProperties) {
        super(pProperties);
    }
    public static final Predicate<ItemStack> FOAM = (p_43017_) -> {
        return p_43017_.is(ModItems.SHOOT_FLARE.get());
    };

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }




    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     */

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        //Dwarfclass

        if(pEntity instanceof Player pPlayer)
        {
            pPlayer.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {

                HelpfullStuff.ItemNBTHelper.putString(pStack,"flare_gun_dwarfclass", classCap.getDwarfClass().name());//Write every tick the Playerclass into the item


            });

        }

        if(pEntity instanceof Player player && pIsSelected)
        {
            if(!HelpfullStuff.ItemNBTHelper.getString(pStack, "flare_gun_dwarfclass").equals(dwarfClassToUse.name())) //TheTrueSCP
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + HelpfullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString()), true);
            }

        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack pUsedStack = pPlayer.getItemInHand(pHand);


            if (!HelpfullStuff.ItemNBTHelper.getString(pUsedStack, "flare_gun_dwarfclass").equals(dwarfClassToUse.name())) {
                return InteractionResultHolder.pass(pUsedStack);
            }


            ItemStack foam = getAmmo(pPlayer);


            pPlayer.getCooldowns().addCooldown(this, 28);
            if (!foam.isEmpty() || pPlayer.isCreative()) {


                ShootFlareEntity shootFlareEntity = new ShootFlareEntity(pPlayer, pLevel);

                shootFlareEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4.0F, 0.0F);
                pPlayer.playSound(ModSounds.TOOL_FOAM_HARDEN.get(), 1, 1);
                pLevel.addFreshEntity(shootFlareEntity);

                if (!pPlayer.isCreative()) {
                    foam.shrink(1);

                }

                if (foam.isEmpty()) {
                    pPlayer.getInventory().removeItem(foam);
                }
            } else
            {
                pPlayer.getInventory().removeItem(foam);
                pPlayer.playSound(SoundEvents.DISPENSER_FAIL, 1, 1);
            }

        return InteractionResultHolder.consume(pPlayer.getItemInHand(pPlayer.getUsedItemHand()));
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
