package net.the_goldbeards.lootdebugs.Items.Tools.CompanyStandardPickaxe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;

public class CompanyPickAxeItem extends PickaxeItem  {




    public CompanyPickAxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }





    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {


        if(!pPlayer.getCooldowns().isOnCooldown(this))
        {
            BlockPos pos = pInteractionTarget.getOnPos();
            pInteractionTarget.hurt(DamageSource.GENERIC, 10);
            pPlayer.level.playSound(pPlayer,pos, ModSounds.POWERATTACK.get(), SoundSource.PLAYERS, 35,1);
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



                if (block != Blocks.BEDROCK || block != Blocks.COMMAND_BLOCK) {
                    player.getCooldowns().addCooldown(this,800);
                    level.playSound(player,pos, ModSounds.POWERATTACK.get(), SoundSource.PLAYERS, 35,1);
                    level.addParticle(ParticleTypes.CRIT, pos.getX(), pos.getY(),pos.getZ(),1,1,1);
                    level.destroyBlock(pos, true);
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
                    return InteractionResult.SUCCESS;

                }


        return InteractionResult.PASS;

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
}
