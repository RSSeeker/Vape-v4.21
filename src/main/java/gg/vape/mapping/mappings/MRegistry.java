package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.stream.Stream;

public class MRegistry
extends Mapping {
    private MappingMethod T;
    private MappingMethod M;
    private static int P;
    private MappingMethod u;
    private MappingMethod H;
    private MappingMethod B;
    private MappingMethod x;


    private Object m(Object object, Object object2) {
        return this.H.invokeObject(object, object2);
    }

    public static int Q() {
        return P;
    }

    public static Object p(MRegistry mRegistry, Object object, int n) {
        return mRegistry.o(object, n);
    }

    public static int Y() {
        int n = MRegistry.Q();
        return 0;
    }

    static {
        MRegistry.o(124);
    }

    public static void o(int n) {
        P = n;
    }

    public static Object d(MRegistry mRegistry, Object object, Object object2) {
        return mRegistry.y(object, object2);
    }

    public static Object S(MRegistry mRegistry, Object object, Object object2) {
        return mRegistry.q(object, object2);
    }

    private int D(Object object, Object object2) {
        return this.x.invokeInt(object, object2);
    }

    public static Object F(MRegistry mRegistry, Object object, Object object2) {
        return mRegistry.m(object, object2);
    }

    public static int e(MRegistry mRegistry, Object object, Object object2) {
        return mRegistry.D(object, object2);
    }

    private Object o(Object object, int n) {
        return this.B.invokeObject(object, n);
    }

    public MRegistry() {
        super(MappedClasses.Fk);
        Class[] classArray = new Class[]{Object.class};
        Class<Integer> clazz = Integer.TYPE;
        String string = "getId";
        MRegistry mRegistry = this;
        this.x = ((MappingMethodBuilder)this.methodBuilder(string, clazz, classArray).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.ua)).buildMethod();
        Class[] classArray2 = new Class[]{Integer.TYPE};
        Class<Object> clazz2 = Object.class;
        boolean bl = true;
        String string2 = "byId";
        Class clazz3 = MappedClasses.ua;
        MRegistry mRegistry2 = this;
        this.B = this.registerInstanceMethodForOwner(clazz3, string2, bl, clazz2, classArray2);
        Class[] classArray3 = new Class[]{Object.class};
        Class clazz4 = MappedClasses.Vo;
        boolean bl2 = true;
        String string3 = "wrapAsHolder";
        MRegistry mRegistry3 = this;
        this.T = this.Y(string3, bl2, clazz4, classArray3);
        Class[] classArray4 = new Class[]{Object.class};
        Class clazz5 = MappedClasses.zC;
        boolean bl3 = true;
        String string4 = "getKey";
        MRegistry mRegistry4 = this;
        this.H = this.Y(string4, bl3, clazz5, classArray4);
        if (MRegistry.Y() != 0) {
            Class[] classArray5 = new Class[]{};
            Class<Stream> clazz6 = Stream.class;
            boolean bl4 = true;
            String string5 = "stream";
            MRegistry mRegistry5 = this;
            this.M = this.Y(string5, bl4, clazz6, classArray5);
            Class[] classArray6 = new Class[]{MappedClasses.zC};
            Class<Object> clazz7 = Object.class;
            String string6 = "get";
            MRegistry mRegistry6 = this;
            this.u = ((MappingMethodBuilder)this.methodBuilder(string6, clazz7, classArray6).setNameForVersion(ForgeVersion.MC_1_21_4.n(), "getValue")).buildMethod();
            GuiComponent.setLegacyComponentState(new GuiComponent[3]);
            return;
        }
        Class[] classArray7 = new Class[]{};
        Class<Stream> clazz8 = Stream.class;
        boolean bl5 = true;
        String string7 = "stream";
        MRegistry mRegistry7 = this;
        this.M = this.Y(string7, bl5, clazz8, classArray7);
        Class[] classArray8 = new Class[]{MappedClasses.zC};
        Class<Object> clazz9 = Object.class;
        String string8 = "get";
        MRegistry mRegistry8 = this;
            this.u = ((MappingMethodBuilder)this.methodBuilder(string8, clazz9, classArray8).setNameForVersion(ForgeVersion.MC_1_21_4.n(), "getValue")).buildMethod();
    }

    private Object q(Object object, Object object2) {
        return this.T.invokeObject(object, object2);
    }

    private Object y(Object object, Object object2) {
        return this.u.invokeObject(object, object2);
    }

    public static Stream h(MRegistry mRegistry, Object object) {
        return mRegistry.X(object);
    }

    private Stream X(Object object) {
        return (Stream)this.M.invokeObject(object, new Object[0]);
    }
}

