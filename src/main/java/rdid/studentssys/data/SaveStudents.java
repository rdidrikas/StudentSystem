package rdid.studentssys.data;

import rdid.studentssys.model.Group;
import rdid.studentssys.model.Student;

public class SaveStudents extends Saver {
    public SaveStudents(String filePath) {
        super(filePath);
    }

    @Override
    public void saveData() {
        String[] content = {"ID", "Name", "Surname", "Email", "Group(s)"};
        writeToFile(content);
        for (Student student : Student.getAllStudents()) {
            String[] studentData = new String[5];
            studentData[0] = String.valueOf(student.getId());
            studentData[1] = student.getName();
            studentData[2] = student.getSurname();
            studentData[3] = student.getEmail();
            StringBuilder groups = new StringBuilder();
            for (Group group : student.getGroup()) {
                groups.append(group.getGroupName()).append(" ");
            }
            studentData[4] = groups.toString().trim();
            writeToFile(studentData);
        }

    }
}
