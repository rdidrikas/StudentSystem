package rdid.studentssys.data;

import rdid.studentssys.model.Group;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;
import rdid.studentssys.model.StudentManager;

import java.util.Arrays;
import java.util.List;

public interface ImportExport {
    List<String[]> importData(String filePath);
    void exportData(String filePath);
    default void studentCSVData(List<String[]> data) {
        for (String[] row : data) {
            try {
                Integer.parseInt(row[0]);
                String name = row[1];
                String surname = row[2];
                String email = row[3];
                String[] groupArr = Arrays.copyOfRange(row, 4, row.length);
                for (int i = 0; i < groupArr.length; i++) {
                    groupArr[i] = groupArr[i].trim();
                    System.out.println("Group: " + groupArr[i]);
                }
                if(groupArr.length == 0) {
                    StudentManager.getInstance().addStudent(new Student(name, surname, email));
                } else {
                    StudentManager.getInstance().addStudent(new Student(name, surname, email, groupArr));
                }
            } catch (NumberFormatException e) {
                String name = row[0];
                String surname = row[1];
                String email = row[2];
                String[] groupArr = Arrays.copyOfRange(row, 3, row.length);
                for (int i = 0; i < groupArr.length; i++) {
                    groupArr[i] = groupArr[i].trim();
                    System.out.println("Group: " + groupArr[i]);
                }
                if(groupArr.length == 0) {
                    StudentManager.getInstance().addStudent(new Student(name, surname, email));
                } else {
                    StudentManager.getInstance().addStudent(new Student(name, surname, email, groupArr));
                }
            }

        }
        List<Group> allGroups = GroupManager.getInstance().getAllGroups();
        for (Group group : allGroups) {
            System.out.println("Group: " + group.getGroupName());
            for (Student student : group.getStudents()) {
                System.out.println("Student: " + student.getName() + " " + student.getSurname());
            }
        }
    }

}
