package stickman.model.ScoreObserver;

import stickman.levels.Level;
import stickman.model.GameEngine;

/**
 * The observer abstract class of the observer pattern
 */
public abstract class Observer {
    /**
     * Observation subject
     */
    protected Level subject;
    /**
     * GameEngine
     */
    protected GameEngine gameEngine;
    /**
     * Update the observer state after observing the subject change
     */
    public abstract void update();
}
