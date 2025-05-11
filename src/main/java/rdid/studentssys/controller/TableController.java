package rdid.studentssys.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.junit.FixMethodOrder;
import rdid.studentssys.data.SaveAttendance;
import rdid.studentssys.design.CalendarView;
import rdid.studentssys.model.Group;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;
import rdid.studentssys.model.StudentManager;

import java.util.ArrayList;
import java.util.List;

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

    private String dialogCSS = getClass().getResource("/rdid/studentssys/css/styles.css").toExternalForm();

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
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Dialog<Student> dialog = new Dialog<>();
            dialog.setTitle("Edit Student " + selectedStudent.getName());
            dialog.setHeaderText("Change Student Details");

            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            TextField nameField = new TextField(selectedStudent.getName());
            TextField surnameField = new TextField(selectedStudent.getSurname());
            TextField emailField = new TextField(selectedStudent.getEmail());
            String groupNames = "";
            for (Group group : selectedStudent.getGroup()) {
                String name = group.getGroupName();
                groupNames = groupNames + name + ",";
            }
            TextField groupField = new TextField(groupNames);
            grid.add(new Label("Name: "), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Surname: "), 0, 1);
            grid.add(surnameField, 1, 1);
            grid.add(new Label("Email: "), 0, 2);
            grid.add(emailField, 1, 2);
            grid.add(new Label("Group(s) (comma separated): "), 0, 3);
            grid.add(groupField, 1, 3);

            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getStylesheets().add(dialogCSS);

            dialog.setResultConverter(buttonType -> {
                if (buttonType == saveButtonType) {
                    String name = nameField.getText();
                    String surname = surnameField.getText();
                    String email = emailField.getText();
                    String groups = groupField.getText();

                    if (!name.isEmpty() && !surname.isEmpty() && !email.isEmpty()) {
                        selectedStudent.setName(name);
                        selectedStudent.setSurname(surname);
                        selectedStudent.setEmail(email);
                        System.out.println("Updated student details: " + name + " " + surname + " " + email);
                    } else {
                        System.out.println("Cannot leave empty fields");
                        handleEditStudentButton();
                    }
                    if (!groups.isEmpty()) {
                        GroupManager.getInstance().removeStudentFromAllGroups(selectedStudent);
                        String[] gr = groups.split(",");
                        for (int i = 0; i < gr.length; i++) {
                            GroupManager.getInstance().addStudentToGroup(selectedStudent, gr[i]);
                        }
                    }
                }
                return null;
            });

            dialog.showAndWait();

        } else {
            System.out.println("No student selected for editing.");
        }
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
        Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            Dialog<Group> dialog = new Dialog<>();
            dialog.setTitle("Edit Group " + selectedGroup.getGroupName());
            dialog.setHeaderText("Change Group Name");

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
                    if (!nameField.getText().isEmpty()){
                        String newGroupName = nameField.getText();
                        selectedGroup.setGroupName(newGroupName);
                        System.out.println("Updated group name to: " + newGroupName);
                    }
                }
                return null;
            });

            dialog.showAndWait();

            mainController.updateDashboard();
        } else {
            System.out.println("No group selected for editing.");
        }
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
