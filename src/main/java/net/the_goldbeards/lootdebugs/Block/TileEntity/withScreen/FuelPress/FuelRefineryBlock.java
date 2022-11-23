package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelPress;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class FuelRefineryBlock extends BaseEntityBlock  {


    public FuelRefineryBlock(Properties properties) {
        super(properties);
    }

    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static VoxelShape NORTH = Shapes.or(
            Block.box(2.25, 0.25, 8.25, 13.75, 4.75, 15.25),
            Block.box(2.25, -0.25, 3.75, 13.75, 1.25, 6.75),
            Block.box(2.25, -0.25, 1.25, 13.75, 1, 3.75),
            Block.box(2.25, -0.25, 0.25, 13.75, 0.75, 1.25),
            Block.box(1.5, 7, 11, 14.5, 14, 13),
            Block.box(9, 8, 11.25, 13, 13, 13.25),
            Block.box(14.5, 6, 10.5, 15.5, 15, 12.5),
            Block.box(14.75, 5.75, 11, 15.75, 15.25, 12),
            Block.box(15, 12.75, 10.75, 16, 13.75, 12.25),
            Block.box(15, 7.25, 10.75, 16, 8.25, 12.25),
            Block.box(0, 7.25, 10.75, 1, 8.25, 12.25),
            Block.box(0, 12.75, 10.75, 1, 13.75, 12.25),
            Block.box(0.25, 5.75, 11, 1.25, 15.25, 12),
            Block.box(1.25, 14.25, 11, 14.75, 15.25, 12),
            Block.box(7.25, 15, 10.5, 13.75, 16, 11.75),
            Block.box(6.75, 14.75, 10.65, 14.25, 15.75, 11.65),
            Block.box(7.75, 14.75, 10.4, 13.25, 15.75, 11.4),
            Block.box(1.25, 5.75, 11, 14.75, 6.75, 12),
            Block.box(0.5, 6, 10.5, 1.5, 15, 12.5),
            Block.box(1.5, 14, 10.5, 14.5, 15, 12.5),
            Block.box(1.5, 6, 10.5, 14.5, 7, 12.5),
            Block.box(10.25, 5.25, 10.25, 14.25, 6.5, 11.75),
            Block.box(6.25, 5.25, 11.75, 9.75, 7.5, 12.75),
            Block.box(1.75, 5.25, 10.25, 5.75, 6.5, 11.75),
            Block.box(5.75, 5.35, 10.4, 10.25, 6.35, 11.65),
            Block.box(2.5, 0.25, 1.4, 3.5, 1.25, 2.4),
            Block.box(3.75, 0.25, 1.4, 4.75, 1.25, 2.4),
            Block.box(5.25, 0.25, 1.4, 7, 1.25, 2.4),
            Block.box(8.5, 0.25, 1.4, 12.25, 1.25, 2.4),
            Block.box(12.5, 0.25, 1.4, 13.5, 1.25, 2.4),
            Block.box(7.25, 0.25, 1.4, 8.25, 1.25, 2.4),
            Block.box(3.75, 0.35, 2.65, 4.75, 1.35, 3.65),
            Block.box(5.25, 0.35, 2.65, 6.75, 1.35, 3.65),
            Block.box(7, 0.35, 2.65, 8, 1.35, 3.65),
            Block.box(8.25, 0.35, 2.65, 9.25, 1.35, 3.65),
            Block.box(9.5, 0.35, 2.65, 10.5, 1.35, 3.65),
            Block.box(10.75, 0.35, 2.65, 11.75, 1.35, 3.65),
            Block.box(12, 0.35, 2.65, 13.5, 1.35, 3.65),
            Block.box(2.5, 0.35, 2.65, 3.5, 1.35, 3.65),
            Block.box(2.5, 0.4, 3.9, 3.5, 1.4, 4.9),
            Block.box(3.75, 0.4, 3.9, 4.75, 1.4, 4.9),
            Block.box(5.25, 0.4, 3.9, 7.25, 1.4, 4.9),
            Block.box(7.5, 0.4, 3.9, 8.5, 1.4, 4.9),
            Block.box(8.75, 0.4, 3.9, 9.75, 1.4, 4.9),
            Block.box(10, 0.4, 3.9, 11, 1.4, 4.9),
            Block.box(11.25, 0.4, 3.9, 12.25, 1.4, 4.9),
            Block.box(12.5, 0.4, 3.9, 13.5, 1.4, 4.9),
            Block.box(2.5, 0.5, 5.15, 3.5, 1.5, 6.15),
            Block.box(3.75, 0.5, 5.15, 4.75, 1.5, 6.15),
            Block.box(5.25, 0.5, 5.25, 13.5, 1.5, 6.5),
            Block.box(2.75, 4.25, 8.5, 13.25, 5.25, 15),
            Block.box(1.75, 0, 13.5, 14.25, 5, 15.5),
            Block.box(2.5, 3.5, 13.75, 13.5, 4.5, 15.75),
            Block.box(3.25, 0.7499138767987645, 11.31194682785392, 12.75, 1.7499138767987645, 13.31194682785392),
            Block.box(3.25, 0.2879741105431215, 11.120605111671374, 12.75, 1.2879741105431215, 13.120605111671374),
            Block.box(3.25, -0.17396565571252154, 10.929263395488828, 12.75, 0.8260343442874785, 12.929263395488828),
            Block.box(3.25, -0.6359054219681681, 10.737921679306284, 12.75, 0.36409457803183365, 12.737921679306284),
            Block.box(3.25, -1.0978451882238076, 10.54657996312374, 12.75, -0.09784518822380761, 12.54657996312374),
            Block.box(2.9999999999999996, 9.830196534130526, 13.408338654117152, 6.5, 10.830196534130526, 14.408338654117152),
            Block.box(2, 7.25, 12.25, 3, 13.75, 13.25),
            Block.box(6.5, 7.25, 12.25, 7.5, 13.75, 13.25),
            Block.box(3, 12.75, 12.25, 6.5, 13.75, 13.25),
            Block.box(3, 7.25, 12.25, 6.5, 8.25, 13.25),
            Block.box(2.9999999999999996, 9.368256767874882, 13.216996937934608, 6.5, 10.368256767874882, 14.216996937934608),
            Block.box(2.9999999999999996, 8.906317001619238, 13.025655221752064, 6.5, 9.906317001619238, 14.025655221752064),
            Block.box(2.9999999999999996, 8.444377235363595, 12.834313505569519, 6.5, 9.444377235363595, 13.834313505569519),
            Block.box(2.9999999999999996, 7.982437469107952, 12.642971789386973, 6.5, 8.982437469107952, 13.642971789386973),
            Block.box(2.9999999999999996, 7.520497702852307, 12.451630073204429, 6.5, 8.520497702852307, 13.451630073204429),
            Block.box(2.9999999999999996, 7.0585579365966655, 12.260288357021883, 6.5, 8.058557936596666, 13.260288357021883),
            Block.box(2.9999999999999996, 6.596618170341021, 12.068946640839338, 6.5, 7.596618170341021, 13.068946640839338),
            Block.box(2.9999999999999996, 6.134678404085378, 11.877604924656794, 6.5, 7.134678404085378, 12.877604924656794),
            Block.box(2.5, 0.5, 13.75, 13.5, 1.5, 15.75),
            Block.box(2.5, 1.5, 13.75, 3.5, 3.5, 15.75),
            Block.box(12.5, 1.5, 13.75, 13.5, 3.5, 15.75),
            Block.box(1.75, 0, 8, 14.25, 5, 10),
            Block.box(1.75, 0, 13.5, 14.25, 5, 15.5),
            Block.box(0, 0.5, 8.5, 2, 1.5, 9.5),
            Block.box(0, 3.5, 8.5, 2, 4.5, 9.5),
            Block.box(0, 1.5, 8.5, 1, 3.5, 9.5),
            Block.box(0, 0.5, 14, 2, 1.5, 15),
            Block.box(0, 3.5, 14, 2, 4.5, 15),
            Block.box(0, 1.5, 14, 1, 3.5, 15),
            Block.box(14, 0.5, 8.5, 16, 1.5, 9.5),
            Block.box(3, 1, 7.75, 6, 4, 8.75),
            Block.box(4.75, 1.25, 7.5, 5.75, 3.75, 8.5),
            Block.box(3.25, 1.25, 7.5, 4.75, 2.25, 8.5),
            Block.box(3.25, 2.75, 7.5, 4.25, 3.75, 8.5),
            Block.box(12.25, 3.5, 7.75, 13.25, 4.5, 8.75),
            Block.box(13, 5.4, 10, 14, 6.4, 11),
            Block.box(11.75, 5.4, 10, 12.75, 6.4, 11),
            Block.box(10.5, 5.4, 10, 11.5, 6.4, 11),
            Block.box(2.5, 5.4, 10, 4.5, 6.4, 11),
            Block.box(10.25, 3.5, 7.75, 11.25, 4.5, 8.75),
            Block.box(10.25, 0.25, 7.75, 12.25, 3, 8.75),
            Block.box(10.5, 0.5, 7.5, 12, 2.75, 8.5),
            Block.box(14, 3.5, 8.5, 16, 4.5, 9.5),
            Block.box(15, 1.5, 8.5, 16, 3.5, 9.5),
            Block.box(15, 1.5, 14, 16, 3.5, 15),
            Block.box(14, 3.5, 14, 16, 4.5, 15),
            Block.box(14, 0.5, 14, 16, 1.5, 15)
    );

    public static VoxelShape EAST = Shapes.or(
            Block.box(0.5, 0.25, 2, 7.5, 4.75, 13.5),
            Block.box(9, -0.25, 2, 12, 1.25, 13.5),
            Block.box(12, -0.25, 2, 14.5, 1, 13.5),
            Block.box(14.5, -0.25, 2, 15.5, 0.75, 13.5),
            Block.box(2.75, 7, 1.25, 4.75, 14, 14.25),
            Block.box(2.5, 8, 8.75, 4.5, 13, 12.75),
            Block.box(3.25, 6, 14.25, 5.25, 15, 15.25),
            Block.box(3.75, 5.75, 14.5, 4.75, 15.25, 15.5),
            Block.box(3.5, 12.75, 14.75, 5, 13.75, 15.75),
            Block.box(3.5, 7.25, 14.75, 5, 8.25, 15.75),
            Block.box(3.5, 7.25, -0.25, 5, 8.25, 0.75),
            Block.box(3.5, 12.75, -0.25, 5, 13.75, 0.75),
            Block.box(3.75, 5.75, 0, 4.75, 15.25, 1),
            Block.box(3.75, 14.25, 1, 4.75, 15.25, 14.5),
            Block.box(4, 15, 7, 5.25, 16, 13.5),
            Block.box(4.1, 14.75, 6.5, 5.1, 15.75, 14),
            Block.box(4.35, 14.75, 7.5, 5.35, 15.75, 13),
            Block.box(3.75, 5.75, 1, 4.75, 6.75, 14.5),
            Block.box(3.25, 6, 0.25, 5.25, 15, 1.25),
            Block.box(3.25, 14, 1.25, 5.25, 15, 14.25),
            Block.box(3.25, 6, 1.25, 5.25, 7, 14.25),
            Block.box(4, 5.25, 10, 5.5, 6.5, 14),
            Block.box(3, 5.25, 6, 4, 7.5, 9.5),
            Block.box(4, 5.25, 1.5, 5.5, 6.5, 5.5),
            Block.box(4.1, 5.35, 5.5, 5.35, 6.35, 10),
            Block.box(13.35, 0.25, 2.25, 14.35, 1.25, 3.25),
            Block.box(13.35, 0.25, 3.5, 14.35, 1.25, 4.5),
            Block.box(13.35, 0.25, 5, 14.35, 1.25, 6.75),
            Block.box(13.35, 0.25, 8.25, 14.35, 1.25, 12),
            Block.box(13.35, 0.25, 12.25, 14.35, 1.25, 13.25),
            Block.box(13.35, 0.25, 7, 14.35, 1.25, 8),
            Block.box(12.1, 0.35, 3.5, 13.1, 1.35, 4.5),
            Block.box(12.1, 0.35, 5, 13.1, 1.35, 6.5),
            Block.box(12.1, 0.35, 6.75, 13.1, 1.35, 7.75),
            Block.box(12.1, 0.35, 8, 13.1, 1.35, 9),
            Block.box(12.1, 0.35, 9.25, 13.1, 1.35, 10.25),
            Block.box(12.1, 0.35, 10.5, 13.1, 1.35, 11.5),
            Block.box(12.1, 0.35, 11.75, 13.1, 1.35, 13.25),
            Block.box(12.1, 0.35, 2.25, 13.1, 1.35, 3.25),
            Block.box(10.85, 0.4, 2.25, 11.85, 1.4, 3.25),
            Block.box(10.85, 0.4, 3.5, 11.85, 1.4, 4.5),
            Block.box(10.85, 0.4, 5, 11.85, 1.4, 7),
            Block.box(10.85, 0.4, 7.25, 11.85, 1.4, 8.25),
            Block.box(10.85, 0.4, 8.5, 11.85, 1.4, 9.5),
            Block.box(10.85, 0.4, 9.75, 11.85, 1.4, 10.75),
            Block.box(10.85, 0.4, 11, 11.85, 1.4, 12),
            Block.box(10.85, 0.4, 12.25, 11.85, 1.4, 13.25),
            Block.box(9.6, 0.5, 2.25, 10.6, 1.5, 3.25),
            Block.box(9.6, 0.5, 3.5, 10.6, 1.5, 4.5),
            Block.box(9.25, 0.5, 5, 10.5, 1.5, 13.25),
            Block.box(0.75, 4.25, 2.5, 7.25, 5.25, 13),
            Block.box(0.25, 0, 1.5, 2.25, 5, 14),
            Block.box(0, 3.5, 2.25, 2, 4.5, 13.25),
            Block.box(2.4380531721460805, 0.7499138767987645, 3, 4.4380531721460805, 1.7499138767987645, 12.5),
            Block.box(2.6293948883286262, 0.2879741105431215, 3, 4.629394888328626, 1.2879741105431215, 12.5),
            Block.box(2.820736604511172, -0.17396565571252154, 3, 4.820736604511172, 0.8260343442874785, 12.5),
            Block.box(3.012078320693716, -0.6359054219681681, 3, 5.012078320693716, 0.36409457803183365, 12.5),
            Block.box(3.2034200368762598, -1.0978451882238076, 3, 5.20342003687626, -0.09784518822380761, 12.5),
            Block.box(1.341661345882848, 9.830196534130526, 2.75, 2.341661345882848, 10.830196534130526, 6.25),
            Block.box(2.5, 7.25, 1.75, 3.5, 13.75, 2.75),
            Block.box(2.5, 7.25, 6.25, 3.5, 13.75, 7.25),
            Block.box(2.5, 12.75, 2.75, 3.5, 13.75, 6.25),
            Block.box(2.5, 7.25, 2.75, 3.5, 8.25, 6.25),
            Block.box(1.5330030620653918, 9.368256767874882, 2.75, 2.533003062065392, 10.368256767874882, 6.25),
            Block.box(1.7243447782479357, 8.906317001619238, 2.75, 2.7243447782479357, 9.906317001619238, 6.25),
            Block.box(1.9156864944304814, 8.444377235363595, 2.75, 2.9156864944304814, 9.444377235363595, 6.25),
            Block.box(2.107028210613027, 7.982437469107952, 2.75, 3.107028210613027, 8.982437469107952, 6.25),
            Block.box(2.298369926795571, 7.520497702852307, 2.75, 3.298369926795571, 8.520497702852307, 6.25),
            Block.box(2.4897116429781168, 7.0585579365966655, 2.75, 3.4897116429781168, 8.058557936596666, 6.25),
            Block.box(2.6810533591606625, 6.596618170341021, 2.75, 3.6810533591606625, 7.596618170341021, 6.25),
            Block.box(2.8723950753432064, 6.134678404085378, 2.75, 3.8723950753432064, 7.134678404085378, 6.25),
            Block.box(0, 0.5, 2.25, 2, 1.5, 13.25),
            Block.box(0, 1.5, 2.25, 2, 3.5, 3.25),
            Block.box(0, 1.5, 12.25, 2, 3.5, 13.25),
            Block.box(5.75, 0, 1.5, 7.75, 5, 14),
            Block.box(0.25, 0, 1.5, 2.25, 5, 14),
            Block.box(6.25, 0.5, -0.25, 7.25, 1.5, 1.75),
            Block.box(6.25, 3.5, -0.25, 7.25, 4.5, 1.75),
            Block.box(6.25, 1.5, -0.25, 7.25, 3.5, 0.75),
            Block.box(0.75, 0.5, -0.25, 1.75, 1.5, 1.75),
            Block.box(0.75, 3.5, -0.25, 1.75, 4.5, 1.75),
            Block.box(0.75, 1.5, -0.25, 1.75, 3.5, 0.75),
            Block.box(6.25, 0.5, 13.75, 7.25, 1.5, 15.75),
            Block.box(7, 1, 2.75, 8, 4, 5.75),
            Block.box(7.25, 1.25, 4.5, 8.25, 3.75, 5.5),
            Block.box(7.25, 1.25, 3, 8.25, 2.25, 4.5),
            Block.box(7.25, 2.75, 3, 8.25, 3.75, 4),
            Block.box(7, 3.5, 12, 8, 4.5, 13),
            Block.box(4.75, 5.4, 12.75, 5.75, 6.4, 13.75),
            Block.box(4.75, 5.4, 11.5, 5.75, 6.4, 12.5),
            Block.box(4.75, 5.4, 10.25, 5.75, 6.4, 11.25),
            Block.box(4.75, 5.4, 2.25, 5.75, 6.4, 4.25),
            Block.box(7, 3.5, 10, 8, 4.5, 11),
            Block.box(7, 0.25, 10, 8, 3, 12),
            Block.box(7.25, 0.5, 10.25, 8.25, 2.75, 11.75),
            Block.box(6.25, 3.5, 13.75, 7.25, 4.5, 15.75),
            Block.box(6.25, 1.5, 14.75, 7.25, 3.5, 15.75),
            Block.box(0.75, 1.5, 14.75, 1.75, 3.5, 15.75),
            Block.box(0.75, 3.5, 13.75, 1.75, 4.5, 15.75),
            Block.box(0.75, 0.5, 13.75, 1.75, 1.5, 15.75)
    );

    public static VoxelShape SOUTH = Shapes.or(
            Block.box(2.25, 0.25, 0.25, 13.75, 4.75, 7.25),
            Block.box(2.25, -0.25, 8.75, 13.75, 1.25, 11.75),
            Block.box(2.25, -0.25, 11.75, 13.75, 1, 14.25),
            Block.box(2.25, -0.25, 14.25, 13.75, 0.75, 15.25),
            Block.box(1.5, 7, 2.5, 14.5, 14, 4.5),
            Block.box(3, 8, 2.25, 7, 13, 4.25),
            Block.box(0.5, 6, 3, 1.5, 15, 5),
            Block.box(0.25, 5.75, 3.5, 1.25, 15.25, 4.5),
            Block.box(0, 12.75, 3.25, 1, 13.75, 4.75),
            Block.box(0, 7.25, 3.25, 1, 8.25, 4.75),
            Block.box(15, 7.25, 3.25, 16, 8.25, 4.75),
            Block.box(15, 12.75, 3.25, 16, 13.75, 4.75),
            Block.box(14.75, 5.75, 3.5, 15.75, 15.25, 4.5),
            Block.box(1.25, 14.25, 3.5, 14.75, 15.25, 4.5),
            Block.box(2.25, 15, 3.75, 8.75, 16, 5),
            Block.box(1.75, 14.75, 3.8499999999999996, 9.25, 15.75, 4.85),
            Block.box(2.75, 14.75, 4.1, 8.25, 15.75, 5.1),
            Block.box(1.25, 5.75, 3.5, 14.75, 6.75, 4.5),
            Block.box(14.5, 6, 3, 15.5, 15, 5),
            Block.box(1.5, 14, 3, 14.5, 15, 5),
            Block.box(1.5, 6, 3, 14.5, 7, 5),
            Block.box(1.75, 5.25, 3.75, 5.75, 6.5, 5.25),
            Block.box(6.25, 5.25, 2.75, 9.75, 7.5, 3.75),
            Block.box(10.25, 5.25, 3.75, 14.25, 6.5, 5.25),
            Block.box(5.75, 5.35, 3.8499999999999996, 10.25, 6.35, 5.1),
            Block.box(12.5, 0.25, 13.1, 13.5, 1.25, 14.1),
            Block.box(11.25, 0.25, 13.1, 12.25, 1.25, 14.1),
            Block.box(9, 0.25, 13.1, 10.75, 1.25, 14.1),
            Block.box(3.75, 0.25, 13.1, 7.5, 1.25, 14.1),
            Block.box(2.5, 0.25, 13.1, 3.5, 1.25, 14.1),
            Block.box(7.75, 0.25, 13.1, 8.75, 1.25, 14.1),
            Block.box(11.25, 0.35, 11.85, 12.25, 1.35, 12.85),
            Block.box(9.25, 0.35, 11.85, 10.75, 1.35, 12.85),
            Block.box(8, 0.35, 11.85, 9, 1.35, 12.85),
            Block.box(6.75, 0.35, 11.85, 7.75, 1.35, 12.85),
            Block.box(5.5, 0.35, 11.85, 6.5, 1.35, 12.85),
            Block.box(4.25, 0.35, 11.85, 5.25, 1.35, 12.85),
            Block.box(2.5, 0.35, 11.85, 4, 1.35, 12.85),
            Block.box(12.5, 0.35, 11.85, 13.5, 1.35, 12.85),
            Block.box(12.5, 0.4, 10.6, 13.5, 1.4, 11.6),
            Block.box(11.25, 0.4, 10.6, 12.25, 1.4, 11.6),
            Block.box(8.75, 0.4, 10.6, 10.75, 1.4, 11.6),
            Block.box(7.5, 0.4, 10.6, 8.5, 1.4, 11.6),
            Block.box(6.25, 0.4, 10.6, 7.25, 1.4, 11.6),
            Block.box(5, 0.4, 10.6, 6, 1.4, 11.6),
            Block.box(3.75, 0.4, 10.6, 4.75, 1.4, 11.6),
            Block.box(2.5, 0.4, 10.6, 3.5, 1.4, 11.6),
            Block.box(12.5, 0.5, 9.35, 13.5, 1.5, 10.35),
            Block.box(11.25, 0.5, 9.35, 12.25, 1.5, 10.35),
            Block.box(2.5, 0.5, 9, 10.75, 1.5, 10.25),
            Block.box(2.75, 4.25, 0.5, 13.25, 5.25, 7),
            Block.box(1.75, 0, 0, 14.25, 5, 2),
            Block.box(2.5, 3.5, -0.25, 13.5, 4.5, 1.75),
            Block.box(3.25, 0.7499138767987645, 2.1880531721460805, 12.75, 1.7499138767987645, 4.1880531721460805),
            Block.box(3.25, 0.2879741105431215, 2.3793948883286262, 12.75, 1.2879741105431215, 4.379394888328626),
            Block.box(3.25, -0.17396565571252154, 2.570736604511172, 12.75, 0.8260343442874785, 4.570736604511172),
            Block.box(3.25, -0.6359054219681681, 2.762078320693716, 12.75, 0.36409457803183365, 4.762078320693716),
            Block.box(3.25, -1.0978451882238076, 2.9534200368762598, 12.75, -0.09784518822380761, 4.95342003687626),
            Block.box(9.5, 9.830196534130526, 1.091661345882848, 13, 10.830196534130526, 2.091661345882848),
            Block.box(13, 7.25, 2.25, 14, 13.75, 3.25),
            Block.box(8.5, 7.25, 2.25, 9.5, 13.75, 3.25),
            Block.box(9.5, 12.75, 2.25, 13, 13.75, 3.25),
            Block.box(9.5, 7.25, 2.25, 13, 8.25, 3.25),
            Block.box(9.5, 9.368256767874882, 1.2830030620653918, 13, 10.368256767874882, 2.283003062065392),
            Block.box(9.5, 8.906317001619238, 1.4743447782479357, 13, 9.906317001619238, 2.4743447782479357),
            Block.box(9.5, 8.444377235363595, 1.6656864944304814, 13, 9.444377235363595, 2.6656864944304814),
            Block.box(9.5, 7.982437469107952, 1.8570282106130271, 13, 8.982437469107952, 2.857028210613027),
            Block.box(9.5, 7.520497702852307, 2.048369926795571, 13, 8.520497702852307, 3.048369926795571),
            Block.box(9.5, 7.0585579365966655, 2.2397116429781168, 13, 8.058557936596666, 3.2397116429781168),
            Block.box(9.5, 6.596618170341021, 2.4310533591606625, 13, 7.596618170341021, 3.4310533591606625),
            Block.box(9.5, 6.134678404085378, 2.6223950753432064, 13, 7.134678404085378, 3.6223950753432064),
            Block.box(2.5, 0.5, -0.25, 13.5, 1.5, 1.75),
            Block.box(12.5, 1.5, -0.25, 13.5, 3.5, 1.75),
            Block.box(2.5, 1.5, -0.25, 3.5, 3.5, 1.75),
            Block.box(1.75, 0, 5.5, 14.25, 5, 7.5),
            Block.box(1.75, 0, 0, 14.25, 5, 2),
            Block.box(14, 0.5, 6, 16, 1.5, 7),
            Block.box(14, 3.5, 6, 16, 4.5, 7),
            Block.box(15, 1.5, 6, 16, 3.5, 7),
            Block.box(14, 0.5, 0.5, 16, 1.5, 1.5),
            Block.box(14, 3.5, 0.5, 16, 4.5, 1.5),
            Block.box(15, 1.5, 0.5, 16, 3.5, 1.5),
            Block.box(0, 0.5, 6, 2, 1.5, 7),
            Block.box(10, 1, 6.75, 13, 4, 7.75),
            Block.box(10.25, 1.25, 7, 11.25, 3.75, 8),
            Block.box(11.25, 1.25, 7, 12.75, 2.25, 8),
            Block.box(11.75, 2.75, 7, 12.75, 3.75, 8),
            Block.box(2.75, 3.5, 6.75, 3.75, 4.5, 7.75),
            Block.box(2, 5.4, 4.5, 3, 6.4, 5.5),
            Block.box(3.25, 5.4, 4.5, 4.25, 6.4, 5.5),
            Block.box(4.5, 5.4, 4.5, 5.5, 6.4, 5.5),
            Block.box(11.5, 5.4, 4.5, 13.5, 6.4, 5.5),
            Block.box(4.75, 3.5, 6.75, 5.75, 4.5, 7.75),
            Block.box(3.75, 0.25, 6.75, 5.75, 3, 7.75),
            Block.box(4, 0.5, 7, 5.5, 2.75, 8),
            Block.box(0, 3.5, 6, 2, 4.5, 7),
            Block.box(0, 1.5, 6, 1, 3.5, 7),
            Block.box(0, 1.5, 0.5, 1, 3.5, 1.5),
            Block.box(0, 3.5, 0.5, 2, 4.5, 1.5),
            Block.box(0, 0.5, 0.5, 2, 1.5, 1.5)
    );

    public static VoxelShape WEST = Shapes.or(
            Block.box(8.5, 0.25, 2, 15.5, 4.75, 13.5),
            Block.box(4, -0.25, 2, 7, 1.25, 13.5),
            Block.box(1.5, -0.25, 2, 4, 1, 13.5),
            Block.box(0.5, -0.25, 2, 1.5, 0.75, 13.5),
            Block.box(11.25, 7, 1.25, 13.25, 14, 14.25),
            Block.box(11.5, 8, 2.75, 13.5, 13, 6.75),
            Block.box(10.75, 6, 0.25, 12.75, 15, 1.25),
            Block.box(11.25, 5.75, 0, 12.25, 15.25, 1),
            Block.box(11, 12.75, -0.25, 12.5, 13.75, 0.75),
            Block.box(11, 7.25, -0.25, 12.5, 8.25, 0.75),
            Block.box(11, 7.25, 14.75, 12.5, 8.25, 15.75),
            Block.box(11, 12.75, 14.75, 12.5, 13.75, 15.75),
            Block.box(11.25, 5.75, 14.5, 12.25, 15.25, 15.5),
            Block.box(11.25, 14.25, 1, 12.25, 15.25, 14.5),
            Block.box(10.75, 15, 2, 12, 16, 8.5),
            Block.box(10.9, 14.75, 1.5, 11.9, 15.75, 9),
            Block.box(10.65, 14.75, 2.5, 11.65, 15.75, 8),
            Block.box(11.25, 5.75, 1, 12.25, 6.75, 14.5),
            Block.box(10.75, 6, 14.25, 12.75, 15, 15.25),
            Block.box(10.75, 14, 1.25, 12.75, 15, 14.25),
            Block.box(10.75, 6, 1.25, 12.75, 7, 14.25),
            Block.box(10.5, 5.25, 1.5, 12, 6.5, 5.5),
            Block.box(12, 5.25, 6, 13, 7.5, 9.5),
            Block.box(10.5, 5.25, 10, 12, 6.5, 14),
            Block.box(10.65, 5.35, 5.5, 11.9, 6.35, 10),
            Block.box(1.6500000000000004, 0.25, 12.25, 2.6500000000000004, 1.25, 13.25),
            Block.box(1.6500000000000004, 0.25, 11, 2.6500000000000004, 1.25, 12),
            Block.box(1.6500000000000004, 0.25, 8.75, 2.6500000000000004, 1.25, 10.5),
            Block.box(1.6500000000000004, 0.25, 3.5, 2.6500000000000004, 1.25, 7.25),
            Block.box(1.6500000000000004, 0.25, 2.25, 2.6500000000000004, 1.25, 3.25),
            Block.box(1.6500000000000004, 0.25, 7.5, 2.6500000000000004, 1.25, 8.5),
            Block.box(2.9000000000000004, 0.35, 11, 3.9000000000000004, 1.35, 12),
            Block.box(2.9000000000000004, 0.35, 9, 3.9000000000000004, 1.35, 10.5),
            Block.box(2.9000000000000004, 0.35, 7.75, 3.9000000000000004, 1.35, 8.75),
            Block.box(2.9000000000000004, 0.35, 6.5, 3.9000000000000004, 1.35, 7.5),
            Block.box(2.9000000000000004, 0.35, 5.25, 3.9000000000000004, 1.35, 6.25),
            Block.box(2.9000000000000004, 0.35, 4, 3.9000000000000004, 1.35, 5),
            Block.box(2.9000000000000004, 0.35, 2.25, 3.9000000000000004, 1.35, 3.75),
            Block.box(2.9000000000000004, 0.35, 12.25, 3.9000000000000004, 1.35, 13.25),
            Block.box(4.15, 0.4, 12.25, 5.15, 1.4, 13.25),
            Block.box(4.15, 0.4, 11, 5.15, 1.4, 12),
            Block.box(4.15, 0.4, 8.5, 5.15, 1.4, 10.5),
            Block.box(4.15, 0.4, 7.25, 5.15, 1.4, 8.25),
            Block.box(4.15, 0.4, 6, 5.15, 1.4, 7),
            Block.box(4.15, 0.4, 4.75, 5.15, 1.4, 5.75),
            Block.box(4.15, 0.4, 3.5, 5.15, 1.4, 4.5),
            Block.box(4.15, 0.4, 2.25, 5.15, 1.4, 3.25),
            Block.box(5.4, 0.5, 12.25, 6.4, 1.5, 13.25),
            Block.box(5.4, 0.5, 11, 6.4, 1.5, 12),
            Block.box(5.5, 0.5, 2.25, 6.75, 1.5, 10.5),
            Block.box(8.75, 4.25, 2.5, 15.25, 5.25, 13),
            Block.box(13.75, 0, 1.5, 15.75, 5, 14),
            Block.box(14, 3.5, 2.25, 16, 4.5, 13.25),
            Block.box(11.56194682785392, 0.7499138767987645, 3, 13.56194682785392, 1.7499138767987645, 12.5),
            Block.box(11.370605111671374, 0.2879741105431215, 3, 13.370605111671374, 1.2879741105431215, 12.5),
            Block.box(11.179263395488828, -0.17396565571252154, 3, 13.179263395488828, 0.8260343442874785, 12.5),
            Block.box(10.987921679306284, -0.6359054219681681, 3, 12.987921679306284, 0.36409457803183365, 12.5),
            Block.box(10.79657996312374, -1.0978451882238076, 3, 12.79657996312374, -0.09784518822380761, 12.5),
            Block.box(13.658338654117152, 9.830196534130526, 9.25, 14.658338654117152, 10.830196534130526, 12.75),
            Block.box(12.5, 7.25, 12.75, 13.5, 13.75, 13.75),
            Block.box(12.5, 7.25, 8.25, 13.5, 13.75, 9.25),
            Block.box(12.5, 12.75, 9.25, 13.5, 13.75, 12.75),
            Block.box(12.5, 7.25, 9.25, 13.5, 8.25, 12.75),
            Block.box(13.466996937934608, 9.368256767874882, 9.25, 14.466996937934608, 10.368256767874882, 12.75),
            Block.box(13.275655221752064, 8.906317001619238, 9.25, 14.275655221752064, 9.906317001619238, 12.75),
            Block.box(13.084313505569519, 8.444377235363595, 9.25, 14.084313505569519, 9.444377235363595, 12.75),
            Block.box(12.892971789386973, 7.982437469107952, 9.25, 13.892971789386973, 8.982437469107952, 12.75),
            Block.box(12.701630073204429, 7.520497702852307, 9.25, 13.701630073204429, 8.520497702852307, 12.75),
            Block.box(12.510288357021883, 7.0585579365966655, 9.25, 13.510288357021883, 8.058557936596666, 12.75),
            Block.box(12.318946640839338, 6.596618170341021, 9.25, 13.318946640839338, 7.596618170341021, 12.75),
            Block.box(12.127604924656794, 6.134678404085378, 9.25, 13.127604924656794, 7.134678404085378, 12.75),
            Block.box(14, 0.5, 2.25, 16, 1.5, 13.25),
            Block.box(14, 1.5, 12.25, 16, 3.5, 13.25),
            Block.box(14, 1.5, 2.25, 16, 3.5, 3.25),
            Block.box(8.25, 0, 1.5, 10.25, 5, 14),
            Block.box(13.75, 0, 1.5, 15.75, 5, 14),
            Block.box(8.75, 0.5, 13.75, 9.75, 1.5, 15.75),
            Block.box(8.75, 3.5, 13.75, 9.75, 4.5, 15.75),
            Block.box(8.75, 1.5, 14.75, 9.75, 3.5, 15.75),
            Block.box(14.25, 0.5, 13.75, 15.25, 1.5, 15.75),
            Block.box(14.25, 3.5, 13.75, 15.25, 4.5, 15.75),
            Block.box(14.25, 1.5, 14.75, 15.25, 3.5, 15.75),
            Block.box(8.75, 0.5, -0.25, 9.75, 1.5, 1.75),
            Block.box(8, 1, 9.75, 9, 4, 12.75),
            Block.box(7.75, 1.25, 10, 8.75, 3.75, 11),
            Block.box(7.75, 1.25, 11, 8.75, 2.25, 12.5),
            Block.box(7.75, 2.75, 11.5, 8.75, 3.75, 12.5),
            Block.box(8, 3.5, 2.5, 9, 4.5, 3.5),
            Block.box(10.25, 5.4, 1.75, 11.25, 6.4, 2.75),
            Block.box(10.25, 5.4, 3, 11.25, 6.4, 4),
            Block.box(10.25, 5.4, 4.25, 11.25, 6.4, 5.25),
            Block.box(10.25, 5.4, 11.25, 11.25, 6.4, 13.25),
            Block.box(8, 3.5, 4.5, 9, 4.5, 5.5),
            Block.box(8, 0.25, 3.5, 9, 3, 5.5),
            Block.box(7.75, 0.5, 3.75, 8.75, 2.75, 5.25),
            Block.box(8.75, 3.5, -0.25, 9.75, 4.5, 1.75),
            Block.box(8.75, 1.5, -0.25, 9.75, 3.5, 0.75),
            Block.box(14.25, 1.5, -0.25, 15.25, 3.5, 0.75),
            Block.box(14.25, 3.5, -0.25, 15.25, 4.5, 1.75),
            Block.box(14.25, 0.5, -0.25, 15.25, 1.5, 1.75)
    );

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {


        if (!pLevel.isClientSide()) {

            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if(entity instanceof FuelRefineryTile) {

                NetworkHooks.openGui(((ServerPlayer)pPlayer), (FuelRefineryTile)entity, pPos);

            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;

    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            if(pLevel.hasNeighborSignal(pPos))
            {
                BlockEntity entity = pLevel.getBlockEntity(pPos);

                if(entity instanceof FuelRefineryTile FPT) {

                    FPT.setConvertingToTrue();

                }
            }

        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
      /*  switch (state.getValue(FACING))
        {
            case NORTH:
                return NORTH;

            case EAST:
                return EAST;

            case WEST:
                return WEST;

            case SOUTH:
                return SOUTH;

            default:
                return NORTH;
        }*/
        return NORTH;
    }



    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @javax.annotation.Nullable
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


    //If the block is destroyed, al items inside drops
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FuelRefineryTile fuelRefineryTile)
            {
                fuelRefineryTile.setRemoved();
                ((FuelRefineryTile) blockEntity).drops();
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FuelRefineryTile(pPos,pState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof FuelRefineryTile BE) {
                    BE.tick(pLevel, pos, blockState, BE);
                }
            };
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(FACING);
    }
}
