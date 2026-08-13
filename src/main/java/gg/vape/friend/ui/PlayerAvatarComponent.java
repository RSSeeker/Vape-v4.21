package gg.vape.friend.ui;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.EntityModelRenderCache;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.GameProfile;
import gg.vape.wrapper.impl.ResourceLocation;
import java.awt.Color;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerAvatarComponent
extends GuiComponent {
    @Nullable
    private EntityLivingBase entity;
    private String username;
    @Nullable
    private ResourceLocation texture;
    @Nullable
    private UUID playerId;

    public String getUsername() {
        return this.username;
    }

    public PlayerAvatarComponent(@Nullable UUID playerId, @NotNull String username, double width, double height) {
        this.playerId = playerId;
        this.username = username;
        this.o(width);
        this.Y(height);
    }

    public void setTexture(@Nullable ResourceLocation texture) {
        this.texture = texture;
    }

    @Nullable
    public ResourceLocation getTexture() {
        return this.texture;
    }

    public PlayerAvatarComponent(double d, double d2) {
        this(null, "", d, d2);
    }

    public static PlayerAvatarComponent fromEntityPlayer(EntityPlayer player, double width, double height) {
        if (player.isNull()) {
            return new PlayerAvatarComponent("", width, height);
        }
        PlayerAvatarComponent avatar = new PlayerAvatarComponent(player.X$src$Ljava_util_UUID_$1o5dyg6(), player.getName(), width, height);
        avatar.setEntity(player);
        return avatar;
    }

    public void setEntity(@Nullable EntityLivingBase entity) {
        this.entity = entity;
    }

    public PlayerAvatarComponent(@NotNull String string, double d, double d2) {
        this(null, string, d, d2);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void H() {
        Color color = new Color(100, 100, 100, 70);
        GuiRenderPrimitives.g(this.G$src$D$1b2f02a(), this.n() + 1.0, this.A(), this.L(), 12.0f, 1.0f, color);
        if (this.entity != null && this.entity.isNotNull()) {
            EntityModelRenderCache.renderEntity(this.entity, (float)this.G$src$D$1b2f02a(), (float)this.n(), (int)this.A(), (int)this.L(), Color.WHITE, 0.0f);
            return;
        }
        if (this.texture != null && this.texture.isNotNull()) {
            String textureKey = this.username != null && !this.username.isEmpty() ? this.username : "unknown";
            EntityModelRenderCache.renderTexture(this.texture, textureKey, (float)this.G$src$D$1b2f02a(), (float)this.n(), (int)this.A(), (int)this.L(), Color.WHITE, 0.0f);
            return;
        }
        ResourceLocation resourceLocation = EntityModelRenderCache.getDefaultSkinTexture();
        EntityModelRenderCache.renderTexture(resourceLocation, "steve", (float)this.G$src$D$1b2f02a(), (float)this.n(), (int)this.A(), (int)this.L(), Color.WHITE, 0.0f);
    }

    public static PlayerAvatarComponent fromGameProfile(GameProfile profile, double width, double height) {
        if (profile.isNull()) {
            return new PlayerAvatarComponent("", width, height);
        }
        return new PlayerAvatarComponent(profile.getUUID(), profile.getName(), width, height);
    }

    public static PlayerAvatarComponent fromTexture(ResourceLocation texture, String username, double width, double height) {
        PlayerAvatarComponent avatar = new PlayerAvatarComponent(username, width, height);
        avatar.setTexture(texture);
        return avatar;
    }

    @Nullable
    public EntityLivingBase getEntity() {
        return this.entity;
    }
}

