import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a Faster Monster which is a Monster that moves faster
 * than Regular Monsters and has more health than a Fast Monster
 *
 * @author Graham Wright
 * @version 1.0
 */
public class FasterMonster extends Monster {

    /**
    * Fast Monster constructor
    *
    * @param x The x coordinate of the Monster
    * @param y The y coordinate of the Monster
    */
    public FasterMonster(int x, int y) {
        super(x, y, -3, 0, 5, 500, 5);
    }

    /**
    * Draws the Monster to the screen
    *
    * @param g The Graphics object that draws to the screen
    */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}