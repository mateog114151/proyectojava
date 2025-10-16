    import java.util.List;
    import java.util.Scanner;

    import model.Usuario;
    import repository.UsuarioRepository;

    public class Main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            UsuarioRepository usuarioRepository = new UsuarioRepository();

            // Pedir datos al usuario
            System.out.print("Ingrese nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese edad: ");
            Long edad = scanner.nextLong();

            // Crear usuario nuevo
            Usuario usuario = new Usuario(nombre, edad);

            // Insertar usuario nuevo
            usuarioRepository.insertarUsuario(usuario);

            // Listar todos los usuarios
            System.out.println("\nUsuarios registrados en la BD:");
            List<Usuario> usuarios = usuarioRepository.listarUsuarios();
            for (Usuario u : usuarios) {
                System.out.println("Nombre: " + u.getNombre() + " | Edad: " + u.getEdad());
            }

            scanner.close();
        }
    }
