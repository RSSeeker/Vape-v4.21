package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MNetworkManager;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Collection;

public class MNetHandlerPlayClientImpl
extends Mapping {
    private MappingField p;
    private final MappingMethod F;
    private MappingMethod r;
    private final MappingField u;
    private MappingMethod h;

    public static boolean X(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.i(object);
    }

    private Object h(Object object) {
        return this.u.getObject(object);
    }

    private void Q(Object object, Object object2) {
        this.F.invokeVoid(object, object2);
    }

    public MNetHandlerPlayClientImpl() {
        this(MNetworkManager.Q());
    }

    private MNetHandlerPlayClientImpl(String[] stringArray) {
        super(MappedClasses.F1);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_20_6.v()) {
            Class<Boolean> clazz = Boolean.TYPE;
            boolean bl = true;
            String string = "doneLoadingTerrain";
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.p = mNetHandlerPlayClientImpl.J(string, bl, clazz);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            Class clazz = MappedClasses.FO;
            boolean bl = true;
            String string = "connection";
            Class clazz2 = MappedClasses.Yt;
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.u = mNetHandlerPlayClientImpl.registerInstanceFieldForOwner(clazz2, string, bl, clazz);
        } else {
            Class clazz = MappedClasses.FO;
            boolean bl = true;
            String string = "netManager";
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.u = mNetHandlerPlayClientImpl.J(string, bl, clazz);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray = new Class[]{MappedClasses.Fm};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "send";
            Class clazz3 = MappedClasses.Yt;
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.F = mNetHandlerPlayClientImpl.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray = new Class[]{MappedClasses.Fm};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "sendPacket";
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.F = mNetHandlerPlayClientImpl.Y(string, bl, clazz, classArray);
        } else {
            Class[] classArray = new Class[]{MappedClasses.Fm};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "addToSendQueue";
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.F = mNetHandlerPlayClientImpl.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            Class[] classArray = new Class[]{};
            Class<Collection> clazz = Collection.class;
            boolean bl = true;
            String string = "getPlayerInfoMap";
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.h = mNetHandlerPlayClientImpl.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_11.d()) {
            Class[] classArray = new Class[]{};
            Class<Boolean> clazz = Boolean.TYPE;
            String string = "hasClientLoaded";
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl = this;
            this.r = mNetHandlerPlayClientImpl.methodBuilder(string, clazz, classArray).buildMethod();
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MNetworkManager.Z(new String[2]);
        }
    }

    public static Collection H(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.N(object);
    }

    public static boolean d(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.K(object);
    }

    public static void I(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object, Object object2) {
        mNetHandlerPlayClientImpl.Q(object, object2);
    }

    private Collection N(Object object) {
        return (Collection)this.h.invokeObject(object, new Object[0]);
    }


    private boolean i(Object object) {
        return this.p.getBoolean(object);
    }

    private boolean K(Object object) {
        return this.r.invokeBoolean(object, new Object[0]);
    }

    public static Object z(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.h(object);
    }
}

