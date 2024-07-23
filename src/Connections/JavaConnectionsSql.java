package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaConnectionsSql {
    // Instancia única de la clase
    private static JavaConnectionsSql instancia;

    // Datos de conexión
    private final String url = "jdbc:mysql://localhost:3306/shoptickets";
    private final String user = "root";
    private final String password = "root";
    private Connection connection;

    // Constructor privado para evitar instanciación externa
    private JavaConnectionsSql() {
        // Registrar el driver de MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método estático para obtener la instancia única
    public static synchronized JavaConnectionsSql obtenerInstancia() {
        if (instancia == null) {
            instancia = new JavaConnectionsSql();
        }
        return instancia;
    }

    // Método para establecer la conexión
    public Connection establecerConexion() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Se conectó");
        }
        return connection;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Se cerró la conexión");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
