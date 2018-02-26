package Modelo;

public class Televisor extends Electrodomestico {

    public enum Tipo {
        PLASMA,
        LED,
        LCD,
        OLED
    }

    protected double pulgadas;
    protected Tipo tipo;

    public Televisor() {
        super();
    }

    @Override
    public void setPrecio(double precioBase) {
        double precioFinal = precioBase;

        if (this.pulgadas > 40) {
            precioFinal += (precioBase * 0.1);
        }

        if (this.tipo == Tipo.PLASMA) {
            precioFinal -= (precioBase * 0.1);
        }

        this.precio = precioFinal;
    }

    @Override
    public String imprimirProducto() {
        String res = super.imprimirProducto() + "tipo de TV: " + this.tipo + "con " + this.pulgadas + " pulgadas";
        return res;

    }

    public double getPulgadas() {
        return pulgadas;
    }

    public void setPulgadas(double pulgadas) {
        this.pulgadas = pulgadas;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = Tipo.valueOf(tipo.toUpperCase());
    }

}
