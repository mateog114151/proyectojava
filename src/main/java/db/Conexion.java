package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    /* ruta de servidor (localhost + nombre base de datos) */
    private static final String URL = "jdbc:mysql://localhost:3306/primer_programa";
    /* Nombre de usuario de base de datos */
    private static final String USER = "root";
    /* Contraseña de usuario de base de datos */
    private static final String PASSWORD = "";

    /**
     * Método para obtener la conexión de la base de datos
     * @return objeto de conexión para usarla
     * @throws SQLException controlar errores de conectividad a la base de datos
     */
    public static Connection getConnection() throws SQLException {
        /* Retorna nuestra conexion a la base de datos */
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
