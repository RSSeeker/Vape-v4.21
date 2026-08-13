package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyMemberStatusComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class PartyMemberTextStatusComponent
extends PartyMemberStatusComponent {
    private double measuredTextHeight;
    private final WrappedTextComponent textComponent;
    private final PaddedComponent paddedText;
    private static GuiComponent[] obfuscationComponents;

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    public PartyMemberTextStatusComponent(String string) {
        this.setShowDisabledOverlay(false);
        GuiComponent[] guiComponentArray = new GuiComponent[1];
        this.textComponent = new WrappedTextComponent(string, 1.0);
        this.paddedText = new PaddedComponent(3.0, this.textComponent);
        guiComponentArray[0] = this.paddedText;
        this.addChildren(guiComponentArray);
        this.textComponent.setWrappingEnabled(false);
        this.textComponent.setWrapWidth(68.0);
        this.textComponent.setFontScale(0.75);
        this.textComponent.setTextColor(Color.WHITE);
    }

    @Override
    public boolean showsAvatar() {
        return true;
    }

    static {
        if (PartyMemberTextStatusComponent.getObfuscationComponents() != null) {
            PartyMemberTextStatusComponent.setObfuscationComponents(new GuiComponent[1]);
        }
    }

    @Override
    public double x() {
        return this.paddedText.A();
    }

    @Override
    public double C() {
        return this.paddedText.L();
    }

    @Override
    public void c() {
        this.textComponent.setFontScale(0.8);
        GuiRenderPrimitives.d(this.paddedText.G$src$D$1b2f02a(), this.paddedText.n(), this.paddedText.A(), this.paddedText.L(), this.getDisabledOverlayColor());
        super.c();
        this.measuredTextHeight = this.textComponent.C();
    }

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }
}
