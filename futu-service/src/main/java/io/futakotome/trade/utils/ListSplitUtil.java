package io.futakotome.trade.utils;

import java.util.ArrayList;
import java.util.List;

public final class ListSplitUtil {
    private static final int BATCH_SIZE = 2000;

    public static <T> List<List<T>> splitList(List<T> originalList) {
        List<List<T>> batchList = new ArrayList<>();
        int totalSize = originalList.size();
        int batchCount = (totalSize + BATCH_SIZE - 1) / BATCH_SIZE;

        for (int i = 0; i < batchCount; i++) {
            int startIndex = i * BATCH_SIZE;
            int endIndex = Math.min((i + 1) * BATCH_SIZE, totalSize);
            List<T> subList = originalList.subList(startIndex, endIndex);
            batchList.add(subList);
        }
        return batchList;
    }
}
