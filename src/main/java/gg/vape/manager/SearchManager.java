package gg.vape.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import gg.vape.Vape;
import gg.vape.module.render.Search;
import gg.vape.ui.unmap.SearchBlock;
import java.util.HashSet;
import java.util.Set;

public class SearchManager {
    private final Set<SearchBlock> searchBlocks = new HashSet<SearchBlock>();

    public JsonArray toJson() {
        JsonArray result = new JsonArray();
        for (SearchBlock searchBlock : this.searchBlocks) {
            result.add((JsonElement)searchBlock.com_google_gson_JsonObject_I());
        }
        return result;
    }

    public void removeSearchBlock(SearchBlock searchBlock) {
        if (searchBlock != null) {
            Search search = Vape.INSTANCE.getModManager().getMod(Search.class);
            this.searchBlocks.remove(searchBlock);
            search.removeSearchBlock(searchBlock);
        }
    }

    public void clearSearchBlocks() {
        for (SearchBlock searchBlock : new HashSet<SearchBlock>(this.searchBlocks)) {
            this.removeSearchBlock(searchBlock);
        }
    }


    public Set<SearchBlock> getSearchBlocks() {
        return this.searchBlocks;
    }

    public void addSearchBlock(SearchBlock searchBlock) {
        this.searchBlocks.add(searchBlock);
        Search search = Vape.INSTANCE.getModManager().getMod(Search.class);
        search.addSearchBlock(searchBlock);
    }

    public void loadJson(JsonArray serializedSearchBlocks) {
        this.clearSearchBlocks();
        for (int index = 0; index < serializedSearchBlocks.size(); ++index) {
            JsonElement element = serializedSearchBlocks.get(index);
            if (!element.isJsonObject() || element.isJsonNull()) continue;
            SearchBlock searchBlock = new SearchBlock(element.getAsJsonObject());
            this.addSearchBlock(searchBlock);
        }
    }
}

