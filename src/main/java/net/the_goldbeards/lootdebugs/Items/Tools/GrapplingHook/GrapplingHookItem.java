package net.the_goldbeards.lootdebugs.Items.Tools.GrapplingHook;

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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Entities.Projectiles.GrapplingHookHookEntity;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.HelpfullStuff;

public class GrapplingHookItem extends Item
{


    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Scout;

    public GrapplingHookItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public int getUseDuration(ItemStack pStack) {
        return Integer.MAX_VALUE;
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

                HelpfullStuff.ItemNBTHelper.putString(pStack,"grapplinghook_dwarfclass", classCap.getDwarfClass().name());//Write every tick the Playerclass into the item


            });

        }

        if(pEntity instanceof Player player && pIsSelected)
        {
            if(!HelpfullStuff.ItemNBTHelper.getString(pStack, "grapplinghook_dwarfclass").equals(dwarfClassToUse.name())) //TheTrueSCP
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + HelpfullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString()), true);
            }

        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged)
    {
        if(pStack.hasTag())
        {
            if(pLevel.getEntity((int) HelpfullStuff.ItemNBTHelper.getFloat(pStack,"lootdebugs.grapplinghook.grapplinghookhookentity.id")) != null)
            {
                if(pLivingEntity instanceof Player player)
                {
                    if(!player.isCreative())
                    {
                        player.getCooldowns().addCooldown(this, 60);//Add cooldown when release hook
                    }
                }

                pLevel.getEntity((int) HelpfullStuff.ItemNBTHelper.getFloat(pStack,"lootdebugs.grapplinghook.grapplinghookhookentity.id")).kill();

            }
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }



    @Override
    public void onUseTick(Level pLevel, LivingEntity pEntity, ItemStack pStack, int pRemainingUseDuration) {

        if (pStack.hasTag()) {
            assert pStack.getTag() != null;
            if (pLevel.getEntity((int) HelpfullStuff.ItemNBTHelper.getFloat(pStack,"lootdebugs.grapplinghook.grapplinghookhookentity.id")) instanceof GrapplingHookHookEntity GH)
            {

                if (GH.position().distanceTo(pEntity.position()) > (100 * 2)) //Block Range * 2 cause 1 vec is a half block
                {
                        GH.kill();
                }

                else if (GH.inGround && GH.position().distanceTo(pEntity.position()) > 1)
                {
                        Vec3 vec3 = new Vec3((pEntity.getX() - GH.getX()) * -1, (pEntity.getY() - GH.getY()) * -1, (pEntity.getZ() - GH.getZ()) * -1);//calculate Movement from Entity to hook
                        pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(vec3).normalize());//Normalize Vec cause the vec would be to fast
                        pEntity.fallDistance = 0;
                        pEntity.resetFallDistance();
                }
                else if(GH.position().distanceTo(pEntity.position()) < 1)
                {
                    pEntity.setDeltaMovement(0,0,0);
                }


            }
        }


        super.onUseTick(pLevel, pEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pEntity, InteractionHand pUsedHand) {
        ItemStack pingItemStack = pEntity.getItemInHand(pUsedHand);
        ItemStack pUsedStack = pEntity.getItemInHand(pUsedHand);


        //ClassStuff

        if (!pUsedStack.getTag().getString("grapplinghook_dwarfclass").equals(dwarfClassToUse.name())) {
            return InteractionResultHolder.pass(pUsedStack);
        }



        //Summon Hook and save his id
        GrapplingHookHookEntity grapplingHookHookEntity = new GrapplingHookHookEntity(pLevel, pEntity);

        grapplingHookHookEntity.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, 12.0F, 0.0F);
        grapplingHookHookEntity.setOwner(pEntity);
        pLevel.addFreshEntity(grapplingHookHookEntity);



        HelpfullStuff.ItemNBTHelper.putFloat(pingItemStack,"lootdebugs.grapplinghook.grapplinghookhookentity.id", grapplingHookHookEntity.getId());//Create fuel with defaut values

        pEntity.startUsingItem(pUsedHand);


        return InteractionResultHolder.consume(pEntity.getItemInHand(pUsedHand));
    }




    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }


}
