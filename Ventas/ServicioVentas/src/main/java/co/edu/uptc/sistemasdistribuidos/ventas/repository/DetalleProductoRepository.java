package co.edu.uptc.sistemasdistribuidos.ventas.repository;

import co.edu.uptc.sistemasdistribuidos.ventas.model.DetalleProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
* @author Juliana Rincon
 * @author Luisa Merchan
 * @author Juan Diego
 * @author Sebastian Camargo
 * */
// Anotación que indica que esta interfaz es un repositorio Spring
@Repository
public interface DetalleProductoRepository extends JpaRepository<DetalleProducto, Long> {
    // Hereda métodos para operaciones CRUD y consultas personalizadas
}

