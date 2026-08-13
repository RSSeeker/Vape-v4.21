package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.impl.hud.AnchoredHudModuleConfigCloseHeaderButton;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameOpenConfigClickHandler;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrameHeaderActionComponent;
import gg.vape.wrapper.impl.Minecraft;

public class AnchoredHudModuleConfigFrame<T extends InteractiveComponent>
extends Frame {
    private final T anchorComponent;
    private double previousAnchorY;
    private final SimpleTextLabelComponent noSettingsLabel;
    private String title = "OverlaySettingsFrame";
    private HudModule hudModule;
    private double previousAnchorX;

    public HudModule getHudModule() {
        return this.hudModule;
    }

    public void repositionToAnchor() {
        GuiComponent anchor = (GuiComponent)this.anchorComponent;
        if (anchor.G$src$D$1b2f02a() != this.previousAnchorX || anchor.n() != this.previousAnchorY) {
            double anchoredY = anchor.n();
            FrameComponent frameComponent = anchor.getParentFrameComponent();
            if (frameComponent.k$src$Z$if6xeb()) {
                anchoredY = Math.min(anchoredY, frameComponent.n() + frameComponent.d$src$D$ibccpu() - anchor.L());
                anchoredY = Math.max(anchoredY, frameComponent.n() + frameComponent.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L());
            }
            if (anchor.G$src$D$1b2f02a() + anchor.A() + this.A() > (double)Minecraft.G().getScaledWidth()) {
                this.M(anchor.G$src$D$1b2f02a() - this.A() + 13.0, anchoredY);
            } else {
                this.M(anchor.G$src$D$1b2f02a() + anchor.A() - 13.0, anchoredY);
            }
            this.previousAnchorX = anchor.G$src$D$1b2f02a();
            this.previousAnchorY = anchor.n();
        }
    }

    @Override
    public void v() {
    }

    @Override
    public void Y() {
        PublicProfilesFrameHeaderActionComponent publicProfilesFrameHeaderActionComponent = (PublicProfilesFrameHeaderActionComponent)this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc();
        if (!publicProfilesFrameHeaderActionComponent.K$src$Ljava_lang_String_$bvh3j6().equalsIgnoreCase(this.getName())) {
            publicProfilesFrameHeaderActionComponent.j(this.getName());
        }
        boolean bl = false;
        for (GuiComponent guiComponent : this.f()) {
            if (guiComponent == this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() || guiComponent == this.noSettingsLabel) continue;
            bl = true;
            break;
        }
        this.noSettingsLabel.setVisible(!bl);
        this.l$src$V$1mibm4x();
    }

    @Override
    public String getName() {
        return this.title;
    }

    public T getAnchorComponent() {
        return this.anchorComponent;
    }

    public AnchoredHudModuleConfigFrame(T t) {
        this.setDisabledOverlayColor(AnchoredHudModuleConfigFrame.J.i);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.anchorComponent = t;
        this.Y(false);
        this.N(true);
        this.setVisible(false);
        this.L(false, false);
        AnchoredHudModuleConfigCloseHeaderButton anchoredHudModuleConfigCloseHeaderButton = new AnchoredHudModuleConfigCloseHeaderButton(this, "settingdots", this.title, 0.7);
        anchoredHudModuleConfigCloseHeaderButton.O$src$Lgg_vape_ui_click_component_SquareIconButtonComp$z3cp96().getClickListeners().clear();
        anchoredHudModuleConfigCloseHeaderButton.O$src$Lgg_vape_ui_click_component_SquareIconButtonComp$z3cp96().addClickListener(new HudModuleFrameOpenConfigClickHandler(this));
        this.Y(anchoredHudModuleConfigCloseHeaderButton);
        this.noSettingsLabel = new SimpleTextLabelComponent("No settings available", 0.75, AnchoredHudModuleConfigFrame.J.h);
        this.noSettingsLabel.setVisible(false);
        this.noSettingsLabel.setRemovable(false);
        this.h(this.noSettingsLabel, new Object[0]);
    }

    public void setHudModule(HudModule hudModule) {
        this.hudModule = hudModule;
        this.setTitle(hudModule.getName());
    }

    @Override
    public double L() {
        double d = 0.0;
        for (GuiComponent guiComponent : this.f()) {
            if (!guiComponent.V$src$Z$1xhop3l()) continue;
            d += guiComponent.L();
        }
        return d;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

