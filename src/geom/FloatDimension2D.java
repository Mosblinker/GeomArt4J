/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geom;

import java.awt.geom.Dimension2D;

/**
 * This is an implementation of {@link Dimension2D Dimension2D} that stores a 
 * width and height in floating point numbers with {@code float} precision. 
 * Normally the width and height are non-negative, but there is nothing to 
 * prevent them from being negative. Unexpected behavior may occur if the width 
 * or height are negative.
 * @author Mosblinker
 */
public class FloatDimension2D extends Dimension2D{
    /**
     * The width of the dimension.
     */
    public float width;
    /**
     * The height of the dimension.
     */
    public float height;
    /**
     * This constructs a FloatDimension2D with the given width and height. 
     * @param width The width.
     * @param height The height.
     */
    public FloatDimension2D(float width, float height){
        this.width = width;
        this.height = height;
    }
    /**
     * This constructs a FloatDimension2D with the width and height of the 
     * given dimensions.
     * @param dim The dimensions to get the width and height from.
     */
    public FloatDimension2D(Dimension2D dim){
        this((float)dim.getWidth(),(float)dim.getHeight());
    }
    /**
     * This constructs a FloatDimension2D with a width and height of zero.
     */
    public FloatDimension2D(){
        this(0,0);
    }
    /**
     * {@inheritDoc }
     */
    @Override
    public double getWidth() {
        return width;
    }
    /**
     * {@inheritDoc }
     */
    @Override
    public double getHeight() {
        return height;
    }
    /**
     * This sets the size of this {@code FloatDimension2D} to the specified 
     * width and height.
     * @param width The new width for this dimension.
     * @param height The new height for this dimension.
     */
    @Override
    public void setSize(double width, double height) {
        this.width = (float)width;
        this.height = (float)height;
    }
    /**
     * This returns a string representation of this {@code FloatDimension2D} 
     * object's {@code width} and {@code height}. This method is primarily 
     * intended to be used only for debugging purposes, and the content and 
     * format of the returned string may vary between implementations.
     * @return A string representation of this {@code FloatDimension2D} object.
     */
    @Override
    public String toString(){
        return getClass().getName()+"[width="+width+",height="+height+"]";
    }
    /**
     * This returns whether the given object is a {@code Dimension2D} object 
     * that is equal to this {@code FloatDimension2D} object. That is to say, 
     * this returns whether the given object is a {@code Dimension2D} object 
     * with the same width and height as this {@code FloatDimension2D} object.
     * @param obj The object to compare to this dimension object.
     * @return Whether the given object is equal to this dimension object.
     */
    @Override
    public boolean equals(Object obj){
        if (obj == this)        // If the given object is this object
            return true;
            // If the given object is a Dimension2D object
        else if (obj instanceof Dimension2D){
                // Get the object as a Dimension2D object
            Dimension2D dim = (Dimension2D) obj;
            return getWidth() == dim.getWidth()&&getHeight()==dim.getHeight();
        }
        return false;
    }
    /**
     * This returns the hash code for this {@code FloatDimension2D} object.
     * @return The hash code for this dimension object.
     */
    @Override
    public int hashCode() {
        int hash = 3;   // This gets the hash code for this dimension object
            // A for loop to go through the width and height
        for (double value : new double[]{getWidth(),getHeight()}){
                // Get the bits for the current value
            long bits = Double.doubleToLongBits(value);
            hash = 37 * hash + (int)(bits ^ (bits >>> 32));
        }
        return hash;
    }
}
