package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.ProfileModuleSnapshot;
import gg.vape.config.ProfileSnapshot;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.ValueComponentFactory;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.BooleanValue;
import gg.vape.value.ConditionalValue;
import gg.vape.value.ListValue;
import gg.vape.value.ModeValue;
import gg.vape.value.SubModuleValue;
import gg.vape.value.Value;
import gg.vape.value.ValueSnapshot;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileSnapshotGuiBuilder {
    private static final String RESET_ICON = "reset_circle";
    private static final int SETTING_ROW_WIDTH = 210;
    private final HashMap<Value<?, ?>, Value<?, ?>> parentByProxyValue;
    private final HashMap<ProfileModuleSnapshot, List<GuiComponent>> componentsByModule;
    private final ProfileSnapshot snapshot;
    private final HashMap<Value<?, ?>, ValueSnapshot<?, ?>> snapshotByProxyValue;
    private final HashMap<Value<?, ?>, Value<?, ?>> proxyBySourceValue;
    private final Color resetButtonColor = new Color(54, 53, 54, 128);
    private final HashMap<Value<?, ?>, GuiComponent> componentByProxyValue;

    public ProfileSnapshotGuiBuilder(ProfileSnapshot profileSnapshot) {
        this.proxyBySourceValue = new HashMap<>();
        this.snapshotByProxyValue = new HashMap<>();
        this.parentByProxyValue = new HashMap<>();
        this.componentByProxyValue = new HashMap<>();
        this.componentsByModule = new HashMap<>();
        this.snapshot = profileSnapshot;
        this.buildComponents();
    }

    public void resetValue(Value<?, ?> sourceValue) {
        Value<?, ?> proxyValue = this.proxyBySourceValue.get(sourceValue);
        if (proxyValue == null) {
            return;
        }
        ValueSnapshot<?, ?> valueSnapshot = this.snapshotByProxyValue.get(proxyValue);
        if (valueSnapshot == null) {
            return;
        }
        proxyValue.reset();
        valueSnapshot.setValue(proxyValue.getValue());
        sourceValue.notifyChanged();
        proxyValue.notifyChanged();
        if (sourceValue instanceof ConditionalValue) {
            ConditionalValue conditionalValue = (ConditionalValue)sourceValue;
            List<Value> list = conditionalValue.getDependentValues();
            for (Value childValue : list) {
                Value<?, ?> childProxyValue = this.proxyBySourceValue.get(childValue);
                GuiComponent guiComponent = this.componentByProxyValue.get(childProxyValue);
                if (guiComponent != null) continue;
                this.resetValue(childValue);
            }
        }
    }

    public List<GuiComponent> getModuleComponents(ProfileModuleSnapshot moduleSnapshot) {
        return this.componentsByModule.get(moduleSnapshot);
    }

    private void buildComponents() {
        for (ProfileModuleSnapshot profileModuleSnapshot : this.snapshot.getAllModules()) {
            Object object;
            Object object2;
            Value<?, ?> value;
            Value<?, ?> value2;
            ArrayList<GuiComponent> arrayList = new ArrayList<GuiComponent>();
            this.componentsByModule.put(profileModuleSnapshot, arrayList);
            for (ValueSnapshot<?, ?> valueSnapshot : profileModuleSnapshot.getValueSnapshots()) {
                Value<?, ?> snapshotValue = ValueComponentFactory.createSnapshotProxyValue(valueSnapshot);
                this.registerSubModuleValues(snapshotValue);
                GuiComponent valueComponent = ValueComponentFactory.createMainValueComponent(snapshotValue, true);
                if (valueComponent == null) continue;
                boolean bl = true;
                if (valueSnapshot.getSourceValue() instanceof ListValue && ((Value)(object2 = (ListValue)valueSnapshot.getSourceValue())).getParent() instanceof BooleanValue && ((BooleanValue)(object = (BooleanValue)((Value)object2).getParent())).getTerminalDependentValue() != null && ((BooleanValue)object).getTerminalDependentValue().equals(object2)) {
                    bl = false;
                }
                valueComponent.setUseExplicitWidth(true);
                valueComponent.setExplicitWidth(182.0);
                valueComponent.setHorizontalInset(0.0);
                FlowLayoutComponent flowLayoutComponent = new FlowLayoutComponent(SETTING_ROW_WIDTH);
                flowLayoutComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
                ProfileSnapshotValueResetButtonComponent resetButton = new ProfileSnapshotValueResetButtonComponent(RESET_ICON, this.resetButtonColor, 0.75, 11.0, 11.0, valueComponent, valueSnapshot);
                resetButton.setVisible(!snapshotValue.isDefault());
                ((InteractiveComponent)resetButton).setClickListener(() -> this.resetValue((Value<?, ?>)valueSnapshot.getSourceValue()));
                if (bl) {
                    flowLayoutComponent.h(valueComponent, new Object[0]);
                    flowLayoutComponent.h(resetButton, new Object[0]);
                    arrayList.add(flowLayoutComponent);
                }
                this.proxyBySourceValue.put((Value<?, ?>)valueSnapshot.getSourceValue(), snapshotValue);
                if (((Value)valueSnapshot.getSourceValue()).getParent() != null) {
                    this.parentByProxyValue.put(snapshotValue, ((Value)valueSnapshot.getSourceValue()).getParent());
                }
                this.snapshotByProxyValue.put(snapshotValue, valueSnapshot);
                if (!bl) continue;
                this.componentByProxyValue.put(snapshotValue, valueComponent);
            }
            for (Value value3 : this.parentByProxyValue.keySet()) {
                ConditionalValue conditionalValue;
                value2 = this.parentByProxyValue.get(value3);
                if (value2 == null) continue;
                value = this.proxyBySourceValue.get(value2);
                if (value instanceof ConditionalValue) {
                    conditionalValue = (ConditionalValue)value;
                    conditionalValue.addDependentValues(value3);
                }
                if (!(value3 instanceof ListValue) || !(value instanceof BooleanValue)) continue;
                conditionalValue = (BooleanValue)value;
                object2 = (ListValue)value3;
                ((BooleanValue)conditionalValue).setCompactListValue((ListValue)object2);
                object = this.componentByProxyValue.get(conditionalValue);
                if (!(object instanceof BooleanToggleComponent)) continue;
                BooleanToggleComponent booleanToggleComponent = (BooleanToggleComponent)object;
                booleanToggleComponent.initializeCompactListComponent();
                booleanToggleComponent.getCompactListComponent().setUseAnchoredPopup(true);
            }
        }
    }

    private void registerSubModuleValues(ModeValue modeValue) {
        for (ModeSelection modeSelection : modeValue.getModes()) {
            if (!(modeSelection instanceof SubModuleValue)) continue;
            SubModuleValue subModuleValue = (SubModuleValue)modeSelection;
            for (Value<?, ?> value : ((Mod)subModuleValue.getInstance()).getAllValues()) {
                Value<?, ?> value2 = this.proxyBySourceValue.get(value);
                if (value2 == null) continue;
                modeValue.addActiveMode(value2, subModuleValue);
            }
        }
    }

    public void resetModule(ProfileModuleSnapshot moduleSnapshot) {
        for (ValueSnapshot<?, ?> valueSnapshot : moduleSnapshot.getValueSnapshots()) {
            this.resetValue((Value<?, ?>)valueSnapshot.getSourceValue());
        }
        moduleSnapshot.resetBind();
        moduleSnapshot.setEnabled(false);
    }

    public void resetAllModules() {
        for (ProfileModuleSnapshot moduleSnapshot : this.snapshot.getAllModules()) {
            this.resetModule(moduleSnapshot);
        }
    }

    private void registerSubModuleValues(Value<?, ?> value) {
        if (value instanceof ModeValue) {
            this.registerSubModuleValues((ModeValue)value);
        }
    }
}
