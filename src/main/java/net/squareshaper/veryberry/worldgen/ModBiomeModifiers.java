package net.squareshaper.veryberry.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.squareshaper.veryberry.VeryBerry;

import java.util.List;

public class ModBiomeModifiers {
    public static void registerBiomeModifiers() {
        VeryBerry.LOGGER.info("Registering Biome Modifiers for " + VeryBerry.MOD_ID + "...");
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.GROVE,
                BiomeKeys.SNOWY_SLOPES, BiomeKeys.ICE_SPIKES), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RIMEBERRY_PATCH_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES), GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.FIRESHINE_BERRY_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(List.of(BiomeKeys.DESERT, BiomeKeys.BADLANDS)), GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.THORNBERRY_KEY);

//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SMALL_END_ISLANDS), GenerationStep.Feature.VEGETAL_DECORATION,
//                ModPlacedFeatures.VOIDBERRY_KEY);
    }
}
