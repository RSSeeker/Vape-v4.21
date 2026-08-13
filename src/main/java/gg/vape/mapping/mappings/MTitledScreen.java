package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MTitledScreen
extends Mapping {
    public MappingMethod renderScoreboardMethod;
    public MappingField displayedTitleField;
    public MappingMethod renderHudMethod;
    private MappingField hudField;

    public Object getDisplayedTitle(Object titledScreenHandle) {
        if (ForgeVersion.MC_26_2.d()) {
            Object hudHandle = this.hudField.getObject(titledScreenHandle);
            return hudHandle == null ? null : this.displayedTitleField.getObject(hudHandle);
        }
        return this.displayedTitleField.getObject(titledScreenHandle);
    }


    public MTitledScreen() {
        this(ScaledResolution.q());
    }

    private MTitledScreen(int initializationState) {
        super(MappedClasses.Zj);
        int nextControlFlowState = initializationState;
        if (nextControlFlowState != 0) {
            if (ForgeVersion.MC_1_8_9.d()) {
                Class[] classArray = new Class[]{MappedClasses.Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "renderScoreboard";
                MTitledScreen mTitledScreen = this;
                this.renderScoreboardMethod = mTitledScreen.Y(string, bl, clazz, classArray);
            }
            Class[] classArray = new Class[]{MappedClasses.Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
            Class<Void> clazz = Void.TYPE;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_96136_a";
            MTitledScreen mTitledScreen = this;
            this.renderScoreboardMethod = mTitledScreen.Y(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.Y, MappedClasses.Zz};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "renderScoreboard";
            MTitledScreen mTitledScreen2 = this;
            this.renderScoreboardMethod = this.Y(string2, bl2, clazz2, classArray2);
            Class<String> clazz3 = String.class;
            boolean bl3 = true;
            String string3 = "displayedTitle";
            MTitledScreen mTitledScreen3 = this;
            this.displayedTitleField = this.J(string3, bl3, clazz3);
            if (GuiComponent.getLegacyComponentState() == null) {
                ScaledResolution.r(++nextControlFlowState);
            }
            return;
        }
        if (ForgeVersion.MC_1_8_9.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.d()) {
                    if (ForgeVersion.MC_1_21_0.d()) {
                        if (ForgeVersion.MC_26_2.d()) {
                            this.renderHudMethod = null;
                        } else {
                            Class[] classArray = new Class[]{MappedClasses.m, MappedClasses.uy};
                            Class<Void> clazz = Void.TYPE;
                            boolean bl = true;
                            String string = ForgeVersion.MC_26_1.d() ? "extractRenderState" : "render";
                            MTitledScreen mTitledScreen = this;
                            this.renderHudMethod = mTitledScreen.Y(string, bl, clazz, classArray);
                        }
                    } else {
                        Class[] classArray = new Class[]{MappedClasses.m, Float.TYPE};
                        Class<Void> clazz = Void.TYPE;
                        boolean bl = true;
                        String string = "render";
                        MTitledScreen mTitledScreen = this;
                        this.renderHudMethod = mTitledScreen.Y(string, bl, clazz, classArray);
                    }
                } else {
                    Class[] classArray = new Class[]{MappedClasses.DQ, Float.TYPE};
                    Class<Void> clazz = Void.TYPE;
                    boolean bl = true;
                    String string = "renderIngameGui";
                    MTitledScreen mTitledScreen = this;
                    this.renderHudMethod = mTitledScreen.Y(string, bl, clazz, classArray);
                }
            } else {
                Class[] classArray = new Class[]{Float.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "renderGameOverlay";
                Class clazz4 = MappedClasses.Zj;
                MTitledScreen mTitledScreen = this;
                this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz4, string, bl, clazz, classArray);
            }
            if (this.renderHudMethod != null && !Wrapper.isNativeAvailable && !this.renderHudMethod.hasResolutionFailed() && Vape.INSTANCE.isMappingsRemapped()) {
                if (ForgeVersion.MC_1_16_5.d()) {
                    if (MappedClasses.DC != null) {
                        Class[] classArray = new Class[]{MappedClasses.DQ, Float.TYPE};
                        Class<Void> clazz = Void.TYPE;
                        boolean bl = false;
                        String string = this.renderHudMethod.getResolvedName();
                        Class clazz5 = MappedClasses.DC;
                        MTitledScreen mTitledScreen = this;
                        this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz5, string, bl, clazz, classArray);
                    }
                } else {
                    Class[] classArray = new Class[]{Float.TYPE};
                    Class<Void> clazz = Void.TYPE;
                    boolean bl = false;
                    String string = this.renderHudMethod.getResolvedName();
                    Class clazz6 = MappedClasses.DC;
                    MTitledScreen mTitledScreen = this;
                    this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz6, string, bl, clazz, classArray);
                }
            }
        } else {
            Class[] classArray = new Class[]{Float.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "renderGameOverlay";
            Class clazz7 = MappedClasses.Zj;
            MTitledScreen mTitledScreen = this;
            this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz7, string, bl, clazz, classArray);
            if (!Wrapper.isNativeAvailable && !this.renderHudMethod.hasResolutionFailed() && Vape.INSTANCE.isMappingsRemapped()) {
                Class[] classArray3 = new Class[]{Float.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz8 = Void.TYPE;
                boolean bl4 = false;
                String string4 = this.renderHudMethod.getResolvedName();
                Class clazz9 = MappedClasses.DC;
                MTitledScreen mTitledScreen4 = this;
                this.renderHudMethod = this.registerInstanceMethodForOwner(clazz9, string4, bl4, clazz8, classArray3);
            }
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                if (ForgeVersion.MC_26_2.d()) {
                    this.renderScoreboardMethod = null;
                } else {
                    Class[] classArray = new Class[]{MappedClasses.m, MappedClasses.Y};
                    Class<Void> clazz = Void.TYPE;
                    boolean bl = true;
                    String string = "displayScoreboardSidebar";
                    MTitledScreen mTitledScreen = this;
                    this.renderScoreboardMethod = mTitledScreen.Y(string, bl, clazz, classArray);
                }
            } else {
                Class[] classArray = new Class[]{MappedClasses.DQ, MappedClasses.Y};
                Class<Void> clazz = Void.TYPE;
                boolean bl = Wrapper.isNativeAvailable;
                String string = "func_238447_a_";
                MTitledScreen mTitledScreen = this;
                this.renderScoreboardMethod = mTitledScreen.Y(string, bl, clazz, classArray);
            }
            if (ForgeVersion.MC_26_2.d()) {
                Class clazz = MappedClasses.zK;
                boolean bl = true;
                String string = "hud";
                MTitledScreen mTitledScreen = this;
                this.hudField = mTitledScreen.J(string, bl, clazz);
                Class clazz10 = MappedClasses.Yr;
                boolean bl5 = true;
                String string5 = "title";
                Class clazz11 = MappedClasses.zK;
                MTitledScreen mTitledScreen5 = this;
                this.displayedTitleField = this.registerInstanceFieldForOwner(clazz11, string5, bl5, clazz10);
            } else {
                Class clazz = MappedClasses.Yr;
                boolean bl = true;
                String string = "displayedTitle";
                MTitledScreen mTitledScreen = this;
                this.displayedTitleField = mTitledScreen.J(string, bl, clazz);
            }
        } else if (ForgeVersion.MC_1_7_10.L()) {
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                Class[] classArray = new Class[]{MappedClasses.Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "renderScoreboard";
                MTitledScreen mTitledScreen = this;
                this.renderScoreboardMethod = mTitledScreen.Y(string, bl, clazz, classArray);
            } else {
                Class[] classArray = new Class[]{MappedClasses.Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
                Class<Void> clazz = Void.TYPE;
                boolean bl = Wrapper.isNativeAvailable;
                String string = "func_96136_a";
                MTitledScreen mTitledScreen = this;
                this.renderScoreboardMethod = mTitledScreen.Y(string, bl, clazz, classArray);
            }
        } else {
            Class[] classArray = new Class[]{MappedClasses.Y, MappedClasses.Zz};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "renderScoreboard";
            MTitledScreen mTitledScreen = this;
            this.renderScoreboardMethod = mTitledScreen.Y(string, bl, clazz, classArray);
            Class<String> clazz12 = String.class;
            boolean bl6 = true;
            String string6 = "displayedTitle";
            MTitledScreen mTitledScreen6 = this;
            this.displayedTitleField = this.J(string6, bl6, clazz12);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            ScaledResolution.r(++nextControlFlowState);
        }
    }
}

