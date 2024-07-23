/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Connections.JavaConnectionsSql;
import Models.Admin;
import Models.BienestarUniversitario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author rafae
 */
public class BienestarUniversitarioDaoImpl implements BienestarUniversitarioDao {

    @Override
    public void crearBienestarUniversitario(BienestarUniversitario bienestarUniversitario) throws SQLException {
        String sql = "INSERT INTO bienestar (nombreUsuario, contrasena) VALUES (?, ?)";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bienestarUniversitario.getNombreUsuario());
            stmt.setString(2, bienestarUniversitario.getContrasena());

            stmt.executeUpdate();
            System.out.println("Admin creado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

 

    @Override
    public Optional<BienestarUniversitario> verificarCredenciales(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM bienestar WHERE nombreUsuario = ? AND contrasena = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    BienestarUniversitario bienestarUniversitario = new BienestarUniversitario(id, nombreUsuario, contrasena);
                    return Optional.of(bienestarUniversitario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    
public ResultSet obtenerFacultadesYTicketsResultSet() throws SQLException {
    String sql = "SELECT f.id, f.nombre AS nombre_facultad, COUNT(t.id) AS cantidad_tickets " +
                 "FROM facultad f " +
                 "LEFT JOIN carrera c ON f.id = c.id_facultad " +
                 "LEFT JOIN estudiantes e ON c.id = e.id_carrera " +
                 "LEFT JOIN tickets t ON e.id = t.estudiante_id " +
                 "GROUP BY f.id ORDER BY cantidad_tickets DESC";
 
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

    // Devolver el ResultSet para que se maneje externamente
    // No cerramos la conexión y el statement aquí, se espera que se manejen externamente
    return rs;
}
 public ResultSet obtenerCarrerasConTickets() throws SQLException {
         String sql = "SELECT c.id AS carrera_id, c.nombreCarrera AS nombre_carrera, COUNT(t.id) AS cantidad_tickets " +
                     "FROM carrera c " +
                     "LEFT JOIN estudiantes e ON e.id_carrera = c.id " +
                     "LEFT JOIN tickets t ON t.estudiante_id = e.id " +
                     "GROUP BY c.id, c.nombreCarrera  " +
                     "ORDER BY cantidad_tickets DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace();
            throw e;  // Re-lanzar la excepción para manejarla externamente
        }

        // Devolver el ResultSet para que se maneje externamente
        // No cerramos la conexión y el statement aquí, se espera que se manejen externamente
        return rs;
    }
}


