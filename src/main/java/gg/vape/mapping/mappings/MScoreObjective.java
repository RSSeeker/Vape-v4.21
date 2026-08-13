package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MScoreboard;
import gg.vape.wrapper.impl.ForgeVersion;

public class MScoreObjective
extends Mapping {
    private MappingMethod displayNameOrScoreboardMethod;
    private MappingMethod getScoreboardMethod;
    private MappingMethod formattedDisplayNameMethod;


    public static Object getFormattedDisplayName(MScoreObjective mapping, Object objective) {
        return mapping.getFormattedDisplayName(objective);
    }

    public MScoreObjective() {
        this(MScoreboard.getScoreboardControlFlowState());
    }

    private MScoreObjective(int[] nArray) {
        super(MappedClasses.Y);
        if (nArray != null) {
            Class[] classArray = new Class[]{};
            Class clazz = MappedClasses.F6;
            boolean bl = true;
            String string = "getScoreboard";
            MScoreObjective mScoreObjective = this;
            this.getScoreboardMethod = mScoreObjective.Y(string, bl, clazz, classArray);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray2 = new Class[]{};
                Class clazz2 = MappedClasses.Yr;
                boolean bl2 = true;
                String string2 = "getDisplayName";
                MScoreObjective mScoreObjective2 = this;
                this.displayNameOrScoreboardMethod = this.Y(string2, bl2, clazz2, classArray2);
                Class[] classArray3 = new Class[]{};
                Class clazz3 = MappedClasses.Yr;
                boolean bl3 = true;
                String string3 = "func_237498_g_";
                MScoreObjective mScoreObjective3 = this;
                this.formattedDisplayNameMethod = this.Y(string3, bl3, clazz3, classArray3);
            } else {
                Class[] classArray4 = new Class[]{};
                Class<String> clazz4 = String.class;
                boolean bl4 = true;
                String string4 = "getDisplayName";
                MScoreObjective mScoreObjective4 = this;
                this.displayNameOrScoreboardMethod = this.Y(string4, bl4, clazz4, classArray4);
            }
            return;
        }
        Class[] classArray = new Class[]{};
        Class clazz = MappedClasses.F6;
        boolean bl = true;
        String string = "getScoreboard";
        MScoreObjective mScoreObjective = this;
        this.displayNameOrScoreboardMethod = mScoreObjective.Y(string, bl, clazz, classArray);
    }

    private Object getDisplayNameComponent(Object objective) {
        return this.displayNameOrScoreboardMethod.invokeObject(objective, new Object[0]);
    }

    private Object getFormattedDisplayName(Object objective) {
        return this.formattedDisplayNameMethod.invokeObject(objective, new Object[0]);
    }

    public static String getDisplayNameText(MScoreObjective mapping, Object objective) {
        return mapping.getDisplayNameText(objective);
    }

    private String getDisplayNameText(Object objective) {
        return (String)this.displayNameOrScoreboardMethod.invokeObject(objective, new Object[0]);
    }

    public static Object getDisplayNameComponent(MScoreObjective mapping, Object objective) {
        return mapping.getDisplayNameComponent(objective);
    }

    private Object getScoreboard(Object objective) {
        return this.getScoreboardMethod.invokeObject(objective, new Object[0]);
    }

    public static Object getScoreboard(MScoreObjective mapping, Object objective) {
        return mapping.getScoreboard(objective);
    }
}

