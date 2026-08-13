package gg.vape.module.none;

import com.google.common.collect.ImmutableList;
import gg.vape.Vape;
import gg.vape.input.BindSet;
import gg.vape.module.Mod;
import gg.vape.unmap.ModBendable;
import java.util.Collections;
import java.util.List;

public class ClientSettingsGuiBindBendable
extends ModBendable {

    public ClientSettingsGuiBindBendable(Mod mod) {
        super(mod);
    }

    @Override
    public boolean usesOwnKeybindStorage() {
        return false;
    }

    @Override
    public boolean supportsActivationMode() {
        return false;
    }

    @Override
    public List<Integer> getBoundInputs() {
        return ImmutableList.copyOf(((BindSet)Vape.INSTANCE.getPublicProfileSettings().guiBind.getValue()).getBoundInputs());
    }

    @Override
    public void setBoundInputs(List<Integer> inputCodes) {
        ((BindSet)Vape.INSTANCE.getPublicProfileSettings().guiBind.getValue()).setBoundInputs(inputCodes);
        if (!this.hasValidBinding()) {
            this.setBoundInputs(Collections.singletonList(161));
        }
    }
}

