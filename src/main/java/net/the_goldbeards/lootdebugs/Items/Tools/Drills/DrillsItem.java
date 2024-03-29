

package net.the_goldbeards.lootdebugs.Items.Tools.Drills;


import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ForgeHooks;
import net.the_goldbeards.lootdebugs.Items.Fuel.FuelDiggingItem;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrillsItem extends FuelDiggingItem
{
	public DrillsItem(Properties pProperties) {
		super(pProperties);
	}

	public static float fuelConsumeEachBlock = 1f;
	public static float fuelConsumeEachEntity = 2f;

	public static float maxTemp = 100;
	public static float minTemp = 0;
	public static float tempDecreaseAmount = 0.2f;
	public static float tempDecreaseAmountOverheated = tempDecreaseAmount / 2;
	public static float tempIncreaseAmount = 2f;

	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
	{
		//Have Fuel in Inv
		if(pEntity instanceof Player player)
		{
			ModUtils.ItemNBTHelper.putBoolean(pStack,"havefuelininventory", haveUseableFuel(pStack, player));

			if(pIsSelected)
			{
				if(!haveFuel(pStack))
				{
					player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("message.lootdebugs.tool.no_fuel").getString()), true);
				}
				else
				{
					if(isOverheated(pStack))
					{
						player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("message.lootdebugs.tool.temperature").getString() + " " + Math.round(ModUtils.ItemNBTHelper.getFloat(pStack, "temperature")) + "/" + maxTemp), true);
					}
					else {
						player.displayClientMessage(new TextComponent(new TranslatableComponent("message.lootdebugs.tool.temperature").getString() + " " + Math.round(ModUtils.ItemNBTHelper.getFloat(pStack, "temperature")) + "/" + maxTemp), true);
					}
				}
			}


			//Temperature Handling
			if(getTemp(pStack) >= maxTemp)
			{
				if(pLevel instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer)
				{
					serverLevel.playSound(null, player.blockPosition(), ModSounds.DRILLS_OVERHEAT.get(), SoundSource.PLAYERS, 1, 1);
					serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
				}


				ModUtils.ItemNBTHelper.putBoolean(pStack,"isoverheated", true);
			}

			if(isOverheated(pStack))
			{
				decreaseTemperature(pStack, tempDecreaseAmountOverheated);
			}
			else
			{
				decreaseTemperature(pStack, tempDecreaseAmount);

			}

			if(getTemp(pStack) <= minTemp)
			{
				ModUtils.ItemNBTHelper.putBoolean(pStack, "isoverheated", false);
			}
		}

		super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

		ItemStack stack = pPlayer.getItemInHand(pUsedHand);

		if(!canToolBeUsed(stack, pPlayer) || !canDrill(stack))
		{
			return InteractionResultHolder.pass(stack);
		}

		//Switch Between On Block Mode/Normal Mode

		boolean onBlockMode = stack.getOrCreateTag().getBoolean("onBlockMode");
		stack.getOrCreateTag().putBoolean("onBlockMode", !onBlockMode);
		if(onBlockMode)
		{
			pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.on_block_drill_mode_off"), true);
		}
		else
		{
			pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.tool.on_block_drill_mode_on"), true);

		}

		return super.use(pLevel, pPlayer, pUsedHand);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos ipos, Player player) {//check if the tool can Break extra blocks

		Level world = player.level;
		// early exit for client
		if(world.isClientSide|| !(player instanceof ServerPlayer))
		{
			return false;
		}
		

		HitResult mop = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);

		//Cancel if player have no fuel in inventory or is not the right dwarfclass

		if (!canToolBeUsed(stack, player))
		{
			return false;
		}

		if(!canDrill(stack) && !player.getAbilities().instabuild)
		{
			return false;
		}

		if (mop == null)
		{
			return false;
		}

		if(isNoBreakableBlock(world.getBlockState(ipos)))
		{
			return false;
		}

		if(stack.getOrCreateTag().getBoolean("onBlockMode"))
		{
			return false;
		}






		ImmutableList<BlockPos> additional = getExtraBlocksDug(world, player, mop);//Get all blocks which should be broken
		for(BlockPos pos : additional)//For every block destroy block
		{
			if(!world.hasChunkAt(pos))
				continue;
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			if(!state.isAir() && state.getDestroyProgress(player, world, pos) != 0)
			{

				if (stack.getItem() instanceof DrillsItem && canDrill(stack) && canToolBeUsed(stack, player)) {
					ModUtils.ItemNBTHelper.rotateDrills(world, stack);//Play Anim
				}

				int xpDropEvent = ForgeHooks.onBlockBreakEvent(world, ((ServerPlayer)player).gameMode.getGameModeForPlayer(), (ServerPlayer)player, pos);
				if(xpDropEvent < 0)
					continue;

				if(player.isCreative())//Creative Mode
				{
					if(block.onDestroyedByPlayer(state, world, pos, player, false, state.getFluidState()))
					{
						block.destroy(world, pos, state);
					}
				}
				else//Survival Mode
				{
					BlockEntity te = world.getBlockEntity(pos);

					stack.mineBlock(world, state, pos, player);
					if(block.onDestroyedByPlayer(state, world, pos, player, true, state.getFluidState()))
					{
						block.destroy(world, pos, state);
						block.playerDestroy(world, player, pos, state, te, stack);


						if(world instanceof ServerLevel)
							block.popExperience((ServerLevel)world, pos, xpDropEvent);

					}
				}

				world.levelEvent(2001, pos, Block.getId(state));
				((ServerPlayer)player).connection.send(new ClientboundBlockUpdatePacket(world, pos));
			}
		}

		return false;

	}

	@Override
	public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity living)
	{
		if(state.getDestroySpeed(world, pos) != 0)
		{
			if(!stack.isEmpty())
			{
				if(living instanceof Player)
				{
					if(((Player)living).getAbilities().instabuild)
					{
						return true;
					}
					((DrillsItem)stack.getItem()).afterBlockbreak(stack, (Player)living, pos, world);
				}
				if(living instanceof Player player)
				{
					consumeFuel(player, FUEL, fuelConsumeEachBlock);//Consume fuel for every block which mined
					riseTemperature(stack, tempIncreaseAmount);
				}
			}
		}

		return true;
	}


	public void afterBlockbreak(ItemStack pStack, Player player, BlockPos breakPos, Level level)
	{
//Calls after a block was broken by the drills
	}


	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity)
	{
		if(canDrill(stack))
		{
			consumeFuel(player,FUEL , fuelConsumeEachEntity);
			entity.hurt(DamageSource.playerAttack(player), 4);
			return true;
		}
		else
		{
			return false;
		}
	}

	public void riseTemperature(ItemStack pStack, float increaseAmount)
	{
		float oldTemp = ModUtils.ItemNBTHelper.getFloat(pStack, "temperature");
		float newTemp = 0;
		if(oldTemp + increaseAmount > maxTemp)
		{
			newTemp = maxTemp;
		}
		else
		{
			newTemp = oldTemp + increaseAmount;
		}

		ModUtils.ItemNBTHelper.putFloat(pStack, "temperature", newTemp);
	}

	public void decreaseTemperature(ItemStack pStack, float decreaseAmount)
	{
		float oldTemp = ModUtils.ItemNBTHelper.getFloat(pStack, "temperature");
		float newTemp = 0;
		if(oldTemp - decreaseAmount < minTemp)
		{
			newTemp = minTemp;
		}
		else
		{
			newTemp = oldTemp - decreaseAmount;
		}

		ModUtils.ItemNBTHelper.putFloat(pStack, "temperature", newTemp);
	}

	//calculate the blocks
	public ImmutableList<BlockPos> getExtraBlocksDug(Level world, Player player, HitResult rtr)
	{
		if(!(rtr instanceof BlockHitResult brtr))
			return ImmutableList.of();
		Direction side = brtr.getDirection();
		int diameter = 2;
		int depth = 1;

		BlockPos startPos = brtr.getBlockPos();
		BlockState state = world.getBlockState(startPos);
		float maxHardness = 1;
		if(!state.isAir())
			maxHardness = state.getDestroyProgress(player, world, startPos)*0.4F;
		if(maxHardness < 0)
			maxHardness = 0;

		if(diameter%2==0)//even numbers
		{
			float hx = (float)brtr.getLocation().x-brtr.getBlockPos().getX();
			float hy = (float)brtr.getLocation().y-brtr.getBlockPos().getY();
			float hz = (float)brtr.getLocation().z-brtr.getBlockPos().getZ();
			if((side.getAxis()== Direction.Axis.Y&&hx < .5)||(side.getAxis()== Direction.Axis.Z&&hx < .5))
				startPos = startPos.offset(-diameter/2, 0, 0);
			if(side.getAxis()!= Direction.Axis.Y&&hy < .5)
				startPos = startPos.offset(0, -diameter/2, 0);
			if((side.getAxis()== Direction.Axis.Y&&hz < .5)||(side.getAxis()== Direction.Axis.X&&hz < .5))
				startPos = startPos.offset(0, 0, -diameter/2);
		}
		else//odd numbers
			startPos = startPos.offset(-(side.getAxis()== Direction.Axis.X?0: diameter/2), -(side.getAxis()== Direction.Axis.Y?0: diameter/2), -(side.getAxis()== Direction.Axis.Z?0: diameter/2));
		ImmutableList.Builder<BlockPos> b = ImmutableList.builder();
		for(int dd = 0; dd < depth; dd++)
			for(int dw = 0; dw < diameter; dw++)
				for(int dh = 0; dh < diameter; dh++)
				{
					BlockPos pos = startPos.offset((side.getAxis()== Direction.Axis.X?dd: dw), (side.getAxis()== Direction.Axis.Y?dd: dh), (side.getAxis()== Direction.Axis.Y?dh: side.getAxis()== Direction.Axis.X?dw: dd));
					if(pos.equals(brtr.getBlockPos()))
						continue;
					state = world.getBlockState(pos);
					if(state.isAir())
						continue;
					Block block = state.getBlock();
					float h = state.getDestroyProgress(player, world, pos);
					boolean canHarvest = block.canHarvestBlock(world.getBlockState(pos), world, pos, player);
					boolean drillMat = !state.is(ModTags.Blocks.NOT_MINEABLE_WITH_DRILLS);
					boolean hardness = h >= maxHardness;
					if(canHarvest&&drillMat&&hardness)
						b.add(pos);
				}
		return b.build();
	}

	@Override
	public float getDestroySpeed(ItemStack pStack, BlockState pState) {

		//If the block is breakable, the drills can be used and have fuel
		if(canDrill(pStack) && canToolBeUsed(pStack) && !isNoBreakableBlock(pState))
		{
			return 30;//The drills can mine the block
		}

		//The drill cant drill
		return 0;
	}

	@Override
	public Tier getHarvestLevel(ItemStack stack, @Nullable Player player) {
		if(canDrill(stack) && canToolBeUsed(stack))
			return Tiers.NETHERITE;
		return null;
	}

	private boolean haveFuel(ItemStack pStack)
	{
		return ModUtils.ItemNBTHelper.getBoolean(pStack,"havefuelininventory");
	}

	private boolean canDrill(ItemStack pStack)
	{
		boolean haveFuel = haveFuel(pStack);
		boolean isOverheated = ModUtils.ItemNBTHelper.getBoolean(pStack,"isoverheated");

		return haveFuel && !isOverheated;
	}

	private boolean isNoBreakableBlock(BlockState block)
	{
		return block.is(ModTags.Blocks.NOT_MINEABLE_WITH_DRILLS);
	}


	//Do not use this, use haveFuel Instead
	private boolean haveUseableFuel(ItemStack pStack, Player pPlayer)
	{
		if(pStack.getItem() instanceof DrillsItem)
		{
			if(!getUsableInventoryFuel(pPlayer, FUEL).isEmpty())
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

		pTooltipComponents.add(new TranslatableComponent("tooltip.lootdebugs.drills.on_block_drill_mode"));

		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return true;
	}


	@Override
	public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
		return false;
	}

	//Disable Reequipanim
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	public boolean canToolBeUsed(ItemStack pUsedStack, Player pPlayer)
	{
		return ModUtils.DwarfClasses.canPlayerUseItem(pUsedStack, pPlayer, getDwarfClassToUse());
	}

	/**
	 * only use when you already saved the playerclass into the stack == if you called canToolBeUsed(ItemStack pUsedStack, Player pPlayer)
	 */
	public boolean canToolBeUsed(ItemStack pUsedStack)
	{
		return ModUtils.DwarfClasses.canItemBeUsed(pUsedStack, getDwarfClassToUse());
	}


	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

	@Override
	public IClassData.Classes getDwarfClassToUse()
	{
		return IClassData.Classes.Driller;
	}

	public float getTemp(ItemStack pStack)
	{
		return ModUtils.ItemNBTHelper.getFloat(pStack, "temperature");
	}

	public boolean isOverheated(ItemStack pStack)
	{
		return ModUtils.ItemNBTHelper.getBoolean(pStack,"isoverheated");
	}

}
