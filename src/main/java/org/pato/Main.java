package org.pato;
import java.util.Scanner;

public class Main {

    // Matriz de usuarios:
    // Columna 0 = Nombre, Columna 1 = Correo, Columna 2 = Contraseña
    static String[][] usuarios = new String[100][3];
    static int totalUsuarios = 0;

    // Matriz de Wishlist:
    // Columna 0 = Correo del dueño, Columna 1 = Nombre del disco, Columna 2 = Precio
    static String[][] wishlist = new String[100][3];
    static int totalDeseos = 0;

    static Scanner scanner = new Scanner(System.in);
    static int usuarioActual = -1; // Guarda la fila del usuario con sesión iniciada

    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("\n===========================");
            System.out.println("      DISCOS SALVAJES");
            System.out.println("===========================");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                System.out.println("Opción inválida. Ingrese un número.");
                continue;
            }

            if (opcion == 1) {
                registrarUsuario();
            } else if (opcion == 2) {
                iniciarSesion();
            } else if (opcion == 0) {
                System.out.println("Fin del programa.");
            } else {
                System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    // MÓDULO DE REGISTRO
    public static void registrarUsuario() {
        if (totalUsuarios >= 100) {
            System.out.println("El sistema está lleno, no se pueden registrar más usuarios.");
            return;
        }

        System.out.println("\n--- MÓDULO DE REGISTRO ---");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine();

        // Verificar si el correo ya existe en la matriz
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i][1].equals(correo)) {
                System.out.println("Error: Este correo ya se encuentra registrado.");
                return;
            }
        }

        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        // Guardar en la matriz de usuarios
        usuarios[totalUsuarios][0] = nombre;
        usuarios[totalUsuarios][1] = correo;
        usuarios[totalUsuarios][2] = contraseña;
        totalUsuarios++;

        System.out.println("¡Usuario registrado con éxito!");
    }

    // MÓDULO DE INICIO DE SESIÓN
    public static void iniciarSesion() {
        System.out.println("\n--- MÓDULO DE INICIO DE SESIÓN ---");
        System.out.print("Ingrese su correo: ");
        String correoIngresado = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contraseñaIngresada = scanner.nextLine();

        int encontradaFila = -1;
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i][1].equals(correoIngresado)) {
                if (usuarios[i][2].equals(contraseñaIngresada)) {
                    encontradaFila = i;
                }
                break;
            }
        }

        if (encontradaFila != -1) {
            usuarioActual = encontradaFila;
            System.out.println("¡Inicio de sesión exitoso! Bienvenido, " + usuarios[usuarioActual][0]);
            menuWishlist();
        } else {
            System.out.println("Correo o contraseña incorrectos.");
        }
    }

    // MENÚ DE LA WISHLIST (DESPUÉS DE INICIAR SESIÓN)
    public static void menuWishlist() {
        int opcion = 0;
        do {
            System.out.println("\n--- MI WISHLIST DE VINILOS ---");
            System.out.println("1. Ver mi wishlist");
            System.out.println("2. Agregar disco");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                System.out.println("Opción inválida.");
                continue;
            }

            if (opcion == 1) {
                mostrarWishlist();
            } else if (opcion == 2) {
                agregarDisco();
            } else if (opcion == 0) {
                System.out.println("Sesión cerrada.");
                usuarioActual = -1;
            } else {
                System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    // MOSTRAR WISHLIST
    public static void mostrarWishlist() {
        String correoActual = usuarios[usuarioActual][1];
        System.out.println("\n--- WISHLIST DE " + usuarios[usuarioActual][0].toUpperCase() + " ---");

        boolean hayDiscos = false;
        double totalPrecio = 0;
        int contador = 1;

        for (int i = 0; i < totalDeseos; i++) {
            // Si el correo del disco coincide con el usuario logueado
            if (wishlist[i][0].equals(correoActual)) {
                double precio = Double.parseDouble(wishlist[i][2]);
                System.out.println(contador + ". Álbum: " + wishlist[i][1] + " | Precio: $" + wishlist[i][2]);
                totalPrecio += precio;
                contador++;
                hayDiscos = true;
            }
        }

        if (!hayDiscos) {
            System.out.println("Tu wishlist está vacía.");
        } else {
            System.out.println("Total estimado a gastar: $" + totalPrecio);
        }
    }

    // AGREGAR DISCO A LA WISHLIST
    public static void agregarDisco() {
        if (totalDeseos >= 100) {
            System.out.println("La lista de deseos global está llena.");
            return;
        }

        System.out.println("\n--- AGREGAR DISCO ---");
        System.out.print("Nombre del álbum: ");
        String album = scanner.nextLine();
        System.out.print("Precio del vinilo: ");

        String precioStr = scanner.nextLine().replace(",", ".");

        // Guardar en la matriz de deseos
        wishlist[totalDeseos][0] = usuarios[usuarioActual][1]; // Correo del dueño
        wishlist[totalDeseos][1] = album;                     // Nombre del álbum
        wishlist[totalDeseos][2] = precioStr;                 // Precio
        totalDeseos++;

        System.out.println("¡Disco agregado a tu wishlist con éxito!");
    }
}