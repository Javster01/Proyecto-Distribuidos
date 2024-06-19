package co.edu.uptc.SistemasDistribuidos.Ventas.controller;

import co.edu.uptc.SistemasDistribuidos.Ventas.model.DetalleProducto;
import co.edu.uptc.SistemasDistribuidos.Ventas.model.Factura;

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

    public String mostrarFactura(Factura factura) {
        String cadena = "";
        /*
        cadena = factura.getIdFactura();
        for (DetalleProducto detalleProducto : detalles) {
            cadena
        }
        */
        return "";
    }
}
