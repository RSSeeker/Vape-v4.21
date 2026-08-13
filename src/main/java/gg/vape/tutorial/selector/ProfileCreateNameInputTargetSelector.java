package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ProfilesTutorialPage;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileCreatePanelComponent;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileCreateNameInputTargetSelector
extends TutorialTargetSelector<ProfileCreatePanelComponent> {
    private final ProfilesTutorialPage tutorialPage;

    private boolean matchesPanel(ProfileCreatePanelComponent profileCreatePanelComponent) {
        return true;
    }

    @Override
    public boolean matches(ProfileCreatePanelComponent profileCreatePanelComponent) {
        return this.matchesPanel(profileCreatePanelComponent);
    }

    @Override
    public ArrayList<GuiComponent> findTargets(GuiComponent guiComponent) {
        if (this.getTargetType().isInstance(guiComponent)) {
            ProfileCreatePanelComponent profileCreatePanelComponent = (ProfileCreatePanelComponent)guiComponent;
            return new ArrayList<GuiComponent>(Arrays.asList(profileCreatePanelComponent.getNameInput()));
        }
        return null;
    }

    public ProfileCreateNameInputTargetSelector(ProfilesTutorialPage profilesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = profilesTutorialPage;
    }
}
