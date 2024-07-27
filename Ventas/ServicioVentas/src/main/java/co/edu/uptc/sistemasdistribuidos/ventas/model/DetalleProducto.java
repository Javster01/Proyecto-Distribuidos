package co.edu.uptc.sistemasdistribuidos.ventas.model;

import jakarta.persistence.*;

@Entity
public class DetalleProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private int cantidadProducto;

    @Column(nullable = false)
    private double precioUnitarioProducto;

    @Column(nullable = false)
    private double precioTotalProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private Factura factura;

    public DetalleProducto() {}

    public DetalleProducto(String nombreProducto, int cantidadProducto, double precioUnitarioProducto, double precioTotalProducto) {
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioUnitarioProducto = precioUnitarioProducto;
        this.precioTotalProducto = precioTotalProducto;
    }

    // Getters and setters

    @Override
    public String toString() {
        return nombreProducto + "     " +
                cantidadProducto + "     " +
                precioUnitarioProducto + "     " +
                precioTotalProducto;
    }
}
