package gg.vape.ui.click.frame.impl;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.none.TextGuiSettings;
import gg.vape.module.none.TextGuiSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.FontFamily;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.MutableColor;
import gg.vape.utils.NameComparator;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextGuiOverlayComponent
extends GuiComponent {
    public SmoothFontRenderer K;
    private HashMap<Mod, TextGuiModuleRenderState> O;
    private TextGuiSettingsFrame v;
    private List<Mod> R;
    public SmoothFontRenderer b;
    private TextGuiSettings i = Vape.INSTANCE.getModManager().getMod(TextGuiSettings.class);
    private double G = 110.0;
    private HashSet<Mod> I;
    private TimerUtil Q;
    private double o = 0.0;
    private final Comparator<Mod> BY;
    private HashMap<Mod, Double> a = new HashMap();


    public static boolean G(TextGuiOverlayComponent textGuiOverlayComponent) {
        return textGuiOverlayComponent.q$src$Z$emgut();
    }

    private Color R(Mod mod) {
        ModeSelection modeSelection = (ModeSelection)this.i.colorMode.getValue();
        if (modeSelection.equals(this.i.matchGuiColor)) {
            return J.z();
        }
        if (modeSelection.equals(this.i.customColor)) {
            return this.i.textGuiColor.getMutableColor();
        }
        return new Color(mod.getGuiColor());
    }

    @Override
    public void H() {
        this.L(false);
    }

    private void l(List<Mod> list) {
        TextGuiModuleRenderState textGuiModuleRenderState;
        ArrayList<Mod> arrayList = new ArrayList<Mod>();
        ArrayList<Mod> arrayList2 = new ArrayList<Mod>();
        for (Mod mod : list) {
            if (this.R.contains(mod)) continue;
            arrayList.add(mod);
        }
        for (Mod mod : this.R) {
            if (list.contains(mod)) continue;
            arrayList2.add(mod);
        }
        for (Mod mod : arrayList) {
            textGuiModuleRenderState = new TextGuiModuleRenderState(mod);
            textGuiModuleRenderState.w(true);
            textGuiModuleRenderState.f(0.0f);
            this.O.put(mod, textGuiModuleRenderState);
        }
        for (Mod mod : arrayList2) {
            textGuiModuleRenderState = new TextGuiModuleRenderState(mod);
            textGuiModuleRenderState.w(false);
            textGuiModuleRenderState.f(1.0f);
            this.O.put(mod, textGuiModuleRenderState);
        }
        if (!this.Q.hasTimeElapsed(10L) && this.i.animations.getEffectiveValue().booleanValue()) {
            return;
        }
        this.Q.reset();
        for (Mod mod : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderState2;
            textGuiModuleRenderState = this.O.get(mod);
            if (TextGuiModuleRenderState.h(textGuiModuleRenderState)) continue;
            if (!this.i.animations.getEffectiveValue().booleanValue()) {
                if (TextGuiModuleRenderState.a(textGuiModuleRenderState)) {
                    TextGuiModuleRenderState.f(textGuiModuleRenderState, 1.0f);
                    textGuiModuleRenderState.T(true);
                    continue;
                }
                TextGuiModuleRenderState.f(textGuiModuleRenderState, 0.0f);
                textGuiModuleRenderState.T(true);
                continue;
            }
            if (TextGuiModuleRenderState.a(textGuiModuleRenderState)) {
                textGuiModuleRenderState2 = textGuiModuleRenderState;
                TextGuiModuleRenderState.f(textGuiModuleRenderState2, (float)((double)TextGuiModuleRenderState.G(textGuiModuleRenderState2) + 0.08));
                if (!(TextGuiModuleRenderState.G(textGuiModuleRenderState) >= 1.0f)) continue;
                TextGuiModuleRenderState.f(textGuiModuleRenderState, 1.0f);
                textGuiModuleRenderState.T(true);
                continue;
            }
            textGuiModuleRenderState2 = textGuiModuleRenderState;
            TextGuiModuleRenderState.f(textGuiModuleRenderState2, (float)((double)TextGuiModuleRenderState.G(textGuiModuleRenderState2) - 0.08));
            if (!(TextGuiModuleRenderState.G(textGuiModuleRenderState) <= 0.0f)) continue;
            TextGuiModuleRenderState.f(textGuiModuleRenderState, 0.0f);
            textGuiModuleRenderState.T(true);
        }
    }

    @Override
    public void I() {
        this.L(true);
    }

    public TextGuiOverlayComponent(TextGuiSettingsFrame textGuiSettingsFrame) {
        this.I = new HashSet();
        this.O = new HashMap();
        this.R = new ArrayList<Mod>();
        this.Q = new TimerUtil();
        this.BY = new TextGuiModuleComparator(this);
        this.v = textGuiSettingsFrame;
        this.setUseExplicitWidth(true);
        this.setUseExplicitHeight(true);
    }

    @Override
    public double C() {
        int n = this.R();
        if (n == 0 && !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext()) {
            return this.o(3);
        }
        return this.o(n);
    }

    private void f(List<Mod> list) {
        float f = ((Double)this.i.scale.getValue()).floatValue();
        this.b = this.w((int)(16.0f * f));
        int n = !this.q$src$Z$emgut() ? 14 : 16;
        this.K = this.w((int)((float)n * f));
        switch (((ModeSelection)this.i.sortMode.getValue()).getName()) {
            case "Alphabetical": {
                list.sort(new NameComparator());
                break;
            }
            case "Length": {
                list.sort(this.BY);
            }
        }
    }

    private SmoothFontRenderer w(int n) {
        FontFamily fontFamily = Vape.INSTANCE.getFontSelector().W().b();
        if (fontFamily != null && fontFamily != FontFamily.PROXIMA) {
            return Vape.INSTANCE.getFontManager().n(fontFamily, n, false);
        }
        return Vape.INSTANCE.getFontManager().a(n);
    }

    @Override
    public double x() {
        float f = ((Double)this.i.scale.getValue()).floatValue();
        return this.G + (double)(7.0f * f) + 2.0;
    }

    private int R() {
        int n = 0;
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            if (!mod.isEnabled() || mod.getGuiColor() == 0 || !mod.q$src$Z$12h8h4c() || this.i.hideModules.getEffectiveValue().booleanValue() && this.i.hiddenModules.matches(mod.getName(), false)) continue;
            ++n;
        }
        for (Mod mod : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
            if (TextGuiModuleRenderState.a(textGuiModuleRenderState) || TextGuiModuleRenderState.h(textGuiModuleRenderState) || !(TextGuiModuleRenderState.G(textGuiModuleRenderState) > 0.0f)) continue;
            ++n;
        }
        return n;
    }

    @Override
    public void e(GuiMouseEvent guiMouseEvent) {
        super.e(guiMouseEvent);
        if (!this.i.clickDisable.getEffectiveValue().booleanValue() || ClientSettings.INSTANCE.inputEnabled) {
            return;
        }
        for (Mod mod : this.O.keySet()) {
            RectData rectData;
            TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
            if (TextGuiModuleRenderState.J(textGuiModuleRenderState) == null || !(rectData = TextGuiModuleRenderState.J(textGuiModuleRenderState)).J(guiMouseEvent.getX(), guiMouseEvent.getY())) continue;
            if (mod.isEnabled()) {
                mod.setEnabled(false);
                this.I.add(mod);
                break;
            }
            mod.setEnabled(true);
            this.I.remove(mod);
            this.R.add(mod);
            break;
        }
    }

    private boolean q$src$Z$emgut() {
        return this.i.smoothFont.getEffectiveValue() == false;
    }

    private void L(boolean bl) {
        float f;
        Object object;
        int n;
        boolean bl2;
        MousePosition mousePosition = RenderUtils.h();
        float f2 = ((Double)this.i.scale.getValue()).floatValue();
        double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        f2 = (float)((double)f2 * d);
        SmoothFontRenderer smoothFontRenderer = this.w((int)(16.0f * f2));
        int n2 = !this.q$src$Z$emgut() ? 14 : 16;
        SmoothFontRenderer smoothFontRenderer2 = this.w((int)((float)n2 * f2));
        float f3 = this.v.isManagedByClickGui() ? (float)this.n() : (bl ? (float)(this.n() - this.v.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L()) : (float)(this.n() + 4.0));
        float f4 = f3;
        double d2 = this.v.A();
        boolean bl3 = bl2 = this.G$src$D$1b2f02a() + d2 / 2.0 >= (double)(Minecraft.J() / 4) / Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        if (bl2) {
            float f5;
            Object object2;
            int n3;
            boolean bl4 = Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled();
            double d3 = 10.0;
            CopyOnWriteArrayList<Mod> copyOnWriteArrayList = new CopyOnWriteArrayList<Mod>();
            for (Mod object5 : Vape.INSTANCE.getModManager().collectMods()) {
                if (!object5.isEnabled() || object5.getGuiColor() == 0 || !object5.q$src$Z$12h8h4c() || this.i.hideModules.getEffectiveValue().booleanValue() && this.i.hiddenModules.matches(object5.getName(), false)) continue;
                copyOnWriteArrayList.add(object5);
            }
            this.l(copyOnWriteArrayList);
            this.R.clear();
            this.R.addAll(copyOnWriteArrayList);
            for (Mod object7 : this.O.keySet()) {
                TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(object7);
                if (this.I.contains(object7)) {
                    if (object7.isEnabled()) {
                        textGuiModuleRenderState.T(true);
                        textGuiModuleRenderState.f(1.0f);
                        textGuiModuleRenderState.w(true);
                        this.I.remove(object7);
                    } else {
                        textGuiModuleRenderState.T(true);
                        textGuiModuleRenderState.f(1.0f);
                        textGuiModuleRenderState.w(false);
                        if (!copyOnWriteArrayList.contains(object7)) {
                            copyOnWriteArrayList.add(object7);
                        }
                    }
                }
                if (TextGuiModuleRenderState.h(textGuiModuleRenderState) || TextGuiModuleRenderState.a(textGuiModuleRenderState) || !(TextGuiModuleRenderState.G(textGuiModuleRenderState) > 0.0f)) continue;
                copyOnWriteArrayList.add(object7);
            }
            this.f(copyOnWriteArrayList);
            ArrayList arrayList = new ArrayList();
            for (Mod mod : copyOnWriteArrayList) {
                arrayList.add(mod);
                String string = mod.getName();
                String string2 = mod.getSuffixForMode(this.i.getSuffixModeIndex());
                double d4 = this.o(smoothFontRenderer, smoothFontRenderer2, string, string2, f2, bl2);
                d3 = Math.max(d3, d4);
            }
            boolean bl5 = arrayList.isEmpty() && !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext();
            String[] stringArray = new String[]{"Example 1", "Example 2", "Example 3"};
            int n4 = n3 = bl5 ? stringArray.length : arrayList.size();
            if (bl5) {
                for (String string : stringArray) {
                    d3 = Math.max(d3, smoothFontRenderer.Y(string, this.q$src$Z$emgut()));
                }
            }
            if (this.i.watermark.getEffectiveValue().booleanValue()) {
                d3 = Math.max(d3, 60.0);
            }
            if (this.i.addCustomText.getEffectiveValue().booleanValue() && ((String)this.i.customText.getValue()).length() > 0) {
                String string = ((String)this.i.customText.getValue()).replace("&", "\u00a7");
                SmoothFontRenderer smoothFontRenderer3 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0f * f2), true);
                double d5 = smoothFontRenderer3.Y(string, this.q$src$Z$emgut());
                d3 = Math.max(d3, d5);
            }
            this.G = d3;
            double d6 = this.G$src$D$1b2f02a() + d2 - d3;
            ClientSettings.INSTANCE.syncRainbowHue();
            if (this.i.watermark.getEffectiveValue().booleanValue()) {
                float f6 = (float)(d6 + d3 - (double)(58.0f * f2));
                float f7 = f6 + 40.0f * f2;
                float f8 = 12.0f * f2;
                object2 = this.v.applyDefaultEditorAlpha(J.z());
                boolean bl6 = this.i.shadow.getEffectiveValue() != false && ((Color)object2).getAlpha() == 255;
                ImageRenderer.drawImage((Color)object2, f6, f3, "vapelogo", f8, f8, bl6);
                ImageRenderer.drawImage(this.v.applyDefaultEditorAlpha(Color.WHITE), f7, f3, "v4", f8, f8, bl6);
                f3 += 15.0f * f2;
            }
            if (this.i.addCustomText.getEffectiveValue().booleanValue() && ((String)this.i.customText.getValue()).length() > 0) {
                String string = ((String)this.i.customText.getValue()).replace("&", "\u00a7");
                SmoothFontRenderer smoothFontRenderer4 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0f * f2), true);
                double d7 = smoothFontRenderer4.Y(string, this.q$src$Z$emgut());
                f5 = (float)(this.G$src$D$1b2f02a() + d2 - 4.0 - d7);
                if (this.i.shadow.getEffectiveValue().booleanValue()) {
                    smoothFontRenderer4.V(string, f5, f3, this.v.applyDefaultEditorAlpha(this.i.customTextColorEnabled.getEffectiveValue() != false && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled() ? this.i.customTextColor.getMutableColor() : J.z()), this.q$src$Z$emgut());
                } else {
                    smoothFontRenderer4.E(string, f5, f3, this.v.applyDefaultEditorAlpha(this.i.customTextColorEnabled.getEffectiveValue() != false && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled() ? this.i.customTextColor.getMutableColor() : J.z()), this.q$src$Z$emgut());
                }
                f3 = (float)((double)f3 + (smoothFontRenderer4.R(string, this.q$src$Z$emgut()) + (double)(4.0f * f2)));
            }
            if (!bl5 && arrayList.isEmpty()) {
                this.o = f3 - f4;
                return;
            }
            Color color = J.z();
            Color color2 = this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 160));
            boolean bl7 = this.i.renderBackground.getEffectiveValue();
            float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
            float hue = hsbColor[0];
            int n5 = -1;
            int n6 = 1;
            int n7 = 8;
            int n8 = 2;
            int n9 = 4;
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.disableLighting();
            double[] dArray = bl5 ? new double[stringArray.length] : null;
            for (int i = 0; i < n3; ++i) {
                boolean bl8;
                double d8;
                double d9;
                double d10;
                double d11;
                boolean bl9;
                MutableColor mutableColor;
                String string;
                String string3;
                Mod mod;
                Mod mod2 = mod = bl5 ? null : (Mod)arrayList.get(i);
                if (bl5) {
                    string3 = stringArray[i];
                    string = "";
                    mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(J.z()));
                    bl9 = false;
                } else {
                    bl9 = this.I.contains(mod);
                    string3 = mod.getName();
                    string = mod.getSuffixForMode(this.i.getSuffixModeIndex());
                    if (bl9) {
                        string = "\u00a77| Disabled";
                    }
                    mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(this.R(mod)));
                    if (bl4) {
                        if ((hue -= 0.025f) <= 0.0f) {
                            hue = 1.0f;
                        }
                        hsbColor[0] = hue;
                        mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(ColorUtil.createReadableHsbColor(hue, hsbColor[1], hsbColor[2], 4)));
                    }
                }
                double d12 = this.o(smoothFontRenderer, smoothFontRenderer2, string3, string, f2, bl2);
                f5 = (float)(this.G$src$D$1b2f02a() + d2 - 4.0 - d12);
                double d13 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
                double d14 = f5;
                double d15 = d14 + (d12 + 3.5);
                if (i == 0) {
                    f3 += 1.0f * f2;
                }
                int n10 = (int)(f3 - 1.0f * f2);
                int n11 = (int)(d13 + 2.0);
                double d16 = d12 + (double)(7.0f * f2);
                d15 -= d12 + 5.0;
                if (n5 != -1) {
                    n10 = n5;
                }
                boolean bl10 = false;
                if (!bl5 && this.i.clickDisable.getEffectiveValue().booleanValue() && this.O.containsKey(mod)) {
                    TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
                    textGuiModuleRenderState.a(d15, n10, d16, n11);
                    if (TextGuiModuleRenderState.J(textGuiModuleRenderState).Z(mousePosition)) {
                        bl10 = true;
                    }
                }
                if (bl10) {
                    int n12 = ColorUtil.calculatePerceivedBrightness(mutableColor);
                    if (n12 > 200) {
                        mutableColor.blend(new Color(100, 100, 100, 100), 0.1f);
                    } else {
                        mutableColor.blend(new Color(255, 255, 255, 255), 0.2f);
                    }
                }
                n11 = (int)((float)n11 + 1.0f * f2);
                float f9 = 2.0f * f2;
                if (this.q$src$Z$emgut()) {
                    f9 = 0.0f;
                }
                int n13 = 0;
                if (i > 0) {
                    if (bl5) {
                        d11 = dArray[i - 1];
                        if (d11 < d16) {
                            n13 |= n6;
                        }
                    } else {
                        Mod mod3 = (Mod)arrayList.get(i - 1);
                        if (this.a.containsKey(mod3) && (d10 = this.a.get(mod3).doubleValue()) < d16) {
                            n13 |= n6;
                        }
                    }
                } else {
                    n13 |= n6;
                }
                if (i < n3 - 1) {
                    if (bl5) {
                        d11 = smoothFontRenderer.Y(stringArray[i + 1], this.q$src$Z$emgut()) + (double)(7.0f * f2);
                        d9 = d16 - d11;
                        if (Math.abs(d9) < (double)f9) {
                            d16 = d11;
                            d15 += d9;
                        }
                        if (d11 < d16) {
                            n13 |= n7;
                            if (d9 < (double)f9) {
                                d16 += (double)f9 - d9;
                                d15 -= (double)f9 - d9;
                            }
                        }
                    } else {
                        Mod mod4 = (Mod)arrayList.get(i + 1);
                        if (this.a.containsKey(mod4)) {
                            d10 = this.a.get(mod4);
                            d8 = d16 - d10;
                            if (Math.abs(d8) < (double)f9) {
                                d16 = d10;
                                d15 += d8;
                            }
                            if (d10 < d16) {
                                n13 |= n7;
                                if (d8 < (double)f9) {
                                    d16 += (double)f9 - d8;
                                    d15 -= (double)f9 - d8;
                                }
                            }
                        }
                    }
                } else {
                    n13 |= n7;
                }
                if (i > 0) {
                    if (bl5) {
                        double d17 = dArray[i - 1];
                        d9 = d16 - d17;
                        if (d9 == 0.0) {
                            n13 &= ~n6;
                        }
                    } else {
                        Mod mod5 = (Mod)arrayList.get(i - 1);
                        if (this.a.containsKey(mod5) && (d8 = d16 - (d10 = this.a.get(mod5).doubleValue())) == 0.0) {
                            n13 &= ~n6;
                        }
                    }
                }
                boolean bl11 = i == 0;
                boolean bl12 = bl8 = i == n3 - 1;
                if (bl11) {
                    n13 |= n8;
                }
                if (bl8) {
                    n13 |= n9;
                }
                Color color3 = color2;
                Color mutableColor2 = bl9 ? this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 100)) : mutableColor;
                float f10 = 1.5f * f2;
                double d18 = d15;
                double d19 = d16 - (double)f10;
                if ((n13 & n7) != 0) {
                    d19 -= (double)f9;
                    d18 += (double)f9;
                }
                double d20 = d15;
                double d21 = n10 - 1;
                double d22 = d16;
                double d23 = n11 + 2;
                TextGuiModuleRenderState textGuiModuleRenderState = null;
                if (!bl5 && this.O.containsKey(mod)) {
                    textGuiModuleRenderState = this.O.get(mod);
                    d15 += d16 - d16 * (double)TextGuiModuleRenderState.G(textGuiModuleRenderState);
                }
                RenderUtils.m(d20, d21, d22, d23);
                if (bl7) {
                    GuiRenderPrimitives.p(d15, n10, d16, n11, color3, false, f9, 1.0f, 0.0f, null, n13);
                    boolean bl13 = true;
                    if (textGuiModuleRenderState != null && !TextGuiModuleRenderState.h(textGuiModuleRenderState)) {
                        bl13 = false;
                    }
                    if (bl13) {
                        float f11 = 1.0f * f2;
                        GuiRenderPrimitives.a(d18, (float)(n10 + n11) - f11 / 2.0f, d19, f11, this.v.applyDefaultEditorAlpha(new Color(30, 30, 30, 40)));
                    }
                    if (GuiRenderPrimitives.d() && this.q$src$Z$emgut()) {
                        int n14;
                        Color color4;
                        float f12;
                        float f13;
                        float f14;
                        boolean bl14;
                        Color mutableColor3;
                        double d24;
                        double d25;
                        double d26;
                        double d27;
                        double d28 = d15;
                        Color color5 = null;
                        float f15 = 0.0f;
                        float f16 = 1.0f;
                        float f17 = f9;
                        boolean bl15 = false;
                        Color mutableColor4 = mutableColor2;
                        double d29 = n11;
                        double d30 = f10;
                        double d31 = n10;
                        double d32 = d28 + (d16 - 1.5 * (double)f2);
                        if (bl11) {
                            d27 = d32;
                            d26 = d31;
                            d25 = d30;
                            d24 = d29;
                            mutableColor3 = mutableColor4;
                            bl14 = bl15;
                            f14 = f17;
                            f13 = f16;
                            f12 = f15;
                            color4 = color5;
                            n14 = n8;
                        } else {
                            Color color6 = color5;
                            float f18 = f15;
                            float f19 = f16;
                            float f20 = f17;
                            boolean bl16 = bl15;
                            Color mutableColor5 = mutableColor4;
                            double d33 = d29;
                            double d34 = d30;
                            double d35 = d31;
                            double d36 = d32;
                            if (bl8) {
                                d27 = d36;
                                d26 = d35;
                                d25 = d34;
                                d24 = d33;
                                mutableColor3 = mutableColor5;
                                bl14 = bl16;
                                f14 = f20;
                                f13 = f19;
                                f12 = f18;
                                color4 = color6;
                                n14 = n9;
                            } else {
                                d27 = d36;
                                d26 = d35;
                                d25 = d34;
                                d24 = d33;
                                mutableColor3 = mutableColor5;
                                bl14 = bl16;
                                f14 = f20;
                                f13 = f19;
                                f12 = f18;
                                color4 = color6;
                                n14 = 0;
                            }
                        }
                        GuiRenderPrimitives.p(d27, d26, d25, d24, mutableColor3, bl14, f14, f13, f12, color4, n14);
                    } else {
                        int n15;
                        Color color7;
                        float f21;
                        float f22;
                        float f23;
                        boolean bl17;
                        Color mutableColor6;
                        double d37;
                        double d38;
                        double d39;
                        double d40;
                        double d41 = d15;
                        RenderUtils.m(d41 + (d16 - 1.5 * (double)f2), n10, f10, d23);
                        double d42 = d15;
                        Color color8 = null;
                        float f24 = 0.0f;
                        float f25 = 1.0f;
                        float f26 = f9;
                        boolean bl18 = false;
                        Color mutableColor7 = mutableColor2;
                        double d43 = n11;
                        double d44 = 5.0;
                        double d45 = n10;
                        double d46 = d42 + (d16 - 5.0);
                        if (bl11) {
                            d40 = d46;
                            d39 = d45;
                            d38 = d44;
                            d37 = d43;
                            mutableColor6 = mutableColor7;
                            bl17 = bl18;
                            f23 = f26;
                            f22 = f25;
                            f21 = f24;
                            color7 = color8;
                            n15 = n8;
                        } else {
                            Color color9 = color8;
                            float f27 = f24;
                            float f28 = f25;
                            float f29 = f26;
                            boolean bl19 = bl18;
                            Color mutableColor8 = mutableColor7;
                            double d47 = d43;
                            double d48 = d44;
                            double d49 = d45;
                            double d50 = d46;
                            if (bl8) {
                                d40 = d50;
                                d39 = d49;
                                d38 = d48;
                                d37 = d47;
                                mutableColor6 = mutableColor8;
                                bl17 = bl19;
                                f23 = f29;
                                f22 = f28;
                                f21 = f27;
                                color7 = color9;
                                n15 = n9;
                            } else {
                                d40 = d50;
                                d39 = d49;
                                d38 = d48;
                                d37 = d47;
                                mutableColor6 = mutableColor8;
                                bl17 = bl19;
                                f23 = f29;
                                f22 = f28;
                                f21 = f27;
                                color7 = color9;
                                n15 = 0;
                            }
                        }
                        GuiRenderPrimitives.p(d40, d39, d38, d37, mutableColor6, bl17, f23, f22, f21, color7, n15);
                        RenderUtils.T();
                    }
                    n5 = n10 + n11;
                    f5 += 2.0f * f2;
                    if (bl5) {
                        dArray[i] = d16;
                    } else {
                        this.a.put(mod, d16);
                    }
                }
                if (!bl5 && textGuiModuleRenderState != null && !textGuiModuleRenderState.W()) {
                    double d51 = -(d12 - d12 * (double)TextGuiModuleRenderState.G(textGuiModuleRenderState));
                    d51 = -d51;
                    f5 = (float)((double)f5 + d51);
                    mutableColor.withAlpha((int)(255.0f * TextGuiModuleRenderState.G(textGuiModuleRenderState)));
                    mutableColor = new MutableColor(mutableColor.getRed(), mutableColor.getGreen(), mutableColor.getBlue(), mutableColor.getAlpha());
                }
                double d52 = (double)(n10 + n11 / 2) - (smoothFontRenderer.R("A", this.q$src$Z$emgut()) + (double)(this.q$src$Z$emgut() ? 0.0f : 2.0f * f2)) / 2.0;
                if (bl9) {
                    mutableColor = bl10 ? new MutableColor(this.v.applyDefaultEditorAlpha(new Color(200, 200, 200, 255))) : new MutableColor(this.v.applyDefaultEditorAlpha(new Color(175, 175, 175, 255)));
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                if (this.i.shadow.getEffectiveValue().booleanValue()) {
                    MutableColor mutableColor14 = new MutableColor(this.v.applyDefaultEditorAlpha(new Color(0, 0, 0, bl7 ? 80 : 170)));
                    if (mutableColor.equals(Color.BLACK)) {
                        mutableColor = new MutableColor(1, 1, 1, 255);
                    }
                    OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                    smoothFontRenderer.t(string3, f5, d52, mutableColor, mutableColor14, this.q$src$Z$emgut());
                    OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                } else {
                    smoothFontRenderer.E(string3, f5, d52, mutableColor, this.q$src$Z$emgut());
                }
                if (!string.isEmpty()) {
                    double d53 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
                    double d54 = smoothFontRenderer2.R("A", this.q$src$Z$emgut());
                    double d55 = d53 - d54;
                    if (bl7 || !this.i.shadow.getEffectiveValue().booleanValue()) {
                        smoothFontRenderer2.E(string, (double)f5 + smoothFontRenderer.Y(string3, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "), d52 + d55, this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)), this.q$src$Z$emgut());
                    } else {
                        smoothFontRenderer2.V(string, (double)f5 + smoothFontRenderer.Y(string3, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "), d52 + d55, this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)), this.q$src$Z$emgut());
                    }
                }
                RenderUtils.T();
                f3 += (float)n11;
            }
            this.o = f3 - f4;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            return;
        }
        boolean bl20 = Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled();
        double d56 = 10.0;
        CopyOnWriteArrayList<Mod> copyOnWriteArrayList = new CopyOnWriteArrayList<Mod>();
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            if (!mod.isEnabled() || mod.getGuiColor() == 0 || !mod.q$src$Z$12h8h4c() || this.i.hideModules.getEffectiveValue().booleanValue() && this.i.hiddenModules.matches(mod.getName(), false)) continue;
            copyOnWriteArrayList.add(mod);
        }
        this.l(copyOnWriteArrayList);
        this.R.clear();
        this.R.addAll(copyOnWriteArrayList);
        for (Mod mod : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
            if (this.I.contains(mod)) {
                if (mod.isEnabled()) {
                    textGuiModuleRenderState.T(true);
                    textGuiModuleRenderState.f(1.0f);
                    textGuiModuleRenderState.w(true);
                    this.I.remove(mod);
                } else {
                    textGuiModuleRenderState.T(true);
                    textGuiModuleRenderState.f(1.0f);
                    textGuiModuleRenderState.w(false);
                    if (!copyOnWriteArrayList.contains(mod)) {
                        copyOnWriteArrayList.add(mod);
                    }
                }
            }
            if (TextGuiModuleRenderState.h(textGuiModuleRenderState) || TextGuiModuleRenderState.a(textGuiModuleRenderState) || !(TextGuiModuleRenderState.G(textGuiModuleRenderState) > 0.0f)) continue;
            copyOnWriteArrayList.add(mod);
        }
        this.f(copyOnWriteArrayList);
        ArrayList arrayList = new ArrayList();
        for (Mod mod : copyOnWriteArrayList) {
            arrayList.add(mod);
            String string = mod.getName();
            String string4 = mod.getSuffixForMode(this.i.getSuffixModeIndex());
            double d57 = this.o(smoothFontRenderer, smoothFontRenderer2, string, string4, f2, bl2);
            d56 = Math.max(d56, d57);
        }
        boolean bl21 = arrayList.isEmpty() && !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext();
        String[] stringArray = new String[]{"Example 1", "Example 2", "Example 3"};
        int n23 = n = bl21 ? stringArray.length : arrayList.size();
        if (bl21) {
            for (String string : stringArray) {
                d56 = Math.max(d56, smoothFontRenderer.Y(string, this.q$src$Z$emgut()));
            }
        }
        if (this.i.watermark.getEffectiveValue().booleanValue()) {
            d56 = Math.max(d56, 60.0);
        }
        if (this.i.addCustomText.getEffectiveValue().booleanValue() && ((String)this.i.customText.getValue()).length() > 0) {
            String string = ((String)this.i.customText.getValue()).replace("&", "\u00a7");
            SmoothFontRenderer smoothFontRenderer5 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0f * f2), true);
            double d58 = smoothFontRenderer5.Y(string, this.q$src$Z$emgut());
            d56 = Math.max(d56, d58);
        }
        this.G = d56;
        double d59 = this.G$src$D$1b2f02a();
        ClientSettings.INSTANCE.syncRainbowHue();
        if (this.i.watermark.getEffectiveValue().booleanValue()) {
            float f30 = (float)d59;
            float f31 = f30 + 40.0f * f2;
            float f32 = 12.0f * f2;
            object = this.v.applyDefaultEditorAlpha(J.z());
            boolean bl22 = this.i.shadow.getEffectiveValue() != false && ((Color)object).getAlpha() == 255;
            ImageRenderer.drawImage((Color)object, f30, f3, "vapelogo", f32, f32, bl22);
            ImageRenderer.drawImage(this.v.applyDefaultEditorAlpha(Color.WHITE), f31, f3, "v4", f32, f32, bl22);
            f3 += 15.0f * f2;
        }
        if (this.i.addCustomText.getEffectiveValue().booleanValue() && ((String)this.i.customText.getValue()).length() > 0) {
            String string = ((String)this.i.customText.getValue()).replace("&", "\u00a7");
            SmoothFontRenderer smoothFontRenderer6 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0f * f2), true);
            double d60 = smoothFontRenderer6.Y(string, this.q$src$Z$emgut());
            f = (float)(this.G$src$D$1b2f02a() + 2.0);
            if (this.i.shadow.getEffectiveValue().booleanValue()) {
                smoothFontRenderer6.V(string, f, f3, this.v.applyDefaultEditorAlpha(this.i.customTextColorEnabled.getEffectiveValue() != false && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled() ? this.i.customTextColor.getMutableColor() : J.z()), this.q$src$Z$emgut());
            } else {
                smoothFontRenderer6.E(string, f, f3, this.v.applyDefaultEditorAlpha(this.i.customTextColorEnabled.getEffectiveValue() != false && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled() ? this.i.customTextColor.getMutableColor() : J.z()), this.q$src$Z$emgut());
            }
            f3 = (float)((double)f3 + (smoothFontRenderer6.R(string, this.q$src$Z$emgut()) + (double)(4.0f * f2)));
        }
        if (!bl21 && arrayList.isEmpty()) {
            this.o = f3 - f4;
            return;
        }
        Color color = J.z();
        Color color15 = this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 160));
        boolean bl23 = this.i.renderBackground.getEffectiveValue();
        float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
        float hue = hsbColor[0];
        int n24 = -1;
        int n25 = 2;
        int n26 = 4;
        int n27 = 1;
        int n28 = 8;
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();
        double[] dArray = bl21 ? new double[stringArray.length] : null;
        for (int i = 0; i < n; ++i) {
            boolean bl24;
            boolean bl25;
            MutableColor mutableColor;
            String string;
            String string5;
            Mod mod;
            Mod mod6 = mod = bl21 ? null : (Mod)arrayList.get(i);
            if (bl21) {
                string5 = stringArray[i];
                string = "";
                mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(J.z()));
                bl25 = false;
            } else {
                bl25 = this.I.contains(mod);
                string5 = mod.getName();
                string = mod.getSuffixForMode(this.i.getSuffixModeIndex());
                if (bl25) {
                    string = "\u00a77| Disabled";
                }
                mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(this.R(mod)));
                if (bl20) {
                    if ((hue -= 0.025f) <= 0.0f) {
                        hue = 1.0f;
                    }
                    hsbColor[0] = hue;
                    mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(ColorUtil.createReadableHsbColor(hue, hsbColor[1], hsbColor[2], 4)));
                }
            }
            double d61 = this.o(smoothFontRenderer, smoothFontRenderer2, string5, string, f2, bl2);
            f = (float)(this.G$src$D$1b2f02a() + 2.0);
            double d62 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
            double d63 = f;
            double d64 = d63 + -1.5;
            if (i == 0) {
                f3 += 1.0f * f2;
            }
            int n29 = (int)(f3 - 1.0f * f2);
            int n30 = (int)(d62 + 2.0);
            double d65 = d61 + (double)(7.0f * f2);
            if (n24 != -1) {
                n29 = n24;
            }
            boolean bl26 = false;
            if (!bl21 && this.i.clickDisable.getEffectiveValue().booleanValue() && this.O.containsKey(mod)) {
                TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
                textGuiModuleRenderState.a(d64, n29, d65, n30);
                if (TextGuiModuleRenderState.J(textGuiModuleRenderState).Z(mousePosition)) {
                    bl26 = true;
                }
            }
            if (bl26) {
                int n31 = ColorUtil.calculatePerceivedBrightness(mutableColor);
                if (n31 > 200) {
                    mutableColor.blend(new Color(100, 100, 100, 100), 0.1f);
                } else {
                    mutableColor.blend(new Color(255, 255, 255, 255), 0.2f);
                }
            }
            n30 = (int)((float)n30 + 1.0f * f2);
            float f33 = 2.0f * f2;
            if (this.q$src$Z$emgut()) {
                f33 = 0.0f;
            }
            int n32 = 0;
            if (i > 0) {
                if (bl21) {
                    double d66 = dArray[i - 1];
                    if (d66 < d65) {
                        n32 |= n25;
                    }
                } else {
                    double d67;
                    Mod mod7 = (Mod)arrayList.get(i - 1);
                    if (this.a.containsKey(mod7) && (d67 = this.a.get(mod7).doubleValue()) < d65) {
                        n32 |= n25;
                    }
                }
            } else {
                n32 |= n25;
            }
            if (i < n - 1) {
                if (bl21) {
                    double d68 = smoothFontRenderer.Y(stringArray[i + 1], this.q$src$Z$emgut()) + (double)(7.0f * f2);
                    double d69 = d65 - d68;
                    if (Math.abs(d69) < (double)f33) {
                        d65 = d68;
                    }
                    if (d68 < d65) {
                        n32 |= n26;
                        if (d69 < (double)f33) {
                            d65 += (double)f33 - d69;
                        }
                    }
                } else {
                    Mod mod8 = (Mod)arrayList.get(i + 1);
                    if (this.a.containsKey(mod8)) {
                        double d70 = this.a.get(mod8);
                        double d71 = d65 - d70;
                        if (Math.abs(d71) < (double)f33) {
                            d65 = d70;
                        }
                        if (d70 < d65) {
                            n32 |= n26;
                            if (d71 < (double)f33) {
                                d65 += (double)f33 - d71;
                            }
                        }
                    }
                }
            } else {
                n32 |= n26;
            }
            if (i > 0) {
                if (bl21) {
                    double d72 = dArray[i - 1];
                    double d73 = d65 - d72;
                    if (d73 == 0.0) {
                        n32 &= ~n25;
                    }
                } else {
                    double d74;
                    double d75;
                    Mod mod9 = (Mod)arrayList.get(i - 1);
                    if (this.a.containsKey(mod9) && (d75 = d65 - (d74 = this.a.get(mod9).doubleValue())) == 0.0) {
                        n32 &= ~n25;
                    }
                }
            }
            boolean bl27 = i == 0;
            boolean bl28 = bl24 = i == n - 1;
            if (bl27) {
                n32 |= n27;
            }
            if (bl24) {
                n32 |= n28;
            }
            Color color16 = color15;
            Color mutableColor15 = bl25 ? this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 100)) : mutableColor;
            float f34 = 1.5f * f2;
            double d76 = d64;
            double d77 = d65 - (double)f34;
            d76 += (double)f34;
            if ((n32 & n26) != 0) {
                d77 -= (double)f33;
            }
            double d78 = d64;
            double d79 = n29 - 1;
            double d80 = d65;
            double d81 = n30 + 2;
            TextGuiModuleRenderState textGuiModuleRenderState = null;
            if (!bl21 && this.O.containsKey(mod)) {
                textGuiModuleRenderState = this.O.get(mod);
                d65 *= (double)TextGuiModuleRenderState.G(textGuiModuleRenderState);
            }
            RenderUtils.m(d78, d79, d80, d81);
            if (bl23) {
                GuiRenderPrimitives.p(d64, n29, d65, n30, color16, false, f33, 1.0f, 0.0f, null, n32);
                boolean bl29 = true;
                if (textGuiModuleRenderState != null && !TextGuiModuleRenderState.h(textGuiModuleRenderState)) {
                    bl29 = false;
                }
                if (bl29) {
                    float f35 = 1.0f * f2;
                    GuiRenderPrimitives.a(d76, (float)(n29 + n30) - f35 / 2.0f, d77, f35, this.v.applyDefaultEditorAlpha(new Color(30, 30, 30, 40)));
                }
                if (GuiRenderPrimitives.d() && this.q$src$Z$emgut()) {
                    int n33;
                    Color color17;
                    float f36;
                    float f37;
                    float f38;
                    boolean bl30;
                    Color mutableColor16;
                    double d82;
                    double d83;
                    double d84;
                    double d85;
                    double d86 = d64;
                    Color color18 = null;
                    float f39 = 0.0f;
                    float f40 = 1.0f;
                    float f41 = f33;
                    boolean bl31 = false;
                    Color mutableColor17 = mutableColor15;
                    double d87 = n30;
                    double d88 = f34;
                    double d89 = n29;
                    double d90 = d86 + 0.0;
                    if (bl27) {
                        d85 = d90;
                        d84 = d89;
                        d83 = d88;
                        d82 = d87;
                        mutableColor16 = mutableColor17;
                        bl30 = bl31;
                        f38 = f41;
                        f37 = f40;
                        f36 = f39;
                        color17 = color18;
                        n33 = n27;
                    } else {
                        Color color19 = color18;
                        float f42 = f39;
                        float f43 = f40;
                        float f44 = f41;
                        boolean bl32 = bl31;
                        Color mutableColor18 = mutableColor17;
                        double d91 = d87;
                        double d92 = d88;
                        double d93 = d89;
                        double d94 = d90;
                        if (bl24) {
                            d85 = d94;
                            d84 = d93;
                            d83 = d92;
                            d82 = d91;
                            mutableColor16 = mutableColor18;
                            bl30 = bl32;
                            f38 = f44;
                            f37 = f43;
                            f36 = f42;
                            color17 = color19;
                            n33 = n28;
                        } else {
                            d85 = d94;
                            d84 = d93;
                            d83 = d92;
                            d82 = d91;
                            mutableColor16 = mutableColor18;
                            bl30 = bl32;
                            f38 = f44;
                            f37 = f43;
                            f36 = f42;
                            color17 = color19;
                            n33 = 0;
                        }
                    }
                    GuiRenderPrimitives.p(d85, d84, d83, d82, mutableColor16, bl30, f38, f37, f36, color17, n33);
                } else {
                    int n34;
                    Color color20;
                    float f45;
                    float f46;
                    float f47;
                    boolean bl33;
                    Color mutableColor19;
                    double d95;
                    double d96;
                    double d97;
                    double d98;
                    double d99 = d64;
                    RenderUtils.m(d99 + 0.0, n29, f34, d81);
                    double d100 = d64;
                    Color color21 = null;
                    float f48 = 0.0f;
                    float f49 = 1.0f;
                    float f50 = f33;
                    boolean bl34 = false;
                    Color mutableColor20 = mutableColor15;
                    double d101 = n30;
                    double d102 = 5.0;
                    double d103 = n29;
                    double d104 = d100 + 0.0;
                    if (bl27) {
                        d98 = d104;
                        d97 = d103;
                        d96 = d102;
                        d95 = d101;
                        mutableColor19 = mutableColor20;
                        bl33 = bl34;
                        f47 = f50;
                        f46 = f49;
                        f45 = f48;
                        color20 = color21;
                        n34 = n27;
                    } else {
                        Color color22 = color21;
                        float f51 = f48;
                        float f52 = f49;
                        float f53 = f50;
                        boolean bl35 = bl34;
                        Color mutableColor21 = mutableColor20;
                        double d105 = d101;
                        double d106 = d102;
                        double d107 = d103;
                        double d108 = d104;
                        if (bl24) {
                            d98 = d108;
                            d97 = d107;
                            d96 = d106;
                            d95 = d105;
                            mutableColor19 = mutableColor21;
                            bl33 = bl35;
                            f47 = f53;
                            f46 = f52;
                            f45 = f51;
                            color20 = color22;
                            n34 = n28;
                        } else {
                            d98 = d108;
                            d97 = d107;
                            d96 = d106;
                            d95 = d105;
                            mutableColor19 = mutableColor21;
                            bl33 = bl35;
                            f47 = f53;
                            f46 = f52;
                            f45 = f51;
                            color20 = color22;
                            n34 = 0;
                        }
                    }
                    GuiRenderPrimitives.p(d98, d97, d96, d95, mutableColor19, bl33, f47, f46, f45, color20, n34);
                    RenderUtils.T();
                }
                n24 = n29 + n30;
                f += 2.0f * f2;
                if (bl21) {
                    dArray[i] = d65;
                } else {
                    this.a.put(mod, d65);
                }
            }
            if (!bl21 && textGuiModuleRenderState != null && !textGuiModuleRenderState.W()) {
                double d109 = -(d61 - d61 * (double)TextGuiModuleRenderState.G(textGuiModuleRenderState));
                f = (float)((double)f + d109);
                mutableColor.withAlpha((int)(255.0f * TextGuiModuleRenderState.G(textGuiModuleRenderState)));
                mutableColor = new MutableColor(mutableColor.getRed(), mutableColor.getGreen(), mutableColor.getBlue(), mutableColor.getAlpha());
            }
            double d110 = (double)(n29 + n30 / 2) - (smoothFontRenderer.R("A", this.q$src$Z$emgut()) + (double)(this.q$src$Z$emgut() ? 0.0f : 2.0f * f2)) / 2.0;
            if (bl25) {
                mutableColor = bl26 ? new MutableColor(this.v.applyDefaultEditorAlpha(new Color(200, 200, 200, 255))) : new MutableColor(this.v.applyDefaultEditorAlpha(new Color(175, 175, 175, 255)));
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.i.shadow.getEffectiveValue().booleanValue()) {
                MutableColor mutableColor27 = new MutableColor(this.v.applyDefaultEditorAlpha(new Color(0, 0, 0, bl23 ? 80 : 170)));
                if (mutableColor.equals(Color.BLACK)) {
                    mutableColor = new MutableColor(1, 1, 1, 255);
                }
                OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                smoothFontRenderer.t(string5, f, d110, mutableColor, mutableColor27, this.q$src$Z$emgut());
                OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            } else {
                smoothFontRenderer.E(string5, f, d110, mutableColor, this.q$src$Z$emgut());
            }
            if (!string.isEmpty()) {
                double d111 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
                double d112 = smoothFontRenderer2.R("A", this.q$src$Z$emgut());
                double d113 = d111 - d112;
                if (bl23 || !this.i.shadow.getEffectiveValue().booleanValue()) {
                    smoothFontRenderer2.E(string, (double)f + smoothFontRenderer.Y(string5, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "), d110 + d113, this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)), this.q$src$Z$emgut());
                } else {
                    smoothFontRenderer2.V(string, (double)f + smoothFontRenderer.Y(string5, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "), d110 + d113, this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)), this.q$src$Z$emgut());
                }
            }
            RenderUtils.T();
            f3 += (float)n30;
        }
        this.o = f3 - f4;
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private double o(SmoothFontRenderer smoothFontRenderer, SmoothFontRenderer smoothFontRenderer2, String string, String string2, float f, boolean bl) {
        double d = smoothFontRenderer.Y(string, this.q$src$Z$emgut());
        double d2 = smoothFontRenderer2.Y(string2.isEmpty() ? "" : string2, this.q$src$Z$emgut());
        double d3 = smoothFontRenderer.Y(" ", this.q$src$Z$emgut());
        d += d2;
        if (!string2.isEmpty()) {
            d += d3;
            if (this.q$src$Z$emgut()) {
                d -= (double)(1.0f * f);
            }
            if (bl) {
                d += 1.0;
            }
        }
        return d;
    }

    public static TextGuiSettings F(TextGuiOverlayComponent textGuiOverlayComponent) {
        return textGuiOverlayComponent.i;
    }

    public double m$src$D$cf9yf() {
        return this.o;
    }

    @Override
    public void u() {
        if (ClientSettings.INSTANCE.inputEnabled || !this.i.clickDisable.getEffectiveValue().booleanValue()) {
            this.I.clear();
        }
        for (Mod mod : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
            if (TextGuiModuleRenderState.J(textGuiModuleRenderState) == null || mod.isEnabled()) continue;
            TextGuiModuleRenderState.H(textGuiModuleRenderState, null);
        }
    }

    public double f$src$D$8kpsw() {
        return this.G;
    }

    private double o(int n) {
        float f = ((Double)this.i.scale.getValue()).floatValue();
        double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        f = (float)((double)f * d);
        SmoothFontRenderer smoothFontRenderer = this.w((int)(16.0f * f));
        double d2 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
        int n2 = (int)(d2 + 2.0);
        n2 = (int)((float)n2 + 1.0f * f);
        double d3 = 0.0;
        if (this.i.watermark.getEffectiveValue().booleanValue()) {
            d3 += (double)(15.0f * f);
        }
        if (this.i.addCustomText.getEffectiveValue().booleanValue() && ((String)this.i.customText.getValue()).length() > 0) {
            String string = ((String)this.i.customText.getValue()).replace("&", "\u00a7");
            SmoothFontRenderer smoothFontRenderer2 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0f * f), true);
            d3 += smoothFontRenderer2.R(string, this.q$src$Z$emgut()) + (double)(4.0f * f);
        }
        if (n > 0) {
            d3 += (double)(1.0f * f);
            d3 += (double)(n2 * n);
        }
        return d3;
    }
}
