package net.the_goldbeards.lootdebugs.Items.Tools.PlatformGun;

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
import net.the_goldbeards.lootdebugs.Entities.Tools.FoamEntity;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.util.function.Predicate;


public class PlatformGunItem extends Item {


    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Engineer;

    public static final Predicate<ItemStack> FOAM = (p_43017_) -> {
        return p_43017_.is(ModItems.FOAM.get());
    };


    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    public PlatformGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        //Dwarfclass

        if(pEntity instanceof Player pPlayer)
        {
            pPlayer.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {

                UsefullStuff.ItemNBTHelper.putString(pStack,"platform_gun_dwarfclass", classCap.getDwarfClass().name());//Write every tick the Playerclass into the item


            });

        }

        if(pEntity instanceof Player player && pIsSelected)
        {
            if(UsefullStuff.ItemNBTHelper.getString(pStack,"platform_gun_dwarfclass") != dwarfClassToUse.name()) //TheTrueSCP
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + UsefullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString() + " " + new TranslatableComponent("tool.wrong_class_after").getString()), true);
            }

        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {

        ItemStack pUsedStack = pPlayer.getItemInHand(pHand);

        if(pUsedStack.getTag().getString("platform_gun_dwarfclass") != dwarfClassToUse.name())
        {
            return InteractionResultHolder.pass(pUsedStack);
        }

        ItemStack foam = getAmmo(pPlayer);

        pPlayer.getCooldowns().addCooldown(this, 20);
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
            pUsedStack.releaseUsing(pLevel,pPlayer , 0);

        }
        else
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