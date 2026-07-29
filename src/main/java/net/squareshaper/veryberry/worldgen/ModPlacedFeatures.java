package net.squareshaper.veryberry.worldgen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;
import net.squareshaper.veryberry.VeryBerry;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RIMEBERRY_KEY = registerKey("rimeberry");
    public static final RegistryKey<PlacedFeature> RIMEBERRY_PATCH_KEY = registerKey("rimeberry_patch");
    public static final RegistryKey<PlacedFeature> FIRESHINE_BERRY_KEY = registerKey("fireshine_berry");
    public static final RegistryKey<PlacedFeature> THORNBERRY_KEY = registerKey("thornberry");
//    public static final RegistryKey<PlacedFeature> VOIDBERRY_KEY = registerKey("voidberry");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, RIMEBERRY_KEY, registryLookup.getOrThrow(ModConfiguredFeatures.RIMEBERRY_KEY), List.of(
                BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR)));

        register(context, RIMEBERRY_PATCH_KEY, registryLookup.getOrThrow(ModConfiguredFeatures.RIMEBERRY_PATCH_KEY),
                List.of(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        BiomePlacementModifier.of()));

        register(context, FIRESHINE_BERRY_KEY, registryLookup.getOrThrow(ModConfiguredFeatures.FIRESHINE_BERRY_KEY),
                List.of(CountPlacementModifier.of(20),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.belowTop(40), YOffset.TOP),
                        EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.IS_AIR, 20),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
                        BiomePlacementModifier.of()));

        register(context, THORNBERRY_KEY, registryLookup.getOrThrow(ModConfiguredFeatures.THORNBERRY_KEY),
                List.of());

//        register(context, VOIDBERRY_KEY, registryLookup.getOrThrow(ModConfiguredFeatures.VOIDBERRY_KEY),
//                List.of(CountPlacementModifier.of(UniformIntProvider.create(100, 200)),
//                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(40), YOffset.aboveBottom(70)),
//                        SquarePlacementModifier.of(),
//                        BiomePlacementModifier.of()));
    }

    private static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, VeryBerry.id(name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                 RegistryEntry<ConfiguredFeature<?, ?>> config, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(config, List.copyOf(modifiers)));
    }
}
