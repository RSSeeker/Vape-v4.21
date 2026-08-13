package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterLogicalOperator;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

class InventoryFilterLogicalOperatorDividerComponent
extends GuiComponent {
    private final InventoryFilterLogicalOperator operator;

    public InventoryFilterLogicalOperatorDividerComponent(InventoryFilterLogicalOperator inventoryFilterLogicalOperator) {
        this.operator = inventoryFilterLogicalOperator;
    }

    @Override
    public double x() {
        return 50.0;
    }


    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.getAlternateFontRenderer(0.65);
        String label = this.operator.getName().toUpperCase();
        double badgeWidth = 20.0;
        double componentX = this.G$src$D$1b2f02a();
        this.getClass();
        double badgeX = componentX - 5.0 / 2.0;
        if (this.operator == InventoryFilterLogicalOperator.AND) {
            GuiRenderPrimitives.C(badgeX + badgeWidth / 2.0, this.n() - 1.0, 1.0, 5.0, InventoryFilterLogicalOperatorDividerComponent.J.o);
            GuiRenderPrimitives.C(badgeX + badgeWidth / 2.0, this.n() + this.L() / 2.0 + fontRenderer.d(label) + 2.0, 1.0, 5.0, InventoryFilterLogicalOperatorDividerComponent.J.o);
        }
        double labelY = this.n() + this.L() / 2.0 - fontRenderer.d(label) / 2.0;
        this.getClass();
        double badgeY = labelY - 5.0 / 2.0;
        double labelHeight = fontRenderer.d(label);
        this.getClass();
        GuiRenderPrimitives.B(badgeX, badgeY, badgeWidth, labelHeight + 5.0, InventoryFilterLogicalOperatorDividerComponent.J.R, 1.0f);
        fontRenderer.W(label, badgeX + badgeWidth / 2.0, labelY, Color.WHITE);
    }
}

