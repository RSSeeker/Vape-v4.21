package gg.vape.mapping;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemMappingEntry {
    private final String modernId;
    @Nullable
    private final Integer legacyId;
    private static String[] obfuscationTable;
    @Nullable
    private final String legacyIdString;
    private final String resourceKey;
    @Nullable
    private final Integer metadata;

    public static void setObfuscationTable(String[] table) {
        obfuscationTable = table;
    }


    public static ItemMappingEntry parse(String mappingLine) {
        String legacyIdToken;
        String[] columns = mappingLine.split(",", -1);
        String resourceKey = columns[0];
        String modernId = columns[1];
        legacyIdToken = columns.length > 2 ? columns[2] : null;
        if (legacyIdToken == null || legacyIdToken.isEmpty() || legacyIdToken.equals("null")) {
            legacyIdToken = null;
        }
        return new ItemMappingEntry(resourceKey, modernId, legacyIdToken);
    }

    static {
        ItemMappingEntry.setObfuscationTable(null);
    }

    @Nullable
    public String getLegacyIdString() {
        return this.legacyIdString;
    }

    public boolean hasModernId() {
        return this.modernId != null;
    }

    @Nullable
    public ItemStack resolveItemStack() {
        ItemStack itemStack;
        Item item = this.resolveItem();
        if (item == null || item.isNull()) {
            return null;
        }
        if (ForgeVersion.MC_26_1.d()) {
            itemStack = ItemStack.G(item);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            itemStack = ItemStack.S(item);
        } else {
            itemStack = ItemStack.S(item);
            if (this.metadata != null) {
                itemStack.s(this.metadata);
            }
        }
        return itemStack;
    }

    @Nullable
    public Item resolveItem() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Item.L(this.modernId);
        }
        if (this.legacyId == null) {
            return null;
        }
        return Item.T(this.legacyId);
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public String getModernId() {
        return this.modernId;
    }

    public ItemMappingEntry(String resourceKey, String modernId, @Nullable String legacyIdString) {
        this.resourceKey = resourceKey;
        this.legacyIdString = legacyIdString;
        this.modernId = modernId;
        if (legacyIdString != null) {
            String[] legacyParts = legacyIdString.split(":");
            this.legacyId = Integer.parseInt(legacyParts[0]);
            this.metadata = legacyParts.length > 1 ? Integer.parseInt(legacyParts[1]) : 0;
        } else {
            this.legacyId = null;
            this.metadata = null;
        }
    }

    @Nullable
    public Integer getLegacyId() {
        return this.legacyId;
    }

    public String toString() {
        return "UniversalItem{resourceKey='" + this.resourceKey + '\'' + ", legacyId='" + this.legacyIdString + '\'' + ", modernId='" + this.modernId + '\'' + '}';
    }

    @Nullable
    public Integer getMetadata() {
        return this.metadata;
    }

    public static String[] getObfuscationTable() {
        return obfuscationTable;
    }
}

