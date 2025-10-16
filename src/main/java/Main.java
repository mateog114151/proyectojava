package com.sena;

import java.util.List;
import java.util.Scanner;

import model.Usuario;
import repository.UsuarioRepository;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UsuarioRepository usuarioRepository = new UsuarioRepository();

    public static void main(String[] args) {
        int opcion;
        
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    buscarUsuario();
                    break;
                case 4:
                    editarUsuario();
                    break;
                case 5:
                    eliminarUsuario();
                    break;
                case 6:
                    System.out.println("\n¡Hasta pronto!");
                    break;
                default:
                    System.out.println("\n❌ Opción inválida. Intente nuevamente.");
            }
            
            if (opcion != 6) {
                esperarEnter();
            }
            
        } while (opcion != 6);
        
        scanner.close();
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║    SISTEMA DE GESTIÓN DE USUARIOS  ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1. Crear nuevo usuario");
        System.out.println("2. Listar todos los usuarios");
        System.out.println("3. Buscar usuario");
        System.out.println("4. Editar usuario");
        System.out.println("5. Eliminar usuario");
        System.out.println("6. Salir");
        System.out.print("\nSeleccione una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void crearUsuario() {
        System.out.println("\n═══ CREAR NUEVO USUARIO ═══");
        
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese edad: ");
        Long edad = Long.parseLong(scanner.nextLine());
        
        Usuario usuario = new Usuario(nombre, edad);
        usuarioRepository.insertarUsuario(usuario);
    }
    
    private static void listarUsuarios() {
        System.out.println("\n═══ LISTADO DE USUARIOS ═══");
        List<Usuario> usuarios = usuarioRepository.listarUsuarios();
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        
        System.out.println("\n┌──────┬──────┬─────────────────────────┬────────┐");
        System.out.println("│  #   │  ID  │         NOMBRE          │  EDAD  │");
        System.out.println("├──────┼──────┼─────────────────────────┼────────┤");
        
        int contador = 1;
        for (Usuario u : usuarios) {
            System.out.printf("│ %-4d │ %-4d │ %-23s │ %-6d │%n", 
                contador++, u.getId(), u.getNombre(), u.getEdad());
        }
        
        System.out.println("└──────┴──────┴─────────────────────────┴────────┘");
        System.out.println("Total de usuarios: " + usuarios.size());
    }
    
    private static void buscarUsuario() {
        System.out.println("\n═══ BUSCAR USUARIO ═══");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por nombre");
        System.out.print("\nSeleccione tipo de búsqueda: ");
        
        int opcion = leerOpcion();
        Usuario usuario = null;
        
        switch (opcion) {
            case 1:
                System.out.print("Ingrese el ID: ");
                Integer id = Integer.parseInt(scanner.nextLine());
                usuario = usuarioRepository.buscarPorId(id);
                break;
            case 2:
                System.out.print("Ingrese el nombre: ");
                String nombre = scanner.nextLine();
                List<Usuario> usuarios = usuarioRepository.buscarPorNombre(nombre);
                
                if (usuarios.isEmpty()) {
                    System.out.println("\n❌ No se encontraron usuarios con ese nombre.");
                    return;
                }
                
                System.out.println("\n✅ Usuarios encontrados:");
                System.out.println("┌──────┬─────────────────────────┬────────┐");
                System.out.println("│  ID  │         NOMBRE          │  EDAD  │");
                System.out.println("├──────┼─────────────────────────┼────────┤");
                
                for (Usuario u : usuarios) {
                    System.out.printf("│ %-4d │ %-23s │ %-6d │%n", 
                        u.getId(), u.getNombre(), u.getEdad());
                }
                
                System.out.println("└──────┴─────────────────────────┴────────┘");
                return;
            default:
                System.out.println("\n❌ Opción inválida.");
                return;
        }
        
        if (usuario != null) {
            System.out.println("\n✅ Usuario encontrado:");
            System.out.printf("ID: %d | Nombre: %s | Edad: %d%n", 
                usuario.getId(), usuario.getNombre(), usuario.getEdad());
        } else {
            System.out.println("\n❌ Usuario no encontrado.");
        }
    }
    
    private static void editarUsuario() {
        System.out.println("\n═══ EDITAR USUARIO ═══");
        
        listarUsuarios();
        
        System.out.print("\nIngrese el ID del usuario a editar: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        
        Usuario usuario = usuarioRepository.buscarPorId(id);
        
        if (usuario == null) {
            System.out.println("\n❌ Usuario no encontrado.");
            return;
        }
        
        System.out.println("\nUsuario actual:");
        System.out.printf("Nombre: %s | Edad: %d%n", usuario.getNombre(), usuario.getEdad());
        
        System.out.print("\nIngrese el nuevo nombre (Enter para mantener actual): ");
        String nuevoNombre = scanner.nextLine();
        if (nuevoNombre.trim().isEmpty()) {
            nuevoNombre = usuario.getNombre();
        }
        
        System.out.print("Ingrese la nueva edad (0 para mantener actual): ");
        String edadStr = scanner.nextLine();
        Long nuevaEdad = usuario.getEdad();
        
        if (!edadStr.trim().isEmpty()) {
            Long edadTemp = Long.parseLong(edadStr);
            if (edadTemp != 0) {
                nuevaEdad = edadTemp;
            }
        }
        
        Usuario usuarioActualizado = new Usuario(id, nuevoNombre, nuevaEdad);
        usuarioRepository.actualizarUsuario(usuarioActualizado);
    }
    
    private static void eliminarUsuario() {
        System.out.println("\n═══ ELIMINAR USUARIO ═══");
        
        listarUsuarios();
        
        System.out.print("\nIngrese el ID del usuario a eliminar: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        
        Usuario usuario = usuarioRepository.buscarPorId(id);
        
        if (usuario == null) {
            System.out.println("\n❌ Usuario no encontrado.");
            return;
        }
        
        System.out.println("\nUsuario a eliminar:");
        System.out.printf("ID: %d | Nombre: %s | Edad: %d%n", 
            usuario.getId(), usuario.getNombre(), usuario.getEdad());
        
        System.out.print("\n¿Está seguro de eliminar este usuario? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("S")) {
            usuarioRepository.eliminarUsuario(id);
        } else {
            System.out.println("\n❌ Operación cancelada.");
        }
    }
    
    private static void esperarEnter() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
