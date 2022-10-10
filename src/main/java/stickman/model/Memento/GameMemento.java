package stickman.model.Memento;

import stickman.levels.Level;

/**
 * Memento class to store game state
 */
public class GameMemento {

    /**
     * The current level of the game
     */
    private Level currentLevel;
    /**
     * Current level number
     */
    private int currentLevelNumber;
    /**
     * Score of the current level
     */
    private int currentScore;
    /**
     * Final scroe of the game
     */
    private int finalScore;
    /**
     * Remain lives
     */
    private int life;

    /**
     * Constructor, Create a new memo
     * @param currentLevel The current level of the game
     * @param currentLevelNumber Current level number
     * @param currentScore Score of the current level
     * @param finalScore Final scroe of the game
     * @param life Remain lives
     */
    public GameMemento(Level currentLevel, int currentLevelNumber, int currentScore, int finalScore, int life) {
        this.currentLevel = currentLevel;
        this.currentLevelNumber = currentLevelNumber;
        this.currentScore = currentScore;
        this.finalScore = finalScore;
        this.life = life;
    }

    /**
     * Get the current level of the game
     * @return the current level of the game
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Get the current level number
     * @return the current level number
     */
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    /**
     * Get score of the current level
     * @return Score of the current level
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Get final scroe of the game
     * @return Final scroe of the game
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Get the number of remain lives
     * @return the number of remain lives
     */
    public int getLife() {
        return life;
    }
}
