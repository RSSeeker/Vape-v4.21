package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import org.jetbrains.annotations.Nullable;

public class PopupFrame
extends Frame {
    private boolean FA;
    private static int[] Fq;
    private Frame FV;
    private Frame Fm;
    private GuiComponent FM;
    private GuiComponent FI;

    public static void W(int[] nArray) {
        Fq = nArray;
    }

    public GuiComponent V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc() {
        return this.FM;
    }

    @Override
    public void A$src$V$4ceaf0() {
        this.l$src$V$1mibm4x();
        ClientSettings.INSTANCE.replaceFrame(this.FV, this);
        boolean bl = false;
        if (this.FV instanceof CollapsibleFrame) {
            bl = ((CollapsibleFrame)((Object)this.FV)).q();
        }
        this.setVisible(this.FV.V$src$Z$1xhop3l() && !bl && this.FM.V$src$Z$1xhop3l());
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
    }

    @Nullable
    public <T extends GuiComponent> T k(Class<T> clazz) {
        return (T)(clazz.isInstance(this.FI) ? (GuiComponent)clazz.cast(this.FI) : null);
    }

    @Override
    public String getName() {
        return null;
    }

    public PopupFrame(GuiComponent guiComponent, GuiComponent guiComponent2) {
        this.FM = guiComponent;
        this.Fm = this.FV = guiComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa();
        while (this.Fm instanceof PopupFrame) {
            this.Fm = ((PopupFrame)this.Fm).Q$src$Lgg_vape_ui_click_frame_Frame_$1y8ivjg();
        }
        this.FI = guiComponent2;
        this.h(guiComponent2, new Object[0]);
        this.K(guiComponent.G$src$D$1b2f02a());
        this.S(guiComponent.n());
        this.l$src$V$1mibm4x();
    }

    public boolean c$src$Z$1kex42k() {
        return this.FA;
    }

    static {
        if (PopupFrame.V$src$AI$11y84sf() == null) {
            PopupFrame.W(new int[1]);
        }
    }

    public Frame X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9() {
        return this.Fm;
    }

    public void t(boolean bl) {
        this.FA = bl;
    }

    @Override
    public void c() {
        super.c();
    }

    @Override
    public void v() {
    }

    @Override
    public void Y() {
    }

    public Frame Q$src$Lgg_vape_ui_click_frame_Frame_$1y8ivjg() {
        return this.FV;
    }

    public GuiComponent D$src$Lgg_vape_ui_click_component_GuiComponent_$srx612() {
        return this.FI;
    }


    @Override
    public void z(boolean bl) {
    }

    @Override
    public void onDisable() {
    }

    public static int[] V$src$AI$11y84sf() {
        return Fq;
    }
}

