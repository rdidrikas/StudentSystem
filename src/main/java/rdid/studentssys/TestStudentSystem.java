package rdid.studentssys;

import org.junit.Test;
import rdid.studentssys.model.Group;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;
import rdid.studentssys.data.CSVhandler;

import java.time.LocalDate;
import java.util.List;


import static org.junit.Assert.*;

public class TestStudentSystem {

    @Test
    public void testCreateStudent() {
        // Create a new student
        Student student = new Student(0, "John", "Doe", "johndoe@gmail.com");

        // Test if the student is created correctly
        assertNotNull(student);
        assertEquals("John", student.getName());
        assertEquals("Doe", student.getSurname());
        assertEquals("johndoe@gmail.com", student.getEmail());
        assertEquals(0, student.getId());
        assertEquals(student.getGroup().get(0).getGroupName(), "Default Group");
    }

    @Test
    public void testAddStudentToGroup() {
        // Create student and group
        Student student = new Student(1, "John", "Doe", "johndoe@gmail.com");
        Group group = new Group("Math Group");

        // Add student to group
        group.addStudent(student);

        // Test if the student was added to the group
        assertTrue(group.getStudents().contains(student));
    }

    @Test
    public void testAttendance() {
        // Create student and mark attendance
        Student student = new Student(2, "John", "Doe", "johndoe@gmail.com");
        student.markAttendance(LocalDate.now(), true);

        // Check if attendance was marked correctly
        assertTrue(student.getAttendance().containsKey(LocalDate.now()));
        assertTrue(student.getAttendance().get(LocalDate.now()));

        System.out.println(GroupManager.getInstance().getAllGroups().get(0).getStudents().get(0));
    }
    @Test
    public void testData(){
        CSVhandler csvHandler = new CSVhandler();
        String filePath = "src/main/resources/test.csv";
        List<String[]> data = csvHandler.importData(filePath);
        assertNotNull(data);
        csvHandler.studentCSVData(data);
    }
}
