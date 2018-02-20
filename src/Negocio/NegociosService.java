package Negocio;

import Modelo.Cliente;
import Modelo.Lavadora;
import Modelo.Mayorista;
import Modelo.Mueble;
import Modelo.Particular;
import Modelo.Producto;
import Modelo.Televisor;
import Modelo.Venta;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NegociosService {

    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Venta> ventas;

    NegociosService() {
        productos = new ArrayList();
        clientes = new ArrayList();
        ventas = new ArrayList();
    }

    public void introducirProducto(Producto p) {

        try {
            productos.add(p);
        } catch (Exception e) {
            throw new RuntimeException("Error al introducir el producto\n" + e.getMessage());
        }
    }

    public void introducirCliente(Cliente c) {
        try {
            clientes.add(c);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void introducirVenta(int ncliente, int nproducto, String vend) {
        try {

            Cliente clienteVenta = buscarCliente(ncliente);

            Producto productoVenta = buscarProducto(nproducto);

            Venta v = new Venta();
            v.setCliente(clienteVenta);
            v.setVendedor(vend);
            v.setProducto(productoVenta);
            v.setPrecioVenta(); //calcula el precio de la venta segun el cliente-mayorista

            ventas.add(v);

            clienteVenta.getCompras().add(v);
            productoVenta.getVentas().add(v);

        } catch (Exception e) {
            throw new RuntimeException("No ha sido posible introducir la venta. " + e.getMessage());
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

    public Cliente buscarCliente(int numeroCliente) {
        Cliente cliente = null;

        try {
            for (Cliente c : clientes) {
                if (c.getIdCliente() == numeroCliente) {
                    cliente = c;
                    break;
                }
            }
            
            if (cliente == null) {
                throw new Exception("El cliente con id: " + numeroCliente + " no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        
        return cliente;
    }

    public Venta buscarVenta(int nv) {
        Venta venta = null;

        try {

            for (int i = 0; i < ventas.size(); i++) {
                if (ventas.get(i).getIdVenta() == nv) {
                    venta = ventas.get(i);
                    break;
                }
            }
            if (venta == null) {
                throw new Exception("La venta con id: " + nv + " no existe");
            }
            venta.imprimirVenta();
        } catch (Exception e) {
            throw new RuntimeException("No ha sido posible imprimir la venta" + e.getMessage());
        }

        return venta;
    }

    public void elimninarProducto(int nproducto) {
        try {
            Producto productoEliminar = null;
            //Eliminamos de ventas el producto seleccionado
            List<Venta> ventasEliminar = new ArrayList();
            for (Venta v : ventas) {
                if (v.getProducto().getId() == nproducto) {
                    ventasEliminar.add(v);

                }
            }
            ventas.removeAll(ventasEliminar);

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

    public void eliminarCliente(int numCliente) {
        try {
            // Al eliminar un cliente también eliminamos las ventas asociadas a el

            //Eliminamos las ventas del cliente seleccionado
            List<Venta> ventasEliminar = new ArrayList();
            for (Venta v : ventas) {
                if (v.getCliente().getIdCliente() == numCliente) {
                    ventasEliminar.add(v);

                }
            }
            ventas.removeAll(ventasEliminar);

            //Eliminamos el cliente
            Cliente clienteBorrar = null;
            for (int i = 0; i < clientes.size() && clienteBorrar == null; i++) {
                if (clientes.get(i).getIdCliente() == numCliente) {
                    clienteBorrar = clientes.get(i);
                }
            }

            if (clienteBorrar == null) {
                // hacemos saltar una excepcion que nos lleva directamente al catch
                throw new Exception("No existe ningun cliente con ese Id");
            }

            clientes.remove(clienteBorrar);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void eliminarVenta(int nv) {

        try {

            Venta ventaBorrar = null;
            for (int i = 0; i < ventas.size() && ventas.get(i).getIdVenta() != nv; i++) {

                if (ventas.get(i).getIdVenta() == nv) {
                    ventaBorrar = ventas.get(i);

                }
            }
            if (ventaBorrar == null) {
                // hacemos saltar una excepcion que nos lleva directamente al catch
                throw new Exception("No existe ninguna venta con ese Id");
            }
            // este código solo se ejecuta si todo va bien
            ventas.remove(ventaBorrar);

        } catch (Exception e) {
            throw new RuntimeException("imposible eliminar la venta");
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

    public String imprimirTodosClientes() {
        String res = "";
        if (clientes.isEmpty()) {
            res = "No hay clientes introducidos.";

        } else {
            for (Cliente c : clientes) {

                if (c instanceof Mayorista) {
                    Mayorista m = (Mayorista) c;
                    res += "\n ID NOMBRE  RAZON SOCIAL  CIF   TIPO  DESCUENTO" + "\n" + m.getIdCliente() + "   " + m.getNombre() + "   " + m.getRazonSocial() + "   " + m.getCif() + "   " + m.getTipoMayorista() + "   " + m.getDescuento();

                }
                if (c instanceof Particular) {
                    Particular p = (Particular) c;
                    res += "\n ID NOMBRE  RAZON SOCIAL  DNI" + "\n" + p.getIdCliente() + "   " + p.getNombre() + "      " + p.getRazonSocial() + "   " + p.getDni();

                }
            }
        }
        return res;
    }

    public String imprimirtodasVentas() {
        String res = "";
        if (ventas.isEmpty()) {
            res = "No hay ventas introducidas.";

        } else {
            for (Venta v : ventas) {
                //res += "\n ID VENTA VENDEDOR  CLIENTE PRODUCTO PRECIO VENTA" + "\n" + v.getIdVenta() + "   " + v.getVendedor() + "   " + v.getCliente().getIdCliente() + "  " + v.getProducto().getId() + "   " + v.getPrecioVenta();
                res = String.format("\n ID VENTA VENDEDOR  CLIENTE PRODUCTO PRECIO VENTA\n %d   %s   %d  %d   %f", v.getIdVenta(), v.getVendedor(), v.getCliente().getIdCliente(), v.getProducto().getId(), v.getPrecioVenta());
            }
        }
        return res;
    }
}
