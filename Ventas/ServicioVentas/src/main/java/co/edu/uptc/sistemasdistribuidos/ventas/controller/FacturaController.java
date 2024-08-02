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
import org.springframework.web.client.RestTemplate;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con las facturas")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consume-flask")
    public String consumeFlaskService() {
        String flaskServiceUrl = "http://10.200.3.132:5000/api/get_productos";
        ResponseEntity<String> response = restTemplate.getForEntity(flaskServiceUrl, String.class);
        return response.getBody();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva factura")
    public ResponseEntity<ByteArrayResource> createFactura(@RequestBody Factura factura) throws IOException {
        double totalFactura = 0;
        for (var detalle : factura.getDetalleProductos()) {
            detalle.setPrecioTotalProducto(detalle.getCantidadProducto() * detalle.getPrecioUnitarioProducto());
            detalle.setFactura(factura);
            totalFactura += detalle.getPrecioTotalProducto();
        }
        factura.setTotalFactura(totalFactura);
        double cambio = factura.getDineroRecibido() - totalFactura;
        factura.setCambio(cambio);
        Factura savedFactura = facturaRepository.save(factura);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdfGeneratorService.generarPDF(savedFactura, byteArrayOutputStream);

        ByteArrayResource byteArrayResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "factura.pdf");

        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las facturas")
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una factura por ID")
    public Factura getFacturaById(@PathVariable int id) {
        Optional<Factura> optionalFactura = facturaRepository.findById(id);
        return optionalFactura.orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }
}
