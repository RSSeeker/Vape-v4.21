package gg.vape.ui.click.frame.impl.target;

import gg.vape.event.EventBus;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.event.impl.EventLivingUpdate;
import gg.vape.event.impl.EventPlayerUseItem;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoCombatStatStripComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoDistanceStatStripComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoEntityPreviewComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoHealthBarComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoLiveEntityPreviewComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoPositiveStatStripComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoPreviewHealthBarComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoResettingCombatStatStripComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoSettingsFrame;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.StringUtils;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import java.awt.Color;
import java.util.function.Predicate;

public class TargetInfoPreviewComponent
extends PaddedComponent
implements EventListener {
    private final BlurRegionRenderer backgroundBlur;
    private int pendingLocalHealingPotions;
    private final TargetInfoResettingCombatStatStripComponent comboStrip;
    private final SpacerComponent nameSpacer;
    private final FlowLayoutComponent healthRow;
    private int lastTargetEntityId = -1;
    private final PanelComponent entityHeaderPanel;
    private final TimerUtil targetTimeoutTimer = new TimerUtil();
    private final PanelComponent statsPanel;
    private final FlowLayoutComponent nameRow;
    private EntityLivingBase target;
    private final TargetInfoCombatStatStripComponent potsUsedStrip;
    private final TargetInfoDistanceStatStripComponent damageStrip;
    private final FlowLayoutComponent rootLayout;
    private final TargetInfoSettingsFrame settingsFrame;
    private final TargetInfoEntityPreviewComponent entityPreview;
    private final FlowLayoutComponent statsRow;
    private final TargetInfoPositiveStatStripComponent hitsStrip;
    private final TargetInfoHealthBarComponent healthBar;

    @EventHandler
    public void onUpdate(EventLivingUpdate eventLivingUpdate) {
        if (!this.isActive()) {
            return;
        }
        if (this.getTarget() == null || this.getTarget().isNull() || Minecraft.thePlayer().isNull()) {
            return;
        }
        if (Minecraft.thePlayer().getDistanceToEntity(this.getTarget()) > 6.0f) {
            return;
        }
        if (eventLivingUpdate.getEntity().getObject().equals(Minecraft.thePlayer().getObject())) {
            this.hitsStrip.decrement();
            this.comboStrip.decrementCombo();
        }
        if (eventLivingUpdate.getEntity().getObject().equals(this.getTarget().getObject())) {
            this.hitsStrip.increment();
            this.comboStrip.incrementCombo();
        }
    }

    public boolean isPreviewMode() {
        return !ClientSettings.INSTANCE.inputEnabled;
    }

    public EntityLivingBase getTarget() {
        return this.isPreviewMode() ? Minecraft.thePlayer() : this.target;
    }

    public TargetInfoPreviewComponent(TargetInfoSettingsFrame targetInfoSettingsFrame) {
        super(10.0, new FlowLayoutComponent(100.0));
        this.entityHeaderPanel = new PanelComponent(100.0, 10.0);
        this.healthRow = new FlowLayoutComponent(100.0);
        this.nameRow = new FlowLayoutComponent(90.0);
        this.nameSpacer = new SpacerComponent(12.0, 12.0);
        this.entityPreview = new TargetInfoLiveEntityPreviewComponent(this, 12.0, 12.0);
        this.healthBar = new TargetInfoPreviewHealthBarComponent(this, 100, 4);
        this.backgroundBlur = new BlurRegionRenderer(0, 0);
        this.statsPanel = new PanelComponent(100.0, 14.0);
        this.statsRow = new FlowLayoutComponent(100.0);
        this.damageStrip = new TargetInfoDistanceStatStripComponent();
        this.hitsStrip = new TargetInfoPositiveStatStripComponent();
        this.potsUsedStrip = new TargetInfoCombatStatStripComponent();
        this.comboStrip = new TargetInfoResettingCombatStatStripComponent();
        this.settingsFrame = targetInfoSettingsFrame;
        this.rootLayout = (FlowLayoutComponent)super.H$src$Lgg_vape_ui_click_component_GuiComponent_$kfnvup();
        this.rootLayout.h(new SpacerComponent(1.0, 2.0), new Object[0]);
        this.statsPanel.h(this.statsRow, "wrap, alignright");
        this.rootLayout.h(this.statsPanel, new Object[0]);
        this.nameRow.h(this.nameSpacer, new Object[0]);
        this.entityPreview.setFrame(targetInfoSettingsFrame);
        this.entityHeaderPanel.h(this.entityPreview, new Object[0]);
        this.entityHeaderPanel.h(this.nameRow, new Object[0]);
        this.rootLayout.addChildren(this.entityHeaderPanel);
        this.rootLayout.h(new SpacerComponent(100.0, 8.0), new Object[0]);
        this.healthBar.setFrame(targetInfoSettingsFrame);
        this.healthRow.h(this.healthBar, new Object[0]);
        this.rootLayout.addChildren(this.healthRow);
        this.rootLayout.setShowDisabledOverlay(false);
        this.entityHeaderPanel.setShowDisabledOverlay(false);
        this.nameRow.setShowDisabledOverlay(false);
        this.healthRow.setShowDisabledOverlay(false);
        this.statsPanel.setShowDisabledOverlay(false);
        this.nameRow.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.entityHeaderPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.statsRow.setShowDisabledOverlay(false);
        this.hitsStrip.setFrame(targetInfoSettingsFrame);
        this.potsUsedStrip.setFrame(targetInfoSettingsFrame);
        this.comboStrip.setFrame(targetInfoSettingsFrame);
        this.damageStrip.setFrame(targetInfoSettingsFrame);
        this.statsRow.h(this.hitsStrip, new Object[0]);
        this.statsRow.h(this.potsUsedStrip, new Object[0]);
        this.statsRow.h(this.comboStrip, new Object[0]);
        this.statsRow.h(this.damageStrip, new Object[0]);
        EventBus.getInstance().registerListener(this, new Predicate[0]);
    }

    private void updateStatVisibility() {
        this.hitsStrip.setVisible(this.settingsFrame.getSettings().hitsComparator.getEffectiveValue());
        this.potsUsedStrip.setVisible(this.settingsFrame.getSettings().potsUsedComparator.getEffectiveValue());
        this.comboStrip.setVisible(this.settingsFrame.getSettings().comboCounter.getEffectiveValue());
        this.damageStrip.setVisible(this.settingsFrame.getSettings().damageComparator.getEffectiveValue());
        boolean bl = false;
        for (GuiComponent guiComponent : this.statsRow.f()) {
            if (!guiComponent.V$src$Z$1xhop3l()) continue;
            bl = true;
            break;
        }
        this.statsPanel.setVisible(bl);
    }

    private double getContentY() {
        return this.n() + 2.0;
    }

    public void setTarget(EntityLivingBase target) {
        this.target = target;
    }

    @Override
    public void u() {
        Entity entity;
        RayTraceResult rayTraceResult;
        if (!this.isActive()) {
            return;
        }
        this.updateDamageComparison();
        this.updateStatVisibility();
        if (this.targetTimeoutTimer.hasTimeElapsed(1000L) && this.getTarget() != null && !this.getTarget().isNull() && this.getTarget().w$src$F$15l9epb() <= 0.0f) {
            this.showTarget(null);
        }
        if (this.targetTimeoutTimer.hasTimeElapsed(3000L)) {
            this.showTarget(null);
        }
        if (this.settingsFrame.getSettings().showHovered.getEffectiveValue().booleanValue() && (rayTraceResult = Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0()).isNotNull() && (entity = rayTraceResult.getEntity()).isNotNull() && entity.isInstance(MappedClasses.zm) && !entity.isInstance(MappedClasses.FT)) {
            this.showTarget(new EntityLivingBase(entity));
        }
    }

    private void showTarget(EntityLivingBase entityLivingBase) {
        if (entityLivingBase == null) {
            this.setTarget(null);
            return;
        }
        this.rootLayout.setVisible(true);
        this.targetTimeoutTimer.reset();
        if (this.getTarget() != null && this.getTarget().equals(entityLivingBase)) {
            return;
        }
        int n = entityLivingBase.S();
        this.setTarget(entityLivingBase);
        this.healthBar.setEntity(entityLivingBase);
        if (n != this.lastTargetEntityId) {
            this.hitsStrip.setComparisonValue(0);
            this.potsUsedStrip.setComparisonValue(0);
            this.comboStrip.setCombo(0);
        }
        this.lastTargetEntityId = n;
        this.H(true);
    }

    @EventHandler
    public void onPostAttack(EventPostAttack eventPostAttack) {
        if (!this.isActive()) {
            this.showTarget(null);
            return;
        }
        Entity entity = eventPostAttack.getTarget();
        if (entity.isInstance(MappedClasses.zm) && !entity.isInstance(MappedClasses.FT)) {
            this.showTarget(new EntityLivingBase(entity));
        }
    }

    public String getTargetName() {
        if (this.getTarget() == null) {
            return "";
        }
        return this.getTarget().getName();
    }

    private boolean isActive() {
        return this.settingsFrame.y$src$Z$1f55jvh() && Minecraft.theWorld().isNotNull();
    }

    private void updateDamageComparison() {
        if (this.getTarget() == null) {
            return;
        }
        this.damageStrip.setComparisonValue((int)RotationUtil.y(Minecraft.thePlayer(), this.getTarget()));
    }

    @Override
    public void H() {
        this.renderPreview();
    }

    private void renderPreview() {
        this.rootLayout.setVisible(this.getTarget() != null || this.isPreviewMode());
        if (!this.rootLayout.V$src$Z$1xhop3l()) {
            return;
        }
        this.entityPreview.setVisible(this.getTarget().isInstance(MappedClasses.Yl));
        if (this.statsPanel.V$src$Z$1xhop3l()) {
            this.N(5.0);
        } else {
            this.N(10.0);
        }
        this.H(true);
        float f = this.settingsFrame.getEditorOpacity();
        this.backgroundBlur.setDimensions((int)this.A() * 2, (int)this.L() * 2);
        if (f >= 1.0f) {
        this.backgroundBlur.renderBlur((int)this.G$src$D$1b2f02a(), (int)this.getContentY(), 20.0f, 3.0f);
        }
        GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.getContentY(), this.A(), this.L(), this.settingsFrame.applyDefaultEditorAlpha(new Color(18, 18, 18, 173)), false, 3.0f, 1.0f);
        String string = this.getTargetName();
        String string2 = StringUtils.l(string);
        if (string2.isEmpty()) {
            string = "\u00a77(Empty Name)";
        }
        TruncatedTextComponent truncatedTextComponent = new TruncatedTextComponent(string, "...", 80.0, 1.3, this.settingsFrame.getEditorForegroundColor(), false);
        double d = this.entityPreview.V$src$Z$1xhop3l() ? this.entityPreview.n() + this.entityPreview.L() / 2.0 - truncatedTextComponent.getTextHeight() / 2.0 : this.entityHeaderPanel.n() + this.entityHeaderPanel.L() / 2.0 - truncatedTextComponent.getTextHeight() / 2.0;
        truncatedTextComponent.renderAt(this.nameSpacer.G$src$D$1b2f02a() + (double)(this.entityPreview.V$src$Z$1xhop3l() ? 5 : 2), d);
    }

    @EventHandler
    public void onPlayerUseItem(EventPlayerUseItem eventPlayerUseItem) {
        if (!this.isActive()) {
            return;
        }
        if (this.getTarget() == null || this.getTarget().isNull()) {
            return;
        }
        ItemStack itemStack = eventPlayerUseItem.getItemStack();
        if (itemStack.isNotNull() && MappedClasses.Di.isInstance(itemStack.getItem().getObject()) && ItemStackScoreUtil.i(itemStack)) {
            ++this.pendingLocalHealingPotions;
        }
    }

    @EventHandler
    public void onEntityJoinWorld(EventEntityJoinWorld eventEntityJoinWorld) {
        if (!this.isActive()) {
            return;
        }
        if (this.getTarget() == null || this.getTarget().isNull() || Minecraft.thePlayer().isNull()) {
            return;
        }
        if (!eventEntityJoinWorld.getEntity().isInstance(MappedClasses.Zf)) {
            return;
        }
        EntityPotion entityPotion = new EntityPotion(eventEntityJoinWorld.getEntity());
        if (entityPotion.getPotion().isNull() || !ItemStackScoreUtil.i(entityPotion.getPotion())) {
            return;
        }
        if (this.pendingLocalHealingPotions > 0) {
            this.potsUsedStrip.decrement();
            --this.pendingLocalHealingPotions;
        } else {
            this.potsUsedStrip.increment();
        }
    }

    @Override
    public void I() {
        this.c();
    }
}
