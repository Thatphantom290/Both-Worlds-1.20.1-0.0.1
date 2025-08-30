package com.thatgrey.both_worlds.entity.custom;

import com.thatgrey.both_worlds.entity.ModEntities;
import com.thatgrey.both_worlds.entity.animations.ModAnimations;
import com.thatgrey.both_worlds.sound.ModSounds;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MarmotEntity extends Animal implements GeoEntity, Sittable {

    private static final EntityDataAccessor<Boolean> DATA_SITTING =
            SynchedEntityData.defineId(MarmotEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT =
            SynchedEntityData.defineId(MarmotEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MarmotEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.FOLLOW_RANGE, 8D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 0.85D));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.150D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(Items.HANGING_ROOTS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.6D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.25D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomSitGoal<>(this));
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(DATA_SITTING, sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(DATA_SITTING);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variantId) {
        this.entityData.set(DATA_VARIANT, variantId);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SITTING, false);
        this.entityData.define(DATA_VARIANT, Variant.BROWN.getId());
    }

    public enum Variant {
        BROWN(0), BEIGE(1), GREY(2), WHITE(3);

        private final int id;
        Variant(int id) { this.id = id; }
        public int getId() { return id; }
        public static Variant byId(int id) {
            for (Variant v : values()) if (v.id == id) return v;
            return BROWN;
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor world,
            DifficultyInstance difficulty,
            MobSpawnType reason,
            @Nullable SpawnGroupData spawnData,
            @Nullable CompoundTag dataTag
    ) {
        Holder<Biome> biome = world.getBiome(this.blockPosition());

        if (biome.is(Biomes.PLAINS) || biome.is(Biomes.MEADOW)) {
            this.setVariant(Variant.BROWN.getId());
        } else if (biome.is(Biomes.TAIGA) || biome.is(Biomes.SNOWY_TAIGA)) {
            this.setVariant(Variant.GREY.getId());
        } else if (biome.is(Biomes.WINDSWEPT_HILLS) || biome.is(Biomes.WINDSWEPT_GRAVELLY_HILLS)
                || biome.is(Biomes.SNOWY_SLOPES) || biome.is(Biomes.JAGGED_PEAKS) || biome.is(Biomes.FROZEN_PEAKS)) {
            this.setVariant(world.getRandom().nextFloat() < 0.1F ? Variant.WHITE.getId() : Variant.GREY.getId());
        } else if (biome.is(Biomes.SAVANNA) || biome.is(Biomes.SAVANNA_PLATEAU)) {
            this.setVariant(Variant.BEIGE.getId());
        }

        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setVariant(tag.getInt("Variant"));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, event -> {
            if (isSitting()) return event.setAndContinue(ModAnimations.MARMOT_SIT);
            if (event.isMoving()) return event.setAndContinue(ModAnimations.MARMOT_WALK);
            return event.setAndContinue(ModAnimations.MARMOT_IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        Entity babyEntity = this.getType().create(level);
        if (!(babyEntity instanceof MarmotEntity)) return null;
        MarmotEntity baby = (MarmotEntity) babyEntity;
        baby.setBaby(true);
        return baby;
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return stack.is(Items.HANGING_ROOTS);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.MARMOT_IDLE.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource source) {
        return ModSounds.MARMOT_HURT1.get();
    }

    @Override
    public SoundEvent getEatingSound(ItemStack stack) {
        return ModSounds.MARMOT_HAPPY.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.MARMOT_DEATH.get();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isFood(itemstack) && !this.isBaby()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
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
            return EntityDimensions.scalable(original.width * 0.35F, original.height * 0.35F);
        }
        return original;
    }
}
