package rdid.studentssys.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rdid.studentssys.model.Group;
import rdid.studentssys.model.Student;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {


    @Test
    @DisplayName("Test if the default group is created correctly")
    public void testCreateStudent() {
        // Create a new student
        Student student = new Student("John", "Doe", "johndoe@gmail.com");

        // Test if the student is created correctly
        assertNotNull(student);
        assertEquals("John", student.getName());
        assertEquals("Doe", student.getSurname());
        assertEquals("johndoe@gmail.com", student.getEmail());
        assertEquals(1, student.getId());
        assertNotNull(student.getGroup());
    }

    @Test
    public void testAddStudentToGroup() {
        // Create student and group
        Student student = new Student("John", "Doe", "johndoe@gmail.com");
        Group group = new Group("Math Group");

        // Add student to group
        group.addStudent(student);

        // Test if the student was added to the group
        assertTrue(group.getStudents().contains(student));
    }

    @Test
    public void testAttendance() {
        // Create student and mark attendance
        Student student = new Student("John", "Doe", "johndoe@gmail.com");
        student.markAttendance(LocalDate.now(), true);

        // Check if attendance was marked correctly
        assertTrue(student.getAttendance().containsKey(LocalDate.now()));
        assertTrue(student.getAttendance().get(LocalDate.now()));
    }
}