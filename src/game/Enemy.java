package game;

public class Enemy {

    private double x, y;
    private double speed;
    private double width = 35;
    private double height = 35;

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
        this.speed = 3;
    }

    public Enemy(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public boolean isOffScreen() {
        return y > 600;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
