package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.RenderSystem;
import java.nio.IntBuffer;
import org.jetbrains.annotations.Nullable;

public class MGlStateManager
extends Mapping {
    public final MappingMethod o;
    public MappingMethod j;
    public MappingMethod U;
    private final MappingField s;
    public final MappingMethod d;
    public MappingMethod q;
    public MappingMethod w;
    public final MappingMethod E;
    public MappingMethod T;
    @Nullable
    public MappingMethod J;
    public final MappingMethod l;
    public MappingMethod L;
    public final MappingMethod a;
    private MappingMethod W;
    public final MappingMethod V;
    public final MappingMethod x;
    public MappingMethod D;
    public final MappingMethod c;
    public MappingMethod I;
    public MappingMethod h;
    public MappingMethod F;
    public final MappingMethod i;
    public MappingMethod p;
    public final MappingMethod B;
    private MappingField n;
    public MappingMethod z;
    public final MappingMethod e;
    public MappingMethod O;
    public MappingField X;

    public MGlStateManager() {
        this(MEntityRenderer.X());
    }

    private MGlStateManager(int n) {
        super(MappedClasses.K);
        int n2 = n;
        if (n2 != 0) {
            if (ForgeVersion.MC_1_21_6.d()) {
                Class[] classArray = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "color";
                MGlStateManager mGlStateManager = this;
            mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            }
            Class[] classArray = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_179131_c";
            MGlStateManager mGlStateManager = this;
            this.E = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "enableAlpha";
            MGlStateManager mGlStateManager2 = this;
            this.L = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "enableDepth";
            MGlStateManager mGlStateManager3 = this;
            this.d = this.registerStaticMethod(string3, bl3, clazz3, classArray3);
            Class[] classArray4 = new Class[]{};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "disableDepth";
            MGlStateManager mGlStateManager4 = this;
            this.i = this.registerStaticMethod(string4, bl4, clazz4, classArray4);
            Class[] classArray5 = new Class[]{};
            Class<Void> clazz5 = Void.TYPE;
            boolean bl5 = true;
            String string5 = "disableAlpha";
            MGlStateManager mGlStateManager5 = this;
            this.I = this.registerStaticMethod(string5, bl5, clazz5, classArray5);
            Class[] classArray6 = new Class[]{};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "enableTexture2D";
            MGlStateManager mGlStateManager6 = this;
            this.U = this.registerStaticMethod(string6, bl6, clazz6, classArray6);
            Class[] classArray7 = new Class[]{};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "disableTexture2D";
            MGlStateManager mGlStateManager7 = this;
            this.D = this.registerStaticMethod(string7, bl7, clazz7, classArray7);
            Class[] classArray8 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz8 = Void.TYPE;
            boolean bl8 = true;
            String string8 = "rotate";
            MGlStateManager mGlStateManager8 = this;
            this.z = this.registerStaticMethod(string8, bl8, clazz8, classArray8);
            Class<Integer> clazz9 = Integer.TYPE;
            boolean bl9 = true;
            String string9 = "activeTextureUnit";
            MGlStateManager mGlStateManager9 = this;
            this.s = this.registerStaticField(string9, bl9, clazz9);
            Class clazz10 = MappedClasses.Yk;
            boolean bl10 = true;
            String string10 = "blendState";
            MGlStateManager mGlStateManager10 = this;
            this.X = this.registerStaticField(string10, bl10, clazz10);
            Class<?> clazz11 = DescUtils.getArrayType(MappedClasses.Zn);
            boolean bl11 = true;
            String string11 = "textureState";
            MGlStateManager mGlStateManager11 = this;
            this.n = this.registerStaticField(string11, bl11, clazz11);
            Class[] classArray9 = new Class[]{Integer.TYPE};
            Class<Void> clazz12 = Void.TYPE;
            boolean bl12 = true;
            String string12 = "setActiveTexture";
            MGlStateManager mGlStateManager12 = this;
            this.V = this.registerStaticMethod(string12, bl12, clazz12, classArray9);
            Class[] classArray10 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz13 = Void.TYPE;
            boolean bl13 = true;
            String string13 = "tryBlendFuncSeparate";
            MGlStateManager mGlStateManager13 = this;
            this.x = this.registerStaticMethod(string13, bl13, clazz13, classArray10);
            Class[] classArray11 = new Class[]{};
            Class<Integer> clazz14 = Integer.TYPE;
            boolean bl14 = true;
            String string14 = "generateTexture";
            MGlStateManager mGlStateManager14 = this;
            this.j = this.registerStaticMethod(string14, bl14, clazz14, classArray11);
            Class[] classArray12 = new Class[]{Float.TYPE};
            Class<Void> clazz15 = Void.TYPE;
            boolean bl15 = true;
            String string15 = "setFogDensity";
            MGlStateManager mGlStateManager15 = this;
            this.F = this.registerStaticMethod(string15, bl15, clazz15, classArray12);
            if (GuiComponent.getLegacyComponentState() == null) {
                MEntityRenderer.c(++n2);
            }
            this.o = null;
            this.l = null;
            this.a = null;
            this.c = null;
            this.B = null;
            this.e = null;
            return;
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "_glBindFramebuffer";
            MGlStateManager mGlStateManager = this;
            this.O = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = false;
            String string = "getFrameBuffer";
            MGlStateManager mGlStateManager = this;
            this.p = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "m_84544_";
            MGlStateManager mGlStateManager = this;
            this.c = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray13 = new Class[]{Boolean.TYPE};
            Class<Void> clazz16 = Void.TYPE;
            boolean bl16 = false;
            String string16 = "m_84298_";
            MGlStateManager mGlStateManager16 = this;
            this.B = this.registerStaticMethod(string16, bl16, clazz16, classArray13);
            Class[] classArray14 = new Class[]{};
            Class<Void> clazz17 = Void.TYPE;
            boolean bl17 = false;
            String string17 = "m_84525_";
            MGlStateManager mGlStateManager17 = this;
            this.o = this.registerStaticMethod(string17, bl17, clazz17, classArray14);
            Class[] classArray15 = new Class[]{};
            Class<Void> clazz18 = Void.TYPE;
            boolean bl18 = false;
            String string18 = "m_84519_";
            MGlStateManager mGlStateManager18 = this;
            this.l = this.registerStaticMethod(string18, bl18, clazz18, classArray15);
            Class[] classArray16 = new Class[]{};
            Class<Void> clazz19 = Void.TYPE;
            boolean bl19 = false;
            String string19 = "m_84091_";
            MGlStateManager mGlStateManager19 = this;
            this.e = this.registerStaticMethod(string19, bl19, clazz19, classArray16);
            Class[] classArray17 = new Class[]{};
            Class<Void> clazz20 = Void.TYPE;
            boolean bl20 = false;
            String string20 = "m_84094_";
            MGlStateManager mGlStateManager20 = this;
            this.a = this.registerStaticMethod(string20, bl20, clazz20, classArray17);
            if (ForgeVersion.MC_1_21_6.v()) {
                Class[] classArray18 = new Class[]{Integer.TYPE, Integer.TYPE};
                Class<Void> clazz21 = Void.TYPE;
                boolean bl21 = false;
                String string21 = "_blendFunc";
                MGlStateManager mGlStateManager21 = this;
                this.W = this.registerStaticMethod(string21, bl21, clazz21, classArray18);
            }
        } else {
            Class[] classArray = new Class[]{Integer.TYPE, Float.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "alphaFunc";
            MGlStateManager mGlStateManager = this;
            this.q = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray19 = new Class[]{};
            Class<Void> clazz22 = Void.TYPE;
            boolean bl22 = true;
            String string22 = "loadIdentity";
            MGlStateManager mGlStateManager22 = this;
            this.w = this.registerStaticMethod(string22, bl22, clazz22, classArray19);
            Class[] classArray20 = new Class[]{Integer.TYPE};
            Class<Void> clazz23 = Void.TYPE;
            boolean bl23 = true;
            String string23 = "bindTexture";
            MGlStateManager mGlStateManager23 = this;
            this.c = this.registerStaticMethod(string23, bl23, clazz23, classArray20);
            Class[] classArray21 = new Class[]{};
            Class<Void> clazz24 = Void.TYPE;
            boolean bl24 = true;
            String string24 = "enableLighting";
            MGlStateManager mGlStateManager24 = this;
            this.T = this.registerStaticMethod(string24, bl24, clazz24, classArray21);
            Class[] classArray22 = new Class[]{};
            Class<Void> clazz25 = Void.TYPE;
            boolean bl25 = true;
            String string25 = "disableLighting";
            MGlStateManager mGlStateManager25 = this;
            this.h = this.registerStaticMethod(string25, bl25, clazz25, classArray22);
            Class[] classArray23 = new Class[]{Boolean.TYPE};
            Class<Void> clazz26 = Void.TYPE;
            boolean bl26 = true;
            String string26 = "depthMask";
            MGlStateManager mGlStateManager26 = this;
            this.B = this.registerStaticMethod(string26, bl26, clazz26, classArray23);
            Class[] classArray24 = new Class[]{};
            Class<Void> clazz27 = Void.TYPE;
            boolean bl27 = true;
            String string27 = "enableBlend";
            MGlStateManager mGlStateManager27 = this;
            this.o = this.registerStaticMethod(string27, bl27, clazz27, classArray24);
            Class[] classArray25 = new Class[]{};
            Class<Void> clazz28 = Void.TYPE;
            boolean bl28 = true;
            String string28 = "disableBlend";
            MGlStateManager mGlStateManager28 = this;
            this.l = this.registerStaticMethod(string28, bl28, clazz28, classArray25);
            Class[] classArray26 = new Class[]{};
            Class<Void> clazz29 = Void.TYPE;
            boolean bl29 = true;
            String string29 = "enableCull";
            MGlStateManager mGlStateManager29 = this;
            this.e = this.registerStaticMethod(string29, bl29, clazz29, classArray26);
            Class[] classArray27 = new Class[]{};
            Class<Void> clazz30 = Void.TYPE;
            boolean bl30 = true;
            String string30 = "disableCull";
            MGlStateManager mGlStateManager30 = this;
            this.a = this.registerStaticMethod(string30, bl30, clazz30, classArray27);
        }
        if (ForgeVersion.MC_1_17.d()) {
            if (ForgeVersion.MC_26_1.v()) {
                Class[] classArray = new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = false;
                String string = "m_84300_";
                MGlStateManager mGlStateManager = this;
                this.E = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            } else {
                this.E = null;
            }
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "m_84513_";
            MGlStateManager mGlStateManager = this;
            this.d = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray28 = new Class[]{};
            Class<Void> clazz31 = Void.TYPE;
            boolean bl31 = false;
            String string31 = "m_84507_";
            MGlStateManager mGlStateManager31 = this;
            this.i = this.registerStaticMethod(string31, bl31, clazz31, classArray28);
            if (ForgeVersion.MC_1_20_6.v()) {
                Class[] classArray29 = new Class[]{};
                Class<Void> clazz32 = Void.TYPE;
                boolean bl32 = false;
                String string32 = "m_84109_";
                MGlStateManager mGlStateManager32 = this;
                this.U = this.registerStaticMethod(string32, bl32, clazz32, classArray29);
                Class[] classArray30 = new Class[]{};
                Class<Void> clazz33 = Void.TYPE;
                boolean bl33 = false;
                String string33 = "m_84110_";
                MGlStateManager mGlStateManager33 = this;
                this.D = this.registerStaticMethod(string33, bl33, clazz33, classArray30);
            }
            Class<Integer> clazz34 = Integer.TYPE;
            boolean bl34 = false;
            String string34 = "f_84076_";
            MGlStateManager mGlStateManager34 = this;
            this.s = this.registerStaticField(string34, bl34, clazz34);
            Class clazz35 = ForgeVersion.MC_26_2.d()
                    ? DescUtils.getArrayType(MappedClasses.Yk) : MappedClasses.Yk;
            boolean bl35 = false;
            String string35 = "f_84066_";
            MGlStateManager mGlStateManager35 = this;
            this.X = this.registerStaticField(string35, bl35, clazz35);
            Class<?> clazz36 = DescUtils.getArrayType(MappedClasses.Zn);
            boolean bl36 = false;
            String string36 = "f_84077_";
            MGlStateManager mGlStateManager36 = this;
            this.n = this.registerStaticField(string36, bl36, clazz36);
            Class[] classArray31 = new Class[]{Integer.TYPE};
            Class<Void> clazz37 = Void.TYPE;
            boolean bl37 = false;
            String string37 = "m_84538_";
            MGlStateManager mGlStateManager37 = this;
            this.V = this.registerStaticMethod(string37, bl37, clazz37, classArray31);
            Class[] classArray32 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz38 = Void.TYPE;
            boolean bl38 = false;
            String string38 = "m_84335_";
            MGlStateManager mGlStateManager38 = this;
            this.x = this.registerStaticMethod(string38, bl38, clazz38, classArray32);
            if (ForgeVersion.MC_1_21_10.v()) {
                Class[] classArray33 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, IntBuffer.class};
                Class<Void> clazz39 = Void.TYPE;
                boolean bl39 = false;
                String string39 = "m_84209_";
                MGlStateManager mGlStateManager39 = this;
                this.J = this.registerStaticMethod(string39, bl39, clazz39, classArray33);
            }
            Class[] classArray34 = new Class[]{};
            Class<Integer> clazz40 = Integer.TYPE;
            boolean bl40 = false;
            String string40 = "m_84111_";
            MGlStateManager mGlStateManager40 = this;
            this.j = this.registerStaticMethod(string40, bl40, clazz40, classArray34);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "color4f";
            MGlStateManager mGlStateManager = this;
            this.E = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray35 = new Class[]{};
            Class<Void> clazz41 = Void.TYPE;
            boolean bl41 = true;
            String string41 = "enableAlphaTest";
            MGlStateManager mGlStateManager41 = this;
            this.L = this.registerStaticMethod(string41, bl41, clazz41, classArray35);
            Class[] classArray36 = new Class[]{};
            Class<Void> clazz42 = Void.TYPE;
            boolean bl42 = true;
            String string42 = "enableDepthTest";
            MGlStateManager mGlStateManager42 = this;
            this.d = this.registerStaticMethod(string42, bl42, clazz42, classArray36);
            Class[] classArray37 = new Class[]{};
            Class<Void> clazz43 = Void.TYPE;
            boolean bl43 = true;
            String string43 = "disableDepthTest";
            MGlStateManager mGlStateManager43 = this;
            this.i = this.registerStaticMethod(string43, bl43, clazz43, classArray37);
            Class[] classArray38 = new Class[]{};
            Class<Void> clazz44 = Void.TYPE;
            boolean bl44 = true;
            String string44 = "disableAlphaTest";
            MGlStateManager mGlStateManager44 = this;
            this.I = this.registerStaticMethod(string44, bl44, clazz44, classArray38);
            Class[] classArray39 = new Class[]{};
            Class<Void> clazz45 = Void.TYPE;
            boolean bl45 = true;
            String string45 = "enableTexture";
            MGlStateManager mGlStateManager45 = this;
            this.U = this.registerStaticMethod(string45, bl45, clazz45, classArray39);
            Class[] classArray40 = new Class[]{};
            Class<Void> clazz46 = Void.TYPE;
            boolean bl46 = true;
            String string46 = "disableTexture";
            MGlStateManager mGlStateManager46 = this;
            this.D = this.registerStaticMethod(string46, bl46, clazz46, classArray40);
            Class[] classArray41 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz47 = Void.TYPE;
            boolean bl47 = true;
            String string47 = "rotatef";
            MGlStateManager mGlStateManager47 = this;
            this.z = this.registerStaticMethod(string47, bl47, clazz47, classArray41);
            Class<Integer> clazz48 = Integer.TYPE;
            boolean bl48 = true;
            String string48 = "activeTexture";
            MGlStateManager mGlStateManager48 = this;
            this.s = this.registerStaticField(string48, bl48, clazz48);
            Class clazz49 = MappedClasses.Yk;
            boolean bl49 = true;
            String string49 = "BLEND";
            MGlStateManager mGlStateManager49 = this;
            this.X = this.registerStaticField(string49, bl49, clazz49);
            Class<?> clazz50 = DescUtils.getArrayType(MappedClasses.Zn);
            boolean bl50 = true;
            String string50 = "TEXTURES";
            MGlStateManager mGlStateManager50 = this;
            this.n = this.registerStaticField(string50, bl50, clazz50);
            Class[] classArray42 = new Class[]{Integer.TYPE};
            Class<Void> clazz51 = Void.TYPE;
            boolean bl51 = true;
            String string51 = "activeTexture";
            MGlStateManager mGlStateManager51 = this;
            this.V = this.registerStaticMethod(string51, bl51, clazz51, classArray42);
            Class[] classArray43 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz52 = Void.TYPE;
            boolean bl52 = true;
            String string52 = "blendFuncSeparate";
            MGlStateManager mGlStateManager52 = this;
            this.x = this.registerStaticMethod(string52, bl52, clazz52, classArray43);
            Class[] classArray44 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, IntBuffer.class};
            Class<Void> clazz53 = Void.TYPE;
            boolean bl53 = true;
            String string53 = "texImage2D";
            MGlStateManager mGlStateManager53 = this;
            this.J = this.registerStaticMethod(string53, bl53, clazz53, classArray44);
            Class[] classArray45 = new Class[]{};
            Class<Integer> clazz54 = Integer.TYPE;
            boolean bl54 = true;
            String string54 = "genTexture";
            MGlStateManager mGlStateManager54 = this;
            this.j = this.registerStaticMethod(string54, bl54, clazz54, classArray45);
        } else {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class[] classArray = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "color";
                MGlStateManager mGlStateManager = this;
                this.E = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            } else {
                Class[] classArray = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = Wrapper.isNativeAvailable;
                String string = "func_179131_c";
                MGlStateManager mGlStateManager = this;
                this.E = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            }
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "enableAlpha";
            MGlStateManager mGlStateManager = this;
            this.L = mGlStateManager.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray46 = new Class[]{};
            Class<Void> clazz55 = Void.TYPE;
            boolean bl55 = true;
            String string55 = "enableDepth";
            MGlStateManager mGlStateManager55 = this;
            this.d = this.registerStaticMethod(string55, bl55, clazz55, classArray46);
            Class[] classArray47 = new Class[]{};
            Class<Void> clazz56 = Void.TYPE;
            boolean bl56 = true;
            String string56 = "disableDepth";
            MGlStateManager mGlStateManager56 = this;
            this.i = this.registerStaticMethod(string56, bl56, clazz56, classArray47);
            Class[] classArray48 = new Class[]{};
            Class<Void> clazz57 = Void.TYPE;
            boolean bl57 = true;
            String string57 = "disableAlpha";
            MGlStateManager mGlStateManager57 = this;
            this.I = this.registerStaticMethod(string57, bl57, clazz57, classArray48);
            Class[] classArray49 = new Class[]{};
            Class<Void> clazz58 = Void.TYPE;
            boolean bl58 = true;
            String string58 = "enableTexture2D";
            MGlStateManager mGlStateManager58 = this;
            this.U = this.registerStaticMethod(string58, bl58, clazz58, classArray49);
            Class[] classArray50 = new Class[]{};
            Class<Void> clazz59 = Void.TYPE;
            boolean bl59 = true;
            String string59 = "disableTexture2D";
            MGlStateManager mGlStateManager59 = this;
            this.D = this.registerStaticMethod(string59, bl59, clazz59, classArray50);
            Class[] classArray51 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz60 = Void.TYPE;
            boolean bl60 = true;
            String string60 = "rotate";
            MGlStateManager mGlStateManager60 = this;
            this.z = this.registerStaticMethod(string60, bl60, clazz60, classArray51);
            Class<Integer> clazz61 = Integer.TYPE;
            boolean bl61 = true;
            String string61 = "activeTextureUnit";
            MGlStateManager mGlStateManager61 = this;
            this.s = this.registerStaticField(string61, bl61, clazz61);
            Class clazz62 = MappedClasses.Yk;
            boolean bl62 = true;
            String string62 = "blendState";
            MGlStateManager mGlStateManager62 = this;
            this.X = this.registerStaticField(string62, bl62, clazz62);
            Class<?> clazz63 = DescUtils.getArrayType(MappedClasses.Zn);
            boolean bl63 = true;
            String string63 = "textureState";
            MGlStateManager mGlStateManager63 = this;
            this.n = this.registerStaticField(string63, bl63, clazz63);
            Class[] classArray52 = new Class[]{Integer.TYPE};
            Class<Void> clazz64 = Void.TYPE;
            boolean bl64 = true;
            String string64 = "setActiveTexture";
            MGlStateManager mGlStateManager64 = this;
            this.V = this.registerStaticMethod(string64, bl64, clazz64, classArray52);
            Class[] classArray53 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz65 = Void.TYPE;
            boolean bl65 = true;
            String string65 = "tryBlendFuncSeparate";
            MGlStateManager mGlStateManager65 = this;
            this.x = this.registerStaticMethod(string65, bl65, clazz65, classArray53);
            Class[] classArray54 = new Class[]{};
            Class<Integer> clazz66 = Integer.TYPE;
            boolean bl66 = true;
            String string66 = "generateTexture";
            MGlStateManager mGlStateManager66 = this;
            this.j = this.registerStaticMethod(string66, bl66, clazz66, classArray54);
            Class[] classArray55 = new Class[]{Float.TYPE};
            Class<Void> clazz67 = Void.TYPE;
            boolean bl67 = true;
            String string67 = "setFogDensity";
            MGlStateManager mGlStateManager67 = this;
            this.F = this.registerStaticMethod(string67, bl67, clazz67, classArray55);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MEntityRenderer.c(++n2);
        }
    }

    private void Q() {
        this.d.invokeVoidNoArgs(null);
    }

    private Object[] T() {
        return this.n.getObjectArray(null);
    }

    private void T(int n, int n2) {
        this.W.invokeVoid(null, n, n2);
    }

    private void P(boolean bl) {
        this.B.invokeVoid(null, bl);
    }

    private void y() {
        this.i.invokeVoidNoArgs(null);
    }

    private void N(int n) {
        this.c.invokeVoid(null, n);
    }

    public static void B(MGlStateManager mGlStateManager, int n, int n2, int n3, int n4) {
        mGlStateManager.x(n, n2, n3, n4);
    }

    private void i(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, IntBuffer intBuffer) {
        if (this.J != null) {
            this.J.invokeVoid(null, n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
        }
    }

    public static void c(MGlStateManager mGlStateManager, float f) {
        mGlStateManager.u(f);
    }

    public static void P(MGlStateManager mGlStateManager) {
        mGlStateManager.z();
    }

    private void n() {
        this.l.invokeVoidNoArgs(null);
    }

    public static void O(MGlStateManager mGlStateManager, float f, float f2, float f3, float f4) {
        mGlStateManager.g(f, f2, f3, f4);
    }

    public static void Q(MGlStateManager mGlStateManager, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, IntBuffer intBuffer) {
        mGlStateManager.i(n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }

    private void Y() {
        this.I.invokeVoidNoArgs(null);
    }

    public static void j(MGlStateManager mGlStateManager, int n, int n2) {
        mGlStateManager.T(n, n2);
    }

    private void W(float f, float f2, float f3, float f4) {
        this.z.invokeVoid(null, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
    }

    private void j$src$V$1ouix6v() {
        this.T.invokeVoidNoArgs(null);
    }

    private void L(int n) {
        this.V.invokeVoid(null, n);
    }

    private void u(float f) {
        this.F.invokeVoid(null, Float.valueOf(f));
    }

    public static Object U(MGlStateManager mGlStateManager) {
        return mGlStateManager.K();
    }

    private int j() {
        return this.j.invokeInt(null, new Object[0]);
    }

    private void e(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        if (this.E == null) {
            return;
        }
        this.E.invokeVoid(null, bl, bl2, bl3, bl4);
    }

    private void c() {
        this.U.invokeVoidNoArgs(null);
    }

    public static void c(MGlStateManager mGlStateManager) {
        mGlStateManager.G();
    }


    public static void v(MGlStateManager mGlStateManager) {
        mGlStateManager.w();
    }

    public static void Z(MGlStateManager mGlStateManager) {
        mGlStateManager.c();
    }

    private void z() {
        this.w.invokeVoidNoArgs(null);
    }

    private void w() {
        this.L.invokeVoidNoArgs(null);
    }

    private void g(float f, float f2, float f3, float f4) {
        if (this.E == null) {
            RenderSystem.U(f, f2, f3, f4);
            return;
        }
        this.E.invokeVoid(null, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
    }

    public static void N(MGlStateManager mGlStateManager) {
        mGlStateManager.a();
    }

    public static void M(MGlStateManager mGlStateManager) {
        mGlStateManager.Q();
    }

    private void G() {
        this.e.invokeVoidNoArgs(null);
    }

    private int L() {
        return this.s.getInt(null);
    }

    public static void a(MGlStateManager mGlStateManager) {
        mGlStateManager.I();
    }

    public static void X(MGlStateManager mGlStateManager) {
        mGlStateManager.N();
    }

    public static int A(MGlStateManager mGlStateManager) {
        return mGlStateManager.j();
    }

    private void a() {
        this.a.invokeVoidNoArgs(null);
    }

    public static void x(MGlStateManager mGlStateManager) {
        mGlStateManager.j$src$V$1ouix6v();
    }

    public static void P(MGlStateManager mGlStateManager, float f, float f2, float f3, float f4) {
        mGlStateManager.W(f, f2, f3, f4);
    }

    private void I() {
        this.D.invokeVoidNoArgs(null);
    }

    public static Object[] e(MGlStateManager mGlStateManager) {
        return mGlStateManager.T();
    }

    public static void p(MGlStateManager mGlStateManager, boolean bl) {
        mGlStateManager.P(bl);
    }

    public static void m(MGlStateManager mGlStateManager, int n) {
        mGlStateManager.L(n);
    }

    public int w(int n) {
        if (this.p == null) {
            return -1;
        }
        return this.p.invokeInt(null, n);
    }

    private void N() {
        this.h.invokeVoidNoArgs(null);
    }

    private void x(int n, int n2, int n3, int n4) {
        this.x.invokeVoid(null, n, n2, n3, n4);
    }

    public static void a(MGlStateManager mGlStateManager, int n, int n2) {
        mGlStateManager.k(n, n2);
    }

    private void k(int n, int n2) {
        this.O.invokeVoid(null, n, n2);
    }

    public static void j(MGlStateManager mGlStateManager) {
        mGlStateManager.y();
    }

    public static void O(MGlStateManager mGlStateManager) {
        mGlStateManager.Y();
    }

    private Object K() {
        return selectPrimaryBlendState(this.X.getObject(null), ForgeVersion.MC_26_2.d());
    }

    static Object selectPrimaryBlendState(Object blendState, boolean indexedBlendStates) {
        if (!indexedBlendStates) {
            return blendState;
        }
        if (!(blendState instanceof Object[])) {
            throw new IllegalStateException("GlStateManager.BLEND is not a state array");
        }
        Object[] blendStates = (Object[])blendState;
        if (blendStates.length == 0) {
            throw new IllegalStateException("GlStateManager.BLEND has no draw-buffer states");
        }
        return blendStates[0];
    }

    public static void Z(MGlStateManager mGlStateManager, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        mGlStateManager.e(bl, bl2, bl3, bl4);
    }

    private void L$src$V$1oe13e1() {
        this.o.invokeVoidNoArgs(null);
    }
}

