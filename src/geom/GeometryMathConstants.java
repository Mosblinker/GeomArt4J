/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package geom;

/**
 * This contains a bunch of constants for math involving geometry.
 * @author Mosblinker
 */
public interface GeometryMathConstants {
    /**
     * This is the maximum angle for a circle, in degrees. In other words, 360 
     * degrees.
     */
    public static final double FULL_CIRCLE_DEGREES = 360.0;
    /**
     * This is the angle for half a circle, in degrees. In other words, 180 
     * degrees.
     */
    public static final double HALF_CIRCLE_DEGREES = FULL_CIRCLE_DEGREES/2.0;
    /**
     * This is the angle for quarter of a circle, in degrees. In other words, 90 
     * degrees.
     */
    public static final double QUARTER_CIRCLE_DEGREES = FULL_CIRCLE_DEGREES/4.0;
    /**
     * This is 2<i>{@link Math#PI pi}</i>. This is equivalent to the maximum 
     * angle for a circle, in radians.
     */
    public static final double TWO_PI = Math.PI * 2.0;
    /**
     * This is <i>{@link Math#PI pi}</i>/2. This is equivalent to the angle for 
     * quarter of a circle, in radians.
     */
    public static final double HALF_PI = Math.PI / 2.0;
    /**
     * This is the value for the golden ratio.
     */
    public static final double GOLDEN_RATIO = (1.0 + Math.sqrt(5)) / 2.0;
}
