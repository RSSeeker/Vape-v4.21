package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MGlStateManagerTexGenState;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MGL20
extends Mapping {
    private MappingMethod v;
    private MappingMethod h;

    private void I(int n, int n2, IntBuffer intBuffer) {
        this.v.invokeVoid(null, n, n2, intBuffer);
    }

    public MGL20() {
        this(MGlStateManagerTexGenState.z());
    }

    private MGL20(boolean bl) {
        super(MappedClasses.D4);
        if (bl) {
            if (!GuiRenderPrimitives.V()) {
                Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, IntBuffer.class};
                Class<Void> clazz = Void.TYPE;
                boolean bl2 = false;
                String string = "glGetProgram";
                MGL20 mGL20 = this;
                this.v = mGL20.registerStaticMethod(string, bl2, clazz, classArray);
                Class[] classArray2 = new Class[]{Integer.TYPE, Boolean.TYPE, FloatBuffer.class};
                Class<Void> clazz2 = Void.TYPE;
                boolean bl3 = false;
                String string2 = "glUniformMatrix4";
                MGL20 mGL202 = this;
                this.h = this.registerStaticMethod(string2, bl3, clazz2, classArray2);
            } else {
                Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, IntBuffer.class};
                Class<Void> clazz = Void.TYPE;
                boolean bl4 = false;
                String string = "glGetProgramiv";
                MGL20 mGL20 = this;
                this.v = mGL20.registerStaticMethod(string, bl4, clazz, classArray);
                Class[] classArray3 = new Class[]{Integer.TYPE, Boolean.TYPE, FloatBuffer.class};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl5 = false;
                String string3 = "glUniformMatrix4fv";
                MGL20 mGL203 = this;
                this.h = this.registerStaticMethod(string3, bl5, clazz3, classArray3);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MGlStateManagerTexGenState.d(false);
            }
            return;
        }
        Class[] classArray = new Class[]{Integer.TYPE, Boolean.TYPE, FloatBuffer.class};
        Class<Void> clazz = Void.TYPE;
        boolean bl6 = false;
        String string = "glUniformMatrix4fv";
        MGL20 mGL20 = this;
        this.h = mGL20.registerStaticMethod(string, bl6, clazz, classArray);
        if (GuiComponent.getLegacyComponentState() == null) {
            MGlStateManagerTexGenState.d(true);
        }
    }


    public static void X(MGL20 mGL20, int n, boolean bl, FloatBuffer floatBuffer) {
        mGL20.i(n, bl, floatBuffer);
    }

    private void i(int n, boolean bl, FloatBuffer floatBuffer) {
        this.h.invokeVoid(null, n, bl, floatBuffer);
    }

    public static void P(MGL20 mGL20, int n, int n2, IntBuffer intBuffer) {
        mGL20.I(n, n2, intBuffer);
    }
}

