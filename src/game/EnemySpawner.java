package game;

/**
 * Spawns enemies at difficulty-dependent speeds.
 * This class just creates new Enemy objects positioned at random X.
 *
 * Smooth speed scaling:
 *  EASY   = 1.2
 *  MEDIUM = 1.7
 *  HARD   = 2.3
 */
public class EnemySpawner {

    public Enemy spawn(String difficulty, double canvasWidth) {
        double x = Math.random() * (canvasWidth - 40);
        double y = -40;
        double speed;

        switch (difficulty) {
            case "HARD":
                speed = 2.3;
                break;
            case "MEDIUM":
                speed = 1.7;
                break;
            default: // EASY
                speed = 1.2;
                break;
        }

        return new Enemy(x, y, speed);
    }
}
