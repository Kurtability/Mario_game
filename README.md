# Mario_game


-To run the game, just hit the Gradle build and Gradle run command in terminal.


List features (i.e., Level Transition, Score and Time, and Save and Load) I have implemented in my extension
- LevelTransition, done by tweaking the JSON configuration file
- Score and Time, done by implementing the Observer pattern
- Save and Load, done by implementing the Prototype pattern and the Memento pattern



List the names of the design patterns I have used in my extension and provide the corresponding file names regarding these patterns
- Prototype pattern, Cloneable.java, Entity.java, LevelImpl.java
- Memento pattern, Caretaker.java, GameMemento.java, GameEngineImpl.java
- Observer pattern, GameEngineImpl.java, LevelImpl.java, Observer.java, CurrentScoreObserver.java, FinalScoreObserver.java




which key on the keyboard should be pressed to demo your load and save.
"S" key to save, "Q" key to load 



*I have changed the flag position on the first level from the original code, so that its easier to test level transition. Rather than having to go through the whole first level, which is time consuming. 
