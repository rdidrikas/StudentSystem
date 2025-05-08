package rdid.studentssys.data;

public class SaveAttendance extends Saver {
    public SaveAttendance(String filePath) {
        super(filePath);
    }

    @Override
    public void saveData() {
        String[] content = {"Date", "Status"};
        overwriteToFile(content);
    }
}
