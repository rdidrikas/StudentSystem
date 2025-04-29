package rdid.studentssys.data;

import java.util.List;

public class Excelhandler implements ImportExport {
    @Override
    public List<String[]> importData(String filePath) {
        System.out.println("Importing data from " + filePath);
        return null;
    }

    @Override
    public void exportData(String filePath) {

        System.out.println("Exporting data to " + filePath);
    }
}
