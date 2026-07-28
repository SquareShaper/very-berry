package net.squareshaper.veryberry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import net.squareshaper.veryberry.registry.ModBlocks;
import net.squareshaper.veryberry.registry.ModItems;
import org.jetbrains.annotations.Nullable;

public class ThornBerryBranch extends HorizontalFacingBlock implements Fertilizable {
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;
    public static final MapCodec<ThornBerryBranch> CODEC = createCodec(ThornBerryBranch::new);
    private static final SoundEvent PICK_SOUND = SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES;

    //the base model
    protected static final Vec3d minCube0 = new Vec3d(5, 3, 13);
    protected static final Vec3d maxCube0 = new Vec3d(11, 9, 17);
    protected static final Vec3d minCube1 = new Vec3d(5, 3, 10);
    protected static final Vec3d maxCube1 = new Vec3d(11, 11, 17);
    protected static final Vec3d minCube2 = new Vec3d(5, 3, 10);
    protected static final Vec3d maxCube2 = new Vec3d(11, 12, 17);
    protected static final Vec3d minCube3 = new Vec3d(5, 3, 10);
    protected static final Vec3d maxCube3 = new Vec3d(11, 14, 17);


    protected static final VoxelShape[] AGE_TO_EAST_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(minCube0.getZ(), minCube0.getY(), minCube0.getX(), maxCube0.getZ(), maxCube0.getY(), maxCube0.getX()),
            Block.createCuboidShape(minCube1.getZ(), minCube1.getY(), minCube1.getX(), maxCube1.getZ(), maxCube1.getY(), maxCube1.getX()),
            Block.createCuboidShape(minCube2.getZ(), minCube2.getY(), minCube2.getX(), maxCube2.getZ(), maxCube2.getY(), maxCube2.getX()),
            Block.createCuboidShape(minCube3.getZ(), minCube3.getY(), minCube3.getX(), maxCube3.getZ(), maxCube3.getY(), maxCube3.getX())
    };
    protected static final VoxelShape[] AGE_TO_SOUTH_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(minCube0.getX(), minCube0.getY(), minCube0.getZ(), maxCube0.getX(), maxCube0.getY(), maxCube0.getZ()),
            Block.createCuboidShape(minCube1.getX(), minCube1.getY(), minCube1.getZ(), maxCube1.getX(), maxCube1.getY(), maxCube1.getZ()),
            Block.createCuboidShape(minCube2.getX(), minCube2.getY(), minCube2.getZ(), maxCube2.getX(), maxCube2.getY(), maxCube2.getZ()),
            Block.createCuboidShape(minCube3.getX(), minCube3.getY(), minCube3.getZ(), maxCube3.getX(), maxCube3.getY(), maxCube3.getZ())
    };
    protected static final VoxelShape[] AGE_TO_NORTH_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(minCube0.getX(), minCube0.getY(), 16 - maxCube0.getZ(), maxCube0.getX(), maxCube0.getY(), 16 - minCube0.getZ()),
            Block.createCuboidShape(minCube1.getX(), minCube1.getY(), 16 - maxCube1.getZ(), maxCube1.getX(), maxCube1.getY(), 16 - minCube1.getZ()),
            Block.createCuboidShape(minCube2.getX(), minCube2.getY(), 16 - maxCube2.getZ(), maxCube2.getX(), maxCube2.getY(), 16 - minCube2.getZ()),
            Block.createCuboidShape(minCube3.getX(), minCube3.getY(), 16 - maxCube3.getZ(), maxCube3.getX(), maxCube3.getY(), 16 - minCube3.getZ())
    };
    protected static final VoxelShape[] AGE_TO_WEST_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(16 - maxCube0.getZ(), minCube0.getY(), minCube0.getX(), 16 - minCube0.getZ(), maxCube0.getY(), maxCube0.getX()),
            Block.createCuboidShape(16 - maxCube1.getZ(), minCube1.getY(), minCube1.getX(), 16 - minCube1.getZ(), maxCube1.getY(), maxCube1.getX()),
            Block.createCuboidShape(16 - maxCube2.getZ(), minCube2.getY(), minCube2.getX(), 16 - minCube2.getZ(), maxCube2.getY(), maxCube2.getX()),
            Block.createCuboidShape(16 - maxCube3.getZ(), minCube3.getY(), minCube3.getX(), 16 - minCube3.getZ(), maxCube3.getY(), maxCube3.getX())
    };

    public ThornBerryBranch(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(AGE, 0));
    }

    public static float getMinDrops() {
        return 1;
    }

    public static float getMaxDrops() {
        return 4;
    }

    public static int getHarvestAge() {
        return MAX_AGE;
    }

    public Item getBerryDrop() {
        return ModItems.THORNBERRIES;
    }


    //make sure that Bonemeal actually grows it fully
    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean bl = i == 3;
        return !bl && stack.isOf(Items.BONE_MEAL)
                ? ActionResult.FAIL
                : super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int i = state.get(AGE);
        if (i == MAX_AGE) {
            int j = 2 + world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(getBerryDrop(), j));
            world.playSound(null, pos, PICK_SOUND, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            BlockState blockState = state.with(AGE, 1);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    private boolean canGrowAtHeight(ServerWorld world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock().equals(Blocks.CACTUS)) {
            return world.getBlockState(pos.east()).getBlock() != ModBlocks.THORNBERRY_BRANCH &
                    world.getBlockState(pos.west()).getBlock() != ModBlocks.THORNBERRY_BRANCH &
                    world.getBlockState(pos.north()).getBlock() != ModBlocks.THORNBERRY_BRANCH &
                    world.getBlockState(pos.south()).getBlock() != ModBlocks.THORNBERRY_BRANCH;
        }
        return false;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.random.nextInt(5) == 0) {
            int i = state.get(AGE);
            if (i < MAX_AGE) {
                world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            }
            if (i == MAX_AGE) {
                Direction[] directions = {Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                Direction direction = directions[world.random.nextBetween(0,1)];
                BlockPos cactusStem = pos.offset(direction).offset(state.get(FACING)); // get cactus stem above or below
                if (canGrowAtHeight(world, cactusStem)) { // use helper to check all four blocks around the cactus block
                    Direction facing = directions[world.random.nextBetween(2,5)];
                    BlockPos growthSpot = cactusStem.offset(facing); // pick one of the four sides of the cactus
                    if (world.getBlockState(growthSpot).isAir()) {
                        // Place the new ThornBerryBranch, if you're in air
                        world.setBlockState(growthSpot, this.getDefaultState().with(FACING, facing.getOpposite()));
                    }
                }
            }
        }
    }

    @Override
    public MapCodec<ThornBerryBranch> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = state.get(AGE);
        return switch (state.get(FACING)) {
            case SOUTH -> AGE_TO_SOUTH_SHAPE[i];
            case WEST -> AGE_TO_WEST_SHAPE[i];
            case EAST -> AGE_TO_EAST_SHAPE[i];
            default -> AGE_TO_NORTH_SHAPE[i];
        };
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.offset(state.get(FACING)));
        //gonna have to change the randomTick() code as well, if I want to change what it grows on
        return blockState.getBlock() == Blocks.CACTUS;
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), Block.NOTIFY_LISTENERS);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();

        for (Direction direction : ctx.getPlacementDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockState = blockState.with(FACING, direction);
                if (blockState.canPlaceAt(worldView, blockPos)) {
                    return blockState;
                }
            }
        }

        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return direction == state.get(FACING) && !state.canPlaceAt(world, pos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }
}
