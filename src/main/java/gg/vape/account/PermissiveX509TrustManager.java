package gg.vape.account;

import gg.vape.account.MicrosoftSessionAuthenticator;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

class PermissiveX509TrustManager
implements X509TrustManager {
    final MicrosoftSessionAuthenticator authenticator;

    PermissiveX509TrustManager(MicrosoftSessionAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certificateChain, String authType) {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certificateChain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
