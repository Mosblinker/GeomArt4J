/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package anim;

import event.DisabledComponentMouseListener;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author Mosblinker
 */
public class FrameNavigationPanel extends JPanel{
    
    
    
    public static final String FRAME_BUTTONS_LOOP_PROPERTY_CHANGED = 
            "FrameButtonsLoopPropertyChanged";
    
    public static final String NEXT_BUTTON_CAN_ADD_FRAMES_PROPERTY_CHANGED = 
            "NextButtonAddsFramesPropertyChanged";
    
    public static final String PREVIOUS_BUTTON_CAN_ADD_FRAMES_PROPERTY_CHANGED = 
            "PreviousButtonAddsFramesPropertyChanged";
    
    protected static final int FRAME_BUTTONS_LOOP_FLAG = 0x0002;
    
    protected static final int NEXT_BUTTON_ADDS_FRAME_FLAG = 0x0004;
    
    protected static final int PREVIOUS_BUTTON_ADDS_FRAME_FLAG = 0x0008;
    
    
    
    protected JComponent createButtonSeparator(){
        return new Box.Filler(new Dimension(7, 0), new Dimension(7, 0), 
                new Dimension(7, 32767));
    }
    
    private JButton createButton(FrameNavigation nav, String command, 
            String toolTip, Handler handler, boolean show, boolean sepNext){
        JButton button = new JButton(new FrameNavigationIcon(nav));
        button.setActionCommand(command);
        button.setToolTipText(toolTip);
        button.addActionListener(handler);
        button.addMouseListener(getDisabledComponentListener());
        JComponent sep = createButtonSeparator();
        buttonSeparators.put(button, sep);
        if (!sepNext)
            add(sep);
        add(button);
        if (sepNext)
            add(sep);
        button.setVisible(show);
        sep.setVisible(show);
        return button;
    }
    
    private JButton createButton(FrameNavigation nav, String toolTip, 
            Handler handler, boolean show, boolean sepNext){
        return createButton(nav,nav.name(),toolTip,handler,show,sepNext);
    }
    
    private void initialize(){
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        buttonSeparators = new HashMap<>();
        Handler handler = new Handler();
            // Create the first frame button
        firstButton = createButton(FrameNavigation.FIRST,"First",handler,true,true);
            // Create the previous frame button
        prevButton = createButton(FrameNavigation.PREVIOUS,"Previous",handler,true,true);
            // Create the play/pause button
        playButton = new JToggleButton(new FrameNavigationIcon(FrameNavigation.PLAY));
        playButton.setRolloverIcon(new FrameNavigationIcon(FrameNavigation.PLAY));
        playButton.setSelectedIcon(new FrameNavigationIcon(FrameNavigation.PAUSE));
//        playButton.setActionCommand(command);
        playButton.setToolTipText("Play/Pause");
        playButton.addActionListener(handler);
        playButton.addMouseListener(getDisabledComponentListener());
//        playButton.setEnabled(false);
        JComponent playSep = createButtonSeparator();
        buttonSeparators.put(playButton, playSep);
            // Add the play/pause button to the panel
//        playButton.setVisible(show);
//        playSep.setVisible(playButton.isVisible());
        add(playButton);
        add(playSep);
            // Create the frame slider
        frameSlider = new JSlider();
        frameSlider.setMajorTickSpacing(1);
        frameSlider.setMaximum(0);
        frameSlider.setPaintTicks(true);
        frameSlider.addChangeListener(handler);
            // Add the frame slider to the panel
        add(frameSlider);
            // Create the stop button
        stopButton = createButton(FrameNavigation.STOP,"Stop",handler,true,false);
            // Create the next frame button
        nextButton = createButton(FrameNavigation.NEXT,"Next",handler,true,false);
            // Create the last frame button
        lastButton = createButton(FrameNavigation.LAST,"Last",handler,true,false);
        
        setButtonMargins(new Insets(2,0,2,0));
        updateFrameNavigation();
    }
    
    public FrameNavigationPanel(){
        initialize();
    }
    
    private void setNavFlag(int flag, boolean value, String propName){
            // If the flag is already set to the desired value.
        if (getNavFlag(flag) == value)    
            return;
            // If the flag is to be set, then set it. Otherwise, clear it
        navFlags = (value)?(navFlags|flag):Math.max(0,navFlags&~flag);
        if (propName != null)
            firePropertyChange(propName,!value,value);
    }
    
    private boolean getNavFlag(int flag){
        return (navFlags & flag) == flag;
    }
    
    
    
    public void setButtonMargins(Insets margin){
        buttonMargin = margin;
        for (AbstractButton button : buttonSeparators.keySet())
            button.setMargin(buttonMargin);
    }
    
    public Insets getButtonMargins(){
        return (buttonMargin == null) ? null : (Insets) buttonMargin.clone();
    }
    /**
     * 
     * @param value
     * @param loop 
     */
    protected void setValue(int value, boolean loop){
        if (loop){
            int size = getFrameCount();
            int temp = value - getMinimum();
            value = ((size+temp)%size)+getMinimum();
        }
        frameSlider.setValue(value);
    }
    /**
     * 
     * @param value 
     */
    public void setValue(int value){
        setValue(value,false);
    }
    /**
     * 
     * @return 
     */
    public int getValue(){
        return frameSlider.getValue();
    }
    /**
     * 
     * @param minimum 
     */
    public void setMinimum(int minimum){
        frameSlider.setMinimum(minimum);
    }
    /**
     * 
     * @return 
     */
    public int getMinimum(){
        return frameSlider.getMinimum();
    }
    /**
     * 
     * @param maximum 
     */
    public void setMaximum(int maximum){
        frameSlider.setMaximum(maximum);
    }
    /**
     * 
     * @return 
     */
    public int getMaximum(){
        return frameSlider.getMaximum();
    }
    /**
     * 
     * @return 
     */
    public int getFrameCount(){
        return getMaximum() - getMinimum() + 1;
    }
    /**
     * 
     * @param value 
     */
    public void setFrameButtonsLoop(boolean value){
        setNavFlag(FRAME_BUTTONS_LOOP_FLAG,value,
                FRAME_BUTTONS_LOOP_PROPERTY_CHANGED);
    }
    /**
     * 
     * @return 
     */
    public boolean getFrameButtonsLoop(){
        return getNavFlag(FRAME_BUTTONS_LOOP_FLAG);
    }
    /**
     * 
     * @param value 
     */
    public void setNextButtonAddsFrames(boolean value){
        setNavFlag(NEXT_BUTTON_ADDS_FRAME_FLAG,value,
                NEXT_BUTTON_CAN_ADD_FRAMES_PROPERTY_CHANGED);
    }
    /**
     * 
     * @return 
     */
    public boolean getNextButtonAddsFrames(){
        return getNavFlag(NEXT_BUTTON_ADDS_FRAME_FLAG);
    }
    /**
     * 
     * @param value 
     */
    public void setPreviousButtonAddsFrames(boolean value){
        setNavFlag(PREVIOUS_BUTTON_ADDS_FRAME_FLAG,value,
                PREVIOUS_BUTTON_CAN_ADD_FRAMES_PROPERTY_CHANGED);
    }
    /**
     * 
     * @return 
     */
    public boolean getPreviousButtonAddsFrames(){
        return getNavFlag(PREVIOUS_BUTTON_ADDS_FRAME_FLAG);
    }
    /**
     * 
     */
    protected void frameChanged(){
        
    }
    
    protected void doAnimationTimer(ActionEvent evt){
        // Insert Animation Timer code
    }
    
    protected void toNextFrame(boolean loop){
        setValue(getValue()+1,loop);
    }
    
    public void toNextFrame(){
        toNextFrame(false);
    }
    
    protected void toPreviousFrame(boolean loop){
        setValue(getValue()-1,loop);
    }
    
    public void toPreviousFrame(){
        toPreviousFrame(false);
    }
    
    public void toFirstFrame(){
        setValue(getMinimum());
    }
    
    public void toLastFrame(){
        setValue(getMaximum());
    }
    
    public void play(){
        // Insert Play code
    }
    
    public void pause(){
        // Insert Pause code
    }
    
    public void stop(){
        // Insert Stop code
        playButton.setSelected(false);
        updateFrameNavigation();
        toFirstFrame();
    }
    
    public void doAction(FrameNavigation nav, boolean loop){
        Objects.requireNonNull(nav);
        switch(nav){
            case NEXT:
                toNextFrame(loop);
                return;
            case PREVIOUS:
                toPreviousFrame(loop);
                return;
            case FIRST:
                toFirstFrame();
                return;
            case LAST:
                toLastFrame();
                return;
            case PLAY:
                play();
                return;
            case PAUSE:
                pause();
                return;
            case STOP:
                stop();
        }
    }
    
    public void doAction(FrameNavigation nav){
        doAction(nav,false);
    }
    /**
     * This returns the mouse listener used to cause disabled components to 
     * provide error feedback to the user when they are pressed.
     * @return The mouse listener used to make disabled components provide 
     * error feedback when pressed.
     */
    protected MouseListener getDisabledComponentListener(){
            // If the disabled component mouse listener has not been initialized 
        if (disabledListener == null)   // yet
            disabledListener = new DisabledComponentMouseListener();
        return disabledListener;
    }
    /**
     * This adds the given {@code ActionListener} to this panel.
     * @param l The listener to add.
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void addActionListener(ActionListener l){
        if (l != null)          // If the listener is not null
            listenerList.add(ActionListener.class, l);
    }
    /**
     * This removes the given {@code ActionListener} from this panel.
     * @param l The listener to remove.
     * @see #addActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void removeActionListener(ActionListener l){
        listenerList.remove(ActionListener.class, l);
    }
    /**
     * This returns an array containing all the {@code ActionListener}s that 
     * have been added to this panel.
     * @return An array containing the {@code ActionListener}s that have been 
     * added, or an empty array if none have been added.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     */
    public ActionListener[] getActionListeners(){
        return listenerList.getListeners(ActionListener.class);
    }
    /**
     * This notifies all the {@code ActionListener}s that have been added to 
     * this panel if the given {@code ActionEvent} is not null. If the source 
     * of the given {@code ActionEvent} is not this panel, then the {@code 
     * ActionEvent} will be redirected to have this panel as its source.
     * @param evt The {@code ActionEvent} to fire.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     * @see #fireActionPerformed(String, long, int) 
     * @see #fireActionPerformed(String) 
     */
    protected void fireActionPerformed(ActionEvent evt){
        if (evt == null)    // If the event is null
            return;
            // If the event's source is not this component
        else if (evt.getSource() != this){
            evt = new ActionEvent(this, evt.getID(), evt.getActionCommand(),
                    evt.getWhen(),evt.getModifiers());
        }
            // A for loop to go through the action listeners
        for (ActionListener l : listenerList.getListeners(ActionListener.class)){
            if (l != null)  // If the listener is not null
                l.actionPerformed(evt);
        }
    }
    /**
     * This notifies all the {@code ActionListener}s that have been added to 
     * this panel of the given action command if the action command is not null. 
     * @param command The action command for the action event to fire.
     * @param when The time at which the event occurred.
     * @param modifiers The modifier keys that were down during the event. Zero 
     * indicates that no known modifiers were passed.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     * @see #fireActionPerformed(ActionEvent) 
     * @see #fireActionPerformed(String) 
     */
    protected void fireActionPerformed(String command, long when, int modifiers){
        if (command == null)    // If the action command is null.
            return;
        fireActionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
                command,when,modifiers));
    }
    /**
     * This notifies all the {@code ActionListener}s that have been added to 
     * this panel of the given action command if the action command is not null. 
     * @param command The action command for the action event to fire.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     * @see #fireActionPerformed(ActionEvent) 
     * @see #fireActionPerformed(String, long, int) 
     */
    protected void fireActionPerformed(String command){
        fireActionPerformed(command,System.currentTimeMillis(),0);
    }
    /**
     * This adds the given {@code ChangeListener} to this component.
     * @param l The listener to add.
     * @see #removeChangeListener(ChangeListener) 
     * @see #getChangeListeners() 
     */
    public void addChangeListener(ChangeListener l){
        if (l != null)          // If the listener is not null
            listenerList.add(ChangeListener.class, l);
    }
    /**
     * This removes the given {@code ChangeListener} from this component.
     * @param l The listener to remove.
     * @see #addChangeListener(ChangeListener) 
     * @see #getChangeListeners() 
     */
    public void removeChangeListener(ChangeListener l){
        listenerList.remove(ChangeListener.class, l);
    }
    /**
     * This returns an array containing all the {@code ChangeListener}s that 
     * have been added to this component.
     * @return An array containing the {@code ChangeListener}s that have been 
     * added, or an empty array if none have been added.
     * @see #addChangeListener(ChangeListener) 
     * @see #removeChangeListener(ChangeListener) 
     */
    public ChangeListener[] getChangeListeners(){
        return listenerList.getListeners(ChangeListener.class);
    }
    /**
     * This is used to notify the {@code ChangeListener}s that the state of this  
     * component has changed.
     */
    protected void fireStateChanged(){
            // This constructs the evet to fire
        ChangeEvent evt = new ChangeEvent(this);
            // A for loop to go through the change listeners
        for (ChangeListener l : listenerList.getListeners(ChangeListener.class)){
            if (l != null)  // If the listener is not null
                l.stateChanged(evt);
        }
    }
    /**
     * 
     */
    protected void updateFrameNavigation(){
        playButton.setEnabled(isEnabled() && getFrameCount() > 1);
        stopButton.setEnabled(playButton.isEnabled() && 
                playButton.isSelected());
        frameSlider.setEnabled(playButton.isEnabled() && 
                !playButton.isSelected());
        boolean backEnabled = getValue() > getMinimum();
        boolean forEnabled = getValue() < getMaximum();
        boolean canLoop = getFrameButtonsLoop();
        prevButton.setEnabled(frameSlider.isEnabled() && (backEnabled || 
                canLoop || getPreviousButtonAddsFrames()));
        firstButton.setEnabled(frameSlider.isEnabled() && backEnabled);
        nextButton.setEnabled(frameSlider.isEnabled() && (forEnabled || canLoop 
                || getNextButtonAddsFrames()));
        lastButton.setEnabled(frameSlider.isEnabled() && forEnabled);
    }
    /**
     * {@inheritDoc }
     */
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            updateFrameNavigation();
        } catch (NullPointerException ex){ }
    }
    
    
    /**
     * This is used to provide error feedback to the user when this is disabled. 
     * This is initialized the first time it is requested.
     */
    private DisabledComponentMouseListener disabledListener = null;
    
    
    
    private int navFlags = 0x0000;
    
    private Insets buttonMargin = null;
    
    protected Map<AbstractButton,JComponent> buttonSeparators;
    
    protected JButton firstButton;
    
    protected JButton prevButton;
    
    protected JButton nextButton;
    
    protected JButton lastButton;
    
    protected JToggleButton playButton;
    
    protected JButton stopButton;
    
    protected JSlider frameSlider;
    
    private class Handler implements ChangeListener, ActionListener{

        @Override
        public void stateChanged(ChangeEvent evt) {
            updateFrameNavigation();
            frameChanged();
            fireStateChanged();
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand() == null)
                return;
            FrameNavigation nav = null;
            try{
                nav = FrameNavigation.valueOf(evt.getActionCommand());
            } catch (IllegalArgumentException ex){ }
            if (nav != null)
                doAction(nav,getFrameButtonsLoop());
        }
    }
}
