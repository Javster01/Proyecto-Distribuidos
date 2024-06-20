package co.edu.uptc.sistemasdistribuidos.ventas.model;
import java.util.Date;
import java.util.List;

public class Factura {
    private int idFactura;
    private Date fecha;
    private String cliente;
    private List<DetalleProducto> detalleProductos;
    private double totalFactura;

    public Factura(int idFactura, Date fecha, String cliente, List<DetalleProducto> detalleProductos, double totalFactura) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.detalleProductos = detalleProductos;
        this.totalFactura = totalFactura;
    }

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

    @Override
    public String toString() {
        String  detalleProductosString="";
        for (DetalleProducto detalleProducto : detalleProductos) {
            detalleProductosString += detalleProducto.toString()+"\n";
        }
        return "Factura:\n" +
                "IdFactura: " + idFactura +"\n"+
                "Fecha: " + fecha +"\n"+
                "Cliente: " + cliente + '\n' +
                "Producto     Cantidad      PrecioUnitario      PrecioTotalProducto\n" + detalleProductosString+
                "TotalFactura: " + totalFactura;
    }
}