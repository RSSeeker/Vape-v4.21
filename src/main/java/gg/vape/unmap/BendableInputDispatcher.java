package gg.vape.unmap;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.unmap.Bendable;
import java.util.ArrayList;

public class BendableInputDispatcher
implements EventListener {
    private static final ArrayList<Bendable> registeredBindings = new ArrayList();
    private static GuiComponent[] legacyComponents;

    @EventHandler
    public void onKeyPress(EventKeyPress event) {
        if (!event.isDown()) {
            return;
        }
        for (Bendable binding : registeredBindings) {
            binding.activateIfMatched(event.getKey());
        }
    }

    public static void register(Bendable binding) {
        registeredBindings.add(binding);
    }

    @EventHandler
    public void onMouseButton(EventMouseButton event) {
        if (!event.getButtonState()) {
            return;
        }
        for (Bendable binding : registeredBindings) {
            binding.activateIfMatched(-100 + event.getButton());
        }
    }

    public static GuiComponent[] getLegacyComponents() {
        return legacyComponents;
    }

    public static void setLegacyComponents(GuiComponent[] components) {
        legacyComponents = components;
    }


    static {
        BendableInputDispatcher.setLegacyComponents(new GuiComponent[1]);
    }
}

