package net.the_goldbeards.lootdebugs.Items.Tools.Drills;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

//Old Drills, use the DrillsItem instead
@Deprecated(forRemoval = true, since = "1.5")
public class DrillsOldItem extends Item
{


    public static int rangeInBlocks = 150;

    public static final Predicate<ItemStack> FUEL = (p_43017_) -> {
        return p_43017_.is(ModItems.FUEL_CANISTER.get());
    };

    public DrillsOldItem(Properties pProperties) {
        super( pProperties);
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
    public InteractionResult useOn(UseOnContext pContext) {

        Player player = pContext.getPlayer();
        BlockPos posBlock = pContext.getClickedPos();
        BlockPos posBlockDown = pContext.getClickedPos().below();
        Level level = pContext.getLevel();
        Block block = level.getBlockState(posBlock).getBlock();
        Block blockDown = level.getBlockState(posBlockDown).getBlock();

        ItemStack fuel = getFuel(player);
        ItemStack pStack = player.getItemInHand(player.getUsedItemHand());






            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putString("drills_dwarfclass", classCap.getDwarfClass().name());
                pStack.setTag(compoundTag);

            });

            if(pStack.getTag().getInt("drills_dwarfclass") != 1)
            {
                return InteractionResult.PASS;
            }


if(!level.isClientSide) {
    if (pStack.getOrCreateTag().contains("lootdebugs.drillsrot")) {
        float i = pStack.getOrCreateTag().getFloat("lootdebugs.drillsrot");
        i += 0.25f;
        pStack.getOrCreateTag().putFloat("lootdebugs.drillsrot", i);

        if (pStack.getOrCreateTag().getFloat("lootdebugs.drillsrot") >= 1f) {
            pStack.getOrCreateTag().putFloat("lootdebugs.drillsrot", 0);
        }
    } else if (!pStack.getOrCreateTag().contains("lootdebugs.drillsrot")) {
        pStack.getOrCreateTag().putFloat("lootdebugs.drillsrot", 0.0f);
    }
}

        if(level.isClientSide) {
            if (pStack.getOrCreateTag().contains("lootdebugs.drillsrot")) {
                float i = pStack.getOrCreateTag().getFloat("lootdebugs.drillsrot");
                i += 0.25f;
                pStack.getOrCreateTag().putFloat("lootdebugs.drillsrot", i);

                if (pStack.getOrCreateTag().getFloat("lootdebugs.drillsrot") >= 1f) {
                    pStack.getOrCreateTag().putFloat("lootdebugs.drillsrot", 0);
                }
            } else if (!pStack.getOrCreateTag().contains("lootdebugs.drillsrot")) {
                pStack.getOrCreateTag().putFloat("lootdebugs.drillsrot", 0.0f);
            }
        }




                    if (player.isCrouching()) {
                        if (pStack.hasTag()) {
                            boolean oldValue = pStack.getTag().getBoolean("lootdebugs.drills.fuel.ondrillmode");
                            pStack.getTag().putBoolean("lootdebugs.drills.fuel.ondrillmode", !oldValue);
                        } else {
                            createTag(pStack);
                        }
                    }

                    if (!player.isCrouching() && (!fuel.isEmpty() || player.isCreative()) && !ModUtils.BlockHelpers.isBlockTag(block,ModTags.Blocks.NOT_MINEABLE_WITH_DRILLS))
                    //If the block is not Mineable and the fuel is in the inventory or the player is in creative mode
                    {


                        //Break Block
                        level.destroyBlock(posBlock, true);
                        level.updateNeighborsAt(posBlock, block);


                        if (pStack.hasTag()) {

                            if (!pStack.getTag().getBoolean("lootdebugs.drills.fuel.ondrillmode")) {
                                //Break Block below
                                level.destroyBlock(posBlockDown, true);
                                level.updateNeighborsAt(posBlockDown, blockDown);
                            }

                        } else {
                            createTag(pStack);
                        }


                        level.playSound(player, posBlock, ModSounds.DRILLS.get(), SoundSource.BLOCKS, 0.5f, 1);


                        if (!player.isCreative())//If the player is in creative mode, the drills should not loose fuel
                        {
                            float fuelConsum = 1f;

                            if (pStack.hasTag()) {

                                if (pStack.getTag().getBoolean("lootdebugs.drills.fuel.ondrillmode")) {
                                    fuelConsum = 0.5f;
                                } else {
                                    fuelConsum = 1f;
                                }
                            } else {
                                createTag(pStack);
                            }

                            int maxDamage = fuel.getMaxDamage();
                            fuel.hurtAndBreak((int) ((maxDamage / rangeInBlocks) * fuelConsum), player, (p_41300_) -> { //Calculate the fuel consum to range and max damage
                                p_41300_.broadcastBreakEvent(pContext.getHand());

                            });
                        }
                    }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {


        pTooltipComponents.add(new TranslatableComponent("on_block_drill_mode"));

        if(pStack.hasTag())
        {
            pTooltipComponents.add( new TextComponent("" + pStack.getTag().getBoolean("lootdebugs.drills.fuel.ondrillmode")));
        }
        else
        {createTag(pStack);}

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public ItemStack getFuel(Player player) {
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack1 = player.getInventory().getItem(i);
            if (FUEL.test(itemstack1)) {
                return itemstack1;
            }
        }

        return ItemStack.EMPTY;
    }






    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);

        if(!pLevel.isClientSide()) {
            if (pPlayer.isCrouching()) {
                if (pStack.hasTag()) {
                    boolean oldValue = pStack.getTag().getBoolean("lootdebugs.drills.fuel.ondrillmode");
                    pStack.getTag().putBoolean("lootdebugs.drills.fuel.ondrillmode", !oldValue);
                } else {
                    createTag(pStack);
                }
            }
        }




        return super.use(pLevel, pPlayer, pUsedHand);
    }


    public void createTag(ItemStack stack)
    {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("lootdebugs.drills.fuel.ondrillmode",false);//Create fuel with defaut values

        stack.setTag(nbt);
    }

}