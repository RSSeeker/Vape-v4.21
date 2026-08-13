package gg.vape.friend;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.friend.TargetEntry;
import gg.vape.friend.TargetType;

public class Enemy
extends TargetEntry {
    private String alias;
    private String name;
    private boolean exclusive;

    public String getAlias() {
        return this.alias;
    }

    public Enemy(String name, String alias) {
        this(name, alias, false);
    }

    public static Enemy fromJson(JsonObject jsonObject) {
        String name = "";
        String alias = "";
        boolean exclusive = true;
        if (jsonObject.get("name") != null && !jsonObject.get("name").isJsonNull()) {
            name = jsonObject.get("name").getAsString();
        }
        if (jsonObject.get("alias") != null && !jsonObject.get("alias").isJsonNull()) {
            alias = jsonObject.get("alias").getAsString();
        }
        if (jsonObject.get("exclusive") != null && !jsonObject.get("exclusive").isJsonNull()) {
            exclusive = jsonObject.get("exclusive").getAsBoolean();
        }
        return new Enemy(name, alias, exclusive);
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
        Vape.INSTANCE.getFriendManager().refreshPlayerNames();
    }

    public String getDisplayName() {
        if (Vape.INSTANCE.getEnemyManager().useAlias.getEffectiveValue().booleanValue()) {
            return this.getAlias();
        }
        return this.name;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.name);
        jsonObject.addProperty("alias", this.alias);
        jsonObject.addProperty("exclusive", Boolean.valueOf(this.exclusive));
        return jsonObject;
    }

    public Enemy(String name, String alias, boolean exclusive) {
        super(TargetType.ENEMY);
        this.name = name;
        this.alias = alias;
        this.exclusive = exclusive;
    }

    public boolean isExclusive() {
        return this.exclusive;
    }

    public String getName() {
        return this.name;
    }
}

