package gg.vape.manager.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import gg.vape.friend.Enemy;
import gg.vape.friend.ui.EnemySettingsFrame;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.none.ClientSettings;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class EnemyManager {
    public BooleanValue useAlias;
    public ColorValue enemyColor;
    public BooleanValue useEnemies;
    public BooleanValue useColor;
    public BooleanValue spoofAlias;
    private final Set<Enemy> enemies = new HashSet<Enemy>();
    private static int[] obfuscationState;

    public EnemyManager() {
        this.useEnemies = BooleanValue.create(this, "Use Enemies", true);
        this.useAlias = BooleanValue.create(this, "Use Alias", true);
        this.spoofAlias = BooleanValue.create(this, "Spoof alias", false, "This will make the enemies name be replaced in chat with their alias.\nApplies on regular Nametags as well");
        this.useColor = BooleanValue.create(this, "Use color", true, "Re-colors certain render modules to use \"Enemies Color\" on enemies");
        this.enemyColor = ColorValue.create(this, "Enemies Color", new Color(244, 66, 66));
        this.spoofAlias.addChangeListener(this::onSpoofAliasChanged);
    }

    public static void setObfuscationState(int[] state) {
        obfuscationState = state;
    }

    public void clearEnemies() {
        this.getEnemies().clear();
    }

    public boolean isEnemy(String name) {
        if (!this.useEnemies.getEffectiveValue().booleanValue()) {
            return false;
        }
        Enemy enemy = this.getEnemy(name);
        return enemy != null;
    }

    private void onSpoofAliasChanged(BooleanValue ignored) {
        this.refreshPlayerNames();
    }

    public void addEnemy(Enemy enemy) {
        Enemy existing = this.getEnemy(enemy.getName());
        if (existing != null) {
            this.enemies.remove(existing);
        }
        this.enemies.add(enemy);
        this.refreshPlayerNames();
    }

    public Enemy findTargetedEnemy(String name, boolean respectEnabledSetting) {
        if (this.useEnemies.getEffectiveValue().booleanValue() || !respectEnabledSetting) {
            Enemy enemy = this.getEnemy(name);
            if (enemy != null) {
                return null;
            }
            return enemy;
        }
        return null;
    }

    static {
        EnemyManager.setObfuscationState(null);
    }

    public boolean isExclusiveEnemy(EntityLivingBase entity) {
        Enemy enemy = this.findTargetedEnemy(entity.getName());
        if (enemy != null) {
            return enemy.isExclusive();
        }
        return false;
    }

    public static int[] getObfuscationState() {
        return obfuscationState;
    }

    public boolean isEnemy(EntityLivingBase entity) {
        return this.isEnemy(entity.getName());
    }

    public void refreshPlayerNames() {
        if (Minecraft.theWorld().isNull()) {
            return;
        }
        for (Object playerObject : Minecraft.theWorld().X()) {
            new EntityPlayer(playerObject).w$src$V$1iu649y();
        }
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public JsonArray toJson() {
        JsonArray result = new JsonArray();
        for (Enemy enemy : this.getEnemies()) {
            result.add((JsonElement)enemy.toJson());
        }
        return result;
    }

    public void loadJson(JsonArray serializedEnemies) {
        if (serializedEnemies.size() == 0) {
            return;
        }
        this.clearEnemies();
        for (int index = 0; index < serializedEnemies.size(); ++index) {
            try {
                JsonElement element = serializedEnemies.get(index);
                if (!element.isJsonObject() || element.isJsonNull()) continue;
                Enemy enemy = Enemy.fromJson(element.getAsJsonObject());
                this.addEnemy(enemy);
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        ClientSettings.getFrame(EnemySettingsFrame.class).refreshEntries();
    }

    public Enemy getEnemy(String name) {
        for (Enemy enemy : this.enemies) {
            if (!enemy.getName().equalsIgnoreCase(name)) continue;
            return enemy;
        }
        return null;
    }

    public Enemy findTargetedEnemy(String name) {
        return this.findTargetedEnemy(name, true);
    }

    public void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
        this.refreshPlayerNames();
    }

    public Set<Enemy> getEnemies() {
        return this.enemies;
    }

    public void toggleCrosshairTarget() {
        RayTraceResult rayTraceResult = Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0();
        if (rayTraceResult.isNull()) {
            return;
        }
        Entity entity = rayTraceResult.getEntity();
        if (entity.isNull()) {
            return;
        }
        if (entity.isInstance(MappedClasses.lG)) {
            EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(entity);
            String name = entityOtherPlayerMP.getName();
            Enemy enemy = this.getEnemy(name);
            if (enemy != null) {
                this.removeEnemy(enemy);
            } else {
                this.addEnemy(new Enemy(name, name));
            }
        }
    }
}

