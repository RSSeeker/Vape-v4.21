package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.mappings.MPlayerControllerMP;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ForgeVersion;

public class MWorldClient
extends Mapping {
    private final MappingMethod u;
    private MappingField D;
    private MappingField x;
    private final MappingMethod s;

    public MWorldClient() {
        this(MPlayerControllerMP.d());
    }

    private MWorldClient(int n) {
        super(MappedClasses.Z);
        int n2 = n;
        if (n2 != 0) {
            if (ForgeVersion.MC_1_8_9.L()) {
                Class[] classArray = new Class[]{Integer.TYPE, MappedClasses.zc};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "addEntityImpl";
                MWorldClient mWorldClient = this;
                mWorldClient.Y(string, bl, clazz, classArray);
                Class clazz2 = MappedClasses.CLIENT_WORLD_INFO;
                boolean bl2 = true;
                String string2 = "clientLevelData";
                Class clazz3 = MappedClasses.Z;
                MWorldClient mWorldClient2 = this;
                this.D = this.registerInstanceFieldForOwner(clazz3, string2, bl2, clazz2);
            }
            Class[] classArray = new Class[]{Integer.TYPE, MappedClasses.zc};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "addEntityToWorld";
            MWorldClient mWorldClient = this;
            this.s = mWorldClient.Y(string, bl, clazz, classArray);
            this.u = null;
            if (GuiComponent.getLegacyComponentState() == null) {
                MPlayerControllerMP.p(++n2);
            }
            return;
        }
        if (ForgeVersion.MC_1_8_9.L()) {
            Class clazz = MappedClasses.le;
            boolean bl = true;
            String string = "clientChunkProvider";
            Class clazz4 = MappedClasses.Z;
            MWorldClient mWorldClient = this;
            this.x = mWorldClient.registerInstanceFieldForOwner(clazz4, string, bl, clazz);
        }
        Class[] classArray = new Class[]{Integer.TYPE};
        Class clazz = MappedClasses.zc;
        String string = "getEntityByID";
        MWorldClient mWorldClient = this;
        this.u = ((MappingMethodBuilder)((MappingMethodBuilder)mWorldClient.methodBuilder(string, clazz, classArray).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getEntity")).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.YU)).buildMethod();
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray2 = new Class[]{Integer.TYPE, MappedClasses.zc};
            Class<Void> clazz5 = Void.TYPE;
            boolean bl = true;
            String string3 = "addEntityImpl";
            MWorldClient mWorldClient3 = this;
            this.s = this.Y(string3, bl, clazz5, classArray2);
            Class clazz6 = MappedClasses.CLIENT_WORLD_INFO;
            boolean bl3 = true;
            String string4 = "clientLevelData";
            Class clazz7 = MappedClasses.Z;
            MWorldClient mWorldClient4 = this;
            this.D = this.registerInstanceFieldForOwner(clazz7, string4, bl3, clazz6);
        } else {
            Class[] classArray3 = new Class[]{Integer.TYPE, MappedClasses.zc};
            Class<Void> clazz8 = Void.TYPE;
            boolean bl = true;
            String string5 = "addEntityToWorld";
            MWorldClient mWorldClient5 = this;
            this.s = this.Y(string5, bl, clazz8, classArray3);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MPlayerControllerMP.p(++n2);
        }
    }

    public void Z(Object object, int n, Entity entity) {
        if (ForgeVersion.MC_1_20_6.d()) {
            this.s.invokeVoid(object, entity.getObject());
            return;
        }
        this.s.invokeVoid(object, n, entity.getObject());
    }

    public Object t(Object object, int n) {
        return this.u.invokeObject(object, n);
    }

    public Object u(Object object) {
        return this.D.getObject(object);
    }

    public Object y(Object object) {
        return this.x.getObject(object);
    }

}

