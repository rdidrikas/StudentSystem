package rdid.studentssys.data;

import java.util.List;

public interface ImportExport {
    List<String[]> importData(String filePath);
    void exportData(String filePath);

}
