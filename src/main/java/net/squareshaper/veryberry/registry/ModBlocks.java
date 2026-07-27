package net.squareshaper.veryberry.registry;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.squareshaper.veryberry.VeryBerry;
import net.squareshaper.veryberry.block.*;

import java.util.function.Function;

public class ModBlocks {
    public static final Block RIMEBERRY_BUSH = registerBlockNoItem("rimeberry_bush",
            properties -> new RimeBerryBushBlock(properties.strength(1).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).ticksRandomly()
                    .pistonBehavior(PistonBehavior.DESTROY).nonOpaque().noCollision().luminance(state -> state.get(RimeBerryBushBlock.AGE) == 3 ? 4 : 0)
                    .mapColor(MapColor.DIAMOND_BLUE).breakInstantly()));

    public static final Block FIRESHINE_BERRY_BODY = registerBlockNoItem("fireshine_berry_body",
            properties -> new FireShineBerryBody(properties
                    .mapColor(MapColor.DARK_RED)
                    .noCollision()
                    .luminance(NetherVines.getLuminanceSupplier(13))
                    .breakInstantly()
                    .nonOpaque()
                    .sounds(BlockSoundGroup.CAVE_VINES)
                    .pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block FIRESHINE_BERRY_HEAD = registerBlockNoItem("fireshine_berry_head",
            properties -> new FireShineBerryHead(properties
                    .mapColor(MapColor.DARK_RED)
                    .ticksRandomly()
                    .noCollision()
                    .luminance(NetherVines.getLuminanceSupplier(13))
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CAVE_VINES)
                    .nonOpaque()
                    .pistonBehavior(PistonBehavior.DESTROY)));

    //use glow-lichen generation logic as inspiration for this one
    public static final Block VOID_BERRY_MOSS = registerBlockNoItem("voidberry_moss",
            properties -> new VoidBerryMoss(properties
                    .mapColor(MapColor.PURPLE)
                    .ticksRandomly()
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.LILY_PAD)
                    .pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block THORNBERRY_BRANCH = registerBlockNoItem("thornberry_branch",
            properties -> new ThornBerryBranch(properties.breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).ticksRandomly()
                    .pistonBehavior(PistonBehavior.DESTROY).noCollision().mapColor(MapColor.DARK_GREEN)));

    //Helper functions
    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function) {
        Block toRegister = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, VeryBerry.id(name))));
        registerBlockItem(name, toRegister);
        return Registry.register(Registries.BLOCK, VeryBerry.id(name), toRegister);
    }

    private static Block registerBlockNoItem(String name, Function<AbstractBlock.Settings, Block> function) {
        return Registry.register(Registries.BLOCK, VeryBerry.id(name),
                function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, VeryBerry.id(name)))));
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, VeryBerry.id(name),
                new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, VeryBerry.id(name)))));
    }

    public static void registerModBlocks() {
        VeryBerry.LOGGER.info("Registering Blocks for " + VeryBerry.MOD_ID + "...");
    }
}
