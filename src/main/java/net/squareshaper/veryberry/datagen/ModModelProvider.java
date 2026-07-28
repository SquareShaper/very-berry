package net.squareshaper.veryberry.datagen;


import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.squareshaper.veryberry.registry.ModBlocks;
import net.squareshaper.veryberry.registry.ModItems;

import static net.minecraft.client.data.BlockStateModelGenerator.createBooleanModelMap;
import static net.minecraft.client.data.BlockStateModelGenerator.createWeightedVariant;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //Rimeberry Bush
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(ModBlocks.RIMEBERRY_BUSH)
                .with(BlockStateVariantMap.models(Properties.AGE_3).generate(stage -> createWeightedVariant(blockStateModelGenerator
                        .createSubModel(ModBlocks.RIMEBERRY_BUSH, "_age" + stage, Models.CROSS, TextureMap::cross)))));

        //Fireshine Berry Bottom
        WeightedVariant weightedVariant = createWeightedVariant(blockStateModelGenerator.createSubModel(ModBlocks.FIRESHINE_BERRY_HEAD,
                "", Models.CROSS, TextureMap::cross));
        WeightedVariant weightedVariant2 = createWeightedVariant(blockStateModelGenerator.createSubModel(ModBlocks.FIRESHINE_BERRY_HEAD,
                "_lit", Models.CROSS, TextureMap::cross));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(ModBlocks.FIRESHINE_BERRY_HEAD).with(
                createBooleanModelMap(Properties.BERRIES, weightedVariant2, weightedVariant)));

        //Fireshine Berry Body
        WeightedVariant weightedVariant3 = createWeightedVariant(blockStateModelGenerator.createSubModel(ModBlocks.FIRESHINE_BERRY_BODY,
                "", Models.CROSS, TextureMap::cross));
        WeightedVariant weightedVariant4 = createWeightedVariant(blockStateModelGenerator.createSubModel(ModBlocks.FIRESHINE_BERRY_BODY,
                "_lit", Models.CROSS, TextureMap::cross));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(ModBlocks.FIRESHINE_BERRY_BODY).with(
                createBooleanModelMap(Properties.BERRIES, weightedVariant4, weightedVariant3)));

        //Void Berry Moss - currently moved to blockstates folder, hard coded json
//        blockStateModelGenerator.blockStateCollector
//                .accept(VariantsBlockStateSupplier.create(ModBlocks.VOID_BERRY_MOSS)
//                                .coordinate(BlockStateVariantMap.create(Properties.AGE_3)
//                                                .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(ModBlocks.VOID_BERRY_MOSS, "_stage0")))
//                                                .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(ModBlocks.VOID_BERRY_MOSS, "_stage1")))
//                                                .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(ModBlocks.VOID_BERRY_MOSS, "_stage2")))
//                                                .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(ModBlocks.VOID_BERRY_MOSS, "_stage3")))
//                                ).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates())
//                );


//        hard code as JSON as well...
//        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(ModBlocks.THORNBERRY_BRANCH)
//                .with(BlockStateVariantMap.models(Properties.AGE_3).generate(stage -> createWeightedVariant(blockStateModelGenerator
//                        .createSubModel(ModBlocks.THORNBERRY_BRANCH, "_age" + stage, Models.CROSS, TextureMap::cross)))));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        //Berries
        itemModelGenerator.register(ModItems.RIMEBERRIES, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRESHINE_BERRIES, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOIDBERRIES, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHRONOBERRIES, Models.GENERATED);
        itemModelGenerator.register(ModItems.THORNBERRIES, Models.GENERATED);
        //Berry Foods
        itemModelGenerator.register(ModItems.FIRESHINE_BERRY_JUICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RIMEBERRY_MUFFIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.THORNBERRY_SLICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.THORNBERRY_CHIP, Models.GENERATED);
        itemModelGenerator.register(ModItems.THORNBERRY_CHIPS, Models.GENERATED);
        itemModelGenerator.register(ModItems.BUNDLED_THORNBERRY_CHIPS, Models.GENERATED);
    }
}
