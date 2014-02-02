import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A Basic Tower deals a basic amount of damage with a normal radius of
 * attack
 *
 * @author Graham Wright
 * @version 1.0
 */
public class BasicTower extends Tower {

    /**
    * Basic Tower constructor
    *
    * @param p The location of the Tower
    */
    public BasicTower(Point p) {
        super(p);
    }

    /**
    * Basic Tower constructor
    *
    * @param x The x coordinate of the Tower
    * @param y The y coordinate of the Tower
    */
    public BasicTower(int x, int y) {
        super(x, y);
    }

    /**
    * Draws the Tower's radius to the GUI
    *
    * @param g The Graphics object dealing with drawing the Tower
    */
    public void drawReach(Graphics g) {
        int r = getRadius();
        g.setColor(new Color(232, 227, 227));
        g.fillOval(getX() - r + 12, getY() - r + 12, r * 2, r * 2);
    }

    /**
    * Draws the Tower to the GUI
    *
    * @param g The Graphics object dealing with drawing the Tower
    */
    public void drawTower(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY(), (int) drawRectangle.getHeight(),
                                                (int) drawRectangle.getWidth());
    }
}