package org.studio4sv.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * Abstract base class for all THOUE GeckoLib mobs.
 * <p>
 * Handles the shared GeckoLib wiring (animation controllers, instance cache) so
 * concrete mobs only need to provide their stats and behaviour.
 */
public abstract class ThoMob extends Monster implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final String idleAnim;
    private final String walkAnim;
    private final String attackAnim;

    /** Name of the head bone in the .geo.json model, or null to disable head-tracking. */
    private final String headBone;

    protected ThoMob(EntityType<? extends Monster> type, Level level, String idle, String walk, String attack) {
        this(type, level, idle, walk, attack, "Head");
    }

    protected ThoMob(EntityType<? extends Monster> type, Level level, String idle, String walk, String attack,
                     String headBone) {
        super(type, level);
        this.idleAnim = idle;
        this.walkAnim = walk;
        this.attackAnim = attack;
        this.headBone = headBone;
    }

    public String idleAnim() {
        return this.idleAnim;
    }

    public String walkAnim() {
        return this.walkAnim;
    }

    public String attackAnim() {
        return this.attackAnim;
    }

    public String headBone() {
        return this.headBone;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 2, this::animationPredicate));
    }

    private PlayState animationPredicate(AnimationState<ThoMob> state) {
        if (this.swinging) {
            state.getController().forceAnimationReset();
            state.getController().setAnimation(
                    RawAnimation.begin().then(this.attackAnim, Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
            return PlayState.CONTINUE;
        }

        if (state.isMoving()) {
            state.setAnimation(RawAnimation.begin().thenLoop(this.walkAnim));
        } else {
            state.setAnimation(RawAnimation.begin().thenLoop(this.idleAnim));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public static AttributeSupplier.Builder createThouAttributes(double maxHealth, double attackDamage,
                                                                 double attackSpeed, double movementSpeed) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, maxHealth)
                .add(Attributes.ATTACK_DAMAGE, attackDamage)
                .add(Attributes.ATTACK_SPEED, attackSpeed)
                .add(Attributes.MOVEMENT_SPEED, movementSpeed)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ARMOR, 0.0D);
    }
}
