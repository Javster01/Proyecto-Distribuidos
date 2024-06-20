package co.edu.uptc.sistemasdistribuidos.ventas;

import co.edu.uptc.sistemasdistribuidos.ventas.controller.Control;
import co.edu.uptc.sistemasdistribuidos.ventas.model.DetalleProducto;
import co.edu.uptc.sistemasdistribuidos.ventas.model.Factura;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ServicioVentasApplication {

    public static void main(String[] args)
    {
        List<DetalleProducto> detallesFactura1 = new ArrayList<>();
        Control control = new Control();
        detallesFactura1.add(new DetalleProducto("Leche",2,4000,
                control.calcularPrecioTotalProducto(2,4000)));
        detallesFactura1.add(new DetalleProducto("Cafe",3,10000,
                control.calcularPrecioTotalProducto(3,10000)));
        detallesFactura1.add(new DetalleProducto("Chocolate",4,6000,
                control.calcularPrecioTotalProducto(4,6000)));
        Factura factura1 = new Factura(1,new Date(),"1009202",detallesFactura1,control.calcularTotalFactura(detallesFactura1));
        System.out.println(factura1.toString());

        SpringApplication.run(ServicioVentasApplication.class, args);
    }

}
