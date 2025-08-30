package com.thatgrey.both_worlds.entity.custom;

import com.thatgrey.both_worlds.entity.ModEntities;
import com.thatgrey.both_worlds.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.animation.*;

public class HyraxEntity extends Animal implements GeoEntity, Sittable {

    private static final EntityDataAccessor<Boolean> DATA_SITTING =
            SynchedEntityData.defineId(HyraxEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT =
            SynchedEntityData.defineId(HyraxEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HyraxEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.FOLLOW_RANGE, 10.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 5.0F, 0.12F, 0.12F));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(Items.HANGING_ROOTS), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 0.6D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new RandomSitGoal<>(this));
    }

    public void setSitting(boolean sitting) { this.entityData.set(DATA_SITTING, sitting); }
    public boolean isSitting() { return this.entityData.get(DATA_SITTING); }

    public int getVariant() { return this.entityData.get(DATA_VARIANT); }
    public void setVariant(int v) { this.entityData.set(DATA_VARIANT, v); }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SITTING, false);
        this.entityData.define(DATA_VARIANT, Variant.BROWN.getId());
    }

    public enum Variant {
        BROWN(0), BEIGE(1), GREY(2);
        private final int id;
        Variant(int id) { this.id = id; }
        public int getId() { return id; }
        public static Variant byId(int i) {
            for (Variant v : values()) if (v.id == i) return v;
            return BROWN;
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (dataTag != null && dataTag.contains("Variant")) {
            this.setVariant(dataTag.getInt("Variant"));
        } else {
            int randomVariant = this.random.nextInt(Variant.values().length);
            this.setVariant(randomVariant);
        }
        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getVariant());
        tag.putBoolean("Sitting", this.isSitting());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setVariant(tag.getInt("Variant"));
        this.setSitting(tag.getBoolean("Sitting"));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, event -> {
            if (isSitting()) {
                return event.setAndContinue(RawAnimation.begin().then("hyrax_sit", Animation.LoopType.LOOP));
            }
            if (event.isMoving()) {
                return event.setAndContinue(RawAnimation.begin().then("hyrax_walk", Animation.LoopType.LOOP));
            }
            return event.setAndContinue(RawAnimation.begin().then("hyrax_idle", Animation.LoopType.LOOP));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }

    public class ModEntitySpawn {
        public static void registerSpawnPlacements() {
            SpawnPlacements.register(
                    ModEntities.HYRAX.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    HyraxEntity::checkHyraxSpawn
            );
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        HyraxEntity baby = ModEntities.HYRAX.get().create(level);
        if (baby != null) {
            baby.setBaby(true);
            int chosenVariant = this.random.nextBoolean() ? this.getVariant() : ((HyraxEntity) partner).getVariant();
            baby.setVariant(chosenVariant);
        }
        return baby;
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) { return stack.is(Items.HANGING_ROOTS); }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (isFood(item) && !this.isBaby()) {
            if (!player.getAbilities().instabuild) item.shrink(1);
            this.setInLove(player);
            this.level().broadcastEntityEvent(this, (byte)18);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        EntityDimensions original = super.getDimensions(pose);
        if (this.isBaby()) {
            return EntityDimensions.scalable(original.width * 0.12F, original.height * 0.12F);
        }
        return original;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() { return ModSounds.HYRAX_ANGRY.get(); }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public static boolean checkHyraxSpawn(EntityType<HyraxEntity> entity, LevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entity, level, reason, pos, random);
    }
}
