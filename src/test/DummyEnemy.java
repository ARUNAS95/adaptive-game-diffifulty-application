package test;

public class DummyEnemy {

    private double x, y;
    private boolean destroyed = false;

    public DummyEnemy(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isDestroyed() { return destroyed; }
    public void setDestroyed(boolean d) { destroyed = d; }
}
