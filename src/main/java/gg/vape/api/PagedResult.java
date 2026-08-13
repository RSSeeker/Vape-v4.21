package gg.vape.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PagedResult<T> {
    private final boolean lastPage;
    private final long numberOfElements;
    private final long totalElements;
    private final long totalPages;
    private final List<T> content;
    private final long pageSize;

    public long getNumberOfElements() {
        return this.numberOfElements;
    }

    public boolean isLastPage() {
        return this.lastPage;
    }

    PagedResult(List<T> content, boolean lastPage, long totalPages, long totalElements, long pageSize,
                long numberOfElements) {
        this.content = content;
        this.lastPage = lastPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.numberOfElements = numberOfElements;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public List<T> getContent() {
        return this.content;
    }

    public static <T> PagedResult<T> fromJson(JsonObject pageJson, Function<JsonElement, T> elementParser) {
        ArrayList<T> content = new ArrayList<T>();
        for (JsonElement element : pageJson.get("content").getAsJsonArray()) {
            content.add(elementParser.apply(element));
        }
        return new PagedResult(content, pageJson.get("last").getAsBoolean(), pageJson.get("totalPages").getAsLong(),
                pageJson.get("totalElements").getAsLong(), pageJson.get("size").getAsLong(),
                pageJson.get("numberOfElements").getAsLong());
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public String toString() {
        return "Paged{content=" + this.content + ", last=" + this.lastPage + ", totalPages=" + this.totalPages + ", totalElements=" + this.totalElements + ", size=" + this.pageSize + ", numberOfElements=" + this.numberOfElements + '}';
    }
}
