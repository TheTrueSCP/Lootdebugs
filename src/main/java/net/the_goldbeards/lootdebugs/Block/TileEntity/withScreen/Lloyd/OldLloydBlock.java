package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Lloyd;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class OldLloydBlock extends Block
{
    public OldLloydBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.block.old.lloyd"), true);

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
