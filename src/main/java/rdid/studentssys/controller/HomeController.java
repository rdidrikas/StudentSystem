package rdid.studentssys.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import rdid.studentssys.data.CSVhandler;
import rdid.studentssys.data.SaveStudents;
import rdid.studentssys.design.CalendarView;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;
import rdid.studentssys.model.StudentManager;

import java.util.Calendar;
import java.util.Optional;


public class HomeController implements DashboardObserver {

    private String dialogCSS = getClass().getResource("/rdid/studentssys/css/styles.css").toExternalForm();
    private boolean menuToggled = false;
    private boolean actionMenuToggled = false;

    ChangeListener<Student> selectionListener;

    @FXML private Button studentsButton;
    @FXML private Button importCSVStudentsButton;

    @FXML private GridPane calendarGrid;

    @FXML private Label studentCount;
    @FXML private Label groupCount;

    @FXML private HBox statsRow;
    @FXML private VBox sidebar;
    @FXML private VBox subGroupsSidebar;
    @FXML private VBox subStudentsSidebar;
    @FXML private VBox subImportSidebar;
    @FXML private VBox subExportSidebar;
    @FXML private VBox subImportCSVSidebar;
    @FXML private VBox subImportExcelSidebar;
    @FXML private VBox subExportCSVSidebar;
    @FXML private VBox subExportExcelSidebar;
    @FXML private VBox studentsContentPane;
    @FXML private VBox attendancePane;
    @FXML private VBox tableActions;

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> surnameColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, String> groupsColumn;

    private final ObservableList<Student> students = FXCollections.observableArrayList();


    private void toggleDashboardExpand(boolean expand) {
        TranslateTransition slideTransition = new TranslateTransition(Duration.millis(250), statsRow);

        if (expand) {
            slideTransition.setToX(140);
        } else {
            slideTransition.setToX(0);
        }
        slideTransition.play();
    }

    @Override
    public void updateDashboard() {
        studentCount.setText(String.valueOf(StudentManager.getInstance().getAllStudents().size()));
        groupCount.setText(String.valueOf(GroupManager.getInstance().getAllGroups().size()));
        System.out.println("Dashboard updated");
    }

    @FXML public void initialize() {
        updateDashboard();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        groupsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.join(", ", cellData.getValue().getGroupNames())
        ));

        studentTable.setItems(students);

    }

    @FXML public void handleEditStudentButton() {

    }

    @FXML public void handleDeleteStudentButton() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            students.remove(selectedStudent);
            StudentManager.getInstance().deleteStudent(selectedStudent);
            System.out.println("Deleted student: " + selectedStudent.getName());
            actionMenuToggled = false;
            toggleVisibility(tableActions);
        } else {
            System.out.println("No student selected for deletion.");
        }
    }

    @FXML public void handleViewStudentAttendanceButton() {
        toggleVisibility(studentsContentPane);
        toggleVisibility(tableActions);
        toggleVisibility(attendancePane);
        Student student = studentTable.getSelectionModel().getSelectedItem();
        CalendarView calendar = new CalendarView(student);
        calendarGrid.getChildren().add(calendar);
    }

    private void loadInitialData() {
        students.setAll(StudentManager.getInstance().getAllStudents());
    }

    private void setupTableListeners() {
        selectionListener = (
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        handleStudentSelection(newSelection);
                    }
                }
        );
        studentTable.getSelectionModel().selectedItemProperty().addListener(selectionListener);
    }

    private void handleStudentSelection(Student selectedStudent) {
        System.out.println("Selected student: " + selectedStudent.getName());
        if (!actionMenuToggled) {
            toggleVisibility(tableActions);
            actionMenuToggled = true;
        }
        // Perform actions based on the selected student
    }

    @FXML public void toggleMenu() {
        menuToggled = !menuToggled;
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
    }

    @FXML public void handleViewStudentButton(){
        toggleVisibility(studentsContentPane);
        toggleVisibility(statsRow);
        toggleDashboardExpand(true);
        toggleVisibility(sidebar);
        actionMenuToggled = false;

        if (studentsContentPane.isVisible()) {
            loadInitialData();
            setupTableColumns();
            setupTableListeners();
        } else {
            studentTable.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
            studentTable.getSelectionModel().clearSelection();
            toggleVisibility(tableActions);
        }
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Import Students CSV");
        dialog.setContentText("File name:");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getStylesheets().add(dialogCSS);
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

    @FXML public void saveData() {
        // Handle save data button click
        System.out.println("Save Data button clicked");
        SaveStudents saveStudents = new SaveStudents("src/main/resources/data/students.csv");
        saveStudents.saveData();
    }

    private void toggleVisibility(VBox menu) {
        if (menuToggled) {
            toggleDashboardExpand(true);
        } else {
            toggleDashboardExpand(false);
        }

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

    private void toggleVisibility(HBox menu) {

        if (menuToggled) {
            toggleDashboardExpand(true);
        } else {
            toggleDashboardExpand(false);
        }

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
