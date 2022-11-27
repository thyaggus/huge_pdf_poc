package br.com.quasar.pdf.service;

import br.com.quasar.pdf.entidade.Linha;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

// https://www.tutorialspoint.com/pdfbox/pdfbox_adding_pages.htm
public class PdfService {

    private static long LINHAS = 10000;

    private static List<Linha> gerarCarga() {

        List<Linha> linhas = new LinkedList<>();

        Linha linha = new Linha();
        linha.setData("13/04/1981");
        linha.setDia("13");
        linha.setHistorico("Vai para puta que o pariu");
        linha.setSubHistorico("Falha técnica é o caralho");
        linha.setDocumento("123456789");
        linha.setValor("R$ 99.999.999,99");

        for (long i = 0; i < LINHAS; i++) {
            linhas.add(linha);
        }

        return linhas;
    }

    private static void createFile() throws IOException {
        Path file = Paths.get("teste.txt");
        List<String> linhas = new LinkedList<>();
        for (Linha l: gerarCarga()) {
            linhas.add(l.getLinha());
        }
        Files.write(file, linhas, StandardCharsets.UTF_8);
    }

    private static void createPdf2() throws IOException {
        String nomeArquivos = "Extrato" + LINHAS + ".pdf";
        PDDocument document = new PDDocument();

        List<Linha>linhaLinkada = gerarCarga();

        float POINTS_PER_INCH = 72;
        float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;

        long linePosition = 1;
        for (int paginas = 0; paginas < (LINHAS/60) + 1; paginas ++) {
            PDPage page = new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210 * POINTS_PER_MM));
            document.addPage( page );
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            contentStream.beginText();
            contentStream.setLeading(8.5f);
            contentStream.newLineAtOffset(0, 550);

            for (int li = 60 * paginas; li < 60*(paginas+1) && li < linhaLinkada.size(); li ++ ) {
                if (linePosition % 2 == 0) {
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                } else {
                    contentStream.setFont(PDType1Font.COURIER, 10);
                }
                contentStream.showText(String.format("[%7d] ", linePosition++) + linhaLinkada.get(li).getLinha());
                contentStream.newLine();

            }
            contentStream.endText();
            contentStream.close();
            contentStream.close();
        }

        document.save(nomeArquivos);
        document.close();
    }

    private static void createPdf() throws IOException {
        PDDocument document = new PDDocument();

        float POINTS_PER_INCH = 72;
        float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
        PDPage page = new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210 * POINTS_PER_MM));
        document.addPage( page );
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

        contentStream.beginText();
        contentStream.setLeading(8.5f);
        contentStream.newLineAtOffset(0, 550);

        long linePosition = 1;
        for (Linha l: gerarCarga()) {
            if (linePosition % 2 == 0) {
                contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
            } else {
                contentStream.setFont(PDType1Font.COURIER, 10);
            }
            contentStream.showText(String.format("[%7d] ", linePosition++) + l.getLinha());
            contentStream.newLine();

            if (linePosition % 60 == 0) {
                document.addPage( page );
            }
        }
        contentStream.endText();
        contentStream.close();
        document.save("Extrato" + LINHAS + ".pdf");
        document.close();
    }

    //-------------------------------------------

    public static void main(String[] args) throws IOException {
        long inicio = System.currentTimeMillis();
        createPdf2();
        System.out.println("#> Tempo: " + (System.currentTimeMillis() - inicio)/1000 + "s");
    }
}
