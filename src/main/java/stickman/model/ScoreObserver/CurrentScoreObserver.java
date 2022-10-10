package stickman.model.ScoreObserver;

import stickman.levels.Level;
import stickman.model.GameEngine;

/**
 * The observer class to observe the score changes
 */
public class CurrentScoreObserver extends Observer{

    /**
     * Constructor
     * @param subject Observed subject class
     * @param gameEngine Game engine class that aggregates scores
     */
    public CurrentScoreObserver(Level subject, GameEngine gameEngine){
        this.subject = subject;
        this.gameEngine = gameEngine;
        this.subject.attach(this);
    }

    /**
     * Update the current level score after the score changes
     */
    @Override
    public void update() {
        int currentScore = this.gameEngine.getCurrentScore();

        if (this.subject.levelWon()) {
            this.gameEngine.setCurrentScore(0);
        } else {
            this.gameEngine.setCurrentScore(currentScore + this.subject.getScore());
        }
    }
}
