package net.the_goldbeards.lootdebugs.Items.Tools.PingTool;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Entities.Tools.PingEntity;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicAllClassItem;
import net.the_goldbeards.lootdebugs.Network.Capabillity.PingClasses.PingPlayerClassSyncPacket;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.capability.Ping.IPingData;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class PingItem extends BasicAllClassItem {


    public PingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {

        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);

        if(pPlayer.getMainHandItem() != pStack && !Screen.hasShiftDown())
        {
            return InteractionResultHolder.pass(pStack);
        }



        if(canUseItem(pStack))
        {

            if (!pLevel.isClientSide())
            {
                pPlayer.getCooldowns().addCooldown(this, 10);
                ItemStack pingItemStack = pPlayer.getItemInHand(pUsedHand);
             //   pLevel.playSound(null, pPlayer.blockPosition(), ModSounds.MARK_SOUND.get(), SoundSource.PLAYERS, 1f, 1f);

                if (pingItemStack.hasTag()) {

                    if (pLevel.getEntity((int) ModUtils.ItemNBTHelper.getFloat(pingItemStack, "lootdebugs.pingitem.pingentity.id")) instanceof PingEntity PE) {
                        PE.kill();
                    } else if (pLevel.getEntity((int) ModUtils.ItemNBTHelper.getFloat(pingItemStack, "lootdebugs.pingitem.pingentity.id")) instanceof LivingEntity LE) {

                        LE.setGlowingTag(false);
                        ModUtils.PingClasses.setPlayerPingClass(LE, IPingData.PingClasses.None);
                        PacketHandler.sendToClients(new PingPlayerClassSyncPacket(LE.getId(), IPingData.PingClasses.None));
                    }
                }

                PingEntity pingEntity = new PingEntity(pPlayer, pLevel, pingItemStack);

                pingEntity.setOwner(pPlayer);
                pLevel.addFreshEntity(pingEntity);
                pingEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 10F, 0.0F);
                ModUtils.ItemNBTHelper.putFloat(pingItemStack, "lootdebugs.pingitem.pingentity.id", (int) pingEntity.getId());//save ping id in itemstack
            }
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
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
