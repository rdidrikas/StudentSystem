package rdid.studentssys.model;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private static List<Student> students = new ArrayList<>();
    private static StudentManager instance;

    private StudentManager() {}

    public static StudentManager getInstance() {
        if (instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        GroupManager.getInstance().removeStudentFromAllGroups(student);
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student getStudentByID(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public boolean alreadyHere(String name, String surname, String email) {
        for (Student student : students) {
            if (student.getName().equals(name) && student.getSurname().equals(surname) && student.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
