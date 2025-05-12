package rdid.studentssys.data;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

public class Excelhandler implements ImportExport {

    @Override
    public List<String[]> importData(String filePath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(filePath)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount < 2) {
                return null;
            }
            List<String[]> data = new ArrayList<>();
            for (int i = 1; i < rowCount; i++) { // Skip header
                Row row = sheet.getRow(i);
                int cellCount = row.getPhysicalNumberOfCells();
                String[] rowData = new String[cellCount];
                for (int j = 0; j < cellCount; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                rowData[j] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                rowData[j] = String.valueOf(cell.getNumericCellValue());
                                rowData[j] = rowData[j].replaceAll("\\.0$", ""); // Remove decimal point
                                break;
                            default:
                                rowData[j] = "";
                        }
                    } else {
                        rowData[j] = "";
                    }
                }
                data.add(rowData);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public void convertCsvToExcel(String csvPath, String excelPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath));
             XSSFWorkbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Students");
            String line;
            int rowNum = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Row row = sheet.createRow(rowNum++);

                for (int colNum = 0; colNum < values.length; colNum++) {
                    Cell cell = row.createCell(colNum);
                    cell.setCellValue(values[colNum]);
                }
            }

            try (FileOutputStream out = new FileOutputStream(excelPath)) {
                workbook.write(out);
            }

            System.out.println("Conversion complete: " + excelPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
