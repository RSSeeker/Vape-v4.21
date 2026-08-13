package gg.vape.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.vape.Vape;
import gg.vape.account.LicenseInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LicenseInfoClient {
    HttpURLConnection connection;
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public LicenseInfo fetchLicenseInfo(String licenseKey) {
        try {
            String responseLine;
            URL endpoint = new URL("http://api.thealtening.com/v2/license?key=" + licenseKey);
            this.connection = (HttpURLConnection)endpoint.openConnection();
            this.connection.setRequestMethod("GET");
            this.connection.setUseCaches(false);
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            StringBuffer responseBody = new StringBuffer();
            while ((responseLine = responseReader.readLine()) != null) {
                responseBody.append(responseLine);
                responseBody.append('\r');
            }
            responseReader.close();
            return (LicenseInfo)this.gson.fromJson(responseBody.toString(), LicenseInfo.class);
        }
        catch (Exception error) {
            Vape.logThrowable(error);
            try {
                if (this.connection.getResponseCode() == 403) {
                    return null;
                }
                System.out.println("Unhandled error code: " + this.connection.getResponseCode());
            }
            catch (IOException responseError) {
                Vape.logThrowable(responseError);
            }
            return null;
        }
    }

    private static Exception preserveException(Exception error) {
        return error;
    }
}
