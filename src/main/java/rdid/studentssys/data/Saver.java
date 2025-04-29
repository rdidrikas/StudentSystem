package rdid.studentssys.data;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Saver {
    protected String filePath;

    public Saver(String filePath) {
        this.filePath = filePath;
    }

    public abstract void saveData();

    protected void writeToFile (String[] content) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
