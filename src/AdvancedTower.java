import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * This defines an Advanced Tower which has a smaller radius of attack but
 * attacks with more damage each hit
 *
 * @author Graham Wright
 * @version 1.0
 */
public class AdvancedTower extends Tower {

    /**
    * Advanced Tower constructor
    *
    * @param p The location of the Tower
    */
    public AdvancedTower(Point p) {
        super((int) p.getX(), (int) p.getY(), 15, 60, 150);
    }

    /**
    * Advanced Tower constructor
    *
    * @param x The x coordinate of the Tower
    * @param y The y coordinate of the Tower
    */
    public AdvancedTower(int x, int y) {
        super(x, y, 4, 30, 125);
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