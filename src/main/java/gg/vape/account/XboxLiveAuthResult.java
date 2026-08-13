package gg.vape.account;

public class XboxLiveAuthResult {
    public String userHash;
    public String token;

    public XboxLiveAuthResult(String userHash, String token) {
        this.userHash = userHash;
        this.token = token;
    }
}
