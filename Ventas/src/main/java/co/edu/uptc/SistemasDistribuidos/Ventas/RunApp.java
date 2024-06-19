package co.edu.uptc.SistemasDistribuidos.Ventas;

import co.edu.uptc.SistemasDistribuidos.Ventas.controller.Control;
import co.edu.uptc.SistemasDistribuidos.Ventas.model.DetalleProducto;
import co.edu.uptc.SistemasDistribuidos.Ventas.model.Factura;
import co.edu.uptc.SistemasDistribuidos.Ventas.model.Producto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RunApp {
    public static void main(String[] args) {
        System.out.println("Hola mundoooo!!!");
        Producto producto1 = new Producto(1,"Leche",10, 4000);
        Producto producto2 = new Producto(2,"Cafe",20, 10000);
        Producto producto3 = new Producto(1,"Chocolate",10, 6000);

        List<DetalleProducto> detallesFactura1 = new ArrayList<>();
        Control control = new Control();
        detallesFactura1.add(new DetalleProducto(producto1.getNombre(),2,producto1.getPrecio(),
                control.calcularPrecioTotalProducto(2,producto1.getPrecio())));
        detallesFactura1.add(new DetalleProducto(producto1.getNombre(),3,producto2.getPrecio(),
                control.calcularPrecioTotalProducto(3,producto2.getPrecio())));
        detallesFactura1.add(new DetalleProducto(producto3.getNombre(),4,producto3.getPrecio(),
                control.calcularPrecioTotalProducto(4,producto3.getPrecio())));

        Factura factura1 = new Factura(1,new Date(),"1009202",detallesFactura1,control.calcularTotalFactura(detallesFactura1));

    }
}
