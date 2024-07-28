package co.edu.uptc.sistemasdistribuidos.ventas.controller;

import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import co.edu.uptc.sistemasdistribuidos.ventas.repository.FacturaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con las facturas")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @PostMapping
    @Operation(summary = "Crear una nueva factura")
    public Factura createFactura(@RequestBody Factura factura) {
        double totalFactura = 0;
        for (var detalle : factura.getDetalleProductos()) {
            detalle.setPrecioTotalProducto(detalle.getCantidadProducto() * detalle.getPrecioUnitarioProducto());
            detalle.setFactura(factura);
            totalFactura += detalle.getPrecioTotalProducto();
        }
        factura.setTotalFactura(totalFactura);
        return facturaRepository.save(factura);
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
