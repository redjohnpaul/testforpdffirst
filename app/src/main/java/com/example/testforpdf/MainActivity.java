package com.example.testforpdf;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            createFarmPestInfestationReport();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createFarmPestInfestationReport() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF.pdf");

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.LETTER);
        Document document;
        document = new Document(pdfDocument);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandlerPortrait(document));

        Drawable logo1 = getDrawable(R.drawable.mao);
        Bitmap bitmap = ((BitmapDrawable)logo1).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Drawable logo2 = getDrawable(R.drawable.san_ildefonso);
        Bitmap bitmap2 = ((BitmapDrawable)logo2).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] bitmapData2 = stream2.toByteArray();
        ImageData imageData2 = ImageDataFactory.create(bitmapData2);
        Image image2 = new Image(imageData2);

//        Paragraph farmId = new Paragraph("Farm ID: ").setMarginTop(0).setMarginBottom(0);
//        Paragraph farmName = new Paragraph("Farm Name: ").setMarginTop(0).setMarginBottom(0);
//        Paragraph ownerName = new Paragraph("Owner’s Name: ").setMarginTop(0).setMarginBottom(0);
//        Paragraph farmLocation = new Paragraph("Location: ").setMarginTop(0).setMarginBottom(0);
        Paragraph filler = new Paragraph("");
//        Paragraph reportLabels = new Paragraph("Month - Year\nHarvest Report\nRequested MM/DD/YYYY").setTextAlignment(TextAlignment.CENTER);

        Paragraph reportLabel = new Paragraph("Month - Year\n" +
                "Monthly Pest Report for Single Farm\n" +
                "Requested MM/DD/YYYY\n").setTextAlignment(TextAlignment.CENTER);
        Paragraph farmOwner = new Paragraph("Farm Owner: ").setMarginTop(0).setMarginBottom(1).setFontSize(10f).setBold();
        Paragraph farmID = new Paragraph("Farm ID: ").setMarginTop(0).setMarginBottom(1).setFontSize(10f).setBold();
        Paragraph farmLocation = new Paragraph("Location: ").setMarginTop(0).setMarginBottom(1).setFontSize(10f).setBold();

        image.setWidth(80);
        image.setHeight(80);
        image2.setWidth(80);
        image2.setHeight(80);
        image.setHorizontalAlignment(HorizontalAlignment.LEFT);
        image2.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        float colWidth[]={225f, 300f, 225f};
        Table table = new Table(colWidth);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
        Text greenSakahanda = new Text("Sakahanda");
        Color myColor = new DeviceRgb(37, 163, 88);
        greenSakahanda.setFontColor(myColor);
        table.addCell(new Cell().add(new Paragraph("Municipality of San Ildefonso\n" +
                "Municipal Agriculture Office of San Ildefonso\n").add(greenSakahanda)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setFontSize(14f));
        table.addCell(new Cell().add(image2).setBorder(Border.NO_BORDER));

        float columnWidth[] = {112f, 112f, 112f, 112f, 112f, 112f, 112f, 112f};
        Table table2 = new Table(columnWidth);
        table2.setMarginTop(0);
        table2.addCell(new Cell().add(new Paragraph("Pest Infestation ID").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Affected Plant/s").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Plot ID").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Plot Name").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Pest").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Date Reported").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Severity Level").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Status").setBold()));

        for (int x=0; x<15; x++){
            for (int y=0; y<8; y++){
                table2.addCell(new Cell().add(new Paragraph("content")).setFontSize(11f));
            }
        }

        document.add(filler);
        document.add(filler);
        document.add(table);
        document.add(reportLabel);
        document.add(farmOwner);
        document.add(farmID);
        document.add(farmLocation);
        document.add(filler);
        document.add(filler);
        document.add(table2);
        document.close();
        Toast.makeText(this, "oks na ulet pdf", Toast.LENGTH_SHORT).show();
    }

    private void createIndividualPestInfestationReport() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF.pdf");

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.LETTER);
        Document document;
        document = new Document(pdfDocument);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandlerPortrait(document));

        Drawable logo1 = getDrawable(R.drawable.mao);
        Bitmap bitmap = ((BitmapDrawable)logo1).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Drawable logo2 = getDrawable(R.drawable.san_ildefonso);
        Bitmap bitmap2 = ((BitmapDrawable)logo2).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] bitmapData2 = stream2.toByteArray();
        ImageData imageData2 = ImageDataFactory.create(bitmapData2);
        Image image2 = new Image(imageData2);

//        Paragraph farmId = new Paragraph("Farm ID: ").setMarginTop(0).setMarginBottom(0);
//        Paragraph farmName = new Paragraph("Farm Name: ").setMarginTop(0).setMarginBottom(0);
//        Paragraph ownerName = new Paragraph("Owner’s Name: ").setMarginTop(0).setMarginBottom(0);
//        Paragraph farmLocation = new Paragraph("Location: ").setMarginTop(0).setMarginBottom(0);
        Paragraph filler = new Paragraph("");
//        Paragraph reportLabels = new Paragraph("Month - Year\nHarvest Report\nRequested MM/DD/YYYY").setTextAlignment(TextAlignment.CENTER);

        Paragraph reportLabel = new Paragraph("Month - Year\n" +
                "Individual Pest Report\n" +
                "Requested MM/DD/YYYY\n").setTextAlignment(TextAlignment.CENTER);
        Paragraph farmOwner = new Paragraph("Farm Owner: ").setMarginTop(0).setMarginBottom(1).setFontSize(10f).setBold();
        Paragraph farmID = new Paragraph("Farm ID: ").setMarginTop(0).setMarginBottom(1).setFontSize(10f).setBold();
        Paragraph farmLocation = new Paragraph("Location: ").setMarginTop(0).setMarginBottom(1).setFontSize(10f).setBold();

        Paragraph pestInfestationID = new Paragraph("Pest Infestation ID: ").setMarginTop(0).setMarginBottom(1).setFontSize(14f).setBold();
        Paragraph pestPlotName = new Paragraph("Plot name: ").setMarginTop(0).setMarginBottom(1).setBold();
        Paragraph pestPlotID = new Paragraph("Plot ID: ").setMarginTop(0).setMarginBottom(1).setBold();
        Paragraph pestName = new Paragraph("Pest: ").setMarginTop(0).setMarginBottom(1).setBold();
        Paragraph pestDateReported = new Paragraph("Date Reported: ").setMarginTop(0).setMarginBottom(1).setBold();
        Paragraph pestSeverityLevel = new Paragraph("Severity Level: ").setMarginTop(0).setMarginBottom(1).setBold();
        Paragraph pestDescription = new Paragraph("Description: ").setMarginTop(0).setMarginBottom(1).setBold();
        Paragraph pestStatus = new Paragraph("Status: ").setMarginTop(0).setMarginBottom(1).setBold();

        image.setWidth(80);
        image.setHeight(80);
        image2.setWidth(80);
        image2.setHeight(80);
        image.setHorizontalAlignment(HorizontalAlignment.LEFT);
        image2.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        float colWidth[]={225f, 300f, 225f};
        Table table = new Table(colWidth);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
        Text greenSakahanda = new Text("Sakahanda");
        Color myColor = new DeviceRgb(37, 163, 88);
        greenSakahanda.setFontColor(myColor);
        table.addCell(new Cell().add(new Paragraph("Municipality of San Ildefonso\n" +
                "Municipal Agriculture Office of San Ildefonso\n").add(greenSakahanda)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setFontSize(14f));
        table.addCell(new Cell().add(image2).setBorder(Border.NO_BORDER));
        document.add(filler);
        document.add(filler);
        document.add(table);
        document.add(reportLabel);
        document.add(farmOwner);
        document.add(farmID);
        document.add(farmLocation);
        document.add(filler);
        document.add(filler);
        document.add(pestInfestationID);
        document.add(pestPlotName);
        document.add(pestPlotID);
        document.add(pestName);
        document.add(pestDateReported);
        document.add(pestSeverityLevel);
        document.add(pestDescription);
        document.add(pestStatus);
        document.close();
        Toast.makeText(this, "oks na ulet pdf", Toast.LENGTH_SHORT).show();
    }

    private void createHarvestReport() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF.pdf");

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.LETTER.rotate());
        Document document;
        document = new Document(pdfDocument);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandler(document));

        Drawable logo1 = getDrawable(R.drawable.mao);
        Bitmap bitmap = ((BitmapDrawable)logo1).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Drawable logo2 = getDrawable(R.drawable.san_ildefonso);
        Bitmap bitmap2 = ((BitmapDrawable)logo2).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] bitmapData2 = stream2.toByteArray();
        ImageData imageData2 = ImageDataFactory.create(bitmapData2);
        Image image2 = new Image(imageData2);

        Paragraph farmId = new Paragraph("Farm ID: ").setMarginTop(0).setMarginBottom(0);
        Paragraph farmName = new Paragraph("Farm Name: ").setMarginTop(0).setMarginBottom(0);
        Paragraph ownerName = new Paragraph("Owner’s Name: ").setMarginTop(0).setMarginBottom(0);
        Paragraph farmLocation = new Paragraph("Location: ").setMarginTop(0).setMarginBottom(0);
        Paragraph filler = new Paragraph("");
        Paragraph reportLabels = new Paragraph("Month - Year\nHarvest Report\nRequested MM/DD/YYYY").setTextAlignment(TextAlignment.CENTER);

        image.setWidth(80);
        image.setHeight(80);
        image2.setWidth(80);
        image2.setHeight(80);
        image.setHorizontalAlignment(HorizontalAlignment.LEFT);
        image2.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        float colWidth[]={225f, 300f, 225f};
        Table table = new Table(colWidth);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
        Text greenSakahanda = new Text("Sakahanda");
        Color myColor = new DeviceRgb(37, 163, 88);
        greenSakahanda.setFontColor(myColor);
        table.addCell(new Cell().add(new Paragraph("Municipality of San Ildefonso\n" +
                "Municipal Agriculture Office of San Ildefonso\n").add(greenSakahanda)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setFontSize(14f));
        table.addCell(new Cell().add(image2).setBorder(Border.NO_BORDER));

        float columnWidth[] = {100f, 100f, 100f, 100f, 100f, 100f, 100f, 100f, 100f};
        Table table2 = new Table(columnWidth);
        table2.setMarginTop(0);
        table2.addCell(new Cell().add(new Paragraph("Harvest ID").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Plot ID").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Plot Name").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Crop").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Sowing Date").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Harvest Date").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Weight (tons)").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Market Price (per ton)").setBold()));
        table2.addCell(new Cell().add(new Paragraph("Gross Income").setBold()));

        for (int x=0; x<15; x++){
            for (int y=0; y<9; y++){
                table2.addCell(new Cell().add(new Paragraph("content")));
            }
        }


        document.add(table);
        document.add(reportLabels);
        document.add(filler).add(filler);
        document.add(farmId).add(farmName).add(ownerName).add(farmLocation);
        document.add(filler).add(filler);
        document.add(table2);
        document.close();
        Toast.makeText(this, "oks na ulet pdf", Toast.LENGTH_SHORT).show();
    }

    protected class TextFooterEventHandler implements IEventHandler {

        protected Document doc;

        public TextFooterEventHandler(Document doc) {
            this.doc = doc;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNumber(page);

            String p = "Report Generated By SAKAHANDA Mobile Farming Application\n";
            String p2 = "Generated MM/DD/YYYY";

            canvas.beginText();
            try {
                canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA_OBLIQUE), 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            canvas.moveText((pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2 + doc.getLeftMargin(), pageSize.getTop() - doc.getTopMargin() + 10)
                    .moveText(-360, (pageSize.getBottom() + doc.getBottomMargin()) - (pageSize.getTop() + doc.getTopMargin()) + 50)
                    .showText(p)
                    .showText(p2)
                    .moveText(675, -0)
                    .showText(String.format(String.valueOf(pageNum)))
                    .endText()
                    .release();
        }

//            public void writeTotal(Event event){
//                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
//                int pages = docEvent.getDocument().getNumberOfPages();
//                Rectangle pageSize = docEvent.getPage().getPageSize();
//                PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
//
//                canvas.beginText();
//                try {
//                    canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA_OBLIQUE), 5);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                canvas.moveText((pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2 + doc.getLeftMargin(), pageSize.getTop() - doc.getTopMargin() + 10)
//                        .moveText(-360, (pageSize.getBottom() + doc.getBottomMargin()) - (pageSize.getTop() + doc.getTopMargin()) + 50)
//                        .moveText(675, -0)
//                        .showText(String.format("Page %d of %d", pageNum, pages))
//                        .endText()
//                        .release();
//            }

        }

    protected class TextFooterEventHandlerPortrait implements IEventHandler {

        protected Document doc;

        public TextFooterEventHandlerPortrait(Document doc) {
            this.doc = doc;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNumber(page);

            String p = "Report Generated By SAKAHANDA Mobile Farming Application\n";
            String p2 = "Generated MM/DD/YYYY";

            Drawable logo1 = getDrawable(R.drawable.logo);
            Bitmap bitmap = ((BitmapDrawable)logo1).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            imageData.setHeight(30);
            imageData.setWidth((float) 25.3);
            Log.i("Height", String.valueOf(imageData.getHeight()));
            Log.i("Width", String.valueOf(imageData.getWidth()));
            Image image = new Image(imageData);

            Drawable logo2 = getDrawable(R.drawable.logo_text);
            Bitmap bitmap2 = ((BitmapDrawable)logo2).getBitmap();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            byte[] bitmapData2 = stream2.toByteArray();
            ImageData imageData2 = ImageDataFactory.create(bitmapData2);
            imageData2.setHeight((float) 17.8);
            imageData2.setWidth((float) 91.9);
            Image image2 = new Image(imageData2);

            canvas.beginText();
            try {
                canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA_OBLIQUE), 9);
            } catch (IOException e) {
                e.printStackTrace();
            }
            canvas.moveText((pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2 + doc.getLeftMargin(), pageSize.getTop() - doc.getTopMargin() + 10)
                    .moveText(-280, (pageSize.getBottom() + doc.getBottomMargin()) - (pageSize.getTop() + doc.getTopMargin()) + 50);
                    canvas.addImage(imageData,30,30,true);
                    canvas.addImage(imageData2,60,35,true);
                    canvas.moveText(500, -0)
                    .showText(String.format(String.valueOf(pageNum)))
                    .endText()
                    .release();
        }

//            public void writeTotal(Event event){
//                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
//                int pages = docEvent.getDocument().getNumberOfPages();
//                Rectangle pageSize = docEvent.getPage().getPageSize();
//                PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
//
//                canvas.beginText();
//                try {
//                    canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA_OBLIQUE), 5);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                canvas.moveText((pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2 + doc.getLeftMargin(), pageSize.getTop() - doc.getTopMargin() + 10)
//                        .moveText(-360, (pageSize.getBottom() + doc.getBottomMargin()) - (pageSize.getTop() + doc.getTopMargin()) + 50)
//                        .moveText(675, -0)
//                        .showText(String.format("Page %d of %d", pageNum, pages))
//                        .endText()
//                        .release();
//            }

    }
    }