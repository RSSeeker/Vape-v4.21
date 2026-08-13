package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.TextGuiTutorialPage;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.SettingsFrameHeaderComponent;
import java.util.ArrayList;
import java.util.Arrays;

public class SettingsFrameHeaderPrimaryButtonTargetSelector
extends TutorialTargetSelector<SettingsFrameHeaderComponent> {
    private final TextGuiTutorialPage tutorialPage;
    private SettingsFrameHeaderComponent selectedHeader;

    @Override
    public ArrayList<GuiComponent> findTargets(GuiComponent guiComponent) {
        if (this.getTargetType().isInstance(guiComponent)) {
            ArrayList<GuiComponent> arrayList = super.findTargets(guiComponent);
            if (arrayList != null && this.selectedHeader != null && this.selectedHeader.equals(guiComponent)) {
                return new ArrayList<GuiComponent>(Arrays.asList(this.selectedHeader.x$src$Lgg_vape_ui_click_component_IconButtonComponent_$x1h5th()));
            }
            return arrayList;
        }
        return null;
    }

    public SettingsFrameHeaderPrimaryButtonTargetSelector(TextGuiTutorialPage textGuiTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = textGuiTutorialPage;
        this.selectedHeader = null;
    }

    private boolean matchesHeader(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        this.selectedHeader = settingsFrameHeaderComponent;
        return true;
    }

    @Override
    public boolean matches(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        return this.matchesHeader(settingsFrameHeaderComponent);
    }

}
