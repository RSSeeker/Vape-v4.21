package gg.vape.ui.click.frame.impl.main;

import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public final class ClickGuiModuleCardRenderState {
    private final Color t;
    private final String R;

    public boolean boolean_n() {
        boolean bl = this.R != null;
        return bl;
    }

    public boolean R() {
        boolean bl = this.t != null;
        return bl;
    }


    @Nullable
    public String java_lang_String_n() {
        return this.R;
    }

    public static ClickGuiModuleCardRenderState b(Color color) {
        return new ClickGuiModuleCardRenderState(null, color);
    }

    public static ClickGuiModuleCardRenderState j(String string) {
        return new ClickGuiModuleCardRenderState(string, null);
    }

    @Nullable
    public Color u() {
        return this.t;
    }

    private ClickGuiModuleCardRenderState(@Nullable String string, @Nullable Color color) {
        this.R = string;
        this.t = color;
    }

    public /* synthetic */ String n() {
        return this.java_lang_String_n();
    }

    public /* synthetic */ boolean n$src$Z$1c2q0zn() {
        return this.boolean_n();
    }
}

