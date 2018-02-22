package Negocio;

import Modelo.Lavadora;
import Modelo.Mueble;
import Modelo.Producto;
import Modelo.Televisor;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductosService {

    private List<Producto> productos;
    private ClientesService clientes;
    private VentasService ventas;

    public ProductosService() {
        this.productos = new ArrayList();
    }

    public void enlazar(ClientesService clientes, VentasService ventas) {
        this.clientes = clientes;
        this.ventas = ventas;
    }

    private List<Producto> getProductos() {
        return productos;
    }

    public void introducirProducto(Producto p) {

        try {
            productos.add(p);
        } catch (Exception e) {
            throw new RuntimeException("Error al introducir el producto\n" + e.getMessage());
        }
    }

    public Producto buscarProducto(int np) throws Exception {
        Producto producto = null;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == np) {
                producto = productos.get(i);
                break;
            }
        }

        if (producto == null) {
            throw new Exception("El producto no existe.");
        }
        return producto;
    }

    public void elimninarProducto(int nproducto) {
        try {
            Producto productoEliminar = null;
            
            ventas.eliminarVentasPorProducto(nproducto);

            //Eliminamos el producto
            for (Producto p : productos) {
                if (p.getId() == nproducto) {
                    productoEliminar = p;
                }
            }
            productos.remove(productoEliminar);

        } catch (Exception e) {
            throw new RuntimeException("imposible eliminar producto");
        }

    }

    public String imprimirTodosProductos() {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");

        String res = "";
        if (productos.isEmpty()) {
            res = "No hay productos introducidos.";

        } else {
            for (Producto p : productos) {
                if (p instanceof Televisor) {
                    Televisor t = (Televisor) p;
                    res += "\n ID NOMBRE  PRECIO  MARCA   FABRICANTE  TAMAÑO   TIPO    PULGADAS" + "\n" + t.getId() + "   " + t.getNombre() + "   " + t.getPrecio() + t.getMarca() + "   " + t.getFabricante() + "   " + t.getTamanyo() + t.getTipo() + "  " + t.getPulgadas();

                }

                if (p instanceof Lavadora) {
                    Lavadora l = (Lavadora) p;
                    res += "\n ID NOMBRE  PRECIO  MARCA   FABRICANTE  REVOLUCIONES  CARGA" + "\n" + l.getId() + "   " + l.getNombre() + "   " + l.getPrecio() + "  " + l.getMarca() + "         " + l.getFabricante() + "   " + l.getTamanyo() + "     " + l.getRevoluciones() + "     " + l.getCarga();

                }

                if (p instanceof Mueble) {
                    Mueble m = (Mueble) p;
                    res += "\n ID NOMBRE  PRECIO     AÑO FABRICACION              MADERA  ESTILO" + "\n" + m.getId() + "   " + m.getNombre() + "   " + m.getPrecio() + "  " + m.getAnyoFab().format(formateador) + "   " + m.getTipoMadera() + "     " + m.getEstilo();

                }
            }
        }
        return res;
    }
}
