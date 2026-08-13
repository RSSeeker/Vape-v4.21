package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.api.ApiHttpClient;
import java.text.ParseException;
import java.util.Date;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfileReviewResponse {
    private final long id;
    private final Date updatedDate;
    private final String response;
    private final Date createdDate;

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static PublicProfileReviewResponse fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        try {
            return new PublicProfileReviewResponse(object.get("id").getAsLong(), ApiHttpClient.parseApiDate(object.get("createdDate").getAsString()), ApiHttpClient.parseApiDate(object.get("updatedDate").getAsString()), object.get("response").getAsString());
        }
        catch (ParseException parseException) {
            throw new RuntimeException(parseException);
        }
    }

    PublicProfileReviewResponse(long id, Date createdDate, Date updatedDate, String response) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.response = response;
    }

    public String getResponse() {
        return this.response;
    }

    public long getId() {
        return this.id;
    }

    public String toString() {
        return "PublicProfileReviewResponse{id=" + this.id + ", date=" + this.createdDate + ", response='" + this.response + '\'' + '}';
    }

    private static ParseException preserveParseException(ParseException error) {
        return error;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }
}
