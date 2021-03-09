package ma.ac.inpt.exporter;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import javafx.stage.FileChooser;
import ma.ac.inpt.Libsection;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDF {

    public static Table table;
    static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public static void printReport(String zone_name, Date date, String[] res, Image chart1, Image chart2) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("pdf", "*.pdf")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("Rapport_" + zone_name + ".pdf");

        File file = fileChooser.showSaveDialog(Libsection.stage);
        if (file == null)
            throw new Exception("Fichier de sauvegarde mal choisi");
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        table = new Table(new float[6]);

        table.setWidth(UnitValue.createPercentValue(100));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(30, 30, 30, 30);
        Paragraph title = new Paragraph().add(new Text("Libsection: ").setBold().setUnderline().setFontSize(26));
        title.setTextAlignment(TextAlignment.LEFT);
        document.add(title);
        document.add(new Paragraph().add(new Text("").setFontSize(18)));
        document.add(new Paragraph().add(new Text("Nom de l'echantillon :" + zone_name).setFontSize(18)));
        document.add(new Paragraph().add(new Text("Date de scan :" + formatter.format(date)).setFontSize(18)));
        chart1.scale(0.6f, 0.6f);
        chart2.scale(0.7f, 0.7f);
        document.add(new Paragraph().add(chart1).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph().add(chart2).setTextAlignment(TextAlignment.CENTER).setMarginTop(25f).setMarginBottom(5f));
        table.setMarginTop(0.5f);
        table.addHeaderCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
        table.addHeaderCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("P")));
        table.addHeaderCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Mg")));
        table.addHeaderCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("N")));
        table.addHeaderCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("K")));
        table.addHeaderCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Cu")));
        table.addCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(zone_name)));
        table.addCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(res[0])));
        table.addCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(res[1])));
        table.addCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(res[2])));
        table.addCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(res[3])));
        table.addCell(new Cell().setKeepTogether(true).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(res[4])));
        document.add(table);
        document.close();
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
