package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MFontSet;
import gg.vape.ui.click.component.GuiComponent;
import java.util.Map;

public class MFontManager
extends Mapping {
    private MappingField v;
    private MappingMethod U;
    private MappingField q;
    private MappingField i;

    public Object x(Object object, String string) {
        if (object == null || string == null) {
            return null;
        }
        try {
            Map<?, ?> map = (Map<?, ?>)this.v.getObject(object);
            if (map != null) {
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    Object k = entry.getKey();
                    if (k == null || !k.toString().contains(string)) continue;
                    return entry.getValue();
                }
            }
        }
        catch (Exception exception) {
            Vape.debugLog("[FontManagerHook] Error getting FontSet for " + string + ": " + exception.getMessage());
        }
        return this.x(object);
    }

    public MFontManager() {
        this(MFontSet.getFontSetControlFlowMarker());
    }

    private MFontManager(String string) {
        super(MappedClasses.lr);
        if (string != null) {
            if (MappedClasses.lr == null) {
                Vape.debugLog("[FontManagerHook] FontManagerClass is null - not available on this version");
                return;
            }
            Class<Map> clazz = Map.class;
            boolean bl = true;
            String string2 = "fontSets";
            MFontManager mFontManager = this;
            this.v = this.J(string2, bl, clazz);
            Class clazz2 = MappedClasses.D9;
            boolean bl2 = true;
            String string3 = "missingFontSet";
            MFontManager mFontManager2 = this;
            this.i = this.J(string3, bl2, clazz2);
            Class clazz3 = MappedClasses.Dt;
            boolean bl3 = true;
            String string4 = "textureManager";
            MFontManager mFontManager3 = this;
            this.q = this.J(string4, bl3, clazz3);
            Class[] classArray = new Class[]{MappedClasses.zC};
            Class clazz4 = MappedClasses.D9;
            boolean bl4 = true;
            String string5 = "getFontSetRaw";
            MFontManager mFontManager4 = this;
            this.U = this.Y(string5, bl4, clazz4, classArray);
            if (GuiComponent.getLegacyComponentState() == null) {
                MFontSet.setFontSetControlFlowMarker("qmD2Ub");
            }
            return;
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MFontSet.setFontSetControlFlowMarker("qmD2Ub");
        }
    }

    public Object u(Object object) {
        if (object == null) {
            return null;
        }
        try {
            Map<?, ?> map = (Map<?, ?>)this.v.getObject(object);
            if (map != null) {
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    Object k = entry.getKey();
                    if (k == null || !k.toString().contains("default")) continue;
                    return entry.getValue();
                }
            }
        }
        catch (Exception exception) {
            Vape.debugLog("[FontManagerHook] Error getting default FontSet: " + exception.getMessage());
        }
        return this.x(object);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public Object x(Object object) {
        if (object == null || this.i == null) {
            return null;
        }
        return this.i.getObject(object);
    }
}

