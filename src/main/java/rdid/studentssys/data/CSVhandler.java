package rdid.studentssys.data;

public class CSVhandler implements ImportExport {
    @Override
    public void importData(String filePath) {
        System.out.println("Importing data from " + filePath);
    }

    @Override
    public void exportData(String filePath) {
        System.out.println("Exporting data to " + filePath);
    }
}
