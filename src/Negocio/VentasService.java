package Negocio;

import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Venta;
import java.util.ArrayList;
import java.util.List;

public class VentasService {

    private List<Venta> ventas;
    private ClientesService clientes;
    private ProductosService productos;

    public VentasService() {
        this.ventas = new ArrayList();
    }

    public void enlazar(ClientesService clientes, ProductosService productos) {
        this.clientes = clientes;
        this.productos = productos;
    }

    private List<Venta> getVentas() {
        return ventas;
    }

    public void introducirVenta(int ncliente, int nproducto, String vend) {
        try {

            Cliente clienteVenta = clientes.buscarCliente(ncliente);

            Producto productoVenta = productos.buscarProducto(nproducto);

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

    public void eliminarVentasPorProducto(int nproducto) {
        //Eliminamos de ventas el producto seleccionado
        List<Venta> ventasEliminar = new ArrayList();

        for (Venta v : this.getVentas()) {
            if (v.getProducto().getId() == nproducto) {
                ventasEliminar.add(v);

            }
        }

        this.getVentas().removeAll(ventasEliminar);
    }

    public void eliminarVentasPorCliente(int numCliente) {
        //Eliminamos las ventas del cliente seleccionado
        List<Venta> ventasEliminar = new ArrayList();
        for (Venta v : this.getVentas()) {
            if (v.getCliente().getIdCliente() == numCliente) {
                ventasEliminar.add(v);

            }
        }
        this.getVentas().removeAll(ventasEliminar);
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
