package stickman.model.ScoreObserver;

import stickman.levels.Level;
import stickman.model.GameEngine;

/**
 * The observer class to observe the score changes
 */
public class FinalScoreObserver extends Observer{

    /**
     * Constructor
     * @param subject Observed subject class
     * @param gameEngine Game engine class that aggregates scores
     */
    public FinalScoreObserver(Level subject, GameEngine gameEngine){
        this.subject = subject;
        this.gameEngine = gameEngine;
        this.subject.attach(this);
    }

    /**
     * Update the final score after the score changes
     */
    @Override
    public void update() {
        int currentFinalScore = this.gameEngine.getFinalScore();
        this.gameEngine.setFinalScore( currentFinalScore + this.subject.getScore());
    }
}
