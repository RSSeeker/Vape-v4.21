package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import java.util.List;

public class MInventoryListBridge
extends Mapping {
    private MappingField armorField;
    private MappingField slotsField;

    public static Object getArmor(MInventoryListBridge mapping) {
        return mapping.readArmor();
    }

    public static List getSlots(MInventoryListBridge mapping, Object slotGroupHandle) {
        return mapping.readSlots(slotGroupHandle);
    }

    private List readSlots(Object slotGroupHandle) {
        return (List)this.slotsField.getObject(slotGroupHandle);
    }

    public MInventoryListBridge() {
        super(MappedClasses.Y_);
        Class armorFieldType = MappedClasses.Y_;
        boolean armorFieldPublic = true;
        String armorFieldName = "ARMOR";
        MInventoryListBridge mapping = this;
        this.armorField = mapping.registerStaticField(armorFieldName, armorFieldPublic, armorFieldType);
        Class<List> slotsFieldType = List.class;
        boolean slotsFieldPublic = true;
        String slotsFieldName = "slots";
        MInventoryListBridge slotsMapping = this;
        this.slotsField = slotsMapping.J(slotsFieldName, slotsFieldPublic, slotsFieldType);
    }

    private Object readArmor() {
        return this.armorField.getObject(null);
    }
}

