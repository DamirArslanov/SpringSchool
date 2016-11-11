package school.utils;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import school.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Cheshire on 04.11.2016.
 */
@Service
public class PDFCreator extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map model,
                                    com.lowagie.text.Document document,
                                    com.lowagie.text.pdf.PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) throws Exception {



        final String FONT = "font/Arial/arial.ttf";
        BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, true);
        Font font = new Font(baseFont, 12, Font.NORMAL);

        Set<User> users = (Set<User>) model.get("users");
        PdfPTable table = new PdfPTable(3);

        PdfPCell header1 = new PdfPCell(new Phrase("Имя", font));
        PdfPCell header2 = new PdfPCell(new Phrase("Username", font));
        PdfPCell header3 = new PdfPCell(new Phrase("Пароль", font));
        header1.setHorizontalAlignment(Element.ALIGN_LEFT);
        header2.setHorizontalAlignment(Element.ALIGN_LEFT);
        header3.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell cutLine = new PdfPCell(new Phrase("✄--------✄-------✄--------✄--------✄--------✄--------✄--------✄--------✄--------✄--------✄--------✄--------✄--------✄"));
        cutLine.setColspan(3);
        cutLine.setBorder(1);




        for (User user : users) {
            table.addCell(header1);;
            table.addCell(header2);
            table.addCell(header3);
            table.addCell(new Phrase(user.getParentName(), font));
            table.addCell(new Phrase(user.getUsername(), font));
            table.addCell(new Phrase(user.getPassword(), font));
            table.addCell(cutLine);
//            table.addCell(user.getUsername());
        }
        document.add(table);
    }
}
