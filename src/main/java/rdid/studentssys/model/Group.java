package rdid.studentssys.model;

import java.time.LocalDate;
import java.util.*;

public class Group {

    private static Group defaultGroup;
    private String groupName;
    private List<Student> students;
    private Map<Student, Map<LocalDate, Boolean>> attendanceRecords;

    public Group(String groupName) {
        this.groupName = groupName;
        this.students = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        if (students.contains(student)) {
            System.out.println("Student already in group.");
            return;
        }
        students.add(student);
        student.setGroup(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setGroup(null);
    }

    public int getGroupSize() {
        return students.size();
    }


    public void updateAttendance(Student student, Map<LocalDate, Boolean> attendance) {
        if (attendanceRecords == null) {
            attendanceRecords = new HashMap<>();
        }
        attendanceRecords.put(student, attendance);
    }

}
