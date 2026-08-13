package gg.vape.friend;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendActivityListener;
import gg.vape.friend.OnlineFriendActivityType;
import gg.vape.friend.activity.ActivityHealthData;
import gg.vape.friend.activity.ActivityItemStack;
import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.friend.activity.ActivityPositionData;
import gg.vape.friend.activity.ActivitySnapshotPayload;
import gg.vape.friend.activity.ActivitySnapshotPayloadBuilder;
import gg.vape.friend.activity.ActivityTargetData;
import gg.vape.mapping.MappedClasses;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionEntry;
import gg.vape.wrapper.impl.PotionRegistry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.Nullable;

public class OnlineFriendActivityState {
    private float absorptionAmount;
    private final ActivityItemStack[] inventory;
    private double positionY;
    private int recentAttackCount;
    private boolean reservedFlag;
    HashMap<Integer, Integer> recentTargetAges;
    private int afkTicks;
    private int clicksPerSecond;
    private int buildingTicks;
    private int stationaryTicks;
    private final Map<PotionEntry, Integer> potionAmplifiers;
    private double positionX;
    @Nullable
    private UUID targetUuid;
    private boolean dataAvailable;
    private double positionZ;
    @Nullable
    private String targetName;
    private AtomicBoolean reservedAtomicFlag;
    private float health;
    private HashSet<OnlineFriendActivityType> activityTypes;
    private DoubleAnimation animation;
    private final ActivityItemStack[] armor;
    private int heldItemSlot;
    private int swingProgressTicks;
    private float maxHealth;
    private final OnlineFriend friend;
    private int hurtTime;

    public int getHurtTime() {
        return this.hurtTime;
    }

    public void setReservedFlag(boolean ignored) {
    }

    public int getSwingProgressTicks() {
        return this.swingProgressTicks;
    }

    @Nullable
    public static ActivityItemStack fromItemStack(ItemStack itemStack) {
        ActivityItemStackPayload activityItemStackPayload = OnlineFriendActivityState.createItemStackPayload(itemStack);
        if (activityItemStackPayload == null) {
            return null;
        }
        return ActivityItemStack.fromPayload(activityItemStackPayload);
    }

    public int getRecentAttackCount() {
        return this.recentAttackCount;
    }

    public float getHealth(EntityPlayer entityPlayer) {
        return this.health;
    }


    @Nullable
    public String getTargetName() {
        return this.targetName;
    }

    public int getSwingProgressTicks(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return this.swingProgressTicks;
        }
        return entityPlayer.i();
    }

    public void removeActivity(OnlineFriendActivityType onlineFriendActivityType) {
        this.activityTypes.remove((Object)onlineFriendActivityType);
    }


    public void updateLocalActivity() {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.isNull()) {
            return;
        }
        int afkThresholdTicks = 600;
        if (entityPlayerSP.V() != entityPlayerSP.a$src$F$1txy325() || entityPlayerSP.J() != entityPlayerSP.g()) {
            this.afkTicks = 0;
        }
        if (this.afkTicks > afkThresholdTicks) {
            this.addActivity(OnlineFriendActivityType.AFK);
        } else {
            this.removeActivity(OnlineFriendActivityType.AFK);
        }
        if (!this.recentTargetAges.isEmpty()) {
            this.setRecentAttackCount(this.recentTargetAges.size());
            this.addActivity(OnlineFriendActivityType.COMBAT);
            this.ageRecentTargets();
        } else {
            this.removeActivity(OnlineFriendActivityType.COMBAT);
        }
        int buildingThresholdTicks = 160;
        ActivityItemStack activityItemStack = this.getInventory()[this.getHeldItemSlot()];
        if (activityItemStack != null) {
            Item item = Item.L(String.valueOf(activityItemStack.getItemId()));
            if (item.isInstance(MappedClasses.Vw) && Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362().isKeyDown()) {
                this.buildingTicks = 0;
            }
            if (item.isInstance(MappedClasses.Vw) && this.buildingTicks < buildingThresholdTicks) {
                this.addActivity(OnlineFriendActivityType.BUILDING);
            } else {
                this.removeActivity(OnlineFriendActivityType.BUILDING);
            }
        } else {
            this.removeActivity(OnlineFriendActivityType.BUILDING);
        }
        if (entityPlayerSP.t() != 0.0 || entityPlayerSP.q() > 0.0 || entityPlayerSP.T() != 0.0) {
            this.stationaryTicks = 0;
        }
        if (this.stationaryTicks < 50) {
            this.addActivity(OnlineFriendActivityType.MOVING);
        } else {
            this.removeActivity(OnlineFriendActivityType.MOVING);
        }
        ++this.afkTicks;
        ++this.buildingTicks;
        ++this.stationaryTicks;
    }

    public boolean hasData() {
        return this.dataAvailable;
    }

    public ActivityItemStack[] getInventory() {
        return this.inventory;
    }

    public static ActivitySnapshotPayload createSnapshot(EntityPlayer entityPlayer) {
        ActivitySnapshotPayloadBuilder activitySnapshotPayloadBuilder = new ActivitySnapshotPayloadBuilder();
        activitySnapshotPayloadBuilder.withPosition(new ActivityPositionData(entityPlayer.z(), entityPlayer.N(), entityPlayer.h()));
        LinkedHashMap<Short, Integer> linkedHashMap = new LinkedHashMap<Short, Integer>();
        for (Object potionEffectHandle : entityPlayer.B$src$Ljava_util_Collection_$1uxz2f9()) {
            PotionEntry potionEntry;
            PotionEffect potionEffect = new PotionEffect(potionEffectHandle);
            if (potionEffect.isNull() || (potionEntry = PotionRegistry.R(potionEffect)) == null) continue;
            linkedHashMap.put(potionEntry.getLegacyId(), potionEffect.k());
        }
        activitySnapshotPayloadBuilder.withHealth(new ActivityHealthData(entityPlayer.w$src$F$15l9epb(), entityPlayer.I$src$F$14vyvep(), entityPlayer.p(), entityPlayer.c$src$I$15a9iwo(), linkedHashMap));
        OnlineFriendActivityListener onlineFriendActivityListener = OnlineFriendActivityListener.INSTANCE;
        EntityPlayer target = onlineFriendActivityListener.getCombatTarget();
        if (target != null) {
            activitySnapshotPayloadBuilder.withTarget(new ActivityTargetData(target.X$src$Ljava_util_UUID_$1o5dyg6(), target.getName()));
        } else {
            activitySnapshotPayloadBuilder.withTarget(null);
        }
        return activitySnapshotPayloadBuilder.build();
    }

    public void setDataAvailable(boolean dataAvailable) {
        this.dataAvailable = dataAvailable;
    }

    public void setBuildingTicks(int buildingTicks) {
        this.buildingTicks = buildingTicks;
    }

    public void setHurtTime(int hurtTime) {
        this.hurtTime = hurtTime;
    }

    public void recordAttack(int entityId) {
        this.recentTargetAges.put(entityId, 0);
    }

    public void setSwingProgressTicks(int swingProgressTicks) {
        this.swingProgressTicks = swingProgressTicks;
    }

    public float getMaxHealth(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return this.maxHealth;
        }
        return entityPlayer.I$src$F$14vyvep();
    }

    public void addActivity(OnlineFriendActivityType onlineFriendActivityType) {
        this.activityTypes.add(onlineFriendActivityType);
    }

    public float getAbsorptionAmount(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return this.absorptionAmount;
        }
        return entityPlayer.p();
    }

    public void setAfkTicks(int afkTicks) {
        this.afkTicks = afkTicks;
    }

    public int getHeldItemSlot() {
        return this.heldItemSlot;
    }

    public void clearActivity() {
        this.activityTypes.clear();
        this.recentTargetAges.clear();
    }

    public double getPositionY(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return this.positionY;
        }
        return entityPlayer.A();
    }

    public int getHurtTime(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return this.hurtTime;
        }
        return entityPlayer.c$src$I$15a9iwo();
    }

    @Nullable
    public UUID getTargetUuid() {
        return this.targetUuid;
    }

    public OnlineFriendActivityState(OnlineFriend onlineFriend) {
        this.activityTypes = new HashSet<OnlineFriendActivityType>(Arrays.asList(new OnlineFriendActivityType[0]));
        this.afkTicks = 0;
        this.buildingTicks = 1000000;
        this.stationaryTicks = 100000;
        this.recentAttackCount = 0;
        this.reservedAtomicFlag = new AtomicBoolean(false);
        this.recentTargetAges = new HashMap();
        this.health = 15.0f;
        this.maxHealth = 20.0f;
        this.absorptionAmount = 2.0f;
        this.swingProgressTicks = 0;
        this.animation = new DoubleAnimation(0.05, 0.0, 1.0);
        this.clicksPerSecond = 0;
        this.positionX = 0.0;
        this.positionY = 64.0;
        this.positionZ = 0.0;
        this.armor = new ActivityItemStack[4];
        this.inventory = new ActivityItemStack[36];
        this.potionAmplifiers = new LinkedHashMap<PotionEntry, Integer>();
        this.dataAvailable = false;
        this.friend = onlineFriend;
    }

    public void setClicksPerSecond(int clicksPerSecond) {
        this.clicksPerSecond = clicksPerSecond;
    }

    public void setHeldItemSlot(int heldItemSlot) {
        this.heldItemSlot = heldItemSlot;
    }

    @Nullable
    public static ActivityItemStackPayload createItemStackPayload(ItemStack itemStack) {
        if (itemStack.isNull() || itemStack.getItem().P() == 0) {
            return null;
        }
        Map<Enchantment, Short> map = EnchantmentUtil.A(itemStack);
        HashMap<Short, Short> hashMap = new HashMap<Short, Short>();
        for (Map.Entry<Enchantment, Short> entry : map.entrySet()) {
            Short enchantmentId = EnchantmentUtil.c(entry.getKey());
            if (enchantmentId == null) continue;
            hashMap.put(enchantmentId, entry.getValue());
        }
        return new ActivityItemStackPayload(itemStack.getItem().P(), itemStack.t(), itemStack.L(), hashMap);
    }

    public void ageRecentTargets() {
        int retentionTicks = 150;
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        hashMap.putAll(this.recentTargetAges);
        Set<Integer> set = hashMap.keySet();
        for (Integer entityId : set) {
            if (this.recentTargetAges.get(entityId) > retentionTicks) {
                this.recentTargetAges.remove(entityId);
                continue;
            }
            this.recentTargetAges.put(entityId, this.recentTargetAges.get(entityId) + 1);
        }
    }

    public ActivityItemStack[] getArmor() {
        return this.armor;
    }

    public boolean hasTarget() {
        return this.getTargetUuid() != null && this.getTargetName() != null;
    }

    public int getClicksPerSecond() {
        return this.clicksPerSecond;
    }

    public int getBuildingTicks() {
        return this.buildingTicks;
    }

    public OnlineFriendActivityType getPrimaryActivity() {
        if (this.activityTypes.contains((Object)OnlineFriendActivityType.DEAD)) {
            return OnlineFriendActivityType.DEAD;
        }
        if (this.activityTypes.contains((Object)OnlineFriendActivityType.AFK)) {
            return OnlineFriendActivityType.AFK;
        }
        if (this.activityTypes.contains((Object)OnlineFriendActivityType.COMBAT)) {
            return OnlineFriendActivityType.COMBAT;
        }
        if (this.activityTypes.contains((Object)OnlineFriendActivityType.BUILDING)) {
            return OnlineFriendActivityType.BUILDING;
        }
        if (this.activityTypes.contains((Object)OnlineFriendActivityType.MOVING)) {
            return OnlineFriendActivityType.MOVING;
        }
        return OnlineFriendActivityType.NONE;
    }

    public DoubleAnimation getAnimation() {
        return this.animation;
    }

    public double getPositionX(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return this.positionX;
        }
        return entityPlayer.c();
    }

    public void applySnapshot(ActivitySnapshotPayload activitySnapshotPayload) {
        ActivityHealthData activityHealthData;
        this.dataAvailable = true;
        ActivityPositionData activityPositionData = activitySnapshotPayload.getPosition();
        if (activityPositionData != null) {
            this.positionX = activityPositionData.getX();
            this.positionY = activityPositionData.getY();
            this.positionZ = activityPositionData.getZ();
        }
        if ((activityHealthData = activitySnapshotPayload.getHealth()) != null) {
            this.health = activityHealthData.getHealth();
            this.maxHealth = activityHealthData.getMaxHealth();
            this.absorptionAmount = activityHealthData.getAbsorptionAmount();
            this.hurtTime = activityHealthData.getHurtTime();
            this.potionAmplifiers.clear();
            for (Map.Entry<Short, Integer> entry : activityHealthData.getPotionAmplifiers().entrySet()) {
                PotionEntry potionEntry = PotionRegistry.A(entry.getKey());
                if (potionEntry == null) continue;
                this.potionAmplifiers.put(potionEntry, entry.getValue());
            }
        }
        ActivityTargetData targetData = activitySnapshotPayload.getTarget();
        if (targetData != null) {
            this.targetUuid = targetData.getUuid();
            this.targetName = targetData.getName();
        } else {
            this.targetUuid = null;
            this.targetName = null;
        }
    }

    public void setRecentAttackCount(int recentAttackCount) {
        this.recentAttackCount = recentAttackCount;
    }

    public OnlineFriend getFriend() {
        return this.friend;
    }

    public boolean isReservedFlagSet() {
        return this.reservedFlag;
    }

    public double getPositionZ(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return this.positionZ;
        }
        return entityPlayer.Z();
    }
}
