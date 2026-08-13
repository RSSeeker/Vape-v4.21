package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.ui.ItemFilterSelectionComponent;
import gg.vape.module.utility.inventory.cleaner.ui.MaterialFilterSelectionRow;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

class MaterialFilterSelectionRowContent
extends GuiComponent {
    private final ColorAnimation backgroundColorAnimation;
    private final ItemFilterSelectionComponent selectionComponent;
    final MaterialFilterSelectionRow row;

    @Override
    public void c() {
        this.selectionComponent.K(this.G$src$D$1b2f02a());
        this.selectionComponent.S(this.n());
        this.selectionComponent.setScale(0.5f);
        this.selectionComponent.setIconWidth(8.0f);
        this.selectionComponent.setIconHeight(8.0f);
        this.selectionComponent.o(this.A());
        this.selectionComponent.Y(this.L());
        super.c();
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundColorAnimation.getInterpolatedColor());
    }

    @Override
    public void F() {
        if (this.w$src$Z$e457mb()) {
            this.backgroundColorAnimation.J();
        }
    }

    @Override
    public double x() {
        return 14.0;
    }

    MaterialFilterSelectionRowContent(MaterialFilterSelectionRow materialFilterSelectionRow, ItemFilterSelection itemFilterSelection) {
        this.row = materialFilterSelectionRow;
        this.backgroundColorAnimation = new ColorAnimation(0.15, MaterialFilterSelectionRowContent.J.z, MaterialFilterSelectionRowContent.J.M);
        this.selectionComponent = new ItemFilterSelectionComponent(itemFilterSelection);
        this.setPropagateMouseEvents(true);
        this.addChildren(this.selectionComponent);
    }

    @Override
    public double C() {
        return 14.0;
    }

    @Override
    public void onEnable() {
        this.backgroundColorAnimation.J();
    }

}

