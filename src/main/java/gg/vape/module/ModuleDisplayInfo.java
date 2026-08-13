package gg.vape.module;

import java.awt.Color;

public class ModuleDisplayInfo {
    private final String suffix;
    private final String label;
    private final String description;
    private final Color color;

    public ModuleDisplayInfo(String label, Color color, String description, String suffix) {
        this.label = label;
        this.color = color;
        this.description = description;
        this.suffix = suffix;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescription() {
        return this.description;
    }

    public Color getColor() {
        return this.color;
    }

    public ModuleDisplayInfo(String string, Color color) {
        this(string, color, null, null);
    }

    public ModuleDisplayInfo(String string, Color color, String string2) {
        this(string, color, null, string2);
    }

    public String getSuffix() {
        return this.suffix;
    }
}

