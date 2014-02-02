import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class represents a Tower in the Tower Defense Game
 * A tower can be placed by the player and it can shoot at Monsters when
 * in range
 *
 * @author Graham Wright
 * @version 1.0
 */
public abstract class Tower {

    public static final int BASIC_TOWER = 0;
    public static final int ADVANCED_TOWER = 1;
    private final int towerHeight = 24;
    private final int towerWidth = 24;
    private int x, y, damage, radius, cost;
    protected Rectangle drawRectangle;

    /**
    * The Tower constructor taking in a Point for location
    *
    * @param p The Point location of the Tower
    */
    public Tower(Point p) {
        this((int) p.getX(), (int) p.getY());
    }

    /**
    * The Tower constructor taking in an x and y coordinate for location
    *
    * @param x The x coordinate of the tower
    * @param y The y coordinate of the Tower
    */
    public Tower(int x, int y) {
        this(x, y, 5, 100, 75);
    }

    /**
    * The Tower constructor taking in all information
    *
    * @param x The x coordinate of the Tower
    * @param y The y coordinate of the Tower
    * @param damage The amount of damage the Tower inflicts each turn
    * @param radius The reach of the tower
    * @param cost The cost in money of making this tower
    */
    public Tower(int x, int y, int damage, int radius, int cost) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.radius = radius;
        this.cost = cost;
        this.drawRectangle = new Rectangle(x, y, towerWidth, towerHeight);
    }

    /**
    * Gets the x coordinate of the Tower
    *
    * @return x The x coordinate of the Tower
    */
    public int getX() {
        return x;
    }

    /**
    * Gets the y coordinate of the Tower
    *
    * @return y The y coordinate of the Tower
    */
    public int getY() {
        return y;
    }

    /**
    * Gets the draw rectangle of the tower itself
    *
    * @return drawRectangle The Rectangle defining the Tower itself
    */
    public Rectangle getDrawRectangle() {
        return drawRectangle;
    }

    /**
    * Gets the radius of reach of the Tower
    *
    * @return radius The reach of the Tower
    */
    public int getRadius() {
        return radius;
    }

    /**
    * Gets the cost in money of creating this Tower
    *
    * @return cost The amount of money used to create this Tower
    */
    public int getCost() {
        return cost;
    }

    /**
    * Decides if two Towers overlap in the Tower itself, not including reach
    *
    * @param other The other Tower to check intersection with
    * @return boolean Whether or not this Tower intersects with other Tower
    */
    public boolean intersectsWith(Tower other) {
        return other.getDrawRectangle().intersects(drawRectangle);
    }

    /**
    * Decides if two Towers overlap in the Tower itself, not including reach
    *
    * @param towers The ArrayList of Towers currently on the map
    * @return boolean Whether or not this Tower intersects with a Tower on the
    * map
    */
    public boolean canPlaceOnMap(ArrayList<Tower> towers) {
        if (!GamePanel.BOUNDS.contains(this.x, this.y)) {
            return false;
        }
        for (Tower tower : towers) {
            if (tower.intersectsWith(this)) {
                return false;
            }
        }
        return true;
    }

    /**
    * Inflicts damage on a Monster
    *
    * @param monster The Monster taking the damage
    */
    public void fire(Monster monster) {
        monster.takeHit(damage);
    }

    /**
    * Decides whether or not this tower can reach a monster
    *
    * @param monster The Monster begin tested range for
    * @return boolean Whether or not this Tower can reach the monster
    */
    public boolean canReach(Monster monster) {
        int x1 = monster.getX();
        int y1 = monster.getY();
        return Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y)) <= radius;
    }

    /**
    * Draws the Tower's radius to the GUI
    *
    * @param g The Graphics object dealing with drawing the Tower
    */
    public abstract void drawReach(Graphics g);

    /**
    * Draws the Tower to the GUI
    *
    * @param g The Graphics object dealing with drawing the Tower
    */
    public abstract void drawTower(Graphics g);
}
