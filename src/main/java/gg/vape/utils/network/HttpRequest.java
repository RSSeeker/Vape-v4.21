package gg.vape.utils.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String W;
    private final String z;
    private Map<Object, Object> F = new HashMap<Object, Object>();
    private HttpURLConnection P;
    private final String u;

    public HttpRequest x(String string) {
        this.W = string;
        return this;
    }

    public String h() {
        try {
            String string;
            Closeable closeable;
            HttpURLConnection httpURLConnection = this.e();
            if (this.z.equalsIgnoreCase("POST")) {
                if (httpURLConnection == null) {
                    this.z();
                }
                closeable = new OutputStreamWriter(httpURLConnection.getOutputStream());
                ((Writer)closeable).write(this.W);
                ((OutputStreamWriter)closeable).flush();
                ((OutputStreamWriter)closeable).close();
            }
            if (httpURLConnection.getResponseCode() != 200) {
                return null;
            }
            closeable = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((string = ((BufferedReader)closeable).readLine()) != null) {
                stringBuilder.append(string);
            }
            ((BufferedReader)closeable).close();
            return stringBuilder.toString();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    public HttpURLConnection e() throws IOException {
        if (this.P == null) {
            this.z();
        }
        return this.P;
    }

    public HttpRequest L(String string) {
        return this.e("Accept", string);
    }

    public HttpRequest R(String string) {
        return this.e("Authorization", string);
    }

    public JsonObject P() {
        return new JsonParser().parse(this.h()).getAsJsonObject();
    }

    public HttpRequest(String string, String string2) {
        this.u = string;
        this.z = string2;
    }

    private static IOException a(IOException iOException) {
        return iOException;
    }

    public void z() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(this.u).openConnection();
        httpURLConnection.setRequestMethod(this.z);
        if (this.z.equalsIgnoreCase("POST")) {
            httpURLConnection.setDoOutput(true);
        }
        httpURLConnection.setDoInput(true);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        for (Map.Entry<Object, Object> entry : this.F.entrySet()) {
            httpURLConnection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        this.P = httpURLConnection;
    }

    public HttpRequest e(Object object, Object object2) {
        this.F.put(object, object2);
        return this;
    }

    public HttpRequest D(String string, String string2) {
        return this.R(string + " " + string2);
    }
}

