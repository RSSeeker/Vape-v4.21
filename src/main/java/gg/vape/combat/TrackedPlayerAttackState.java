package gg.vape.combat;

import gg.vape.mapping.MappedClasses;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EffectRenderer;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.SoundAwareEntityFX;
import java.util.List;

public class TrackedPlayerAttackState {
    private static final String CRITICAL_HIT_PARTICLE_NAME = "crit";

    private int estimatedFoodLevel = 20;
    private int foodTickTimer;
    private float estimatedHealth = 20.0f;
    private boolean attackPending;
    private EntityPlayer player;
    private final TimerUtil inactivityTimer = new TimerUtil();
    private float estimatedSaturationLevel = 5.0f;
    private boolean receivedLivingUpdate;
    private int ticksSinceAttack;
    private float estimatedExhaustionLevel;
    private boolean targetWasBlocking;
    private ItemStack attackingItem;

    public TrackedPlayerAttackState(EntityPlayer player) {
        this.player = player;
    }

    void markLivingUpdate() {
        this.receivedLivingUpdate = true;
    }

    void updatePlayer(EntityPlayer player) {
        this.player = player;
    }

    TimerUtil getInactivityTimer() {
        return this.inactivityTimer;
    }

    void resetPrediction() {
        this.estimatedHealth = 20.0f;
        this.estimatedFoodLevel = 20;
        this.foodTickTimer = 0;
        this.estimatedExhaustionLevel = 0.0f;
        this.estimatedSaturationLevel = 5.0f;
        this.attackingItem = null;
        this.attackPending = false;
        this.inactivityTimer.reset();
    }

    void recordAttack(boolean targetWasBlocking, ItemStack attackingItem) {
        this.targetWasBlocking = targetWasBlocking;
        this.ticksSinceAttack = 0;
        this.attackPending = true;
        this.attackingItem = attackingItem;
    }

    float getEstimatedHealth() {
        return this.estimatedHealth;
    }

    void addEstimatedHealth(float amount) {
        this.estimatedHealth += amount;
    }

    public void update() {
        ++this.ticksSinceAttack;
        this.inactivityTimer.reset();
        if (this.estimatedHealth > 20.0f) {
            this.estimatedHealth = 20.0f;
        }

        boolean fallDamageApplied = AttackStrengthTracker.INSTANCE.shouldEstimateFallDamage()
                && this.applyFallDamage(this.player.N() - this.player.W(), this.player.b$src$Z$fqlxe4());
        boolean foodHealingAdjusted = AttackStrengthTracker.INSTANCE.shouldEstimateFoodHealing()
                && this.simulateFoodHealing();
        if (this.receivedLivingUpdate) {
            this.estimatedExhaustionLevel += 0.3f;
            if (this.attackPending && this.ticksSinceAttack <= 11) {
                boolean criticalHit = this.hasCriticalHitParticle();
                float damage = AttackStrengthTracker.calculateAttackDamage(
                        this.attackingItem, this.player, this.targetWasBlocking, criticalHit);
                this.estimatedHealth -= damage;
                this.attackPending = false;
            } else if (!fallDamageApplied && !foodHealingAdjusted && this.player.U$src$Z$fjglof()) {
                this.estimatedHealth -= AttackStrengthTracker.applyDamageReductions(this.player, false, 1.0f);
            }
            this.receivedLivingUpdate = false;
            this.targetWasBlocking = false;
        }
    }

    private boolean hasCriticalHitParticle() {
        EffectRenderer effectRenderer = Minecraft.z();
        if (effectRenderer.isNull()) {
            return false;
        }
        List<SoundAwareEntityFX> particleEmitters = effectRenderer.getParticleEmitters();
        for (SoundAwareEntityFX particle : particleEmitters) {
            if (!particle.Z$src$Lgg_vape_wrapper_impl_EnumParticleTypes_$1aa3947()
                    .K().equalsIgnoreCase(CRITICAL_HIT_PARTICLE_NAME)) {
                continue;
            }
            if (this.player.isNull() || this.player.getDistanceToEntity(particle) >= 1.1f
                    || particle.N() >= this.player.N() + 2.5 || RotationUtil.S(this.player, particle)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private boolean simulateFoodHealing() {
        boolean healthAdjustedDown = false;
        if (this.estimatedHealth >= 20.0f) {
            this.foodTickTimer = 0;
        }
        if (this.estimatedHealth > 0.0f && this.estimatedHealth < 20.0f) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80
                    && (!this.player.U$src$Z$fjglof() || Minecraft.thePlayer().i(PotionRegistry.W))) {
                this.estimatedHealth += 1.0f;
                this.estimatedExhaustionLevel += 3.0f;
                this.foodTickTimer = 0;
            }
        } else if (this.estimatedFoodLevel <= 0) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                if (this.estimatedHealth > 1.0f) {
                    this.estimatedHealth -= 1.0f;
                    healthAdjustedDown = true;
                }
                this.foodTickTimer = 0;
            }
        } else {
            this.foodTickTimer = 0;
        }
        return healthAdjustedDown;
    }

    public boolean applyFallDamage(double fallDistance, boolean onGround) {
        float previousFallDistance = (float)(this.player.N() - this.player.W());
        if (ForgeVersion.MC_1_7_10.Y()) {
            int blockX = MathUtil.floor(this.player.z());
            int blockY = MathUtil.floor(this.player.N() - (double)0.2f);
            int blockZ = MathUtil.floor(this.player.h());
            BlockPos landingPosition = BlockPos.create(blockX, blockY, blockZ);
            Block landingBlock = Minecraft.theWorld().getBlockState(landingPosition).getBlock();
            if (BlockUtil.p(landingBlock)) {
                Block blockBelow = Minecraft.theWorld().getBlockState(
                        landingPosition.d$src$Lgg_vape_wrapper_impl_BlockPos_$6vry9r()).getBlock();
                if (blockBelow.isInstance(MappedClasses.V7) || blockBelow.isInstance(MappedClasses.lx)
                        || blockBelow.isInstance(MappedClasses.YY)) {
                    landingPosition = landingPosition.d$src$Lgg_vape_wrapper_impl_BlockPos_$6vry9r();
                    landingBlock = blockBelow;
                }
            }
            previousFallDistance += this.player.M$src$F$ff28gb();
            this.player.L(fallDistance, onGround, landingBlock, landingPosition);
        } else {
            this.player.q(fallDistance, onGround);
        }

        if (this.player.b$src$Z$fqlxe4() && previousFallDistance > 0.0f) {
            PotionEffect jumpBoost = this.player.b(PotionRegistry.Z);
            float jumpReduction = jumpBoost.isNotNull() ? (float)(jumpBoost.L() + 1) : 0.0f;
            int damage = MathUtil.ceil(previousFallDistance - 3.0f - jumpReduction);
            if (damage > 0) {
                this.estimatedHealth -= (float)damage;
                return true;
            }
        }
        return false;
    }
}
