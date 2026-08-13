package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MITextComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.unmap.TextComponentBase;
import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class ITextComponent
extends Wrapper {
    private static GuiComponent[] o;
    private static final String d;

    public static GuiComponent[] g() {
        return o;
    }

    public static MutableTextComponent P(String string) {
        return new MutableTextComponent(MITextComponent.J(ITextComponent.vapeInstance.getMappings().RL, string));
    }

    static {
        ITextComponent.z(new GuiComponent[3]);
        d = "This method is only for versions 1.16.5 and above";
    }

    public TextComponent h() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return new TextComponent(MITextComponent.y(ITextComponent.vapeInstance.getMappings().RL, this.getObject()));
        }
        throw new UnsupportedOperationException(d);
    }

    public List<ITextComponent> G() {
        ArrayList<ITextComponent> arrayList = new ArrayList<ITextComponent>();
        for (Object e : MITextComponent.V(ITextComponent.vapeInstance.getMappings().RL, this.I)) {
            arrayList.add(new ITextComponent(e));
        }
        return arrayList;
    }

    public String getFormattedText() {
        return MITextComponent.b(ITextComponent.vapeInstance.getMappings().RL, this.I);
    }

    public String a() {
        if (ForgeVersion.MC_1_20_6.d()) {
            Vape.notifyNativeStackTrace();
        }
        return MITextComponent.A(ITextComponent.vapeInstance.getMappings().RL, this.I);
    }

    public ITextComponent(Object object) {
        super(object);
    }

    private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
        return unsupportedOperationException;
    }

    public StringTextComponentBase F() {
        if (ForgeVersion.MC_1_20_6.v()) {
            Vape.notifyNativeStackTrace();
        }
        return new StringTextComponentBase(MITextComponent.r(ITextComponent.vapeInstance.getMappings().RL, this.getObject()));
    }

    public static TextComponentBaseBridge a(String string) {
        if (ForgeVersion.MC_1_20_6.v()) {
            Vape.notifyNativeStackTrace();
        }
        return new TextComponentBaseBridge(MITextComponent.f(ITextComponent.vapeInstance.getMappings().RL, string));
    }

    public TextComponentBase J() {
        if (ForgeVersion.MC_1_20_6.v()) {
            Vape.notifyNativeStackTrace();
        }
        return new TextComponentBase(MITextComponent.e(ITextComponent.vapeInstance.getMappings().RL, this.getObject()));
    }

    public static void z(GuiComponent[] guiComponentArray) {
        o = guiComponentArray;
    }
}
