package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.ArmorStatusHudFrame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Slot;
import java.awt.Color;

public class ArmorStatusItemComponent
extends GuiComponent {
    private final int slotIndex;
    private boolean lowDurabilityTracked;
    private boolean broken;
    private boolean previewMode;
    private final TimerUtil brokenDisplayTimer = new TimerUtil();
    private boolean lastVisibleEntry;
    private final ArmorStatusHudFrame frame;
    private boolean compact;
    private final ItemStack previewItem;

    public void setPreviewMode(boolean previewMode) {
        this.previewMode = previewMode;
    }

    public Slot getSlot() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return new Slot(null);
        }
        Container inventoryContainer =
                player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        if (inventoryContainer.isNull()) {
            return new Slot(null);
        }
        return inventoryContainer.getSlot(this.slotIndex);
    }

    public void setLastVisibleEntry(boolean lastVisibleEntry) {
        this.lastVisibleEntry = lastVisibleEntry;
    }

    @Override
    public double x() {
        return 0.0;
    }

    private void renderCompact(ItemStack itemStack) {
        if (this.broken) {
            GuiRenderPrimitives.C((float)this.G$src$D$1b2f02a() - 2.0f, (float)this.n(), this.frame.A(), this.L(), this.frame.applyDefaultEditorAlpha(new Color(250, 50, 57, 120)));
            ImageRenderer.drawRes(this.frame.applyDefaultEditorAlpha(ArmorStatusItemComponent.J.d), (float)this.G$src$D$1b2f02a() + 4.0f, (float)this.n() + 3.0f, "armor", 0.45f);
            ImageRenderer.drawRes(this.frame.applyDefaultEditorAlpha(ArmorStatusItemComponent.J.d), (float)(this.G$src$D$1b2f02a() + 1.0), (float)this.n() + 15.0f, "bar-1", 0.15f);
            return;
        }
        if (itemStack.isNull()) {
            return;
        }
        int durabilityPercent = this.getDurabilityPercent();
        if (durabilityPercent == -1) {
            return;
        }
        float durabilityRatio = (float)durabilityPercent / 100.0f;
        if (durabilityRatio < 0.1f) {
            float warningStrength = (0.1f - durabilityRatio) / 0.1f;
            int warningAlpha = (int)(30.0f + 30.0f * warningStrength);
            Color color = this.frame.applyDefaultEditorAlpha(new Color(250, 50, 57, warningAlpha));
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a() - 2.0, this.n(),
                    this.frame.A(), this.L(), color);
        }
        ItemIconRenderer.renderItemStack(itemStack, (float)(this.G$src$D$1b2f02a() + 3.0),
                (float)this.n(), 16, 16, this.frame.getEditorOpacity());
        GuiRenderPrimitives.e(this.G$src$D$1b2f02a() + 1.0, this.n() + 17.0,
                20.0, 1.5, this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 153)), false, 1.0f, 1.0f);
        GuiRenderPrimitives.I(this.G$src$D$1b2f02a() + 1.0, this.n() + 17.0,
                20.0f * durabilityRatio, 1.5,
                this.frame.applyDefaultEditorAlpha(RenderUtils.q(durabilityRatio, true)), true, 1.0f, 1.0f,
                4.0f, this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 75)));
        if (!this.lastVisibleEntry) {
            Color color = ColorUtil.withAlpha(Color.WHITE, 51);
            GuiRenderPrimitives.a(this.G$src$D$1b2f02a() + 7.0, this.n() + 23.0, 8.0, 2.0f, color);
        }
    }

    public boolean isBroken() {
        return this.broken;
    }

    public void setCompact(boolean compact) {
        this.compact = compact;
    }

    @Override
    public double C() {
        return 0.0;
    }

    private int getDurabilityPercent() {
        ItemStack itemStack = this.getDisplayedItem();
        if (itemStack == null || itemStack.isNull()) {
            return -1;
        }
        Item item = itemStack.getItem();
        if (item.isNull()) {
            return -1;
        }
        float maximumDamage = ForgeVersion.MC_1_20_6.d()
                ? (float)itemStack.y() : (float)item.a();
        float currentDamage = itemStack.L();
        if (maximumDamage <= 0.0f) {
            return -1;
        }
        float remainingDurability = maximumDamage - currentDamage;
        if (remainingDurability == 0.0f) {
            return 1;
        }
        if (remainingDurability < 0.0f) {
            return 100;
        }
        return (int)Math.ceil(remainingDurability / maximumDamage * 100.0f);
    }

    public ItemStack getEquippedItem() {
        Slot slot = this.getSlot();
        if (slot.isNull()) {
            return new ItemStack(null);
        }
        return slot.getStack();
    }

    @Override
    public double L() {
        return this.frame.getItemHeight();
    }

    private void renderDetailed(ItemStack itemStack) {
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().W(0.85, true);
        if (this.broken) {
            GuiRenderPrimitives.C((float)this.G$src$D$1b2f02a() - 2.0f, (float)this.n(), this.frame.A(), this.L(), this.frame.applyDefaultEditorAlpha(new Color(250, 50, 57, 120)));
            ImageRenderer.drawRes(this.frame.applyDefaultEditorAlpha(ArmorStatusItemComponent.J.d), (float)this.G$src$D$1b2f02a() + 2.0f, (float)this.n() + 3.0f, "armor", 0.45f);
            ImageRenderer.drawRes(this.frame.applyDefaultEditorAlpha(ArmorStatusItemComponent.J.d), (float)this.G$src$D$1b2f02a() + 23.0f, (float)this.n() + 11.0f, "bar-1", 0.2f);
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 20.0, this.n() + 4.0, 12.0, 2.0f, this.frame.applyDefaultEditorAlpha(ArmorStatusItemComponent.J.d));
            smoothFontRenderer.d("0%", this.G$src$D$1b2f02a() + 26.0, this.n() + 5.0, this.frame.getEditorForegroundColor());
            return;
        }
        if (itemStack.isNull()) {
            return;
        }
        float durabilityRatio = (float)this.getDurabilityPercent() / 100.0f;
        if (durabilityRatio < 0.1f) {
            float warningStrength = (0.1f - durabilityRatio) / 0.1f;
            int warningAlpha = (int)(30.0f + 30.0f * warningStrength);
            Color warningColor = this.frame.applyDefaultEditorAlpha(new Color(250, 50, 57, warningAlpha));
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a() - 2.0, this.n(),
                    this.frame.A(), this.L(), warningColor);
        }
        Color color = ColorUtil.withAlpha(Color.WHITE, 51);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 20.0, this.n() + 4.0, 12.0, 2.0f, this.frame.applyDefaultEditorAlpha(color));
        ItemIconRenderer.renderItemStack(itemStack, (float)(this.G$src$D$1b2f02a() + 2.0), (float)(this.n() + 2.0), 16, 16, this.frame.getEditorOpacity(), 1.0f);
        int durabilityPercent = this.getDurabilityPercent();
        if (durabilityPercent == -1) {
            return;
        }
        String durabilityText = String.valueOf(durabilityPercent);
        smoothFontRenderer.d(durabilityText + "%", this.G$src$D$1b2f02a() + 26.0,
                this.n() + 5.0, this.frame.getEditorForegroundColor());
        GuiRenderPrimitives.e(this.G$src$D$1b2f02a() + 26.0, this.n() + 13.0, 20.0, 1.5, this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 153)), false, 1.0f, 1.0f);
        GuiRenderPrimitives.I(this.G$src$D$1b2f02a() + 26.0, this.n() + 13.0,
                20.0f * durabilityRatio, 1.5,
                this.frame.applyDefaultEditorAlpha(RenderUtils.q(durabilityRatio, true)), true,
                1.0f, 1.0f, 4.0f, this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 75)));
    }

    public ArmorStatusItemComponent(ArmorStatusHudFrame frame, int slotIndex, ItemStack previewItem) {
        this.frame = frame;
        this.slotIndex = slotIndex;
        this.previewItem = previewItem;
    }

    @Override
    public void I() {
        this.H();
    }

    @Override
    public void H() {
        ItemStack itemStack = this.getDisplayedItem();
        if (itemStack.isNotNull() || this.previewMode) {
            this.brokenDisplayTimer.reset();
            this.broken = false;
            this.lowDurabilityTracked = this.getDurabilityPercent() <= 2;
        } else if (this.lowDurabilityTracked) {
            this.broken = true;
            this.lowDurabilityTracked = false;
        }
        if (this.broken && this.brokenDisplayTimer.hasTimeElapsed(4000L)) {
            this.setVisible(false);
        }
        if (itemStack.isNull() && !this.broken) {
            this.setVisible(false);
        }
        if (this.compact) {
            this.renderCompact(itemStack);
        } else {
            try {
                this.renderDetailed(itemStack);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
    }

    @Override
    public double A() {
        return this.frame.A();
    }

    public ItemStack getDisplayedItem() {
        if (this.previewMode) {
            return this.previewItem;
        }
        return this.getEquippedItem();
    }
}
