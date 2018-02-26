package Negocio;

public class FormatoFechaErroneo extends RuntimeException {

    public FormatoFechaErroneo(String message, String formatoCorrecto, String entrada) {
        super(message + ". Se debe usar el formato " + formatoCorrecto + ". " + entrada + " no sigue el formato.");
    }

}
