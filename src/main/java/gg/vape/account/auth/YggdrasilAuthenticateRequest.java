package gg.vape.account.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import gg.vape.account.auth.YggdrasilAgent;
import gg.vape.account.auth.YggdrasilAuthRequest;

public class YggdrasilAuthenticateRequest
implements YggdrasilAuthRequest {
    @Expose
    @SerializedName(value="password")
    private final String password;
    @Expose
    @SerializedName(value="agent")
    private final YggdrasilAgent agent;
    @Expose
    @SerializedName(value="username")
    private final String username;
    @Expose(serialize=false, deserialize=false)
    @SerializedName(value="clientToken")
    private final String clientToken;

    public YggdrasilAuthenticateRequest(String username, String password, String clientToken, YggdrasilAgent agent) {
        this.agent = agent;
        this.username = username;
        this.password = password;
        this.clientToken = clientToken;
    }

    public YggdrasilAuthenticateRequest(String username, String password, String clientToken) {
        this(username, password, clientToken, new YggdrasilAgent());
    }

    public YggdrasilAuthenticateRequest(String username, String password) {
        this(username, password, "", new YggdrasilAgent());
    }

    public YggdrasilAuthenticateRequest(String username, String password, YggdrasilAgent agent) {
        this(username, password, "", agent);
    }
}

