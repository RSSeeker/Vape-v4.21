package gg.vape.account.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YggdrasilAgent {
    @Expose
    @SerializedName(value="name")
    private final String name;
    private static final String DEFAULT_NAME = "Minecraft";
    @Expose
    @SerializedName(value="version")
    private final int version;

    public YggdrasilAgent(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public int getVersion() {
        return this.version;
    }

    public String getName() {
        return this.name;
    }

    public YggdrasilAgent() {
        this(DEFAULT_NAME, 1);
    }
}
