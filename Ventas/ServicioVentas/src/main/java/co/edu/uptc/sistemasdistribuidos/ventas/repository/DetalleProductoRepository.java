package co.edu.uptc.sistemasdistribuidos.ventas.repository;

import co.edu.uptc.sistemasdistribuidos.ventas.model.DetalleProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleProductoRepository extends JpaRepository<DetalleProducto, Long> {
}
