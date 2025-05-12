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


}
