package co.edu.uptc.sistemasdistribuidos.ventas.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


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
    private List<DetalleProducto> detalleProductos;

    @Column(nullable = false)
    private double totalFactura;

    public Factura() {}

    public Factura(int idFactura, Date fecha, String cliente, List<DetalleProducto> detalleProductos, double totalFactura) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.detalleProductos = detalleProductos;
        this.totalFactura = totalFactura;
    }

    // Getters and setters

    @Override
    public String toString() {
        StringBuilder detalleProductosString = new StringBuilder();
        for (DetalleProducto detalleProducto : detalleProductos) {
            detalleProductosString.append(detalleProducto.toString()).append("\n");
        }
        return "Factura:\n" +
                "IdFactura: " + idFactura +"\n"+
                "Fecha: " + fecha +"\n"+
                "Cliente: " + cliente + '\n' +
                "Producto     Cantidad      PrecioUnitario      PrecioTotalProducto\n" + detalleProductosString +
                "TotalFactura: " + totalFactura;
    }
}
