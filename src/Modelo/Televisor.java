
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
