package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryFilterRuleRowBase;
import gg.vape.module.utility.inventory.cleaner.ui.SlotInventoryFilterRuleRowContent;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class SlotInventoryFilterRuleRow
extends InventoryFilterRuleRowBase {
    private final PaddedComponent paddedContent;
    private final SlotInventoryFilterRule rule;
    private final ColorAnimation deleteLineColor;
    private final SlotInventoryFilterRuleRowContent content;
    private static final String CLOSE_ICON = "newclose";
    private final InventoryCleanerProfile profile;
    @Nullable
    private Runnable removeCallback;
    private final ColorAnimation closeIconColor;

    @Override
    public void refresh() {
        this.content.refresh();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        Runnable runnable;
        if ((double)guiMouseEvent.getX() >= this.G$src$D$1b2f02a() + this.A() - 10.0 && (double)guiMouseEvent.getX() <= this.G$src$D$1b2f02a() + this.A() && (double)guiMouseEvent.getY() >= this.n() && (double)guiMouseEvent.getY() <= this.n() + 8.0) {
            this.rule.reset();
            this.refresh();
            return;
        }
        if (this.content.w$src$Z$e457mb() && (runnable = this.removeCallback) != null) {
            ClientSettings.UI_EXECUTOR.execute(() -> this.runRemoveCallback(runnable));
        }
    }

    @Override
    public double C() {
        return this.paddedContent.L();
    }


    @Override
    public void c() {
        this.paddedContent.K(this.G$src$D$1b2f02a());
        this.paddedContent.S(this.n());
        this.paddedContent.l$src$V$1mibm4x();
        super.c();
        if (!this.rule.getItemSelection().isEmpty()) {
            GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() - 10.0, this.n(), 10.0, 1.0, this.deleteLineColor.getInterpolatedColor());
            ImageRenderer.drawImage(this.closeIconColor.getInterpolatedColor(), (float)(this.G$src$D$1b2f02a() + this.A() - 11.0), (float)(this.n() - 1.0), CLOSE_ICON, 12.0f, 12.0f, false);
        }
    }

    @Override
    public double x() {
        return this.paddedContent.A();
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.deleteLineColor.J();
            this.closeIconColor.J();
        }
    }

    public void setRemoveCallback(@Nullable Runnable removeCallback) {
        this.removeCallback = removeCallback;
    }

    private void runRemoveCallback(Runnable runnable) {
        runnable.run();
        this.refresh();
    }

    public SlotInventoryFilterRuleRow(InventoryCleanerProfile inventoryCleanerProfile, SlotInventoryFilterRule slotInventoryFilterRule) {
        this.deleteLineColor = new ColorAnimation(0.15, new Color(0, 0, 0, 0), SlotInventoryFilterRuleRow.J.d);
        this.closeIconColor = new ColorAnimation(0.15, new Color(0, 0, 0, 0), Color.WHITE);
        this.profile = inventoryCleanerProfile;
        this.rule = slotInventoryFilterRule;
        this.content = new SlotInventoryFilterRuleRowContent(this, inventoryCleanerProfile, slotInventoryFilterRule);
        this.paddedContent = new PaddedComponent(2.0, this.content);
        this.refresh();
        this.setPropagateMouseEvents(true);
        this.addChildren(this.paddedContent);
    }

    @Nullable
    public Runnable getRemoveCallback() {
        return this.removeCallback;
    }

    @Override
    public void onEnable() {
        this.deleteLineColor.J();
        this.closeIconColor.J();
    }
}

