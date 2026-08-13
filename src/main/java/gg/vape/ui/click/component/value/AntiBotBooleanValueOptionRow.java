package gg.vape.ui.click.component.value;

import gg.vape.module.blatant.antibot.AntiBotBooleanValue;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class AntiBotBooleanValueOptionRow
extends GuiComponent {
    private static final double SWATCH_BORDER_WIDTH = 1.0;
    private final AntiBotBooleanValue teamColorValue;
    private final String label;
    private static final double SWATCH_WIDTH = 8.0;
    private static final double SWATCH_HEIGHT = 8.0;

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 16.0;
    }

    @Override
    public void F() {
    }


    public String getFormattedTeamColor() {
        Integer teamColor = (Integer)this.teamColorValue.getValue();
        if (teamColor == null) {
            return "None";
        }
        return String.format("#%06X", teamColor);
    }

    public AntiBotBooleanValueOptionRow(AntiBotBooleanValue antiBotBooleanValue) {
        this(antiBotBooleanValue, "Team color");
    }

    @Override
    public double x() {
        return 110.0;
    }

    public AntiBotBooleanValueOptionRow(AntiBotBooleanValue antiBotBooleanValue, String label) {
        this.teamColorValue = antiBotBooleanValue;
        this.label = label;
        this.bindValue(antiBotBooleanValue);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void H() {
        this.onDisable();
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        smoothFontRenderer.d(this.label, this.G$src$D$1b2f02a() + 5.0, this.n() + this.L() / 2.0 - smoothFontRenderer.d(this.label) / 2.0, AntiBotBooleanValueOptionRow.J.Z);
        Integer teamColor = (Integer)this.teamColorValue.getValue();
        Color swatchColor = teamColor != null ? new Color(teamColor) : Color.GRAY;
        double swatchX = this.G$src$D$1b2f02a() + this.A() - 5.0 - SWATCH_WIDTH;
        double swatchY = this.n() + (this.L() - SWATCH_HEIGHT) / 2.0;
        GuiRenderPrimitives.B(swatchX, swatchY, SWATCH_WIDTH, SWATCH_HEIGHT, swatchColor, (float)SWATCH_BORDER_WIDTH);
    }

    public AntiBotBooleanValue getTeamColorValue() {
        return this.teamColorValue;
    }

    @Override
    public void u() {
        this.w("Current team color: " + this.getFormattedTeamColor());
    }
}

