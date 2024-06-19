package co.edu.uptc.SistemasDistribuidos.Ventas.model;

public class DetalleProducto {
    private String nombreProducto;
    private int cantidadProducto;
    private double precioUnitarioPorrducto;
    private double precioTotalProducto;

    public DetalleProducto(String nombreProducto, int cantidadProducto, double precioUnitarioPorrducto, double precioTotalProducto) {
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioUnitarioPorrducto = precioUnitarioPorrducto;
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

    public double getPrecioUnitarioPorrducto() {
        return precioUnitarioPorrducto;
    }

    public void setPrecioUnitarioPorrducto(double precioUnitarioPorrducto) {
        this.precioUnitarioPorrducto = precioUnitarioPorrducto;
    }

    public double getPrecioTotalProducto() {
        return precioTotalProducto;
    }

    public void setPrecioTotalProducto(double precioTotalProducto) {
        this.precioTotalProducto = precioTotalProducto;
    }

    @Override
    public String toString() {
        return "DetalleProducto{" +
                "nombreProducto='" + nombreProducto + '\'' +
                ", cantidadProducto=" + cantidadProducto +
                ", precioUnitarioPorrducto=" + precioUnitarioPorrducto +
                ", precioTotalProducto=" + precioTotalProducto +
                '}';
    }
}
