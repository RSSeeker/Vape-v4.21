package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.api.ApiHttpClient;
import gg.vape.api.PagedResult;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileShareInfo;
import gg.vape.config.PublicProfileUser;
import gg.vape.ui.click.component.GuiComponent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfile {
    private String shareCode;
    @Nullable
    private final Date updatedDate;
    private final String name;
    private final Map<String, Object> data;
    private final String description;
    private final Date creationDate;
    private final PagedResult<PublicProfileReview> reviews;
    private long dislikes;
    @Nullable
    private final PublicProfileShareInfo shareInfo;
    private static GuiComponent[] sharedGuiComponents;
    private final long profileId;
    private final long downloads;
    private final long version;
    @Nullable
    private PublicProfileReview viewerReview;
    private final List<String> tags;
    private long likes;
    @Nullable
    private final PublicProfileUser owner;

    public long getReviewCount() {
        return this.getReviews().getTotalElements() + (long)(this.getViewerReview() != null ? 1 : 0);
    }

    public String getDescription() {
        return this.description;
    }

    public int getApprovalPercentage() {
        long reviewCount = this.getReviewCount();
        if (reviewCount == 0L) {
            return 0;
        }
        return (int)((double)this.getLikes() / (double)reviewCount * 100.0);
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    @Nullable
    public PublicProfileReview getViewerReview() {
        return this.viewerReview;
    }

    public long getDownloads() {
        return this.downloads;
    }

    PublicProfile(long profileId, @Nullable PublicProfileUser owner, String name, String description, List<String> tags, Map<String, Object> data, @Nullable String shareCode, long version, long likes, long dislikes, long downloads, Date creationDate, @Nullable Date updatedDate, @Nullable PublicProfileReview viewerReview, PagedResult<PublicProfileReview> reviews, @Nullable PublicProfileShareInfo shareInfo) {
        this.profileId = profileId;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.data = data;
        this.shareCode = shareCode;
        this.version = version;
        this.likes = likes;
        this.dislikes = dislikes;
        this.downloads = downloads;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.viewerReview = viewerReview;
        this.reviews = reviews;
        this.shareInfo = shareInfo;
    }

    public long getDislikes() {
        return this.dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public static GuiComponent[] getSharedGuiComponents() {
        return sharedGuiComponents;
    }

    public void setViewerReview(@Nullable PublicProfileReview viewerReview) {
        this.viewerReview = viewerReview;
    }

    public String toString() {
        return "FullPublicProfile{profileId=" + this.profileId + ", owner=" + this.owner + ", name='" + this.name + '\'' + ", description='" + this.description + '\'' + ", tags=" + this.tags + ", data=" + this.data + ", version=" + this.version + ", likes=" + this.likes + ", dislikes=" + this.dislikes + ", reviews=" + this.reviews + '}';
    }

    public long getLikes() {
        return this.likes;
    }

    public long getVersion() {
        return this.version;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public PagedResult<PublicProfileReview> getReviews() {
        return this.reviews;
    }

    static {
        PublicProfile.setSharedGuiComponents(null);
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static PublicProfile fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        ArrayList<String> tags = new ArrayList<String>();
        for (JsonElement tagElement : object.get("tags").getAsJsonArray()) {
            tags.add(tagElement.getAsString());
        }
        try {
            return new PublicProfile(object.get("profileId").getAsLong(), PublicProfileUser.fromJson(object.get("owner")), object.get("name").getAsString(), object.get("description").getAsString(), tags, (Map)ApiHttpClient.GSON.fromJson(object.get("data"), Map.class), ConfigJsonUtils.getString(object, "shareCode"), object.get("version").getAsLong(), object.get("likes").getAsLong(), object.get("dislikes").getAsLong(), object.get("downloads").getAsLong(), ApiHttpClient.parseApiDate(object.get("creationDate").getAsString()), ApiHttpClient.parseApiDate(ConfigJsonUtils.getString(object, "updatedDate")), object.has("viewerReview") ? PublicProfileReview.fromJson(object.get("viewerReview")) : null, PagedResult.fromJson(object.get("reviews").getAsJsonObject(), PublicProfileReview::fromJson), PublicProfileShareInfo.fromJson(object.get("metadata")));
        }
        catch (ParseException parseException) {
            throw new RuntimeException(parseException);
        }
    }

    public long getProfileId() {
        return this.profileId;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    private static Exception preserveException(Exception error) {
        return error;
    }

    public String getUppercaseShareCode() {
        return this.shareCode.toUpperCase();
    }

    public Date getLatestDate() {
        return this.updatedDate != null ? this.updatedDate : this.creationDate;
    }

    @Nullable
    public PublicProfileShareInfo getShareInfo() {
        return this.shareInfo;
    }

    @Nullable
    public PublicProfileUser getOwner() {
        return this.owner;
    }

    public static void setSharedGuiComponents(GuiComponent[] components) {
        sharedGuiComponents = components;
    }
}
