package net.squareshaper.veryberry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BundledThornberryChips extends Item {
    public BundledThornberryChips(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack.copy(), world, user); // Consume a copy of the stack, so the real one doesn't get removed from inventory
        if (user instanceof PlayerEntity player) {
            stack.damage(1, player); // damage the real stack
        }
        return stack; // return the newly damaged stack
    }
}