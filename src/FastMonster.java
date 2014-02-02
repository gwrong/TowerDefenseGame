import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a Fast Monster which is a Monster that moves faster
 * than Regular Monsters
 *
 * @author Graham Wright
 * @version 1.0
 */
public class FastMonster extends Monster {

    /**
    * Fast Monster constructor
    *
    * @param x The x coordinate of the Monster
    * @param y The y coordinate of the Monster
    */
    public FastMonster(int x, int y) {
        super(x, y, -3, 0, 5, 300, 3);
    }

    /**
    * Draws the Monster to the screen
    *
    * @param g The Graphics object that draws to the screen
    */
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}