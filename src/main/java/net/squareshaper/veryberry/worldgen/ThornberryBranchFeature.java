package net.squareshaper.veryberry.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.squareshaper.veryberry.block.ThornBerryBranch;
import net.squareshaper.veryberry.registry.ModBlocks;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class ThornberryBranchFeature extends Feature<DefaultFeatureConfig> {
    public ThornberryBranchFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) { // based on the FreezeTopLayerFeature
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int k = blockPos.getX() + i;
                int l = blockPos.getZ() + j;
                int m = structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING, k, l);
                mutable.set(k, m, l);
                mutable2.set(mutable).move(Direction.DOWN, 1);

                while (structureWorldAccess.getBlockState(mutable2).getBlock() == Blocks.CACTUS) {
                    Random random = structureWorldAccess.getRandom();
                    if (random.nextBetween(0, 2) < 1) {
                        Direction direction = Direction.values()[random.nextBetween(2, 5)];
                        if (structureWorldAccess.getBlockState(mutable2.offset(direction)).isAir()) {
                            structureWorldAccess.setBlockState(mutable2.offset(direction), ModBlocks.THORNBERRY_BRANCH.getDefaultState().with(HORIZONTAL_FACING, direction.getOpposite()).with(ThornBerryBranch.AGE, random.nextBetween(0, 3)), Block.FORCE_STATE);
                        }
                    }
                    mutable2.move(Direction.DOWN);
                }
            }
        }

        return true;
    }
}
