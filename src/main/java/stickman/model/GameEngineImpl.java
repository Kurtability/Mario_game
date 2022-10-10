package stickman.model;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import stickman.Entities.Entity;
import stickman.levels.*;
import org.json.simple.JSONObject;
import stickman.model.Memento.CareTaker;
import stickman.model.Memento.GameMemento;
import stickman.model.ScoreObserver.CurrentScoreObserver;
import stickman.model.ScoreObserver.FinalScoreObserver;

public class GameEngineImpl implements GameEngine {
    Level currentLevel;
    int currentLevelNumber;
    LevelDirector levelDirector;
    JSONObject configuration;

    //Add new attributes
    /**
     * Number of levels
     */
    private int levelCount;
    /**
     * Current level score
     */
    private int currentScore;
    /**
     * Final score
     */
    private int finalScore;
    /**
     * Player's remaining lives
     */
    private int life;
    /**
     * Whether the player has remaining lives
     */
    private boolean hasLife = true;
    /**
     *  Elapsed time since the start of the level
     */
    private double elapsedTime;
    /**
     * Game start time
     */
    private double levelStartTime;
    /**
     * Final score observer
     */
    private FinalScoreObserver finalScoreObserver;
    /**
     * Current score observer
     */
    private CurrentScoreObserver currentScoreObserver;
    /**
     * CareTaker
     */
    private CareTaker careTaker;

    public GameEngineImpl(JSONObject configuration) {
        // Extract the total number of levels from the configuration file
        JSONObject levels = (JSONObject)configuration.get("levels");
        this.levelCount = levels.size();
        this.life = (int)(double)configuration.get("life");
        this.careTaker = new CareTaker();
        this.configuration = configuration;
        this.currentLevelNumber = 1;
        startLevel();
    }

    private void loadLevel(int levelNumber) {
        JSONObject levels = (JSONObject)configuration.get("levels");

        String key = String.valueOf(levelNumber);
        JSONObject level = (JSONObject)levels.get(key);

        if (level != null) {
            levelDirector = new LevelDirector(new DefaultLevelBuilder(level, this));
            currentLevel = levelDirector.construct();
        }
        //
        levelStartTime = System.currentTimeMillis();
        // Initialize the score observer
        currentScoreObserver = new CurrentScoreObserver(currentLevel, this);
        finalScoreObserver = new FinalScoreObserver(currentLevel, this);
    }

    @Override
    public Level getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void startLevel() {
        loadLevel(this.currentLevelNumber);
    }

    @Override
    public boolean jump() {
        return currentLevel.jump();
    }

    @Override
    public boolean moveLeft() {
        currentLevel.moveLeft();
        return false;
    }

    @Override
    public boolean moveRight() {
        currentLevel.moveRight();
        return false;
    }

    @Override
    public boolean stopMoving() {
        currentLevel.stopMoving();
        return false;
    }

    @Override
    public void tick() {
        if (currentLevel.levelLost()) {
            startLevel();
            die();
        }
        currentLevel.tick();

    }

    // Add new methods
    /**
     * Determine whether the current level is the last level
     * @return True if it is the last level
     */
    @Override
    public boolean isLastLevel() {
        if (currentLevelNumber < levelCount) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Load the next level of the current level
     */
    @Override
    public void getNextLevel() {
        currentLevelNumber++;
        loadLevel(currentLevelNumber);
    }

    /**
     * Get the current level number
     * @return the current level number
     */
    @Override
    public int getCurrentLevelNumber() {
        return this.currentLevelNumber;
    }

    /**
     * Set the current level number
     * @param levelNumber the current level number
     */
    @Override
    public void setCurrentLevelNumber(int levelNumber) {
        this.currentLevelNumber = levelNumber;
    }

    /**
     * Set score of the current level
     * @param score of the current level
     */
    @Override
    public void setCurrentScore(int score) {
        this.currentScore = score;
    }

    /**
     * Get score of the current level
     * @return Score of the current level
     */
    @Override
    public int getCurrentScore() {
        return this.currentScore;
    }

    /**
     * Set final score
     * @param score final score
     */
    @Override
    public void setFinalScore(int score) {
        this.finalScore = score;
        if (this.finalScore < 0)
            this.finalScore = 0;
    }

    /**
     * Get final score
     * @return final score
     */
    @Override
    public int getFinalScore() {
        return this.finalScore;
    }

    /**
     * Get the total running time of the game
     * @return the total running time of the game
     */
    @Override
    public double getElapsedTime() {
        elapsedTime = (System.currentTimeMillis() - levelStartTime) / 1000.0;
        return elapsedTime;
    }

    /**
     * The player dies, loses a life
     */
    @Override
    public void die() {
        this.life--;
        if (this.life <= 0) {
            this.hasLife = false;
        }
    }

    /**
     * Determine if the player has remaining lives
     * @return True if the player has remaining lives
     */
    @Override
    public boolean isHasLife() {
        return this.hasLife;
    }

    /**
     * Get the number of remain lives
     * @return the number of remain lives
     */
    @Override
    public int getLife() {
        return this.life;
    }

    /**
     * Set the number of remain lives
     * @param life
     */
    @Override
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Save the current state of the game to the memo
     * @return Memo of the current state of the game
     */
    public GameMemento saveStateToMemento() {
        GameMemento gameMemento = null;
        try {
            gameMemento = new GameMemento(((LevelImp)currentLevel).deepCopy(), currentLevelNumber, currentScore, finalScore, life);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return gameMemento;
    }

    /**
     * Restore the game state from the memo
     * @param gameMemento Stored game state memo
     */
    public void getStateFromMemento(GameMemento gameMemento){
        try {
            this.currentLevel = ((LevelImp) gameMemento.getCurrentLevel()).deepCopy();
            this.currentLevelNumber = gameMemento.getCurrentLevelNumber();
            this.currentScore = gameMemento.getCurrentScore();
            this.finalScore = gameMemento.getFinalScore();
            this.life = gameMemento.getLife();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Save game state
     */
    @Override
    public void save() {
        this.careTaker.add(this.saveStateToMemento());
    }

    /**
     * Reload game state
     */
    @Override
    public void reload() {
        int size = careTaker.getMementoSize();
        this.getStateFromMemento(careTaker.get(size - 1));
    }

}
