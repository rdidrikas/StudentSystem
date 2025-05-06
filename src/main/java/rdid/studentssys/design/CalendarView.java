package rdid.studentssys.design;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import java.time.LocalDate;
import java.time.Month;

public class CalendarView extends GridPane {

    public CalendarView() {
        generateCalendar();
    }

    private void generateCalendar() {
        LocalDate firstDayOfMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        int startDay = firstDayOfMonth.getDayOfWeek().getValue();
        int numDays = lastDayOfMonth.getDayOfMonth();

        // Create buttons for each day
        for (int day = 1; day <= numDays; day++) {
            Button dayButton = new Button(String.valueOf(day));
            int finalDay = day;
            dayButton.setOnAction(e -> handleAttendance(finalDay));  // Event handling for attendance marking
            this.add(dayButton, (startDay + day - 1) % 7, (startDay + day - 1) / 7);
        }
    }

    private void handleAttendance(int day) {
        // Handle attendance marking (perhaps open a dialog or change button color)
        System.out.println("Attendance marked for day " + day);
    }
}