package rdid.studentssys.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

abstract public class Utils {

    public void toggleExpand(boolean expand, Pane menu) {
        TranslateTransition slideTransition = new TranslateTransition(Duration.millis(250), menu);

        if (expand) {
            slideTransition.setToX(140);
        } else {
            slideTransition.setToX(0);
        }
        slideTransition.play();
    }

    public void toggleVisibility(Pane menu) {

        boolean menuVisible = menu.isVisible();

        TranslateTransition slideTransition = new TranslateTransition(Duration.millis(250), menu);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), menu);

        if (menuVisible) {
            slideTransition.setToX(-menu.getWidth()); // move it left
            slideTransition.setOnFinished(event -> {
                menu.setVisible(false);
                menu.setManaged(false);
            });
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> {
                menu.setVisible(false);
            });
        } else {
            // Prepare menu first
            menu.setTranslateX(-menu.getWidth()); // start off-screen
            menu.setVisible(true);
            menu.setManaged(true);
            menu.setOpacity(0);
            // Slide menu in
            slideTransition.setToX(0); // move to normal position
            fadeTransition.setToValue(1);
        }

        slideTransition.play();
        fadeTransition.play();
    }

}
