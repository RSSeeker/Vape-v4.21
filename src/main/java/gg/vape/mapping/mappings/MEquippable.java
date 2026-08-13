package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import java.util.Optional;

public class MEquippable
extends Mapping {
    private MappingField h;
    private static String U;
    private MappingField n;

    static {
        MEquippable.z("ZtcwIc");
    }

    public Optional p(Object object) {
        return (Optional)this.n.getObject(object);
    }


    public MEquippable() {
        this(MEquippable.p());
    }

    private MEquippable(String string) {
        super(MappedClasses.Zs);
        if (string != null) {
            Class clazz = MappedClasses.FY;
            boolean bl = true;
            String string2 = "slot";
            MEquippable mEquippable = this;
            this.h = mEquippable.J(string2, bl, clazz);
            Class<Optional> clazz2 = Optional.class;
            boolean bl2 = true;
            String string3 = "assetId";
            MEquippable mEquippable2 = this;
            this.n = this.J(string3, bl2, clazz2);
            return;
        }
        Class clazz = MappedClasses.FY;
        boolean bl = true;
        String string4 = "slot";
        MEquippable mEquippable = this;
        this.h = mEquippable.J(string4, bl, clazz); 
        Class<Optional> clazz3 = Optional.class;
        boolean bl3 = true;
        String string5 = "assetId";
        MEquippable mEquippable3 = this;
        this.n = this.J(string5, bl3, clazz3);
        GuiComponent.setLegacyComponentState(new GuiComponent[5]);
    }

    public static void z(String string) {
        U = string;
    }

    public static String p() {
        return U;
    }

    public Object G(Object object) {
        return this.h.getObject(object);
    }
}

