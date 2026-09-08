package org.studio4sv.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SparkSphere extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> DATA_XP_LEVEL =
            SynchedEntityData.defineId(SparkSphere.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_XP_PROGRESS =
            SynchedEntityData.defineId(SparkSphere.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID =
            SynchedEntityData.defineId(SparkSphere.class, EntityDataSerializers.OPTIONAL_UUID);

    public SparkSphere(EntityType<? extends SparkSphere> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.setNoAi(true);
    }

    public SparkSphere(EntityType<? extends SparkSphere> type, Level level,
                       UUID owner, int xpLevel, float xpProgress) {
        this(type, level);
        this.entityData.set(DATA_OWNER_UUID, Optional.of(owner));
        this.entityData.set(DATA_XP_LEVEL, xpLevel);
        this.entityData.set(DATA_XP_PROGRESS, xpProgress);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_XP_LEVEL, 0);
        builder.define(DATA_XP_PROGRESS, 0.0F);
        builder.define(DATA_OWNER_UUID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.entityData.get(DATA_OWNER_UUID).ifPresent(uuid -> {
            tag.putLong("OwnerMostSig", uuid.getMostSignificantBits());
            tag.putLong("OwnerLeastSig", uuid.getLeastSignificantBits());
        });
        tag.putInt("StoredXpLevel", this.entityData.get(DATA_XP_LEVEL));
        tag.putFloat("StoredXpProgress", this.entityData.get(DATA_XP_PROGRESS));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("OwnerMostSig") && tag.contains("OwnerLeastSig")) {
            this.entityData.set(DATA_OWNER_UUID,
                    Optional.of(new UUID(tag.getLong("OwnerMostSig"), tag.getLong("OwnerLeastSig"))));
        }
        this.entityData.set(DATA_XP_LEVEL, tag.getInt("StoredXpLevel"));
        this.entityData.set(DATA_XP_PROGRESS, tag.getFloat("StoredXpProgress"));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            List<Player> players = this.level().getEntitiesOfClass(Player.class,
                    this.getBoundingBox(), p -> true);
            for (Player player : players) {
                collectXp(player);
                this.discard();
                return;
            }
        }
    }

    private void collectXp(Player player) {
        int xpLevel = this.entityData.get(DATA_XP_LEVEL);
        float xpProgress = this.entityData.get(DATA_XP_PROGRESS);

        int storedTotal = getTotalXpToLevel(xpLevel)
                + (int) (xpProgress * getXpNeededForNextLevel(xpLevel));

        player.giveExperiencePoints(storedTotal);
    }

    public Optional<UUID> getOwnerUUID() {
        return this.entityData.get(DATA_OWNER_UUID);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state ->
                state.setAndContinue(RawAnimation.begin().thenLoop("main"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean isNoAi() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity entity) {
        return false;
    }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    private static int getXpNeededForNextLevel(int level) {
        if (level <= 15) return 2 * level + 7;
        if (level <= 31) return 5 * level - 38;
        return 9 * level - 158;
    }

    private static int getTotalXpToLevel(int level) {
        if (level <= 15) return level * level + 6 * level;
        if (level <= 31) return (int) (2.5 * level * level - 40.5 * level + 360);
        return (int) (4.5 * level * level - 162.5 * level + 2220);
    }
}
