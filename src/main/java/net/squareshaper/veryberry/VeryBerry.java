package net.squareshaper.veryberry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.squareshaper.veryberry.registry.*;
import net.squareshaper.veryberry.worldgen.ModBiomeModifiers;
import net.squareshaper.veryberry.worldgen.ThornberryBranchFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.join;
import static java.util.Collections.nCopies;

public class VeryBerry implements ModInitializer {
	public static final String MOD_ID = "very-berry";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Feature<DefaultFeatureConfig> THORNBERRY_GROWTH_CONFIG = Registry.register(Registries.FEATURE, "thornberry_growth", new ThornberryBranchFeature(DefaultFeatureConfig.CODEC));


	public static Identifier id(String name){
		return Identifier.of(MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModFoodComponents.registerModFoodComponents();
		ModBiomeModifiers.registerBiomeModifiers();
		ModEffects.registerModEffects();

		CompostingChanceRegistry.INSTANCE.add(ModItems.RIMEBERRIES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.FIRESHINE_BERRIES, 0.4f);

		ComponentTooltipAppenderRegistry.addLast(ModDataComponentTypes.EFFECT_FOOD);
		ComponentTooltipAppenderRegistry.addLast(ModDataComponentTypes.TEXT_DESCRIPTION);
	}

	public static String addZeroIfOneDigit(int number) {
		if (number > 9) {
			return String.valueOf(number);
		}
		else {
			return "0" + number;
		}
	}

	//conversion logic courtesy of https://stackoverflow.com/a/39411250
	public static String romanNumeralsOrEmpty(int number) {
		if (number == 0) {
			return "";
		}
		else {
			return " " + join("", nCopies(number + 1, "I"))
					.replace("IIIII", "V")
					.replace("IIII", "IV")
					.replace("VV", "X")
					.replace("VIV", "IX")
					.replace("XXXXX", "L")
					.replace("XXXX", "XL")
					.replace("LL", "C")
					.replace("LXL", "XC")
					.replace("CCCCC", "D")
					.replace("CCCC", "CD")
					.replace("DD", "M")
					.replace("DCD", "CM");
		}
	}

	public static String tickToTime(int ticks) {
		int hour = 72000;
		int minute = 1200;
		int second = 20;

		String output = "";

		if (ticks >= hour) {
			int hours = Math.floorDiv(ticks, hour);
			ticks = ticks - hours * hour;
			int minutes = Math.floorDiv(ticks, minute);
			ticks = ticks - minutes * minute;
			int seconds = Math.floorDiv(ticks, second);
			output = hours + ":" + addZeroIfOneDigit(minutes) + ":" +  addZeroIfOneDigit(seconds);
		} else if (ticks >= minute) {
			int minutes = Math.floorDiv(ticks, minute);
			ticks = ticks - minutes * minute;
			int seconds = Math.floorDiv(ticks, second);
			output = addZeroIfOneDigit(minutes) + ":" +  addZeroIfOneDigit(seconds);
		} else if (ticks >= second){
			int seconds = Math.floorDiv(ticks, second);
			output = "00:" +  addZeroIfOneDigit(seconds);
		}
		return output;
	}
}