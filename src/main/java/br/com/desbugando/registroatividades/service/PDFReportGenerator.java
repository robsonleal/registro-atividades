package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PDFReportGenerator {
    public byte[] generateAtividadesReport(List<AtividadeDTO> atividades, String periodoTituloString) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph title = new Paragraph("Relatório de Atividades - " + periodoTituloString);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Chunk linebreak = new Chunk(new DottedLineSeparator());
            document.add(linebreak);

            for (AtividadeDTO atividade : atividades) {
                document.add(new Paragraph("Título: " + atividade.getTitulo()));
                document.add(new Paragraph("Descrição: " + atividade.getDescricao()));

                document.add(new Paragraph("\nMovimentos registrados"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                    .withZone(ZoneId.systemDefault());
                DateTimeFormatter formatterOnlyMinutes = DateTimeFormatter.ofPattern("HH:mm")
                    .withZone(ZoneId.systemDefault());

                for (MovimentoDTO movimento : atividade.getMovimentos()) {
                    String instantInicioString = formatter.format(movimento.getRegistroInicio());
                    String instantFimString = formatterOnlyMinutes.format(movimento.getRegistroFim());

                    document.add(new Paragraph(String.format("%s até %s", instantInicioString,
                        instantFimString)));
                    document.add(new Paragraph(movimento.getDescricao()));
                }

                document.add(new Paragraph(linebreak));
            }

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        return out.toByteArray();
    }
}
