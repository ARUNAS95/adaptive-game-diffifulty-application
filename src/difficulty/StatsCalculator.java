package difficulty;

import datastructures.sort.MergeSort;
import java.util.List;

/**
 * Computes aggregate statistics from a list of PerformanceMetrics.
 * This is used by the AdaptiveDifficultyEngine to determine difficulty updates.
 * 
 * Uses MergeSort to sort performance metrics before analysis, ensuring
 * we prioritize better-performing intervals when computing aggregate stats.
 */
public class StatsCalculator {

    public static Stats computeStats(List<PerformanceMetric> metrics) {
        if (metrics == null || metrics.isEmpty()) {
            return new Stats(0, 0, 0, 0.0, 0);
        }

        // Sort metrics by performance score (best first) using MergeSort
        // This helps identify trends and prioritize recent good performance
        List<PerformanceMetric> sortedMetrics = new java.util.ArrayList<>(metrics);
        MergeSort.sort(sortedMetrics);

        int totalKills = 0;
        int totalShots = 0;
        int totalBypassed = 0;
        int totalScoreDelta = 0;

        for (PerformanceMetric m : sortedMetrics) {
            totalKills += m.getKills();
            totalShots += m.getShots();
            totalBypassed += m.getBypassed();
            totalScoreDelta += m.getScoreDelta();
        }

        double accuracy =
            (totalShots == 0)
            ? 0
            : (double) totalKills / totalShots;

        return new Stats(
            totalKills,
            totalShots,
            totalBypassed,
            accuracy,
            totalScoreDelta
        );
    }
}
