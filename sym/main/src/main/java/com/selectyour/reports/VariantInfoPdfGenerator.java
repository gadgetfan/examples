package com.selectyour.reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.selectyour.gwtshared.PictureConstants;

import java.io.IOException;

/**
 * generates pdf with variant info and ads
 */
public class VariantInfoPdfGenerator extends PdfGenerator { //TODO: оформить в виде хелпера, как ProbeHelper
    private VariantInfoReportData data;

    public VariantInfoPdfGenerator(String realContextPath, VariantInfoReportData data) {
        super(realContextPath);
        this.data = data;
    }

    @Override
    protected void addMetaData(Document document) {
        //TODO: move russian text to properties
        document.addTitle("Информация о картине");
        document.addSubject("Сгенерировано " + PictureConstants.DOMAIN_NAME);
        document.addKeywords("Картина, Выбор");
        document.addAuthor("www." + PictureConstants.DOMAIN_NAME + ", Выбери твое");
        document.addCreator("www." + PictureConstants.DOMAIN_NAME + ", Выбери твое");
    }

    @Override
    protected void addContent(Document document) throws DocumentException {
        PdfPTable mainTable = new PdfPTable(3);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new float[]{60, 5, 35});
        PdfPCell cellImage = new PdfPCell();
        cellImage.setBorder(PdfPCell.NO_BORDER);

        try {
            Image image = Image.getInstance(data.getImageData());
            cellImage.addElement(image);
        } catch (IOException e) {
            e.printStackTrace();  //TODO: log it
        }

        cellImage.addElement(new Paragraph(data.getAds(), getAdsFont()));
        mainTable.addCell(cellImage);

        PdfPCell cellEmpty = new PdfPCell(); //TODO: make indent instead of special cell
        cellEmpty.setBorder(PdfPCell.NO_BORDER);
        mainTable.addCell(cellEmpty);

        PdfPCell cellDescription = new PdfPCell();
        cellDescription.setBorder(PdfPCell.NO_BORDER);
        cellDescription.addElement(new Paragraph(data.getPrice(), getMainFont()));
        cellDescription.addElement(new Paragraph(" "));

        List descriptionList = new List(List.UNORDERED, 10);
        descriptionList.setListSymbol("\u2022");
        //descriptionList.setIndentationLeft(10f);
        for (String s : data.getDescription()) {
            ListItem item = new ListItem(s, getMainFont());
            //item.setSpacingBefore(10f);
            descriptionList.add(item);
        }
        cellDescription.addElement(descriptionList);
        cellDescription.addElement(new Paragraph(" "));

        for (String s : data.getOther()) {
            cellDescription.addElement(new Paragraph(s, getSmallFont()));
        }

        mainTable.addCell(cellDescription);

        document.add(mainTable);
    }
}
