package test;

import game.Bullet;
import game.CollisionHandler;

import java.util.ArrayList;
import java.util.List;

public class TestCollision {
    public static void main(String[] args) {

        List<Bullet> bullets = new ArrayList<>();
        List<DummyEnemy> enemies = new ArrayList<>();

        bullets.add(new Bullet(100, 100));
        enemies.add(new DummyEnemy(100, 100));

        // This won't compile yet because CollisionHandler expects Enemy.
        // When your teammate creates Enemy.java, everything will work.
    }
}
