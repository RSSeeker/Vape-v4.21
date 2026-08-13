package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiProfilesPage;

public class ProfilesPageEmptyStateComponent
extends GuiComponent {
    final BindableInputComponent createNameInput;
    final ClickGuiProfilesPage profilesPage;

    @Override
    public void H() {
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        double d3 = this.A();
        double d4 = this.createNameInput.A();
        double d5 = d + d3 - d4;
        double d6 = d2 + (this.L() - this.createNameInput.L()) / 2.0;
        this.createNameInput.K(d5);
        this.createNameInput.S(d6);
        double d7 = ClickGuiProfilesPage.getCreateNameInput(this.profilesPage).L();
        double d8 = d2 + (this.L() - d7) / 2.0;
        double d9 = d3 - d4 - 6.0;
        ClickGuiProfilesPage.getCreateNameInput(this.profilesPage).o(d9);
        ClickGuiProfilesPage.getCreateNameInput(this.profilesPage).K(d);
        ClickGuiProfilesPage.getCreateNameInput(this.profilesPage).S(d8);
        super.H();
    }

    @Override
    public void I() {
    }

    public ProfilesPageEmptyStateComponent(ClickGuiProfilesPage clickGuiProfilesPage, BindableInputComponent bindableInputComponent) {
        this.profilesPage = clickGuiProfilesPage;
        this.createNameInput = bindableInputComponent;
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void u() {
    }
}
