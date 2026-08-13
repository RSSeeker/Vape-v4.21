package gg.vape.ui.click.frame.impl;

import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;

public class ModuleCategoryFrameHeader
extends FrameHeaderComponent {
    private TextLabel K = new TextLabel("", 0.75);
    private int ZG = 0;
    private IconButtonComponent Q;
    private TextLabel G;
    private String R;
    private String O;
    private IconButtonComponent v;
    private IconButtonComponent o;
    private TextLabel I = new TextLabel("", 0.75);
    private ModuleCategoryFrame i;

    @Override
    public void I() {
    }

    @Override
    public void F() {
    }

    public void y(int n) {
        this.ZG = n;
    }

    public static ModuleCategoryFrame e(ModuleCategoryFrameHeader moduleCategoryFrameHeader) {
        return moduleCategoryFrameHeader.i;
    }

    public void h() {
        if (ClientSettings.moduleSearchActive) {
            ClientSettings.moduleSearchActive = false;
            ClientSettings.refreshModuleCategoryHeaders();
        }
        if (this.i.N$src$Lgg_vape_module_Mod_$1rbaf6a() != null) {
            this.i.G(null);
        }
        ClientSettings.refreshCategoryHeader(this.i.G$src$Lgg_vape_module_Category_$qyt4o7());
        for (GuiComponent guiComponent : this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().f()) {
            if (guiComponent instanceof FrameHeaderComponent) continue;
            if (!(guiComponent instanceof ModuleComponent)) {
                guiComponent.setVisible(false);
                continue;
            }
            guiComponent.setVisible(!this.i.q() && ((ModuleComponent)guiComponent).getModule().isVisible());
        }
        this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().l$src$V$1mibm4x();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }


    public ModuleCategoryFrameHeader(ModuleCategoryFrame moduleCategoryFrame, String string, String string2) {
        super(moduleCategoryFrame);
        this.G = new TextLabel("Edit");
        this.o = new IconButtonComponent("newhide", 0.7);
        this.v = new IconButtonComponent("moduleback");
        this.Q = new IconButtonComponent("upcollapse", 0.3);
        this.i = moduleCategoryFrame;
        this.O = string;
        this.R = string2;
        this.o.addClickListener(new ModuleCategoryFrameHeaderSearchIconClickHandler(this));
        this.o.w("Edit hidden modules");
        this.v.addClickListener(new ModuleCategoryFrameHeaderClearModuleSelectionClickHandler(this));
        this.G.addClickListener(new ModuleCategoryFrameHeaderSearchLabelClickHandler(this));
        this.Q.addClickListener(new ModuleCategoryFrameHeaderCollapseToggleClickHandler(this, moduleCategoryFrame));
        this.addChildren(this.G, this.v, this.o, this.Q, this.I, this.K);
    }

    @Override
    public void u() {
        if (this.i.N$src$Lgg_vape_module_Mod_$1rbaf6a() != null && ClientSettings.moduleSearchActive) {
            this.i.G(null);
        }
    }

    public void s(Mod mod) {
        if (mod == null) {
            this.K.setLabelText("");
            return;
        }
        this.K.setLabelText("< " + mod.getName().toUpperCase().substring(0, 3));
        this.K.setClickListener(new ModuleCategoryFrameHeaderActionClickHandler(this, mod));
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        double d = smoothFontRenderer.d(this.R);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0 + 1.0;
        float f = (float)ImageRenderer.getImageWidth(this.O) / 3.5f;
        float f2 = (float)ImageRenderer.getImageHeight(this.O) / 3.5f;
        double d3 = (float)this.G$src$D$1b2f02a() + 6.0f;
        double d4 = this.n() + this.L() / 2.0 - (double)(f2 / 2.0f) + 1.0;
        smoothFontRenderer.d(this.R, d3 + (double)f + 4.0, d2, ModuleCategoryFrameHeader.J.A);
        if (this.i.N$src$Lgg_vape_module_Mod_$1rbaf6a() != null && !ClientSettings.moduleSearchActive) {
            this.o.setVisible(false);
            this.G.setVisible(false);
            this.v.setOverrideColor(ModuleCategoryFrameHeader.J.A);
            this.v.setVisible(true);
            this.v.K(this.G$src$D$1b2f02a() + 5.0);
            this.v.S(this.n());
            this.v.Y(this.L());
            this.Q.setVisible(false);
            this.K.setVisible(true);
            this.I.setVisible(true);
            this.I.K(this.G$src$D$1b2f02a() + this.A() - 5.0 - this.I.A());
            this.I.S(this.n());
            this.I.Y(this.L());
            this.K.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - this.I.A() - this.K.A());
            this.K.S(this.n());
            this.K.Y(this.L());
        } else {
            this.K.setVisible(false);
            this.I.setVisible(false);
            this.Q.setVisible(true);
            ImageRenderer.drawImage(ModuleCategoryFrameHeader.J.A, (float)d3, (float)d4, this.O, f, f2, false);
            this.v.setVisible(false);
            if (ClientSettings.moduleSearchActive) {
                this.o.setVisible(false);
                this.G.setFontScale(0.75);
                this.G.setVisible(true);
                this.G.setLabelText("Done");
                this.G.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - 16.0 - smoothFontRenderer.N(this.G.getText()) / 2.0);
                this.G.S(this.n());
                this.G.Y(this.L());
            } else {
                boolean bl = this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().getBounds().Z(RenderUtils.h());
                if (((ModuleCategoryFrameHeader)this.getParentFrameComponent().j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc()).g$src$I$pd0er5() > 0) {
                    double d5 = smoothFontRenderer.N("" + this.ZG);
                    if (bl) {
                        smoothFontRenderer.d("" + this.ZG, this.G$src$D$1b2f02a() + this.A() - 5.0 - 16.0 - 3.0 - d5, d2, ModuleCategoryFrameHeader.J.Z);
                    }
                    this.o.setIconResource("newhide");
                } else {
                    this.o.setIconResource("newedit");
                }
                this.o.setVisible(bl);
                this.G.setVisible(false);
                this.o.setOverrideColor(ClientSettings.moduleSearchActive ? ModuleCategoryFrameHeader.J.f : null);
                this.o.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - 16.0);
                this.o.S(this.n() + 1.0);
                this.o.Y(this.L());
            }
            this.Q.K(this.G$src$D$1b2f02a() + this.A() - 7.5 - 8.0);
            this.Q.S(this.n());
            this.Q.Y(this.L());
            this.Q.setIconResource(this.i.q() ? "downexpand" : "upcollapse");
        }
    }

    public void S(Mod mod) {
        if (mod == null) {
            this.I.setLabelText("");
            return;
        }
        this.I.setLabelText(mod.getName().toUpperCase().substring(0, 3) + " >");
        this.I.setClickListener(new ModuleCategoryFrameHeaderNextModuleClickHandler(this, mod));
    }

    public int g$src$I$pd0er5() {
        return this.ZG;
    }
}

