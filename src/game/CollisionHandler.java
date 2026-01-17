package game;

import java.util.Iterator;
import java.util.List;

public class CollisionHandler {

    public static int handleBulletEnemyCollisions(
            List<Bullet> bullets,
            List<Enemy> enemies) {

        int kills = 0;

        Iterator<Bullet> bIt = bullets.iterator();

        while (bIt.hasNext()) {
            Bullet b = bIt.next();

            Iterator<Enemy> eIt = enemies.iterator();
            while (eIt.hasNext()) {
                Enemy e = eIt.next();

                if (isColliding(b, e)) {
                    kills++;
                    bIt.remove();   // remove bullet
                    eIt.remove();   // remove enemy
                    break;
                }
            }
        }

        return kills;
    }

    private static boolean isColliding(Bullet b, Enemy e) {
        double dx = b.getX() - e.getX();
        double dy = b.getY() - e.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        return dist < 20; // simple circular hitbox
    }
}
