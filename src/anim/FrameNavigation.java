/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package anim;

/**
 *
 * @author Mosblinker
 */
public enum FrameNavigation {
    
    NEXT,
    
    PREVIOUS,
    
    LAST(true),
    
    FIRST(true),
    
    PLAY,
    
    PAUSE,
    
    STOP;
    /**
     * 
     * @param jumpsToEnd Whether this represents jumping to the end of a list.
     */
    private FrameNavigation(boolean jumpToEnd){
        this.endJump = jumpToEnd;
    }
    /**
     * 
     */
    private FrameNavigation(){
        this(false);
    }
    /**
     * This indicates that this jumps to either end of a list.
     */
    private final boolean endJump;
    /**
     * This returns whether this is the icon for a navigation button that jumps 
     * to the end of a list.
     * @return Whether this icon is for navigating to the end of a list.
     */
    public boolean getJumpsToEnd(){
        return endJump;
    }
}
