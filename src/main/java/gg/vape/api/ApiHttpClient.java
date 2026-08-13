package gg.vape.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.api.ApiHttpStatusException;
import gg.vape.api.ApiPermissiveX509ExtendedTrustManager;
import gg.vape.api.ApiResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class ApiHttpClient {
    private static boolean opaqueState;
    private static final DateFormat API_DATE_FORMAT;
    public static final Gson GSON;

    @Nullable
    @Contract(value="null -> null")
    public static Date parseApiDate(@Nullable String dateText) throws ParseException {
        if (dateText == null) {
            return null;
        }
        return API_DATE_FORMAT.parse(dateText);
    }

    static {
        ApiHttpClient.setOpaqueState(true);
        GSON = new GsonBuilder().serializeNulls().create();
        API_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    }

    private static <R> R request(String url, String httpMethod, Class<R> responseType, @Nullable Object body) throws Exception {
        return (R)ApiHttpClient.withConnection(url,
                connection -> ApiHttpClient.executeJsonRequest(body, httpMethod, url, responseType, connection));
    }

    private static <T> T withConnection(String url, Function<HttpURLConnection, T> requestHandler) throws Exception {
        try {
            TrustManager[] trustManagers = new TrustManager[]{new ApiPermissiveX509ExtendedTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConnection = (HttpsURLConnection)connection;
                httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                httpsConnection.setHostnameVerifier((hostname, session) -> true);
            }
            return requestHandler.apply(connection);
        }
        catch (Throwable error) {
            throw new Exception(error);
        }
    }

    private static Object readJsonResponse(Class responseType, HttpURLConnection connection) {
        try {
            return GSON.fromJson((Reader)new InputStreamReader(connection.getInputStream()), responseType);
        }
        catch (IOException error) {
            throw new RuntimeException(error);
        }
    }

    public static boolean opaquePredicate() {
        boolean state = ApiHttpClient.getOpaqueState();
        return false;
    }

    public static <R> ApiResponse<R> getApiResponse(String url, Function<JsonElement, R> dataParser) throws Exception {
        return ApiResponse.fromJson(ApiHttpClient.get(url, JsonObject.class), dataParser);
    }

    public static void setOpaqueState(boolean state) {
        opaqueState = state;
    }

    public static <R> ApiResponse<R> postApiResponse(String url, Object body, Function<JsonElement, R> dataParser) throws Exception {
        return ApiResponse.fromJson(ApiHttpClient.request(url, "POST", JsonObject.class, body), dataParser);
    }

    public static <R> R post(String url, Class<R> responseType, Object body) throws Exception {
        return ApiHttpClient.request(url, "POST", responseType, body);
    }

    public static <R> ApiResponse<R> deleteApiResponse(String url, Object body, Function<JsonElement, R> dataParser) throws Exception {
        return ApiResponse.fromJson(ApiHttpClient.request(url, "DELETE", JsonObject.class, body), dataParser);
    }

    public static <R> R delete(String url, Class<R> responseType) throws Exception {
        return ApiHttpClient.request(url, "DELETE", responseType, null);
    }

    public static boolean getOpaqueState() {
        return opaqueState;
    }

    private static Throwable preserveThrowable(Throwable error) {
        return error;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Object executeJsonRequest(Object body, String httpMethod, String url, Class responseType,
                                             HttpURLConnection connection) {
        try {
            int statusCode;
            block10: {
                String jsonBody = body != null ? GSON.toJson(body) : null;
                byte[] bodyBytes = jsonBody != null ? jsonBody.getBytes(StandardCharsets.UTF_8) : null;
                connection.setDoOutput(true);
                connection.setRequestMethod(httpMethod);
                if (jsonBody != null) {
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Content-Length", Integer.toString(bodyBytes.length));
                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    Throwable pendingError = null;
                    try {
                        output.write(bodyBytes);
                        if (output == null) break block10;
                    }
                    catch (Throwable writeError) {
                        try {
                            pendingError = writeError;
                            throw writeError;
                        }
                        catch (Throwable closeTrigger) {
                            if (output == null) throw closeTrigger;
                            if (pendingError == null) {
                                output.close();
                                throw closeTrigger;
                            }
                            try {
                                output.close();
                                throw closeTrigger;
                            }
                            catch (Throwable closeError) {
                                pendingError.addSuppressed(closeError);
                                throw closeTrigger;
                            }
                        }
                    }
                    output.close();
                }
            }
            if ((statusCode = connection.getResponseCode()) == 200) {
                return GSON.fromJson((Reader)new InputStreamReader(connection.getInputStream()), responseType);
            }
            throw new ApiHttpStatusException(url, httpMethod, statusCode);
        }
        catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public static <R> R get(String url, Class<R> responseType) throws Exception {
        return (R)ApiHttpClient.withConnection(url, connection -> ApiHttpClient.readJsonResponse(responseType, connection));
    }

    public static <R> R delete(String url, Class<R> responseType, Object body) throws Exception {
        return ApiHttpClient.request(url, "DELETE", responseType, body);
    }
}
