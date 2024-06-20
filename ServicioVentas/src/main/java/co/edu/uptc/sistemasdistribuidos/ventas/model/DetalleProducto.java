package co.edu.uptc.sistemasdistribuidos.ventas.model;

public class DetalleProducto {
    private String nombreProducto;
    private int cantidadProducto;
    private double precioUnitarioProducto;
    private double precioTotalProducto;

    public DetalleProducto(String nombreProducto, int cantidadProducto, double precioUnitarioProducto, double precioTotalProducto) {
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioUnitarioProducto = precioUnitarioProducto;
        this.precioTotalProducto = precioTotalProducto;
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

    @Override
    public String toString() {
        return nombreProducto+"     "+
                cantidadProducto+"     "+
                precioUnitarioProducto +"     "+
                precioTotalProducto;
    }
}

