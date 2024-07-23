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
import Models.Facultad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IFacultadDAOImpl implements IFacultadDAO {

     @Override
    public int obtenerIdFacultadPorNombre(String nombreFacultad) throws SQLException {
        int idFacultad = -1; // Valor por defecto si no se encuentra la facultad

        String sql = "SELECT id FROM facultad WHERE nombre = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreFacultad);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idFacultad = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return idFacultad;
    }
    
    @Override
    public void crearFacultad(Facultad facultad) throws SQLException {
        String sql = "INSERT INTO facultad (nombre) VALUES (?)";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, facultad.getNombre());

            stmt.executeUpdate();
            System.out.println("Facultad creada exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


   @Override
    public Facultad obtenerFacultadPorId(int id) throws SQLException {
        String sql = "SELECT * FROM facultad WHERE id = ?";
        Facultad facultad = null;

        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                facultad = new Facultad();
                facultad.setId(rs.getInt("id"));
                facultad.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return facultad;
    }

 @Override
    public boolean actualizarFacultad(Facultad facultad) throws SQLException {
        String sql = "UPDATE facultad SET nombre = ? WHERE id = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, facultad.getNombre());
            stmt.setInt(2, facultad.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

     @Override
    public ResultSet obtenerTodasLasFacultadesConId() throws SQLException {
        String sql = "SELECT id, nombre FROM facultad";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            // Manejar excepciones o lanzarlas para ser manejadas en el controlador
            throw e;
        } finally {
            // No cerrar la conexión aquí, se debe hacer en el método que llame a obtenerTodasLasFacultadesConId()
            // Cerrar stmt y rs adecuadamente
        }

        return rs;
    }
  @Override
    public boolean eliminarFacultad(int id) throws SQLException {
        String sql = "DELETE FROM facultad WHERE id = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Si se eliminó correctamente, debe ser mayor a 0
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    


       @Override
    public ResultSet obtenerTodasLasFacultades() throws SQLException {
        String sql = "SELECT * FROM facultad";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
