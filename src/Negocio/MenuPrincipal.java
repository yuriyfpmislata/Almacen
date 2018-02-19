package Negocio;

import Modelo.Cliente;
import Modelo.Lavadora;
import Modelo.Mayorista;
import Modelo.Mueble;
import Modelo.Particular;
import Modelo.Producto;
import Modelo.Televisor;
import Modelo.TipoMayorista;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuPrincipal {

    NegociosService servicio;

    MenuPrincipal() {
        servicio = new NegociosService();
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
                    servicio.introducirProducto(p);
                }
                if (opcionProductos == 2) {
                    System.out.println("Introduzca el número de producto: ");
                    int num = sc.nextInt();
                    servicio.elimninarProducto(num);
                }
                if (opcionProductos == 3) {
                    System.out.println("Introduzca el número de producto: ");
                    int nprod = sc.nextInt();
                    System.out.println(servicio.buscarProducto(nprod).imprimirProducto());
                }
                if (opcionProductos == 4) {
                    System.out.println(servicio.imprimirTodosProductos());
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
        String opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Introduzca el tipo de Madera");
            System.out.println("1.Pino");
            System.out.println("2.Roble");
            System.out.println("3.Haya");

            opcion = sc.nextLine();

        } while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("1"));

        if (opcion.equals("1")) {
            m = Mueble.Madera.PINO;
        }
        if (opcion.equals("2")) {
            m = Mueble.Madera.ROBLE;
        }
        if (opcion.equals("3")) {
            m = Mueble.Madera.HAYA;
        }
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
                    servicio.introducirCliente(c);
                }
                if (opcionClientes == 2) {
                    System.out.println("Introduzca el número de cliente: ");
                    int num = sc.nextInt();
                    try {
                        servicio.eliminarCliente(num);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                    }
                }
                if (opcionClientes == 3) {
                    System.out.println("Introduzca el número de cliente: ");
                    int ncliente = sc.nextInt();
                    Cliente resultado = servicio.buscarCliente(ncliente);
                    if (resultado != null) {
                        System.out.println(resultado);
                    } else {
                        System.err.println("No existe ningun cliente con ese Id");
                    }
                }
                if (opcionClientes == 4) {
                    System.out.println(servicio.imprimirTodosClientes());
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

        String opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Introduzca el tipo de Mayorista");
            System.out.println("1.Gran superfície");
            System.out.println("2.Tienda");

            opcion = sc.nextLine();

        } while (!opcion.equals("1") && !opcion.equals("2"));

        if (opcion.equals("1")) {
            tipoMayorista = Modelo.TipoMayorista.GRAN_SUPERFICIE;
        }
        if (opcion.equals("2")) {
            tipoMayorista = Modelo.TipoMayorista.TIENDA;
        }

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
                    servicio.introducirVenta(nv, np, v);
                }
                if (opcionVentas.equals("2")) {
                    System.out.println("Introduzca número de venta: ");
                    int nv = Integer.parseInt(sc.nextLine());
                    servicio.eliminarVenta(nv);
                }
                if (opcionVentas.equals("3")) {
                    System.out.println("Introduzca número de venta: ");
                    int nv = Integer.parseInt(sc.nextLine());
                    servicio.buscarVenta(nv);
                }
                if (opcionVentas.equals("4")) {
                    System.out.println(servicio.imprimirtodasVentas());
                }

            } while (!opcionVentas.equals("0"));

        } catch (Exception e) {
            System.out.println("Opcion no valida");
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
