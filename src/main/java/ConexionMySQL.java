import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión con la base de datos MySQL.
 */
public class ConexionMySQL {

    // Método para establecer la conexión con la base de datos
    public static Connection conectar() {
        String url = "jdbc:mysql://localhost:3307/Conexion_Proyecto"; // Cambia el puerto si es necesario
        String usuario = "root";  // Tu usuario de MySQL
        String contrasena = "Liz_30031989*";    // Tu contraseña de MySQL

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    // Método para insertar un usuario en la base de datos
    public static void insertarUsuario(String nombre, String correo) {
        String query = "INSERT INTO usuarios (nombre, correo) VALUES (?, ?)";

        try (Connection conexion = conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, nombre);
            stmt.setString(2, correo);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario insertado exitosamente.");
            } else {
                System.out.println("No se pudo insertar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario: " + e.getMessage());
        }
    }

    // Método para consultar los usuarios de la base de datos
    public static void consultarUsuarios() {
        String query = "SELECT * FROM usuarios";

        try (Connection conexion = conectar();
             PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Iterar sobre los resultados
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Correo: " + correo);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los usuarios: " + e.getMessage());
        }
    }

    // Método para eliminar un usuario de la base de datos
    public static void eliminarUsuario(int id) {
        String query = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conexion = conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Método principal para insertar, consultar y eliminar usuarios
    public static void main(String[] args) {
        // Insertar usuarios
        insertarUsuario("Andrés Quintero", "andres.quintero@email.com");
        insertarUsuario("Juan Pérez", "juan.perez@email.com");
        insertarUsuario("Camila Fuentes", "camila.fuentes@email.com");

        // Consultar y mostrar los usuarios
        System.out.println("Usuarios antes de eliminar:");
        consultarUsuarios();

        // Eliminar un usuario por su ID (por ejemplo, el ID 2)
        eliminarUsuario(2);

        // Consultar y mostrar los usuarios después de eliminar
        System.out.println("Usuarios después de eliminar:");
        consultarUsuarios();
    }
}