package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiHttpClient;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReviewResponse;
import gg.vape.config.PublicProfileUser;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.none.ClientSettings;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfileReview {
    private final PublicProfileUser commenter;
    @Nullable
    private Boolean read;
    static final boolean ASSERTIONS_DISABLED = !PublicProfileReview.class.desiredAssertionStatus();
    private final String message;
    private final long commentId;
    private final Date updatedDate;
    private final boolean latest;
    private final Date createdDate;
    private final boolean liked;
    private final long version;
    @Nullable
    private PublicProfileReviewResponse response;
    private final long profileId;

    public boolean isLatest() {
        return this.latest;
    }

    public String getMessage() {
        return this.message;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static PublicProfileReview fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        try {
            return new PublicProfileReview(object.get("commentId").getAsLong(), object.get("profileId").getAsLong(), ApiHttpClient.parseApiDate(object.get("createdDate").getAsString()), ApiHttpClient.parseApiDate(object.get("updatedDate").getAsString()), PublicProfileUser.fromJson(object.get("commenter")), object.get("message").getAsString(), object.get("liked").getAsBoolean(), object.get("version").getAsLong(), object.get("latest").getAsBoolean(), ConfigJsonUtils.getBoolean(object, "read"), PublicProfileReviewResponse.fromJson(object.get("response")));
        }
        catch (ParseException parseException) {
            throw new RuntimeException(parseException);
        }
    }

    public boolean isUnread() {
        return this.read != null && this.read == false;
    }

    @Nullable
    public PublicProfileReviewResponse getResponse() {
        return this.response;
    }

    public void setResponse(@Nullable PublicProfileReviewResponse response) {
        this.response = response;
    }

    public boolean isLiked() {
        return this.liked;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public String toString() {
        return "PublicProfileReview{commentId=" + this.commentId + ", profileId=" + this.profileId + ", date=" + this.createdDate + ", commenter=" + this.commenter + ", message='" + this.message + '\'' + ", liked=" + this.liked + ", version=" + this.version + ", latest=" + this.latest + ", response=" + this.response + '}';
    }

    public boolean isRead() {
        return this.read != null && this.read != false;
    }

    public long getVersion() {
        return this.version;
    }

    private static Exception preserveException(Exception error) {
        return error;
    }

    public long getCommentId() {
        return this.commentId;
    }

    @Nullable
    public Boolean getRead() {
        return this.read;
    }

    PublicProfileReview(long commentId, long profileId, Date createdDate, Date updatedDate, PublicProfileUser commenter, String message, boolean liked, long version, boolean latest, @Nullable Boolean read, @Nullable PublicProfileReviewResponse response) {
        this.commentId = commentId;
        this.profileId = profileId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.commenter = commenter;
        this.message = message;
        this.liked = liked;
        this.version = version;
        this.latest = latest;
        this.read = read;
        this.response = response;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public void delete(PublicProfile publicProfile, Runnable completionCallback) {
        ApiServices.getInstance().getPublicProfileApi().deleteReview(this).whenCompleteAsync((apiResponse, error) -> this.handleDeleteResponse(publicProfile, completionCallback, apiResponse, error), (Executor)ClientSettings.UI_EXECUTOR);
    }

    public PublicProfileUser getCommenter() {
        return this.commenter;
    }

    private void handleDeleteResponse(PublicProfile publicProfile, Runnable completionCallback, ApiResponse apiResponse, Throwable error) {
        if (error != null) {
            Vape.logThrowable(error);
            return;
        }
        if (!apiResponse.isSuccessful()) {
            Vape.debugLog("Failed to delete review: " + apiResponse.getError());
            PublicProfileManager.showWarning("Failed to delete review: " + apiResponse.getError());
            return;
        }
        if (!ASSERTIONS_DISABLED && apiResponse.getData() == null) {
            throw new AssertionError();
        }
        publicProfile.setViewerReview(null);
        if (this.liked) {
            publicProfile.setLikes(publicProfile.getLikes() - 1L);
        } else {
            publicProfile.setDislikes(publicProfile.getDislikes() - 1L);
        }
        if (completionCallback != null) {
            completionCallback.run();
        }
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }
}
