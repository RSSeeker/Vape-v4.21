package gg.vape.account;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.Vape;
import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.account.MutableAccountCredentials;
import gg.vape.account.PermissiveX509TrustManager;
import gg.vape.account.XboxLiveAuthResult;
import gg.vape.utils.network.HttpRequest;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class MicrosoftSessionAuthenticator {
    private final String minecraftAuthenticationEndpoint;
    private MutableAccountCredentials credentials;
    private final String microsoftAuthorizationEndpoint;
    private final String xstsAuthorizationEndpoint;
    private final String xboxLiveAuthenticationEndpoint;
    private static String[] opaqueStringSlots;

    public MinecraftSessionWrapper authenticate() throws IOException {
        String[] microsoftTokens = this.requestMicrosoftOAuthTokens();
        if (microsoftTokens == null || microsoftTokens.length < 2) {
            return null;
        }
        XboxLiveAuthResult xboxLiveAuth = this.requestXboxLiveToken(microsoftTokens[0]);
        if (xboxLiveAuth == null) {
            return null;
        }
        String xstsToken = this.requestXstsToken(xboxLiveAuth.token);
        if (xstsToken == null) {
            return null;
        }
        String minecraftAccessToken = this.exchangeXboxTokenForMinecraftAccessToken(xboxLiveAuth.userHash, xstsToken);
        if (minecraftAccessToken == null) {
            return null;
        }
        if (this.credentials.getProfileId() == null || this.credentials.getProfileName() == null) {
            String[] minecraftProfile = this.requestMinecraftProfile(minecraftAccessToken);
            if (minecraftProfile == null) {
                return null;
            }
            this.credentials.setProfileName(minecraftProfile[0]);
            this.credentials.setProfileId(minecraftProfile[1]);
        }
        return MinecraftSessionWrapper.createLegacy(this.credentials.getProfileName(), this.credentials.getProfileId(),
                minecraftAccessToken, "mojang");
    }

    public static void setOpaqueStringSlots(String[] slots) {
        opaqueStringSlots = slots;
    }

    private XboxLiveAuthResult requestXboxLiveToken(String microsoftAccessToken) throws IOException {
        JsonObject requestBody = new JsonObject();
        JsonObject properties = new JsonObject();
        properties.addProperty("AuthMethod", "RPS");
        properties.addProperty("SiteName", "user.auth.xboxlive.com");
        properties.addProperty("RpsTicket", microsoftAccessToken);
        requestBody.add("Properties", (JsonElement)properties);
        requestBody.addProperty("RelyingParty", "http://auth.xboxlive.com");
        requestBody.addProperty("TokenType", "JWT");
        HttpRequest request = new HttpRequest(this.xboxLiveAuthenticationEndpoint, "POST")
                .L("application/json").e("Content-Type", "application/json").x(requestBody.toString());
        JsonObject responseBody = request.P();
        if (request.e().getResponseCode() != 200) {
            return null;
        }
        return new XboxLiveAuthResult(responseBody.getAsJsonObject("DisplayClaims").getAsJsonArray("xui")
                .get(0).getAsJsonObject().get("uhs").getAsString(), responseBody.get("Token").getAsString());
    }

    private String exchangeXboxTokenForMinecraftAccessToken(String userHash, String xstsToken) throws IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("identityToken", "XBL3.0 x=" + userHash + ";" + xstsToken);
        HttpRequest request = new HttpRequest(this.minecraftAuthenticationEndpoint, "POST")
                .L("application/json").e("Content-Type", "application/json").x(requestBody.toString());
        JsonObject responseBody = request.P();
        if (request.e().getResponseCode() != 200) {
            return null;
        }
        return responseBody.get("access_token").getAsString();
    }

    public static String[] getOpaqueStringSlots() {
        return opaqueStringSlots;
    }

    private static IOException preserveIOException(IOException error) {
        return error;
    }

    private String requestXstsToken(String xboxUserToken) throws IOException {
        JsonObject requestBody = new JsonObject();
        JsonObject properties = new JsonObject();
        properties.addProperty("SandboxId", "RETAIL");
        JsonArray userTokens = new JsonArray();
        userTokens.add((JsonElement)new JsonPrimitive(xboxUserToken));
        properties.add("UserTokens", (JsonElement)userTokens);
        requestBody.add("Properties", (JsonElement)properties);
        requestBody.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
        requestBody.addProperty("TokenType", "JWT");
        HttpRequest request = new HttpRequest(this.xstsAuthorizationEndpoint, "POST")
                .L("application/json").e("Content-Type", "application/json").x(requestBody.toString());
        JsonObject responseBody = request.P();
        if (request.e().getResponseCode() != 200) {
            return null;
        }
        return responseBody.get("Token").getAsString();
    }

    static {
        MicrosoftSessionAuthenticator.setOpaqueStringSlots(new String[4]);
    }

    public MicrosoftSessionAuthenticator(MutableAccountCredentials credentials) {
        this.microsoftAuthorizationEndpoint = "https://login.live.com/oauth20_authorize.srf?client_id=000000004C12AE6F&redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=token&locale=en";
        this.xboxLiveAuthenticationEndpoint = "https://user.auth.xboxlive.com/user/authenticate";
        this.xstsAuthorizationEndpoint = "https://xsts.auth.xboxlive.com/xsts/authorize";
        this.minecraftAuthenticationEndpoint = "https://api.minecraftservices.com/authentication/login_with_xbox";
        this.credentials = credentials;
        CookieHandler.setDefault(new CookieManager());
        TrustManager[] trustManagers = new TrustManager[]{new PermissiveX509TrustManager(this)};
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        catch (Exception error) {
            Vape.logThrowable(error);
        }
    }

    private String[] requestMicrosoftOAuthTokens() throws IOException {
        HttpRequest authorizationRequest = new HttpRequest(this.microsoftAuthorizationEndpoint, "GET");
        Pattern ppftPattern = Pattern.compile("value=\"(.+?)\"");
        String authorizationPage = authorizationRequest.h();
        Matcher ppftMatcher = ppftPattern.matcher(authorizationPage);
        String ppft = "";
        if (!ppftMatcher.find()) {
            return null;
        }
        ppft = ppftMatcher.group(1);
        Pattern postUrlPattern = Pattern.compile("urlPost:'(.+?)'");
        Matcher postUrlMatcher = postUrlPattern.matcher(authorizationPage);
        String postUrl = "";
        if (!postUrlMatcher.find()) {
            return null;
        }
        postUrl = postUrlMatcher.group(1);
        String formBody = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(this.credentials.getUsername(), "UTF-8")
                + "&" + URLEncoder.encode("loginfmt", "UTF-8") + "=" + URLEncoder.encode(this.credentials.getUsername(), "UTF-8")
                + "&" + URLEncoder.encode("passwd", "UTF-8") + "=" + URLEncoder.encode(this.credentials.getPassword(), "UTF-8")
                + "&" + URLEncoder.encode("PPFT", "UTF-8") + "=" + URLEncoder.encode(ppft, "UTF-8");
        byte[] formBytes = formBody.getBytes(StandardCharsets.UTF_8);
        HttpRequest loginRequest = new HttpRequest(postUrl, "POST")
                .e("Content-Type", "application/x-www-form-urlencoded")
                .e("Content-Length", Integer.toString(formBytes.length)).x(formBody);
        String loginResponse = loginRequest.h();
        HttpURLConnection connection = loginRequest.e();
        if (connection.getResponseCode() != 200) {
            return null;
        }
        if (loginResponse.contains("Sign in to")) {
            throw new IllegalStateException("Invalid Email or Password");
        }
        if (loginResponse.contains("Help us protect your account")) {
            throw new IllegalStateException("2-Factor Enabled unable to log in");
        }
        String redirectUrl = connection.getURL().toString();
        Vape.debugLog("redirected URl: " + redirectUrl);
        String fragment = redirectUrl.split("#")[1];
        String[] parameters = fragment.split("&");
        String accessToken = null;
        String refreshToken = null;
        for (String parameter : parameters) {
            if (parameter.contains("access_token")) {
                accessToken = parameter.split("=")[1];
                continue;
            }
            if (!parameter.contains("refresh_token")) continue;
            refreshToken = parameter.split("=")[1];
        }
        return new String[]{accessToken, refreshToken};
    }

    private String[] requestMinecraftProfile(String minecraftAccessToken) throws IOException {
        String profileEndpoint = "https://api.minecraftservices.com/minecraft/profile";
        HttpRequest request = new HttpRequest(profileEndpoint, "GET")
                .e("Authorization", "Bearer " + minecraftAccessToken);
        if (request.e().getResponseCode() != 200) {
            return null;
        }
        JsonObject responseBody = request.P();
        String profileName = responseBody.get("name").getAsString();
        String profileId = responseBody.get("id").getAsString();
        return new String[]{profileName, profileId};
    }
}
