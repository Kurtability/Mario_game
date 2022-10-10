package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import stickman.Entities.Entity;
import stickman.levels.Level;
import stickman.model.GameEngine;
import java.util.ArrayList;
import java.util.List;

public class GameWindow {
    private final int width;
    private final int height;
    private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private static final double X_VIEWPORT_MARGIN = 200;
    private static final double Y_VIEWPORT_MARGIN = 100;

    // Add labels to show game time and score
    /**
     * Label showing the running time of the game
     */
    private Label time;
    /**
     * Label showing the current level score
     */
    private Label score;
    /**
     * Label showing the final score of the game
     */
    private Label finalScore;

    public GameWindow(GameEngine model, int width, int height) {
        this.model = model;
        this.pane = new Pane();
        this.width = width;
        this.height = height;

        // Initialize the newly added javafx component
        VBox vBox=new VBox();
        time = new Label("Time:");
        score = new Label("Score: 0");
        finalScore = new Label("FinalScore: 0");
        vBox.getChildren().add(time);
        vBox.getChildren().add(score);
        vBox.getChildren().add(finalScore);
        this.pane.getChildren().add(vBox);

        this.scene = new Scene(pane, width, height);

        this.entityViews = new ArrayList<>();

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        model.getCurrentLevel().getBGDrawer().draw(model, pane);
    }

    public Scene getScene() {
        return this.scene;
    }

    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void draw() {
        model.tick();
        Level currentLevel = model.getCurrentLevel();
        List<Entity> entities = currentLevel.getEntities();

        for (EntityView entityView: entityViews) {
            entityView.markForDelete();
        }

        double heroXPos = model.getCurrentLevel().getHeroX();
        double heroYPos = model.getCurrentLevel().getHeroY();
        heroXPos -= xViewportOffset;
        heroYPos -= yViewportOffset;

        // Correct X-axis camera
        if (heroXPos < X_VIEWPORT_MARGIN) {
            if (xViewportOffset >= 0) { // Don't go further left than the start of the level
                xViewportOffset -= X_VIEWPORT_MARGIN - heroXPos;
                if (xViewportOffset < 0) {
                    xViewportOffset = 0;
                    model.getCurrentLevel().setHeroX(X_VIEWPORT_MARGIN);
                }
            }
        } else if (heroXPos > width - X_VIEWPORT_MARGIN) {
            xViewportOffset += heroXPos - (width - X_VIEWPORT_MARGIN);
        }

        // Correct Y-axis camera
        if (heroYPos > (height - Y_VIEWPORT_MARGIN)) {
            yViewportOffset += heroYPos - (height - Y_VIEWPORT_MARGIN);
            if (yViewportOffset > 0)
                yViewportOffset = 0;
        } else if (heroYPos < Y_VIEWPORT_MARGIN) {
            yViewportOffset -= Y_VIEWPORT_MARGIN - heroYPos;
        }

        currentLevel.getBGDrawer().update(xViewportOffset, yViewportOffset);

        for (Entity entity: entities) {
            boolean notFound = true;
            for (EntityView view: entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (EntityView entityView: entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);

        // Refresh game time and score
        updateGameInfo();
    }

    /**
     *
     */
    private void updateGameInfo() {
        this.time.setText("Time:" + (int)model.getElapsedTime());
        this.score.setText("Score:" + model.getCurrentScore());
        this.finalScore.setText("FinalScore:" + model.getFinalScore());
    }
}
