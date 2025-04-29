package rdid.studentssys.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class HomeController {

    @FXML private Button studentsButton;
    @FXML private VBox sidebar;
    @FXML private VBox subGroupsSidebar;
    @FXML private VBox subStudentsSidebar;
    @FXML private VBox subImportSidebar;
    @FXML private VBox subExportSidebar;
    @FXML private VBox subImportCSVSidebar;
    @FXML private VBox subImportExcelSidebar;
    @FXML private VBox subExportCSVSidebar;
    @FXML private VBox subExportExcelSidebar;


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

    @FXML public void toggleMenu() {
        toggleVisibility(sidebar);
    }

    @FXML public void toggleStudentsMenu() {
        toggleVisibility(subStudentsSidebar);
    }

    @FXML public void toggleGroupsMenu() {
        toggleVisibility(subGroupsSidebar);
    }

    @FXML public void toggleImportMenu() {
        toggleVisibility(subImportSidebar);
    }

    @FXML public void toggleExportMenu() {
        toggleVisibility(subExportSidebar);
    }

    @FXML public void toggleImportCSVMenu() {
        toggleVisibility(subImportCSVSidebar);
    }

    @FXML public void toggleImportExcelMenu() {
        toggleVisibility(subImportExcelSidebar);
    }

    @FXML public void toggleExportExcelMenu() {
        toggleVisibility(subExportExcelSidebar);
    }

    @FXML public void toggleExportCSVMenu() {
        toggleVisibility(subExportCSVSidebar);
    }

    @FXML public void handleAddStudentButton() {
        // Handle add student button click
        System.out.println("Add Student button clicked");
    }

    @FXML public void handleViewStudentButton(){
        System.out.println("View Student button clicked");
    }

    @FXML public void handleEditStudentsButton() {
        // Handle manage groups button click
        System.out.println("Manage Groups button clicked");
    }

    @FXML public void handleAddGroupButton() {
        // Handle add group button click
        System.out.println("Add Group button clicked");
    }

    @FXML public void handleViewGroupsButton(){
        // Handle view group button click
        System.out.println("View Group button clicked");
    }

    @FXML public void handleEditGroupsButton() {
        // Handle manage groups button click
        System.out.println("Manage Groups button clicked");
    }

    @FXML public void handleImportCSVStudents(){
        // Handle import students button click
        System.out.println("Import Students button clicked");
    }

    @FXML public void handleImportExcelStudents(){
        // Handle import students button click
        System.out.println("Import Students button clicked");
    }

    @FXML public void handleExportCSVStudents() {
        // Handle export data button click
        System.out.println("Export Students button clicked");
    }

    @FXML public void handleExportExcelStudents() {
        // Handle export data button click
        System.out.println("Export Students button clicked");
    }

    @FXML public void handleImportCSVGroups(){
        // Handle import students button click
        System.out.println("Import Groups button clicked");
    }

    @FXML public void handleImportExcelGroups(){
        // Handle import students button click
        System.out.println("Import Groups button clicked");
    }

    @FXML public void handleExportCSVGroups() {
        // Handle export data button click
        System.out.println("Export Groups button clicked");
    }

    @FXML public void handleExportExcelGroups() {
        // Handle export data button click
        System.out.println("Export Groups button clicked");
    }

}
