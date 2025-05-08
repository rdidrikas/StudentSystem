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
import javafx.scene.layout.Pane;
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


public class HomeController extends Utils {

    @FXML private SidebarController sidebarController;
    @FXML private TableController tableController;

    private boolean menuToggled = false;
    private boolean actionMenuToggled = false;

    @FXML private Label studentCount;
    @FXML private Label groupCount;
    @FXML private Label HomeName;
    @FXML private Button sidebarChnopcik;

    @FXML private VBox sidebar;
    @FXML private HBox statsRow;
    @FXML private HBox mainHeader;

    @FXML public void toggleMenu() {
        menuToggled = !menuToggled;
        toggleVisibility(sidebar);
        toggleExpand(menuToggled, statsRow);
    }

    public void updateDashboard() {
        studentCount.setText(String.valueOf(StudentManager.getInstance().getAllStudents().size()));
        groupCount.setText(String.valueOf(GroupManager.getInstance().getAllGroups().size()));
        System.out.println("Dashboard updated");
    }

    @FXML public void initialize() {
        updateDashboard();
        sidebarController.setMainController(this);
        tableController.setMainController(this);
    }

    public TableController getTableController() {
        return tableController;
    }

    public HBox getStatsRow() {
        return statsRow;
    }

    public VBox getSidebar() {
        return sidebar;
    }

    public Button getSidebarChnopcik() {
        return sidebarChnopcik;
    }

    public Label getHomeName() {
        return HomeName;
    }

    public HBox getMainHeader() {
        return mainHeader;
    }

}
