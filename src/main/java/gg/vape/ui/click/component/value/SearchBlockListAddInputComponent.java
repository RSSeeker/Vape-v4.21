package gg.vape.ui.click.component.value;

import gg.vape.Vape;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.value.SearchBlockListDropdownLayer;
import gg.vape.ui.unmap.SearchBlock;
import gg.vape.unmap.ColorUtil;

public class SearchBlockListAddInputComponent
extends TextInputComponentBase {
    private Runnable afterAdd;
    private boolean resetFocusAnimation = false;

    @Override
    public void F() {
        if (this.resetFocusAnimation) {
            this.borderAnimation.J();
            this.resetFocusAnimation = false;
        }
        super.F();
    }

    public SearchBlockListAddInputComponent(String placeholder, Runnable afterAdd) {
        super(placeholder);
        this.setShowDisabledOverlay(false);
        this.afterAdd = afterAdd;
    }

    public SearchBlockListAddInputComponent(String placeholder) {
        super(placeholder);
        this.setShowDisabledOverlay(false);
    }

    @Override
    public double getComponentWidth() {
        if (this.afterAdd != null) {
            return this.A();
        }
        return 110.0;
    }

    @Override
    public double C() {
        return 20.0;
    }


    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void submit() {
        if (!this.hasNonBlankText()) {
            this.setText("");
            return;
        }
        SearchBlock searchBlock = new SearchBlock(this.getText(), ColorUtil.createRandomReadableColor().getRGB());
        Vape.INSTANCE.getSearch().addSearchBlock(searchBlock);
        if (this.afterAdd != null) {
            this.afterAdd.run();
        } else {
            ((SearchBlockListDropdownLayer)this.getParentFrameComponent()).refreshContents();
        }
        this.setText("");
    }
}
