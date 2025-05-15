/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Mosblinker
 */
public class CenteredTextPainter extends ListenedPainter<String>{
    
    public static final String LINE_SPACING_PROPERTY_CHANGED = 
            "LineSpacingPropertyChanged";
    
    public static final String ANTIALIASING_PROPERTY_CHANGED = 
            "AntialiasingPropertyChanged";
    /**
     * This is the amount by which to separate the lines of text. This is just 
     * the base value and is scaled to account for the font size.
     */
    private double lineSpacing = 0.0;
    /**
     * This is whether the text will be rendered with antialiasing.
     */
    private boolean antialiasing = true;
    /**
     * 
     */
    public CenteredTextPainter(){
    }
    /**
     * 
     * @param painter 
     */
    public CenteredTextPainter(CenteredTextPainter painter){
        this.antialiasing = painter.antialiasing;
        this.lineSpacing = painter.lineSpacing;
    }
    
    public double getLineSpacing(){
        return lineSpacing;
    }
    
    public CenteredTextPainter setLineSpacing(double value){
        if (value != lineSpacing){
            double old = lineSpacing;
            lineSpacing = value;
            firePropertyChange(LINE_SPACING_PROPERTY_CHANGED,old,lineSpacing);
        }
        return this;
    }
    
    public boolean isAntialiasingEnabled(){
        return antialiasing;
    }
    
    public CenteredTextPainter setAntialiasingEnabled(boolean value){
        if (value != antialiasing){
            antialiasing = value;
            firePropertyChange(ANTIALIASING_PROPERTY_CHANGED,value);
        }
        return this;
    }
    /**
     * This calculates and returns the spacing to add between the lines of text, 
     * given the size of the text. This allows for the line spacing to scale 
     * with the text size instead of being a fixed distance for all sizes.
     * @param size The point size of the font.
     * @return The line spacing for the text.
     * @see #getLineSpacing() 
     * @see #setLineSpacing(double) 
     * @see #getLineSpacing(java.awt.Font) 
     * @see Font#getSize2D() 
     */
    protected double getLineSpacing(double size){
        return getLineSpacing() * (size / 10.0);
    }
    /**
     * This calculates and returns the spacing to add between the lines of text, 
     * given the size of the font. This allows for the line spacing to scale 
     * with the font size instead of being a fixed distance for all fonts.
     * @param font The font to use.
     * @return The line spacing for the text.
     * @see #getLineSpacing() 
     * @see #setLineSpacing(double) 
     * @see #getLineSpacing(double) 
     * @see Font#getSize2D() 
     */
    protected double getLineSpacing(Font font){
            // If the font is null
        if (font == null)
            return getLineSpacing();
        return getLineSpacing(font.getSize2D());
    }
    @Override
    public void paint(Graphics2D g, String text, int width, int height) {
            // Check if the graphics context is null
        Objects.requireNonNull(g);
            // If either the width or height are less than or equal to zero or 
            // the given String is null or blank (nothing would be rendered 
            // anyway)
        if (width <= 0 || height <= 0 || text == null || text.isBlank())
            return;
            // Configure a copy of the graphics context
        g = configureGraphics((Graphics2D) g.create());
            // Clip the graphics context region to only fill the area
        g.clipRect(0, 0, width, height);
            // Get the font metrics for the font set for the graphics
        FontMetrics metrics = g.getFontMetrics();
            // A list to contain all the lines of text in the given String
        ArrayList<String> lines = new ArrayList<>();
            // A list to contain the bounds of each line of text
        ArrayList<Rectangle2D> lineBounds = new ArrayList<>();
            // This gets the height of all of the text
        double textHeight = 0;
            // This is the x-coordinate for the center of the area
        double centerX = width/2.0;
            // This is the spacing to use between each line of text.
        double spacing = getLineSpacing(g.getFont());
            // Go through each line of text in the given String
        for (Object temp : text.lines().toArray()){
                // Get the current line of text
            String t = temp.toString();
            lines.add(t);
                // Get the bounds of the line of text
            Rectangle2D bounds = metrics.getStringBounds(t, g);
            lineBounds.add(bounds);
                // Add the height of the text along with spacing for the next 
                // line of text
            textHeight += bounds.getHeight() + spacing;
                // Center the line of text in the x-axis (y-axis will come later)
            bounds.setFrameFromCenter(centerX, bounds.getCenterY(), 
                    (width-bounds.getWidth())/2.0, bounds.getY());
        }   // Remove the last line spacing from the height
        textHeight -= spacing;
            // This is the y-coordinate for the top corner of the current line 
            // of text
        double y = (height - textHeight) / 2.0;
            // Go through each line of text
        for (int i = 0; i < lines.size(); i++){
                // Get the bounds for the text
            Rectangle2D bounds = lineBounds.get(i);
                // Draw the line of text using the centered x-coordinate, and 
                // subtracting the negative y-coordinate from the top corner for 
                // the current line to get the y-coordinate for the baseline 
            g.drawString(lines.get(i), (float)bounds.getX(), (float)(y-bounds.getY()));
                // Get the y-coordinate for the next line of text
            y += bounds.getHeight() + spacing;
        }
        g.dispose();
    }
    /**
     * This is used to configure the graphics context used to render the spiral. 
     * It's assumed that the returned graphics context is the same as the given 
     * graphics context, or at least that the returned graphics context 
     * references the given graphics context in some way. 
     * @param g The graphics context to render to.
     * @return The given graphics context, now configured for rendering the 
     * spiral.
     * @see #paint 
     */
    protected Graphics2D configureGraphics(Graphics2D g){
            // Enable/Disable antialiasing depending on whether text should be 
            // antialiased
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                (isAntialiasingEnabled()) ? RenderingHints.VALUE_ANTIALIAS_ON : 
                        RenderingHints.VALUE_ANTIALIAS_OFF);
            // Enable/Disable antialiasing for text depending on whether text 
            // should be antialiased
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                (isAntialiasingEnabled()) ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : 
                        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            // Prioritize rendering quality over speed
        g.setRenderingHint(RenderingHints.KEY_RENDERING, 
                RenderingHints.VALUE_RENDER_QUALITY);
            // Prioritize quality over speed for alpha interpolation
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, 
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            // Enable dithering
        g.setRenderingHint(RenderingHints.KEY_DITHERING, 
                RenderingHints.VALUE_DITHER_ENABLE);
            // Prioritize color rendering quality over speed
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, 
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            // Set the stroke normalization to be pure, i.e. geometry should be 
            // left unmodified
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_PURE);
        return g;
    }
    @Override
    protected String paramString(){
        return "lineSpacing="+getLineSpacing()+
                    // If the text is antialiased, say so
                ((isAntialiasingEnabled())?",antialiased":"");
    }
}
