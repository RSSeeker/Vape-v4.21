package gg.vape.tutorial.page.profile;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ProfilesTutorialPage;
import gg.vape.ui.click.frame.impl.profile.ProfileListEntryComponent;

public class SecondActiveProfileListEntrySelector
extends TutorialTargetSelector<ProfileListEntryComponent> {
    private final ProfilesTutorialPage tutorialPage;

    private boolean matchesActiveProfile(ProfileListEntryComponent profileListEntryComponent) {
        return profileListEntryComponent.isActiveProfile();
    }

    @Override
    public boolean matches(ProfileListEntryComponent profileListEntryComponent) {
        return this.matchesActiveProfile(profileListEntryComponent);
    }

    public SecondActiveProfileListEntrySelector(ProfilesTutorialPage profilesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = profilesTutorialPage;
    }

}
