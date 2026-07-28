package net.squareshaper.veryberry.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.squareshaper.veryberry.registry.ModItems;


import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            public void generate() {
                //Berry Foods
                createShapeless(RecipeCategory.FOOD, ModItems.FIRESHINE_BERRY_JUICE)
                        .input(ModItems.FIRESHINE_BERRIES)
                        .input(ModItems.FIRESHINE_BERRIES)
                        .input(Items.SUGAR)
                        .input(ModItems.FIRESHINE_BERRIES)
                        .input(ModItems.FIRESHINE_BERRIES)
                        .input(Items.GLASS_BOTTLE)
                        .criterion(hasItem(ModItems.FIRESHINE_BERRIES), conditionsFromItem(ModItems.FIRESHINE_BERRIES))
                        .offerTo(recipeExporter);

                createShapeless(RecipeCategory.FOOD, ModItems.RIMEBERRY_MUFFIN, 2)
                        .input(ModItems.RIMEBERRIES)
                        .input(ModItems.RIMEBERRIES)
                        .input(Items.WHEAT)
                        .input(Items.WHEAT)
                        .input(Items.MILK_BUCKET)
                        .criterion(hasItem(ModItems.FIRESHINE_BERRIES), conditionsFromItem(ModItems.FIRESHINE_BERRIES))
                        .offerTo(recipeExporter);

                createShapeless(RecipeCategory.FOOD, ModItems.THORNBERRY_SLICE, 4)
                        .input(ModItems.THORNBERRIES)
                        .criterion(hasItem(ModItems.THORNBERRIES), conditionsFromItem(ModItems.THORNBERRIES))
                        .offerTo(recipeExporter);


                offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, ModItems.THORNBERRY_SLICE,
                        ModItems.THORNBERRY_CHIP, 0.3f);

                offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 100, ModItems.THORNBERRY_SLICE,
                        ModItems.THORNBERRY_CHIP, 0.3f);

                createShaped(RecipeCategory.FOOD, ModItems.BUNDLED_THORNBERRY_CHIPS)
                        .input('#', ModItems.THORNBERRY_CHIP)
                        .input('x', ItemTags.BUNDLES)
                        .pattern("###")
                        .pattern("#x#")
                        .pattern("###")
                        .criterion(hasItem(ModItems.THORNBERRY_CHIP), conditionsFromItem(ModItems.THORNBERRY_CHIP))
                        .offerTo(recipeExporter);
            }

            // making the food cooking recipes work courtesy of
            // https://github.com/ivangeevo/vegehenna/
            // Open source is awesome!
            public <T extends AbstractCookingRecipe> void offerFoodCookingRecipe(
                    RecipeExporter exporter,
                    String cooker,
                    RecipeSerializer<T> serializer,
                    AbstractCookingRecipe.RecipeFactory<T> recipeFactory,
                    int cookingTime,
                    ItemConvertible input,
                    ItemConvertible output,
                    float experience
            ) {
                CookingRecipeJsonBuilder.create(Ingredient.ofItem(input), RecipeCategory.FOOD, output, experience, cookingTime, serializer, recipeFactory)
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .offerTo(exporter, getItemPath(output) + "_from_" + cooker);
            }
        };
    }

    @Override
    public String getName() {
        return "VeryBerryRecipes";
    }
}
