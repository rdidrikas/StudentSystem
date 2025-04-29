package rdid.studentssys.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class HomeController {

    @FXML
    private Button studentsButton;
    @FXML
    private VBox sidebar;
    @FXML
    private VBox subGroupsSidebar;
    @FXML
    private VBox subStudentsSidebar;


    private void toggleVisibility(VBox menu) {

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
            menu.setVisible(true);
            menu.setManaged(true);
            menu.setTranslateX(-menu.getWidth()); // start off-screen
            menu.setOpacity(0);
            // Slide menu in
            slideTransition.setToX(0); // move to normal position
            fadeTransition.setToValue(1);
        }

        slideTransition.play();
        fadeTransition.play();
    }

    @FXML
    public void toggleMenu() {
        toggleVisibility(sidebar);
    }

    @FXML
    public void toggleStudentsMenu() {
        toggleVisibility(subStudentsSidebar);
    }

    @FXML
    public void toggleGroupsMenu() {
        toggleVisibility(subGroupsSidebar);
    }


    @FXML
    public void handleAddStudentButton() {
        // Handle add student button click
        System.out.println("Add Student button clicked");
    }

    @FXML
    public void handleViewStudentButton(){
        System.out.println("View Student button clicked");
    }

    @FXML
    public void handleEditStudentsButton() {
        // Handle manage groups button click
        System.out.println("Manage Groups button clicked");
    }

    @FXML
    public void handleAddGroupButton() {
        // Handle add group button click
        System.out.println("Add Group button clicked");
    }

    @FXML
    public void handleViewGroupsButton(){
        // Handle view group button click
        System.out.println("View Group button clicked");
    }

    @FXML
    public void handleEditGroupsButton() {
        // Handle manage groups button click
        System.out.println("Manage Groups button clicked");
    }


    @FXML
    public void handleImportDataButton() {
        // Handle import data button click
        System.out.println("Import Data button clicked");
    }

    @FXML
    public void handleExportDataButton() {
        // Handle export data button click
        System.out.println("Export Data button clicked");
    }
}
