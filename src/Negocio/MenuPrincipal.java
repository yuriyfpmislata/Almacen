package Negocio;

import Modelo.Cliente;
import Modelo.Lavadora;
import Modelo.Mayorista;
import Modelo.Mueble;
import Modelo.Mueble.Madera;
import Modelo.Particular;
import Modelo.Producto;
import Modelo.Televisor;
import Modelo.TipoMayorista;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuPrincipal {

    ProductosService productos;
    ClientesService clientes;
    VentasService ventas;

    MenuPrincipal() {
        /**
         * Planteamiento division servicios
         *
         * Los 3 servicios se crean en menu principal; tras construirse, se enlazan.
         *
         * Cada servicio usa su nombre comun (p.ej "productos") como la lista. En cambio, usa el nombre comun de otros servicios como referencia al otro servicio (de forma que pueda llamar a sus
         * metodos).
         *
         * ** Se podría refactorizar llamando listaProductos a la lista propiamente dicha.
         *
         */
        this.productos = new ProductosService();
        this.clientes = new ClientesService();
        this.ventas = new VentasService();

        this.productos.enlazar(clientes, ventas);
        this.clientes.enlazar(productos, ventas);
        this.ventas.enlazar(clientes, productos);
    }

    public void inciarAplicacion() {
        try {
            // menu Principal
            int opcion = -1;
            do {
                System.out.println("1.Productos");
                System.out.println("2.Clientes");
                System.out.println("3.Ventas");
                System.out.println("0. Para Salir");
                System.out.println("Introduzca la opcion");
                Scanner sc = new Scanner(System.in);
                opcion = sc.nextInt();
                if (opcion == 1) {
                    menuProductos();
                }
                if (opcion == 2) {
                    menuClientes();
                }
                if (opcion == 3) {
                    menuVentas();
                }

            } while (opcion != 0);

            System.out.println("Gracias por usar nuestra aplicacion");
            System.out.println("Hasta la proxima");

        } catch (Exception e) {
            System.out.println("Opcion no valida");
            this.inciarAplicacion();
        }
    }

    private void menuProductos() {
        try {
            int opcionProductos = -1;
            do {
                System.out.println("1.Introducir Producto");
                System.out.println("2.Dar de baja un producto");
                System.out.println("3.Imprimir los datos de un producto");
                System.out.println("4.Imprimir todos los productos");
                System.out.println("0. Salir del menu");
                Scanner sc = new Scanner(System.in);
                opcionProductos = sc.nextInt();

                if (opcionProductos == 1) {
                    Producto p = datosProducto();
                    productos.introducirProducto(p);
                }
                if (opcionProductos == 2) {
                    System.out.println("Introduzca el número de producto: ");
                    int num = sc.nextInt();
                    productos.elimninarProducto(num);
                    System.out.println("Se ha eliminado el producto con número: " + num);
                }
                if (opcionProductos == 3) {
                    System.out.println("Introduzca el número de producto: ");
                    int nprod = sc.nextInt();
                    System.out.println(productos.buscarProducto(nprod).imprimirProducto());
                }
                if (opcionProductos == 4) {
                    System.out.println(productos.imprimirTodosProductos());
                }

            } while (opcionProductos != 0);
        } catch (Exception e) {
            System.out.println("Opcion no valida" + e.getMessage());
            this.menuProductos();
        }
    }

    public Producto datosProducto() throws Exception {
        Scanner sc = new Scanner(System.in);
        Producto producto = null;
        String nombre;
        double precio;
        int opcion = -1;
        do {
            System.out.println("Introduzca el nombre: ");
            nombre = sc.nextLine();

            System.out.println("Introduzca precio base: ");
            precio = Double.parseDouble(sc.nextLine());

            System.out.println("Introduzca el tipo de producto: ");
            System.out.println("1. Mueble");
            System.out.println("2. Lavadora");
            System.out.println("3. Televisor");
            opcion = sc.nextInt();
            if (opcion == 1) {
                producto = pedirMueble();
            }
            if (opcion == 2) {
                producto = pedirLavadora();
            }
            if (opcion == 3) {
                producto = pedirTelevisor();
            }
            if (producto != null) {
                producto.setNombre(nombre);
                producto.setPrecio(precio);
            }

        } while (opcion != 1 && opcion != 2 && opcion != 3);

        if (producto != null) {
            System.out.println("Creado producto con ID: " + producto.getId());
        }

        return producto;
    }

    public Mueble pedirMueble() throws ParseException {
        Mueble m = new Mueble();
        Scanner sc = new Scanner(System.in);

        m.setTipoMadera(pedirMadera());

        System.out.println("Introduzca el estilo:");
        m.setEstilo(sc.nextLine());

        System.out.println("Introduzca la fecha (dd/mes/yyyy): ");

        try {
            String entradaFecha = sc.nextLine();
            this.validarFecha(entradaFecha);

            m.setAnyoFab(this.validarFecha(entradaFecha));
        } catch (FormatoFechaErroneo e) {
            System.err.println(e.getMessage());
            m.setAnyoFab(LocalDate.now());
        }

        return m;

    }

    public Lavadora pedirLavadora() {
        Scanner sc = new Scanner(System.in);
        Lavadora l = new Lavadora();

        System.out.println("Introduzca las revoluciones(rpm): ");
        int rev = Integer.parseInt(sc.nextLine());
        l.setRevoluciones(rev);

        System.out.println("Introduzca la capacidad (kg): ");
        int c = Integer.parseInt(sc.nextLine());
        l.setCarga(c);

        return l;
    }

    public Televisor pedirTelevisor() {
        Televisor tv = new Televisor();
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca las pulgadas: ");
        double pulgadas = Double.parseDouble(sc.nextLine());
        tv.setPulgadas(pulgadas);

        System.out.println("Introduzca el tipo: ");

        int intentosRestantes = 4;
        while (intentosRestantes > 0) {
            try {
                tv.setTipo(sc.nextLine());
                intentosRestantes = 0;
            } catch (Exception e) {
                System.err.println("Tipo de televisor INVÁLIDO");
                intentosRestantes--;
            }
        }

        return tv;
    }

    private Mueble.Madera pedirMadera() {
        Mueble.Madera m = null;
        int opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Introduzca el tipo de Madera");

            int contador = 0;
            for (Madera tipo : Mueble.Madera.values()) {
                System.out.println((contador++) + ". " + tipo.toString());
            }

            opcion = Integer.parseInt(sc.nextLine());
            // la opcion NO esta en el rango correcto
        } while (!(opcion < 0) && !(opcion < Mueble.Madera.values().length));

        m = Mueble.Madera.values()[opcion];

        return m;
    }

    private void menuClientes() {
        Scanner sc = new Scanner(System.in);

        try {
            int opcionClientes = -1;
            do {
                System.out.println("1.Introducir Cliente");
                System.out.println("2.Dar de baja un cliente");
                System.out.println("3.Imprimir los datos de un cliente");
                System.out.println("4.Imprimir todos los clientes");
                System.out.println("0. Salir del menu");

                opcionClientes = sc.nextInt();
                if (opcionClientes == 1) {
                    Cliente c = datosCliente();
                    clientes.introducirCliente(c);
                }
                if (opcionClientes == 2) {
                    System.out.println("Introduzca el número de cliente: ");
                    int num = sc.nextInt();
                    try {
                        clientes.eliminarCliente(num);
                        System.out.println("Se ha eliminado el cliente con número: " + num);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                    }
                }
                if (opcionClientes == 3) {
                    System.out.println("Introduzca el número de cliente: ");
                    int ncliente = sc.nextInt();
                    Cliente resultado = clientes.buscarCliente(ncliente);
                    if (resultado != null) {
                        System.out.println(resultado);
                    } else {
                        System.err.println("No existe ningun cliente con ese Id");
                    }
                }
                if (opcionClientes == 4) {
                    System.out.println(clientes.imprimirTodosClientes());
                }

            } while (opcionClientes != 0);
        } catch (Exception e) {
            System.out.println("Opcion no valida");
            this.menuClientes();
        }

    }

    public Cliente datosCliente() {
        Scanner sc = new Scanner(System.in);

        Cliente cliente = null;

        String nombre;
        String razonSocial;

        int opcion = -1;
        do {
            System.out.println("Introduzca el nombre: ");
            nombre = sc.nextLine();

            System.out.println("Introduzca razon social: ");
            razonSocial = sc.nextLine();

            System.out.println("Introduzca el tipo de cliente: ");
            System.out.println("1. Particular");
            System.out.println("2. Mayorista");

            opcion = sc.nextInt();
            if (opcion == 1) {
                cliente = pedirParticular();
            }
            if (opcion == 2) {
                cliente = pedirMayorista();
            }
            if (cliente != null) {
                cliente.setNombre(nombre);
                cliente.setRazonSocial(razonSocial);
            }

        } while (opcion != 1 && opcion != 2 && opcion != 3);

        if (cliente != null) {
            System.out.println("Creado cliente con ID: " + cliente.getIdCliente());
        }

        return cliente;
    }

    public char calcularLetraDNI(int dni) {
        char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        return letras[dni % letras.length];
    }

    public Particular pedirParticular() {
        Scanner sc = new Scanner(System.in);
        String dni = "";
        boolean dniInvalido = true;

        Particular particular = null;

        while (dniInvalido) {
            System.out.println("Introduzca el DNI [sin la letra]: ");
            dni = sc.nextLine();

            try {
                if (dni.length() < 8) {
                    // longitud < 8 -> insuficiente
                    throw new Exception("Longitud inválida del DNI introducido");
                } else if (dni.length() == 8) {
                    // DNI sin letra -> calcular letra
                    int dniComoNumero;

                    // comprobar si la cadena contiene solo numeros
                    try {
                        dniComoNumero = Integer.parseInt(dni);
                    } catch (Exception e) {
                        throw new Exception("El DNI no puede contener letras");
                    }

                    dni += calcularLetraDNI(dniComoNumero);
                } else if (dni.length() > 8) {
                    // posible DNI con letra -> cortar y calcular letra de todos modos
                    String trozo8 = dni.substring(0, 8);
                    int dniComoNumero;

                    // comprobar si la cadena contiene solo numeros
                    try {
                        dniComoNumero = Integer.parseInt(trozo8);
                    } catch (Exception e) {
                        throw new Exception("El DNI debe empezar por 8 números");
                    }

                    dni = trozo8 + calcularLetraDNI(dniComoNumero);
                }
                // el dni es valido si no se ha lanzado excepcion (saltado al catch)
                dniInvalido = false;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        particular = new Particular();
        particular.setDni(dni);

        return particular;
    }

    public Mayorista pedirMayorista() {
        Scanner sc = new Scanner(System.in);

        String cif;
        TipoMayorista tipoMayorista;
        double descuento;

        Mayorista mayorista = null;

        System.out.println("Introduzca el CIF: ");
        cif = sc.nextLine();

        tipoMayorista = pedirTipoMayorista();

        System.out.println("Introduzca el descuento: ");
        descuento = Double.parseDouble(sc.nextLine());

        mayorista = new Mayorista();
        mayorista.setCif(cif);
        mayorista.setTipoMayorista(tipoMayorista);
        mayorista.setDescuento(descuento);

        return mayorista;
    }

    public Modelo.TipoMayorista pedirTipoMayorista() {
        Modelo.TipoMayorista tipoMayorista = null;

        int opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Introduzca el tipo de Mayorista");

            int contador = 0;
            for (TipoMayorista tipo : Modelo.TipoMayorista.values()) {
                System.out.println((contador++) + ". " + tipo.toString());
            }

            opcion = Integer.parseInt(sc.nextLine());
            // la opcion NO esta en el rango correcto
        } while (!(opcion < 0) && !(opcion < Modelo.TipoMayorista.values().length));

        tipoMayorista = Modelo.TipoMayorista.values()[opcion];

        return tipoMayorista;
    }

    private void menuVentas() {
        Scanner sc = new Scanner(System.in);

        try {
            String opcionVentas = "-1";
            do {
                System.out.println("1.Introducir Venta");
                System.out.println("2.Dar de baja una venta");
                System.out.println("3.Imprimir los datos de una venta");
                System.out.println("4.Imprimir todas las ventas");
                System.out.println("0. Salir del menu");
                opcionVentas = sc.nextLine();

                if (opcionVentas.equals("1")) {
                    System.out.println("Introduce el número de cliente.");
                    int nv = Integer.parseInt(sc.nextLine());
                    System.out.println("Introduce el número de producto.");
                    int np = Integer.parseInt(sc.nextLine());
                    System.out.println("Introduce el nombre del vendedor: ");
                    String v = sc.nextLine();
                    ventas.introducirVenta(nv, np, v);
                }
                if (opcionVentas.equals("2")) {
                    System.out.println("Introduzca número de venta: ");
                    int nv = Integer.parseInt(sc.nextLine());
                    ventas.eliminarVenta(nv);
                }
                if (opcionVentas.equals("3")) {
                    System.out.println("Introduzca número de venta: ");
                    int nv = Integer.parseInt(sc.nextLine());
                    ventas.buscarVenta(nv);
                }
                if (opcionVentas.equals("4")) {
                    System.out.println(ventas.imprimirtodasVentas());
                }

            } while (!opcionVentas.equals("0"));

        } catch (Exception e) {
            System.out.println("Opcion no valida. " + e.getMessage());
            this.menuVentas();
        }

    }

    private LocalDate validarFecha(String fecha) throws ParseException {
        DateTimeFormatter formateador;

        formateador = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");

        LocalDate fec = null;

        try {
            fec = LocalDate.parse(fecha, formateador);
        } catch (DateTimeParseException e) {
            throw new FormatoFechaErroneo("Formato de fecha erróneo", "dd/MMMM/yyyy", fecha);
        }

        return fec;
    }

}
