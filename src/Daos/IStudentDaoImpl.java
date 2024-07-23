/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Connections.JavaConnectionsSql;
import Models.Carrera;
import Models.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rafae
 */
public class IStudentDaoImpl implements IStudentDao {

    @Override
    public void crearEstudiante(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiantes (nombre, identificacion, id_carrera, semestre, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getIdentificacion());
            stmt.setInt(3, estudiante.getCarrera().getId()); // ID de la carrera seleccionada
            stmt.setInt(4, estudiante.getSemestre());
            stmt.setBoolean(5, estudiante.isEstado());

            stmt.executeUpdate();
            System.out.println("Estudiante creado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public int obtenerIdPorCedula(String cedula) throws SQLException {
        String query = "SELECT id FROM estudiantes WHERE identificacion = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1; // Devuelve -1 si no se encuentra el estudiante
    }

    @Override
    public ResultSet obtenerTodasLasCarreras() throws SQLException {
        String sql = "SELECT id, nombreCarrera FROM carrera";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return rs;
    }

    @Override
    public ResultSet obtenerTodasEstudiante() throws SQLException {
        String sql = "SELECT * FROM estudiantes";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return rs;
    }

    @Override
    public boolean existeIdentificacion(String identificacion) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM estudiantes WHERE identificacion = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, identificacion);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

  // Otros métodos de la implementación aquí
    
    @Override
    public ResultSet buscarEstudiantePorCedula(String cedula) throws SQLException {
        String sql = "SELECT * FROM estudiantes WHERE identificacion = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cedula);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public ResultSet obtenerTodasEstudianteConCarrera() throws SQLException {
        String sql = "SELECT e.*, c.nombreCarrera "
                + "FROM estudiantes e "
                + "JOIN carrera c ON e.id_carrera = c.id";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Aquí generalmente se cierran las conexiones, pero depende de cómo manejes tu código
        }

        return rs;
    }

    @Override
    public Estudiante buscarEstudiantePorIdentificacion(String identificacion) throws SQLException {
        String sql = "SELECT e.id, e.nombre, e.id_carrera, e.semestre, e.estado, c.nombre AS nombre_carrera "
                + "FROM estudiantes e "
                + "JOIN carrera c ON e.id_carrera = c.id "
                + "WHERE e.identificacion = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, identificacion);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nombreEstudiante = rs.getString("nombre");
                int idCarrera = rs.getInt("id_carrera");
                String nombreCarrera = rs.getString("nombre_carrera");
                Integer semestre = rs.getInt("semestre");
                boolean estado = rs.getBoolean("estado");

                // Crear el objeto Carrera con el id y nombre obtenidos
                Carrera carrera = new Carrera(idCarrera, nombreCarrera);

                return new Estudiante(id, nombreEstudiante, identificacion, carrera, semestre, estado);
            } else {
                return null; // Retorna null si no se encuentra ningún estudiante con esa identificación
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            // No cerramos la conexión aquí para que pueda ser utilizada externamente
        }
    }

    @Override
    public boolean eliminarEstudiante(int id) throws SQLException {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se eliminó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

   
    

  
    @Override
public boolean actualizarEstudiante(Estudiante estudiante) throws SQLException {
    String sql = "UPDATE estudiantes SET nombre = ?, identificacion = ?, id_carrera = ?, semestre = ?, estado = ? WHERE id = ?";
    
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, estudiante.getNombre());
        stmt.setString(2, estudiante.getIdentificacion());
        stmt.setInt(3, estudiante.getCarrera().getId()); // ID de la carrera seleccionada
        stmt.setInt(4, estudiante.getSemestre());
        stmt.setBoolean(5, estudiante.isEstado());
        stmt.setInt(6, estudiante.getId()); // ID del estudiante a actualizar

        int filasActualizadas = stmt.executeUpdate();
        return filasActualizadas > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

@Override
public boolean eliminarEstudiantePorCedula(String cedula) throws SQLException {
    String sql = "UPDATE estudiantes SET eliminado = 1 WHERE identificacion = ?";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, cedula);
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;
    }
}

@Override
public boolean esEstudianteEliminado(int id) throws SQLException {
    String sql = "SELECT eliminado FROM estudiantes WHERE id = ?";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("eliminado") == 1;
            } else {
                throw new SQLException("No se encontró ningún estudiante con el ID proporcionado");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}


}
