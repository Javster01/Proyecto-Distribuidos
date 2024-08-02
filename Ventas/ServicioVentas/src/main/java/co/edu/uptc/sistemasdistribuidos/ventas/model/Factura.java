package co.edu.uptc.sistemasdistribuidos.ventas.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import java.util.List;

/**
 * @author Juliana Rincon
 * @author Luisa Merchan
 * @author Juan Diego
 * @author Sebastian Camargo
 */
// Anotación que indica que esta clase es una entidad de JPA
@Entity
public class Factura {

    // Campo que representa la clave primaria de la entidad, generada automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idFactura;

    // Campo que representa la fecha de la factura, no puede ser nulo
    @Column(nullable = false)
    private Date fecha;

    // Campo que representa el nombre del cliente, no puede ser nulo
    @Column(nullable = false)
    private String cliente;

    // Relación One-to-Many con la entidad DetalleProducto, con cascada y carga perezosa
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetalleProducto> detalleProductos;

    // Campo que representa el total de la factura, no puede ser nulo
    @Column(nullable = false)
    private double totalFactura;

    // Campo que representa el dinero recibido, no puede ser nulo
    @Column(nullable = false)
    private double dineroRecibido;

    // Campo que representa el cambio a devolver, no puede ser nulo
    @Column(nullable = false)
    private double cambio;

    // Constructor sin parámetros
    public Factura() {}

    // Constructor con parámetros
    public Factura(Date fecha, String cliente, List<DetalleProducto> detalleProductos, double totalFactura, double dineroRecibido, double cambio) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.detalleProductos = detalleProductos;
        this.totalFactura = totalFactura;
        this.dineroRecibido = dineroRecibido;
        this.cambio = cambio;
    }

    // Métodos Getters y Setters

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<DetalleProducto> getDetalleProductos() {
        return detalleProductos;
    }

    public void setDetalleProductos(List<DetalleProducto> detalleProductos) {
        this.detalleProductos = detalleProductos;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public double getDineroRecibido() {
        return dineroRecibido;
    }

    public void setDineroRecibido(double dineroRecibido) {
        this.dineroRecibido = dineroRecibido;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    // Método toString para representar el objeto como una cadena
    @Override
    public String toString() {
        StringBuilder detalleProductosString = new StringBuilder();
        for (DetalleProducto detalleProducto : detalleProductos) {
            detalleProductosString.append(detalleProducto.toString()).append("\n");
        }
        return "Factura:\n" +
                "IdFactura: " + idFactura + "\n" +
                "Fecha: " + fecha + "\n" +
                "Cliente: " + cliente + '\n' +
                "Producto     Cantidad      PrecioUnitario      PrecioTotalProducto\n" + detalleProductosString +
                "TotalFactura: " + totalFactura + "\n" +
                "DineroRecibido: " + dineroRecibido + "\n" +
                "Cambio: " + cambio;
    }
}
