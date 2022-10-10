package stickman.model.Memento;

import java.util.ArrayList;
import java.util.List;

/**
 * The Caretaker class is responsible for restoring the state of the object from Memento
 */
public class CareTaker {
    /**
     * Stored memo
     */
    private List<GameMemento> mementoList = new ArrayList<GameMemento>();

    /**
     * Add a memo
     * @param gameState Game state
     */
    public void add(GameMemento gameState){
        mementoList.add(gameState);
    }

    /**
     * Get a memo
     * @param index Memo serial number
     * @return Game state
     */
    public GameMemento get(int index){
        return mementoList.get(index);
    }

    /**
     * Get memo size
     * @return memo size
     */
    public int getMementoSize() {
        return mementoList.size();
    }
}
