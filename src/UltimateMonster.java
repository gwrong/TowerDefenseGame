import java.awt.Color;
import java.awt.Graphics;

/**
 * An Ultimate Monster is the strongest Monster of them all. It stutters as it
 * moves and has extreme health
 *
 * @author Graham Wright
 * @version 1.0
 */
public class UltimateMonster extends Monster {

    /**
    * Ultimate Monster constructor
    *
    * @param x The x coordinate of the Monster
    * @param y The y coordinate of the Monster
    */
    public UltimateMonster(int x, int y) {
        super(x, y, -3, 0, 75, 10000, 1);
    }

    /**
    * Draws the Monster to the screen
    *
    * @param g The Graphics object that draws to the screen
    */
    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval(getX() - 25, getY() - 25, 50, 50);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}