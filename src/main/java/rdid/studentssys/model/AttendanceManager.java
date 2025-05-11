package rdid.studentssys.model;

import com.opencsv.CSVReader;
import rdid.studentssys.design.CalendarView;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceManager {

    private static AttendanceManager instance;

    private AttendanceManager() {}

    public static AttendanceManager getInstance() {
        if (instance == null) {
            instance = new AttendanceManager();
        }
        return instance;
    }

    public Map<LocalDate, CalendarView.AttendanceStatus> loadAttendance(int studentId) {
        String filePath = "src/main/resources/data/attendance/student" + studentId + ".csv";
        Map<LocalDate, CalendarView.AttendanceStatus> attendanceMap = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            reader.readNext(); // Skip header
            List<String[]> data = reader.readAll();
            reader.close();
            for (String[] row : data) {
                LocalDate date = LocalDate.parse(row[0]);
                String status = row[1];
                CalendarView.AttendanceStatus attendanceStatus;
                if (status.equals("PRESENT")) {
                    attendanceStatus = CalendarView.AttendanceStatus.PRESENT;
                } else if (status.equals("ABSENT")) {
                    attendanceStatus = CalendarView.AttendanceStatus.ABSENT;
                } else {
                    attendanceStatus = CalendarView.AttendanceStatus.UNMARKED;
                }
                attendanceMap.put(date, attendanceStatus);
            }
        } catch (Exception e) {
            System.out.println("Error loading attendance data: " + e.getMessage());
        }

        return attendanceMap;
    }

}
