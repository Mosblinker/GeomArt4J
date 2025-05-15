/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package anim;

import icons.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Objects;

/**
 * This is an icon used for navigation buttons to denote the direction in which 
 * they go in. This also provides a version for navigation buttons that jump to 
 * the start or end of a list and a disabled version.
 * @author Mosblinker
 */
public class FrameNavigationIcon implements Icon2D{
    /**
     * The width of the icon.
     */
    private final int width;
    /**
     * The height of the icon.
     */
    private final int height;
    /**
     * 
     */
    private final FrameNavigation value;
    /**
     * The Path2D object used to draw the arrow.
     */
    private final Path2D arrow;
    /**
     * This constructs a NavigationIcon with the given direction, width, height, 
     * and whether it jumps to the end.
     * @param value
     * @param width The width of the icon (must be greater than 0).
     * @param height The height of the icon (must be greater than 0).
     */
    public FrameNavigationIcon(FrameNavigation value, int width, int height){
        this.value = Objects.requireNonNull(value);
        if (width < 1 || height < 1)   // If the width or height are less than 1
            throw new IllegalArgumentException(
                    "Width and height both must be greater than 0: width=" + 
                            width + " height=" + height);
        this.width = width;
        this.height = height;
        arrow = new Path2D.Double();
        boolean rightward = false;
        switch(value){
            case LAST:
            case NEXT:
            case PLAY:
                rightward = true;
            case PREVIOUS:
            case FIRST:
                    // If this is pointing to the right, place the point on the right of 
                    // the icon. Otherwise, place it on the left. This will be the side 
                    // with only one point
                arrow.moveTo((rightward) ? (width-1) : 0, height/2.0);
                    // If this is pointing to the right, place the next two points on 
                    // the left of this icon. Otherwise, place them on the right.
                arrow.lineTo((rightward) ? 0 : (width-1), 0);
                arrow.lineTo(arrow.getCurrentPoint().getX(), height-1);
                arrow.closePath();
                if (value.getJumpsToEnd()){   // If this represents jumping to the end of a list
                        // Get the x for the starting point
                    int lineX = (int) arrow.getCurrentPoint().getX();
                    arrow.moveTo(lineX, 0);
                    arrow.lineTo(Math.abs(lineX-(width/8.0)), 0);
                    arrow.lineTo(arrow.getCurrentPoint().getX(), height-1);
                    arrow.lineTo(lineX, height-1);
                    arrow.closePath();
                }
                break;
            case STOP:
                arrow.append(new Rectangle(0,0,width,height), false);
                break;
            case PAUSE:
                Rectangle2D rect = new Rectangle2D.Double(0,0,width/3.0,height);
                arrow.append(rect, false);
                rect.setFrame(rect.getMaxX()+rect.getWidth(), rect.getY(), 
                        rect.getWidth(), rect.getHeight());
                arrow.append(rect, false);
        }
    }
    /**
     * This constructs a NavigationIcon with the given direction, size, and 
     * whether it jumps to the end.
     * @param value
     * @param size The width and height of the icon (must be greater than 0).
     */
    public FrameNavigationIcon(FrameNavigation value,int size){
        this(value,size,size);
    }
    /**
     * This constructs a NavigationIcon with the given direction and whether it 
     * jumps to the end of a list.The icon will be 16x16 in size.
     * @param value
     */
    public FrameNavigationIcon(FrameNavigation value){
        this(value,16);
    }
    @Override
    public void paintIcon2D(Component c, Graphics2D g, int x, int y) {
        g.translate(x, y);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            // If this is the enabled version of the icon, use the color black. 
            // Otherwise, use gray to indicate that this icon appears grayed out
        g.setColor((c.isEnabled())?java.awt.Color.BLACK:java.awt.Color.GRAY);
        g.fill(arrow);
    }
    /**
     * This returns the Path2D object used to render the arrow indicating the 
     * direction. Icons for buttons that jump to the end of the list have a line 
     * added to the side in which they point towards.
     * @return The Path2D object used to render the arrow.
     */
    protected Path2D getArrow(){
        return arrow;
    }
    /**
     * 
     * @return 
     */
    public FrameNavigation getNavigation(){
        return value;
    }
    @Override
    public int getIconWidth() {
        return width;
    }
    @Override
    public int getIconHeight() {
        return height;
    }
    /**
     * This returns a String representation of this icon. 
     * This method is primarily intended to be used only for debugging purposes, 
     * and the content and format of the returned String may vary between 
     * implementations.
     * @return A String representation of this icon.
     */
    protected String paramString(){
        return getIconWidth()+"x"+getIconHeight()+
                ",navigation="+getNavigation();
    }
    /**
     * This returns a string representation of this icon and its values.
     * @return A string representation of this icon and its values.
     */
    @Override
    public String toString(){
        return getClass().getName()+"["+paramString()+"]";
    }
}
