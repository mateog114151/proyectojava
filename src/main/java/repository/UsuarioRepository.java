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

    /** Insertar usuario a la base de datos */
    public void insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, edad) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setLong(2, usuario.getEdad());
            preparedStatement.execute();

            System.out.println("Usuario insertado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Listar todos los usuarios */
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT nombre, edad FROM usuario";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                Long edad = rs.getLong("edad");
                usuarios.add(new Usuario(nombre, edad));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
