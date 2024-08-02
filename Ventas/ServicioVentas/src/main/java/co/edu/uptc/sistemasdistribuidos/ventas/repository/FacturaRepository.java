package co.edu.uptc.sistemasdistribuidos.ventas.repository;

import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Clase interface de Factura del repository
 * @author Juliana Rincon
 * @author Luisa Merchan
 * @author Juan Diego
 * @author Sebastian Camargo
 */
// Anotación que indica que esta interfaz es un repositorio Spring
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    // Hereda métodos para operaciones CRUD y consultas personalizadas
}
