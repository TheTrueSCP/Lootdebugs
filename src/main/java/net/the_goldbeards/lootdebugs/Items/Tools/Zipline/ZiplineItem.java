package net.the_goldbeards.lootdebugs.Items.Tools.Zipline;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicToolItem;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.util.function.Predicate;

public class ZiplineItem extends BasicToolItem
{

    public ZiplineItem(Properties pProperties)
    {
        super( pProperties);
    }

    public static final Predicate<ItemStack> ZIPLINE = (p_43017_) -> {
        return p_43017_.is(ModItems.ZIPLINE_AMMO.get());
    };

    @Override
    public IClassData.Classes getDwarfClassToUse() {
        return IClassData.Classes.Gunner;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        BlockPos linkPos = new BlockPos(pPlayer.blockPosition().getX(), pPlayer.blockPosition().getY(), pPlayer.blockPosition().getZ());

        ItemStack pUsedStack = pPlayer.getItemInHand(pUsedHand);

        if(pPlayer.getMainHandItem() != pUsedStack)
        {
            return InteractionResultHolder.pass(pUsedStack);
        }

        if(Screen.hasShiftDown())
        {
            return InteractionResultHolder.pass(pUsedStack);
        }

        if (!ModUtils.DwarfClasses.canPlayerUseItem(pUsedStack, pPlayer, getDwarfClassToUse())) {
            return InteractionResultHolder.pass(pUsedStack);
        }

        ItemStack zipline = getAmmo(pPlayer);


        if(pPlayer.isOnGround() &&  isZiplinePlaceable(pLevel, pPlayer.blockPosition()) && linkPos != null && isSolidGround(pLevel, linkPos))
        {
            if (!zipline.isEmpty() || pPlayer.isCreative())
            {
                pPlayer.getCooldowns().addCooldown(this, 28);

                //Shoots zipline entity with blocklink to current player pos
                ZiplineEntity ziplineEntity = new ZiplineEntity(pPlayer, pLevel, linkPos, pPlayer.getDirection().getOpposite());
                ziplineEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 9.0F, 0.0F);
                pLevel.addFreshEntity(ziplineEntity);

                ziplineEntity.setPos(ziplineEntity.getX(), ziplineEntity.getY() - 0.5f, ziplineEntity.getZ());

                pLevel.playSound(pPlayer, pPlayer, ModSounds.TOOL_SHOOT.get(), SoundSource.PLAYERS, 5, 1);

                if (!pPlayer.isCreative()) {
                    zipline.shrink(1);

                }

                if (zipline.isEmpty())
                {
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
            pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.not_on_ground"), true);
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    public static boolean isZiplinePlaceable(Level pLevel, BlockPos ziplinePos)
    {
        if((pLevel.isEmptyBlock(ziplinePos) && pLevel.isEmptyBlock(ziplinePos.above(1))))
        {
            return true;
        }

        if((pLevel.getBlockState(ziplinePos).is(ModTags.Blocks.REPLACEABLE_BLOCKS)) && pLevel.getBlockState(ziplinePos.above(1)).is(ModTags.Blocks.REPLACEABLE_BLOCKS))
        {
            return true;
        }

        return false;
    }

    public static boolean isSolidGround(Level pLevel, BlockPos ziplineBlock)
    {

        BlockPos blockpos = ziplineBlock.below();
        BlockState blockstateBelow = pLevel.getBlockState(blockpos);
        return !blockstateBelow.isAir();
       // return blockstateBelow.isFaceSturdy(pLevel, blockpos, Direction.UP);
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
