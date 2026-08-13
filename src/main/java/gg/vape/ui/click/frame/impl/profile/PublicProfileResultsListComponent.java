package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.PagedResultListComponent;

public class PublicProfileResultsListComponent
extends PagedResultListComponent {
    public PublicProfileResultsListComponent(double d, double d2) {
        super(d, d2);
        this.setLoadThreshold(6);
    }
}
