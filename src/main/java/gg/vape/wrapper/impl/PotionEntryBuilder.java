package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.PotionEntry;
import java.util.LinkedHashMap;
import java.util.Map;

public class PotionEntryBuilder {
    private Short legacyId;
    private final Map<ForgeVersion, Integer> idsByVersion = new LinkedHashMap<ForgeVersion, Integer>();
    private ForgeVersion supportedVersion;
    private String name;

    public PotionEntry build() {
        return new PotionEntry(this);
    }

    public static Map<ForgeVersion, Integer> getIdsByVersion(PotionEntryBuilder builder) {
        return builder.idsByVersion;
    }

    public PotionEntryBuilder setLegacyId(short legacyId) {
        this.legacyId = legacyId;
        return this;
    }

    public static ForgeVersion getSupportedVersion(PotionEntryBuilder builder) {
        return builder.supportedVersion;
    }

    public PotionEntryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PotionEntryBuilder setSupportedVersion(ForgeVersion supportedVersion) {
        this.supportedVersion = supportedVersion;
        return this;
    }

    public PotionEntryBuilder addId(ForgeVersion version, int id) {
        this.idsByVersion.put(version, id);
        return this;
    }

    public static String getName(PotionEntryBuilder builder) {
        return builder.name;
    }

    public static Short getLegacyId(PotionEntryBuilder builder) {
        return builder.legacyId;
    }
}
