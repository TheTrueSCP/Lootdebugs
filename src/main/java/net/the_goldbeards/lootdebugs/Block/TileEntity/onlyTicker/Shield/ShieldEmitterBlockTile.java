package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyTicker.Shield;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.util.ModTags;

public class ShieldEmitterBlockTile extends BlockEntity {

    private int time = 0;
    public ShieldEmitterBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntities.SHIELD_EMITTER_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick()
    {
        BlockPos pPos = this.getBlockPos();
        Level pLevel = this.getLevel();





        if(time >= 180)
        {

            for (int x = -4; x < 4; x++) {
                for (int y = -4; y < 4; y++) {
                    for (int z = -4; z < 4; z++) {
                        BlockPos testpos = new BlockPos(pPos.getX() + (x),pPos.getY() + (y), pPos.getZ() + (z));
                        if (pLevel.getBlockState(testpos).is(ModBlocks.SHIELD_BLOCK.get())) {
                            pLevel.setBlock(testpos, Blocks.AIR.defaultBlockState(), 3);

                        }
                    }
                }
            }
        }
        else
        {
            time++;
            placeShieldBlocks(pLevel, pPos);
        }

    }

    public void placeShieldBlocks(Level pLevel, BlockPos pPos)
    {
        BlockState pShieldBlockState = ModBlocks.SHIELD_BLOCK.get().defaultBlockState();
        //7x7x2
        for(int t = -1; t < 2; t++) {

            //5x5 Area
            for (int i = 0; i < 3; i++) {
                //East Building
                for (int y = 0; y < 3; y++)
                {
                    if(pLevel.getBlockState(pPos.north(y).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(y).east(i).above(t), pShieldBlockState, 3);
                    }

                }

                for (int z = 0; z < 2; z++)
                {
                    if(pLevel.getBlockState(pPos.north(z).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(z).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int u = 0; u < 1; u++)
                {
                    if(pLevel.getBlockState(pPos.north(u).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {

                        pLevel.setBlock(pPos.north(u).east(i).above(t), pShieldBlockState, 3);
                    }

                }

                for (int y = 0; y < 3; y++)
                {
                    if(pLevel.getBlockState(pPos.south(y).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(y).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int z = 0; z < 2; z++)
                {
                    if(pLevel.getBlockState(pPos.south(z).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(z).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int u = 0; u < 1; u++)
                {
                    if(pLevel.getBlockState(pPos.south(u).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(u).east(i).above(t), pShieldBlockState, 3);
                    }
                }
                for (int y = 0; y < 3; y++)
                {
                    if(pLevel.getBlockState(pPos.north(y).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(y).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int z = 0; z < 2; z++)
                {
                    if(pLevel.getBlockState(pPos.north(z).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(z).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int u = 0; u < 1; u++)
                {
                    if(pLevel.getBlockState(pPos.north(u).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(u).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int y = 0; y < 3; y++)
                {
                    if(pLevel.getBlockState(pPos.south(y).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(y).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int z = 0; z < 2; z++)
                {
                    if(pLevel.getBlockState(pPos.south(z).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(z).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int u = 0; u < 1; u++)
                {
                    if(pLevel.getBlockState(pPos.south(u).east(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(u).east(i).above(t), pShieldBlockState, 3);
                    }
                }

                //West build
                for (int y = 0; y < 3; y++)
                {
                    if(pLevel.getBlockState(pPos.north(y).west(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(y).west(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int z = 0; z < 2; z++)
                {
                    if (pLevel.getBlockState(pPos.north(z).west(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(z).west(i).above(t), pShieldBlockState, 3);
                    }
                }


                for (int u = 0; u < 1; u++)
                {
                    if (pLevel.getBlockState(pPos.north(u).west(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.north(u).west(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int y = 0; y < 3; y++)
                {
                    if (pLevel.getBlockState(pPos.south(y).west(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {

                        pLevel.setBlock(pPos.south(y).west(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int z = 0; z < 2; z++)
                {
                    if (pLevel.getBlockState(pPos.south(z).west(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(z).west(i).above(t), pShieldBlockState, 3);
                    }
                }

                for (int u = 0; u < 1; u++)
                {
                    if (pLevel.getBlockState(pPos.south(u).west(i).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                    {
                        pLevel.setBlock(pPos.south(u).west(i).above(t), pShieldBlockState, 3);
                    }
                }
            }

            //Rims
            for (int i = -1; i < 2; i++) {
                if(pLevel.getBlockState(pPos.north(i).east(3).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.north(i).east(3).above(t), pShieldBlockState, 3);
                }

                if(pLevel.getBlockState(pPos.south(i).west(3).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.south(i).west(3).above(t), pShieldBlockState, 3);
                }

                if(pLevel.getBlockState(pPos.east(i).north(3).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.east(i).north(3).above(t), pShieldBlockState, 3);
                }

                if(pLevel.getBlockState(pPos.west(i).south(3).above(t)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.west(i).south(3).above(t), pShieldBlockState, 3);
                }
            }
        }


        //5x5x1


        for (int i = -1; i < 2; i++)
        {
            for (int r = -1; r < 2; r++)
            {
                if (pLevel.getBlockState(pPos.north(i).west(r).above(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.north(i).west(r).above(2), pShieldBlockState, 3);
                }
            }
        }

        //5x5 Rims
        for (int i = -1; i < 2; i++) {

            if (pLevel.getBlockState(pPos.north(i).east(2).above(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
            {
                pLevel.setBlock(pPos.north(i).east(2).above(2), pShieldBlockState, 3);
            }

            if (pLevel.getBlockState(pPos.south(i).west(2).above(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS)) {
                pLevel.setBlock(pPos.south(i).west(2).above(2), pShieldBlockState, 3);
            }

            if (pLevel.getBlockState(pPos.east(i).north(2).above(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
            {
                pLevel.setBlock(pPos.east(i).north(2).above(2), pShieldBlockState, 3);
            }

            if (pLevel.getBlockState(pPos.west(i).south(2).above(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
            {
                pLevel.setBlock(pPos.west(i).south(2).above(2), pShieldBlockState, 3);
            }
        }


        //5x5x1 Below
        for (int i = -1; i < 2; i++)
        {
            for (int r = -1; r < 2; r++)
            {
                if (pLevel.getBlockState(pPos.north(i).west(r).below(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.north(i).west(r).below(2), pShieldBlockState, 3);
                }
            }
        }

        //5x5 Rims Below
        for (int i = -1; i < 2; i++) {

            if (pLevel.getBlockState(pPos.north(i).east(2).below(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
            {
                pLevel.setBlock(pPos.north(i).east(2).below(2), pShieldBlockState, 3);
            }

            if (pLevel.getBlockState(pPos.south(i).west(2).below(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
            {
                pLevel.setBlock(pPos.south(i).west(2).below(2), pShieldBlockState, 3);
            }

            if (pLevel.getBlockState(pPos.east(i).north(2).below(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
            {
                pLevel.setBlock(pPos.east(i).north(2).below(2), pShieldBlockState, 3);
            }

            if (pLevel.getBlockState(pPos.west(i).south(2).below(2)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
            {
                pLevel.setBlock(pPos.west(i).south(2).below(2), pShieldBlockState, 3);
            }
        }


        //3x3
        for (int i = -1; i < 2; i++)
        {
            for (int r = -1; r < 2; r++)
            {
                if (pLevel.getBlockState(pPos.north(i).west(r).above(3)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.north(i).west(r).above(3), pShieldBlockState, 3);
                }
            }
        }

        //3x3 Below
        for (int i = -1; i < 2; i++)
        {
            for (int r = -1; r < 2; r++)
            {

                if (pLevel.getBlockState(pPos.north(i).west(r).below(3)).is(ModTags.Blocks.SHIELD_BLOCK_REPLACEABLE_BLOCKS))
                {
                    pLevel.setBlock(pPos.north(i).west(r).below(3), pShieldBlockState, 3);
                }
            }
        }
    }
}
