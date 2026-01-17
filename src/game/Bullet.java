package game;

/**
 * Represents a bullet shot by the player.
 * Moves upward every frame and checks for off-screen.
 */
public class Bullet {

    private double x, y;
    private double speed = 8;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Move the bullet upward */
    public void update() {
        y -= speed;
    }

    public boolean isOffScreen() {
        return y < 0;
    }

    // ----- Getters -----
    public double getX() { return x; }
    public double getY() { return y; }
}
