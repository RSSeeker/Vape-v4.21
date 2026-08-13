package gg.vape.module.render.hud;

import gg.vape.unmap.INamed;
import java.util.ArrayList;
import java.util.List;

public class HudModuleGroup
implements INamed {
    public static final HudModuleGroup GAME;
    public static final List<HudModuleGroup> GROUPS;
    private final String name;
    private final String key;
    public static final HudModuleGroup ALL;
    public static final HudModuleGroup HUD;
    public static final HudModuleGroup FAVORITE;

    public String toString() {
        return this.name;
    }

    public HudModuleGroup(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    static {
        FAVORITE = new HudModuleGroup("Favorite");
        ALL = new HudModuleGroup("All");
        HUD = new HudModuleGroup("HUD");
        GAME = new HudModuleGroup("Game");
        GROUPS = new ArrayList<HudModuleGroup>();
        GROUPS.add(FAVORITE);
        GROUPS.add(ALL);
        GROUPS.add(HUD);
        GROUPS.add(GAME);
    }

    public HudModuleGroup(String name) {
        this(name, "");
    }

    public static List<HudModuleGroup> getGroups() {
        return GROUPS;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

