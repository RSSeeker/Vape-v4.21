package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.PublicProfileUser;
import gg.vape.ui.click.component.ImageTextureComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileUserAvatarTextureCache;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class PublicProfileUserAvatarComponent
extends ImageTextureComponent {
    private long userId;
    private static final String OFFLINE_AVATAR_RESOURCE = "avatar offline@2x";


    @Override
    public void H() {
        GlImageTexture glImageTexture = PublicProfileUserAvatarTextureCache.q().s(this.userId);
        this.setTint(Color.white);
        if (glImageTexture != null) {
            this.setTexture(glImageTexture);
        } else {
            this.setTexture(ImageRenderer.loadResource(OFFLINE_AVATAR_RESOURCE, false, false));
        }
        super.H();
    }

    public PublicProfileUserAvatarComponent(@Nullable PublicProfileUser publicProfileUser, double d, double d2) {
        this(publicProfileUser != null ? publicProfileUser.getUserId() : -1L, d, d2);
    }

    public void W(long l) {
        this.userId = l;
    }

    public PublicProfileUserAvatarComponent(long l, double d, double d2) {
        this.userId = l;
        this.o(d);
        this.Y(d2);
    }
}

