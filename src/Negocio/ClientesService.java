package Negocio;

import Modelo.Cliente;
import Modelo.Mayorista;
import Modelo.Particular;
import java.util.ArrayList;
import java.util.List;

public class ClientesService {

    private List<Cliente> clientes;
    private ProductosService productos;
    private VentasService ventas;

    public ClientesService() {
        this.clientes = new ArrayList();
    }

    public void enlazar(ProductosService productos, VentasService ventas) {
        this.productos = productos;
        this.ventas = ventas;
    }

    private List<Cliente> getClientes() {
        return clientes;
    }

    public void introducirCliente(Cliente c) {
        try {
            clientes.add(c);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

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

    public void eliminarCliente(int numCliente) {
        try {
            // Al eliminar un cliente también eliminamos las ventas asociadas a el
            
            ventas.eliminarVentasPorCliente(numCliente);

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

}
