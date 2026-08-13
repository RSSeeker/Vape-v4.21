package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ProfilesTutorialPage;
import gg.vape.ui.click.frame.FrameNavigationButtonComponent;

public class ProfilesNavigationButtonTargetSelector
extends TutorialTargetSelector<FrameNavigationButtonComponent> {
    private static final String targetPageName = "Profiles";
    private final ProfilesTutorialPage tutorialPage;

    private boolean matchesPage(FrameNavigationButtonComponent frameNavigationButtonComponent) {
        return frameNavigationButtonComponent.N$src$Ljava_lang_String_$wy122q().equals(targetPageName);
    }

    @Override
    public boolean matches(FrameNavigationButtonComponent frameNavigationButtonComponent) {
        return this.matchesPage(frameNavigationButtonComponent);
    }

    public ProfilesNavigationButtonTargetSelector(ProfilesTutorialPage profilesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = profilesTutorialPage;
    }
}
