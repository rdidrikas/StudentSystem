package rdid.studentssys.design;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

import rdid.studentssys.controller.TableController;
import rdid.studentssys.model.AttendanceManager;
import rdid.studentssys.model.Student;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class CalendarView extends GridPane {

    @FXML private Button prevMonthBtn;
    @FXML private Button nextMonthBtn;
    @FXML private Label  monthLabel;

    private Map<LocalDate, AttendanceStatus> attendanceMap = new HashMap<>();
    private Student student;
    private YearMonth currentMonth;
    String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        UNMARKED
    }

    public CalendarView(Student student, Button prevMonthBtn, Button nextMonthBtn, Label monthLabel) {
        this.prevMonthBtn = prevMonthBtn;
        this.nextMonthBtn = nextMonthBtn;
        this.monthLabel = monthLabel;
        this.student = student;
        this.currentMonth = YearMonth.now();
        attendanceMap = AttendanceManager.getInstance().loadAttendance(student.getId());
        generateCalendar(student);
        setupNav();
    }

    private void generateCalendar(Student student) {

        monthLabel.setText(currentMonth.getMonth().name() + " " + currentMonth.getYear());

        LocalDate firstDayOfMonth = currentMonth.atDay(1);

        int startDay = firstDayOfMonth.getDayOfWeek().getValue() % 7; // monday -> 1, tuesday -> 2 etc.
        int numDays = currentMonth.lengthOfMonth();

        for (int col = 0; col < days.length; col++) {
            Label dayLabel = new Label(days[col]);
            dayLabel.getStyleClass().add("day-label");
            dayLabel.setContentDisplay(ContentDisplay.CENTER);
            this.add(dayLabel, col, 0); // column = col, row = 0
        }

        // Create buttons for each day
        for (int day = 1; day <= numDays; day++) {
            Button dayButton = new Button(String.valueOf(day));
            switch (attendanceMap.getOrDefault(LocalDate.of(firstDayOfMonth.getYear(), firstDayOfMonth.getMonth(), day), AttendanceStatus.UNMARKED)) {
                case PRESENT:
                    dayButton.getStyleClass().add("day-button-present");
                    break;
                case ABSENT:
                    dayButton.getStyleClass().add("day-button-absent");
                    break;
                default:
                    dayButton.getStyleClass().add("day-button-unmarked");
            }
            int finalDay = day;
            dayButton.setOnAction(e -> toggleButton(dayButton, LocalDate.of(firstDayOfMonth.getYear(), firstDayOfMonth.getMonth(), finalDay)));  // Event handling for attendance marking
            this.add(dayButton, (startDay + day - 1) % 7, 1 + (startDay + day - 1) / 7);
        }
    }

    private void setupNav() {
        prevMonthBtn.setOnAction(e -> {
            currentMonth = currentMonth.minusMonths(1);
            refreshCalendar();
        });
        nextMonthBtn.setOnAction(e -> {
            currentMonth = currentMonth.plusMonths(1);
            refreshCalendar();
        });
    }

    private void refreshCalendar() {
        this.getChildren().clear();
        generateCalendar(student);
    }

    private void handleAttendance(int day) {
        // Handle attendance marking (perhaps open a dialog or change button color)
        System.out.println("Attendance marked for day " + day);
    }

    private void toggleButton(Button button, LocalDate date) {
        if (button.getStyleClass().contains("day-button-unmarked")) {
            button.getStyleClass().remove("day-button-unmarked");
            button.getStyleClass().add("day-button-present");
            attendanceMap.put(date, AttendanceStatus.PRESENT);
        } else if (button.getStyleClass().contains("day-button-present")) {
            button.getStyleClass().remove("day-button-present");
            button.getStyleClass().add("day-button-absent");
            attendanceMap.put(date, AttendanceStatus.ABSENT);
        } else {
            button.getStyleClass().remove("day-button-absent");
            button.getStyleClass().add("day-button-unmarked");
            attendanceMap.put(date, AttendanceStatus.UNMARKED);
        }
    }

    public Map<LocalDate, AttendanceStatus> getAttendanceMap() {
        return attendanceMap;
    }

    public void setAttendanceMap(Map<LocalDate, AttendanceStatus> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }
}