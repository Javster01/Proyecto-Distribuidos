package co.edu.uptc.sistemasdistribuidos.ventas.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import java.util.List;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idFactura;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetalleProducto> detalleProductos;

    @Column(nullable = false)
    private double totalFactura;

    @Column(nullable = false)
    private double dineroRecibido;

    @Column(nullable = false)
    private double cambio;

    public Factura() {}

    public Factura(Date fecha, String cliente, List<DetalleProducto> detalleProductos, double totalFactura, double dineroRecibido, double cambio) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.detalleProductos = detalleProductos;
        this.totalFactura = totalFactura;
        this.dineroRecibido = dineroRecibido;
        this.cambio = cambio;
    }

    // Getters and Setters

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
