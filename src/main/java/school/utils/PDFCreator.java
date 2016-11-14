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
import school.entity.Children;
import school.entity.Teacher;
import school.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ArslanovDamir on 04.11.2016.
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


        List<Children> childrens = (List<Children>) model.get("childrens");
        List<Teacher> teachers = (List<Teacher>) model.get("teachers");
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



        if (childrens != null) {
            for (Children children : childrens) {
                if (children.getParent() != null) {
                    table.addCell(header1);;
                    table.addCell(header2);
                    table.addCell(header3);

                    table.addCell(new Phrase(children.getFIO(), font));
                    table.addCell(new Phrase(children.getParent().getUsername(), font));
                    table.addCell(new Phrase(children.getParent().getPassword(), font));
                    table.addCell(cutLine);
                }
            }
        }

        if (teachers != null) {
            for (Teacher teacher : teachers) {
                    table.addCell(header1);;
                    table.addCell(header2);
                    table.addCell(header3);

                    table.addCell(new Phrase(teacher.getFIO(), font));
                    table.addCell(new Phrase(teacher.getUsername(), font));
                    table.addCell(new Phrase(teacher.getPassword(), font));
                    table.addCell(cutLine);
            }
        }

        document.add(table);
    }
}
