package ui;

import difficulty.AdaptiveDifficultyEngine;
import game.Enemy;
import game.EnemySpawner;
import game.GameEngine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdaptiveGameApp extends Application {

    private static final double START_WIDTH = 800;
    private static final double START_HEIGHT = 800;

    private GameEngine engine;
    private EnemySpawner spawner;
    private GameCanvasRenderer renderer;
    private HUDRenderer hud;

    private Canvas canvas;
    private GraphicsContext gc;

    private double playerX;   // updated from mouse

    private long lastSpawnTime = 0;
    private long spawnIntervalMs = 1200;
    private String prevDifficulty = "EASY";

    @Override
    public void start(Stage stage) {

        AdaptiveDifficultyEngine diffEngine = new AdaptiveDifficultyEngine();
        engine = new GameEngine(diffEngine);
        spawner = new EnemySpawner();
        renderer = new GameCanvasRenderer();
        hud = new HUDRenderer();

        canvas = new Canvas(START_WIDTH, START_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, START_WIDTH, START_HEIGHT);

        // Make canvas responsive to window size
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());

        playerX = START_WIDTH / 2.0;

        // Mouse controls: move player & shoot
        scene.setOnMouseMoved(e -> playerX = e.getX());

        scene.setOnMouseClicked(e -> {
            if (engine.isGameOver()) return;
            if (e.getButton() == MouseButton.PRIMARY) {
                // Player is at bottom of the screen
                engine.shoot(playerX, canvas.getHeight() - 40);
            }
        });

        // Restart on R
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case R -> restartGame();
                default -> { }
            }
        });

        stage.setTitle("Adaptive Difficulty Shooter");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        // Game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameLoop();
            }
        };
        timer.start();
    }

    private void gameLoop() {
        if (!engine.isGameOver()) {
            engine.update();
            spawnEnemiesByDifficulty();
        }

        String currentDiff = engine.getDifficulty();
        if (!currentDiff.equals(prevDifficulty)) {
            renderer.triggerDifficultyFlash(currentDiff);
            prevDifficulty = currentDiff;
        }

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        // Background, enemies, bullets, danger line
        renderer.render(gc, engine, w, h);

        // Draw player on top
        drawPlayer(w, h);

        // Use engine's own accuracy calculation
        double accuracy = engine.getAccuracy();
        int bulletsLeft = engine.getBullets().size();

        hud.render(
                gc,
                engine.getScore(),
                accuracy,
                engine.getBypassed(),
                bulletsLeft,
                currentDiff
        );

        if (engine.isGameOver()) {
            drawGameOverOverlay(w, h);
        }
    }

    private void spawnEnemiesByDifficulty() {
        long now = System.currentTimeMillis();
        String diff = engine.getDifficulty();

        // Spawn interval per difficulty
        switch (diff) {
            case "HARD" -> spawnIntervalMs = 500;
            case "MEDIUM" -> spawnIntervalMs = 850;
            default -> spawnIntervalMs = 1200; // EASY
        }

        if (now - lastSpawnTime >= spawnIntervalMs) {
            Enemy e = spawner.spawn(diff, canvas.getWidth());
            engine.spawnEnemy(e);
            lastSpawnTime = now;
        }
    }

    private void drawPlayer(double w, double h) {
        double playerY = h - 40;

        // Player ship as a white triangle
        gc.setFill(Color.WHITE);
        double triW = 24, triH = 18;

        gc.fillPolygon(
                new double[]{playerX, playerX - triW / 2, playerX + triW / 2},
                new double[]{playerY, playerY + triH, playerY + triH},
                3
        );
    }

    private void drawGameOverOverlay(double w, double h) {
        gc.setFill(new Color(0, 0, 0, 0.75));
        gc.fillRect(0, 0, w, h);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(42));
        gc.fillText("GAME OVER", w / 2 - 140, h / 2 - 20);

        // Dynamic bypass limit based on final difficulty
        String diff = engine.getDifficulty();
        int maxBypass = switch (diff) {
            case "HARD" -> 20;
            case "MEDIUM" -> 15;
            case "EASY" -> 10;
            default -> 10;
        };

        gc.setFont(Font.font(20));
        gc.fillText(
                "Bypassed: " + engine.getBypassed() + " / " + maxBypass,
                w / 2 - 110,
                h / 2 + 15
        );
        gc.fillText("Final Score: " + engine.getScore(), w / 2 - 90, h / 2 + 45);
        gc.fillText("Press R to restart", w / 2 - 85, h / 2 + 75);
    }

    private void restartGame() {
        AdaptiveDifficultyEngine diffEngine = new AdaptiveDifficultyEngine();
        engine = new GameEngine(diffEngine);
        prevDifficulty = "EASY";
        lastSpawnTime = 0;
        playerX = canvas.getWidth() / 2.0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
