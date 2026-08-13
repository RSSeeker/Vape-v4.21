package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.ItemInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryFilterRuleRowBase;
import gg.vape.module.utility.inventory.cleaner.ui.ItemInventoryFilterRuleRowContent;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ItemInventoryFilterRuleRow
extends InventoryFilterRuleRowBase {
    @Nullable
    private Runnable onDelete;
    private final ItemInventoryFilterRule rule;
    private static final String CLOSE_ICON = "newclose";
    private final InventoryCleanerProfile profile;
    private final PaddedComponent paddedContent;
    @Nullable
    private Runnable onSelect;
    private final ItemInventoryFilterRuleRowContent content;
    private final ColorAnimation lineAnimation;
    private final ColorAnimation iconAnimation;

    public ItemInventoryFilterRule getRule() {
        return this.rule;
    }

    @Override
    public double C() {
        return this.paddedContent.L();
    }

    public ItemInventoryFilterRuleRow(InventoryCleanerProfile inventoryCleanerProfile, ItemInventoryFilterRule itemInventoryFilterRule) {
        this.lineAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), ItemInventoryFilterRuleRow.J.d);
        this.iconAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), Color.WHITE);
        this.profile = inventoryCleanerProfile;
        this.rule = itemInventoryFilterRule;
        this.setPropagateMouseEvents(true);
        this.content = new ItemInventoryFilterRuleRowContent(this, inventoryCleanerProfile, itemInventoryFilterRule);
        this.paddedContent = new PaddedComponent(2.0, this.content);
        this.addChildren(this.paddedContent);
        this.refresh();
    }

    @Override
    public double x() {
        return this.paddedContent.A();
    }

    @Override
    public void c() {
        this.paddedContent.K(this.G$src$D$1b2f02a());
        this.paddedContent.S(this.n());
        this.paddedContent.l$src$V$1mibm4x();
        super.c();
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() - 10.0, this.n(), 10.0, 1.0, this.lineAnimation.getInterpolatedColor());
        ImageRenderer.drawImage(this.iconAnimation.getInterpolatedColor(), (float)(this.G$src$D$1b2f02a() + this.A() - 11.0), (float)(this.n() - 1.0), CLOSE_ICON, 12.0f, 12.0f, false);
    }

    @Override
    public void refresh() {
        this.content.refresh();
    }

    public InventoryCleanerProfile getProfile() {
        return this.profile;
    }


    public void setOnSelect(@Nullable Runnable onSelect) {
        this.onSelect = onSelect;
    }

    @Nullable
    public Runnable getOnDelete() {
        return this.onDelete;
    }

    public void setOnDelete(@Nullable Runnable onDelete) {
        this.onDelete = onDelete;
    }

    @Nullable
    public Runnable getOnSelect() {
        return this.onSelect;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if ((double)guiMouseEvent.getX() >= this.G$src$D$1b2f02a() + this.A() - 10.0 && (double)guiMouseEvent.getX() <= this.G$src$D$1b2f02a() + this.A() && (double)guiMouseEvent.getY() >= this.n() && (double)guiMouseEvent.getY() <= this.n() + 8.0) {
            Runnable runnable = this.onDelete;
            if (runnable != null) {
                runnable.run();
            }
            return;
        }
        if (this.content.w$src$Z$e457mb()) {
            Runnable runnable = this.onSelect;
            if (runnable != null) {
                ClientSettings.UI_EXECUTOR.execute(runnable);
            }
            this.refresh();
        }
    }

    @Override
    public void onEnable() {
        this.lineAnimation.J();
        this.iconAnimation.J();
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.lineAnimation.J();
            this.iconAnimation.J();
        }
    }
}

