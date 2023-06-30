package net.the_goldbeards.lootdebugs.Items.Tools.CompanyStandardPickaxe;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.ModUtils;

import java.util.List;

public class CompanyPickAxeItem extends PickaxeItem  {




    public CompanyPickAxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }





    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {


        if(!pPlayer.getCooldowns().isOnCooldown(this) && ModUtils.DwarfClasses.isPlayerDwarf(pPlayer))
        {
            BlockPos pos = pInteractionTarget.getOnPos();
            pInteractionTarget.hurt(DamageSource.GENERIC, 100);
            pPlayer.level.playSound(null, pos, ModSounds.POWERATTACK.get(), SoundSource.PLAYERS, 35,1);
            pPlayer.level.addParticle(ParticleTypes.CRIT, pos.getX(), pos.getY(),pos.getZ(),1,1,1);
            pPlayer.getCooldowns().addCooldown(this, 800);
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {



        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Block block = level.getBlockState(pos).getBlock();
        Player player = pContext.getPlayer();
        ItemStack itemstack = pContext.getItemInHand();
        CompanyPickAxeItem item = (CompanyPickAxeItem) pContext.getItemInHand().getItem();


        if(ModUtils.DwarfClasses.isPlayerDwarf(player))
        {

            if (block != Blocks.BEDROCK || block != Blocks.COMMAND_BLOCK)
            {
                if(level instanceof ServerLevel serverLevel)
                {
                    if(!player.isCreative())
                    {
                        player.getCooldowns().addCooldown(this, 800);
                    }
                    level.playSound(player,pos, ModSounds.POWERATTACK.get(), SoundSource.PLAYERS, 35,1);
                    level.addParticle(ParticleTypes.CRIT, pos.getX(), pos.getY(),pos.getZ(),1,1,1);

                    LootTable table = serverLevel.getServer().getLootTables().get(block.getLootTable());

                    LootContext ctx = new LootContext.Builder(serverLevel).withParameter(LootContextParams.THIS_ENTITY, player).withRandom(level.random).withLuck(4).withRandom(level.random).create(LootContextParamSet.builder().build());



                    List<ItemStack> stacks = table.getRandomItems(ctx);
                    for(ItemStack stack : stacks)
                    {
                            ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                            serverLevel.addFreshEntity(itemEntity);
                    }

                    level.destroyBlock(pos, false, player);
                    level.updateNeighborsAt(pos, level.getBlockState(pos).getBlock());

                    if (block == Blocks.OBSIDIAN) {
                        itemstack.hurtAndBreak(40, player, (p_41300_) -> {
                            p_41300_.broadcastBreakEvent(pContext.getHand());

                        });
                    } else {
                        itemstack.hurtAndBreak(20, player, (p_41300_) -> {
                            p_41300_.broadcastBreakEvent(pContext.getHand());

                        });
                    }
                }
            }
            return InteractionResult.SUCCESS;

        }


        return InteractionResult.PASS;

    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
    {
        if(pEntity instanceof Player player && pIsSelected)
        {
            if(!ModUtils.DwarfClasses.isPlayerDwarf(player))
            {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("message.lootdebugs.tool.no_dwarf").getString()), true);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return ModUtils.DwarfClasses.isPlayerDwarf(pPlayer);
    }
}
