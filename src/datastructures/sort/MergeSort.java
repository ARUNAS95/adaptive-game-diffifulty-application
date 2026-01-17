package datastructures.sort;

import difficulty.PerformanceMetric;
import java.util.List;

/**
 * MergeSort implementation to sort performance metrics.
 */
public class MergeSort {

    public static void sort(List<PerformanceMetric> list) {
        if (list.size() <= 1) return;
        mergeSort(list, 0, list.size() - 1);
    }

    private static void mergeSort(List<PerformanceMetric> arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    private static void merge(List<PerformanceMetric> arr, int left, int mid, int right) {
        PerformanceMetric[] temp = new PerformanceMetric[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            int scoreI = score(arr.get(i));
            int scoreJ = score(arr.get(j));

            if (scoreI <= scoreJ) temp[k++] = arr.get(i++);
            else temp[k++] = arr.get(j++);
        }

        while (i <= mid) temp[k++] = arr.get(i++);
        while (j <= right) temp[k++] = arr.get(j++);

        for (int n = 0; n < temp.length; n++)
            arr.set(left + n, temp[n]);
    }

    /** Simple derived score for sorting */
    private static int score(PerformanceMetric m) {
        return m.getKills() * 10 + (int)(m.getShots() * 0.1) - m.getBypassed() * 20;
    }
}
