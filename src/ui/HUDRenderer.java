package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Draws score, accuracy, bullets left, bypassed count, and difficulty.
 * Bypass max depends on difficulty:
 *  EASY: 10  MEDIUM: 15  HARD: 20
 */
public class HUDRenderer {

    public void render(GraphicsContext gc,
                       int score,
                       double accuracy,
                       int bypassed,
                       int bulletsLeft,
                       String difficulty) {

        int maxBypass;
        switch (difficulty) {
            case "HARD":
                maxBypass = 20;
                break;
            case "MEDIUM":
                maxBypass = 15;
                break;
            case "EASY":
            default:
                maxBypass = 10;
                break;
        }

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));

        gc.fillText("Score: " + score, 20, 30);
        gc.fillText("Accuracy: " + String.format("%.1f%%", accuracy * 100), 20, 55);
        gc.fillText("Bypassed: " + bypassed + " / " + maxBypass, 20, 80);
        gc.fillText("Bullets: " + bulletsLeft, 20, 105);
        gc.fillText("Difficulty: " + difficulty, 20, 130);
    }
}
