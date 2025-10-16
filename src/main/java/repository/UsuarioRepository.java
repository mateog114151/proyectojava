package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Usuario;

public class UsuarioRepository {

    public void insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, edad) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setLong(2, usuario.getEdad());
            ps.execute();

            System.out.println("\n✅ Usuario insertado correctamente");

        } catch (Exception e) {
            System.out.println("\n❌ Error al insertar usuario");
            e.printStackTrace();
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, edad FROM usuario";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Long edad = rs.getLong("edad");
                usuarios.add(new Usuario(id, nombre, edad));
            }

        } catch (Exception e) {
            System.out.println("\n❌ Error al listar usuarios");
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario buscarPorId(Integer id) {
        String sql = "SELECT id, nombre, edad FROM usuario WHERE id = ?";
        Usuario usuario = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Integer idUsuario = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Long edad = rs.getLong("edad");
                usuario = new Usuario(idUsuario, nombre, edad);
            }

        } catch (Exception e) {
            System.out.println("\n❌ Error al buscar usuario por ID");
            e.printStackTrace();
        }
        return usuario;
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, edad FROM usuario WHERE nombre LIKE ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nombreUsuario = rs.getString("nombre");
                Long edad = rs.getLong("edad");
                usuarios.add(new Usuario(id, nombreUsuario, edad));
            }

        } catch (Exception e) {
            System.out.println("\n❌ Error al buscar usuarios por nombre");
            e.printStackTrace();
        }
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, edad = ? WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setLong(2, usuario.getEdad());
            ps.setInt(3, usuario.getId());
            
            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("\n✅ Usuario actualizado correctamente");
            } else {
                System.out.println("\n❌ No se pudo actualizar el usuario");
            }

        } catch (Exception e) {
            System.out.println("\n❌ Error al actualizar usuario");
            e.printStackTrace();
        }
    }

    public void eliminarUsuario(Integer id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int filasEliminadas = ps.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("\n✅ Usuario eliminado correctamente");
                
                // Verificar si la tabla quedó vacía
                verificarYResetearAutoIncrement(conn);
            } else {
                System.out.println("\n❌ No se pudo eliminar el usuario");
            }

        } catch (Exception e) {
            System.out.println("\n❌ Error al eliminar usuario");
            e.printStackTrace();
        }
    }
    
    private void verificarYResetearAutoIncrement(Connection conn) {
        try {
            String countSql = "SELECT COUNT(*) as total FROM usuario";
            PreparedStatement ps = conn.prepareStatement(countSql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next() && rs.getInt("total") == 0) {
                String resetSql = "ALTER TABLE usuario AUTO_INCREMENT = 1";
                PreparedStatement resetPs = conn.prepareStatement(resetSql);
                resetPs.execute();
                System.out.println("🔄 Contador de ID reiniciado a 1");
            }
            
        } catch (Exception e) {
        }
    }
}