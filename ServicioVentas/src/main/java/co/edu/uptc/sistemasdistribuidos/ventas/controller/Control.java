package co.edu.uptc.sistemasdistribuidos.ventas.controller;

import co.edu.uptc.sistemasdistribuidos.ventas.model.DetalleProducto;
import java.util.List;

public class Control {
    public double calcularPrecioTotalProducto(int cantidad, double precioProducto) {
        return cantidad * precioProducto;
    }

    public double calcularTotalFactura(List<DetalleProducto> detalles) {
        double totalFactura = 0;
        for (DetalleProducto detalleProducto : detalles) {
            totalFactura += detalleProducto.getPrecioTotalProducto();
        }
        return totalFactura;
    }
}