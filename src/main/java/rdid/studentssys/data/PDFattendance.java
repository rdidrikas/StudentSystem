package rdid.studentssys.data;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PDFattendance {

    public static void convertCsvToPdf(String csvFile, String pdfFile) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA, 12);

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        float y = 700;
        String line;

        while ((line = br.readLine()) != null) {
            if (y < 50) {
                contentStream.endText();
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                y = 700;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.showText(line);
            contentStream.endText();
            y -= 20;
        }

        contentStream.close();
        document.save(pdfFile);
        document.close();
    }
}
