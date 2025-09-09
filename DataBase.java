import java.util.Scanner;

public class DataBase {

    static class Cliente {
        String nombre;
        float salario;
        char sexo;
        String cargo;

        Cliente(String nombre, float salario, char sexo, String cargo) {
            this.nombre = nombre;
            this.salario = salario;
            this.sexo = sexo;
            this.cargo = cargo;
        }
    }

    static Cliente[] clientes = new Cliente[30];
    static int cantidad = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean activo = true;

        while (activo) {
            char opcion = mostrarMenu();

            switch (opcion) {
                case '1':
                    if (cantidad < clientes.length) {
                        agregarCliente(sc);
                    } else {
                        System.out.println("La base de datos está llena.");
                    }
                    break;
                case '2':
                    listarClientes();
                    break;
                case '3':
                    modificarCliente(sc);
                    break;
                case '4':
                    eliminarCliente(sc);
                    break;
                case '5':
                    activo = false;
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        sc.close();
    }

    public static char mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       GESTIÓN DE CLIENTES    ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ 1. Agregar cliente           ║");
        System.out.println("║ 2. Ver clientes              ║");
        System.out.println("║ 3. Editar cliente            ║");
        System.out.println("║ 4. Eliminar cliente          ║");
        System.out.println("║ 5. Salir                     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.print("Seleccione una opción ---> ");
        return sc.next().charAt(0);
    }

    public static void agregarCliente(Scanner sc) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Salario: ");
        float salario = sc.nextFloat();
        sc.nextLine();

        System.out.print("Sexo (M/F): ");
        char sexo = sc.next().charAt(0);
        sc.nextLine();

        System.out.print("Cargo: ");
        String cargo = sc.nextLine();

        clientes[cantidad++] = new Cliente(nombre, salario, sexo, cargo);
        System.out.println("Cliente registrado.");
    }

    public static void listarClientes() {
        if (cantidad == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║                LISTA DE CLIENTES               ║");
        System.out.println("╠════╦════════════════╦══════════╦══════╦════════╣");
        System.out.println("║ #  ║ Nombre         ║ Salario  ║ Sexo ║ Cargo  ║");
        System.out.println("╠════╬════════════════╬══════════╬══════╬════════╣");

        for (int i = 0; i < cantidad; i++) {
            Cliente c = clientes[i];
            System.out.printf("║ %-2d ║ %-14s ║ %-8.2f ║  %-3s ║ %-6s ║\n",
                    i + 1, c.nombre, c.salario, c.sexo, c.cargo);
        }

        System.out.println("╚════╩════════════════╩══════════╩══════╩════════╝");
    }

    public static int[] buscarPorNombre(String nombre) {
        int[] coincidencias = new int[cantidad];
        int contador = 0;

        for (int i = 0; i < cantidad; i++) {
            if (clientes[i].nombre.equalsIgnoreCase(nombre)) {
                coincidencias[contador++] = i;
            }
        }

        int[] resultado = new int[contador];
        System.arraycopy(coincidencias, 0, resultado, 0, contador);
        return resultado;
    }

    public static int seleccionarCliente(Scanner sc, int[] posiciones) {
        if (posiciones.length == 1) {
            return posiciones[0];
        }

        System.out.println("Se encontraron varios clientes con ese nombre:");
        for (int i = 0; i < posiciones.length; i++) {
            Cliente c = clientes[posiciones[i]];
            System.out.printf("%d. %s - %.2f - %s - %s\n",
                    i + 1, c.nombre, c.salario, c.sexo, c.cargo);
        }

        System.out.print("Seleccione el número correspondiente: ");
        int seleccion = sc.nextInt();
        sc.nextLine();
        return posiciones[seleccion - 1];
    }

    public static void modificarCliente(Scanner sc) {
        if (cantidad == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        listarClientes();

        System.out.print("Ingrese el nombre del cliente a editar: ");
        String nombre = sc.nextLine();

        int[] encontrados = buscarPorNombre(nombre);

        if (encontrados.length == 0) {
            System.out.println("No se encontró ningún cliente con ese nombre.");
            return;
        }

        int idx = seleccionarCliente(sc, encontrados);
        Cliente c = clientes[idx];

        System.out.print("Nuevo nombre (" + c.nombre + "): ");
        c.nombre = sc.nextLine();

        System.out.print("Nuevo salario (" + c.salario + "): ");
        c.salario = sc.nextFloat();
        sc.nextLine();

        System.out.print("Nuevo sexo (" + c.sexo + "): ");
        c.sexo = sc.next().charAt(0);
        sc.nextLine();

        System.out.print("Nuevo cargo (" + c.cargo + "): ");
        c.cargo = sc.nextLine();

        System.out.println("Datos actualizados correctamente.");
    }

    public static void eliminarCliente(Scanner sc) {
        if (cantidad == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        listarClientes();

        System.out.print("Ingrese el nombre del cliente a eliminar: ");
        String nombre = sc.nextLine();

        int[] encontrados = buscarPorNombre(nombre);

        if (encontrados.length == 0) {
            System.out.println("No se encontró ningún cliente con ese nombre.");
            return;
        }

        int idx = seleccionarCliente(sc, encontrados);

        for (int i = idx; i < cantidad - 1; i++) {
            clientes[i] = clientes[i + 1];
        }

        clientes[--cantidad] = null;

        System.out.println("Cliente eliminado.");
    }
}

