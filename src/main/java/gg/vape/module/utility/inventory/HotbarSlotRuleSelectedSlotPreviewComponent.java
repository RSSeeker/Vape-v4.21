package gg.vape.module.utility.inventory;

import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.ItemStack;

public class HotbarSlotRuleSelectedSlotPreviewComponent
extends GuiComponent {
    private static final String SLOT_PREFIX = "SLOT ";
    private HotbarSlotRuleItemPickerFrame pickerFrame;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void u() {
    }

    @Override
    public double L() {
        return super.L();
    }

    @Override
    public void I() {
    }

    @Override
    public void F() {
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.8);
        double d = 55.0;
        double d2 = (this.A() - d) / 2.0;
        ItemStack itemStack = this.pickerFrame.getGroupComponent().getRules().get(this.pickerFrame.getSelectedSlot()).createItemStack();
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + d2 - 0.5, this.n() + 20.0 - 5.0, d + 1.0, d + 1.0, HotbarSlotRuleSelectedSlotPreviewComponent.J.l);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + d2, this.n() + 20.0 - 5.0 + 0.5, d, d, HotbarSlotRuleSelectedSlotPreviewComponent.J.r);
        if (itemStack != null && itemStack.isNotNull()) {
            float f = (float)(this.G$src$D$1b2f02a() + d / 2.0 - 10.0);
            float f2 = (float)(this.n() + d / 2.0);
            ItemIconRenderer.renderItemStack(itemStack, f, f2, 32, 32, 1.0f);
        }
        String string = SLOT_PREFIX + (this.pickerFrame.getSelectedSlot() + 1);
        smoothFontRenderer.W(string, this.G$src$D$1b2f02a() + 5.0 + d / 2.0, this.n() + 20.0 + d + smoothFontRenderer.d(string), HotbarSlotRuleSelectedSlotPreviewComponent.J.Z);
    }

    @Override
    public double A() {
        return super.A();
    }

    @Override
    public double C() {
        return 70.0;
    }


    public HotbarSlotRuleSelectedSlotPreviewComponent(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
    }

    @Override
    public double x() {
        return 70.0;
    }
}

