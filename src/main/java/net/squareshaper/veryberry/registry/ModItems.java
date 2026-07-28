package net.squareshaper.veryberry.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.squareshaper.veryberry.VeryBerry;
import net.squareshaper.veryberry.component.EffectFoodComponent;
import net.squareshaper.veryberry.component.TextDescriptionComponent;
import net.squareshaper.veryberry.item.BundledThornberryChips;

import java.util.function.Function;

public class ModItems {
    //Berries
    public static final Item RIMEBERRIES = registerItem("rimeberries", setting -> new BlockItem(ModBlocks.RIMEBERRY_BUSH,
            setting.food(ModFoodComponents.RIMEBERRIES, ModFoodComponents.RIMEBERRIES_EFFECT).useItemPrefixedTranslationKey()
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())));

    public static final Item FIRESHINE_BERRIES = registerItem("fireshine_berries", setting -> new BlockItem(ModBlocks.FIRESHINE_BERRY_HEAD,
            setting.food(ModFoodComponents.FIRESHINE_BERRIES, ModFoodComponents.FIRESHINE_BERRIES_EFFECT).useItemPrefixedTranslationKey()
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())));

    public static final Item VOIDBERRIES = registerItem("voidberries", setting -> new BlockItem(ModBlocks.VOID_BERRY_MOSS,
            setting.food(ModFoodComponents.VOIDBERRIES, ModFoodComponents.VOIDBERRIES_EFFECT).useItemPrefixedTranslationKey()
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())));

    //Remember to change the block back to ModBlocks.CHRONOBERRY_PLANT!
    public static final Item CHRONOBERRIES = registerItem("chronoberries", setting -> new BlockItem(ModBlocks.RIMEBERRY_BUSH,
            setting.food(ModFoodComponents.CHRONOBERRIES, ModFoodComponents.CHRONOBERRIES_EFFECT).useItemPrefixedTranslationKey()
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())));

    public static final Item THORNBERRIES = registerItem("thornberries", setting -> new BlockItem(ModBlocks.THORNBERRY_BRANCH,
            setting.food(ModFoodComponents.THORNBERRIES, ModFoodComponents.THORNBERRIES_EFFECT).useItemPrefixedTranslationKey()
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())));

    //Berry Foods
    public static final Item FIRESHINE_BERRY_JUICE = registerItem("fireshine_berry_juice", setting -> new Item(
            setting.food(ModFoodComponents.FIRESHINE_BERRY_JUICE, ModFoodComponents.FIRESHINE_BERRY_JUICE_EFFECT)
                    .useRemainder(Items.GLASS_BOTTLE).component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())
                    .component(ModDataComponentTypes.TEXT_DESCRIPTION, new TextDescriptionComponent("item.very-berry.fireshine_berry_juice.tooltip"))));


    public static final Item RIMEBERRY_MUFFIN = registerItem("rimeberry_muffin", setting -> new Item(
            setting.food(ModFoodComponents.RIMEBERRY_MUFFIN, ModFoodComponents.RIMEBERRY_MUFFIN_EFFECT)
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())
                    .recipeRemainder(Items.BUCKET)
                    .component(ModDataComponentTypes.TEXT_DESCRIPTION, new TextDescriptionComponent("item.very-berry.rimeberry_muffin.tooltip"))));

    public static final Item THORNBERRY_SLICE = registerItem("thornberry_slice", setting -> new Item(
            setting.food(ModFoodComponents.THORNBERRY_SLICE, ModFoodComponents.THORNBERRY_SLICE_EFFECT)
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())
                    .component(ModDataComponentTypes.TEXT_DESCRIPTION, new TextDescriptionComponent("item.very-berry.thornberry_slice.tooltip"))));

    public static final Item THORNBERRY_CHIP = registerItem("thornberry_chip", setting -> new Item(
            setting.food(ModFoodComponents.THORNBERRY_CHIP, ModFoodComponents.THORNBERRY_CHIP_EFFECT)
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())
                    .component(ModDataComponentTypes.TEXT_DESCRIPTION, new TextDescriptionComponent("item.very-berry.thornberry_chip.tooltip"))));

    public static final Item BUNDLED_THORNBERRY_CHIPS = registerItem("bundled_thornberry_chips", setting -> new BundledThornberryChips(
            setting.food(ModFoodComponents.BUNDLED_THORNBERRY_CHIPS, ModFoodComponents.BUNDDLED_THORNBERRY_CHIPS_EFFECT)
                    .component(ModDataComponentTypes.EFFECT_FOOD, new EffectFoodComponent())
                    .component(ModDataComponentTypes.TEXT_DESCRIPTION, new TextDescriptionComponent("item.very-berry.bundled_thornberry_chips.tooltip"))
                    .maxDamage(32).maxCount(1)));



    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, VeryBerry.id(name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, VeryBerry.id(name)))));
    }

    public static void registerModItems() {
        VeryBerry.LOGGER.info("Registering Items for " + VeryBerry.MOD_ID + "...");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.GLOW_BERRIES, ModItems.CHRONOBERRIES);
            entries.addAfter(Items.GLOW_BERRIES, ModItems.VOIDBERRIES);
            entries.addAfter(Items.GLOW_BERRIES, ModItems.THORNBERRIES);
            entries.addAfter(Items.GLOW_BERRIES, ModItems.RIMEBERRIES);
            entries.addAfter(Items.GLOW_BERRIES, ModItems.FIRESHINE_BERRIES);
            entries.addAfter(Items.PUMPKIN_PIE, ModItems.BUNDLED_THORNBERRY_CHIPS);
            entries.addAfter(Items.PUMPKIN_PIE, ModItems.THORNBERRY_CHIP);
            entries.addAfter(Items.PUMPKIN_PIE, ModItems.THORNBERRY_SLICE);
            entries.addAfter(Items.PUMPKIN_PIE, ModItems.RIMEBERRY_MUFFIN);
            entries.addAfter(Items.PUMPKIN_PIE, ModItems.FIRESHINE_BERRY_JUICE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.SWEET_BERRIES, ModItems.VOIDBERRIES);
            entries.addAfter(Items.SWEET_BERRIES, ModItems.THORNBERRIES);
            entries.addAfter(Items.SWEET_BERRIES, ModItems.RIMEBERRIES);
            entries.addAfter(Items.SWEET_BERRIES, ModItems.FIRESHINE_BERRIES);
        });
    }
}
