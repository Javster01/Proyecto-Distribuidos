package co.edu.uptc.sistemasdistribuidos.ventas.controller;

import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import co.edu.uptc.sistemasdistribuidos.ventas.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    @Autowired
    private FacturaRepository facturaRepository;

    @GetMapping
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @PostMapping
    public Factura createFactura(@RequestBody Factura factura) {
        return facturaRepository.save(factura);
    }
}
