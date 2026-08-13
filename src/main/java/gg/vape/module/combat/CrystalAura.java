package gg.vape.module.combat;

import gg.vape.Vape;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.MinecraftVersionConstraint;
import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;
import gg.vape.module.combat.crystalaura.CrystalAuraPlacementSubModule;
import gg.vape.module.combat.crystalaura.CrystalAuraTargetSubModule;
import gg.vape.module.combat.crystalaura.ExplosionType;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.Freecam;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.ui.click.frame.impl.hud.ActiveModuleStackFrame;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.DirectionalPosition;
import gg.vape.utils.math.NumericMathUtil;
import gg.vape.value.ModeValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.Enchantments;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLiving;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;

public class CrystalAura
extends Mod {
    private final ModeValue mode;
    private final CrystalAuraPlacementSubModule autoSubModule = new CrystalAuraPlacementSubModule(this, "Auto");
    private static Freecam freecam;
    private final CrystalAuraTargetSubModule manualSubModule = new CrystalAuraTargetSubModule(this, "Manual");
    private final RotationControlClaim rotationClaim = SharedModuleControlClaims.rotation;
    public float applyDamageReductions(EntityPlayerSP player, float damage) {
        damage = this.applyArmorReduction(player, damage);
        damage = this.applyPotionAndEnchantmentReductions(player, damage);
        return Math.max(damage, 0.0f);
    }

    public int findObsidianSlot(EntityPlayerSP player) {
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack itemStack = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(slot);
            if (!this.isObsidian(itemStack)) continue;
            return slot;
        }
        return -1;
    }

    public static float reduceDamageByArmor(float damage, float armor, float armorToughness) {
        float toughnessScale = 2.0f + armorToughness / 4.0f;
        float effectiveArmor = NumericMathUtil.clamp(armor - damage / toughnessScale, armor * 0.2f, 20.0f);
        return damage * (1.0f - effectiveArmor / 25.0f);
    }

    public boolean shouldPause() {
        if (freecam == null) {
            freecam = Vape.INSTANCE.getModManager().getMod(Freecam.class);
        }
        return freecam != null && freecam.isEnabled() || this.rotationClaim.isBlockedFor(this) && !this.rotationClaim.acquire(this, true);
    }

    protected float applyPotionAndEnchantmentReductions(EntityPlayerSP player, float damage) {
        if (player.i(PotionRegistry.P)) {
            int resistanceReduction = (player.b(PotionRegistry.P).L() + 1) * 5;
            int remainingPercent = 25 - resistanceReduction;
            damage = Math.max(damage * (float)remainingPercent / 25.0f, 0.0f);
        }
        if (damage <= 0.0f) {
            return 0.0f;
        }
        float blastProtection = CrystalAura.getBlastProtectionLevel(player);
        if (blastProtection > 0.0f) {
            damage = CrystalAura.reduceDamageByBlastProtection(damage, blastProtection);
        }
        return damage;
    }

    @Override
    public String getDetailedSuffix() {
        if (this.autoSubModule.isSelectedSubModule() && this.autoSubModule.isEnabled()) {
            return this.autoSubModule.getDetailedSuffix();
        }
        if (this.manualSubModule.isSelectedSubModule() && this.manualSubModule.isEnabled()) {
            return this.manualSubModule.getDetailedSuffix();
        }
        return "";
    }

    public boolean isEndCrystal(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return false;
        }
        ItemMappingEntry itemMappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(itemStack);
        if (itemMappingEntry != null) {
            return "minecraft:end_crystal".equals(itemMappingEntry.getResourceKey()) || "end_crystal".equals(itemMappingEntry.getModernId());
        }
        return itemStack.f().toLowerCase().contains("end_crystal");
    }

    public int findCrystalSlot(EntityPlayerSP player) {
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack itemStack = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(slot);
            if (!this.isEndCrystal(itemStack)) continue;
            return slot;
        }
        return -1;
    }

    public boolean isSelfDamageSafe(Vec3 explosionPosition, ExplosionType explosionType, EntityPlayerSP player, World world, boolean preventSuicide, double maximumDamage) {
        float explosionDamage = this.calculateExplosionDamage(player, explosionType, explosionPosition, world);
        if (explosionDamage <= 0.0f) {
            return true;
        }
        float effectiveHealth = player.w$src$F$15l9epb() + player.p();
        if (preventSuicide && effectiveHealth <= explosionDamage) {
            return false;
        }
        return (double)explosionDamage <= maximumDamage;
    }

    public Vec3 getInteractionPoint(DirectionalPosition directionalPosition) {
        switch (directionalPosition.getFacingIndex()) {
            case 1: {
                return Vec3.create((double)directionalPosition.B() + 0.5, directionalPosition.E() + 1, (double)directionalPosition.A() + 0.5);
            }
            case 0: {
                return Vec3.create((double)directionalPosition.B() + 0.5, directionalPosition.E(), (double)directionalPosition.A() + 0.5);
            }
            case 2: {
                return Vec3.create((double)directionalPosition.B() + 0.5, (double)directionalPosition.E() + 0.75, directionalPosition.A());
            }
            case 3: {
                return Vec3.create((double)directionalPosition.B() + 0.5, (double)directionalPosition.E() + 0.75, directionalPosition.A() + 1);
            }
            case 4: {
                return Vec3.create(directionalPosition.B(), (double)directionalPosition.E() + 0.75, (double)directionalPosition.A() + 0.5);
            }
            case 5: {
                return Vec3.create(directionalPosition.B() + 1, (double)directionalPosition.E() + 0.75, (double)directionalPosition.A() + 0.5);
            }
        }
        return Vec3.create((double)directionalPosition.B() + 0.5, directionalPosition.E() + 1, (double)directionalPosition.A() + 0.5);
    }

    @Override
    public ModuleDisplayInfo getModuleDisplayInfo() {
        if (this.autoSubModule.isSelectedSubModule() && this.autoSubModule.isEnabled()) {
            return this.autoSubModule.getModuleDisplayInfo();
        }
        return null;
    }

    public int countCrystalsInHotbar(EntityPlayerSP player) {
        int crystalCount = 0;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(i);
            if (!this.isEndCrystal(itemStack)) continue;
            crystalCount += itemStack.t();
        }
        return crystalCount;
    }

    protected float applyArmorReduction(EntityPlayerSP player, float damage) {
        return CrystalAura.reduceDamageByArmor(damage, (float)player.o(MonsterAttributesBridge.L()), (float)player.o(MonsterAttributesBridge.m$src$Lgg_vape_wrapper_impl_Holder_$1lgjxui()));
    }

    public float calculateRawExplosionDamage(float explosionDiameter, double normalizedDistance, float blockDensity) {
        double impact = (1.0 - normalizedDistance) * (double)blockDensity;
        double damageScale = ForgeVersion.MC_1_12_2.d() ? 7.0 : 8.0;
        return (float)((impact * impact + impact) / 2.0 * damageScale * (double)explosionDiameter + 1.0);
    }

    public float calculateExplosionDamage(EntityPlayerSP player, ExplosionType explosionType, Vec3 explosionPosition, World world) {
        float explosionDiameter = explosionType.getExplosionPower() * 2.0f;
        double explosionX = explosionPosition.getX();
        double explosionY = explosionPosition.getY();
        double explosionZ = explosionPosition.getZ();
        int minimumX = NumericMathUtil.floorDouble(explosionX - (double)explosionDiameter - 1.0);
        int maximumX = NumericMathUtil.floorDouble(explosionX + (double)explosionDiameter + 1.0);
        int minimumY = NumericMathUtil.floorDouble(explosionY - (double)explosionDiameter - 1.0);
        int maximumY = NumericMathUtil.floorDouble(explosionY + (double)explosionDiameter + 1.0);
        int minimumZ = NumericMathUtil.floorDouble(explosionZ - (double)explosionDiameter - 1.0);
        int maximumZ = NumericMathUtil.floorDouble(explosionZ + (double)explosionDiameter + 1.0);
        boolean insideExplosionBounds = MathUtil.e(player.z(), (double)minimumX, (double)maximumX)
                && MathUtil.e(player.N(), (double)minimumY, (double)maximumY)
                && MathUtil.e(player.h(), (double)minimumZ, (double)maximumZ);
        if (insideExplosionBounds) {
            double normalizedDistance = ForgeVersion.MC_1_16_5.d()
                    ? Math.sqrt(player.g(explosionPosition)) / (double)explosionDiameter
                    : player.i(explosionX, explosionY, explosionZ) / (double)explosionDiameter;
            if (normalizedDistance <= 1.0) {
                float blockDensity = CrystalAura.calculateBlockDensity(explosionPosition, player, world);
                float rawDamage = this.calculateRawExplosionDamage(explosionDiameter, normalizedDistance, blockDensity);
                return this.applyDamageReductions(player, rawDamage);
            }
        }
        return -1.0f;
    }

    public CrystalAura() {
        super("CrystalAura", -4263937, Category.COMBAT, "Automatically places crystals on obsidian and breaks them for you.");
        this.mode = ModeValue.create((Object)this, "Mode", "Auto - Automatically finds targets and places/breaks crystals\nManual - Hold right-click on obsidian with crystal to place and break crystals", (ModeSelection)this.autoSubModule.getSelectionValue(), this.autoSubModule.getSelectionValue(), this.manualSubModule.getSelectionValue());
        this.P(this.mode, new MinecraftVersionConstraint[0]);
        this.rotationClaim.setPriority(this, 6);
    }

    public static float calculateBlockDensity(Vec3 explosionPosition, Entity entity, World world) {
        AxisAlignedBB bounds = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
        double minimumX = bounds.getMinX();
        double minimumY = bounds.getMinY();
        double minimumZ = bounds.getMinZ();
        double maximumX = bounds.getMaxX();
        double maximumY = bounds.getMaxY();
        double maximumZ = bounds.getMaxZ();
        double xStep = 1.0 / ((maximumX - minimumX) * 2.0 + 1.0);
        double yStep = 1.0 / ((maximumY - minimumY) * 2.0 + 1.0);
        double zStep = 1.0 / ((maximumZ - minimumZ) * 2.0 + 1.0);
        double xOffset = (1.0 - Math.floor(1.0 / xStep) * xStep) / 2.0;
        double zOffset = (1.0 - Math.floor(1.0 / zStep) * zStep) / 2.0;
        if (!(xStep < 0.0 || yStep < 0.0 || zStep < 0.0)) {
            int unobstructedSamples = 0;
            int totalSamples = 0;
            for (double xFraction = 0.0; xFraction <= 1.0; xFraction += xStep) {
                for (double yFraction = 0.0; yFraction <= 1.0; yFraction += yStep) {
                    for (double zFraction = 0.0; zFraction <= 1.0; zFraction += zStep) {
                        double sampleX = NumericMathUtil.interpolate(xFraction, minimumX, maximumX);
                        double sampleY = NumericMathUtil.interpolate(yFraction, minimumY, maximumY);
                        double sampleZ = NumericMathUtil.interpolate(zFraction, minimumZ, maximumZ);
                        Vec3 samplePoint = Vec3.create(sampleX + xOffset, sampleY, sampleZ + zOffset);
                        if (world.K(samplePoint, explosionPosition, false, true, false, entity).getTypeOfHit().equals(RayTraceResult_type.miss())) {
                            ++unobstructedSamples;
                        }
                        ++totalSamples;
                    }
                }
            }
            return (float)unobstructedSamples / (float)totalSamples;
        }
        return 0.0f;
    }


    @Override
    public void onEnable() {
        ClientSettings.getFrame(ActiveModuleStackFrame.class).addModule(this);
    }

    public static float reduceDamageByBlastProtection(float damage, float protectionLevel) {
        float clampedProtection = NumericMathUtil.clamp(protectionLevel, 0.0f, 20.0f);
        return damage * (1.0f - clampedProtection / 25.0f);
    }

    @Override
    public void onDisable() {
        ClientSettings.getFrame(ActiveModuleStackFrame.class).removeModule(this);
        this.rotationClaim.release(this);
    }

    public boolean isCrystalBaseBlock(BlockState blockState) {
        if (blockState.isNull()) {
            return false;
        }
        String blockName = blockState.getBlock().U().toLowerCase();
        return blockName.contains("obsidian") || blockName.contains("bedrock");
    }

    public static float getBlastProtectionLevel(EntityLivingBase entity) {
        int protectionLevel = 0;
        int generalProtection = EnchantmentHelper.a(Enchantments.c(), new EntityLiving(entity.getObject()));
        if (generalProtection > 0) {
            protectionLevel += generalProtection;
        }
        int blastProtection = EnchantmentHelper.a(Enchantments.N(), new EntityLiving(entity.getObject()));
        if (blastProtection > 0) {
            protectionLevel += 2 * blastProtection;
        }
        return protectionLevel;
    }

    public boolean isObsidian(ItemStack itemStack) {
        if (itemStack.isNull() || !itemStack.getItem().isInstance(MappedClasses.Vw)) {
            return false;
        }
        ItemMappingEntry itemMappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(itemStack);
        if (itemMappingEntry != null) {
            return "minecraft:obsidian".equals(itemMappingEntry.getResourceKey()) || "obsidian".equals(itemMappingEntry.getModernId());
        }
        return itemStack.f().toLowerCase().contains("obsidian");
    }
}
