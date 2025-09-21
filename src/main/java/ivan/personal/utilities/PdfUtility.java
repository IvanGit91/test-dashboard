package ivan.personal.utilities;

import com.google.gson.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ivan.personal.dto.report.details.ReportDetails;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class PdfUtility {
    private static final BaseColor red = new BaseColor(178, 34, 34);
    private static final BaseColor green = new BaseColor(34, 139, 34);
    private static final Font fontTextBold = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
    private static final Font fontText = new Font(Font.FontFamily.HELVETICA, 13);
    private static final Font fontTextRed = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, red);
    private static final Font fontTextGreen = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, green);
    private static final Font fontTextRedKo = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, red);
    private static final Font fontTextGreenOk = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, green);
    public static final String dataFolder = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    SimpleDateFormat sdf3 = new SimpleDateFormat("EEEE, MM yyyy HH:mm", Locale.ENGLISH);
    TimeZone gmt = TimeZone.getTimeZone("CEST");

    public static String createPdf(String prettyJsonString) {
        String tempFile = null;
        String fileName = dataFolder + "export.pdf";
        try {
            Document document = new Document(PageSize.A4, 0, 0, 30, 30);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            Paragraph dati = new Paragraph();
            dati.add(new Phrase(prettyJsonString, font));
            document.add(dati);

            document.close();
            tempFile = fileName;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return tempFile;
    }

    public String createReportDetailsPdf(JsonObject report, ReportDetails reportDetails) {

        sdf.setTimeZone(gmt);
        sdf.setLenient(false);
        sdf2.setTimeZone(gmt);
        sdf2.setLenient(false);
        sdf3.setTimeZone(gmt);
        sdf3.setLenient(false);

        String tempFile = null;
        String fileName = dataFolder + "export.pdf";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String toJson = gson.toJson(reportDetails);
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(toJson);

        JsonObject head = je.getAsJsonObject();

        String projectName = report.get("projectName").getAsString();
        String name = report.get("name").getAsString();
        String version = report.get("version").getAsString();
        String typeTarget = report.get("typeTarget").getAsString();
        String sut = report.get("sut").getAsString();
        String maker = report.get("maker").getAsString();

        String reason = (report.get("reason").isJsonNull()) ? "null" : report.get("reason").getAsString();
        String created = report.get("created").getAsString();
        String dateHeader = null;
        String dateProject = null;
        try {
            dateHeader = sdf2.format(sdf.parse(created));
            dateProject = sdf3.format(sdf.parse(created));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String status = report.get("status").getAsString();

        try {
            Document pdfDocument = new Document(PageSize.A4, 30, 30, 50, 50);
            PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, new FileOutputStream(fileName));
            pdfWriter.setPageEvent(new PageFooter(projectName, name, dateHeader));
            pdfDocument.open();


            //DATA
            Paragraph dati = new Paragraph();
            dati.add(new Phrase(dateProject, fontTextBold));
            dati.setAlignment(Element.ALIGN_LEFT);
            pdfDocument.add(dati);

            dati = new Paragraph();
            dati.add(new Phrase("Test name: " + name + "\n" +
                    "Version: " + version + "\n" +
                    "Type: " + typeTarget + "\n" +
                    "Project: " + projectName + "\n" +
                    "Target: " + sut + "\n" +
                    "Tester: " + maker + "\n" +
                    "Description: " + reason, fontText));
            dati.setAlignment(Element.ALIGN_LEFT);
            dati.setSpacingAfter(20f);
            pdfDocument.add(dati);


            if (status.equals("FAILED")) {
                dati = new Paragraph("TEST FAILED", fontTextRed);
            } else if (status.equals("PASSED")) {
                dati = new Paragraph("TEST PASSED", fontTextGreen);
            }
            dati.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(dati);


            dati = new Paragraph();
            dati.add(new Phrase("MAIN TEST:", fontTextBold));
            dati.setAlignment(Element.ALIGN_LEFT);
            dati.setSpacingAfter(30f);
            pdfDocument.add(dati);

            // TABLE
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 8, 1});
            table.setSpacingAfter(10f);

            //HEADERS
            PdfPCell tableCell = new PdfPCell();
            Paragraph datiCell = new Paragraph("STEP", fontText);
            datiCell.setAlignment(Element.ALIGN_LEFT);
            datiCell.setLeading(10, 0);
            tableCell.addElement(datiCell);
            table.addCell(tableCell);

            tableCell = new PdfPCell();
            datiCell = new Paragraph("", fontText);
            datiCell.setAlignment(Element.ALIGN_LEFT);
            datiCell.setLeading(10, 0);
            tableCell.addElement(datiCell);
            table.addCell(tableCell);

            tableCell = new PdfPCell();
            datiCell = new Paragraph("", fontText);
            datiCell.setAlignment(Element.ALIGN_LEFT);
            datiCell.setLeading(10, 0);
            tableCell.addElement(datiCell);
            table.addCell(tableCell);

            boolean hasFeedback;
            String statusKey;
            //Foreach on the keywords
            JsonArray reportTestRepeatWrappers = head.getAsJsonArray("reportTestRepeatWrappers");
            if (reportTestRepeatWrappers != null) {
                for (JsonElement elem : reportTestRepeatWrappers) {
                    JsonObject reportTestRepeatWrappersObj = elem.getAsJsonObject();
                    JsonArray routines = reportTestRepeatWrappersObj.getAsJsonArray("routines");
                    for (JsonElement elem2 : routines) {
                        JsonObject routinesObj = elem2.getAsJsonObject();
                        JsonArray repeatList = routinesObj.getAsJsonArray("repeatList");
                        int topCounter = 0;
                        for (JsonElement elem3 : repeatList) {
                            JsonObject repeatListObj = elem3.getAsJsonObject();
                            JsonObject keywordsObj = repeatListObj.get("keywords").getAsJsonObject();
                            for (int counter = 0; counter < keywordsObj.size(); counter++) {
                                JsonObject keywordsObjElem = keywordsObj.get(Integer.toString(counter)).getAsJsonObject();
                                String keyName = keywordsObjElem.get("name").getAsString();

                                hasFeedback = keywordsObjElem.has("feedback");
                                if (hasFeedback) {
                                    JsonObject keywordsFeedback = keywordsObjElem.getAsJsonObject("feedback");
                                    JsonObject keywordsChildren = keywordsFeedback.getAsJsonObject("_children");
                                    JsonObject keywordsStatus = keywordsChildren.getAsJsonObject("status");
                                    statusKey = keywordsStatus.get("_value").getAsString();
                                } else {
                                    statusKey = "-";
                                }

                                tableCell = new PdfPCell();
                                datiCell = new Paragraph(String.valueOf(topCounter), fontText);
                                datiCell.setAlignment(Element.ALIGN_LEFT);
                                datiCell.setLeading(10, 0);
                                tableCell.addElement(datiCell);
                                table.addCell(tableCell);

                                tableCell = new PdfPCell();
                                datiCell = new Paragraph(keyName, fontText);
                                datiCell.setAlignment(Element.ALIGN_LEFT);
                                datiCell.setLeading(10, 0);
                                tableCell.addElement(datiCell);
                                table.addCell(tableCell);

                                tableCell = new PdfPCell();
                                if (statusKey.equals("pass"))
                                    datiCell = new Paragraph("OK", fontTextGreenOk);
                                else if (statusKey.equals("fail"))
                                    datiCell = new Paragraph("NOT OK", fontTextRedKo);
                                else
                                    datiCell = new Paragraph("-");
                                datiCell.setAlignment(Element.ALIGN_LEFT);
                                datiCell.setLeading(10, 0);
                                tableCell.addElement(datiCell);
                                table.addCell(tableCell);
                                topCounter++;
                            }
                        }

                    }
                }
            }
            pdfDocument.add(table);
            pdfDocument.close();
            tempFile = fileName;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

}
