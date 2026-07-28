package net.squareshaper.veryberry.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.squareshaper.veryberry.registry.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(BlockTags.SWORD_EFFICIENT)
                .add(ModBlocks.RIMEBERRY_BUSH)
                .add(ModBlocks.FIRESHINE_BERRY_BODY)
                .add(ModBlocks.FIRESHINE_BERRY_HEAD)
                .add(ModBlocks.VOID_BERRY_MOSS)
                .add(ModBlocks.THORNBERRY_BRANCH);

        valueLookupBuilder(BlockTags.CLIMBABLE)
                .add(ModBlocks.FIRESHINE_BERRY_BODY)
                .add(ModBlocks.FIRESHINE_BERRY_HEAD)
                .add(ModBlocks.VOID_BERRY_MOSS);
    }
}
