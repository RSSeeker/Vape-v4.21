package gg.vape.utils.datas;

import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.datas.SearchResultData;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchResultDataPool {
    public static final Stack<SearchResultData> AVAILABLE_RESULTS = new Stack();

    public static SearchResultData acquire(int x, int y, int z, int blockId, SearchBlock searchBlock, AtomicBoolean active, int metadata) {
        if (!AVAILABLE_RESULTS.isEmpty()) {
            SearchResultData result = AVAILABLE_RESULTS.pop();
            result.t(x, y, z, blockId, searchBlock, active, metadata);
            return result;
        }
        return new SearchResultData(x, y, z, blockId, searchBlock, active, metadata);
    }

    public static void release(SearchResultData result) {
        AVAILABLE_RESULTS.push(result);
    }
}
