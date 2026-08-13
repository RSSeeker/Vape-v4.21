package gg.vape.ui.click.frame;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.input.MouseInput;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.value.ListValueDropdownLayer;
import gg.vape.ui.click.component.value.SearchBlockListDropdownLayer;
import gg.vape.ui.click.frame.DimmedCenteredPopupFrame;
import gg.vape.ui.click.frame.FrameCloseButtonClickHandler;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.FrameStateSerializable;
import gg.vape.ui.click.frame.FrameToolbarComponent;
import gg.vape.ui.click.frame.FrameValueDropdownLayer;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOverlayPopupFrame;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;

public abstract class Frame
extends FrameComponent
implements FrameStateSerializable {
    private boolean Ir = true;
    private ArrayList<PopupFrame> IO;
    protected boolean IU;
    private static GuiComponent[] I7;
    private boolean IW;
    protected MousePosition Io;
    private boolean Iv;
    protected boolean I2 = true;
    private boolean IR;
    private TextButton IS;
    private boolean IC;

    public double a$src$D$1f30pmc() {
        return -(this.A() * 0.75);
    }

    public static void J(GuiComponent[] guiComponentArray) {
        I7 = guiComponentArray;
    }

    @Override
    public void S(double d) {
        super.S(Math.floor(d));
    }

    public static GuiComponent[] c$src$ALgg_vape_ui_click_component_GuiComponent_$19bazfq() {
        return I7;
    }

    public void j(boolean bl) {
        int n = (int)((double)Minecraft.J() / 4.0 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() - this.A() / 2.0);
        int n2 = (int)((double)Minecraft.h() / 4.0 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() - this.L() / 2.0);
        if (bl || this.G$src$D$1b2f02a() != (double)n || this.n() != (double)n2) {
            this.M(n, n2);
            this.H(true);
        }
    }

    public void g(boolean bl) {
        this.Iv = bl;
    }


    public boolean n$src$Z$1fa61uz() {
        return this.Iv;
    }

    public abstract String getName();

    public void a() {
        ArrayList<PopupFrame> arrayList = new ArrayList<PopupFrame>(this.IO);
        for (PopupFrame popupFrame : arrayList) {
            ClientSettings.removePopup(popupFrame);
        }
    }

    public double I$src$D$1eptndo() {
        return -(this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() * 0.75);
    }

    public Frame Y(double d, double d2) {
        this.K(d);
        this.S(d2);
        return this;
    }

    public void q(boolean bl) {
        this.IU = bl;
    }

    public double y$src$D$1fg7rv0() {
        double d = this.T$src$D$1evvdwn();
        double d2 = this.a$src$D$1f30pmc();
        if (this.G$src$D$1b2f02a() < d2) {
            return d2;
        }
        if (this.G$src$D$1b2f02a() > d) {
            return d;
        }
        return this.G$src$D$1b2f02a();
    }

    @Override
    public void I() {
        if (!this.y$src$Z$1f55jvh()) {
            return;
        }
        super.I();
    }

    @Override
    public void V() {
    }

    @Override
    public double C() {
        return this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y();
    }

    @Override
    public void t(JsonObject jsonObject) {
        Boolean bl;
        Double d;
        Double d2 = ConfigJsonUtils.getDouble(jsonObject, "x");
        if (d2 != null) {
            this.K(d2);
            this.P$src$V$i0cha4();
        }
        if ((d = ConfigJsonUtils.getDouble(jsonObject, "y")) != null) {
            this.S(d);
            this.P$src$V$i0cha4();
        }
        if ((bl = ConfigJsonUtils.getBoolean(jsonObject, "pinned")) != null) {
            this.c(bl);
        }
        if (this.Ir) {
            Boolean bl2 = ConfigJsonUtils.getBoolean(jsonObject, "visible");
            if (bl2 != null) {
                this.t(jsonObject.get("visible").getAsBoolean(), false);
                ClientSettings.markFramePositioned(this);
            }
        } else {
            this.t(this.IR, false);
        }
    }

    public ArrayList<PopupFrame> s$src$Ljava_util_ArrayList_$1a2240q() {
        return this.IO;
    }

    @Override
    public void J() {
        if (!this.s$src$Ljava_util_ArrayList_$1a2240q().isEmpty()) {
            for (PopupFrame popupFrame : this.s$src$Ljava_util_ArrayList_$1a2240q()) {
                if (!popupFrame.w$src$Z$e457mb() && !(popupFrame instanceof PublicProfileOverlayPopupFrame) && !(popupFrame instanceof DimmedCenteredPopupFrame)) continue;
                return;
            }
        }
        super.J();
    }

    public double R$src$D$1eurspx() {
        double d = this.I$src$D$1eptndo();
        double d2 = this.g$src$D$1f6bh6i();
        if (this.n() < d) {
            return d;
        }
        if (this.n() > d2) {
            return d2;
        }
        return this.n();
    }

    public void b$src$V$1f3kin7() {
        this.j(false);
    }

    public boolean J$src$Z$1eqdghz() {
        return this.I2;
    }

    public void M(double d, double d2) {
        this.K(d);
        this.S(d2);
        this.l$src$V$1mibm4x();
    }

    public boolean E$src$Z$1enmhj6() {
        double d = this.I$src$D$1eptndo();
        double d2 = this.g$src$D$1f6bh6i();
        return this.n() < d || this.n() > d2;
    }

    @Override
    public void setVisible(boolean bl) {
        this.t(bl, true);
    }

    static {
        Frame.J(new GuiComponent[4]);
    }

    public void j$src$V$1f7yve3() {
        if (this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() == null) {
            return;
        }
        if (this.E$src$Z$1enmhj6()) {
            this.S(this.R$src$D$1eurspx());
        }
    }

    public void L$src$V$1erh1l9() {
        if (this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() == null) {
            return;
        }
        if (this.G$src$Z$1eoq2pw()) {
            this.K(this.y$src$D$1fg7rv0());
        }
    }

    public void t(boolean bl, boolean bl2) {
        if (this.V$src$Z$1xhop3l() != bl && bl2) {
            Vape.INSTANCE.saveAndStop();
        }
        if (bl && bl2) {
            ClientSettings.positionFrameIfNeeded(this);
        }
        super.setVisible(bl);
        this.IU = false;
        if (this.n$src$Z$1fa61uz()) {
            this.b$src$V$1f3kin7();
        }
    }

    public TextButton t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3() {
        return this.IS;
    }

    public boolean y$src$Z$1f55jvh() {
        return this.IC;
    }

    public double g$src$D$1f6bh6i() {
        double d = (double)Minecraft.h() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        return d / 2.0 - this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() * 0.5;
    }

    @Override
    public void K(double d) {
        super.K(Math.floor(d));
    }

    public void c(PopupFrame popupFrame) {
        if (this.IO.contains(popupFrame)) {
            this.IO.remove(popupFrame);
            this.IO.add(popupFrame);
        }
    }

    public void A$src$V$4ceaf0() {
    }

    public boolean G$src$Z$1eoq2pw() {
        double d = this.T$src$D$1evvdwn();
        double d2 = this.a$src$D$1f30pmc();
        return this.G$src$D$1b2f02a() < d2 || this.G$src$D$1b2f02a() > d;
    }

    public double T$src$D$1evvdwn() {
        double d = (double)Minecraft.J() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        return d / 2.0 - this.A() * 0.25;
    }

    @Override
    public void H() {
        if (!this.V$src$Z$1xhop3l()) {
            return;
        }
        if (this.n$src$Z$1fa61uz()) {
            this.b$src$V$1f3kin7();
        }
        this.n$src$V$1fa61rj();
        super.H();
        if (this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null && this.IS.V$src$Z$1xhop3l()) {
            this.IS.setLabelText(this.getName());
            this.IS.o(this.IS.A() + 10.0 + 6.0);
            this.IS.Y(12.0);
            this.IS.K(this.y$src$D$1fg7rv0() - this.IS.A() / 4.0);
            this.IS.S(this.R$src$D$1eurspx());
        }
    }

    @Override
    public double x() {
        return this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C();
    }

    public void L(boolean bl, boolean bl2) {
        this.Ir = bl;
        this.IR = bl2;
    }

    public void Y(boolean bl) {
        this.I2 = bl;
    }

    public Frame() {
        this.IS = new TextButton("Frame", Frame.J.i, Frame.J.i.brighter()).setBorderAndBackgroundColor(Frame.J.l, Frame.J.i);
        this.IO = new ArrayList();
        this.IS.addClickListener(new FrameCloseButtonClickHandler(this));
        this.IS.Y(10.0);
    }

    public void c(boolean bl) {
        if (this.IC != bl) {
            Vape.INSTANCE.saveAndStop();
        }
        this.IC = bl;
    }

    public boolean i$src$Z$1f7f2w6() {
        return ClientSettings.INSTANCE.getActiveStack().Y().contains(this);
    }

    public boolean l$src$Z$193vdc5() {
        return this.Ir;
    }

    public boolean d$src$Z$1lx9d06() {
        return true;
    }

    protected void T(double d, double d2) {
        this.K(this.G$src$D$1b2f02a() + d);
        this.S(this.n() + d2);
        for (GuiComponent guiComponent : this.f()) {
            guiComponent.K(guiComponent.G$src$D$1b2f02a() + d);
            guiComponent.S(guiComponent.n() + d2);
            if (guiComponent instanceof Frame) {
                ((Frame)guiComponent).T(d, d2);
            }
            if (guiComponent instanceof FrameComponent) {
                ((FrameComponent)guiComponent).R(d, d2);
            }
            if (!(guiComponent instanceof FrameToolbarComponent)) continue;
            ((FrameHeaderComponent)guiComponent).K(d, d2);
        }
    }

    public boolean a$src$Z$1f30q5a() {
        if (this instanceof ListValueDropdownLayer || this instanceof FrameValueDropdownLayer || this instanceof SearchBlockListDropdownLayer) {
            return false;
        }
        if (this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() == null) {
            return false;
        }
        return this.G$src$Z$1eoq2pw() || this.E$src$Z$1enmhj6();
    }

    private void n$src$V$1fa61rj() {
        if (!this.IU || !this.I2) {
            return;
        }
        boolean bl = MouseInput.isButtonDown(MouseButton.LEFT_CLICK.ordinal());
        if (!bl) {
            this.IU = false;
            this.N(true);
            this.l$src$V$1mibm4x();
            Vape.INSTANCE.saveAndStop();
            return;
        }
        MousePosition mousePosition = RenderUtils.h();
        double d = mousePosition.O - this.Io.O;
        double d2 = mousePosition.H - this.Io.H;
        this.Io = mousePosition;
        this.T(d, d2);
    }

    public void U() {
        ClientSettings.queueFrameOpen(this);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() == null || !this.V$src$Z$1xhop3l()) {
            return;
        }
        if (this.I2 && guiMouseEvent.getAction().equals((Object)MouseButton.LEFT_CLICK) && this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().D(guiMouseEvent.getX(), guiMouseEvent.getY())) {
            this.Io = RenderUtils.h();
            this.IU = true;
            this.D(true);
        }
    }

    @Override
    public JsonObject Z() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", this.getName());
        jsonObject.addProperty("x", (Number)this.G$src$D$1b2f02a());
        jsonObject.addProperty("y", (Number)this.n());
        jsonObject.addProperty("visible", Boolean.valueOf(this.V$src$Z$1xhop3l()));
        jsonObject.addProperty("pinned", Boolean.valueOf(this.y$src$Z$1f55jvh()));
        return jsonObject;
    }
}

