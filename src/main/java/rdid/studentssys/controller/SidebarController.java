package rdid.studentssys.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import rdid.studentssys.data.CSVhandler;
import rdid.studentssys.data.SaveStudents;
import rdid.studentssys.design.CalendarView;
import rdid.studentssys.model.Group;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;

import java.util.Optional;

public class SidebarController extends Utils {

    private String dialogCSS = getClass().getResource("/rdid/studentssys/css/styles.css").toExternalForm();

    @FXML private VBox sidebar;
    @FXML private HBox statsRow;
    @FXML private VBox studentsContentPane;
    @FXML private VBox attendancePane;
    @FXML private VBox tableActions;

    @FXML private VBox subGroupsSidebar;
    @FXML private VBox subStudentsSidebar;
    @FXML private VBox subImportSidebar;
    @FXML private VBox subExportSidebar;
    @FXML private VBox subImportCSVSidebar;
    @FXML private VBox subImportExcelSidebar;
    @FXML private VBox subExportCSVSidebar;
    @FXML private VBox subExportExcelSidebar;

    private HomeController mainController;

    public void setMainController(HomeController mainController) {
        this.mainController = mainController;
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
        System.out.println("Add Student button clicked");

        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add Student");
        dialog.setHeaderText("Enter student details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField surnameField = new TextField();
        surnameField.setPromptText("Surname");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField groupField = new TextField();
        groupField.setPromptText("Group, comma separated");
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Surname:"), 0, 1);
        grid.add(surnameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Group(s):"), 0, 3);
        grid.add(groupField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getStylesheets().add(dialogCSS);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == saveButtonType) {
                if (!groupField.getText().isEmpty()){
                    String[] groupArr = groupField.getText().split(",");
                    new Student(nameField.getText(), surnameField.getText(), emailField.getText(), groupArr);
                } else {
                    new Student(nameField.getText(), surnameField.getText(), emailField.getText());
                }
            }
            return null;
        });

        dialog.showAndWait();

        mainController.updateDashboard();

    }

    @FXML public void handleViewStudentButton() {
        mainController.getTableController().handleViewStudentButton();
    }

    @FXML public void handleAddGroupButton() {

        Dialog<Group> dialog = new Dialog<>();
        dialog.setTitle("Add Group");
        dialog.setHeaderText("Enter Group Name");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getStylesheets().add(dialogCSS);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == saveButtonType) {
                if (nameField.getText().isEmpty()){
                    System.out.println("Group name cannot be empty");
                    handleAddGroupButton(); // Prompt the user again
                }
                GroupManager.getInstance().createGroup(nameField.getText());
            }
            return null;
        });

        dialog.showAndWait();

        mainController.updateDashboard();
    }

    @FXML public void handleViewGroupsButton(){
        mainController.getTableController().handleViewGroupsButton();
    }

    @FXML public void handleImportCSVStudents(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Import Students CSV");
        dialog.setContentText("File name:");
        dialog.setHeaderText(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fileName -> {
            CSVhandler csvHandler = new CSVhandler();
            csvHandler.studentCSVData(csvHandler.importData("src/main/resources/import/" + fileName));
        });
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

    @FXML public void handleExportCSVGroups() {
        // Handle export data button click
        System.out.println("Export Groups button clicked");
    }

    @FXML public void handleExportExcelGroups() {
        // Handle export data button click
        System.out.println("Export Groups button clicked");
    }

    @FXML public void saveData() {
        // Handle save data button click
        System.out.println("Save Data button clicked");
        SaveStudents saveStudents = new SaveStudents("src/main/resources/data/students.csv");
        saveStudents.saveData();
    }
}
