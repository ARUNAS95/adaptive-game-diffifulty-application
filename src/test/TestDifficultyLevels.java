package test;

import difficulty.AdaptiveDifficultyEngine;
import difficulty.PerformanceMetric;
import java.util.ArrayList;
import java.util.List;

public class TestDifficultyLevels {

    public static void main(String[] args) {

        testEasyScenario();
        testMediumScenario();
        testHardScenario();
    }

    private static void testEasyScenario() {
        System.out.println("\n=== TEST EASY SCENARIO ===");

        AdaptiveDifficultyEngine engine = new AdaptiveDifficultyEngine();

        List<PerformanceMetric> metrics = new ArrayList<>();
        metrics.add(new PerformanceMetric(0, 5, 4, -20)); // bad
        metrics.add(new PerformanceMetric(1, 5, 2, -10)); // also bad

        String difficulty = engine.computeNewDifficulty(metrics);
        System.out.println("Expected: EASY");
        System.out.println("Actual:   " + difficulty);
    }

    private static void testMediumScenario() {
        System.out.println("\n=== TEST MEDIUM SCENARIO ===");

        AdaptiveDifficultyEngine engine = new AdaptiveDifficultyEngine();

        List<PerformanceMetric> metrics = new ArrayList<>();
        metrics.add(new PerformanceMetric(2, 5, 1, 10)); // moderate player
        metrics.add(new PerformanceMetric(1, 3, 0, 5));  // stable

        String difficulty = engine.computeNewDifficulty(metrics);
        System.out.println("Expected: MEDIUM");
        System.out.println("Actual:   " + difficulty);
    }


    private static void testHardScenario() {
        System.out.println("\n=== TEST HARD SCENARIO ===");

        AdaptiveDifficultyEngine engine = new AdaptiveDifficultyEngine();

        List<PerformanceMetric> metrics = new ArrayList<>();
        metrics.add(new PerformanceMetric(4, 5, 0, 60)); // 80% accuracy, no bypass
        metrics.add(new PerformanceMetric(3, 4, 0, 40)); // strong performance

        String difficulty = engine.computeNewDifficulty(metrics);
        System.out.println("Expected: HARD");
        System.out.println("Actual:   " + difficulty);
    }
}
