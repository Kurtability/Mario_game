package stickman.model;

import stickman.levels.Level;

public interface GameEngine {
    Level getCurrentLevel();

    void startLevel();

    // Hero inputs - boolean for success (possibly for sound feedback)
    boolean jump();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();

    void tick();

    // Add new methods
    boolean isLastLevel();
    void getNextLevel();
    int getCurrentLevelNumber();
    void setCurrentLevelNumber(int levelNumber);
    void setCurrentScore(int score);
    void setFinalScore(int score);
    int getFinalScore();
    int getCurrentScore();
    double getElapsedTime();
    void die();
    boolean isHasLife();
    int getLife();
    void setLife(int life);

    void save();
    void reload();

}
