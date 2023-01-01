package net.the_goldbeards.lootdebugs.Items.Tools.Zipline;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.util.function.Predicate;

public class ZiplineItem extends Item
{
    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Gunner;

    public ZiplineItem(Properties pProperties)
    {
        super( pProperties);
    }

    public static final Predicate<ItemStack> ZIPLINE = (p_43017_) -> {
        return p_43017_.is(ModItems.SHOOT_ZIPLINE.get());
    };

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
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

                UsefullStuff.ItemNBTHelper.putString(pStack,"flare_gun_dwarfclass", classCap.getDwarfClass().name());//Write every tick the Playerclass into the item


            });

        }

        if(pEntity instanceof Player player && pIsSelected)
        {
            if(!UsefullStuff.ItemNBTHelper.getString(pStack, "flare_gun_dwarfclass").equals(dwarfClassToUse.name())) //TheTrueSCP
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + UsefullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString() + " " + new TranslatableComponent("tool.wrong_class_after").getString()), true);
            }

        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        BlockPos linkPos = new BlockPos(pPlayer.blockPosition().getX(), pPlayer.blockPosition().getY(), pPlayer.blockPosition().getZ());

        ItemStack pUsedStack = pPlayer.getItemInHand(pUsedHand);

        if (!UsefullStuff.ItemNBTHelper.getString(pUsedStack, "flare_gun_dwarfclass").equals(dwarfClassToUse.name())) {
            return InteractionResultHolder.pass(pUsedStack);
        }

        ItemStack zipline = getAmmo(pPlayer);

        if(pPlayer.isOnGround())
        {
            if (!zipline.isEmpty() || pPlayer.isCreative())
            {
                pPlayer.getCooldowns().addCooldown(this, 28);

                ZiplineEntity ziplineEntity = new ZiplineEntity(pPlayer, pLevel, linkPos.above(1));
                ziplineEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 5.0F, 0.0F);
                pLevel.addFreshEntity(ziplineEntity);

                pLevel.playSound(pPlayer, pPlayer, ModSounds.TOOL_SHOOT.get(), SoundSource.PLAYERS, 5, 1);

                if (!pPlayer.isCreative()) {
                    zipline.shrink(1);

                }

                if (zipline.isEmpty()) {
                    pPlayer.getInventory().removeItem(zipline);
                }
            }
            else
            {
                pPlayer.getInventory().removeItem(zipline);
                pPlayer.playSound(SoundEvents.DISPENSER_FAIL, 1, 1);
            }
        }
        else
        {
            pPlayer.displayClientMessage(new TranslatableComponent("tool.not_on_ground"), true);
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    public ItemStack getAmmo(Player player) {
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack1 = player.getInventory().getItem(i);
            if (ZIPLINE.test(itemstack1))
            {
                return itemstack1;
            }
        }

        return ItemStack.EMPTY;
    }
}
