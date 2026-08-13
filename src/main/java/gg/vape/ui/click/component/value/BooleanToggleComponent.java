package gg.vape.ui.click.component.value;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.animation.ThemeColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.value.BooleanStateAdapter;
import gg.vape.ui.click.component.value.BooleanToggleComponentClickMouseListener;
import gg.vape.ui.click.component.value.CompactListValueComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.value.BooleanValue;
import gg.vape.value.ConditionalValue;
import gg.vape.value.ListValue;
import gg.vape.value.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BooleanToggleComponent
extends PanelComponent
implements BooleanStateAdapter<BooleanToggleComponent> {
    private boolean readOnly;
    protected double fontScale;
    List<String> wrappedLabelLines;
    private String wrappedLabelSource;
    private final float knobSize = 4.0f;
    private CompactListValueComponent compactListComponent;
    protected Color labelColor;
    private float switchOffset = 6.0f;
    private boolean brightenOnHover;
    private final ColorAnimation hoverColorAnimation;
    private final BooleanValue booleanValue;
    private final float switchBorderWidth = 1.0f;
    private final DoubleAnimation knobPositionAnimation;
    protected String label;
    private final ThemeColorAnimation enabledColorAnimation;
    private final float switchHeight = 6.0f;

    @Override
    public double C() {
        return 15.0 + (double)(this.getWrappedLabelLines().size() - 1) * this.getFontRenderer(this.fontScale).d(this.getLabel());
    }

    public CompactListValueComponent getCompactListComponent() {
        return this.compactListComponent;
    }

    public boolean isOn() {
        return this.knobPositionAnimation.I$src$Z$c48gtw();
    }

    public void toggle() {
        if (this.booleanValue != null) {
            if (this.booleanValue.getEffectiveValue().equals(this.isOn())) {
                if (!this.booleanValue.isChangeValid(this.booleanValue.getEffectiveValue() == false)) {
                    return;
                }
                this.booleanValue.setValue(this.booleanValue.getEffectiveValue() == false);
            }
            if (this.booleanValue.getDependentValues().size() > 0 && this.getParentFrameComponent() != null) {
                this.getParentFrameComponent().l$src$V$1mibm4x();
            }
        }
        this.enabledColorAnimation.J();
        this.knobPositionAnimation.J();
    }

    private boolean hasSingleListChildInMainGui() {
        if (!(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa() instanceof ClickGuiMainFrame) || this.booleanValue == null) {
            return false;
        }
        List<Value> childValues = this.booleanValue.getDependentValues();
        return childValues.size() == 1 && childValues.get(0) instanceof ListValue;
    }

    public BooleanToggleComponent(String label) {
        this(label, 0.9);
    }

    @Override
    public void c() {
        Color switchColor;
        super.c();
        if (this.isReadOnly()) {
            this.onDisable();
            this.renderLabel();
            String stateText = this.isOn() ? "ON" : "OFF";
            SmoothFontRenderer fontRenderer = this.getAlternateFontRenderer(this.fontScale);
            fontRenderer.d(stateText, this.G$src$D$1b2f02a() + this.A() - 4.0 - fontRenderer.N(stateText), this.n() + this.L() / 2.0 - fontRenderer.d(stateText) / 2.0, this.isOn() ? ClientSettings.INSTANCE.getAccentColor() : BooleanToggleComponent.J.Z);
            return;
        }
        this.l$src$V$1mibm4x();
        this.switchOffset = 5.0f;
        double switchX = this.G$src$D$1b2f02a() + this.A() - 10.0 - (double)this.switchOffset;
        double switchY = this.n() + this.L() / 2.0 - 3.0;
        Color colorSnapshot = switchColor = this.enabledColorAnimation.q() > 0.0 ? this.enabledColorAnimation.getInterpolatedColor() : this.hoverColorAnimation.getInterpolatedColor();
        if (this.w$src$Z$e457mb() && this.brightenOnHover && this.enabledColorAnimation.q() > 0.0) {
            switchColor = ColorUtil.offsetRgb(switchColor, 30.0);
        }
        this.renderLabel();
        GuiRenderPrimitives.j(switchX - 1.0, switchY - 0.5, 12.5, 7.0, switchColor);
        GuiRenderPrimitives.V((float)switchX + 1.0f + (float)this.knobPositionAnimation.getInterpolatedValue().doubleValue(), (float)switchY + 1.0f, 4.0, (float)(0.8 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor()), BooleanToggleComponent.J.i);
        if (this.getBoundValue() != null && this.getBoundValue() instanceof ConditionalValue && !((ConditionalValue)this.getBoundValue()).getDependentValues().isEmpty() && ((ConditionalValue)this.getBoundValue()).hasActiveDependentBranch() && this.getParentFrameComponent() != null) {
            FrameComponent parentFrame = this.getParentFrameComponent();
            List<Value> childValues = ((ConditionalValue)this.getBoundValue()).getDependentValues();
            GuiComponent nextChildComponent = null;
            boolean foundThisComponent = false;
            for (GuiComponent frameComponent : parentFrame.f()) {
                if (frameComponent.equals(this)) {
                    foundThisComponent = true;
                    continue;
                }
                if (!childValues.contains(frameComponent.getBoundValue()) || !foundThisComponent) continue;
                nextChildComponent = frameComponent;
                break;
            }
            if (nextChildComponent != null && !this.hasSingleListChildInMainGui()) {
                Color childBackgroundColor = nextChildComponent.getDisabledOverlayColor();
                ImageRenderer.drawImage(childBackgroundColor, (float)(this.G$src$D$1b2f02a() + this.A() / 8.0), (float)(this.n() + this.L() - 2.0), "dropdownnotch", 7.0f, 3.0f, false);
            }
        }
    }

    protected void synchronizeValueState() {
        if (this.booleanValue != null && !this.booleanValue.getEffectiveValue().equals(this.isOn()) && !this.isAnimating()) {
            this.toggle();
        }
    }

    @Override
    public BooleanToggleComponent setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public BooleanToggleComponent(String label, double fontScale) {
        this(label, fontScale, null);
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.hoverColorAnimation.J();
        }
    }

    protected void renderLabel() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        List<String> labelLines = this.getWrappedLabelLines();
        double totalTextHeight = fontRenderer.d(this.label) * (double)labelLines.size();
        double lineY = this.n() + this.L() / 2.0 - totalTextHeight / 2.0;
        for (String labelLine : labelLines) {
            if (!labelLine.equals(" ")) {
                fontRenderer.d(labelLine, this.G$src$D$1b2f02a() + this.getHorizontalInset(), lineY, this.labelColor);
            }
            lineY += totalTextHeight / (double)labelLines.size();
        }
    }

    public BooleanValue getBooleanValue() {
        return this.booleanValue;
    }

    @Override
    public void onEnable() {
        this.hoverColorAnimation.J();
    }

    public BooleanToggleComponent(String label, double fontScale, BooleanValue booleanValue) {
        super(0.0, 0.0);
        this.hoverColorAnimation = new ColorAnimation(0.15, BooleanToggleComponent.J.K, BooleanToggleComponent.J.W);
        this.enabledColorAnimation = new ThemeColorAnimation(0.15, BooleanToggleComponent.J.W);
        this.knobPositionAnimation = new DoubleAnimation(0.15, 0.0, this.switchOffset - 1.0f);
        this.labelColor = BooleanToggleComponent.J.Z;
        this.brightenOnHover = false;
        this.wrappedLabelSource = "";
        this.wrappedLabelLines = new ArrayList<String>();
        this.label = label;
        this.fontScale = fontScale;
        this.booleanValue = booleanValue;
        if (booleanValue != null) {
            this.bindValue(booleanValue);
            this.initializeCompactListComponent();
            this.w(booleanValue.getDescription());
        }
        this.S(0);
        this.addMouseListener(new BooleanToggleComponentClickMouseListener(this));
        this.synchronizeValueState();
        this.synchronizeAnimationsImmediately();
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public void u() {
        this.synchronizeValueState();
    }


    public void setBrightenOnHover(boolean brightenOnHover) {
        this.brightenOnHover = brightenOnHover;
    }

    private List<String> getWrappedLabelLines() {
        String currentLabel = this.getLabel();
        if (currentLabel.equals(this.wrappedLabelSource)) {
            return this.wrappedLabelLines;
        }
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        String sanitizedLabel = Vape.INSTANCE.getFontSelector().W().s(this.getLabel());
        String[] labelWords = sanitizedLabel.split(" ");
        double availableWidth = this.A() - 20.0;
        ArrayList<String> lines = new ArrayList<String>();
        double currentLineWidth = 0.0;
        String currentLine = "";
        for (String word : labelWords) {
            double appendedLineWidth = currentLineWidth + fontRenderer.N(word + " ");
            if (appendedLineWidth > availableWidth) {
                currentLineWidth = 0.0;
                lines.add(currentLine);
                currentLine = word + " ";
                continue;
            }
            currentLineWidth = appendedLineWidth;
            currentLine = currentLine + word + " ";
        }
        lines.add(currentLine);
        this.wrappedLabelLines = lines;
        this.wrappedLabelSource = currentLabel;
        return lines;
    }

    @Override
    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void toggleIfInteractive() {
        if (this.readOnly) {
            return;
        }
        this.toggle();
    }

    public void synchronizeAnimationsImmediately() {
        if (this.booleanValue != null) {
            if (this.booleanValue.getEffectiveValue().booleanValue()) {
                this.enabledColorAnimation.C();
                this.knobPositionAnimation.C();
            } else {
                this.enabledColorAnimation.O();
                this.knobPositionAnimation.O();
            }
        }
    }

    public void setValue(boolean enabled) {
        if (this.booleanValue != null) {
            this.booleanValue.setValue(enabled);
            if (this.booleanValue.getDependentValues().size() > 0 && this.getParentFrameComponent() != null) {
                this.getParentFrameComponent().l$src$V$1mibm4x();
            }
        }
        if (enabled) {
            this.enabledColorAnimation.c();
            this.knobPositionAnimation.c();
        } else {
            this.enabledColorAnimation.Z();
            this.knobPositionAnimation.Z();
        }
    }

    public DoubleAnimation getKnobPositionAnimation() {
        return this.knobPositionAnimation;
    }

    public void initializeCompactListComponent() {
        if (this.booleanValue.getTerminalDependentValue() != null) {
            this.compactListComponent = new CompactListValueComponent(this.booleanValue.getTerminalDependentValue());
            this.h(this.compactListComponent, "alignright, offsetX 19, offsetY 3");
            this.l$src$V$1mibm4x();
        }
    }

    public boolean isAnimating() {
        return !this.knobPositionAnimation.getInterpolatedValue().equals(this.knobPositionAnimation.getStartValue()) && !this.knobPositionAnimation.getInterpolatedValue().equals(this.knobPositionAnimation.getEndValue());
    }

    @Override
    public double x() {
        return 110.0;
    }

    public BooleanToggleComponent(BooleanValue booleanValue) {
        this(booleanValue != null ? booleanValue.getDisplayName() : null, 0.9, booleanValue);
    }
}

