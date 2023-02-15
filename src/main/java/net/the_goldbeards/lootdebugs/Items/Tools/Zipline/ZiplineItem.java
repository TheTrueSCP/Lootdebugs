package net.the_goldbeards.lootdebugs.Items.Tools.Zipline;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline.ZiplineBlock;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Items.Tools.BasicToolItem;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.util.function.Predicate;

public class ZiplineItem extends BasicToolItem
{

    public ZiplineItem(Properties pProperties)
    {
        super( pProperties);
    }

    public static final Predicate<ItemStack> ZIPLINE = (p_43017_) -> {
        return p_43017_.is(ModItems.SHOOT_ZIPLINE.get());
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

        if (!UsefullStuff.DwarfClasses.canPlayerUseItem(pUsedStack, pPlayer, getDwarfClassToUse())) {
            return InteractionResultHolder.pass(pUsedStack);
        }

        ItemStack zipline = getAmmo(pPlayer);

        if(pPlayer.isOnGround() && pLevel.isEmptyBlock(pPlayer.blockPosition()) && pLevel.isEmptyBlock(pPlayer.blockPosition().above(1)) && linkPos != null && canPlaceBlock(pLevel, linkPos))
        {
            if (!zipline.isEmpty() || pPlayer.isCreative())
            {
                pPlayer.getCooldowns().addCooldown(this, 28);

                //Shoots zipline entity with blocklink to current player pos
                ZiplineEntity ziplineEntity = new ZiplineEntity(pPlayer, pLevel, linkPos);
                ziplineEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 5.0F, 0.0F);
                pLevel.addFreshEntity(ziplineEntity);

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

    public static boolean canPlaceBlock(Level pLevel, BlockPos ziplineBlock)
    {

        BlockPos blockpos = ziplineBlock.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP);
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
