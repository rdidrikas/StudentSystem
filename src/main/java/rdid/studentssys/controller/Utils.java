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
            fadeTransition.setToValue(0.0);
            fadeTransition.setFromValue(1.0);
            // Hide menu after fade out
            fadeTransition.setOnFinished(event -> {
                menu.setVisible(false);
                menu.setManaged(false);
            });
        } else {
            // Prepare menu first
            double width = menu.getBoundsInParent().getWidth(); // forces to calculate width and the anim correctly works
            menu.setTranslateX(-width); // start off-screen
            menu.setVisible(true);
            menu.setManaged(true);
            // Slide menu in
            slideTransition.setToX(0); // move to normal position
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
        }

        slideTransition.play();
        fadeTransition.play();
    }

}
