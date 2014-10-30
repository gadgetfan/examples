package com.selectyour.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Generates pdf-page
 */
public abstract class PdfGenerator {
    private String realContextPath;
    private Font mainFont;
    private Font smallFont;
    private Font adsFont;
    private Document document;

    protected PdfGenerator(String realContextPath) {
        this.realContextPath = realContextPath;
        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont(getRealContextPath() + "fonts/ARIALUNI.TTF", "cp1251", BaseFont.EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();  //TODO: log
        } catch (DocumentException e) {
            e.printStackTrace();  //TODO: log
        }
        mainFont = new Font(baseFont, Font.DEFAULTSIZE, Font.NORMAL);
        smallFont = new Font(baseFont, Font.DEFAULTSIZE / 2, Font.ITALIC);
        adsFont = new Font(baseFont, Font.DEFAULTSIZE / 2, Font.NORMAL, BaseColor.BLUE);

    }

    public InputStream generatePDF() {
        // step 1: creation of a document-object
        document = new Document();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //!TODO: need to close stream
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            // step 3: we open the document
            document.open();
            // step 4: we add a paragraph to the document
            addMetaData(document);
            addContent(document);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
        // step 5: we close the document
        document.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray()); //!TODO: need to close stream

        return bais;
    }

    /**
     * add metadata to pdf-document
     *
     * @param document
     */
    protected abstract void addMetaData(Document document);

    /**
     * adds content to pdf-document
     *
     * @param document
     */
    protected abstract void addContent(Document document) throws DocumentException;

    public String getRealContextPath() {
        return realContextPath;
    }

    public Font getMainFont() {
        return mainFont;
    }

    public Font getSmallFont() {
        return smallFont;
    }

    public Font getAdsFont() {
        return adsFont;
    }
}