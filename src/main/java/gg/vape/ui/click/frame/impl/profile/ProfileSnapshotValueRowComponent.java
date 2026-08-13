package gg.vape.ui.click.frame.impl.profile;

import gg.vape.module.utility.inventory.HotbarSlotRuleValue;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.unmap.ColorUtil;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.ListValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.value.StringMapValue;
import gg.vape.value.Value;
import gg.vape.value.ValueSnapshot;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ProfileSnapshotValueRowComponent
extends GuiComponent {
    private final ValueSnapshot<?, ?> valueSnapshot;
    private final TruncatedTextComponent nameLabel;
    private final SimpleTextLabelComponent valueLabel;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }

    @Override
    public void H() {
        int n;
        String string = ((Value)this.valueSnapshot.getSourceValue()).getName();
        if (this.valueSnapshot.getSourceValue() instanceof ListValue && ((Value)this.valueSnapshot.getSourceValue()).getParent() != null) {
            string = string + " (" + ((Value)this.valueSnapshot.getSourceValue()).getParent().getName() + ")";
        }
        this.nameLabel.setText(string);
        if (this.valueSnapshot.getSourceValue() instanceof BooleanValue) {
            if (((Boolean)this.valueSnapshot.getValue()).booleanValue()) {
                this.valueLabel.setText("ON");
            } else {
                this.valueLabel.setText("OFF");
            }
        } else if (this.valueSnapshot.getSourceValue() instanceof RandomValue) {
            RandomValue randomValue = (RandomValue)this.valueSnapshot.getSourceValue();
            double[] dArray = (double[])this.valueSnapshot.getValue();
            this.valueLabel.setText(randomValue.getEndpointFormat().format(dArray[0]) + " - " + randomValue.getEndpointFormat().format(dArray[1]));
        } else if (this.valueSnapshot.getSourceValue() instanceof NumberValue) {
            NumberValue numberValue = (NumberValue)this.valueSnapshot.getSourceValue();
            Double d = (Double)this.valueSnapshot.getValue();
            this.valueLabel.setText(numberValue.getInputFormat().format(d) + numberValue.getUnitSuffix());
        } else if (this.valueSnapshot.getSourceValue() instanceof ListValue) {
            List<?> list = (List<?>)this.valueSnapshot.getValue();
            int n2 = list.size();
            this.valueLabel.setText(n2 + " item" + (n2 == 1 ? "" : "s"));
            this.valueLabel.w(list.toString());
        } else if (this.valueSnapshot.getSourceValue() instanceof ModeValue) {
            ModeSelection modeSelection = (ModeSelection)this.valueSnapshot.getValue();
            this.valueLabel.setText(modeSelection.getName());
        } else if (this.valueSnapshot.getSourceValue() instanceof HotbarSlotRuleValue) {
            List<?> hotbarRules = (List<?>)this.valueSnapshot.getValue();
            int n3 = hotbarRules.size();
            this.valueLabel.setText(n3 + " item" + (n3 == 1 ? "" : "s"));
        } else if (this.valueSnapshot.getSourceValue() instanceof StringMapValue) {
            Map<?, ?> stringMap = (Map<?, ?>)this.valueSnapshot.getValue();
            int n4 = stringMap.size();
            this.valueLabel.setText(n4 + " item" + (n4 == 1 ? "" : "s"));
        } else if (this.valueSnapshot.getSourceValue() instanceof EntityTargetFilterValue) {
            this.nameLabel.setText("Target Settings");
            Boolean[] targetSettings = (Boolean[])this.valueSnapshot.getValue();
            EntityTargetFilterValue entityTargetFilterValue = (EntityTargetFilterValue)this.valueSnapshot.getSourceValue();
            List<BooleanValue> filterValues = entityTargetFilterValue.getFilterValues();
            boolean targetsPlayers = targetSettings[filterValues.indexOf(entityTargetFilterValue.getPlayersValue())];
            boolean targetsMobs = targetSettings[filterValues.indexOf(entityTargetFilterValue.getMobsValue())];
            boolean targetsPeaceful = targetSettings[filterValues.indexOf(entityTargetFilterValue.getPeacefulValue())];
            boolean targetsNeutral = targetSettings[filterValues.indexOf(entityTargetFilterValue.getNeutralValue())];
            boolean ignoresInvisible = targetSettings[filterValues.indexOf(entityTargetFilterValue.getIgnoreInvisibleValue())];
            boolean ignoresNaked = targetSettings[filterValues.indexOf(entityTargetFilterValue.getIgnoreNakedValue())];
            boolean ignoresBehindWalls = targetSettings[filterValues.indexOf(entityTargetFilterValue.getIgnoreBehindWallsValue())];
            int enabledSettingCount = 0;
            for (Boolean settingEnabled : targetSettings) {
                if (!settingEnabled.booleanValue()) continue;
                ++enabledSettingCount;
            }
            this.valueLabel.setText(enabledSettingCount + " setting" + (enabledSettingCount == 1 ? "" : "s"));
            StringJoiner targetedEntities = new StringJoiner(", ");
            targetedEntities.setEmptyValue("none");
            if (targetsPlayers) {
                targetedEntities.add("players");
            }
            if (targetsMobs) {
                targetedEntities.add("mobs");
            }
            if (targetsPeaceful) {
                targetedEntities.add("peaceful");
            }
            if (targetsNeutral) {
                targetedEntities.add("neutral");
            }
            StringJoiner ignoredEntities = new StringJoiner(", ");
            ignoredEntities.setEmptyValue("none");
            if (ignoresInvisible) {
                ignoredEntities.add("invisible");
            }
            if (ignoresNaked) {
                ignoredEntities.add("naked");
            }
            if (ignoresBehindWalls) {
                ignoredEntities.add("behind walls");
            }
            this.valueLabel.w("Attack " + targetedEntities + " \nIgnore " + ignoredEntities);
        } else if (this.valueSnapshot.getSourceValue() instanceof ColorValue) {
            this.valueLabel.setText("   ");
            this.valueLabel.o(10.0);
            this.valueLabel.Y(10.0);
        } else {
            this.valueLabel.setText(this.valueSnapshot.getSourceValue() != null ? this.valueSnapshot.getSourceValue().toString() : "null");
        }
        double d = this.G$src$D$1b2f02a();
        this.getClass();
        this.nameLabel.K(d + 5.0);
        this.nameLabel.S(this.n() + 2.0);
        this.nameLabel.setDisabledOverlayColor(this.getDisabledOverlayColor());
        double d2 = this.A();
        this.getClass();
        this.nameLabel.setMaxWidth(d2 - (double)(5.0f * 2.0f) - 4.0 - this.valueLabel.A());
        this.nameLabel.setFontScale(0.75);
        double d3 = this.G$src$D$1b2f02a() + this.A() - this.valueLabel.getTextWidth();
        this.getClass();
        this.valueLabel.K(d3 - (double)(5.0f * 2.0f));
        this.valueLabel.S(this.n());
        this.valueLabel.o(this.valueLabel.getTextWidth());
        this.valueLabel.setBold(true);
        if (this.valueSnapshot.getSourceValue() instanceof ColorValue) {
            Object[] colorComponents = (Object[])this.valueSnapshot.getValue();
            float f = ((Double)colorComponents[0]).floatValue() / 255.0f;
            float f2 = ((Double)colorComponents[2]).floatValue() / 255.0f;
            float f3 = ((Double)colorComponents[1]).floatValue() / 255.0f;
            n = Color.HSBtoRGB(f, f2, f3);
            MutableColor mutableColor = new MutableColor(n, 255);
            MutableColor mutableColor2 = new MutableColor(ColorUtil.selectContrastingGray(mutableColor, 0, 240, true));
            mutableColor2.withAlpha(150);
            this.valueLabel.setTextColor(mutableColor2);
            ImageRenderer.drawImage(mutableColor, (float)(this.G$src$D$1b2f02a() + this.A() - 5.0 - 6.0), (float)this.n() + 1.0f, "colorpreview", 7.0f, 7.0f, false);
        } else {
            double d4 = this.valueLabel.G$src$D$1b2f02a() + 3.0;
            double d5 = this.n();
            double d6 = this.valueLabel.A();
            this.getClass();
            GuiRenderPrimitives.B(d4, d5, d6 + 5.0 - 0.5, this.valueLabel.L() - 1.0, new Color(255, 255, 255, 10), 1.0f);
        }
    }

    @Override
    public void F() {
    }

    @Override
    public double x() {
        return 80.0;
    }

    public ProfileSnapshotValueRowComponent(ValueSnapshot<?, ?> valueSnapshot) {
        this.valueSnapshot = valueSnapshot;
        this.nameLabel = new TruncatedTextComponent(((Value)valueSnapshot.getSourceValue()).getName(), "...", 50.0, 0.75, ProfileSnapshotValueRowComponent.J.Z, false, false);
        this.valueLabel = new SimpleTextLabelComponent("", 0.7);
        this.addChildren(this.nameLabel, this.valueLabel);
    }

    @Override
    public double C() {
        return 15.0;
    }


    @Override
    public void u() {
    }
}
