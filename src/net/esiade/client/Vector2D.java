package net.esiade.client;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
 
public class Vector2D {
 
    public double x;
    public double y;
 
    public Vector2D() {
    	x = (Random.nextInt(2)==1 ? 1 : -1)*Random.nextDouble();
        y = (Random.nextInt(2)==1 ? 1 : -1)*Random.nextDouble();
    }
    
    public Vector2D(double k) {
    	x = k*(Random.nextInt(2)==1 ? 1 : -1)*Random.nextDouble();
        y = k*(Random.nextInt(2)==1 ? 1 : -1)*Random.nextDouble();
    }
    
    public Vector2D(int width, int height) {
    	x = Random.nextInt(width);
        y = Random.nextInt(height);
    }
 
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
 
    // -------------------------------------------------------------------------
    // Class specific functions:
    // -------------------------------------------------------------------------
 
    /**
     * Add another vector to this one.
     *
     * @param vector2d
     *            The other vector.
     */
    public void add(Vector2D vector2d) {
        x += vector2d.x;
        y += vector2d.y;
    }
    /**
     * Subtract another vector from this one.
     *
     * @param vector2d
     *            The other vector.
     */
    public void subtract(Vector2D vector2d) {
        x -= vector2d.x;
        y -= vector2d.y;
    }
 
    /**
     * Scale this vector.
     *
     * @param xFactorCircle
     *            Scale the X-factor with this value.
     * @param yFactor
     *            Scale the Y-factor with this value.
     */
    public void scale(double xFactor, double yFactor) {
        this.x *= xFactor;
        this.y *= yFactor;
    }
    
    /**
     * Translate this vector.
     *
     * @param xDist
     *            Translate the X-factor with x.
     * @param yDist
     *            Translate the Y-factor with y.
     */
    public void translate(double xDist, double yDist) {
        this.x += xDist;
        this.y += yDist;
    }
 
    /**
     * Calculate the distance between 2 vector by using Pythagoras' formula.
     *
     * @param vector2d
     *            The other vector.
     * @returns The distance between these 2 vectors as a int.
     */
    public double distance(Vector2D vector2d) {
        double a = vector2d.x - x;
        double b = vector2d.y - y;
        return Math.sqrt(a*a+b*b);
    }
 
    /**
     * Normalize this vector.
     */
    public void normalize() {
        double len = this.length();
        if (len == 0) {
            return;
        }
        x /= len;
        y /= len;
    }
 
    /**
     * Return the length of this vector. (Euclides)
     *
     * @returns The length as a int.
     */
    public double length() {
        double len = (x*x)+(y*y);
        return Math.sqrt(len);
    }
 
    /**
     * Calculates a vector's cross product.
     *
     * @param vector2D
     *            The second vector.
     */
    public double cross(Vector2D vector2D) {
        return x * vector2D.y - y * vector2D.x;
    }
 
    /**
     * Return this vector object as a string.
     */
    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }
 
    // -------------------------------------------------------------------------
    // Getters and setters.
    // -------------------------------------------------------------------------
 
    public double getX() {
        return x;
    }
 
    public void setX(double x) {
        this.x = x;
    }
 
    public double getY() {
        return y;
    }
 
    public void setY(double y) {
        this.y = y;
    }
}