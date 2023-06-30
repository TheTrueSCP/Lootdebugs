package net.the_goldbeards.lootdebugs.Items.Tools.SatchelCharge;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.the_goldbeards.lootdebugs.Entities.Tools.SatchelChargeEntity;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class SatchelChargeItem extends BlockItem {

    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Driller;

    public SatchelChargeItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack stack = pPlayer.getItemInHand(pUsedHand);


        if (ModUtils.DwarfClasses.canPlayerUseItem(stack, pPlayer, dwarfClassToUse))
        {

            SatchelChargeEntity satchelCharge = new SatchelChargeEntity(pPlayer, pLevel);
            satchelCharge.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.2F, 0.0F);
            pLevel.addFreshEntity(satchelCharge);


            if (pPlayer.isCreative()) {
                return InteractionResultHolder.success(new ItemStack(ModItems.SATCHEL_CHARGE.get()));
            } else {
                return InteractionResultHolder.success(ItemStack.EMPTY);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
    {
        if (pEntity instanceof Player player && pIsSelected)
        {
            if (!ModUtils.DwarfClasses.canPlayerUseItem(pStack, player, dwarfClassToUse)) //TheTrueSCP
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("message.lootdebugs.tool.wrong_class_0").getString() + " " + ModUtils.DwarfClasses.getClassTranslate(dwarfClassToUse).getString() + " " + new TranslatableComponent("message.lootdebugs.tool.wrong_class_1").getString()), true);
            }
        }
    }

    //Blockplace
    @Override
    public InteractionResult place(BlockPlaceContext pContext) {

        Player pPlayer = pContext.getPlayer();
        ItemStack stack = pContext.getItemInHand();

        if (!ModUtils.DwarfClasses.canPlayerUseItem(stack, pPlayer, dwarfClassToUse))
        {
            return InteractionResult.FAIL;
        }

        if (!pContext.canPlace()) {
            return InteractionResult.FAIL;
        }
        else
        {
            BlockPlaceContext blockplacecontext = this.updatePlacementContext(pContext);
            if (blockplacecontext == null)
            {
                return InteractionResult.FAIL;
            }

            else
            {
                BlockState blockstate = this.getPlacementState(blockplacecontext);
                if (blockstate == null)
                {
                    return InteractionResult.FAIL;
                }

                else if (!this.placeBlock(blockplacecontext, blockstate))
                {
                    return InteractionResult.FAIL;
                }
                else
                {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    ItemStack itemstack = blockplacecontext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);

                    ItemStack detonator = new ItemStack(ModItems.SATCHEL_CHARGE_DETONATOR.get(), 1);
                    ModUtils.ItemNBTHelper.put(detonator, "satchel_charge", NbtUtils.writeBlockPos(pContext.getClickedPos()));
                    player.getInventory().add(detonator);

                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = this.updateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
                        this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, pContext.getPlayer());
                    level.playSound(player, blockpos, this.getPlaceSound(blockstate1, level, blockpos, pContext.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (player == null || !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }


    private BlockState updateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack pStack, BlockState pState) {
        BlockState blockstate = pState;
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null) {
            CompoundTag compoundtag1 = compoundtag.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> statedefinition = pState.getBlock().getStateDefinition();

            for(String s : compoundtag1.getAllKeys()) {
                Property<?> property = statedefinition.getProperty(s);
                if (property != null) {
                    String s1 = compoundtag1.get(s).getAsString();
                    blockstate = updateState(blockstate, property, s1);
                }
            }
        }

        if (blockstate != pState) {
            pLevel.setBlock(pPos, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState pState, Property<T> pProperty, String pValueIdentifier) {
        return pProperty.getValue(pValueIdentifier).map((p_40592_) -> {
            return pState.setValue(pProperty, p_40592_);
        }).orElse(pState);
    }
}
