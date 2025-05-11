package rdid.studentssys.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import rdid.studentssys.data.SaveAttendance;
import rdid.studentssys.design.CalendarView;
import rdid.studentssys.model.Student;
import rdid.studentssys.model.StudentManager;

public class TableController extends Utils {

    @FXML Label calendarName;

    @FXML private VBox studentsContentPane;
    @FXML private VBox attendancePane;
    @FXML private VBox tableActions;
    @FXML private AnchorPane tableContentPane;

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> surnameColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, String> groupsColumn;

    @FXML private GridPane calendarGrid;

    private final ObservableList<Student> students = FXCollections.observableArrayList();
    private ChangeListener<Student> selectionListener;
    private boolean actionMenuToggled = false;
    private CalendarView calendar;

    private HomeController mainController;

    public void setMainController(HomeController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleViewStudentAttendanceButton() {
        toggleVisibility(studentsContentPane);
        toggleVisibility(tableActions);
        toggleVisibility(attendancePane);
        toggleVisibility(mainController.getMainHeader());
        Student student = studentTable.getSelectionModel().getSelectedItem();

        calendarGrid.getChildren().clear();

        calendar = new CalendarView(student);
        calendarGrid.getChildren().add(calendar);

        calendarName.setText("Attendance for " + student.getName() + " " + student.getSurname() );
        calendarName.getStyleClass().add("calendar-name");
        calendarName.setContentDisplay(ContentDisplay.CENTER);
    }

    public void handleViewStudentButton(){
        toggleVisibility(tableContentPane);
        toggleVisibility(studentsContentPane);
        toggleVisibility(mainController.getStatsRow());
        toggleExpand(true, mainController.getStatsRow());
        toggleVisibility(mainController.getSidebar());
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
        mainController.updateDashboard();
    }

    @FXML public void handleSaveAttendance(){
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            int id = selectedStudent.getId();
            String filePath = "src/main/resources/data/attendance/student" + id + ".csv";
            SaveAttendance saveAttendance = new SaveAttendance(filePath);
            saveAttendance.setAttendanceMap(calendar.getAttendanceMap());
            saveAttendance.saveData();
            System.out.println("Attendance saved for student: " + selectedStudent.getName());
        } else {
            System.out.println("No student selected for saving attendance.");
        }
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

}
