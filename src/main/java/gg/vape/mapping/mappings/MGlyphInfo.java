package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MGlyphProvider;
import gg.vape.ui.click.component.GuiComponent;

public class MGlyphInfo
extends Mapping {
    private MappingField m;
    private MappingField F;
    private MappingField y;
    private MappingField A;
    private MappingField k;
    private MappingField b;
    private MappingField n;
    private MappingField h;
    private MappingField G;
    private MappingField q;
    private MappingMethod Z;

    public float K(Object object) {
        if (this.G == null) {
            return 0.0f;
        }
        return this.G.getFloat(object);
    }

    public MGlyphInfo() {
        super(MappedClasses.v);
        Class<Float> clazz = Float.TYPE;
        boolean bl = true;
        String string = "u0";
        MGlyphInfo mGlyphInfo = this;
        this.m = this.J(string, bl, clazz);
        Class<Float> clazz2 = Float.TYPE;
        boolean bl2 = true;
        String string2 = "u1";
        MGlyphInfo mGlyphInfo2 = this;
        this.h = this.J(string2, bl2, clazz2);
        Class<Float> clazz3 = Float.TYPE;
        boolean bl3 = true;
        String string3 = "v0";
        MGlyphInfo mGlyphInfo3 = this;
        this.b = this.J(string3, bl3, clazz3);
        Class<Float> clazz4 = Float.TYPE;
        boolean bl4 = true;
        String string4 = "v1";
        MGlyphInfo mGlyphInfo4 = this;
        this.n = this.J(string4, bl4, clazz4);
        Class<Float> clazz5 = Float.TYPE;
        boolean bl5 = true;
        String string5 = "left";
        MGlyphInfo mGlyphInfo5 = this;
        this.F = this.J(string5, bl5, clazz5);
        Class<Float> clazz6 = Float.TYPE;
        boolean bl6 = true;
        String string6 = "right";
        MGlyphInfo mGlyphInfo6 = this;
        this.A = this.J(string6, bl6, clazz6);
        if (MGlyphProvider.getGlyphSourceControlFlowState() != null) {
            Class<Float> clazz7 = Float.TYPE;
            boolean bl7 = true;
            String string7 = "up";
            MGlyphInfo mGlyphInfo7 = this;
            this.y = this.J(string7, bl7, clazz7);
            Class<Float> clazz8 = Float.TYPE;
            boolean bl8 = true;
            String string8 = "down";
            MGlyphInfo mGlyphInfo8 = this;
            this.G = this.J(string8, bl8, clazz8);
            Class clazz9 = MappedClasses.g;
            boolean bl9 = true;
            String string9 = "info";
            MGlyphInfo mGlyphInfo9 = this;
            this.k = this.J(string9, bl9, clazz9);
            Class clazz10 = MappedClasses.GPU_TEXTURE_VIEW;
            boolean bl10 = true;
            String string10 = "textureView";
            MGlyphInfo mGlyphInfo10 = this;
            this.q = this.J(string10, bl10, clazz10);
            Class[] classArray = new Class[]{};
            Class clazz11 = MappedClasses.g;
            boolean bl11 = true;
            String string11 = "info";
            MGlyphInfo mGlyphInfo11 = this;
            this.Z = this.Y(string11, bl11, clazz11, classArray);
            GuiComponent.setLegacyComponentState(new GuiComponent[3]);
            return;
        }
        Class<Float> clazz12 = Float.TYPE;
        boolean bl12 = true;
        String string12 = "up";
        MGlyphInfo mGlyphInfo12 = this;
        this.y = this.J(string12, bl12, clazz12);
        Class<Float> clazz13 = Float.TYPE;
        boolean bl13 = true;
        String string13 = "down";
        MGlyphInfo mGlyphInfo13 = this;
        this.G = this.J(string13, bl13, clazz13);
        Class clazz14 = MappedClasses.g;
        boolean bl14 = true;
        String string14 = "info";
        MGlyphInfo mGlyphInfo14 = this;
        this.k = this.J(string14, bl14, clazz14);
        Class clazz15 = MappedClasses.GPU_TEXTURE_VIEW;
        boolean bl15 = true;
        String string15 = "textureView";
        MGlyphInfo mGlyphInfo15 = this;
        this.q = this.J(string15, bl15, clazz15);
        Class[] classArray = new Class[]{};
        Class clazz16 = MappedClasses.g;
        boolean bl16 = true;
        String string16 = "info";
        MGlyphInfo mGlyphInfo16 = this;
        this.Z = this.Y(string16, bl16, clazz16, classArray);
    }

    public float h(Object object) {
        if (this.n == null) {
            return 0.0f;
        }
        return this.n.getFloat(object);
    }

    public Object r$src$Ljava_lang_Object_$1r6sqxs(Object object) {
        if (this.q == null) {
            return null;
        }
        return this.q.getObject(object);
    }

    public float v(Object object) {
        if (this.b == null) {
            return 0.0f;
        }
        return this.b.getFloat(object);
    }

    public float r(Object object) {
        if (this.F == null) {
            return 0.0f;
        }
        return this.F.getFloat(object);
    }

    public Object H(Object object) {
        if (this.Z != null && !this.Z.hasResolutionFailed()) {
            return this.Z.invokeObject(object, new Object[0]);
        }
        if (this.k == null) {
            return null;
        }
        return this.k.getObject(object);
    }

    public float j(Object object) {
        if (this.A == null) {
            return 0.0f;
        }
        return this.A.getFloat(object);
    }

    public float M(Object object) {
        if (this.m == null) {
            return 0.0f;
        }
        return this.m.getFloat(object);
    }

    public float g(Object object) {
        if (this.h == null) {
            return 0.0f;
        }
        return this.h.getFloat(object);
    }


    public float N(Object object) {
        if (this.y == null) {
            return 0.0f;
        }
        return this.y.getFloat(object);
    }
}

