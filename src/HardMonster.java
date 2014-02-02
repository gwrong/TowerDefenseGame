import java.awt.Color;
import java.awt.Graphics;

/**
 * A Hard Monster is a Monster that has more health than a regular monster
 * but travels at the same speed as a regular monster. It is Green.
 *
 * @author Graham Wright
 * @version 1.0
 */
public class HardMonster extends Monster {

    /**
    * Hard Monster constructor
    *
    * @param x The x coordinate of the Monster
    * @param y The y coordinate of the Monster
    */
    public HardMonster(int x, int y) {
        super(x, y, -3, 0, 5, 2000, 1);
    }

    /**
    * Draws the Monster to the screen
    *
    * @param g The Graphics object that draws to the screen
    */
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}