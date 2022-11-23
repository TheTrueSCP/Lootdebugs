package net.the_goldbeards.lootdebugs.Items.Drinks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.the_goldbeards.lootdebugs.init.ModEffects;
import net.the_goldbeards.lootdebugs.init.ModItems;

import javax.annotation.Nullable;

public abstract class BeerItem extends BlockItem
{
    public BeerItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {

        if (!worldIn.isClientSide)
        {
          addEffects(entityLiving);
          if(addDrunkness()) {//If add drunknes, add drunkness
              addDrunkness(entityLiving);
          }
        }
        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof Player && !((Player) entityLiving).getAbilities().invulnerable) {
            stack.shrink(1);
        }

        return stack.isEmpty() ? new ItemStack(ModItems.MUG.get()) : stack;


    }

    public boolean addDrunkness()
    {
        return true;
    }

    public int getDrunkenAmplifier(LivingEntity livingEntity)
    {
        if(!livingEntity.hasEffect(ModEffects.DRUNKNESS.get()))
        {
            return -1;//Safty: IF the player hasnt the effect, return -1
        }
        return livingEntity.getEffect(ModEffects.DRUNKNESS.get()).getAmplifier();//instead, return the Amp
    }
    public void addDrunkness(LivingEntity entityLiving)
    {
        entityLiving.addEffect(new MobEffectInstance(ModEffects.DRUNKNESS.get(),6000,getDrunkenAmplifier(entityLiving) + 1));//Add drunkness and increase the amp for confusion
    }

    public void addEffects(LivingEntity entityLiving)
    {
        //Overrideable void to add effects
    }


    //Default Beer Stuff
    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return  42;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }


    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player,hand);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @javax.annotation.Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
    }

    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    public static void explode(Level pLevel, BlockPos pPos, @Nullable LivingEntity pEntity) {
        if (!pLevel.isClientSide) {
            PrimedTnt primedtnt = new PrimedTnt(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pEntity);
            primedtnt.setFuse(0);
            pLevel.addFreshEntity(primedtnt);
            pLevel.playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            pLevel.gameEvent(pEntity, GameEvent.PRIME_FUSE, pPos);
        }
    }

    //Blockplace
    @Override
    public InteractionResult place(BlockPlaceContext pContext) {

        if(!pContext.getPlayer().isCrouching())
        {
            return InteractionResult.PASS;
        }
        if (!pContext.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = this.updatePlacementContext(pContext);
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = this.getPlacementState(blockplacecontext);
                if (blockstate == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockplacecontext, blockstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    ItemStack itemstack = blockplacecontext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);
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
