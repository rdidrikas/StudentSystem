package rdid.studentssys.data;

import java.util.List;

public interface ImportExport {
    List<String[]> importData(String filePath);
    void exportData(String filePath);

    // void importGroups(String filePath);
    // void exportGroups(String filePath);

    // void importStudents(String filePath);
    // void exportStudents(String filePath);
}
