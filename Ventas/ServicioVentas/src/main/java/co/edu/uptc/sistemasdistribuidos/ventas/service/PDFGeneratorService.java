package co.edu.uptc.sistemasdistribuidos.ventas.service;

import co.edu.uptc.sistemasdistribuidos.ventas.model.DetalleProducto;
import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * Clase que Genera el PDF de la Factura de Venta
 *  @author Juliana Rincon
 *  @author Luisa Merchan
 *  @author Juan Diego
 * @author Sebastian Camargo
 */
@Service
public class PDFGeneratorService {

    public void generarPDF(Factura factura, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage(PDRectangle.A4);
            documento.addPage(pagina);

            PDPageContentStream contenidoStream = new PDPageContentStream(documento, pagina);

            // Encabezado
            contenidoStream.beginText();
            contenidoStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contenidoStream.newLineAtOffset(25, 750);
            contenidoStream.showText("Factura");
            contenidoStream.endText();

            // Detalles de la factura
            contenidoStream.beginText();
            contenidoStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenidoStream.newLineAtOffset(25, 720);
            contenidoStream.showText("Factura ID: " + factura.getIdFactura());
            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Fecha: " + factura.getFecha());
            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Cliente: " + factura.getCliente());
            contenidoStream.endText();

            // Tabla de productos
            float margin = 25;
            float yStart = 670;
            float tableWidth = pagina.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;

            contenidoStream.setLineWidth(1f);
            contenidoStream.beginText();
            contenidoStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenidoStream.newLineAtOffset(margin, yPosition);
            contenidoStream.showText("Nombre del Producto");
            contenidoStream.newLineAtOffset(150, 0);
            contenidoStream.showText("Cantidad");
            contenidoStream.newLineAtOffset(100, 0);
            contenidoStream.showText("Precio Unitario");
            contenidoStream.newLineAtOffset(100, 0);
            contenidoStream.showText("Precio Total");
            contenidoStream.endText();

            yPosition -= 20;

            for (DetalleProducto detalleProducto : factura.getDetalleProductos()) {
                double totalProducto = detalleProducto.getCantidadProducto() * detalleProducto.getPrecioUnitarioProducto();

                contenidoStream.moveTo(margin, yPosition);
                contenidoStream.lineTo(margin + tableWidth, yPosition);
                contenidoStream.stroke();

                contenidoStream.beginText();
                contenidoStream.setFont(PDType1Font.HELVETICA, 12);
                contenidoStream.newLineAtOffset(margin, yPosition - 15);
                contenidoStream.showText(detalleProducto.getNombreProducto());
                contenidoStream.newLineAtOffset(150, 0);
                contenidoStream.showText(String.valueOf(detalleProducto.getCantidadProducto()));
                contenidoStream.newLineAtOffset(100, 0);
                contenidoStream.showText(String.valueOf(detalleProducto.getPrecioUnitarioProducto()));
                contenidoStream.newLineAtOffset(100, 0);
                contenidoStream.showText(String.valueOf(totalProducto));
                contenidoStream.endText();

                yPosition -= 20;
            }

            // Línea final de la tabla
            contenidoStream.moveTo(margin, yPosition);
            contenidoStream.lineTo(margin + tableWidth, yPosition);
            contenidoStream.stroke();

            // Total, dinero recibido y cambio
            yPosition -= 20;
            contenidoStream.beginText();
            contenidoStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenidoStream.newLineAtOffset(margin, yPosition);
            contenidoStream.showText("Total Factura: " + factura.getTotalFactura());
            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Dinero Recibido: " + factura.getDineroRecibido());
            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Cambio: " + factura.getCambio());
            contenidoStream.endText();

            contenidoStream.close();
            documento.save(byteArrayOutputStream);
        }
    }
}
