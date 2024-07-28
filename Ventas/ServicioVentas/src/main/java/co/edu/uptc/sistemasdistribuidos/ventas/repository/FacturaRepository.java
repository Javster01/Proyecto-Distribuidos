package co.edu.uptc.sistemasdistribuidos.ventas.repository;

import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
}
