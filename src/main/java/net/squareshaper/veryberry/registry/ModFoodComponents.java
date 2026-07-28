package net.squareshaper.veryberry.registry;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;
import net.squareshaper.veryberry.VeryBerry;

public class ModFoodComponents {
    public static final FoodComponent RIMEBERRIES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent RIMEBERRIES_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 0), 1f)).build();

    public static final FoodComponent RIMEBERRY_MUFFIN = new FoodComponent.Builder().nutrition(6).saturationModifier(0.8f).alwaysEdible().build();

    public static final ConsumableComponent RIMEBERRY_MUFFIN_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 2400, 0), 1f)).build();

    public static final FoodComponent FIRESHINE_BERRIES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent FIRESHINE_BERRIES_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0), 1f)).build();

    public static final FoodComponent FIRESHINE_BERRY_JUICE = new FoodComponent.Builder().nutrition(6).saturationModifier(0.5f).alwaysEdible().build();

    public static final ConsumableComponent FIRESHINE_BERRY_JUICE_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 2400, 0), 1f))
            .consumeSeconds(1.6F).useAction(UseAction.DRINK).sound(SoundEvents.ENTITY_GENERIC_DRINK).consumeParticles(false).build();

    public static final FoodComponent VOIDBERRIES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent VOIDBERRIES_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.VOID_SKIPPER, 1200, 0), 1f)).build();

    public static final FoodComponent CHRONOBERRIES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent CHRONOBERRIES_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.RETURN, 400, 0), 1f)).build();

    public static final FoodComponent THORNBERRIES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent THORNBERRIES_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.THORNY, 400, 0), 1f)).build();

    public static final FoodComponent THORNBERRY_SLICE = new FoodComponent.Builder().nutrition(1).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent THORNBERRY_SLICE_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.THORNY, 100, 0), 1f)).build();

    public static final FoodComponent THORNBERRY_CHIP = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent THORNBERRY_CHIP_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.THORNY, 400, 0), 1f)).build();

    public static final FoodComponent THORNBERRY_CHIPS = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().build();

    public static final ConsumableComponent THORNBERRY_CHIPS_EFFECT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.THORNY, 1600, 0), 1f)).build();

    public static void registerModFoodComponents() {
        VeryBerry.LOGGER.info("Registering Food Components for " + VeryBerry.MOD_ID + "...");
    }
}
