package rdid.studentssys.data;

import rdid.studentssys.design.CalendarView;

import java.time.LocalDate;
import java.util.Map;

public class SaveAttendance extends Saver {

    private Map<LocalDate, CalendarView.AttendanceStatus> attendanceMap;

    public SaveAttendance(String filePath) {
        super(filePath);
    }

    @Override
    public void saveData() {
        String[] content = {"Date", "Status"};
        overwriteToFile(content);
        for (Map.Entry<LocalDate, CalendarView.AttendanceStatus> entry : attendanceMap.entrySet()) {
            String[] attendanceData = {entry.getKey().toString(), entry.getValue().toString()};
            appendToFile(attendanceData);
        }

    }

    public void setAttendanceMap(Map<LocalDate, CalendarView.AttendanceStatus> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }

}
