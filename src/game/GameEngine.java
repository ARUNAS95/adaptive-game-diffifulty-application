package game;

import difficulty.AdaptiveDifficultyEngine;
import difficulty.PerformanceMetric;
import difficulty.Stats;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Core game logic:
 *  - Updates bullets and enemies
 *  - Detects collisions
 *  - Tracks score / kills / shots / bypassed
 *  - Every 5 seconds asks AdaptiveDifficultyEngine to update difficulty
 *  - Resets stats when difficulty level changes
 *  - Game over when bypassed >= limit for current difficulty:
 *      EASY: 10, MEDIUM: 15, HARD: 20
 */
public class GameEngine {

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();

    private final AdaptiveDifficultyEngine difficultyEngine;

    // Gameplay stats (for HUD & difficulty)
    private int score = 0;
    private int kills = 0;
    private int bypassed = 0;
    private int shots = 0;
    private int lastScore = 0;

    private String difficulty = "EASY";

    private boolean gameOver = false;

    private long lastDifficultyUpdate;

    // 5 seconds between difficulty evaluations
    private static final long DIFFICULTY_INTERVAL_MS = 5000;

    // Where enemy is considered "bypassed" (danger line Y in pixels)
    private static final double BYPASS_LINE_Y = 750.0;

    public GameEngine(AdaptiveDifficultyEngine engine) {
        this.difficultyEngine = engine;
        this.lastDifficultyUpdate = System.currentTimeMillis();
    }

    /**
     * Called by UI when the player shoots.
     */
    public void shoot(double x, double y) {
        if (gameOver) return;
        bullets.add(new Bullet(x, y));
        shots++;
    }

    /**
     * Called every frame by AnimationTimer.
     */
    public void update() {
        if (gameOver) return;

        updateBullets();
        updateEnemies();
        checkDifficultyUpdate();
    }

    private void updateBullets() {
        Iterator<Bullet> iterator = bullets.iterator();

        while (iterator.hasNext()) {
            Bullet b = iterator.next();
            b.update();

            if (b.isOffScreen()) {
                iterator.remove();
            }
        }
    }

    private void updateEnemies() {
        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            e.update();

            boolean enemyHit = false;

            // Check if a bullet hits this enemy
            Iterator<Bullet> bIt = bullets.iterator();
            while (bIt.hasNext()) {
                Bullet b = bIt.next();

                if (isColliding(b, e)) {
                    kills++;
                    score += 10;
                    enemyHit = true;
                    bIt.remove(); // remove bullet
                    break;        // one bullet is enough
                }
            }

            if (enemyHit) {
                iterator.remove(); // remove enemy
                continue;
            }

            // Enemy crosses the danger line → bypass counted
            if (e.getY() >= BYPASS_LINE_Y) {
                bypassed++;
                score -= 2;
                iterator.remove();

                if (bypassed >= getBypassLimitForCurrentDifficulty()) {
                    gameOver = true;
                }
            }
        }
    }

    private boolean isColliding(Bullet b, Enemy e) {
        return (b.getX() >= e.getX() &&
                b.getX() <= e.getX() + e.getWidth() &&
                b.getY() >= e.getY() &&
                b.getY() <= e.getY() + e.getHeight());
    }

    /**
     * Every 5 seconds:
     *  - Compute accuracy and scoreDelta
     *  - Ask difficulty engine for next difficulty
     *  - If difficulty changed → reset kills / shots / bypassed
     */
    private void checkDifficultyUpdate() {
        long now = System.currentTimeMillis();

        if (now - lastDifficultyUpdate >= DIFFICULTY_INTERVAL_MS) {

            int scoreDelta = score - lastScore;
            lastScore = score;

            double accuracy = (shots == 0) ? 0.0 : (double) kills / shots;

            Stats stats = new Stats(
                    kills,
                    shots,
                    bypassed,
                    accuracy,
                    scoreDelta
            );

            String oldDifficulty = difficulty;
            String newDifficulty = difficultyEngine.computeNewDifficulty(stats);

            difficulty = newDifficulty;

            // If difficulty changed, reset stats so new level is evaluated fresh
            if (!newDifficulty.equals(oldDifficulty)) {
                kills = 0;
                shots = 0;
                bypassed = 0;
                // score continues (player keeps their points)
            }

            lastDifficultyUpdate = now;
        }
    }

    private int getBypassLimitForCurrentDifficulty() {
        switch (difficulty) {
            case "HARD":
                return 20;
            case "MEDIUM":
                return 15;
            case "EASY":
            default:
                return 10;
        }
    }

    // --- getters for UI / HUD / tests ---

    public int getScore() {
        return score;
    }

    public int getKills() {
        return kills;
    }

    public int getBypassed() {
        return bypassed;
    }

    public int getShots() {
        return shots;
    }

    public double getAccuracy() {
        return (shots == 0) ? 0.0 : (double) kills / shots;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    /** For tests that manually add enemies */
    public void spawnEnemy(Enemy e) {
        enemies.add(e);
    }

    /** Danger-line Y for renderers (optional if you want to use it) */
    public static double getBypassLineY() {
        return BYPASS_LINE_Y;
    }
}
