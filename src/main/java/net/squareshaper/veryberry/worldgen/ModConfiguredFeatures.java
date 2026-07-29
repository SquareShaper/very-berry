package net.squareshaper.veryberry.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.registry.*;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.squareshaper.veryberry.VeryBerry;
import net.squareshaper.veryberry.block.BerryBushBlock;
import net.squareshaper.veryberry.block.FireShineBerryHead;
import net.squareshaper.veryberry.block.NetherVines;
import net.squareshaper.veryberry.registry.ModBlocks;

import java.util.List;

import static net.squareshaper.veryberry.VeryBerry.THORNBERRY_GROWTH_CONFIG;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> RIMEBERRY_KEY = registerKey("rimeberry");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RIMEBERRY_PATCH_KEY = registerKey("rimeberry_patch");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FIRESHINE_BERRY_KEY = registerKey("fireshine_berry");
    public static final RegistryKey<ConfiguredFeature<?, ?>> THORNBERRY_KEY = registerKey("thornberry");
//    public static final RegistryKey<ConfiguredFeature<?, ?>> VOIDBERRY_KEY = registerKey("voidberry");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<PlacedFeature> registryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        register(context, RIMEBERRY_KEY, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(
                Pool.<BlockState>builder().add(ModBlocks.RIMEBERRY_BUSH.getDefaultState(), 4)
                        .add(ModBlocks.RIMEBERRY_BUSH.getDefaultState().with(BerryBushBlock.AGE, 1), 3)
                        .add(ModBlocks.RIMEBERRY_BUSH.getDefaultState().with(BerryBushBlock.AGE, 2), 3)
                        .add(ModBlocks.RIMEBERRY_BUSH.getDefaultState().with(BerryBushBlock.AGE, 3), 2))));

        register(context, RIMEBERRY_PATCH_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(64, 8, 8,
                registryLookup.getOrThrow(ModPlacedFeatures.RIMEBERRY_KEY)));


        WeightedBlockStateProvider fireShineWeightedBlockStateProvider = new WeightedBlockStateProvider(
                Pool.<BlockState>builder()
                        .add(ModBlocks.FIRESHINE_BERRY_BODY.getDefaultState(), 4)
                        .add(ModBlocks.FIRESHINE_BERRY_BODY.getDefaultState().with(NetherVines.BERRIES, true), 1));

        RandomizedIntBlockStateProvider fireShineRandomizedIntBlockStateProvider = new RandomizedIntBlockStateProvider(
                new WeightedBlockStateProvider(
                        Pool.<BlockState>builder()
                                .add(ModBlocks.FIRESHINE_BERRY_HEAD.getDefaultState(), 4)
                                .add(ModBlocks.FIRESHINE_BERRY_HEAD.getDefaultState().with(NetherVines.BERRIES, true), 1)
                ),
                FireShineBerryHead.AGE,
                UniformIntProvider.create(21, 25)
        );

        register(context, FIRESHINE_BERRY_KEY, Feature.BLOCK_COLUMN, new BlockColumnFeatureConfig(
                List.of(
                        BlockColumnFeatureConfig.createLayer(
                                new WeightedListIntProvider(
                                        Pool.<IntProvider>builder()
                                                .add(UniformIntProvider.create(0, 70), 2)
                                                .add(UniformIntProvider.create(0, 30), 3)
                                                .add(UniformIntProvider.create(0, 10), 10)
                                                .build()
                                ),
                                fireShineWeightedBlockStateProvider
                        ),
                        BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), fireShineRandomizedIntBlockStateProvider)
                ),
                Direction.DOWN,
                BlockPredicate.IS_AIR,
                true
        ));

        register(context, THORNBERRY_KEY, THORNBERRY_GROWTH_CONFIG, new DefaultFeatureConfig());

//        Block multifaceGrowthBlock = ModBlocks.VOID_BERRY_MOSS;
//        ConfiguredFeatures.register(
//                context,
//                VOIDBERRY_KEY,
//                Feature.MULTIFACE_GROWTH,
//                new MultifaceGrowthFeatureConfig(
//                        (MultifaceGrowthBlock) multifaceGrowthBlock,
//                        20,
//                        false,
//                        true,
//                        true,
//                        0.5F,
//                        RegistryEntryList.of(
//                                Block::getRegistryEntry,
//                                Blocks.END_STONE
//                        )
//                )
//        );

    }

    private static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, VeryBerry.id(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key,
                                                                                   F feature, FC featureConfig) {
        context.register(key, new ConfiguredFeature<>(feature, featureConfig));
    }
}
