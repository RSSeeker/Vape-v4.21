package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.mappings.MSPacketMapChunkBulk;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityPotion
extends Mapping {
    private MappingMethod getItemMethod;
    private MappingField potionDamageField;
    private MappingMethod getPotionMethod;

    public Object getPotionItem(Object potionEntityHandle) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getItemMethod.invokeObject(potionEntityHandle);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return this.getPotionMethod.invokeObject(potionEntityHandle);
        }
        return this.potionDamageField.getObject(potionEntityHandle);
    }

    public MEntityPotion() {
        this(MSPacketMapChunkBulk.getMappingControlFlowToken());
    }

    private MEntityPotion(String mappingControlFlowToken) {
        super(MappedClasses.Zf);
        if (mappingControlFlowToken != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.getItemMethod = ((MappingMethodBuilder)((MappingMethodBuilder)this
                        .methodBuilder("getItem", MappedClasses.VK)
                        .setOwnerClass(MappedClasses.ub))
                        .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Y4))
                        .buildMethod();
            } else if (ForgeVersion.MC_1_12_2.d()) {
                this.getPotionMethod = this.Y("getPotion", true, MappedClasses.VK);
            } else {
                this.potionDamageField = this.J("potionDamage", true, MappedClasses.VK);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MSPacketMapChunkBulk.setMappingControlFlowToken("JSVoh");
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            this.getPotionMethod = this.Y("getPotion", true, MappedClasses.VK);
        }
        this.potionDamageField = this.J("potionDamage", true, MappedClasses.VK);
        if (GuiComponent.getLegacyComponentState() == null) {
            MSPacketMapChunkBulk.setMappingControlFlowToken("JSVoh");
        }
    }
}
