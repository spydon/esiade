package net.esiade.client;

import com.google.gwt.user.client.Random;

/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
 
/**
 * 2-dimensional vector. We're trying to keep the mathematical difference between points and vectors alive.
 *
 * @author Pieter De Graef
 */
public class Vector2D {
 
    public double x;
    public double y;
 
    // -------------------------------------------------------------------------
    // Constructors:
    // -------------------------------------------------------------------------
 
    public Vector2D() {
    	x = Random.nextDouble();
        y = Random.nextDouble();
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
//        double len = (x*x)+(y*y);
//		RootPanel.get().add(new Label("Bla " + len));
 //       return len;
//		WTFBBQ BUGG! Varför får man inte göra simpel aritmetik?
       return 0;
        //return Math.sqrt(len);
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