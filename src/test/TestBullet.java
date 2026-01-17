package test;

import game.Bullet;

public class TestBullet {
    public static void main(String[] args) {
        Bullet b = new Bullet(100, 500);

        for (int i = 0; i < 5; i++) {
            b.update();
            System.out.println("Bullet y: " + b.getY());
        }
    }
}
