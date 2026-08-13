package gg.vape.module.render.entity;

import gg.vape.Vape;
import gg.vape.combat.AttackStrengthTracker;
import gg.vape.config.ClientSettings;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.NameTags;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.MutableColor;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.cache.CachedBoolean;
import gg.vape.utils.cache.CachedFloat;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.ModelPlayer;
import gg.vape.wrapper.impl.PotionEffect;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class RenderEntityContext {
    private final CachedBoolean friendCache;
    @Nullable
    private ModelPlayer modelPlayer;
    private final CachedBoolean enemyCache;
    @Nullable
    private final EntityPlayer entityPlayer;
    @Nullable
    private String cachedName;
    private final CachedBoolean invisibleWithoutEquipmentCache;
    private final int entityId;
    private final CachedFloat effectiveHealthCache;
    @Nullable
    private MutableColor fillColor;
    private final CachedFloat healthCache;
    @Nullable
    private String cachedTypeName;
    @Nullable
    private String cachedNameTag;
    private final CachedBoolean invisibleCache;
    private final CachedBoolean botCache;
    private List<PotionEffect> potionEffects;
    @Nullable
    private ItemStack heldItem;
    private final CachedFloat distanceCache;
    private final CachedFloat heightCache;
    private final CachedBoolean sneakingCache;
    private final CachedBoolean syntheticEntityCache = new CachedBoolean();
    private EntityLivingBase entity;
    @Nullable
    private ItemStack bestWeapon;
    private EntityPlayerSP viewer;
    private final CachedBoolean visibilityCache;
    @Nullable
    private MutableColor outlineColor;
    private final CachedFloat maxHealthCache;
    private final CachedBoolean attackableCache;

    public String getName() {
        if (this.cachedName == null) {
            this.cachedName = this.entity.getName();
        }
        return this.cachedName;
    }

    public boolean canViewerSee() {
        if (!this.visibilityCache.isCached()) {
            this.visibilityCache.setValue(this.viewer.canEntityBeSeen(this.entity));
        }
        return (Boolean)this.visibilityCache.getCachedValue();
    }

    @Nullable
    public EntityPlayer getEntityPlayer() {
        return this.entityPlayer;
    }

    public boolean isAttackable() {
        if (!this.attackableCache.isCached()) {
            if (this.entityPlayer != null) {
                this.attackableCache.setValue(Vape.INSTANCE.getClientSettings().isTeammate(this.viewer, this.entity));
            } else {
                this.attackableCache.setValue(false);
            }
        }
        return (Boolean)this.attackableCache.getCachedValue();
    }

    public float getHealth() {
        if (!this.healthCache.isCached()) {
            this.healthCache.setValue(Float.valueOf(this.entity.p()));
        }
        return ((Float)this.healthCache.getCachedValue()).floatValue();
    }

    public boolean isInvisible() {
        if (!this.invisibleCache.isCached()) {
            this.invisibleCache.setValue(this.entity.J$src$Z$fdev5g());
        }
        return (Boolean)this.invisibleCache.getCachedValue();
    }

    public float getHeight() {
        if (!this.heightCache.isCached()) {
            this.heightCache.setValue(Float.valueOf(this.entity.Y()));
        }
        return ((Float)this.heightCache.getCachedValue()).floatValue();
    }

    public int getEntityId() {
        return this.entityId;
    }

    public boolean isInvisibleWithoutEquipment() {
        if (!this.invisibleWithoutEquipmentCache.isCached()) {
            this.invisibleWithoutEquipmentCache.setValue(RotationUtil.k(this.entity));
        }
        return (Boolean)this.invisibleWithoutEquipmentCache.getCachedValue();
    }

    public void update(EntityLivingBase entity, EntityPlayerSP viewer) {
        this.entity = entity;
        this.viewer = viewer;
        this.syntheticEntityCache.clear();
        this.botCache.clear();
        this.visibilityCache.clear();
        this.invisibleCache.clear();
        this.invisibleWithoutEquipmentCache.clear();
        this.sneakingCache.clear();
        this.attackableCache.clear();
        this.friendCache.clear();
        this.enemyCache.clear();
        this.effectiveHealthCache.clear();
        this.maxHealthCache.clear();
        this.healthCache.clear();
        this.distanceCache.clear();
        this.heightCache.clear();
        this.cachedName = null;
        this.cachedTypeName = null;
        this.heldItem = null;
        this.bestWeapon = null;
        this.cachedNameTag = null;
        this.fillColor = null;
        this.outlineColor = null;
        this.modelPlayer = null;
        this.potionEffects = null;
    }

    public String getNameTag() {
        if (this.cachedNameTag == null && this.entityPlayer != null) {
            this.cachedNameTag = Vape.INSTANCE.getModManager().getMod(NameTags.class).Q(this.viewer, this, this.entityPlayer);
        }
        return this.cachedNameTag;
    }

    @Nullable
    public ItemStack getHeldItem() {
        if (this.heldItem == null) {
            ItemStack itemStack = this.entity.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
            this.heldItem = itemStack.isNotNull() ? itemStack : null;
        }
        return this.heldItem;
    }

    public List<PotionEffect> getPotionEffects() {
        if (this.potionEffects == null) {
            this.potionEffects = new ArrayList<>();
            for (Object e : this.entity.B$src$Ljava_util_Collection_$1uxz2f9()) {
                PotionEffect potionEffect = new PotionEffect(e);
                if (potionEffect.k() <= 0) continue;
                this.potionEffects.add(potionEffect);
            }
        }
        return this.potionEffects;
    }


    @Nullable
    public MutableColor getRenderColor(boolean outline) {
        if (outline) {
            if (this.outlineColor == null) {
                this.outlineColor = Vape.INSTANCE.getClientSettings().resolveTeamColor(this, true, true);
            }
            return this.outlineColor;
        }
        if (this.fillColor == null) {
            this.fillColor = Vape.INSTANCE.getClientSettings().resolveTeamColor(this, false);
        }
        return this.fillColor;
    }

    public RenderEntityContext(int entityId, EntityLivingBase entity, EntityPlayerSP viewer) {
        this.botCache = new CachedBoolean();
        this.visibilityCache = new CachedBoolean();
        this.invisibleCache = new CachedBoolean();
        this.invisibleWithoutEquipmentCache = new CachedBoolean();
        this.sneakingCache = new CachedBoolean();
        this.attackableCache = new CachedBoolean();
        this.friendCache = new CachedBoolean();
        this.enemyCache = new CachedBoolean();
        this.effectiveHealthCache = new CachedFloat();
        this.maxHealthCache = new CachedFloat();
        this.healthCache = new CachedFloat();
        this.distanceCache = new CachedFloat();
        this.heightCache = new CachedFloat();
        this.entityId = entityId;
        this.entity = entity;
        this.viewer = viewer;
        boolean isPlayer = entity.isInstance(MappedClasses.Yl);
        this.entityPlayer = isPlayer ? new EntityPlayer(entity) : null;
    }

    public boolean isFriend() {
        if (!this.friendCache.isCached()) {
            if (this.entityPlayer != null) {
                this.friendCache.setValue(Vape.INSTANCE.getFriendManager().isFriend(this.getName()));
            } else {
                this.friendCache.setValue(false);
            }
        }
        return (Boolean)this.friendCache.getCachedValue();
    }

    @Nullable
    public ItemStack getBestWeapon() {
        if (this.bestWeapon == null && this.entityPlayer != null) {
            ItemStack heldItem = this.getHeldItem();
            if (heldItem != null && heldItem.isNotNull()) {
                Item item = heldItem.getItem();
                if (ItemStackScoreUtil.h(item) || ItemStackScoreUtil.I(item)) {
                    this.bestWeapon = heldItem;
                }
            }
        }
        return this.bestWeapon;
    }

    public float getEffectiveHealth() {
        if (!this.effectiveHealthCache.isCached()) {
            if (this.entityPlayer != null) {
                this.effectiveHealthCache.setValue(Float.valueOf(AttackStrengthTracker.INSTANCE.getEstimatedHealth(this.entityPlayer)));
            } else {
                this.effectiveHealthCache.setValue(Float.valueOf(this.entity.w$src$F$15l9epb()));
            }
        }
        return ((Float)this.effectiveHealthCache.getCachedValue()).floatValue();
    }

    public boolean isEnemy() {
        if (!this.enemyCache.isCached()) {
            if (this.entityPlayer != null) {
                this.enemyCache.setValue(Vape.INSTANCE.getEnemyManager().isEnemy(this.getName()));
            } else {
                this.enemyCache.setValue(false);
            }
        }
        return (Boolean)this.enemyCache.getCachedValue();
    }

    public float getMaxHealth() {
        if (!this.maxHealthCache.isCached()) {
            this.maxHealthCache.setValue(Float.valueOf(this.entity.I$src$F$14vyvep()));
        }
        return ((Float)this.maxHealthCache.getCachedValue()).floatValue();
    }

    public boolean isSneaking() {
        if (!this.sneakingCache.isCached()) {
            this.sneakingCache.setValue(this.entity.P());
        }
        return (Boolean)this.sneakingCache.getCachedValue();
    }

    public ModelPlayer getModelPlayer() {
        if (this.modelPlayer == null && this.entityPlayer != null) {
            this.modelPlayer = this.entityPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86();
        }
        return this.modelPlayer;
    }

    public double getDistance() {
        if (!this.distanceCache.isCached()) {
            this.distanceCache.setValue(Float.valueOf(this.viewer.getDistanceToEntity(this.entity)));
        }
        return ((Float)this.distanceCache.getCachedValue()).floatValue();
    }

    public String getTypeName() {
        if (this.cachedTypeName == null) {
            this.cachedTypeName = this.entity.Q().getFormattedText();
        }
        return this.cachedTypeName;
    }

    public boolean isSyntheticEntity() {
        if (!this.syntheticEntityCache.isCached()) {
            this.syntheticEntityCache.setValue(ClientSettings.isReservedEntityId(this.entityId));
        }
        return (Boolean)this.syntheticEntityCache.getCachedValue();
    }

    public boolean isBot() {
        if (!this.botCache.isCached()) {
            this.botCache.setValue(Vape.INSTANCE.getClientSettings().isBot(this.entity));
        }
        return (Boolean)this.botCache.getCachedValue();
    }
}
