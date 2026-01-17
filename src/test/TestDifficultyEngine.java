package test;

import difficulty.AdaptiveDifficultyEngine;
import difficulty.PerformanceMetric;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple non-JavaFX test for difficulty engine.
 */
public class TestDifficultyEngine {

    public static void main(String[] args) {

        AdaptiveDifficultyEngine engine = new AdaptiveDifficultyEngine();

        // Create some fake metrics similar to game play
        List<PerformanceMetric> metrics = new ArrayList<>();
        metrics.add(new PerformanceMetric(5, 6, 0, 100));  // good performance
        metrics.add(new PerformanceMetric(3, 5, 0, 80));

        String level = engine.computeNewDifficulty(metrics);
        System.out.println("Difficulty = " + level);

        // Test undo
//        String previous = engine.undoDifficulty();
//        System.out.println("Undo difficulty = " + previous);
    }
}
