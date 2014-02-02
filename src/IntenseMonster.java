import java.awt.Color;
import java.awt.Graphics;

/**
 * An Intense Monster is even stronger than a Hard Monster and moves at the
 * same pace as a Regular Monster
 *
 * @author Graham Wright
 * @version 1.0
 */
public class IntenseMonster extends Monster {

    /**
    * Intense Monster constructor
    *
    * @param x The x coordinate of the Monster
    * @param y The y coordinate of the Monster
    */
    public IntenseMonster(int x, int y) {
        super(x, y, -3, 0, 10, 4000, 1);
    }

    /**
    * Draws the Monster to the screen
    *
    * @param g The Graphics object that draws to the screen
    */
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval(getX() - 15, getY() - 15, 30, 30);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}