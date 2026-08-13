package gg.vape.ui.click.component.value;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.value.AbstractListValueComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.EntityTargetFilterQuickToggleComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameStackManager;
import gg.vape.ui.click.frame.FrameValueDropdownLayer;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesSidecarPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.value.EntityTargetFilterValue;
import java.awt.Color;

public class EntityTargetFilterComponent
extends AbstractListValueComponent {
    private boolean legacyFlag;
    private FrameValueDropdownLayer dropdownLayer;
    private FrameStackManager frameStackManager;
    private EntityTargetFilterValue targetFilterValue;

    private static void hideSidecarDivider(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
        clickGuiSidecarPanelBase.setDividerVisible(false);
    }

    private void openEditorFromClick() {
        this.openEditor();
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (!expanded && this.dropdownLayer != null && this.frameStackManager != null) {
            this.frameStackManager.m(this.dropdownLayer);
        }
    }

    public EntityTargetFilterComponent(EntityTargetFilterValue targetFilterValue) {
        this.targetFilterValue = targetFilterValue;
        this.bindValue(targetFilterValue);
        this.addClickListener(this::openEditorFromClick);
    }

    private void populateOverlayContent(PanelComponent contentPanel) {
        contentPanel.removeMarkedChildren();
        contentPanel.h(new EntityTargetFilterQuickToggleComponent(this.targetFilterValue), new Object[0]);
        contentPanel.h(new BooleanToggleComponent(this.targetFilterValue.getIgnoreInvisibleValue()), new Object[0]);
        contentPanel.h(new BooleanToggleComponent(this.targetFilterValue.getIgnoreNakedValue()), new Object[0]);
        contentPanel.h(new BooleanToggleComponent(this.targetFilterValue.getIgnoreBehindWallsValue()), new Object[0]);
    }

    public EntityTargetFilterValue getTargetFilterValue() {
        return this.targetFilterValue;
    }


    @Override
    public void H() {
        this.onDisable();
        if (this.dropdownLayer != null) {
            this.dropdownLayer.updatePosition();
        }
        SmoothFontRenderer primaryFontRenderer = this.getFontRenderer(0.9);
        SmoothFontRenderer secondaryFontRenderer = this.getFontRenderer(0.75);
        Color backgroundColor = EntityTargetFilterComponent.J.i;
        Color primaryTextColor = this.isHovered() ? EntityTargetFilterComponent.J.A : (this.isExpanded() ? EntityTargetFilterComponent.J.A : EntityTargetFilterComponent.J.Z);
        double titleHeight = primaryFontRenderer.d("Targets");
        double primaryTextY = this.n() + this.L() / 2.0 - titleHeight / 2.0 - 2.5 - 2.0;
        double secondaryTextY = primaryTextY + 7.5 + 1.0;
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0, this.n() + 2.5, this.A() - 10.0, this.L() - 5.0, this.isExpanded() ? J.z() : this.getHoverAnimation().getInterpolatedColor());
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0 + 0.5, this.n() + 2.5 + 0.5, this.A() - 10.0 - 1.0, this.L() - 5.0 - 1.0, backgroundColor);
        double textX = this.G$src$D$1b2f02a() + 10.0 - 2.0;
        String targetSummary = "Target: ";
        if (this.targetFilterValue.getPlayersValue().getEffectiveValue().booleanValue()) {
            targetSummary = targetSummary + "Players, ";
        }
        if (this.targetFilterValue.getMobsValue().getEffectiveValue().booleanValue()) {
            targetSummary = targetSummary + "Mobs, ";
        }
        if (this.targetFilterValue.getPeacefulValue().getEffectiveValue().booleanValue()) {
            targetSummary = targetSummary + "Peaceful, ";
        }
        if (targetSummary.endsWith(", ")) {
            targetSummary = targetSummary.substring(0, targetSummary.length() - 2);
        }
        if (targetSummary.equals("Target: ")) {
            targetSummary = targetSummary + "Nothing";
            primaryFontRenderer.d(targetSummary, textX, primaryTextY + 1.0, EntityTargetFilterComponent.J.d);
        } else {
            primaryFontRenderer.d(targetSummary, textX, primaryTextY + 1.0, primaryTextColor);
        }
        StringBuilder ignoredTargets = new StringBuilder();
        if (this.targetFilterValue.getIgnoreInvisibleValue().getEffectiveValue().booleanValue()) {
            ignoredTargets.append("invisible, ");
        }
        if (this.targetFilterValue.getIgnoreNakedValue().getEffectiveValue().booleanValue()) {
            ignoredTargets.append("naked, ");
        }
        if (this.targetFilterValue.getIgnoreBehindWallsValue().getEffectiveValue().booleanValue()) {
            ignoredTargets.append("behind walls, ");
        }
        String ignoredTargetSummary = ignoredTargets.length() < 1 ? "none" : ignoredTargets.substring(0, ignoredTargets.length() - 2);
        secondaryFontRenderer.d("Ignore " + ignoredTargetSummary, this.G$src$D$1b2f02a() + 10.0 - 2.0, secondaryTextY, EntityTargetFilterComponent.J.Z);
    }

    private void openEditor() {
        Frame frame;
        if (this.getParentFrameComponent() != null && (frame = this.getParentFrameComponent().L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa()) instanceof ClickGuiMainFrame) {
            ClickGuiMainFrame clickGuiMainFrame = (ClickGuiMainFrame)frame;
            ClickGuiModulesSidecarPanel clickGuiModulesSidecarPanel = new ClickGuiModulesSidecarPanel(null);
            clickGuiModulesSidecarPanel.setFavoriteVisible(false);
            clickGuiModulesSidecarPanel.setToggleVisible(false);
            ClickGuiOverlaySpec clickGuiOverlaySpec = ClickGuiOverlaySpec.builder().title("Target settings").sidecarIcon("newaim").sidecar(clickGuiModulesSidecarPanel).placement(ClickGuiOverlayPlacement.DOCKED_SHIFT).transitionMode(ClickGuiOverlayTransitionMode.PUSH).initializeSidecar(EntityTargetFilterComponent::hideSidecarDivider).initializeContent(this::populateOverlayContent).build();
            clickGuiMainFrame.showOverlay(clickGuiOverlaySpec);
            return;
        }
        this.setExpanded(!this.isExpanded());
        if (this.isExpanded()) {
            if (this.dropdownLayer == null) {
                this.dropdownLayer = new FrameValueDropdownLayer(this);
            }
            this.frameStackManager = ClientSettings.INSTANCE.getActiveStack();
            this.frameStackManager.q(this.dropdownLayer);
        }
    }
}

