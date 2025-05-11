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
import javafx.scene.layout.VBox;
import org.junit.FixMethodOrder;
import rdid.studentssys.data.SaveAttendance;
import rdid.studentssys.design.CalendarView;
import rdid.studentssys.model.Group;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;
import rdid.studentssys.model.StudentManager;

public class TableController extends Utils {

    @FXML Label calendarName;

    @FXML private VBox groupsContentPane;
    @FXML private VBox studentsContentPane;
    @FXML private VBox attendancePane;
    @FXML private VBox studentTableActions;
    @FXML private VBox groupTableActions;
    @FXML private AnchorPane tableContentPane;

    @FXML private TableView<Student> studentTable;
    @FXML private TableView<Group> groupTable;
    @FXML private TableColumn<Group, String> groupNameColumn;
    @FXML private TableColumn<Group, String> studentCountColumn;
    @FXML private TableColumn<Student, String> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> surnameColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, String> groupsColumn;

    @FXML private GridPane calendarGrid;

    private final ObservableList<Student> students = FXCollections.observableArrayList();
    private final ObservableList<Group> groups = FXCollections.observableArrayList();
    private ChangeListener<Student> studentSelectionListener;
    private ChangeListener<Group> groupSelectionListener;
    private boolean studentActionMenuToggled = false;
    private boolean groupActionMenuToggled = false;
    private CalendarView calendar;

    private HomeController mainController;

    public void setMainController(HomeController mainController) {
        this.mainController = mainController;
    }


    public void handleViewStudentButton(){
        toggleVisibility(tableContentPane);
        toggleVisibility(studentsContentPane);
        toggleVisibility(mainController.getStatsRow());
        toggleExpand(true, mainController.getStatsRow());
        toggleVisibility(mainController.getSidebar());
        studentActionMenuToggled = false;

        if (studentsContentPane.isVisible()) {
            loadInitialDataStudent();
            studentSetupTableColumns();
            studentSetupTableListeners();
        } else {
            studentTable.getSelectionModel().selectedItemProperty().removeListener(studentSelectionListener);
            studentTable.getSelectionModel().clearSelection();
            toggleVisibility(studentTableActions);
        }
    }

    public void handleViewGroupsButton() {
        toggleVisibility(tableContentPane);
        toggleVisibility(groupsContentPane);
        toggleVisibility(mainController.getStatsRow());
        toggleExpand(true, mainController.getStatsRow());
        toggleVisibility(mainController.getSidebar());
        groupActionMenuToggled = false;
        if (groupsContentPane.isVisible()) {
            loadInitialDataGroup();
            groupSetupTableColumns();
            groupSetupTableListeners();
        } else {
            groupTable.getSelectionModel().selectedItemProperty().removeListener(groupSelectionListener);
            groupTable.getSelectionModel().clearSelection();
            toggleVisibility(groupTableActions);
        }
    }

    /****** TABLE COLUMNS SETUP ******/

    private void studentSetupTableColumns() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        groupsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.join(", ", cellData.getValue().getGroupNames())
        ));

        studentTable.setItems(students);
    }

    private void groupSetupTableColumns() {
        groupNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String.valueOf(cellData.getValue().getGroupName()))));
        studentCountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGroupSize())));
        groupTable.setItems(groups);
    }

    /****** HANDLE LOADING TABLE DATA ******/

    private void loadInitialDataStudent() {
        students.setAll(StudentManager.getInstance().getAllStudents());
    }

    private void loadInitialDataGroup() {
        groups.setAll(GroupManager.getInstance().getAllGroups());
        for (Group group : groups) {
            System.out.println("Group: " + group.getGroupName());
        }
    }

    /****** SET UP TABLE LISTENERS ******/

    private void studentSetupTableListeners() {
        studentSelectionListener = (
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        handleStudentSelection(newSelection);
                    }
                }
        );
        studentTable.getSelectionModel().selectedItemProperty().addListener(studentSelectionListener);
    }

    private void groupSetupTableListeners() {
        groupSelectionListener = (
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        handleGroupSelection(newSelection);
                    }
                }
        );
        groupTable.getSelectionModel().selectedItemProperty().addListener(groupSelectionListener);
    }

    /****** HANDLE SELECTION ******/

    private void handleStudentSelection(Student selectedStudent) {
        System.out.println("Selected student: " + selectedStudent.getName());
        if (!studentActionMenuToggled) {
            toggleVisibility(studentTableActions);
            studentActionMenuToggled = true;
        }
        // Perform actions based on the selected student
    }

    private void handleGroupSelection(Group selectedGroup) {
        System.out.println("Selected group: " + selectedGroup.getGroupName());
        if (!groupActionMenuToggled) {
            toggleVisibility(groupTableActions);
            groupActionMenuToggled = true;
        }
        // Perform actions based on the selected group
    }

    /****** HANDLE STUDENT BUTTONS ******/

    @FXML public void handleEditStudentButton() {

    }

    @FXML public void handleDeleteStudentButton() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            students.remove(selectedStudent);
            StudentManager.getInstance().deleteStudent(selectedStudent);
            System.out.println("Deleted student: " + selectedStudent.getName());
            studentActionMenuToggled = false;
            toggleVisibility(studentTableActions);
        } else {
            System.out.println("No student selected for deletion.");
        }
        mainController.updateDashboard();
    }

    @FXML
    public void handleViewStudentAttendanceButton() {
        toggleVisibility(studentsContentPane);
        toggleVisibility(studentTableActions);
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

    /****** HANDLE GROUP BUTTONS ******/

    @FXML public void handleEditGroupButton() {

    }

    @FXML public void handleDeleteGroupButton() {
        Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            groups.remove(selectedGroup);
            GroupManager.getInstance().deleteGroup(selectedGroup);
            System.out.println("Deleted group: " + selectedGroup.getGroupName());
            groupActionMenuToggled = false;
            toggleVisibility(groupTableActions);
        } else {
            System.out.println("No group selected for deletion.");
        }
        mainController.updateDashboard();
    }

}
