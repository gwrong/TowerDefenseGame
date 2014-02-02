import java.awt.Color;
import java.awt.Graphics;

/**
 * This represents a Regular Monster
 *
 * @author Graham Wright
 * @version 1.0
 */
public class RegularMonster extends Monster {

    /**
    * Regular Monster constructor
    *
    * @param x The x coordinate of the Monster
    * @param y The y coordinate of the Monster
    */
    public RegularMonster(int x, int y) {
        super(x, y);
    }

    /**
    * Draws the Monster to the screen
    *
    * @param g The Graphics object that draws to the screen
    */
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}