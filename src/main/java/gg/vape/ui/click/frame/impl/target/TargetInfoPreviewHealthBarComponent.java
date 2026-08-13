package gg.vape.ui.click.frame.impl.target;

import gg.vape.ui.click.frame.impl.target.TargetInfoHealthBarComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoPreviewComponent;

public class TargetInfoPreviewHealthBarComponent
extends TargetInfoHealthBarComponent {
    private final TargetInfoPreviewComponent preview;

    public TargetInfoPreviewHealthBarComponent(TargetInfoPreviewComponent targetInfoPreviewComponent, int n, int n2) {
        super(n, n2);
        this.preview = targetInfoPreviewComponent;
    }

    @Override
    public double getHealthFraction() {
        return this.preview.isPreviewMode() ? 0.6 : super.getHealthFraction();
    }

}
