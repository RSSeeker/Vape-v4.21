package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MModelRenderer;
import gg.vape.ui.click.component.GuiComponent;
import java.util.List;

public class MModelBase
extends Mapping {
    private MappingField M;
    private final MappingMethod u;
    private final MappingMethod E;
    private final MappingMethod a;

    private List Z(Object object) {
        return (List)this.M.getObject(object);
    }

    private void b(Object object, Object object2, float f, float f2, float f3) {
        this.u.invokeVoid(object, object2, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3));
    }

    public MModelBase() {
        this(MModelRenderer.T());
    }

    private MModelBase(int[] nArray) {
        super(MappedClasses.M);
        if (nArray != null) {
            Class<List> clazz = List.class;
            boolean bl = true;
            String string = "boxList";
            MModelBase mModelBase = this;
            this.M = mModelBase.J(string, bl, clazz);
            Class[] classArray = new Class[]{MappedClasses.zm, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "setLivingAnimations";
            MModelBase mModelBase2 = this;
            this.u = this.Y(string2, bl2, clazz2, classArray);
            Class[] classArray2 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, MappedClasses.zc};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "setRotationAngles";
            MModelBase mModelBase3 = this;
            this.a = this.Y(string3, bl3, clazz3, classArray2);
            Class[] classArray3 = new Class[]{MappedClasses.zc, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "render";
            MModelBase mModelBase4 = this;
            this.E = this.Y(string4, bl4, clazz4, classArray3);
            return;
        }
        Class<List> clazz = List.class;
        boolean bl = true;
        String string = "boxList";
        MModelBase mModelBase = this;
        this.M = mModelBase.J(string, bl, clazz);
        Class[] classArray = new Class[]{MappedClasses.zm, Float.TYPE, Float.TYPE, Float.TYPE};
        Class<Void> clazz5 = Void.TYPE;
        boolean bl5 = true;
        String string5 = "setLivingAnimations";
        MModelBase mModelBase5 = this;
        this.u = this.Y(string5, bl5, clazz5, classArray);
        Class[] classArray4 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, MappedClasses.zc};
        Class<Void> clazz6 = Void.TYPE;
        boolean bl6 = true;
        String string6 = "setRotationAngles";
        MModelBase mModelBase6 = this;
        this.a = this.Y(string6, bl6, clazz6, classArray4);
        Class[] classArray5 = new Class[]{MappedClasses.zc, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
        Class<Void> clazz7 = Void.TYPE;
        boolean bl7 = true;
        String string7 = "render";
        MModelBase mModelBase7 = this;
        this.E = this.Y(string7, bl7, clazz7, classArray5);
        GuiComponent.setLegacyComponentState(new GuiComponent[4]);
    }

    private void e(Object object, float f, float f2, float f3, float f4, float f5, float f6, Object object2) {
        this.a.invokeVoid(object, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5), Float.valueOf(f6), object2);
    }


    private void i(Object object, Object object2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.E.invokeVoid(object, object2, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5), Float.valueOf(f6));
    }
}

