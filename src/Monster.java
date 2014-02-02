import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * This class defines an abstract Monster in the Tower Defense Game
 * Monsters move around the map and can be defeated by towers
 *
 * @author Graham Wright
 * @version 1.0
 */
public abstract class Monster implements Comparable<Monster> {

    private int x, y, moneyValue, health, speed, pathIndex, distanceTraveled;
    private Point vector;
    private boolean beingAttacked, reachedEnd;

    /**
    * The Monster constructor using defaults for most things
    *
    * @param x The starting x coordinate of the monster
    * @param y The starting y coordinate of the monster
    */
    public Monster(int x, int y) {
        this(x, y, -3, 0, 5, 150, 1);
    }

    /**
    * The Monster constructor using defaults for most things
    *
    * @param x The starting x coordinate of the monster
    * @param y The starting y coordinate of the monster
    * @param vecX The starting x component of velocity
    * @param vecY The starting y component of velocity
    * @param moneyValue The money gained when killing this monster
    * @param health The starting health of the monster
    * @param speed How many times faster than a Regular Monster this monster
    *        moves
    */
    public Monster(int x, int y, int vecX, int vecY, int moneyValue, int health,
                                                                    int speed) {
        this.x = x;
        this.y = y;
        this.vector = new Point(speed * vecX, speed * vecY);
        this.health = health;
        this.moneyValue = moneyValue;
        this.speed = speed;

        pathIndex = 0;
        distanceTraveled = 0;
        beingAttacked = false;
        reachedEnd = false;
    }

    /**
    * The getter for the Monster's x location
    *
    * @return x The x coordinate of the Monster
    */
    public int getX() {
        return x;
    }

    /**
    * The getter for the Monster's y location
    *
    * @return y The y coordinate of the Monster
    */
    public int getY() {
        return y;
    }

    /**
    * The getter for the Monster's money value
    *
    * @return moneyValue The money earned from killing this monster
    */
    public int getMoneyValue() {
        return moneyValue;
    }

    /**
    * The getter for the Monster's total distance travelled, used in ordering
    * the monsters in the Collections.sort method
    */
    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    /**
    * The getter for how much score this monster deducts when it reached the
    * end
    *
    * @return int The score lost by letting this Monster through
    */
    public int getScoreLoss() {
        if (this instanceof UltimateMonster) {
            return 30;
        } else if (this instanceof IntenseMonster) {
            return 20;
        } else {
            return 10;
        }
    }

    /**
    * The getter for the Monster's beingAttacked state
    *
    * @return beingAttacked Whether or not a Tower just attacked this monster
    */
    public boolean isBeingAttacked() {
        return beingAttacked;
    }

    /**
    * The getter for whether or not the Monster reached the end of the path
    *
    * @return reachedEnd Whether or not the Monster has reached the end of the
    * path
    */
    public boolean didReachEnd() {
        return reachedEnd;
    }

    /**
    * Tells the Monster that it is being attacked
    *
    * @param beingAttacked The boolean value to change this.beingAttacked to
    */
    public void setBeingAttacked(boolean beingAttacked) {
        this.beingAttacked = beingAttacked;
    }

    /**
    * The getter for the Monster's beingAttacked state
    *
    * @param beingAttacked Whether or not a Tower just attacked this monster
    */
    public void takeHit(int damage) {

        health -= damage;
        beingAttacked = true;
    }

    /**
    * The move method moves the Monster along the path
    *
    * @param path The ArrayList of Lines that defines the Tower Defense path
    */
    public void move(ArrayList<Line> path) {
        Random rand = new Random();
        if (this instanceof UltimateMonster && rand.nextInt(2) == 0) {
            return;
        }
        int vecX = (int) vector.getX();
        int vecY = (int) vector.getY();
        distanceTraveled += (int) Math.sqrt(vecX * vecX + vecY * vecY);
        x += vecX;
        y += vecY;
        if (!path.get(pathIndex).contains(x, y)) {
            pathIndex++;
            if (pathIndex >= path.size()) {
                reachedEnd = true;
                drainHealth();
            } else {
                //removed speed for now
                vector = path.get(pathIndex).getVector(speed);
                x = (int) path.get(pathIndex).getStart().getX();
                y = (int) path.get(pathIndex).getStart().getY();
            }
        }
    }

    /**
    * Drains the Monster of health, used when it reaches the end of the path
    */
    public void drainHealth() {
        health = 0;
    }

    /**
    * Tells whether or not the monster is dead
    *
    * @return boolean Whether or not the Monster's health is 0 or below
    */
    public boolean isDead() {
        return health <= 0;
    }

    /**
    * Required compareTo method for implementing Comparable
    * A larger distance travelled is considered "less than" a Monster with
    * a shorter distance travelled. This sorts to a descending order of
    * distance travelled.
    *
    * @param other The other Monster to compare to
    * @return int other's distance travelled - this' distance travelled
    */
    public int compareTo(Monster other) {
        if (other.equals(this)) {
            return 0;
        } else {
            return other.getDistanceTraveled() - distanceTraveled;
        }
    }

    /**
    * Tells whether or not the other Monster is equal to this one
    *
    * @param other The other Monster to check equality with
    * @return boolean Whether or not this monster is equal to other
    */
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Monster)) {
            return false;
        }
        Monster monster = (Monster) other;
        return monster.getDistanceTraveled() == distanceTraveled;
    }

    /**
    * Returns an int representation of this object
    *
    * @return int An int representation of this Monster
    */
    public int hashCode() {
        return super.hashCode() + moneyValue;
    }

    /**
    * The draw method draws the Monster to the GUI
    *
    * @param g The Graphics object that can draw to the GUI
    */
    public abstract void draw(Graphics g);
}
