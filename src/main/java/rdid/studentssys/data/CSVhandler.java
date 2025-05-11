package rdid.studentssys.data;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.opencsv.*;
import rdid.studentssys.model.Group;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;
import rdid.studentssys.model.StudentManager;

public class CSVhandler implements ImportExport {

    @Override
    public List<String[]> importData(String filePath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            reader.readNext(); // Skip header
            List<String[]> data = reader.readAll();
            reader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void exportData(String filePath) {
        System.out.println("Exporting data to " + filePath);
    }

    public void studentCSVData(List<String[]> data) {
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
