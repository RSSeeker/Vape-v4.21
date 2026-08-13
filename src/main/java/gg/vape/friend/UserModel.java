package gg.vape.friend;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.ui.click.component.GuiComponent;

public class UserModel {
    private static GuiComponent[] obfuscationState;
    private final String displayName;
    private final long id;

    public String toString() {
        return "UserModel{id=" + this.id + ", displayName='" + this.displayName + '\'' + '}';
    }

    public UserModel(long id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    static {
        UserModel.setObfuscationState(new GuiComponent[5]);
    }


    public void writeTo(ZeusPacketBuffer buffer) {
        buffer.writeLong(this.id);
        buffer.writeString(this.displayName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        UserModel userModel = (UserModel)other;
        return this.id == userModel.id;
    }

    public static GuiComponent[] getObfuscationState() {
        return obfuscationState;
    }

    public long getId() {
        return this.id;
    }

    public int hashCode() {
        return (int)(this.id ^ this.id >>> 32);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static void setObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }

    public UserModel(ZeusPacketBuffer buffer) {
        this.id = buffer.readLong();
        this.displayName = buffer.readString(16);
    }
}

