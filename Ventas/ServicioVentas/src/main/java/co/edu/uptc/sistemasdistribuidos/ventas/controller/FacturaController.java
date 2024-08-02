package co.edu.uptc.sistemasdistribuidos.ventas.controller;

import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import co.edu.uptc.sistemasdistribuidos.ventas.repository.FacturaRepository;
import co.edu.uptc.sistemasdistribuidos.ventas.service.PDFGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Clase de Control de Facturas
 * @author Juliana Rincon
 * @author Luisa Merchan
 * @author Juan Diego
 * @author Sebastian Camargo
 *
 */
@RestController
@RequestMapping("api/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con las facturas")
public class FacturaController {

    // Inyección de dependencias para el repositorio de facturas
    @Autowired
    private FacturaRepository facturaRepository;

    // Inyección de dependencias para el servicio de generación de PDF
    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    // Método para crear una nueva factura
    @PostMapping
    @Operation(summary = "Crear una nueva factura")
    public ResponseEntity<ByteArrayResource> createFactura(@RequestBody Factura factura) throws IOException {
        double totalFactura = 0;
        // Calcular el precio total de cada producto en la factura
        for (var detalle : factura.getDetalleProductos()) {
            detalle.setPrecioTotalProducto(detalle.getCantidadProducto() * detalle.getPrecioUnitarioProducto());
            detalle.setFactura(factura);
            totalFactura += detalle.getPrecioTotalProducto();
        }
        factura.setTotalFactura(totalFactura);
        double cambio = factura.getDineroRecibido() - totalFactura;
        factura.setCambio(cambio);
        // Guardar la factura en la base de datos
        Factura savedFactura = facturaRepository.save(factura);

        // Generar el PDF de la factura
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdfGeneratorService.generarPDF(savedFactura, byteArrayOutputStream);

        ByteArrayResource byteArrayResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

        // Configurar los encabezados de la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "factura.pdf");

        // Devolver la respuesta con el PDF generado
        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }

    // Método para obtener todas las facturas
    @GetMapping
    @Operation(summary = "Obtener todas las facturas")
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    // Método para obtener una factura por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una factura por ID")
    public Factura getFacturaById(@PathVariable int id) {
        Optional<Factura> optionalFactura = facturaRepository.findById(id);
        return optionalFactura.orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }
}
