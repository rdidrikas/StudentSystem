package rdid.studentssys.data;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.opencsv.*;
import rdid.studentssys.model.Group;
import rdid.studentssys.model.GroupManager;
import rdid.studentssys.model.Student;

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
            int id;
            try {
                id = Integer.parseInt(row[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid ID format: " + row[0]);
                continue;
            }
            String name = row[1];
            String surname = row[2];
            String email = row[3];
            String[] groupArr = Arrays.copyOfRange(row, 4, row.length);
            if(groupArr[0].isEmpty()){
                new Student(id, name, surname, email);
            } else {
                new Student(id, name, surname, email, groupArr);
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
