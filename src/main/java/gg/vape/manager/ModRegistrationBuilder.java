package gg.vape.manager;

import gg.vape.manager.ModManager;
import gg.vape.module.MinecraftVersionConstraint;
import gg.vape.module.Mod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ModRegistrationBuilder<T extends Mod> {
    private T module;
    @NotNull
    private final List<List<MinecraftVersionConstraint>> versionConstraints = new ArrayList<List<MinecraftVersionConstraint>>();

    public void registerWith(ModManager modManager) {
        modManager.registerModule((Mod)this.module, this.versionConstraints, false);
    }

    public ModRegistrationBuilder<T> addVersionConstraints(MinecraftVersionConstraint ... constraints) {
        this.versionConstraints.add(Arrays.asList(constraints));
        return this;
    }

    public static <T extends Mod> ModRegistrationBuilder<T> create() {
        return new ModRegistrationBuilder<T>();
    }

    public ModRegistrationBuilder<T> addVersionConstraintList(@NotNull List<MinecraftVersionConstraint> constraints) {
        this.versionConstraints.add(constraints);
        return this;
    }

    public ModRegistrationBuilder<T> setModule(@NotNull T module) {
        this.module = module;
        return this;
    }

    ModRegistrationBuilder() {
    }

    public ModRegistrationBuilder<T> addVersionConstraint(@NotNull MinecraftVersionConstraint constraint) {
        this.versionConstraints.add(Collections.singletonList(constraint));
        return this;
    }
}
