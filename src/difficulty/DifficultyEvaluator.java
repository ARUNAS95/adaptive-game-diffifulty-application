package difficulty;

import datastructures.heap.MinHeap;

/**
 * Encapsulates the rules for changing difficulty
 * based ONLY on accuracy and current difficulty.
 *
 * Uses MinHeap to track worst performance periods for more aggressive adjustments.
 *
 * Rules:
 *  - EASY   -> MEDIUM  if accuracy ≥ 80%
 *  - MEDIUM -> HARD    if accuracy ≥ 70%
 *  - HARD   -> MEDIUM  if accuracy < 55%
 *  - MEDIUM -> EASY    if accuracy < 30%
 */
public class DifficultyEvaluator {

    // MinHeap to track worst performance scores (lowest accuracy periods)
    // Used to identify when player is consistently struggling
    private final MinHeap<Double> worstPerformanceHeap = new MinHeap<>(10);

    public String evaluate(Stats stats, String currentDifficulty) {
        double accuracy = stats.accuracy;
        
        // Track worst performance periods using MinHeap
        // Lower accuracy = worse performance, so we store (1.0 - accuracy) to make it a min-heap
        // This way, the worst performances (lowest accuracy) are at the top
        double performanceScore = 1.0 - accuracy;
        if (worstPerformanceHeap.size() < worstPerformanceHeap.getCapacity()) {
            worstPerformanceHeap.insert(performanceScore);
        } else {
            // If heap is full, only add if this is worse than the current worst
            Double currentWorst = worstPerformanceHeap.peek();
            if (currentWorst != null && performanceScore < currentWorst) {
                worstPerformanceHeap.extractMin();
                worstPerformanceHeap.insert(performanceScore);
            }
        }
        
        // Check if player has been consistently struggling (multiple bad periods)
        boolean consistentlyStruggling = false;
        if (!worstPerformanceHeap.isEmpty() && worstPerformanceHeap.size() >= 3) {
            Double worst = worstPerformanceHeap.peek();
            if (worst != null && worst > 0.5) { // accuracy < 50% consistently
                consistentlyStruggling = true;
            }
        }

        switch (currentDifficulty) {
            case "EASY":
                // Player doing very well in EASY → promote to MEDIUM
                if (accuracy >= 0.80) {
                    return "MEDIUM";
                }
                return "EASY";

            case "MEDIUM":
                // Very strong in MEDIUM → promote to HARD
                if (accuracy >= 0.70) {
                    return "HARD";
                }
                // Really struggling in MEDIUM → drop to EASY
                // Use heap data to make more aggressive adjustment if consistently struggling
                if (accuracy < 0.30 || (consistentlyStruggling && accuracy < 0.40)) {
                    return "EASY";
                }
                return "MEDIUM";

            case "HARD":
                // If accuracy too low in HARD → drop to MEDIUM
                // Use heap data to make more aggressive adjustment if consistently struggling
                if (accuracy < 0.55 || (consistentlyStruggling && accuracy < 0.65)) {
                    return "MEDIUM";
                }
                return "HARD";

            default:
                return "EASY";
        }
    }
}

