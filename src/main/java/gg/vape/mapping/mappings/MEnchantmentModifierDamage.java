package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEnchantmentModifierDamage
extends Mapping {
    private final MappingField sourceField;
    private final MappingField damageModifierField;

    public void setDamageModifier(Object modifierHandle, int damageModifier) {
        this.damageModifierField.setInt(modifierHandle, damageModifier);
    }

    public Object getSource(Object modifierHandle) {
        return this.sourceField.getObject(modifierHandle);
    }

    public int getDamageModifier(Object modifierHandle) {
        return this.damageModifierField.getInt(modifierHandle);
    }

    public void setSource(Object modifierHandle, Object damageSourceHandle) {
        this.sourceField.setObject(modifierHandle, damageSourceHandle);
    }

    public MEnchantmentModifierDamage() {
        super(MappedClasses.l8);
        Class<Integer> damageModifierFieldType = Integer.TYPE;
        boolean damageModifierFieldPublic = true;
        String damageModifierFieldName = "damageModifier";
        MEnchantmentModifierDamage mapping = this;
        this.damageModifierField = mapping.J(damageModifierFieldName, damageModifierFieldPublic, damageModifierFieldType);
        Class sourceFieldType = MappedClasses.uB;
        boolean sourceFieldPublic = true;
        String sourceFieldName = "source";
        MEnchantmentModifierDamage sourceMapping = this;
        this.sourceField = sourceMapping.J(sourceFieldName, sourceFieldPublic, sourceFieldType);
    }
}

