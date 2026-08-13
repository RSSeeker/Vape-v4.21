package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.value.ListValueComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.value.ListValue;
import java.awt.Color;

public class CompactListValueComponent
extends ListValueComponent {
    private final boolean blockedList;
    private final ListValue listValue;

    @Override
    public double x() {
        return 10.0;
    }

    public CompactListValueComponent(ListValue listValue) {
        super(listValue);
        this.w("Open " + listValue.getName());
        this.listValue = listValue;
        this.blockedList = listValue.getName().contains("blacklist") || listValue.getName().contains("blocked");
    }

    @Override
    public void H() {
    }

    @Override
    public void c() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.isExpanded() ? CompactListValueComponent.J.m : CompactListValueComponent.J.i);
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.isExpanded() ? CompactListValueComponent.J.y : CompactListValueComponent.J.l, 1.0f, 0.75f, 1.0f);
        super.c();
        Color iconColor = this.isHovered() ? CompactListValueComponent.J.A : (this.isExpanded() ? CompactListValueComponent.J.A : CompactListValueComponent.J.Z);
        float iconY = (float)(this.n() + this.L() / 2.0) - 2.0f;
        float iconX = (float)this.G$src$D$1b2f02a() + 2.5f;
        if (this.blockedList) {
            ImageRenderer.drawImage(iconColor, iconX, iconY, "newblockedlist", 5.0f, 5.0f, false);
            ImageRenderer.drawImage(CompactListValueComponent.J.d, iconX, iconY, "newblocked", 5.0f, 5.0f, false);
        } else {
            ImageRenderer.drawImage(iconColor, iconX, iconY, "newallowedlist", 5.0f, 5.0f, false);
            ImageRenderer.drawImage(CompactListValueComponent.J.B, iconX, iconY, "newallowed", 5.0f, 5.0f, false);
        }
    }


    @Override
    public double C() {
        return 10.0;
    }

    @Override
    public void I() {
    }
}

