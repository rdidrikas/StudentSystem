package rdid.studentssys.data;

import rdid.studentssys.model.Group;
import rdid.studentssys.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SaveStudents extends Saver {
    public SaveStudents(String filePath) {
        super(filePath);
    }

    @Override
    public void saveData() {
        String[] content = {"ID", "Name", "Surname", "Email", "Group(s)"};
        overwriteToFile(content);
        for (Student student : Student.getAllStudents()) {
            List<String> studentData = new ArrayList<>();
            studentData.add(String.valueOf(student.getId()));
            studentData.add(student.getName());
            studentData.add(student.getSurname());
            studentData.add(student.getEmail());
            List<Group> groups = student.getGroup();
            for (Group group : groups) {
                studentData.add(group.getGroupName());
            }
            String[] studentDataArray = new String[studentData.size()];
            studentDataArray = studentData.toArray(studentDataArray);
            appendToFile(studentDataArray);
        }
    }
}
