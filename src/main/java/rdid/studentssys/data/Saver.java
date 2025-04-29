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

    protected void overwriteToFile (String[] content) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            writer.writeNext(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void appendToFile (String[] content) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true), CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            writer.writeNext(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
