package stickman.levels;

import stickman.model.GameEngine;

public class LevelDirector {

    private LevelBuilder builder;

    public LevelDirector(LevelBuilder builder) {
        this.builder = builder;
    }

    public Level construct() {
        builder.buildLevel();
        return builder.getLevel();
    }
}
