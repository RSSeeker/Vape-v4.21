package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.ui.MaterialFilterSelectionRowContent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class MaterialFilterSelectionRow
extends GuiComponent {
    private final ItemFilterSelection selection;
    private final MaterialFilterSelectionRowContent content;
    private static final String CLOSE_ICON = "newclose";
    private final ColorAnimation lineColorAnimation;
    private final ColorAnimation closeIconColorAnimation;

    @Override
    public void c() {
        this.content.K(this.G$src$D$1b2f02a() + 3.0);
        this.content.S(this.n() + 3.0);
        super.c();
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() - 4.0, this.n() + 3.0, 6.0, 1.0, this.lineColorAnimation.getInterpolatedColor());
        ImageRenderer.drawImage(this.closeIconColorAnimation.getInterpolatedColor(), (float)(this.G$src$D$1b2f02a() + this.A() - 4.5), (float)(this.n() + 2.0), CLOSE_ICON, 7.0f, 7.0f, false);
    }


    public MaterialFilterSelectionRow(ItemFilterSelection itemFilterSelection) {
        this.lineColorAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), MaterialFilterSelectionRow.J.d);
        this.closeIconColorAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), Color.WHITE);
        this.selection = itemFilterSelection;
        this.content = new MaterialFilterSelectionRowContent(this, itemFilterSelection);
        this.setPropagateMouseEvents(true);
        this.addChildren(this.content);
    }

    @Override
    public double C() {
        return 18.0;
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.lineColorAnimation.J();
            this.closeIconColorAnimation.J();
        }
    }

    @Override
    public double x() {
        return 17.0;
    }

    @Override
    public void onEnable() {
        this.lineColorAnimation.J();
        this.closeIconColorAnimation.J();
    }

    public ItemFilterSelection getSelection() {
        return this.selection;
    }
}

