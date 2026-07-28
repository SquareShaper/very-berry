package net.squareshaper.veryberry.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.squareshaper.veryberry.block.*;
import net.squareshaper.veryberry.registry.ModBlocks;
import net.squareshaper.veryberry.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RIMEBERRY_BUSH, berryBushDrops(ModBlocks.RIMEBERRY_BUSH, ModItems.RIMEBERRIES, RimeBerryBushBlock.getMinDrops(),
                RimeBerryBushBlock.getMaxDrops(), RimeBerryBushBlock.getHarvestAge()));

        addDrop(ModBlocks.FIRESHINE_BERRY_BODY, fireshineBerryDrops(ModBlocks.FIRESHINE_BERRY_BODY, ModItems.FIRESHINE_BERRIES, 1, 3));
        addDrop(ModBlocks.FIRESHINE_BERRY_HEAD, fireshineBerryDrops(ModBlocks.FIRESHINE_BERRY_HEAD, ModItems.FIRESHINE_BERRIES, 1, 3));

        addDrop(ModBlocks.VOID_BERRY_MOSS, berryBushDropsAge(ModBlocks.VOID_BERRY_MOSS, ModItems.VOIDBERRIES, VoidBerryMoss.getMinDrops(),
                VoidBerryMoss.getMaxDrops(), VoidBerryMoss.getHarvestAge()));

        addDrop(ModBlocks.THORNBERRY_BRANCH, berryBushDropsAge(ModBlocks.THORNBERRY_BRANCH, ModItems.THORNBERRIES, ThornBerryBranch.getMinDrops(),
                ThornBerryBranch.getMaxDrops(), ThornBerryBranch.getHarvestAge()));
    }

    public LootTable.Builder fireshineBerryDrops(Block block, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);

        return this.applyExplosionDecay(block, LootTable.builder()
                .pool(LootPool.builder().conditionally(
                                BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(NetherVines.BERRIES, true)))
                        .with(ItemEntry.builder(item))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    public LootTable.Builder berryBushDrops(Block block, Item item, float minDrops, float maxDrops, int harvestAge) {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);

        return this.applyExplosionDecay(block, LootTable.builder()
                .pool(LootPool.builder().conditionally(
                                BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(BerryBushBlock.AGE, harvestAge)))
                        .with(ItemEntry.builder(item))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                )
                .pool(LootPool.builder().conditionally(
                                BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(BerryBushBlock.AGE, harvestAge-1)))
                        .with(ItemEntry.builder(item))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    public LootTable.Builder berryBushDropsAge(Block block, Item item, float minDrops, float maxDrops, int harvestAge) {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);

        return this.applyExplosionDecay(block, LootTable.builder()
                .pool(LootPool.builder().conditionally(
                                BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(BerryBushBlock.AGE, harvestAge)))
                        .with(ItemEntry.builder(item))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }


}
