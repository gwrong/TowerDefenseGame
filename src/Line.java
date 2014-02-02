import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * The Line class represents a Line with two points, making the construction
 * of a Tower Defense path easier
 *
 * @author Graham Wright
 * @version 1.0
 */
public class Line {
    private Point start, end;

    /**
    * The Line Constructor taking in two Points
    *
    * @param start The starting point of the line
    * @param end The ending point of the line
    */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
    * Line constructor for 2 coordinates
    *
    * @param x1 The start x coordinate
    * @param y1 The start y coordinate
    * @param x2 The end x coordinate
    * @param y2 The end y coordinate
    */
    public Line(int x1, int y1, int x2, int y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    /**
    * Gets the end Point of the Line
    *
    * @return end The end point of the Line
    */
    public Point getEnd() {
        return end;
    }

    /**
    * Gets the start Point of the Line
    *
    * @return start The start point of the Line
    */
    public Point getStart() {
        return start;
    }

    /**
    * Determines if the coordinate given is along this Line
    *
    * @param x The x coordinate of the location
    * @param y The y coordinate of the location
    * @return boolean Whether or not the location is along the Line
    */
    public boolean contains(int x, int y) {
        int largeX, smallX, largeY, smallY;

        int x1 = (int) start.getX();
        int y1 = (int) start.getY();
        int x2 = (int) end.getX();
        int y2 = (int) end.getY();
        if (Math.abs(x1 - x2) > 0) {
            if (x1 > x2) {
                largeX = x1;
                smallX = x2;
            } else {
                smallX = x1;
                largeX = x2;
            }
            return x >= smallX && x <= largeX;

        } else {
            if (y1 > y2) {
                largeY = y1;
                smallY = y2;
            } else {
                smallY = y1;
                largeY = y2;
            }
            return y >= smallY && y <= largeY;
        }
    }

    /**
    * Constructs a new Point that acts as a Vector for a Monster that changes
    * Lines along the Path based off of the Monster's speed
    *
    * @param speed The speed of the monster
    * @return Point The Point that represents the Monster's new Vector
    */
    public Point getVector(int speed) {
        int x1 = (int) start.getX();
        int y1 = (int) start.getY();
        int x2 = (int) end.getX();
        int y2 = (int) end.getY();

        if (x2 > x1) {
            return new Point(speed * 3, 0);
        } else if (x2 < x1) {
            return new Point(speed * -3, 0);
        } else if (y2 > y1) {
            return new Point(0, speed * 3);
        } else {
            return new Point(0, speed * -3);
        }
    }

    /**
    * Draws the Line to the GUI
    *
    * @param g The Graphics object used to draw the Line
    */
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(),
                                                            (int) end.getY());
    }
}
