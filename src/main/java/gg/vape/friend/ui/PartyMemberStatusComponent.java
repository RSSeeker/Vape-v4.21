package gg.vape.friend.ui;

import gg.vape.ui.click.component.PanelComponent;

public abstract class PartyMemberStatusComponent
extends PanelComponent {
    private boolean localUser;
    private static String obfuscationName;

    public boolean isLocalUser() {
        return this.localUser;
    }

    public PartyMemberStatusComponent() {
        super(0.0, 0.0);
    }

    public void setLocalUser(boolean localUser) {
        this.localUser = localUser;
    }

    public static void setObfuscationName(String name) {
        obfuscationName = name;
    }

    public static String getObfuscationName() {
        return obfuscationName;
    }

    public abstract boolean showsAvatar();

    static {
        if (PartyMemberStatusComponent.getObfuscationName() != null) {
            PartyMemberStatusComponent.setObfuscationName("fFjvh");
        }
    }
}
