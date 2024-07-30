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

            double totalFactura = 0;

            for (DetalleProducto detalleProducto : factura.getDetalleProductos()) {
                double totalProducto = detalleProducto.getCantidadProducto() * detalleProducto.getPrecioUnitarioProducto();
                contenidoStream.showText("Producto: " + detalleProducto.getNombreProducto() +
                        ", Cantidad: " + detalleProducto.getCantidadProducto() +
                        ", Precio Unitario: " + detalleProducto.getPrecioUnitarioProducto() +
                        ", Precio Total: " + totalProducto);
                contenidoStream.newLineAtOffset(0, -15);
                totalFactura += totalProducto;
            }

            contenidoStream.newLineAtOffset(0, -15);
            contenidoStream.showText("Total Factura: " + totalFactura);
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
