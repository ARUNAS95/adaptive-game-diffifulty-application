package difficulty;

import datastructures.bst.BalancedBST;
import datastructures.stack.ArrayStack;
import datastructures.queue.CircularQueue;

import java.util.List;

/**
 * AdaptiveDifficultyEngine decides whether the next difficulty level
 * should be EASY, MEDIUM or HARD based on recent performance.
 *
 * Now uses:
 *  - Stats (kills, shots, bypassed, accuracy, scoreDelta)
 *  - DifficultyEvaluator to apply the accuracy rules
 *  - BalancedBST to archive performance scores
 *  - ArrayStack to store difficulty history (for undo)
 *  - CircularQueue to maintain sliding window of recent PerformanceMetrics
 */
public class AdaptiveDifficultyEngine {

    // Store performance scores over time (for analysis / report)
    private final BalancedBST<Integer> performanceHistory = new BalancedBST<>();

    // Stack to support undo() feature
    private final ArrayStack<String> difficultyHistory = new ArrayStack<>(30);

    // Circular queue for sliding window of recent performance metrics (last 15 intervals)
    private final CircularQueue<PerformanceMetric> recentMetrics = new CircularQueue<>(15);

    // Evaluator with your accuracy-based rules
    private final DifficultyEvaluator evaluator = new DifficultyEvaluator();

    // Current difficulty used by GameEngine + HUD
    private String currentDifficulty = "EASY";

    public AdaptiveDifficultyEngine() {
        // Seed history with initial difficulty
        difficultyHistory.push(currentDifficulty);
    }

    /**
     * Original API: used by your TestDifficultyEngine.
     * It converts PerformanceMetric list → Stats → internal update.
     */
    public String computeNewDifficulty(List<PerformanceMetric> metrics) {
        if (metrics == null || metrics.isEmpty()) {
            return currentDifficulty;
        }
        
        // Add metrics to circular queue for sliding window
        for (PerformanceMetric metric : metrics) {
            recentMetrics.enqueue(metric);
        }
        
        // Use only the most recent 3-5 metrics for difficulty evaluation
        // This ensures recent poor performance immediately affects difficulty
        List<PerformanceMetric> allMetrics = getMetricsFromQueue();
        List<PerformanceMetric> recentMetricsForEvaluation = getRecentMetrics(allMetrics, 5);
        
        Stats stats = StatsCalculator.computeStats(recentMetricsForEvaluation);
        return internalUpdate(stats);
    }

    /**
     * Overloaded API: used directly by GameEngine with already computed Stats.
     * Creates a PerformanceMetric from Stats and adds it to the sliding window.
     * Then computes aggregate stats from the most recent metrics for difficulty evaluation.
     */
    public String computeNewDifficulty(Stats stats) {
        if (stats == null) {
            return currentDifficulty;
        }
        
        // Create PerformanceMetric from Stats and add to circular queue
        PerformanceMetric metric = new PerformanceMetric(
            stats.kills,
            stats.shots,
            stats.bypassed,
            stats.scoreDelta
        );
        recentMetrics.enqueue(metric);
        
        // Use only the most recent 3-5 metrics for difficulty evaluation
        // This ensures recent poor performance immediately affects difficulty
        // rather than being masked by old good performance
        List<PerformanceMetric> allMetrics = getMetricsFromQueue();
        List<PerformanceMetric> recentMetricsForEvaluation = getRecentMetrics(allMetrics, 5);
        Stats aggregateStats = StatsCalculator.computeStats(recentMetricsForEvaluation);
        
        // Use aggregate stats for evaluation, but keep original stats for BST storage
        return internalUpdate(aggregateStats, stats);
    }
    
    /**
     * Helper method to extract all metrics from the circular queue.
     */
    private List<PerformanceMetric> getMetricsFromQueue() {
        return recentMetrics.getAllItems();
    }
    
    /**
     * Get the most recent N metrics from the list (last N items).
     * Used for difficulty evaluation to ensure recent performance changes
     * are immediately reflected rather than being averaged over a long window.
     */
    private List<PerformanceMetric> getRecentMetrics(List<PerformanceMetric> allMetrics, int count) {
        if (allMetrics == null || allMetrics.isEmpty()) {
            return allMetrics;
        }
        
        int size = allMetrics.size();
        if (size <= count) {
            return allMetrics;
        }
        
        // Return the last N metrics (most recent)
        return allMetrics.subList(size - count, size);
    }

    /**
     * Core logic: update performance history, evaluate difficulty,
     * maintain difficulty history.
     */
    private String internalUpdate(Stats stats) {
        return internalUpdate(stats, stats);
    }
    
    /**
     * Core logic with separate stats for evaluation and BST storage.
     */
    private String internalUpdate(Stats evalStats, Stats bstStats) {
        // Store performance score in BST (for analysis) using original stats
        int performanceScore = computeScore(bstStats);
        performanceHistory.insert(performanceScore);

        // Decide next difficulty based on accuracy + currentDifficulty using evaluation stats
        String next = evaluator.evaluate(evalStats, currentDifficulty);

        if (!next.equals(currentDifficulty)) {
            currentDifficulty = next;
            difficultyHistory.push(currentDifficulty);
        }

        return currentDifficulty;
    }

    public String getCurrentDifficulty() {
        return currentDifficulty;
    }

    /**
     * Undo last difficulty change.
     * Returns the difficulty after undo.
     */
    public String undoDifficulty() {

        // Remove the current entry
        if (!difficultyHistory.isEmpty()) {
            difficultyHistory.pop();
        }

        // If nothing left, reset to EASY
        if (difficultyHistory.isEmpty()) {
            currentDifficulty = "EASY";
            difficultyHistory.push(currentDifficulty);
        } else {
            // Get last value by pop + push (no peek needed)
            String previous = difficultyHistory.pop();
            currentDifficulty = previous;
            difficultyHistory.push(previous);
        }

        return currentDifficulty;
    }

    /**
     * Compute a scalar "performance score" from Stats.
     * Used only to populate the BalancedBST for analysis.
     */
    private int computeScore(Stats stats) {
        int killsWeight = 10;
        int bypassPenalty = 5;
        int accuracyBonus = (int) (stats.accuracy * 100); // 0..100

        return (stats.kills * killsWeight)
                + stats.scoreDelta
                + accuracyBonus
                - (stats.bypassed * bypassPenalty);
    }
}

