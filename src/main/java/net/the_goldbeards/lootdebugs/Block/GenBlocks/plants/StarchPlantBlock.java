package net.the_goldbeards.lootdebugs.Block.GenBlocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class StarchPlantBlock extends BushBlock implements BonemealableBlock
{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }


    private static final VoxelShape N_PICKED =Shapes.or(
            Block.box(4.3636023236504595, 13.75, 4.351788323650457, 11.863602323650458, 18.25, 11.851788323650458),
            Block.box(5.1136023236504595, 4.75, 5.1017883236504575, 11.113602323650458, 15.25, 11.101788323650458),
            Block.box(4.3636023236504595, 3.25, 4.351788323650457, 11.863602323650458, 6.25, 11.851788323650458),
            Block.box(2.8636023236504595, 0.25, 2.8517883236504566, 13.363602323650458, 4.75, 13.351788323650458),
            Block.box(5.154532323650459, 19.16969, 16.50178832365046, 11.154532323650457, 22.16969, 22.50178832365046),
            Block.box(6.6136023236504595, 17.461779351950906, 19.674501980971513, 9.613602323650458, 21.961779351950906, 22.674501980971513),
            Block.box(6.263602323650458, 19.74133, 18.10178832365046, 9.76360232365046, 21.24133, 21.601788323650453),
            Block.box(6.6136023236504595, 24.23637038537086, 11.547539103566281, 9.613602323650458, 27.23637038537086, 16.04753910356628),
            Block.box(6.6136023236504595, 22.144480703299724, 11.992397317749619, 9.613602323650458, 25.144480703299724, 19.49239731774962),
            Block.box(6.6136023236504595, 14.433025873566507, 6.243650046721817, 9.613602323650458, 21.933025873566507, 9.243650046721816)
    );
    private static final VoxelShape E_PICKED =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(-6.4, 19.16969, 5.04093, -0.4, 22.16969, 11.04093),
            Block.box(-4.087, 20.1517, 6.5, -1.087, 24.6517, 9.5),
            Block.box(-5.5, 19.74133, 6.15, -2, 21.24133, 9.65),
            Block.box(-3.5, 23.8505, 6.5, 1, 26.8505, 9.5),
            Block.box(-0.765, 21.435, 6.5, 6.735, 24.435, 9.5),
            Block.box(7, 16.55, 6.5, 10, 24.05, 9.5)
    );
    private static final VoxelShape S_PICKED =Shapes.or(
            Block.box(4.351788323650458, 13.75, 4.136397676349542, 11.851788323650458, 18.25, 11.636397676349542),
            Block.box(5.101788323650458, 4.75, 4.886397676349542, 11.101788323650458, 15.25, 10.886397676349542),
            Block.box(4.351788323650458, 3.25, 4.136397676349542, 11.851788323650458, 6.25, 11.636397676349542),
            Block.box(2.8517883236504584, 0.25, 2.6363976763495423, 13.351788323650458, 4.75, 13.136397676349542),
            Block.box(5.060858323650459, 19.16969, -6.513602323650459, 11.060858323650457, 22.16969, -0.5136023236504581),
            Block.box(6.601788323650458, 17.461779351950906, -6.686315980971513, 9.601788323650458, 21.961779351950906, -3.6863159809715143),
            Block.box(6.451788323650458, 19.74133, -5.613602323650457, 9.951788323650458, 21.24133, -2.1136023236504577),
            Block.box(6.601788323650458, 24.23637038537086, -0.05935310356628154, 9.601788323650458, 27.23637038537086, 4.440646896433719),
            Block.box(6.601788323650458, 22.144480703299724, -3.5042113177496192, 9.601788323650458, 25.144480703299724, 3.9957886822503816),
            Block.box(6.601788323650458, 14.433025873566507, 6.744535953278183, 9.601788323650458, 21.933025873566507, 9.744535953278183)
    );

    private static final VoxelShape W_PICKED =Shapes.or(
            Block.box(4.465390647300916, 13.75, 4.238186, 11.965390647300918, 18.25, 11.738185999999999),
            Block.box(5.215390647300917, 4.75, 4.988186, 11.215390647300918, 15.25, 10.988185999999999),
            Block.box(4.465390647300916, 3.25, 4.238186, 11.965390647300918, 6.25, 11.738185999999999),
            Block.box(2.965390647300916, 0.25, 2.738186, 13.465390647300918, 4.75, 13.238185999999999),
            Block.box(16.615390647300917, 19.16969, 4.947256, 22.61539064730092, 22.16969, 10.947256),
            Block.box(19.788104304621974, 17.461779351950906, 6.488186, 22.788104304621974, 21.961779351950906, 9.488185999999999),
            Block.box(18.215390647300918, 19.74133, 6.338185999999999, 21.715390647300914, 21.24133, 9.838186),
            Block.box(11.66114142721674, 24.23637038537086, 6.488186, 16.16114142721674, 27.23637038537086, 9.488185999999999),
            Block.box(12.105999641400079, 22.144480703299724, 6.488186, 19.60599964140008, 25.144480703299724, 9.488185999999999),
            Block.box(6.357252370372277, 14.433025873566507, 6.488186, 9.357252370372276, 21.933025873566507, 9.488185999999999)
    );



    //Grown
    private static final VoxelShape N_GROWN =Shapes.or(
            Block.box(4.2809430118415985, 13.75, 4.397426340869751, 11.780943011841599, 18.25, 11.897426340869751),
            Block.box(5.0309430118415985, 4.75, 5.147426340869751, 11.030943011841599, 15.25, 11.147426340869751),
            Block.box(4.2809430118415985, 3.25, 4.397426340869751, 11.780943011841599, 6.25, 11.897426340869751),
            Block.box(2.7809430118415985, 0.25, 2.8974263408697514, 13.280943011841599, 4.75, 13.397426340869751),
            Block.box(4.155943011841597, 12.25, 17.297426340869755, 5.655943011841597, 19.75, 21.797426340869755),
            Block.box(10.155943011841597, 12.25, 17.297426340869755, 11.655943011841597, 19.75, 21.797426340869755),
            Block.box(5.655943011841597, 12.25, 21.797426340869755, 10.155943011841597, 19.75, 23.297426340869755),
            Block.box(5.655943011841597, 12.25, 15.797426340869755, 10.155943011841597, 19.75, 17.297426340869755),
            Block.box(5.655943011841597, 10.75, 17.297426340869755, 10.155943011841597, 13.75, 21.797426340869755),
            Block.box(6.405943011841597, 9.25, 18.047426340869755, 9.405943011841597, 12.25, 21.047426340869755),
            Block.box(5.0718763279542145, 19.16968889462913, 16.547426340869748, 11.071876327954215, 22.16968889462913, 22.547426340869748),
            Block.box(3.5718763279542163, 16.16968889462913, 15.047426340869752, 12.571876327954215, 20.66968889462913, 24.047426340869748),
            Block.box(6.5309430118415985, 15.485700765515553, 19.691586436326737, 9.530943011841599, 19.985700765515553, 22.69158643632673),
            Block.box(6.5309430118415985, 26.072956435158233, 10.863344415642052, 9.530943011841599, 29.072956435158233, 15.363344415642048),
            Block.box(6.5309430118415985, 20.16840052937773, 12.009481339142264, 9.530943011841599, 23.16840052937773, 19.509481339142262),
            Block.box(6.5309430118415985, 16.269612601085427, 5.559454344500649, 9.530943011841599, 23.769612601085427, 8.559454344500649)
    );

    private static final VoxelShape E_GROWN =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(-5.650000000000004, 12.25, 4.124999999999999, -1.1500000000000035, 19.75, 5.624999999999998),
            Block.box(-5.650000000000004, 12.25, 10.124999999999998, -1.1500000000000035, 19.75, 11.624999999999998),
            Block.box(-7.150000000000004, 12.25, 5.624999999999998, -5.650000000000004, 19.75, 10.124999999999998),
            Block.box(-1.1500000000000035, 12.25, 5.624999999999998, 0.3499999999999964, 19.75, 10.124999999999998),
            Block.box(-5.650000000000004, 10.75, 5.624999999999998, -1.1500000000000035, 13.75, 10.124999999999998),
            Block.box(-4.900000000000004, 9.25, 6.374999999999998, -1.9000000000000035, 12.25, 9.374999999999998),
            Block.box(-6.4, 19.16968889462913, 5.040933316112616, -0.4, 22.16968889462913, 11.040933316112616),
            Block.box(-7.9, 16.16968889462913, 3.5409333161126173, 1.1, 20.66968889462913, 12.540933316112616),
            Block.box(-4.087, 20.151699999999998, 6.5, -1.0870000000000033, 24.651699999999998, 9.5),
            Block.box(-5.500000000000002, 19.741327492162988, 6.15, -2.0000000000000013, 21.241327492162988, 9.65),
            Block.box(-3.5, 23.850499999999997, 6.5, 0.9999999999999964, 26.850499999999997, 9.5),
            Block.box(-0.7650000000000006, 21.435, 6.5, 6.734999999999999, 24.435, 9.5),
            Block.box(7, 16.549999999999997, 6.5, 10, 24.049999999999997, 9.5)
    );
    private static final VoxelShape S_GROWN =Shapes.or(
            Block.box(4.397426340869751, 13.75, 4.2190569881584015, 11.897426340869751, 18.25, 11.719056988158401),
            Block.box(5.147426340869751, 4.75, 4.9690569881584015, 11.147426340869751, 15.25, 10.969056988158401),
            Block.box(4.397426340869751, 3.25, 4.2190569881584015, 11.897426340869751, 6.25, 11.719056988158401),
            Block.box(2.8974263408697514, 0.25, 2.7190569881584015, 13.397426340869751, 4.75, 13.219056988158401),
            Block.box(10.522426340869753, 12.25, -5.680943011841602, 12.022426340869753, 19.75, -1.1809430118416024),
            Block.box(4.522426340869753, 12.25, -5.680943011841602, 6.022426340869753, 19.75, -1.1809430118416024),
            Block.box(6.022426340869753, 12.25, -7.180943011841602, 10.522426340869753, 19.75, -5.680943011841602),
            Block.box(6.022426340869753, 12.25, -1.1809430118416024, 10.522426340869753, 19.75, 0.31905698815839756),
            Block.box(6.022426340869753, 10.75, -5.680943011841602, 10.522426340869753, 13.75, -1.1809430118416024),
            Block.box(6.772426340869753, 9.25, -4.930943011841602, 9.772426340869753, 12.25, -1.9309430118416024),
            Block.box(5.106493024757135, 19.16968889462913, -6.430943011841595, 11.106493024757135, 22.16968889462913, -0.43094301184159534),
            Block.box(3.6064930247571354, 16.16968889462913, -7.930943011841595, 12.606493024757134, 20.66968889462913, 1.0690569881584011),
            Block.box(6.647426340869751, 15.485700765515553, -6.5751031072985775, 9.647426340869751, 19.985700765515553, -3.5751031072985846),
            Block.box(6.647426340869751, 26.072956435158233, 0.7531389133861044, 9.647426340869751, 29.072956435158233, 5.253138913386101),
            Block.box(6.647426340869751, 20.16840052937773, -3.392998010114109, 9.647426340869751, 23.16840052937773, 4.107001989885889),
            Block.box(6.647426340869751, 16.269612601085427, 7.557028984527504, 9.647426340869751, 23.769612601085427, 10.557028984527504)
    );
    private static final VoxelShape W_GROWN =Shapes.or(
            Block.box(4.42836935271135, 13.75, 4.366483329028153, 11.92836935271135, 18.25, 11.866483329028153),
            Block.box(5.17836935271135, 4.75, 5.116483329028153, 11.17836935271135, 15.25, 11.116483329028153),
            Block.box(4.42836935271135, 3.25, 4.366483329028153, 11.92836935271135, 6.25, 11.866483329028153),
            Block.box(2.92836935271135, 0.25, 2.866483329028153, 13.42836935271135, 4.75, 13.366483329028153),
            Block.box(17.328369352711356, 12.25, 10.491483329028155, 21.828369352711356, 19.75, 11.991483329028155),
            Block.box(17.328369352711356, 12.25, 4.491483329028155, 21.828369352711356, 19.75, 5.991483329028155),
            Block.box(21.828369352711356, 12.25, 5.991483329028155, 23.328369352711356, 19.75, 10.491483329028155),
            Block.box(15.828369352711354, 12.25, 5.991483329028155, 17.328369352711356, 19.75, 10.491483329028155),
            Block.box(17.328369352711356, 10.75, 5.991483329028155, 21.828369352711356, 13.75, 10.491483329028155),
            Block.box(18.078369352711356, 9.25, 6.741483329028155, 21.078369352711356, 12.25, 9.741483329028155),
            Block.box(16.57836935271135, 19.16968889462913, 5.075550012915537, 22.57836935271135, 22.16968889462913, 11.075550012915537),
            Block.box(15.07836935271135, 16.16968889462913, 3.575550012915537, 24.07836935271135, 20.66968889462913, 12.575550012915535),
            Block.box(19.722529448168338, 15.485700765515553, 6.616483329028153, 22.72252944816833, 19.985700765515553, 9.616483329028153),
            Block.box(10.89428742748365, 26.072956435158233, 6.616483329028153, 15.394287427483647, 29.072956435158233, 9.616483329028153),
            Block.box(12.040424350983862, 20.16840052937773, 6.616483329028153, 19.540424350983862, 23.16840052937773, 9.616483329028153),
            Block.box(5.5903973563422475, 16.269612601085427, 6.616483329028153, 8.590397356342248, 23.769612601085427, 9.616483329028153)
    );



    public StarchPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos_, BlockState state) {
        return new ItemStack(ModBlocks.STARCH_PLANT.get().asItem());
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {

        if (state.getValue(FACING) == Direction.NORTH && state.getValue(AGE) == 0) {//NORTH
            return N_PICKED;
        }
        else if(state.getValue(FACING) == Direction.NORTH && state.getValue(AGE) == 1){
            return N_GROWN;
        }
        else if(state.getValue(FACING) == Direction.EAST && state.getValue(AGE) == 0){//EAST
            return E_PICKED;
        }
        else if(state.getValue(FACING) == Direction.EAST && state.getValue(AGE) == 1){
            return E_GROWN;
        }
        else if(state.getValue(FACING) == Direction.SOUTH && state.getValue(AGE) == 0){//SOUTH
            return S_PICKED;
        }
        else if(state.getValue(FACING) == Direction.SOUTH && state.getValue(AGE) == 1){
            return S_GROWN;
        }
        else if(state.getValue(FACING) == Direction.WEST && state.getValue(AGE) == 0){//WEST
            return W_PICKED;
        }
        else if(state.getValue(FACING) == Direction.WEST && state.getValue(AGE) == 1){
            return W_GROWN;
        }
        else
        {
            return Shapes.empty();
        }
    }

    public boolean isRandomlyTicking(BlockState p_57284_) {
        return p_57284_.getValue(AGE) < 1;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        int i = state.getValue(AGE);
        if (i < 1 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,rand.nextInt(5) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hint) {
        int i = state.getValue(AGE);
        boolean flag = i == 1;
        if (!flag && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i == 1) {

            popResource(world, pos, new ItemStack(ModItems.STARCH_NUT.get(), 1 + world.random.nextInt(1)));
            world.playSound((Player)null, pos, ModSounds.VEGETATION_PICKED.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hint);
        }
    }

    //Bonemeal
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 1;
    }

    public boolean isBonemealSuccess(Level pLevel, Random pRand, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, Random pRand, BlockPos pPos, BlockState pState) {

        pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(1)), 2);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.DIRT) ||
                state.is(Blocks.COARSE_DIRT) ||
                state.is(Blocks.PODZOL) ||
                state.is(Blocks.STONE) ||
                state.is(Blocks.CLAY) ||
                state.is(Blocks.MOSS_BLOCK) ||
                state.is(Blocks.COBBLED_DEEPSLATE) ||
                state.is(Blocks.COBBLESTONE) ||
                state.is(Blocks.DEEPSLATE) ||
                state.is(Blocks.GRAVEL) ||
                state.is(Blocks.ANDESITE) ||
                state.is(Blocks.GRANITE);
    }

    //Rotation
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(AGE, FACING);
    }


}
