/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

/**
 *
 * @author rafae
 */
import Connections.JavaConnectionsSql;
import Models.Carrera;
import Models.Facultad;
import java.sql.*;

public class ICarreraDAOImpl implements ICarreraDAO {
  
@Override
public void crearCarrera(Carrera carrera) throws SQLException {
    String sql = "INSERT INTO carrera (nombreCarrera, id_facultad) VALUES (?, ?)";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, carrera.getNombreCarrera());
        stmt.setInt(2, carrera.getFacultad().getId());

        stmt.executeUpdate(); // Ejecuta la actualización, no se necesita una declaración de retorno
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
@Override
    public boolean existeCarrera(String nombreCarrera, int idFacultad) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM carrera WHERE nombreCarrera = ? AND id_facultad = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreCarrera);
            stmt.setInt(2, idFacultad);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    @Override
    public boolean existenCarrerasPorFacultad(int idFacultad) throws SQLException {
        String query = "SELECT COUNT(*) FROM carrera WHERE id_facultad = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idFacultad);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    



    
    @Override
public ResultSet obtenerTodasLasCarreras() throws SQLException {
    String sql = "SELECT id, nombreCarrera, id_facultad FROM carrera"; // Ajusta 'nombreCarrera' al nombre correcto en tu base de datos
    try {
        Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}



@Override
public Carrera obtenerCarreraPorId(int id) throws SQLException {
    String sql = "SELECT * FROM carrera WHERE id = ?";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Carrera carrera = new Carrera();
                carrera.setId(rs.getInt("id"));
                carrera.setNombreCarrera(rs.getString("nombreCarrera")); // Ajusta el nombre según tu base de datos
                int facultadId = rs.getInt("id_facultad");
                Facultad facultad = obtenerFacultadPorId(facultadId);
                carrera.setFacultad(facultad);
                return carrera;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Considera un manejo más robusto de la excepción en tu aplicación
        throw e;
    }
    return null;
}

@Override
public void actualizarCarrera(Carrera carrera) throws SQLException {
    // Verificar la existencia del ID de facultad antes de actualizar
    if (!existeFacultad(carrera.getFacultad().getId())) {
        throw new SQLException("El ID de facultad " + carrera.getFacultad().getId() + " no existe en la tabla facultad.");
    }

    // Sentencia SQL para actualizar la carrera
    String sql = "UPDATE carrera SET nombreCarrera = ?, id_facultad = ? WHERE id = ?";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, carrera.getNombreCarrera());
        stmt.setInt(2, carrera.getFacultad().getId());
        stmt.setInt(3, carrera.getId());

        int filasActualizadas = stmt.executeUpdate();
        if (filasActualizadas == 0) {
            throw new SQLException("No se pudo actualizar la carrera con ID " + carrera.getId());
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
 @Override
    public Carrera buscarCarreraPorId(int id) throws SQLException {
        String sql = "SELECT * FROM carrera WHERE id = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Carrera carrera = new Carrera();
                carrera.setId(rs.getInt("id"));
                carrera.setNombreCarrera(rs.getString("nombreCarrera")); // Ajustar según la estructura de tu base de datos
                int facultadId = rs.getInt("id_facultad");
                Facultad facultad = obtenerFacultadPorId(facultadId); // Implementar este método o utilizar tu DAO correspondiente
                carrera.setFacultad(facultad);
                return carrera;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null; // Retorna null si no se encontró la carrera con el ID especificado
    }

// Método para verificar la existencia de un ID de facultad en la tabla facultad
public boolean existeFacultad(int idFacultad) throws SQLException {
    String sql = "SELECT COUNT(*) AS count FROM facultad WHERE id = ?";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idFacultad);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("count");
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
    return false;
}


@Override
public boolean eliminarCarrera(int idCarrera) throws SQLException {
    String sql = "DELETE FROM carrera WHERE id = ?";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idCarrera);
        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

@Override
    public boolean existenEstudiantesPorCarrera(int idCarrera) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM estudiantes WHERE id_carrera = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCarrera);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Retorna true si hay estudiantes asociados, false si no hay ninguno
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false; // En caso de error o si no se encuentra ningún estudiante asociado
    }

    private Facultad obtenerFacultadPorId(int id) throws SQLException {
        String sql = "SELECT * FROM facultad WHERE id = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Facultad facultad = new Facultad();
                facultad.setId(rs.getInt("id"));
                facultad.setNombre(rs.getString("nombre"));
                return facultad;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }
    @Override
    public String obtenerNombreCarrera(int idCarrera) throws SQLException {
        String nombreCarrera = null;
        String query = "SELECT nombreCarrera FROM carrera WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idCarrera);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nombreCarrera = rs.getString("nombreCarrera");
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return nombreCarrera;
    }
}