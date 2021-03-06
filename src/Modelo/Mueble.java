package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Mueble extends Producto {

    public enum Madera {
        PINO, ROBLE, HAYA
    };

    private LocalDate anyoFab;
    private Madera tipoMadera;
    private String estilo;

    public Mueble() {
        super();
    }

    @Override
    public String imprimirProducto() {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");
        String res = super.imprimirProducto() + "el año de fabricación: " + anyoFab.format(formateador) + " el tipo de madera: " + this.tipoMadera + "el estilo: " + getEstilo();
        return res;

    }

    @Override
    public void setPrecio(double precioBase) {
        double precioFinal = precioBase;

        if (this.tipoMadera == Madera.ROBLE) {
            precioFinal += (precioBase * 0.1);
        }

        if (this.tipoMadera == Madera.PINO) {
            precioFinal -= (precioBase * 0.15);
        }

        this.precio = precioFinal;
    }

    public LocalDate getAnyoFab() {
        return anyoFab;
    }

    public void setAnyoFab(LocalDate anyoFab) {
        this.anyoFab = anyoFab;
    }

    public Madera getTipoMadera() {
        return tipoMadera;
    }

    public void setTipoMadera(Madera madera) {
        this.tipoMadera = madera;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

}
