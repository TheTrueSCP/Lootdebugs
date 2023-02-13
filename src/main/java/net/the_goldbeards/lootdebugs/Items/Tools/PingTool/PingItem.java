package net.the_goldbeards.lootdebugs.Items.Tools.PingTool;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Entities.Tools.PingEntity;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

public class PingItem extends Item {


    public PingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {

        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);

        if(!UsefullStuff.DwarfClasses.canPlayerUseItem(pStack, pPlayer, IClassData.Classes.LeafLover))
        {

            if (!pLevel.isClientSide())
            {
                pPlayer.getCooldowns().addCooldown(this, 10);
                ItemStack pingItemStack = pPlayer.getItemInHand(pUsedHand);
             //   pLevel.playSound(null, pPlayer.blockPosition(), ModSounds.MARK_SOUND.get(), SoundSource.PLAYERS, 1f, 1f);

                if (pingItemStack.hasTag()) {

                    if (pLevel.getEntity((int) UsefullStuff.ItemNBTHelper.getFloat(pingItemStack, "lootdebugs.pingitem.pingentity.id")) instanceof PingEntity PE) {
                        PE.kill();
                    } else if (pLevel.getEntity((int) UsefullStuff.ItemNBTHelper.getFloat(pingItemStack, "lootdebugs.pingitem.pingentity.id")) instanceof LivingEntity LE) {

                        LE.setGlowingTag(false);

                    }
                }

                PingEntity pingEntity = new PingEntity(pPlayer, pLevel, pingItemStack);

                pingEntity.setOwner(pPlayer);
                pLevel.addFreshEntity(pingEntity);
                pingEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 10F, 0.0F);
                UsefullStuff.ItemNBTHelper.putFloat(pingItemStack, "lootdebugs.pingitem.pingentity.id", (int) pingEntity.getId());//save ping id in itemstack
            }
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
    {
        if(pEntity instanceof Player player && pIsSelected)
        {
            if(UsefullStuff.DwarfClasses.canPlayerUseItem(pStack, player, IClassData.Classes.LeafLover))
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tools.you_leaf_lover").getString()), true);
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}
