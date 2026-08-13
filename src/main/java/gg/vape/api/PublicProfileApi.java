package gg.vape.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.api.ApiAccessTokenProvider;
import gg.vape.api.ApiHttpClient;
import gg.vape.api.ApiResponse;
import gg.vape.api.PagedResult;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileReviewResponse;
import gg.vape.config.PublicProfileShareInfo;
import gg.vape.config.PublicProfileSortMode;
import gg.vape.config.PublicProfileSummary;
import gg.vape.sync.RemoteProfileData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Nullable;

public class PublicProfileApi {
    private final String baseUrl;
    private static boolean opaqueState;

    private ApiResponse requestDelayedReviewPage(String accessToken, long profileId, long page) {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException ignored) {
            // empty catch block
        }
        try {
            return ApiHttpClient.getApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/view/" + profileId + "/" + page, PublicProfileApi::parseReviewPageWithApiParser);
        }
        catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public CompletableFuture<ApiResponse<RemoteProfileData>> downloadProfile(long profileId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestProfileDownload(accessToken, profileId));
    }

    private ApiResponse requestShareCodeRegeneration(long profileId, String accessToken) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("profileId", (Number)profileId);
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/regenerate/sharecode", payload, PublicProfileShareInfo::fromJson);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<Boolean>> markAllReviewsRead(PublicProfile profile) {
        return this.markAllReviewsRead(profile.getProfileId());
    }

    public CompletableFuture<ApiResponse<PublicProfile>> createProfile(JsonObject payload) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestProfileCreation(accessToken, payload));
    }

    public CompletableFuture<ApiResponse<Boolean>> deleteReviewResponse(PublicProfileReviewResponse response) {
        return this.deleteReviewResponse(response.getId());
    }

    public CompletableFuture<ApiResponse<Boolean>> deleteProfile(long profileId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestProfileDeletion(accessToken, profileId));
    }

    private ApiResponse requestMarkAllReviewsRead(long profileId, String accessToken) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("profileId", (Number)profileId);
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/mark/all", payload, JsonElement::getAsBoolean);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static List parseTags(JsonElement dataElement) {
        ArrayList<String> tags = new ArrayList<String>();
        JsonArray tagsJson = dataElement.getAsJsonArray();
        for (JsonElement tagElement : tagsJson) {
            tags.add(tagElement.getAsString());
        }
        return tags;
    }

    private static PublicProfile parsePublicProfile(JsonElement dataElement) {
        return PublicProfile.fromJson((JsonElement)dataElement.getAsJsonObject());
    }

    public CompletableFuture<ApiResponse<PagedResult<PublicProfileReview>>> getReviewPage(long profileId, long page) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestReviewPage(accessToken, profileId, page));
    }

    public CompletableFuture<ApiResponse<Boolean>> reportReviewResponse(long responseId, String reason) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestReviewResponseReport(reason, accessToken, responseId));
    }

    public CompletableFuture<ApiResponse<Boolean>> markAllReviewsRead(long profileId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestMarkAllReviewsRead(profileId, accessToken));
    }

    public CompletableFuture<ApiResponse<PublicProfileReview>> createReview(PublicProfile profile, boolean liked,
                                                                           @Nullable String reason) {
        return this.createReview(profile.getProfileId(), liked, reason);
    }

    public CompletableFuture<ApiResponse<Boolean>> markReviewsRead(long profileId, List<Long> reviewIds) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestMarkReviewsRead(profileId, reviewIds, accessToken));
    }

    private ApiResponse requestProfileDeletion(String accessToken, long profileId) {
        try {
            return ApiHttpClient.deleteApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/" + profileId + "/delete", null, JsonElement::getAsBoolean);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<PublicProfileReviewResponse>> respondToReview(PublicProfileReview review,
                                                                                       String message) {
        return this.respondToReview(review.getCommentId(), message);
    }

    public CompletableFuture<ApiResponse<Boolean>> deleteReview(PublicProfileReview review) {
        return this.deleteReview(review.getCommentId());
    }

    private ApiResponse requestReviewPage(String accessToken, long profileId, long page) {
        try {
            return ApiHttpClient.getApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/view/" + profileId + "/" + page, PublicProfileApi::parseReviewPage);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<RemoteProfileData>> downloadProfileUpdate(long profileId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestProfileUpdate(accessToken, profileId));
    }

    private ApiResponse requestProfileCreation(String accessToken, JsonObject payload) {
        try {
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/create", payload, PublicProfileApi::parsePublicProfile);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<PagedResult<PublicProfileReview>>> getDelayedReviewPage(long profileId, long page) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestDelayedReviewPage(accessToken, profileId, page));
    }

    public CompletableFuture<ApiResponse<Boolean>> deleteReviewResponse(long responseId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestReviewResponseDeletion(accessToken, responseId));
    }

    private ApiResponse requestReviewResponseReport(String reason, String accessToken, long responseId) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("reason", reason);
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/reports/create/response/" + responseId, payload, JsonElement::getAsBoolean);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<PagedResult<PublicProfileReview>>> getDelayedReviewPage(PublicProfile profile,
                                                                                                 long page) {
        return this.getDelayedReviewPage(profile.getProfileId(), page);
    }

    public CompletableFuture<ApiResponse<PublicProfile>> viewProfile(long profileId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestProfileView(accessToken, profileId));
    }

    public PublicProfileApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private ApiResponse requestProfileView(String accessToken, long profileId) {
        try {
            return ApiHttpClient.getApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/" + profileId + "/view", PublicProfileApi::parsePublicProfile);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static PagedResult parseReviewPageWithApiParser(JsonElement dataElement) {
        return PagedResult.fromJson(dataElement.getAsJsonObject(), PublicProfileApi::parseReview);
    }

    private ApiResponse requestReviewReport(String reason, String accessToken, long reviewId) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("reason", reason);
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/reports/create/review/" + reviewId, payload, JsonElement::getAsBoolean);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static PagedResult parseProfileSummaryPage(JsonElement dataElement) {
        return PagedResult.fromJson(dataElement.getAsJsonObject(), PublicProfileApi::parseProfileSummary);
    }

    public CompletableFuture<ApiResponse<PublicProfileReviewResponse>> respondToReview(long reviewId, String message) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestReviewResponseCreation(message, accessToken, reviewId));
    }

    public CompletableFuture<ApiResponse<Boolean>> deleteReview(long reviewId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestReviewDeletion(accessToken, reviewId));
    }

    static {
        PublicProfileApi.setOpaqueState(true);
    }

    public CompletableFuture<ApiResponse<PublicProfileReview>> createReview(long profileId, boolean liked,
                                                                           @Nullable String reason) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestReviewCreation(profileId, reason, liked, accessToken));
    }

    private ApiResponse requestReviewResponseCreation(String message, String accessToken, long reviewId) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("message", message);
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/respond/" + reviewId, payload, PublicProfileReviewResponse::fromJson);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<Boolean>> markReviewsRead(PublicProfile profile, List<Long> reviewIds) {
        return this.markReviewsRead(profile.getProfileId(), reviewIds);
    }

    public CompletableFuture<ApiResponse<PublicProfileShareInfo>> regenerateShareCode(long profileId) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestShareCodeRegeneration(profileId, accessToken));
    }

    public static boolean getOpaqueState() {
        return opaqueState;
    }

    private ApiResponse requestProfileDownload(String accessToken, long profileId) {
        try {
            return ApiHttpClient.getApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/" + profileId + "/download", RemoteProfileData::fromJson);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ApiResponse requestProfileEdit(String accessToken, JsonObject payload) {
        try {
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/edit", payload, PublicProfileApi::parsePublicProfile);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ApiResponse requestReviewResponseDeletion(String accessToken, long responseId) {
        try {
            return ApiHttpClient.deleteApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/delete/response/" + responseId, null, JsonElement::getAsBoolean);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ApiResponse requestMarkReviewsRead(long profileId, List<Long> reviewIds, String accessToken) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("profileId", (Number)profileId);
            JsonArray reviewIdsJson = new JsonArray();
            Iterator<Long> iterator = reviewIds.iterator();
            while (iterator.hasNext()) {
                long reviewId = iterator.next();
                reviewIdsJson.add((JsonElement)new JsonPrimitive((Number)reviewId));
            }
            payload.add("reviewIds", (JsonElement)reviewIdsJson);
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/mark", payload, JsonElement::getAsBoolean);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<PagedResult<PublicProfileSummary>>> listProfiles(PublicProfileSortMode sortMode,
                                                                                          long page,
                                                                                          @Nullable String search,
                                                                                          @Nullable List<String> tags) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestProfileList(tags, sortMode, page, search, accessToken));
    }

    public static void setOpaqueState(boolean state) {
        opaqueState = state;
    }

    private static PublicProfileReview parseReview(JsonElement dataElement) {
        return PublicProfileReview.fromJson((JsonElement)dataElement.getAsJsonObject());
    }

    private ApiResponse requestReviewCreation(long profileId, String reason, boolean liked, String accessToken) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("profileId", (Number)profileId);
            payload.addProperty("reason", reason);
            payload.addProperty("liked", Boolean.valueOf(liked));
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/create", payload, PublicProfileReview::fromJson);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<Boolean>> reportReview(long reviewId, String reason) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestReviewReport(reason, accessToken, reviewId));
    }

    public static boolean opaquePredicate() {
        boolean state = PublicProfileApi.getOpaqueState();
        return false;
    }

    private ApiResponse requestMostPopularTags(String accessToken) {
        try {
            return ApiHttpClient.getApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/tags", PublicProfileApi::parseTags);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ApiResponse requestProfileUpdate(String accessToken, long profileId) {
        try {
            return ApiHttpClient.getApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/" + profileId + "/update", RemoteProfileData::fromJson);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public CompletableFuture<ApiResponse<List<String>>> getMostPopularTags() {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestMostPopularTags(accessToken));
    }

    private ApiResponse requestReviewDeletion(String accessToken, long reviewId) {
        try {
            return ApiHttpClient.deleteApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/review/delete/" + reviewId, null, JsonElement::getAsBoolean);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static PagedResult parseReviewPage(JsonElement dataElement) {
        return PagedResult.fromJson(dataElement.getAsJsonObject(), PublicProfileReview::fromJson);
    }

    private static PublicProfileSummary parseProfileSummary(JsonElement dataElement) {
        return PublicProfileSummary.fromJson(dataElement.getAsJsonObject());
    }

    public CompletableFuture<ApiResponse<PublicProfile>> editProfile(JsonObject payload) {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestProfileEdit(accessToken, payload));
    }

    private ApiResponse requestProfileList(List<String> tags, PublicProfileSortMode sortMode, long page,
                                           String search, String accessToken) {
        try {
            JsonArray tagsJson = new JsonArray();
            if (tags != null) {
                for (String tag : tags) {
                    tagsJson.add((JsonElement)new JsonPrimitive(tag));
                }
            }
            JsonObject payload = new JsonObject();
            payload.addProperty("mode", sortMode.getQueryValue());
            payload.addProperty("page", (Number)page);
            payload.addProperty("search", search);
            payload.add("tags", (JsonElement)tagsJson);
            return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/public/list", payload, PublicProfileApi::parseProfileSummaryPage);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
