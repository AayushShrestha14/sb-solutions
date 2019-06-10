package com.sb.solutions.core.utils.pdfMaker;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;


/**
 * @author Rujan Maharjan on 4/30/2019
 */
public class PdfMaker {

    public String createPdf(Long cardId) throws IOException {
        try {
            String k = "<html><body> This is my Project </body></html>";
            OutputStream file = new FileOutputStream(new File("C:\\Test.pdf"));
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            InputStream is = new ByteArrayInputStream(k.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
