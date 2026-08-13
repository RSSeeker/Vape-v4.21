package gg.vape.value;

import gg.vape.ui.click.frame.impl.main.ClickGuiModuleCardRenderState;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.SubModuleValue;
import gg.vape.value.Value;
import gg.vape.value.ValueDisplayDescriptor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public final class ModuleValueDisplayFormatter {
    public static List<ClickGuiModuleCardRenderState> buildDescriptorRenderStates(List<ValueDisplayDescriptor> descriptors) {
        List<ValueDisplayDescriptor> displayableDescriptors = ModuleValueDisplayFormatter.filterDisplayableDescriptors(descriptors);
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneDescriptorMode(displayableDescriptors);
        ModeValue subModuleMode = ModuleValueDisplayFormatter.findUniqueDescriptorSubModuleMode(displayableDescriptors);
        List<ClickGuiModuleCardRenderState> renderStates = ModuleValueDisplayFormatter.buildDescriptorRenderStatesInternal(displayableDescriptors, hasExactlyOneMode, subModuleMode, false);
        int textLength = ModuleValueDisplayFormatter.getTextLength(renderStates);
        if (textLength < 50) {
            return ModuleValueDisplayFormatter.buildDescriptorRenderStatesInternal(displayableDescriptors, hasExactlyOneMode, subModuleMode, true);
        }
        return renderStates;
    }

    private static String formatValue(Value<?, ?> value, boolean hasExactlyOneMode, boolean isPrimaryMode) {
        if (value instanceof BooleanValue) {
            BooleanValue booleanValue = (BooleanValue)value;
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents(booleanValue)) {
                return ModuleValueDisplayFormatter.formatLimitDependents(booleanValue);
            }
            return booleanValue.getDisplayName();
        }
        String displayValue = value.getDisplayValue();
        if ((hasExactlyOneMode || isPrimaryMode) && value instanceof ModeValue) {
            return displayValue;
        }
        if (displayValue.isEmpty()) {
            return value.getName();
        }
        return displayValue + " " + value.getName();
    }

    private ModuleValueDisplayFormatter() {
    }

    private static List<Value<?, ?>> filterDisplayableValues(List<Value<?, ?>> values) {
        HashSet<Value> dependentValues = new HashSet<Value>();
        HashSet<Object> colorOnlyToggles = new HashSet<Object>();
        for (Value<?, ?> value : values) {
            if (!(value instanceof BooleanValue)) continue;
            BooleanValue booleanValue = (BooleanValue)value;
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents(booleanValue)) {
                for (Value dependentValue : booleanValue.getDependentValues()) {
                    dependentValues.add(dependentValue);
                }
                continue;
            }
            if (!ModuleValueDisplayFormatter.hasOnlyColorDependents(booleanValue)) continue;
            colorOnlyToggles.add(value);
        }
        ArrayList<Value<?, ?>> displayableValues = new ArrayList<Value<?, ?>>();
        for (Value<?, ?> value : values) {
            if (dependentValues.contains(value) || colorOnlyToggles.contains(value) || !ModuleValueDisplayFormatter.shouldDisplayValue(value)) continue;
            displayableValues.add(value);
        }
        return displayableValues;
    }

    private static boolean hasOnlyColorDependents(BooleanValue booleanValue) {
        List<Value> dependentValues = booleanValue.getDependentValues();
        if (dependentValues.isEmpty()) {
            return false;
        }
        for (Value value : dependentValues) {
            if (value instanceof ColorValue) continue;
            return false;
        }
        return true;
    }

    private static boolean hasNonEmptyLimitDependent(BooleanValue booleanValue) {
        for (Value value : booleanValue.getDependentValues()) {
            LimitValue limitValue;
            if (!(value instanceof LimitValue) || ((List)(limitValue = (LimitValue)value).getValue()).isEmpty()) continue;
            return true;
        }
        return false;
    }

    private static String formatDescriptor(ValueDisplayDescriptor descriptor, boolean hasExactlyOneMode, boolean isPrimaryMode, boolean useFullName) {
        Value<?, ?> value = descriptor.getValue();
        if (value instanceof BooleanValue) {
            BooleanValue booleanValue = (BooleanValue)value;
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents(booleanValue)) {
                return ModuleValueDisplayFormatter.formatLimitDependents(booleanValue);
            }
            return useFullName ? descriptor.getFullName() : descriptor.getDisplayName();
        }
        String displayValue = value.getDisplayValue();
        if ((hasExactlyOneMode || isPrimaryMode) && value instanceof ModeValue) {
            return displayValue;
        }
        String displayName = useFullName ? descriptor.getFullName() : descriptor.getDisplayName();
        if (displayValue.isEmpty()) {
            return displayName;
        }
        return displayValue + " " + displayName;
    }

    private static boolean hasExactlyOneModeValue(List<Value<?, ?>> values) {
        int modeCount = 0;
        for (Value<?, ?> value : values) {
            if (!(value instanceof ModeValue) || ++modeCount <= 1) continue;
            return false;
        }
        return modeCount == 1;
    }


    private static boolean hasExactlyOneDescriptorMode(List<ValueDisplayDescriptor> descriptors) {
        int modeCount = 0;
        for (ValueDisplayDescriptor descriptor : descriptors) {
            if (!(descriptor.getValue() instanceof ModeValue) || ++modeCount <= 1) continue;
            return false;
        }
        return modeCount == 1;
    }

    public static String formatDescriptorSummary(List<ValueDisplayDescriptor> descriptors) {
        List<ValueDisplayDescriptor> displayableDescriptors = ModuleValueDisplayFormatter.filterDisplayableDescriptors(descriptors);
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneDescriptorMode(displayableDescriptors);
        ModeValue modeValue = ModuleValueDisplayFormatter.findUniqueDescriptorSubModuleMode(displayableDescriptors);
        if (modeValue != null) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            boolean addedValue = false;
            Iterator<ValueDisplayDescriptor> iterator = displayableDescriptors.iterator();
            while (iterator.hasNext()) {
                ValueDisplayDescriptor descriptor = iterator.next();
                boolean isPrimaryMode = descriptor.getValue() == modeValue;
                String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, isPrimaryMode, false);
                if (formattedValue.isEmpty()) continue;
                stringJoiner.add(formattedValue);
                addedValue = true;
            }
            if (!addedValue) {
                return "";
            }
            String summary = stringJoiner.toString();
            if (summary.length() < 50) {
                StringJoiner fullNameSummary = new StringJoiner(", ");
                Iterator<ValueDisplayDescriptor> iterator2 = displayableDescriptors.iterator();
                while (iterator2.hasNext()) {
                    ValueDisplayDescriptor descriptor = iterator2.next();
                    boolean isPrimaryMode = descriptor.getValue() == modeValue;
                    String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, isPrimaryMode, true);
                    if (formattedValue.isEmpty()) continue;
                    fullNameSummary.add(formattedValue);
                }
                return fullNameSummary.toString();
            }
            return summary;
        }
        StringJoiner stringJoiner = new StringJoiner(", ");
        boolean addedValue = false;
        for (ValueDisplayDescriptor descriptor : displayableDescriptors) {
            String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, false, false);
            if (formattedValue.isEmpty()) continue;
            stringJoiner.add(formattedValue);
            addedValue = true;
        }
        if (!addedValue) {
            return "";
        }
        String summary = stringJoiner.toString();
        if (summary.length() < 50) {
            StringJoiner fullNameSummary = new StringJoiner(", ");
            for (ValueDisplayDescriptor descriptor : displayableDescriptors) {
                String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, false, true);
                if (formattedValue.isEmpty()) continue;
                fullNameSummary.add(formattedValue);
            }
            return fullNameSummary.toString();
        }
        return summary;
    }

    public static List<ClickGuiModuleCardRenderState> buildValueRenderStates(List<Value<?, ?>> values) {
        List<Value<?, ?>> displayableValues = ModuleValueDisplayFormatter.filterDisplayableValues(values);
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneModeValue(displayableValues);
        ModeValue subModuleMode = ModuleValueDisplayFormatter.findUniqueSubModuleMode(displayableValues);
        return ModuleValueDisplayFormatter.buildValueRenderStatesInternal(displayableValues, hasExactlyOneMode, subModuleMode);
    }

    private static boolean containsSubModuleMode(ModeValue modeValue) {
        for (ModeSelection modeSelection : modeValue.getModes()) {
            if (!(modeSelection instanceof SubModuleValue)) continue;
            return true;
        }
        return false;
    }

    public static String formatValueSummary(List<Value<?, ?>> values) {
        List<Value<?, ?>> displayableValues = ModuleValueDisplayFormatter.filterDisplayableValues(values);
        StringJoiner stringJoiner = new StringJoiner(", ");
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneModeValue(displayableValues);
        ModeValue modeValue = ModuleValueDisplayFormatter.findUniqueSubModuleMode(displayableValues);
        Iterator<Value<?, ?>> iterator = displayableValues.iterator();
        while (iterator.hasNext()) {
            Value<?, ?> value = iterator.next();
            String formattedValue = ModuleValueDisplayFormatter.formatValue(value, hasExactlyOneMode, value == modeValue);
            if (formattedValue.isEmpty()) continue;
            stringJoiner.add(formattedValue);
        }
        return stringJoiner.toString();
    }

    private static ModeValue findUniqueSubModuleMode(List<Value<?, ?>> values) {
        ModeValue modeValue = null;
        for (Value<?, ?> value : values) {
            if (!(value instanceof ModeValue) || !ModuleValueDisplayFormatter.containsSubModuleMode((ModeValue)value)) continue;
            if (modeValue != null) {
                return null;
            }
            modeValue = (ModeValue)value;
        }
        return modeValue;
    }

    private static List<ClickGuiModuleCardRenderState> buildDescriptorRenderStatesInternal(List<ValueDisplayDescriptor> descriptors, boolean hasExactlyOneMode, ModeValue modeValue, boolean useFullNames) {
        ArrayList<ClickGuiModuleCardRenderState> renderStates = new ArrayList<ClickGuiModuleCardRenderState>();
        boolean firstValue = true;
        boolean previousValueHasColor = false;
        if (modeValue != null) {
            for (ValueDisplayDescriptor descriptor : descriptors) {
                Color color;
                if (descriptor.getValue() != modeValue) continue;
                String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, true, useFullNames);
                if (formattedValue.isEmpty() && modeValue.getDisplayColor() == null) break;
                firstValue = false;
                if (!formattedValue.isEmpty()) {
                    renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
                }
                if ((color = modeValue.getDisplayColor()) == null) break;
                renderStates.add(ClickGuiModuleCardRenderState.b(color));
                previousValueHasColor = true;
                break;
            }
        }
        for (ValueDisplayDescriptor descriptor : descriptors) {
            Color color;
            Value<?, ?> value = descriptor.getValue();
            if (value == modeValue) continue;
            String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, false, useFullNames);
            if (formattedValue.isEmpty() && value.getDisplayColor() == null) continue;
            if (!firstValue) {
                renderStates.add(ClickGuiModuleCardRenderState.j(previousValueHasColor ? " " : ", "));
            }
            firstValue = false;
            if (!formattedValue.isEmpty()) {
                renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
            }
            previousValueHasColor = (color = value.getDisplayColor()) != null;
            if (!previousValueHasColor) continue;
            renderStates.add(ClickGuiModuleCardRenderState.b(color));
        }
        return renderStates;
    }

    private static int getTextLength(List<ClickGuiModuleCardRenderState> renderStates) {
        int textLength = 0;
        for (ClickGuiModuleCardRenderState renderState : renderStates) {
            if (!renderState.n$src$Z$1c2q0zn()) continue;
            textLength += renderState.n().length();
        }
        return textLength;
    }

    private static ModeValue findUniqueDescriptorSubModuleMode(List<ValueDisplayDescriptor> descriptors) {
        ModeValue modeValue = null;
        for (ValueDisplayDescriptor descriptor : descriptors) {
            Value<?, ?> value = descriptor.getValue();
            if (!(value instanceof ModeValue) || !ModuleValueDisplayFormatter.containsSubModuleMode((ModeValue)value)) continue;
            if (modeValue != null) {
                return null;
            }
            modeValue = (ModeValue)value;
        }
        return modeValue;
    }

    private static List<ClickGuiModuleCardRenderState> buildValueRenderStatesInternal(List<Value<?, ?>> values, boolean hasExactlyOneMode, ModeValue modeValue) {
        ArrayList<ClickGuiModuleCardRenderState> renderStates = new ArrayList<ClickGuiModuleCardRenderState>();
        boolean firstValue = true;
        boolean previousValueHasColor = false;
        if (modeValue != null) {
            String formattedValue = ModuleValueDisplayFormatter.formatValue(modeValue, hasExactlyOneMode, true);
            if (!formattedValue.isEmpty() || modeValue.getDisplayColor() != null) {
                Color color;
                firstValue = false;
                if (!formattedValue.isEmpty()) {
                    renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
                }
                if ((color = modeValue.getDisplayColor()) != null) {
                    renderStates.add(ClickGuiModuleCardRenderState.b(color));
                    previousValueHasColor = true;
                }
            }
        }
        for (Value<?, ?> value : values) {
            Color color;
            String formattedValue;
            if (value == modeValue || (formattedValue = ModuleValueDisplayFormatter.formatValue(value, hasExactlyOneMode, false)).isEmpty() && value.getDisplayColor() == null) continue;
            if (!firstValue) {
                renderStates.add(ClickGuiModuleCardRenderState.j(previousValueHasColor ? " " : ", "));
            }
            firstValue = false;
            if (!formattedValue.isEmpty()) {
                renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
            }
            previousValueHasColor = (color = value.getDisplayColor()) != null;
            if (!previousValueHasColor) continue;
            renderStates.add(ClickGuiModuleCardRenderState.b(color));
        }
        return renderStates;
    }

    private static boolean hasOnlyLimitDependents(BooleanValue booleanValue) {
        List<Value> dependentValues = booleanValue.getDependentValues();
        if (dependentValues.isEmpty()) {
            return false;
        }
        for (Value value : dependentValues) {
            if (value instanceof LimitValue) continue;
            return false;
        }
        return true;
    }

    private static List<ValueDisplayDescriptor> filterDisplayableDescriptors(List<ValueDisplayDescriptor> descriptors) {
        ArrayList<ValueDisplayDescriptor> displayableDescriptors = new ArrayList<ValueDisplayDescriptor>();
        for (ValueDisplayDescriptor descriptor : descriptors) {
            if (!ModuleValueDisplayFormatter.shouldDisplayValue(descriptor.getValue())) continue;
            displayableDescriptors.add(descriptor);
        }
        return displayableDescriptors;
    }

    private static boolean shouldDisplayValue(Value<?, ?> value) {
        if (value instanceof EntityTargetFilterValue) {
            return false;
        }
        if (!value.areConditionsMet()) {
            return false;
        }
        if (value instanceof BooleanValue) {
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents((BooleanValue)value)) {
                return ((BooleanValue)value).getEffectiveValue() != false && ModuleValueDisplayFormatter.hasNonEmptyLimitDependent((BooleanValue)value);
            }
            return ((BooleanValue)value).getEffectiveValue();
        }
        return !value.getDisplayValue().isEmpty() || value.getDisplayColor() != null;
    }

    private static String formatLimitDependents(BooleanValue booleanValue) {
        StringJoiner summary = new StringJoiner(", ");
        HashSet<Value> seenValues = new HashSet<Value>();
        for (Value value : booleanValue.getDependentValues()) {
            LimitValue limitValue;
            int entryCount;
            if (!(value instanceof LimitValue) || !seenValues.add(value) || (entryCount = ((List)(limitValue = (LimitValue)value).getValue()).size()) == 0) continue;
            summary.add(entryCount + " " + limitValue.getName());
        }
        String formattedSummary = summary.toString();
        return formattedSummary.isEmpty() ? "" : formattedSummary;
    }
}
