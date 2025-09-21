package ivan.personal.utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.net.MalformedURLException;

public class PageFooter extends PdfPageEventHelper {

    private static final Font font = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    private static final Font font2 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private final String name;
    private final String vfName;
    private final String created;

    public PageFooter(String name, String vfName, String created) {
        this.name = name;
        this.vfName = vfName;
        this.created = created;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        String currPage = String.valueOf(writer.getCurrentPageNumber());

        try {
            PdfPTable table = new PdfPTable(5);
            table.setSpacingAfter(30L);
            //CELL 1
            Image logoM3Alpha = Image.getInstance(getClass().getClassLoader().getResource("img/dash.png"));
            logoM3Alpha.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cell1 = new PdfPCell();
            cell1.addElement(logoM3Alpha);
            cell1.setPadding(13);
            table.addCell(cell1);

            //CELL 2
            Paragraph paragraph = new Paragraph(created + "\n" + "Project: " + vfName + "\n" + "Test name: " + name, font);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            PdfPCell cell2 = new PdfPCell();
            cell2.addElement(paragraph);
            cell2.setPadding(3);
            cell2.setColspan(2);
            table.addCell(cell2);

            //CELL 3
            Paragraph paragraph3 = new Paragraph(currPage, font2);
            paragraph3.setAlignment(Element.ALIGN_CENTER);

            PdfPCell cell3 = new PdfPCell();
            cell3.addElement(paragraph3);
            cell3.setPadding(3);
            table.addCell(cell3);

            //CELL 4
            Image logo = null;
            logo = Image.getInstance(getClass().getClassLoader().getResource("img/dashboard/logo_dash.png"));

            logo.setAlignment(Element.ALIGN_CENTER);

            PdfPCell cell4 = new PdfPCell();
            cell4.addElement(logo);
            cell4.setPadding(8);
            table.addCell(cell4);

            document.add(table);
            document.add(new Phrase("\n"));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}