package test;

import game.*;
import difficulty.*;

public class TestGameEngine {

    public static void main(String[] args) throws InterruptedException {

        AdaptiveDifficultyEngine engine = new AdaptiveDifficultyEngine();
        GameEngine game = new GameEngine(engine);

        System.out.println("=== TEST STARTED ===");

        // Simulate shooting
        System.out.println("\n[PLAYER ACTION] Player fires 2 bullets");
        game.shoot(100, 500);
        game.shoot(120, 500);

        System.out.println("Shots so far = 2");
        System.out.println("Bullets list = " + game.getBullets().size());

        // Spawn initial enemies
        System.out.println("\n[GAME SETUP] Spawning 2 enemies at y=100 and y=200");
        game.spawnEnemy(new Enemy(100, 100));
        game.spawnEnemy(new Enemy(200, 200));

        // Run 10 frames
        System.out.println("\n=== FRAME UPDATES (10 FRAMES) ===\n");

        for (int i = 0; i < 10; i++) {
            // Spawn a bypassing enemy every frame
            game.spawnEnemy(new Enemy(0, 700));

            game.update();

            System.out.println("Frame " + i);
            System.out.println("   Bullets     = " + game.getBullets().size());
            System.out.println("   Enemies     = " + game.getEnemies().size());
            System.out.println("   Kills       = " + game.getKills());
            System.out.println("   Bypassed    = " + game.getBypassed());
            System.out.println("   Score       = " + game.getScore());
            System.out.println();
        }

        // Wait for difficulty update
        System.out.println("\n=== WAITING 5 SECONDS FOR DIFFICULTY UPDATE ===");
        Thread.sleep(5000);

        game.update();

        System.out.println("\n[DIFICULTY UPDATE]");
        System.out.println("New difficulty = " + game.getDifficulty());

        // Now simulate forced bypass to reach game over
        System.out.println("\n=== FORCING GAME OVER ===");

        for (int i = 0; i < 10; i++) {
            game.spawnEnemy(new Enemy(0, 700)); // instantly bypass
            game.update();
            System.out.println("Bypassed = " + game.getBypassed());
        }

        System.out.println("\n=== FINAL GAME STATE ===");
        System.out.println("Score     = " + game.getScore());
        System.out.println("Kills     = " + game.getKills());
        System.out.println("Shots     = 2");
        System.out.println("Bypassed  = " + game.getBypassed());
        System.out.println("Difficulty= " + game.getDifficulty());
        System.out.println("GameOver? = " + game.isGameOver());

        System.out.println("\n=== TEST COMPLETE ===");
    }
}
