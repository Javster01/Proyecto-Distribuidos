package co.edu.uptc.sistemasdistribuidos.ventas.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Clase Detalle Producto
 *  @author Juliana Rincon
 *  @author Luisa Merchan
 *  @author Juan Diego
 * @author Sebastian Camargo
 */
// Anotación que indica que esta clase es una entidad de JPA
@Entity
public class DetalleProducto {
    // Campo que representa la clave primaria de la entidad, generada automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // Campo que representa el nombre del producto, no puede ser nulo
    @Column(nullable = false)
    private String nombreProducto;

    // Campo que representa la cantidad del producto, no puede ser nulo
    @Column(nullable = false)
    private int cantidadProducto;
    // Campo que representa el precio unitario del producto, no puede ser nulo
    @Column(nullable = false)
    private double precioUnitarioProducto;
    // Campo que representa el precio total del producto, no puede ser nulo
    @Column(nullable = false)
    private double precioTotalProducto;
    // Relación Many-to-One con la entidad Factura, con carga perezosa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    @JsonBackReference
    private Factura factura;
    // Constructor sin parámetros
    public DetalleProducto() {}

    public DetalleProducto(String nombreProducto, int cantidadProducto, double precioUnitarioProducto) {
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioUnitarioProducto = precioUnitarioProducto;
        this.precioTotalProducto = cantidadProducto * precioUnitarioProducto;
    }

    // Metodos Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getPrecioUnitarioProducto() {
        return precioUnitarioProducto;
    }

    public void setPrecioUnitarioProducto(double precioUnitarioProducto) {
        this.precioUnitarioProducto = precioUnitarioProducto;
    }

    public double getPrecioTotalProducto() {
        return precioTotalProducto;
    }

    public void setPrecioTotalProducto(double precioTotalProducto) {
        this.precioTotalProducto = precioTotalProducto;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    // Método toString para representar el objeto como una cadena
    @Override
    public String toString() {
        return nombreProducto + "     " +
                cantidadProducto + "     " +
                precioUnitarioProducto + "     " +
                precioTotalProducto;
    }
}
