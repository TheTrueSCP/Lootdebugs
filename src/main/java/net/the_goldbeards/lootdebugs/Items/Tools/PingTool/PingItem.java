package net.the_goldbeards.lootdebugs.Items.Tools.PingTool;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Entities.Tools.PingEntity;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.util.HelpfullStuff;

public class PingItem extends Item {


    public PingItem(Properties pProperties) {
        super(pProperties);
    }




    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {



        pPlayer.getCooldowns().addCooldown(this,10);
        ItemStack pingItemStack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.playSound(ModSounds.PING_MARK.get(), 12f,1f);



        if(pingItemStack.hasTag())
        {

                if(pLevel.getEntity((int) HelpfullStuff.ItemNBTHelper.getFloat(pingItemStack,"lootdebugs.pingitem.pingentity.id")) instanceof PingEntity PE)
                {
                    PE.kill();
                }

            else if(pLevel.getEntity((int) HelpfullStuff.ItemNBTHelper.getFloat(pingItemStack,"lootdebugs.pingitem.pingentity.id")) instanceof LivingEntity LE)
            {

                LE.removeEffect(MobEffects.GLOWING);

            }
        }

        PingEntity pingEntity = new PingEntity(pPlayer,pLevel, pingItemStack);

        pingEntity.setOwner(pPlayer);
        pLevel.addFreshEntity(pingEntity);
        pingEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 10F, 0.0F);
        HelpfullStuff.ItemNBTHelper.putFloat(pingItemStack,"lootdebugs.pingitem.pingentity.id", (int) pingEntity.getId());//save ping id in itemstack
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }



    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    protected static BlockHitResult getPlayerPOVHitResult(Level pLevel, Player pPlayer, ClipContext.Fluid pFluidMode) {
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        Vec3 vec3 = pPlayer.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 100;//Distance
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
    }
}
