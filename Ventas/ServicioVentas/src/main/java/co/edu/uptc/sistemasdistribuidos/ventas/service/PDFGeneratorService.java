package co.edu.uptc.sistemasdistribuidos.ventas.service;

import co.edu.uptc.sistemasdistribuidos.ventas.model.DetalleProducto;
import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PDFGeneratorService {

    public void generarPDF(Factura factura, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            PDPageContentStream contenidoStream = new PDPageContentStream(documento, pagina);

            contenidoStream.beginText();
            contenidoStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenidoStream.newLineAtOffset(25, 750);
            contenidoStream.showText("Factura ID: " + factura.getIdFactura());
            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Fecha: " + factura.getFecha());
            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Cliente: " + factura.getCliente());
            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Detalles del Producto:");
            contenidoStream.newLineAtOffset(0, -15);

            for (DetalleProducto detalleProducto : factura.getDetalleProductos()) {
                contenidoStream.showText("Producto: " + detalleProducto.getNombreProducto() +
                        ", Cantidad: " + detalleProducto.getCantidadProducto() +
                        ", Precio Unitario: " + detalleProducto.getPrecioUnitarioProducto() +
                        ", Precio Total: " + detalleProducto.getPrecioTotalProducto());
                contenidoStream.newLineAtOffset(0, -15);
            }

            contenidoStream.endText();
            contenidoStream.close();

            documento.save(byteArrayOutputStream);
        }
    }
}
